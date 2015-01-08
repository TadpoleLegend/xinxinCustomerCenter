package com.ls.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.ls.entity.ApplyingWillingCustomer;
import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.enums.ApplyingCustomerStatus;
import com.ls.enums.CustomerStatusEnum;
import com.ls.exception.ApplicationException;
import com.ls.repository.ApplyingWillingCustomerRepository;
import com.ls.repository.CompanyAdditionalRepository;
import com.ls.repository.CompanyRepository;
import com.ls.service.ApplyingCustomerService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;
import com.ls.vo.WillCustomerCheckResult;

@Service("applyingCustomerService")
public class ApplyingCustomerServiceImpl implements ApplyingCustomerService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ApplyingWillingCustomerRepository applyingWillingCustomerRepository;
	
	@Autowired
	private CompanyAdditionalRepository companyAdditionalRepository;
	
	@Secured({"ROLE_WC_APPROVER", "ROLE_ADMIN"})
	@Transactional
	public ResponseVo approveCustomer(ApplyingWillingCustomer applyingWillingCustomer) {
		
		ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
		updateApplyingCustomer(applyingWillingCustomer, ApplyingCustomerStatus.APPROVED.getId());
		
		return responseVo;
	}
	
	@Secured({"ROLE_WC_APPROVER", "ROLE_ADMIN"})
	@Transactional
	public ResponseVo rejectCustomer(ApplyingWillingCustomer applyingWillingCustomer) {
		
		ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
		updateApplyingCustomer(applyingWillingCustomer, ApplyingCustomerStatus.REJECTED.getId());
		
		return responseVo;
	}
	
	private void updateApplyingCustomer(ApplyingWillingCustomer applyingWillingCustomer, Integer statusId) { 
		
		Integer companyId =  applyingWillingCustomer.getCompanyId();
		Company company = companyRepository.findOne(companyId);
		
		if (statusId == ApplyingCustomerStatus.APPROVED.getId()) {
			
			company.setStatus(CustomerStatusEnum.WILLING_CUSTOMER.getId());
			
		} else if (statusId == ApplyingCustomerStatus.REJECTED.getId()) {
			
			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
		}
		
		company.setUpdateDate(XinXinUtils.getNow());
		
		
		ApplyingWillingCustomer freshApplyingWillingCustomer = applyingWillingCustomerRepository.findOne(applyingWillingCustomer.getId());
		freshApplyingWillingCustomer.setStatus(statusId);
		freshApplyingWillingCustomer.setUpdateDate(XinXinUtils.getNow());
		
		companyRepository.saveAndFlush(company);
		applyingWillingCustomerRepository.saveAndFlush(freshApplyingWillingCustomer);
	}

	public WillCustomerCheckResult checkApplyingCustomer(ApplyingWillingCustomer applyingWillingCustomer) throws ApplicationException {
		
		if (applyingWillingCustomer.getCompanyAdditionalId() == null) {
			throw new ApplicationException("院长信息缺失！");
		}
		CompanyAdditional companyAdditional = companyAdditionalRepository.findOne(applyingWillingCustomer.getCompanyAdditionalId());
		Company company = companyRepository.findOne(applyingWillingCustomer.getCompanyId());
		
		WillCustomerCheckResult willCustomerCheckResult = new WillCustomerCheckResult();
		//name duplication
		Joiner commaJoiner = Joiner.on(",").skipNulls();
		List<CompanyAdditional> listWithSameBossName = companyAdditionalRepository.findByBossNameAndIdNot(applyingWillingCustomer.getBossName(), applyingWillingCustomer.getCompanyAdditionalId());
		if (null == listWithSameBossName || listWithSameBossName.isEmpty()) {
			willCustomerCheckResult.setBossNameResult("未发现同名信息。" );
		} else {
			willCustomerCheckResult.setBossNameResult(commaJoiner.join(geCompanyIdsInAddtionInfoList(listWithSameBossName)));
		}
		//mobile phone number duplicaton
		List<CompanyAdditional> listWithSameBossMobile = companyAdditionalRepository.findByBossMobileAndIdNot(companyAdditional.getBossMobile(), applyingWillingCustomer.getCompanyAdditionalId());
		if (null == listWithSameBossMobile || listWithSameBossMobile.isEmpty()) {
			willCustomerCheckResult.setBossMobileResult("未发现更多的相同手机号的院长信息。" );
		} else {
			willCustomerCheckResult.setBossMobileResult(commaJoiner.join(geCompanyIdsInAddtionInfoList(listWithSameBossMobile)));
		}
		
		List<Company> listWithSameCompanyName = companyRepository.findByNameAndNameNot(company.getName(), company.getName());
		if (null == listWithSameCompanyName || listWithSameCompanyName.isEmpty()) {
			willCustomerCheckResult.setCompanyNameResult("未发现更多的\"" + company.getName() + "\"的意向顾客申请记录。" );
		} else {
			willCustomerCheckResult.setCompanyNameResult(commaJoiner.join(geCompanyIdList(listWithSameCompanyName)));
		}
		
		return willCustomerCheckResult;
	}

	private List<Integer> geCompanyIdsInAddtionInfoList(List<CompanyAdditional> addtionalInformationList) {
		
		List<Integer> idList = Lists.newArrayList();
		for (CompanyAdditional companyAdditional : addtionalInformationList) {
			idList.add(companyAdditional.getCompany().getId());
		}
		
		return idList;
	}
	
	private List<Integer> geCompanyIdList(List<Company> companyList) {
		
		List<Integer> idList = Lists.newArrayList();
		for (Company companyAdditional : companyList) {
			idList.add(companyAdditional.getId());
		}
		
		return idList;
	}
}
