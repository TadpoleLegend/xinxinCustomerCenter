package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="ls_company_addition")
public class CompanyAdditional {
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	protected Company company;

	
	public Integer getId() {
	
		return id;
	}

	
	public void setId(Integer id) {
	
		this.id = id;
	}

	
	public Company getCompany() {
	
		return company;
	}

	
	public void setCompany(Company company) {
	
		this.company = company;
	}
	
	

}
