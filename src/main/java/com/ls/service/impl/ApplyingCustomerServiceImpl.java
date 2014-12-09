package com.ls.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.entity.Company;
import com.ls.enums.ApplyingCustomerStatus;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.ApplyingWillingCustomerRepository;
import com.ls.repository.CompanyRepository;
import com.ls.service.ApplyingCustomerService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Service("applyingCustomerService")
public class ApplyingCustomerServiceImpl implements ApplyingCustomerService {

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ApplyingWillingCustomerRepository applyingWillingCustomerRepository;
	
	@Secured("ROLE_WC_APPROVER")
	@Transactional
	public ResponseVo approveCustomer(ApplyingWillingCustomer applyingWillingCustomer) {
		
		ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
		updateApplyingCustomer(applyingWillingCustomer, ApplyingCustomerStatus.APPROVED.getId());
		
		return responseVo;
	}
	
	@Secured("ROLE_WC_APPROVER")
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
		
		companyRepository.save(company);
		applyingWillingCustomerRepository.save(freshApplyingWillingCustomer);
	}

	public ResponseVo checkApplyingCustomer(ApplyingWillingCustomer applyingWillingCustomer) {
		ResponseVo responseVo = ResponseVo.newSuccessMessage("建议：可以申请通过");
		
		return responseVo;
	}

}
