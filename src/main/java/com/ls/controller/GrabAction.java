package com.ls.controller;

import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.json.JSONException;
import org.apache.struts2.json.JSONUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.OteCompanyURL;
import com.ls.entity.Province;
import com.ls.repository.CityRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.service.GrabService;
import com.ls.service.UserService;
import com.ls.vo.GrabStatistic;

@Component("grabAction")
@Scope("prototype")
public class GrabAction extends BaseAction {

	private static final long serialVersionUID = 1504280162797333021L;

	@Resource(name = "grabService")
	private GrabService grabService;
	
	@Resource(name = "userService")
	private UserService userService;

	@Autowired
	private CityRepository cityRepository;

	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;

	private List<Company> companies;

	private List<City> jiangsuCities = new ArrayList<City>();
	
	private String statistic;
	
	private GrabStatistic grabStatistic;
	
	private List<OteCompanyURL> oteCompanyURLs;

	public String grabCompanyIndexPage() {
		String url = getParameter("url");
		if (null != url) {
			url = URLDecoder.decode(url);

			// companies = grabService.grabCompanyInPage(url);

			Company company = new Company();
			company.setfEurl("http://su.58.com/meirongshi/?PGTID=14052432562410.5737352641994795&ClickID=1");

			companies = ImmutableList.of(company);
		} else {
			
		}

		return SUCCESS;
	}

	public String getcities() {
		
		String provinceName = getParameter("province");
		
		List<Province> provinces = provinceRepository.getProvinceRepository().findByName(provinceName);
		
		if (provinces == null || provinces.isEmpty()) {
			System.out.println("Not found");
		} else {
			jiangsuCities = provinces.get(0).getCitys();
		}

		return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	public String grabSelectedCities() {
		
		String selectedURLs = getParameter("selectedURLs");
		String lastPublishDate = getParameter("lastPublishDate");
		
		if (StringUtils.isBlank(lastPublishDate)) {
			lastPublishDate = "2014/01/01";
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/DD/yyyy");
		java.util.Date lastDate = null;
		try {
			lastDate = simpleDateFormat.parse(lastPublishDate);
			
		} catch (ParseException e1) {
			
		}
		
		try {
			List<String> cityURLs = (List<String>) JSONUtil.deserialize(selectedURLs);
			
			for (String url : cityURLs) {
				 grabStatistic = grabService.grabCompanyInformationByUrl(url, lastDate);
				 try {
					Thread.sleep(60 * 2 * 1000);
					
				} catch (InterruptedException e) {
					
				}
				
			}
		} catch (JSONException e) {
			
		}
		return SUCCESS;
	}
	
	public String load138PreviewList() {
		
		String cityIds = getParameter("cityIdsHtml");
		
		List<Integer> userCityIds = new ArrayList<Integer>();
		List<City> userCities = null;
		if (StringUtils.isEmpty(cityIds)) {
			userCities = cityRepository.findByUsers(ImmutableList.of(commonService.getCurrentLoggedInUser()));
		} else {
			Object[] cityArray = JSONArray.fromObject(cityIds).toArray();
			if (cityArray == null || cityArray.length == 0) {
				userCities = cityRepository.findByUsers(ImmutableList.of(commonService.getCurrentLoggedInUser()));
			} else {
				userCities = userService.getCitiesFromSpans(cityArray);
			}
			
		}
		
		for (City city : userCities) {
			userCityIds.add(city.getId());
		}
		
		oteCompanyURLs = oteCompanyURLRepository.findTop20ByCityIdInOrderByIdAsc(userCityIds);
		
		return SUCCESS;
	}
	
	public List<OteCompanyURL> getOteCompanyURLs() {
		
		return oteCompanyURLs;
	}

	public void setOteCompanyURLs(List<OteCompanyURL> oteCompanyURLs) {
		this.oteCompanyURLs = oteCompanyURLs;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public List<City> getJiangsuCities() {
		return jiangsuCities;
	}

	public void setJiangsuCities(List<City> jiangsuCities) {
		this.jiangsuCities = jiangsuCities;
	}

	public String getStatistic() {
		return statistic;
	}

	public void setStatistic(String statistic) {
		this.statistic = statistic;
	}

	public GrabStatistic getGrabStatistic() {
		return grabStatistic;
	}

	public void setGrabStatistic(GrabStatistic grabStatistic) {
		this.grabStatistic = grabStatistic;
	}
	
}
