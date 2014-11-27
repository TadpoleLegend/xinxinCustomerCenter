package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ls_negativecompany")
public class NegativeCompany implements Serializable {

	private static final long serialVersionUID = 7617585979254480302L;
	@Id
	@GeneratedValue
	protected Integer id;
	private String sourceType;
	private String url;
	private Integer cityId;
	private Integer sb_count;
	private String resourceId;
	private String grabDate;
	
	
	
	public String getGrabDate() {
		return grabDate;
	}
	public void setGrabDate(String grabDate) {
		this.grabDate = grabDate;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public Integer getSb_count() {
		return sb_count;
	}
	public void setSb_count(Integer sb_count) {
		this.sb_count = sb_count;
	}
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	

}
