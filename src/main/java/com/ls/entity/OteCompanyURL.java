package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name="ls_ote_companyurl")
public class OteCompanyURL extends BaseCompanyURL {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3300892773754558793L;

	@Override
	public String toString() {

		return "OteCompanyURL [id=" + id + ", name=" + name + ", area=" + area + ", url=" + url + ", companyId=" + companyId + ", publishDate=" + publishDate + ", cityId=" + cityId + ", createDate=" + createDate + ", hasGet=" + hasGet + ", savedCompany=" + savedCompany + ", status=" + status + ", comments=" + comments + "]";
	}
	
	
	

}
