package com.ls.jobs;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.OteCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.service.GrabService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrab138 {
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Resource(name = "grabService")
	private GrabService grabService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;
	
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	
	/*@Test
	public void testGrabCompanyList() throws Exception{
		try {
			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.OneThreeEight.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				Object [] arr = new Object[3];
				if(cityURL.getUpdateDate()==null){
					arr[2]=0;
				}else{
					days = DateUtils.minusDate(date,cityURL.getUpdateDate());
					arr[2]=days;
				}
				for(int i=1;i<1300;i++){
				arr[1]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				//List<Company> companiesInThisPage = HtmlParserUtilFor138.getInstance().findPagedCompanyList(url);
				List<Company> companiesInThisPage = null;
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(Company company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					company.setResouceType(ResourceTypeEnum.OneThreeEight.getId());
					grabService.mergeCompanyData(company, ResourceTypeEnum.OneThreeEight.getId());
				}
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
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
	@Test
	public void testGrabCompany() throws Exception{
		try {
			List<City> Cities = cityRepository.findAll();
			for(City City:Cities){
				List<OteCompanyURL> list = oteCompanyURLRepository.findByCityId(City.getId());
				for(OteCompanyURL oteCompanyURL:list){
					Company company = envelopeCompany(oteCompanyURL);
					if(company != null){
						HtmlParserUtilFor138.getInstance().findCompanyDetails(company);
					//	grabService.mergeCompanyData(company, ResourceTypeEnum.OneThreeEight.getId());
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
