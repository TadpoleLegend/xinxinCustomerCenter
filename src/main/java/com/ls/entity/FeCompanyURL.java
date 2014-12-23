package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "ls_fe_companyurl")
public class FeCompanyURL extends BaseCompanyURL {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {
		return "FeCompanyURL [id=" + id + ", name=" + name + ", area=" + area + ", url=" + url + ", companyId=" + companyId + ", publishDate=" + publishDate + ", cityId=" + cityId + ", hasGet=" + hasGet + "]";
	}

	
}
