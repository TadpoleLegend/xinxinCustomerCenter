package com.ls.service;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.exception.ApplicationException;
import com.ls.vo.ResponseVo;
import com.ls.vo.WillCustomerCheckResult;


public interface ApplyingCustomerService {

	ResponseVo approveCustomer(ApplyingWillingCustomer applyingWillingCustomer);
	
	ResponseVo rejectCustomer(ApplyingWillingCustomer applyingWillingCustomer);
	
	WillCustomerCheckResult checkApplyingCustomer(ApplyingWillingCustomer applyingWillingCustomer) throws ApplicationException;
}
