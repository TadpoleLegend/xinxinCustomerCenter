package com.ls.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseCompanyURL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2573469483689685576L;
	@Id
	@GeneratedValue
	protected Integer id;
	protected String name;
	protected String area;
	protected String url;
	protected String companyId;
	protected String publishDate;
	protected Integer cityId;
	protected Date createDate;
	protected Boolean hasGet;
	protected String savedCompany;
	protected String status;
	protected String comments;

	public String getComments() {

		return comments;
	}

	public void setComments(String comments) {

		this.comments = comments;

	}

	public String getStatus() {

		return status;
	}

	public void setStatus(String status) {

		this.status = status;
	}

	public Boolean getHasGet() {

		return hasGet;
	}

	public void setHasGet(Boolean hasGet) {

		this.hasGet = hasGet;
	}

	public Date getCreateDate() {

		return createDate;
	}

	public void setCreateDate(Date createDate) {

		this.createDate = createDate;
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

	public String getArea() {

		return area;
	}

	public void setArea(String area) {

		this.area = area;
	}

	public String getUrl() {

		return url;
	}

	public void setUrl(String url) {

		this.url = url;
	}

	public String getCompanyId() {

		return companyId;
	}

	public void setCompanyId(String companyId) {

		this.companyId = companyId;
	}

	public String getPublishDate() {

		return publishDate;
	}

	public void setPublishDate(String publishDate) {

		this.publishDate = publishDate;
	}

	public Integer getCityId() {

		return cityId;
	}

	public void setCityId(Integer cityId) {

		this.cityId = cityId;
	}

	public String getSavedCompany() {

		return savedCompany;
	}

	public void setSavedCompany(String savedCompany) {

		this.savedCompany = savedCompany;
	}

}
