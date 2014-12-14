package com.ls.job.gradcompany;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.OteCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.repository.CityRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.service.GrabService;

public class Grab138Company {
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	private CityRepository cityRepository;
	private OteCompanyURLRepository oteCompanyURLRepository;
	private GrabService grabService;
	
	
	
	public CityRepository getCityRepository() {
		return cityRepository;
	}

	public void setCityRepository(CityRepository cityRepository) {
		this.cityRepository = cityRepository;
	}

	public OteCompanyURLRepository getOteCompanyURLRepository() {
		return oteCompanyURLRepository;
	}

	public void setOteCompanyURLRepository(
			OteCompanyURLRepository oteCompanyURLRepository) {
		this.oteCompanyURLRepository = oteCompanyURLRepository;
	}

	public GrabService getGrabService() {
		return grabService;
	}

	public void setGrabService(GrabService grabService) {
		this.grabService = grabService;
	}

	private Company envelopeCompany(OteCompanyURL oteCompanyURL){
		try {
			Company company = new Company();
			company.setName(oteCompanyURL.getName());
			company.setArea(oteCompanyURL.getArea());
			company.setUpdateDate(sf.parse(oteCompanyURL.getPublishDate()));
			company.setCityId(oteCompanyURL.getCityId());
			company.setoTEresourceId(oteCompanyURL.getCompanyId());
			company.setOteUrl(oteCompanyURL.getUrl());
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
				List<OteCompanyURL> list = oteCompanyURLRepository.findByCityId(City.getId());
				for(OteCompanyURL oteCompanyURL:list){
					Company company = envelopeCompany(oteCompanyURL);
					if(company != null){
						HtmlParserUtilFor138.getInstance().findCompanyDetails(company);
						grabService.mergeCompanyData(company, ResourceTypeEnum.OneThreeEight.getId());
						oteCompanyURL.setHasGet(1);
						oteCompanyURLRepository.save(oteCompanyURL);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	
	}
}
