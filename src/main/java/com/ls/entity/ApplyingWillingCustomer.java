package com.ls.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="ls_applying_willing_customer")
public class ApplyingWillingCustomer implements Serializable {

	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	protected Integer companyId;
	
	protected Integer companyAdditionalId;
	
	protected String companyName;
	
	protected String bossName;
	
	protected Integer status;
	
	protected Date applyingDate;
	
	protected Date updateDate;
	
	protected String applyerName;
	
	@ManyToOne()
	@JoinColumn(name="user_id")
	protected User user;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCompanyName() {
	
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
	
		this.companyName = companyName;
	}
	
	public String getBossName() {
	
		return bossName;
	}
	
	public void setBossName(String bossName) {
	
		this.bossName = bossName;
	}
	
	public Integer getStatus() {
	
		return status;
	}
	
	public void setStatus(Integer status) {
	
		this.status = status;
	}
	
	public User getUser() {
	
		return user;
	}
	
	public void setUser(User user) {
	
		this.user = user;
	}
	
	public Integer getCompanyId() {
	
		return companyId;
	}
	
	public void setCompanyId(Integer companyId) {
	
		this.companyId = companyId;
	}
	
	public Date getApplyingDate() {
	
		return applyingDate;
	}
	
	public void setApplyingDate(Date applyingDate) {
	
		this.applyingDate = applyingDate;
	}
	
	public Date getUpdateDate() {
	
		return updateDate;
	}
	
	public void setUpdateDate(Date updateDate) {
	
		this.updateDate = updateDate;
	}
	
	public String getApplyerName() {
	
		return applyerName;
	}
	
	public void setApplyerName(String applyerName) {
	
		this.applyerName = applyerName;
	}
	
	public Integer getCompanyAdditionalId() {
	
		return companyAdditionalId;
	}
	
	public void setCompanyAdditionalId(Integer companyAdditionalId) {
	
		this.companyAdditionalId = companyAdditionalId;
	}
	
}
