package com.ls.job.gradcompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.GanjiCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.service.GrabService;

public class GrabGanjiCompany {
	private CityRepository cityRepository;
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;
	private GrabService grabService;
	
	
	
	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public GanjiCompanyURLRepository getGanjiCompanyURLRepository() {
		return ganjiCompanyURLRepository;
	}

	public void setGanjiCompanyURLRepository(
			GanjiCompanyURLRepository ganjiCompanyURLRepository) {
		this.ganjiCompanyURLRepository = ganjiCompanyURLRepository;
	}

	public GrabService getGrabService() {
		return grabService;
	}

	public void setGrabService(GrabService grabService) {
		this.grabService = grabService;
	}

	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	private Company envelopeCompany(GanjiCompanyURL ganjiCompanyURL){
		try {
			Company company = new Company();
			company.setName(ganjiCompanyURL.getName());
			company.setArea(ganjiCompanyURL.getArea());
			company.setUpdateDate(sf.parse(ganjiCompanyURL.getPublishDate()));
			company.setCityId(ganjiCompanyURL.getCityId());
			company.setGanjiresourceId(ganjiCompanyURL.getCompanyId());
			company.setGanjiUrl(ganjiCompanyURL.getUrl());
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
				List<GanjiCompanyURL> list = ganjiCompanyURLRepository.findByCityId(City.getId());
				for(GanjiCompanyURL ganjiCompanyURL:list){
					Company company = envelopeCompany(ganjiCompanyURL);
					if(company != null){
						HtmlParserUtilForGanJi.getInstance().findCompanyDetails(company);
						grabService.mergeCompanyData(company, ResourceTypeEnum.OneThreeEight.getId());
						ganjiCompanyURL.setHasGet(1);
						ganjiCompanyURLRepository.save(ganjiCompanyURL);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
