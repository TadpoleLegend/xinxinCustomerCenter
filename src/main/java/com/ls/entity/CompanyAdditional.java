package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_company_addition")
public class CompanyAdditional {

	@Id
	@GeneratedValue
	protected Integer id;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "company_id")
	protected Company company;

	protected String bossName;
	protected String bossMobile;
	protected String bossTelephone;
	protected String bossQQorWechat;
	protected Integer branchCount;
	protected Integer branchManagerCount;
	protected Integer branchConsultantCount;
	protected Integer bedCount;
	protected Float acreage;
	protected Float lastYearIncome;
	protected Float thisYearMonthlyIncome;
	protected Integer bossAge;
	protected String companyLevel;
	protected String firstKidBirthday;
	protected String secondKidBirthday;
	protected String thirdKidBirthday;
	protected String bossBirthday;
	protected String companyAnniversary;
	protected String merryAnniversary;
	protected String loverBirthday;
	protected String comments;
	
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
	
	public String getComments() {
	
		return comments;
	}
	
	public void setComments(String comments) {
	
		this.comments = comments;
	}

	public String getBossName() {
		return bossName;
	}

	public void setBossName(String bossName) {
		this.bossName = bossName;
	}

	public String getBossMobile() {
		return bossMobile;
	}

	public void setBossMobile(String bossMobile) {
		this.bossMobile = bossMobile;
	}

	public String getBossTelephone() {
		return bossTelephone;
	}

	public void setBossTelephone(String bossTelephone) {
		this.bossTelephone = bossTelephone;
	}

	public String getBossQQorWechat() {
		return bossQQorWechat;
	}

	public void setBossQQorWechat(String bossQQorWechat) {
		this.bossQQorWechat = bossQQorWechat;
	}

	public Integer getBranchCount() {
		return branchCount;
	}

	public void setBranchCount(Integer branchCount) {
		this.branchCount = branchCount;
	}

	public Integer getBranchManagerCount() {
		return branchManagerCount;
	}

	public void setBranchManagerCount(Integer branchManagerCount) {
		this.branchManagerCount = branchManagerCount;
	}

	public Integer getBranchConsultantCount() {
		return branchConsultantCount;
	}

	public void setBranchConsultantCount(Integer branchConsultantCount) {
		this.branchConsultantCount = branchConsultantCount;
	}

	public Integer getBedCount() {
		return bedCount;
	}

	public void setBedCount(Integer bedCount) {
		this.bedCount = bedCount;
	}


	public Float getAcreage() {
		return acreage;
	}

	public void setAcreage(Float acreage) {
		this.acreage = acreage;
	}

	public Float getLastYearIncome() {
		return lastYearIncome;
	}

	public void setLastYearIncome(Float lastYearIncome) {
		this.lastYearIncome = lastYearIncome;
	}

	public Float getThisYearMonthlyIncome() {
		return thisYearMonthlyIncome;
	}

	public void setThisYearMonthlyIncome(Float thisYearMonthlyIncome) {
		this.thisYearMonthlyIncome = thisYearMonthlyIncome;
	}

	public Integer getBossAge() {
		return bossAge;
	}

	public void setBossAge(Integer bossAge) {
		this.bossAge = bossAge;
	}
	
	public String getCompanyLevel() {
	
		return companyLevel;
	}
	
	public void setCompanyLevel(String companyLevel) {
	
		this.companyLevel = companyLevel;
	}

	
	public String getFirstKidBirthday() {
	
		return firstKidBirthday;
	}

	
	public void setFirstKidBirthday(String firstKidBirthday) {
	
		this.firstKidBirthday = firstKidBirthday;
	}

	
	public String getSecondKidBirthday() {
	
		return secondKidBirthday;
	}

	
	public void setSecondKidBirthday(String secondKidBirthday) {
	
		this.secondKidBirthday = secondKidBirthday;
	}

	
	public String getThirdKidBirthday() {
	
		return thirdKidBirthday;
	}

	
	public void setThirdKidBirthday(String thirdKidBirthday) {
	
		this.thirdKidBirthday = thirdKidBirthday;
	}

	
	
	public String getBossBirthday() {
	
		return bossBirthday;
	}

	
	public void setBossBirthday(String bossBirthday) {
	
		this.bossBirthday = bossBirthday;
	}

	
	public String getCompanyAnniversary() {
	
		return companyAnniversary;
	}

	
	public void setCompanyAnniversary(String companyAnniversary) {
	
		this.companyAnniversary = companyAnniversary;
	}

	
	public String getMerryAnniversary() {
	
		return merryAnniversary;
	}

	
	public void setMerryAnniversary(String merryAnniversary) {
	
		this.merryAnniversary = merryAnniversary;
	}

	
	public String getLoverBirthday() {
	
		return loverBirthday;
	}

	
	public void setLoverBirthday(String loverBirthday) {
	
		this.loverBirthday = loverBirthday;
	}

	
}
