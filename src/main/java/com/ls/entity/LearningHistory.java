package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "ls_learning_history")
public class LearningHistory {
	
	@Id
	@GeneratedValue
	protected Integer id;
	
	protected String startDate;
	protected String endDate;
	protected Integer middleLevelManagerCount;
	protected Integer highLevelManagerCount;
	protected String comments;
	
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

	
	public String getStartDate() {
	
		return startDate;
	}

	
	public void setStartDate(String startDate) {
	
		this.startDate = startDate;
	}

	
	public String getEndDate() {
	
		return endDate;
	}

	
	public void setEndDate(String endDate) {
	
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
	
}
