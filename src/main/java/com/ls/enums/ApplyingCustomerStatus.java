package com.ls.enums;


public enum ApplyingCustomerStatus {
	APPLYING(10, "正在申请", 1),
	APPROVED(20, "已批准", 2),
	REJECTED(30, "被拒绝", 3);
	
	private Integer id;
	private String name;
	private Integer orderNumber;
	
	private ApplyingCustomerStatus(Integer id, String name, Integer orderNumber) {

		this.id = id;
		this.name = name;
		this.orderNumber = orderNumber;
	}

	
	public Integer getId() {
	
		return id;
	}

	
	public void setId(Integer id) {
	
		this.id = id;
	}

	
	public String getName() {
	
		return name;
	}

	
	public void setName(String name) {
	
		this.name = name;
	}

	
	public Integer getOrderNumber() {
	
		return orderNumber;
	}

	
	public void setOrderNumber(Integer orderNumber) {
	
		this.orderNumber = orderNumber;
	}

	
}
