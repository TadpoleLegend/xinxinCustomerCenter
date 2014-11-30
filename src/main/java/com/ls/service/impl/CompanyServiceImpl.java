package com.ls.service.impl;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableList;
import com.ls.constants.XinXinConstants;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.entity.PhoneCallHistory;
import com.ls.entity.Problem;
import com.ls.entity.Province;
import com.ls.repository.CompanyAdditionalRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.ProvinceRepository;
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
	private ProvinceRepository provinceRepository;

	
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
	
	public Page<Company> getCompanyInPage(final CompanySearchVo companySearchVo) {
		
		 Page<Company> companyPage = companyRepository.findAll(generateSpecification(companySearchVo), new PageRequest(Integer.valueOf(companySearchVo.getPageNumber()) - 1, 10));
		 
		 // fuck lazy loading
		 List<Company> companies = companyPage.getContent();
		 
		 for (Company company : companies) {
			company.setProblems(new ArrayList<Problem>());
			company.setAddtion(null);
			company.setPhoneCallHistories(null);
			company.setLearningHistories(null);
		 }
		 
		 return companyPage;
	}

	private Specification<Company> generateSpecification(final CompanySearchVo companySearchVo) {
		return new Specification<Company>() {

			public Predicate toPredicate(Root<Company> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

			//	root = query.from(Company.class);
				
				Predicate predicate = criteriaBuilder.conjunction();
				
				if (StringUtils.isNotBlank(companySearchVo.getSearchId())) {
					predicate.getExpressions().add(criteriaBuilder.equal(root.get("id"), Integer.valueOf(companySearchVo.getSearchId()))); 
					
					return predicate;
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getCustomerStatus())) {
					Integer status = Integer.valueOf(companySearchVo.getCustomerStatus());
					predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer> get("status"), status));
				}
				//TODO
				predicate.getExpressions().add(criteriaBuilder.or(criteriaBuilder.equal(root.get("ownerUserId"), 1), criteriaBuilder.isNull(root. <Integer> get("ownerUserId")))); //nobody owns it
				
				if (StringUtils.isNotBlank(companySearchVo.getCompanyNameParam())) {
					predicate.getExpressions().add(criteriaBuilder.like(root.<String> get("name"), "%" + companySearchVo.getCompanyNameParam().trim() + "%"));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getContactorParam())) {
					predicate.getExpressions().add(criteriaBuilder.equal(root.<String> get("contactor"), companySearchVo.getContactorParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getDistinctParam())) {
					predicate.getExpressions().add(criteriaBuilder.equal(root.<String> get("area"), companySearchVo.getDistinctParam().trim()));
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getAllStarCheckboxParam())) {
					
					//all star true : ignore star value
					if (companySearchVo.getAllStarCheckboxParam().trim().equalsIgnoreCase("false")) {
						predicate.getExpressions().add(criteriaBuilder.equal(root.<String> get("star"), Integer.valueOf(companySearchVo.getStarParam())));
					}
				}
				
				if (StringUtils.isNotBlank(companySearchVo.getCityId())) {
					
					predicate.getExpressions().add(criteriaBuilder.equal(root.<String> get("cityId"), companySearchVo.getCityId().trim()));
					
				} else if (StringUtils.isNotBlank(companySearchVo.getProvinceId())) {
					Province province = provinceRepository.findOne(Integer.valueOf(companySearchVo.getProvinceId()));
					List<City> cities = province.getCitys();
					
					List<Integer> cityIds = new ArrayList<Integer>();
					for (City city : cities) {
						cityIds.add(city.getId());
					}
					
					predicate.getExpressions().add(root.get("cityId").in(cityIds));
				}
				
				String starLevelComparator = companySearchVo.getStarLevelOperator();
				if (StringUtils.isNotBlank(starLevelComparator) && !starLevelComparator.equals("ALL")) {
					
					Integer star = Integer.valueOf(companySearchVo.getStarParam());
					if (starLevelComparator.equals(">")) {
						predicate.getExpressions().add(criteriaBuilder.greaterThan(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals(">=")) {
						predicate.getExpressions().add(criteriaBuilder.greaterThanOrEqualTo(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("=")) {
						predicate.getExpressions().add(criteriaBuilder.equal(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<")) {
						predicate.getExpressions().add(criteriaBuilder.lessThan(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<=")) {
						predicate.getExpressions().add(criteriaBuilder.lessThanOrEqualTo(root.<Integer> get("star"), star));
					} else if (starLevelComparator.equals("<>")) {
						predicate.getExpressions().add(criteriaBuilder.notEqual(root.<Integer> get("star"), star));
					}
				}
//				Join<Company, PhoneCallHistory> phoneCallHistoryJoin = root.join("phoneCallHistories", JoinType.LEFT);
//				
//				try {
//					predicate.getExpressions().add(criteriaBuilder.equal(phoneCallHistoryJoin.get("nextDate"), XinXinConstants.SIMPLE_DATE_FORMATTER.parse("2014-11-28")));
//				} catch (ParseException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				
				//user private customer shown on the most top.
				Order ownerUserIdOrder = criteriaBuilder.desc(root.get("ownerUserId"));
				Order starOrder = criteriaBuilder.desc(root.get("star"));
				
				List<Order> orders = ImmutableList.of(ownerUserIdOrder, starOrder);	
				
				query.orderBy(orders);
				
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
		if (companyAdditional != null) {
			companyAdditional.setCompany(null);
		}
		
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
