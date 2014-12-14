package com.ls.job.gradcompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.repository.CityRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.service.GrabService;

public class Grab58Company {
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	private FeCompanyURLRepository feCompanyURLRepository;
	private CityRepository cityRepository;
	private GrabService grabService;
	
	
	
	public FeCompanyURLRepository getFeCompanyURLRepository() {
		return feCompanyURLRepository;
	}

	public void setFeCompanyURLRepository(
			FeCompanyURLRepository feCompanyURLRepository) {
		this.feCompanyURLRepository = feCompanyURLRepository;
	}

	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public GrabService getGrabService() {
		return grabService;
	}

	public void setGrabService(GrabService grabService) {
		this.grabService = grabService;
	}

	private Company envelopeCompany(FeCompanyURL feCompanyURL){
		try {
			Company company = new Company();
			company.setName(feCompanyURL.getName());
			company.setArea(feCompanyURL.getArea());
			company.setUpdateDate(sf.parse(feCompanyURL.getPublishDate()));
			company.setCityId(feCompanyURL.getCityId());
			company.setfEresourceId(feCompanyURL.getCompanyId());
			company.setfEurl(feCompanyURL.getUrl());
			return company;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public void execute(){

		try {
			List<City> Cities = cityRepository.findAll();
			for(City City:Cities){
				List<FeCompanyURL> list = feCompanyURLRepository.findByCityId(City.getId());
				for(FeCompanyURL feCompanyURL:list){
					Company company = envelopeCompany(feCompanyURL);
					if(company != null){
						HtmlParserUtilFor58.getInstance().findCompanyDetails(company);
						grabService.mergeCompanyData(company, ResourceTypeEnum.OneThreeEight.getId());
						feCompanyURL.setHasGet(1);
						feCompanyURLRepository.save(feCompanyURL);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
