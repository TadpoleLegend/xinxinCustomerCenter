package com.ls.enums;

public enum CustomerStatusEnum {

	NO_WILLING_CUSTOMER(10, "非意向客户", 1), APPLYING_WILLING_CUSTOMER(11, "申请成为意向客户", 2), 
	WILLING_CUSTOMER(20, "意向客户", 3), INTERNAL_TRAINING_CUSTOMER(30, "内训班	", 4), PUBLIC_COURSE_CUSTOMER(40, "公开课	", 5), 
	ADVANCED_QUALITY_CUSTOMER(50, "精品班", 6), BOSS_CUSTOMER(60, "院长班", 7), BRANCHES_CUSTOMER(70, "连锁系统", 8);

	private Integer id;
	private String name;
	private Integer orderNumber;

	private CustomerStatusEnum(Integer id, String name, Integer orderNumber) {

		this.id = id;
		this.name = name;
		this.orderNumber = orderNumber;
	}

	public static String getName(String id) {

		for (CustomerStatusEnum c : CustomerStatusEnum.values()) {
			if (c.getId().equals(id)) {
				return c.name;
			}
		}
		return null;
	}

	public static CustomerStatusEnum getCustomerStatusById(Integer id) {
		for (CustomerStatusEnum c : CustomerStatusEnum.values()) {
			if (c.getId().equals(id)) {
				return c;
			}
		}
		return null;
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
