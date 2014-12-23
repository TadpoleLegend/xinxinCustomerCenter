package com.ls.controller;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.entity.OteCompanyURL;
import com.ls.jobs.GrabFeJobOneByOneController;
import com.ls.repository.CityRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.service.GrabService;
import com.ls.service.UserService;
import com.ls.vo.GrabStatistic;
import com.ls.vo.ResponseVo;

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

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;

	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;

	private List<Company> companies;

	private List<Company> grabedCompanies;

	private List<City> jiangsuCities = new ArrayList<City>();

	private String statistic;

	private GrabStatistic grabStatistic;

	private List<OteCompanyURL> oteCompanyURLs;
	private List<FeCompanyURL> feCompanyURLs;
	private List<GanjiCompanyURL> ganjiCompanyURLs;

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

		// String provinceName = getParameter("province");
		//
		// List<Province> provinces =
		// provinceRepository.getProvinceRepository().findByName(provinceName);
		//
		// if (provinces == null || provinces.isEmpty()) {
		// System.out.println("Not found");
		// } else {
		// jiangsuCities = provinces.get(0).getCitys();
		// }

		return SUCCESS;
	}

	public String grabSelectedCities() {

		String selectedCityIds = getParameter("selectedIds");
		String datasourceType = getParameter("datasourceType");

		if (StringUtils.isBlank(datasourceType)) {
			setResponse(ResponseVo.newFailMessage("未搜索需要采集的数据源！"));

			return SUCCESS;
		}

		List<Integer> userCityIds = new ArrayList<Integer>();
		if (StringUtils.isEmpty(selectedCityIds) || selectedCityIds.equals("[]")) {

			setResponse(ResponseVo.newFailMessage("未选择需要采集的城市"));

			return SUCCESS;

		} else {

			Object[] cityArray = JSONArray.fromObject(selectedCityIds).toArray();

			for (Object object : cityArray) {

				String idString = object.toString();

				if (idString.contains("province")) {
					continue;
				}

				String cityId = idString.substring("city".length());

				userCityIds.add(Integer.valueOf(cityId));
			}

			if (datasourceType.equals("58")) {
				grabService.grabCompanyDetailInCityList(userCityIds);
			}
		}

		setResponse(ResponseVo.newSuccessMessage(null));

		return SUCCESS;
	}

	public String load138PreviewList() {

		String selectedIds = getParameter("selectedIds");

		try {
			List<Integer> userCityIds = new ArrayList<Integer>();

			if (StringUtils.isEmpty(selectedIds) || selectedIds.equals("[]")) {
				oteCompanyURLs = new ArrayList<OteCompanyURL>();
				return SUCCESS;

			} else {
				Object[] cityArray = JSONArray.fromObject(selectedIds).toArray();
				for (Object object : cityArray) {
					String idString = object.toString();
					if (idString.contains("province")) {
						continue;
					}

					String cityId = idString.substring("city".length());
					userCityIds.add(Integer.valueOf(cityId));
				}
			}

			oteCompanyURLs = oteCompanyURLRepository.findTop20ByCityIdInOrderByIdAsc(userCityIds);

		} catch (Exception e) {
			oteCompanyURLs = new ArrayList<OteCompanyURL>();
		}

		return SUCCESS;
	}

	public String load58PreviewList() {

		String selectedIds = getParameter("selectedIds");

		try {
			List<Integer> userCityIds = new ArrayList<Integer>();

			if (StringUtils.isEmpty(selectedIds) || selectedIds.equals("[]")) {
				feCompanyURLs = new ArrayList<FeCompanyURL>();
				return SUCCESS;

			} else {
				Object[] cityArray = JSONArray.fromObject(selectedIds).toArray();
				for (Object object : cityArray) {
					String idString = object.toString();
					if (idString.contains("province")) {
						continue;
					}

					String cityId = idString.substring("city".length());
					userCityIds.add(Integer.valueOf(cityId));
				}
			}
			PageRequest pageRequest = new PageRequest(0, 500);
			feCompanyURLs = feCompanyURLRepository.findByCityIdInAndSavedCompanyIsNullOrderByIdDesc(userCityIds, pageRequest);

		} catch (Exception e) {
			feCompanyURLs = new ArrayList<FeCompanyURL>();
		}

		return SUCCESS;
	}

	public String loadGanjiPreviewList() {
		String selectedIds = getParameter("selectedIds");

		try {
			List<Integer> userCityIds = new ArrayList<Integer>();

			if (StringUtils.isEmpty(selectedIds) || selectedIds.equals("[]")) {
				ganjiCompanyURLs = new ArrayList<GanjiCompanyURL>();
				return SUCCESS;

			} else {
				Object[] cityArray = JSONArray.fromObject(selectedIds).toArray();
				for (Object object : cityArray) {
					String idString = object.toString();
					if (idString.contains("province")) {
						continue;
					}

					String cityId = idString.substring("city".length());
					userCityIds.add(Integer.valueOf(cityId));
				}
			}

			ganjiCompanyURLs = ganjiCompanyURLRepository.findTop20ByCityIdInOrderByIdAsc(userCityIds);

		} catch (Exception e) {
			ganjiCompanyURLs = new ArrayList<GanjiCompanyURL>();
		}
		return SUCCESS;
	}

	public String grabSinglePage() {

		String targetDetailUrl = getParameter("userInputTargetDetailUrl");

		if (StringUtils.isBlank(targetDetailUrl)) {
			grabedCompanies = new ArrayList<Company>();
			return SUCCESS;
		}

		if (targetDetailUrl.contains("58.com")) {
			ResponseVo responseVo = grabService.grabSingleFECompanyByUrl(targetDetailUrl);

			setResponse(responseVo);
		} else if (targetDetailUrl.contains("ganji.com")) {
			ResponseVo responseVo = grabService.grabSingleGJCompanyByUrl(targetDetailUrl);

			setResponse(responseVo);
		}

		return SUCCESS;
	}

	public List<Company> getGrabedCompanies() {

		return grabedCompanies;
	}

	public void setGrabedCompanies(List<Company> grabedCompanies) {

		this.grabedCompanies = grabedCompanies;
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

	public List<FeCompanyURL> getFeCompanyURLs() {

		return feCompanyURLs;
	}

	public void setFeCompanyURLs(List<FeCompanyURL> feCompanyURLs) {

		this.feCompanyURLs = feCompanyURLs;
	}

	public List<GanjiCompanyURL> getGanjiCompanyURLs() {

		return ganjiCompanyURLs;
	}

	public void setGanjiCompanyURLs(List<GanjiCompanyURL> ganjiCompanyURLs) {

		this.ganjiCompanyURLs = ganjiCompanyURLs;
	}

}
