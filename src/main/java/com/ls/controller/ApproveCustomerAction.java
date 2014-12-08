package com.ls.controller;

import org.springframework.stereotype.Component;

@Component("approveCustomerAction")
public class ApproveCustomerAction extends BaseAction {

	private static final long serialVersionUID = -9147384986853286025L;

	public String approveCustomerToWillList() {
		
		return SUCCESS;
	}


}