package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.repository.ApplyingWillingCustomerRepository;
import com.ls.service.ApplyingCustomerService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Component("approveCustomerAction")
public class ApproveCustomerAction extends BaseAction {

	private static final long serialVersionUID = -9147384986853286025L;
	
	@Autowired
	private ApplyingWillingCustomerRepository applyingWillingCustomerRepository;
	
	@Resource(name="applyingCustomerService")
	private ApplyingCustomerService applyingCustomerService;
	
	private List<ApplyingWillingCustomer> applyingWillingCustomers;
	
	public String approveCustomerToWillList() {
		
		return SUCCESS;
	}

	public String getAllApplyingList() {
		
		applyingWillingCustomers = applyingWillingCustomerRepository.findAll();
		
		return SUCCESS;
	}
	
	public String approveCustomer() {
		ResponseVo responseVo = null;
		try {
			String applyingCustomerJson = getParameter("applyingCustomerJson");
			
			ApplyingWillingCustomer applyingWillingCustomer = XinXinUtils.getJavaObjectFromJsonString(applyingCustomerJson, ApplyingWillingCustomer.class);
			
			responseVo = applyingCustomerService.approveCustomer(applyingWillingCustomer);
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}
		
		setResponse(responseVo);
		return SUCCESS;
	}
	
	public String rejectCustomer() {
		ResponseVo responseVo = null;
		try {
			String applyingCustomerJson = getParameter("applyingCustomerJson");
			
			ApplyingWillingCustomer applyingWillingCustomer = XinXinUtils.getJavaObjectFromJsonString(applyingCustomerJson, ApplyingWillingCustomer.class);
			
			responseVo = applyingCustomerService.rejectCustomer(applyingWillingCustomer);
		} catch (Exception e) {
			
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}
		
		setResponse(responseVo);
		
		return SUCCESS;
	}
	
	public String checkApplyingCustomer() {
		
		return SUCCESS;
	}
	
	public List<ApplyingWillingCustomer> getApplyingWillingCustomers() {
	
		return applyingWillingCustomers;
	}
	
	public void setApplyingWillingCustomers(List<ApplyingWillingCustomer> applyingWillingCustomers) {
	
		this.applyingWillingCustomers = applyingWillingCustomers;
	}
}