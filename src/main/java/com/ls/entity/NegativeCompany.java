package com.ls.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ls_negativecompany")
public class NegativeCompany implements Serializable {

	private static final long serialVersionUID = 7617585979254480302L;
	@Id
	@GeneratedValue
	protected Integer id;
	private String resourceType;
	private String url;
	private Integer cityId;
	private Integer sb_count;
	private String resourceId;
	private String grabDate;
	private String name;
	@Column(length = 3000)
	private String description;
	private String employeeCount;
	protected String address;
	protected String area;
	protected String emailSrc;
	protected String contactor;

	public String getContactor() {

		return contactor;
	}

	public void setContactor(String contactor) {

		this.contactor = contactor;
	}

	public String getAddress() {

		return address;
	}

	public void setAddress(String address) {

		this.address = address;
	}

	public String getArea() {

		return area;
	}

	public void setArea(String area) {

		this.area = area;
	}

	public String getEmailSrc() {

		return emailSrc;
	}

	public void setEmailSrc(String emailSrc) {

		this.emailSrc = emailSrc;
	}

	public String getEmployeeCount() {

		return employeeCount;
	}

	public void setEmployeeCount(String employeeCount) {

		this.employeeCount = employeeCount;
	}

	public String getName() {

		return name;
	}

	public void setName(String name) {

		this.name = name;
	}

	public String getDescription() {

		return description;
	}

	public void setDescription(String description) {

		this.description = description;
	}

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

	public String getResourceType() {

		return resourceType;
	}

	public void setResourceType(String resourceType) {

		this.resourceType = resourceType;
	}

	@Override
	public String toString() {

		return "NegativeCompany [id=" + id + ", resourceType=" + resourceType + ", url=" + url + ", cityId=" + cityId + ", sb_count=" + sb_count + ", resourceId=" + resourceId + ", grabDate=" + grabDate + ", name=" + name + ", description=" + description + ", employeeCount=" + employeeCount + ", address=" + address + ", area=" +
			area + ", emailSrc=" + emailSrc + ", contactor=" + contactor + "]";
	}

}
