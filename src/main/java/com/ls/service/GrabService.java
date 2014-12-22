package com.ls.service;

import java.util.Date;
import java.util.List;

import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.exception.UrlAlreadySavedException;
import com.ls.vo.GrabStatistic;
import com.ls.vo.ResponseVo;

public interface GrabService {
	
	List<String> findFeCityURLs();
	
	void grabCompanyResource(String cityURL);
	
	void grabAllCompanyResource();
	
	List<Company> grabCompanyInPage(String indexPageURL);
	
	Company grabCompanyDetailByUrl(String detailPageUrl);

	GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd);
	
	ResponseVo mergeCompanyData(Company company,String recourceType, FeCompanyURL feCompanyURL);
	
	ResponseVo grabSingleFECompanyByUrlId(Integer urlId);
	
	ResponseVo grabSingleFECompanyByUrl(FeCompanyURL feCompanyURL);
	
	ResponseVo grabSingleFECompanyByUrl(String url);

	void feJobDailyWork();
}
