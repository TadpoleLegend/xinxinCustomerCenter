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

import org.apache.struts2.json.annotations.JSON;

import com.ls.constants.XinXinConstants;
import com.ls.util.XinXinUtils;


@Entity
@Table(name = "ls_phone_call_history")
public class PhoneCallHistory {

	@Id
	@GeneratedValue
	protected Integer id;

	protected String description;

	protected Date createDate;
	
	protected Date nextDate;

	protected String creatorName;
	
	@ManyToOne
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

	@JSON(format="yyyy-MM-dd HH:mm") 
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
//	//@JSON(format="yyyy-MM-dd") 
//	public String getNextDate() {
//		return XinXinConstants.SIMPLE_DATE_FORMATTER.format(nextDate);
//	}
//
//	public void setNextDate(String nextDate) {
//		this.nextDate = XinXinUtils.getStandardDate(nextDate);
//	}

	
	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getCreatorName() {
	
		return creatorName;
	}

	
	public void setCreatorName(String creatorName) {
	
		this.creatorName = creatorName;
	}

	@JSON(format="yyyy-MM-dd") 
	public Date getNextDate() {
	
		return nextDate;
	}

	
	public void setNextDate(Date nextDate) {
	
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
		phoneCallHistory.setCreateDate(new Date());

		return phoneCallHistory;
	}
}
