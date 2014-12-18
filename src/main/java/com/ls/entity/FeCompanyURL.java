package com.ls.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ls_fe_companyurl")
public class FeCompanyURL implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2573469483689685576L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String area;
	private String url;
	private String companyId;
	private String publishDate;
	private Integer cityId;
	private Date createDate;
	private Boolean hasGet;
	private String savedCompany;
	private String status;
	
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
	@Override
	public String toString() {
		return "FeCompanyURL [id=" + id + ", name=" + name + ", area=" + area + ", url=" + url + ", companyId=" + companyId + ", publishDate=" + publishDate + ", cityId=" + cityId + ", hasGet=" + hasGet + "]";
	}

	public String getSavedCompany() {
		return savedCompany;
	}

	public void setSavedCompany(String savedCompany) {
		this.savedCompany = savedCompany;
	}
	
	
}
