package com.ls.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.ls.constants.XinXinConstants;
import com.ls.enums.CustomerStatusEnum;
import com.ls.util.XinXinUtils;

@Entity
@Table(name = "ls_company")
public class Company implements Serializable {

	private static final long serialVersionUID = -6868385772290273229L;

	@Id
	@GeneratedValue
	protected Integer id;
	protected String oTEresourceId;
	protected String fEresourceId;
	protected String ganjiresourceId;
	protected String resouceType;
	protected String name;
	protected String contactor;
	protected String email;
	protected String emailSrc;
	protected String phone;
	protected String phoneSrc;
	protected String mobilePhone;
	protected String mobilePhoneSrc;
	protected Boolean isTracked;
	protected String address;
	protected Integer star;
	protected String area;
	protected String fEurl; //five eight(fe) 58 URL 
	protected String oteUrl; // one three eight( ote) 138 URL 
	protected String ganjiUrl;
	protected Integer cityId;
	protected Integer provinceId;
	protected String employeeCount;
	@Column(length=3000)
	protected String description;
	protected String grabDate;
	protected Boolean active;
	protected Integer status;
	protected String type;
	protected Integer ownerUserId; //manually input company belongs to the user who inputs it to the system.
	protected Date updateDate;
	protected Date createDate;
	
	@ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "ls_company_problem", joinColumns = @JoinColumn(name = "company_id"), inverseJoinColumns = @JoinColumn(name = "problem_id"))
	protected List<Problem> problems;
	
	@Transient
	protected String publishDate;
	
	@OneToOne(mappedBy="company")
	protected CompanyAdditional addtion;
	
	@OneToMany(mappedBy="company")
	protected List<PhoneCallHistory> phoneCallHistories;
	
	@OneToMany(mappedBy="company")
	protected List<LearningHistory> learningHistories;
	
	public List<LearningHistory> getLearningHistories() {
	
		return learningHistories;
	}
	
	public void setLearningHistories(List<LearningHistory> learningHistories) {
	
		this.learningHistories = learningHistories;
	}

	public String getResouceType() {
		return resouceType;
	}

	public void setResouceType(String resouceType) {
		this.resouceType = resouceType;
	}

	public String getfEurl() {
		return fEurl;
	}

	public void setfEurl(String fEurl) {
		this.fEurl = fEurl;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getContactor() {
		return contactor;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailSrc() {
		return emailSrc;
	}

	public void setEmailSrc(String emailSrc) {
		this.emailSrc = emailSrc;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhoneSrc() {
		return phoneSrc;
	}

	public void setPhoneSrc(String phoneSrc) {
		this.phoneSrc = phoneSrc;
	}

	public Boolean getIsTracked() {
		return isTracked;
	}

	public void setIsTracked(Boolean isTracked) {
		this.isTracked = isTracked;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<Problem> getProblems() {
		return problems;
	}

	public void setProblems(List<Problem> problems) {
		this.problems = problems;
	}
	
	public String getPublishDate() {
		return publishDate;
	}

	public void setPublishDate(String publishDate) {
		this.publishDate = publishDate;
	}

	
	public Integer getStar() {
		return star;
	}

	public void setStar(Integer star) {
		this.star = star;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String distinct) {
		this.area = distinct;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

	public Integer getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(Integer provinceId) {
		this.provinceId = provinceId;
	}

	public String getEmployeeCount() {
		return employeeCount;
	}

	public void setEmployeeCount(String employeeCount) {
		this.employeeCount = employeeCount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getMobilePhone() {
		return mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	public String getMobilePhoneSrc() {
		return mobilePhoneSrc;
	}

	public void setMobilePhoneSrc(String mobilePhoneSrc) {
		this.mobilePhoneSrc = mobilePhoneSrc;
	}

	public String getGrabDate() {
		return grabDate;
	}

	public void setGrabDate(String grabDate) {
		this.grabDate = grabDate;
	}


	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public CompanyAdditional getAddtion() {
		return addtion;
	}

	public void setAddtion(CompanyAdditional addtion) {
		this.addtion = addtion;
	}

	public Integer getStatus() {
	
		return status;
	}

	public void setStatus(Integer status) {
	
		this.status = status;
	}

	public Integer getOwnerUserId() {
	
		return ownerUserId;
	}
	
	public void setOwnerUserId(Integer ownerUserId) {
	
		this.ownerUserId = ownerUserId;
	}

	public String getoTEresourceId() {
		return oTEresourceId;
	}

	public void setoTEresourceId(String oTEresourceId) {
		this.oTEresourceId = oTEresourceId;
	}

	public String getfEresourceId() {
		return fEresourceId;
	}

	public void setfEresourceId(String fEresourceId) {
		this.fEresourceId = fEresourceId;
	}


	public String getGanjiresourceId() {
		return ganjiresourceId;
	}

	public void setGanjiresourceId(String ganjiresourceId) {
		this.ganjiresourceId = ganjiresourceId;
	}

	public String getOteUrl() {
		return oteUrl;
	}

	public void setOteUrl(String oteUrl) {
		this.oteUrl = oteUrl;
	}

	public String getGanjiUrl() {
		return ganjiUrl;
	}

	public void setGanjiUrl(String ganjiUrl) {
		this.ganjiUrl = ganjiUrl;
	}

	public List<PhoneCallHistory> getPhoneCallHistories() {
		return phoneCallHistories;
	}

	public void setPhoneCallHistories(List<PhoneCallHistory> phoneCallHistories) {
		this.phoneCallHistories = phoneCallHistories;
	}

	public String getType() {
	
		return type;
	}
	
	public void setType(String type) {
	
		this.type = type;
	}
	
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	public Date getCreateDate() {
	
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
	
		this.createDate = createDate;
	}

	public static Company create() {
		
		Company company = new Company();
		
		company.setActive(true);
		company.setIsTracked(false);
		company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
		company.setStar(0);
		company.setUpdateDate(XinXinUtils.getNow());
		company.setCreateDate(XinXinUtils.getNow());
		
		return company;
	}

	@Override
	public String toString() {

		return "Company [id=" + id + ", oTEresourceId=" + oTEresourceId + ", fEresourceId=" + fEresourceId + ", ganjiresourceId=" + ganjiresourceId + ", resouceType=" + resouceType + ", name=" + name + ", contactor=" + contactor + ", email=" + email + ", emailSrc=" + emailSrc + ", phone=" + phone + ", phoneSrc=" + phoneSrc +
			", mobilePhone=" + mobilePhone + ", mobilePhoneSrc=" + mobilePhoneSrc + ", isTracked=" + isTracked + ", address=" + address + ", star=" + star + ", area=" + area + ", fEurl=" + fEurl + ", oteUrl=" + oteUrl + ", ganjiUrl=" + ganjiUrl + ", cityId=" + cityId + ", provinceId=" + provinceId + ", employeeCount=" +
			employeeCount + ", description=" + description + ", grabDate=" + grabDate + ", active=" + active + ", status=" + status + ", type=" + type + ", ownerUserId=" + ownerUserId + ", updateDate=" + updateDate + ", createDate=" + createDate + ", problems=" + problems + ", publishDate=" + publishDate + ", addtion=" + addtion +
			", phoneCallHistories=" + phoneCallHistories + ", learningHistories=" + learningHistories + "]";
	}
	
	
}
