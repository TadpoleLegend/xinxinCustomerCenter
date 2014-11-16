package com.ls.service;

import java.util.Date;
import java.util.List;

import com.ls.entity.Company;
import com.ls.vo.GrabStatistic;

public interface GrabService {
	
	List<String> findFeCityURLs();
	
	void grabCompanyResource(String cityURL);
	
	void grabAllCompanyResource();
	
	List<Company> grabCompanyInPage(String indexPageURL);
	
	Company grabCompanyDetail(String detailPageUrl);

	GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd);
}
