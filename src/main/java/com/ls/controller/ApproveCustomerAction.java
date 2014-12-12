package com.ls.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ls.entity.ApplyingWillingCustomer;
import com.ls.enums.ApplyingCustomerStatus;
import com.ls.exception.ApplicationException;
import com.ls.repository.ApplyingWillingCustomerRepository;
import com.ls.service.ApplyingCustomerService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;
import com.ls.vo.WillCustomerCheckResult;

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
		for (ApplyingWillingCustomer applyingWillingCustomer : applyingWillingCustomers) {
			applyingWillingCustomer.setUser(null);
		}
		
		return SUCCESS;
	}
	
	public String approveCustomer() {
		ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
		try {
			String applyingCustomerJson = getParameter("applyingCustomerJson");
			
			ApplyingWillingCustomer applyingWillingCustomer = XinXinUtils.getJavaObjectFromJsonString(applyingCustomerJson, ApplyingWillingCustomer.class);
			
			if (applyingWillingCustomer.getStatus() != ApplyingCustomerStatus.APPLYING.getId()) {
				
				responseVo = ResponseVo.newFailMessage("这个申请已经处理过了");
				
			} else {
				responseVo = applyingCustomerService.approveCustomer(applyingWillingCustomer);
			}
			
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}
		
		setResponse(responseVo);
		return SUCCESS;
	}
	
	public String rejectCustomer() {
		ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
		try {
			String applyingCustomerJson = getParameter("applyingCustomerJson");
			
			ApplyingWillingCustomer applyingWillingCustomer = XinXinUtils.getJavaObjectFromJsonString(applyingCustomerJson, ApplyingWillingCustomer.class);
			if (applyingWillingCustomer.getStatus() != ApplyingCustomerStatus.APPLYING.getId()) {
				
				responseVo = ResponseVo.newFailMessage("这个申请已经处理过了");
				
			} else {
				responseVo = applyingCustomerService.rejectCustomer(applyingWillingCustomer);
			}
			
		} catch (Exception e) {
			
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}
		
		setResponse(responseVo);
		
		return SUCCESS;
	}
	
	public String checkApplyingCustomer() {
		
		try {
			ResponseVo responseVo = XinXinUtils.makeGeneralSuccessResponse();
			
			String applyingCustomerJson = getParameter("applyingCustomerJson");
			ApplyingWillingCustomer applyingWillingCustomer = XinXinUtils.getJavaObjectFromJsonString(applyingCustomerJson, ApplyingWillingCustomer.class);
			
			WillCustomerCheckResult willCustomerCheckResult = applyingCustomerService.checkApplyingCustomer(applyingWillingCustomer);
			responseVo.setObject(willCustomerCheckResult);
			
			setResponse(responseVo);
		} catch(ApplicationException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}	catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
		}
		
		return SUCCESS;
	}
	
	public List<ApplyingWillingCustomer> getApplyingWillingCustomers() {
	
		return applyingWillingCustomers;
	}
	
	public void setApplyingWillingCustomers(List<ApplyingWillingCustomer> applyingWillingCustomers) {
	
		this.applyingWillingCustomers = applyingWillingCustomers;
	}
}