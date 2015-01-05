package com.ls.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;

@Entity
@Table(name = "ls_learning_history")
public class LearningHistory {
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	protected Date startDate;
	protected Date endDate;
	protected Integer middleLevelManagerCount;
	protected Integer highLevelManagerCount;
	protected String comments;
	protected Date signUpDate;
	protected Date firstPayDate;
	protected Double firstPayAmount;
	protected Double totalAmount;
	protected Double debtAmount;
	protected Date payDebtDate;
	protected String currentMonthPolicy;
	
	protected String status;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="phase_id")
	protected Phase phase;

	@ManyToOne()
	@JoinColumn(name = "company_id")
	protected Company company;
	
	public String getComments() {
	
		return comments;
	}
	
	public void setComments(String comments) {
	
		this.comments = comments;
	}

	public Integer getId() {
	
		return id;
	}
	
	public void setId(Integer id) {
	
		this.id = id;
	}
	
	@JSON(format="yyyy-MM-dd") 
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	@JSON(format="yyyy-MM-dd") 
	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public Integer getMiddleLevelManagerCount() {
	
		return middleLevelManagerCount;
	}
	
	public void setMiddleLevelManagerCount(Integer middleLevelManagerCount) {
	
		this.middleLevelManagerCount = middleLevelManagerCount;
	}
	
	public Integer getHighLevelManagerCount() {
	
		return highLevelManagerCount;
	}

	public void setHighLevelManagerCount(Integer highLevelManagerCount) {
	
		this.highLevelManagerCount = highLevelManagerCount;
	}
	
	public Phase getPhase() {
	
		return phase;
	}
	
	public void setPhase(Phase phase) {
	
		this.phase = phase;
	}
	
	public Company getCompany() {
	
		return company;
	}

	public void setCompany(Company company) {
	
		this.company = company;
	}
	@JSON(format="yyyy-MM-dd") 
	public Date getSignUpDate() {
		return signUpDate;
	}

	public void setSignUpDate(Date signUpDate) {
		this.signUpDate = signUpDate;
	}
	@JSON(format="yyyy-MM-dd") 
	public Date getFirstPayDate() {
		return firstPayDate;
	}

	public void setFirstPayDate(Date firstPayDate) {
		this.firstPayDate = firstPayDate;
	}

	public Double getFirstPayAmount() {
		return firstPayAmount;
	}

	public void setFirstPayAmount(Double firstPayAmount) {
		this.firstPayAmount = firstPayAmount;
	}

	public Double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Double getDebtAmount() {
		return debtAmount;
	}

	public void setDebtAmount(Double debtAmount) {
		this.debtAmount = debtAmount;
	}
	@JSON(format="yyyy-MM-dd") 
	public Date getPayDebtDate() {
		return payDebtDate;
	}

	public void setPayDebtDate(Date payDebtDate) {
		this.payDebtDate = payDebtDate;
	}

	public String getCurrentMonthPolicy() {
		return currentMonthPolicy;
	}

	public void setCurrentMonthPolicy(String currentMonthPolicy) {
		this.currentMonthPolicy = currentMonthPolicy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
