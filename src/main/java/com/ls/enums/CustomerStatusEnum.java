package com.ls.enums;


/**
 * 
 * 	10	非意向客户		1
	20	意向客户		2
	30	公开课			4
	40	内训班			3
	50	精品班			5
	60	院长班			6
	70	连锁班			7

 * 
 *
 */
public enum CustomerStatusEnum {

	NO_WILLING_CUSTOMER(10, "非意向客户", 1),
	WILLING_CUSTOMER(20, "意向客户", 2),
	PUBLIC_COURSE_CUSTOMER(30, "公开课	", 4),
	INTERNAL_TRAINING_CUSTOMER(40, "内训班	", 3),
	ADVANCED_QUALITY_CUSTOMER(50, "精品班", 5),
	BOSS_CUSTOMER(60, "院长班", 6),
	BRANCHES_CUSTOMER(70, "连锁班	", 7);
	
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
