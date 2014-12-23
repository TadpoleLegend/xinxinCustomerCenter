package com.ls.service;

import java.util.Date;
import java.util.List;

import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.exception.UrlAlreadySavedException;
import com.ls.vo.GrabStatistic;
import com.ls.vo.ResponseVo;


public class BasicGrabService implements GrabService {

	public List<String> findFeCityURLs() {

		// TODO Auto-generated method stub
		return null;
	}

	public void grabCompanyResource(String cityURL) {

		// TODO Auto-generated method stub

	}

	public void grabAllCompanyResource() {

		// TODO Auto-generated method stub

	}

	public List<Company> grabCompanyInPage(String indexPageURL) {

		// TODO Auto-generated method stub
		return null;
	}

	public Company grabCompanyDetailByUrl(String detailPageUrl) {

		// TODO Auto-generated method stub
		return null;
	}

	public GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo saveCompanyToDb(Company company, String recourceType, FeCompanyURL feCompanyURL) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabSingleFECompanyByUrlId(Integer urlId) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabSingleFECompanyByUrl(FeCompanyURL feCompanyURL) {

		// TODO Auto-generated method stub
		return null;
	}

	public void feJobDailyWork() {

		// TODO Auto-generated method stub

	}

	public ResponseVo grabSingleFECompanyByUrl(String url) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabCompanyDetailInCityList(List<Integer> userCityIds) {
		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabSingleGJCompanyByUrlId(Integer urlId) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabSingleGJCompanyByUrl(GanjiCompanyURL ganjiCompanyURL) {

		// TODO Auto-generated method stub
		return null;
	}

	public ResponseVo grabSingleGJCompanyByUrl(String url) {

		// TODO Auto-generated method stub
		return null;
	}

}
