package com.ls.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.entity.Problem;
import com.ls.repository.CompanyAdditionalRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.ProblemRepository;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.CompanySearchVo;

@Service("companyService")
public class CompanyServiceImpl implements CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private ProblemRepository problemRepository;

	
	@Autowired
	private CompanyAdditionalRepository companyAdditionalRepository;

	public List<Company> findCompany(String name) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Company> findAllCompanies() {
		return companyRepository.findAll();
	}

	public Page<Company> getCompanyInPage(Integer index) {

		return companyRepository.findAll(new PageRequest(index, 10));
	}

	public Problem saveProblem(Problem problem) {

		return problemRepository.save(problem);
	}

	
	public Page<Company> getCompanyInPage(String companyNameParam, String contactorParam, String starParam, String allStarCheckboxParam, String distinctParam, Integer pageNumber) {
		Page<Company> companyPage = companyRepository.findAll(generateSpecification(companyNameParam, contactorParam, starParam, allStarCheckboxParam, distinctParam), new PageRequest(pageNumber, 5));
		
		return companyPage;
	}
	
	public Page<Company> getCompanyInPage(final CompanySearchVo companySearchVo) {
		
		 Page<Company> companyPage = companyRepository.findAll(generateSpecification(companySearchVo), new PageRequest(Integer.valueOf(companySearchVo.getPageNumber()), 5));
		 
		 // fuck lazy loading
		 List<Company> companies = companyPage.getContent();
		 
		 for (Company company : companies) {
			company.setProblems(new ArrayList<Problem>());
			company.setAddtion(null);
		 }
		 
		 return companyPage;
	}
	
	private Specification<Company> generateSpecification(final String companyNameParam, final String contactorParam, final String starParam, final String allStarCheckboxParam, final String distinctParam) {
		
		return new Specification<Company>() {

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				
				if (StringUtils.isNotBlank(companyNameParam)) {
					predicate.getExpressions().add(cb.like(root.<String> get("name"), "%" + companyNameParam.trim() + "%"));
				}
				
				if (StringUtils.isNotBlank(contactorParam)) {
					predicate.getExpressions().add(cb.equal(root.<String> get("contactor"), contactorParam.trim()));
				}
				
				if (StringUtils.isNotBlank(distinctParam)) {
					predicate.getExpressions().add(cb.equal(root.<String> get("area"), distinctParam.trim()));
				}
				
				if (StringUtils.isNotBlank(allStarCheckboxParam)) {
					
					//all star true : ignore star value
					if (allStarCheckboxParam.trim().equalsIgnoreCase("false")) {
						predicate.getExpressions().add(cb.equal(root.<String> get("star"), Integer.valueOf(starParam)));
					}
				}
				return predicate;
			}
		};

	}

	private Specification<Company> generateSpecification(final CompanySearchVo companySearchVo) {
		return new Specification<Company>() {

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

				Predicate predicate = cb.conjunction();
				
				if (StringUtils.isNotBlank(companySearchVo.getCompanyNameParam())) {
					predicate.getExpressions().add(cb.like(root.<String> get("name"), "%" + companySearchVo.getCompanyNameParam().trim() + "%"));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getContactorParam())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("contactor"), companySearchVo.getContactorParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getDistinctParam())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("area"), companySearchVo.getDistinctParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getAllStarCheckboxParam())) {
					
					//all star true : ignore star value
					if (companySearchVo.getAllStarCheckboxParam().trim().equalsIgnoreCase("false")) {
						predicate.getExpressions().add(cb.equal(root.<String> get("star"), Integer.valueOf(companySearchVo.getAllStarCheckboxParam())));
					}
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getCityId())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("cityId"), companySearchVo.getCityId().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getProvinceId())) {
					predicate.getExpressions().add(cb.equal(root.<String> get("provinceId"), companySearchVo.getProvinceId().trim()));
				}
				return predicate;
			}
		};

	}

	public CompanyAdditional saveAdditionalCompanyInformation(CompanyAdditional addtion) {
		return companyAdditionalRepository.saveAndFlush(addtion);
	}

	public CompanyAdditional findCompanyAddtionalInformationByCompanyId(Integer companyId) {
		Company company = companyRepository.findOne(Integer.valueOf(companyId));
		
		CompanyAdditional companyAdditional = companyAdditionalRepository.findByCompany(company);
		// fuck lazy
		companyAdditional.setCompany(null);
		
		return companyAdditional;
	}

	@Transactional
	public void checkOrUncheckProblem(String companyJson, String problemJson,
			String checkFlag) {
		
		Company company = XinXinUtils.getJavaObjectFromJsonString(companyJson, Company.class);
		Problem problem = XinXinUtils.getJavaObjectFromJsonString(problemJson, Problem.class);

		Company freshCompanyFromDb = companyRepository.findOne(company.getId());
		Problem freshProblemFromDb = problemRepository.findOne(problem.getId());

		List<Problem> companyProblemList = freshCompanyFromDb.getProblems();
		List<Company> problemCompanyList = freshProblemFromDb.getCompanies();

		if (companyProblemList == null) {
		        companyProblemList = new ArrayList<Problem>();
		}

		if (problemCompanyList == null) {
		        problemCompanyList = new ArrayList<Company>();
		}
		Boolean checked = Boolean.valueOf(checkFlag);
		//add a problem
		if (checked) {

		        if (findProblemId(companyProblemList, freshProblemFromDb) == null) {
		                companyProblemList.add(freshProblemFromDb);
		        }

		        companyRepository.saveAndFlush(freshCompanyFromDb);

		// remove a problem
		} else {

		        //problem.setCompanies(new ArrayList<Company>());

		        Integer problemIdToRemove = findProblemId(companyProblemList, freshProblemFromDb);
		        if (problemIdToRemove != null) {

		                companyProblemList.remove(freshProblemFromDb);
		        }

		        companyRepository.saveAndFlush(freshCompanyFromDb);
		}
		
	}
	
	private Integer findProblemId(List<Problem> problems, Problem problem) {
		
		if (null == problems || problems.size() == 0) {
			return null;
		}
		
		for (Problem element : problems) {
			
			if (element.getId() == problem.getId()) {
				return problem.getId();
			}
		}
		return null;
	}
	
}
