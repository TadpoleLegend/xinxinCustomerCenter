package com.ls.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.ls.constants.XinXinConstants;
@Entity
@Table(name="ls_phone_call_history")
public class PhoneCallHistory {
	
	@Id
	@GeneratedValue
	protected Integer id;
	protected String description;
	protected String createDate;
	protected String nextDate;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id")
	protected User user;
	
	@ManyToOne
	@JoinColumn(name = "company_id")
	protected Company company;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getNextDate() {
		return nextDate;
	}

	public void setNextDate(String nextDate) {
		this.nextDate = nextDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}
	
	public static PhoneCallHistory createNew() {
		PhoneCallHistory phoneCallHistory = new PhoneCallHistory();
		phoneCallHistory.setCreateDate(XinXinConstants.FULL_DATE_FORMATTER.format(new Date()));
		
		return phoneCallHistory;
	}
}
