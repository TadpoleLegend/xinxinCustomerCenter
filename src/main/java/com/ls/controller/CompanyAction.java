package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.repository.CompanyRepository;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.CompanySearchVo;
import com.ls.vo.PagedElement;

@Component("companyAction")
public class CompanyAction extends BaseAction {

	private static final long serialVersionUID = 7818205738152334717L;

	private List<Company> companies;
	
	private PagedElement<Company> company;
	
	private Company c;
	
	private CompanyAdditional companyAdditional;

	@Resource(name = "companyService")
	private CompanyService companyService;
	
	@Autowired
	private CompanyRepository companyRepository;

	public String loadAllCompany() {
		String pageNumbersString = getParameter("pageNumber");
		if (null == pageNumbersString) {
			pageNumbersString = "1";
		}
		
		Integer pageNumber = Integer.valueOf(pageNumbersString);
		
		companies = companyService.findAllCompanies();

		return SUCCESS;
	}

	public String loadCompanyInPage() {
		
		String pageNumbersString = getParameter("pageNumber");
		if (null == pageNumbersString) {
			pageNumbersString = "1";
		}
		
		String companyNameParam = getParameter("seachCompany");
		String contactorParam = getParameter("searchContactor");
		String starParam = getParameter("starInput");
		String allStarCheckboxParam = getParameter("allStar");
		String distinctParam = getParameter("searchDistinct");
		String cityId = getParameter("cityId");
		String provinceId = getParameter("provinceId");
		
		CompanySearchVo companySearchVo = new CompanySearchVo();
		companySearchVo.setCompanyNameParam(companyNameParam);
		companySearchVo.setContactorParam(contactorParam);
		companySearchVo.setStarParam(starParam);
		companySearchVo.setAllStarCheckboxParam(allStarCheckboxParam);
		companySearchVo.setDistinctParam(distinctParam);
		companySearchVo.setCityId(cityId);
		companySearchVo.setProvinceId(provinceId);
		companySearchVo.setPageNumber(pageNumbersString);
		
		
		Page<Company> result = companyService.getCompanyInPage(companySearchVo);
		
		company = new PagedElement<Company>(result);
		
		return SUCCESS;
	}
	
	public String saveCompany() {
		String companyId = getParameter("cid");
		
		c = companyRepository.findOne(Integer.valueOf(companyId));
		
		return SUCCESS;
	}
	
	public String saveAddtionalCompanyInformation() {
		
		String companyId = getParameter("company_id");
//		if (StringUtils.isEmpty(companyId)) {
//			return ERROR;
//		}
		
		Company company = companyRepository.findOne(Integer.valueOf(companyId));
		
		CompanyAdditional addition = XinXinUtils.getJavaObjectFromJsonString(getParameter("additionalCompanyInformation"), CompanyAdditional.class);
		
		addition.setCompany(company);
		
		companyAdditional = companyService.saveAdditionalCompanyInformation(addition);
		
		return SUCCESS;
	}
	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}
	
	public PagedElement<Company> getCompany() {
		return company;
	}

	public void setCompany(PagedElement<Company> company) {
		this.company = company;
	}

	public Company getC() {
		return c;
	}

	public void setC(Company c) {
		this.c = c;
	}

	public CompanyAdditional getCompanyAdditional() {
		return companyAdditional;
	}

	public void setCompanyAdditional(CompanyAdditional companyAdditional) {
		this.companyAdditional = companyAdditional;
	}

	
	
}