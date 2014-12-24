package com.ls.service;

import java.util.Date;
import java.util.List;

import com.ls.entity.BaseCompanyURL;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.vo.GrabStatistic;
import com.ls.vo.ResponseVo;

public interface GrabService {
	
	List<String> findFeCityURLs();
	
	void grabCompanyResource(String cityURL);
	
	void grabAllCompanyResource();
	
	List<Company> grabCompanyInPage(String indexPageURL);
	
	Company grabCompanyDetailByUrl(String detailPageUrl);

	GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd);
	
	ResponseVo saveCompanyToDb(Company company,String recourceType, BaseCompanyURL baseCompanyURL);
	
	ResponseVo grabSingleFECompanyByUrlId(Integer urlId);
	
	ResponseVo grabSingleFECompanyByUrl(FeCompanyURL feCompanyURL);
	
	ResponseVo grabSingleFECompanyByUrl(String url);

	void feJobDailyWork();
	void gjJobDailyWork();
	
	ResponseVo grabCompanyDetailInCityList(List<Integer> userCityIds);
	
	ResponseVo grabSingleGJCompanyByUrlId(Integer urlId);
	
	ResponseVo grabSingleGJCompanyByUrl(GanjiCompanyURL ganjiCompanyURL);
	
	ResponseVo grabSingleGJCompanyByUrl(String url);
}
