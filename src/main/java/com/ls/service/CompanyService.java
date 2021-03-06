package com.ls.service;

import java.text.ParseException;
import java.util.List;

import org.springframework.data.domain.Page;

import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.entity.PhoneCallHistory;
import com.ls.entity.Problem;
import com.ls.vo.CompanySearchVo;
import com.ls.vo.ResponseVo;

public interface CompanyService {
	List<Company> findCompany(String name);

	List<Company> findAllCompanies();

	Page<Company> getCompanyInPage(Integer index);

	Page<Company> getCompanyInPage(CompanySearchVo companySearchVo);

	Problem saveProblem(Problem problem);
	
	CompanyAdditional saveAdditionalCompanyInformation(CompanyAdditional addtion);
	
	CompanyAdditional findCompanyAddtionalInformationByCompanyId(Integer companyId);
	
	void checkOrUncheckProblem(String companyJson, String problemJson, String checkFlag);
	
	ResponseVo changeCompanyStatus(String companyId, String statusId);
	
	PhoneCallHistory savePhoneCallHistory(String jsonPhonecall, String companyId) throws ParseException;
}
