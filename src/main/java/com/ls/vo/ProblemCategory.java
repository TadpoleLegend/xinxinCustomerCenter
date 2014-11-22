package com.ls.vo;

public enum ProblemCategory {
	
	EmployeeProblem(1, "EMPLOYEE", "员工问题"),
	ConsumerProblem(2, "EMPLOYEE", "顾客问题"),
	OtherProblem(3, "EMPLOYEE", "其他问题");
	
	private Integer id;
	private String code;
	private String description;
	private ProblemCategory(Integer id, String code, String description) {
		this.id = id;
		this.code = code;
		this.description = description;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
