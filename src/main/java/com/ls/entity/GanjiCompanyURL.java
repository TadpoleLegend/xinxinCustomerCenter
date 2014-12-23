package com.ls.entity;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.ls.util.XinXinUtils;
@Entity
@Table(name="ls_ganji_companyurl")
public class GanjiCompanyURL extends BaseCompanyURL {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String toString() {

		return "GanjiCompanyURL [id=" + id + ", name=" + name + ", area=" + area + ", url=" + url + ", companyId=" + companyId + ", publishDate=" + publishDate + ", cityId=" + cityId + ", createDate=" + createDate + ", hasGet=" + hasGet + ", savedCompany=" + savedCompany + ", status=" + status + ", comments=" + comments + "]";
	}
	
	
	public static GanjiCompanyURL create() {
		GanjiCompanyURL ganjiCompanyURL = new GanjiCompanyURL();
		
		ganjiCompanyURL.setCreateDate(XinXinUtils.getNow());
		ganjiCompanyURL.setHasGet(false);
		
		return ganjiCompanyURL;
	}

}
