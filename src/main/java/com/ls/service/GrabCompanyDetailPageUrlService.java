package com.ls.service;

import com.ls.vo.ResponseVo;

public interface GrabCompanyDetailPageUrlService {
	
	ResponseVo grabUrl(String postdate);
	
	ResponseVo grabSingleCityUrl(Integer cityUrlId);

	void grabTwoDaysRecently();
}
