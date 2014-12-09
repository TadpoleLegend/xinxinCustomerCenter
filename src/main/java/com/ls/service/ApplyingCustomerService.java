package com.ls.service;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.vo.ResponseVo;


public interface ApplyingCustomerService {

	ResponseVo approveCustomer(ApplyingWillingCustomer applyingWillingCustomer);
	
	ResponseVo rejectCustomer(ApplyingWillingCustomer applyingWillingCustomer);
}
