package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="ls_ote_companyurl")
public class OteCompanyURL implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3300892773754558793L;
	@Id
	@GeneratedValue
	private Integer id;
	private String name;
	private String area;
	private String url;
	private String companyId;
	private String publishDate;
	private Integer cityId;
	private int hasGet;
	
	public int getHasGet() {
		return hasGet;
	}
	public void setHasGet(int hasGet) {
		this.hasGet = hasGet;
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
	

}
