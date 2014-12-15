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
import com.ls.entity.GanjiCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.service.GrabService;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabGanji {
	
	@Autowired
	private CityURLRepository cityURLRepository;
	@Autowired
	private CompanyRepository companyRepository;
	@Resource(name = "grabService")
	private GrabService grabService;
	@Autowired
	private CityRepository cityRepository;
	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;
	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	/*@Test
	public void testGrabCompanyList() throws Exception{
		try {
			Date date  = Calendar.getInstance().getTime();
			List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.Ganji.getId());
			for(CityURL cityURL:cityUrls){
				int days= 0;
				Object [] arr = new Object[2];
				if(cityURL.getUpdateDate()==null){
					arr[0]=0;
				}else{
					days = DateUtils.minusDate(cityURL.getUpdateDate(),date);
					int u = DateUtils.getGanjiDate(days);
					arr[0]=u;
				}
				for(int i=1;i<1000;i++){
				arr[1]=i;
				String cityUrl = cityURL.getUrl();
				String url =  MessageFormat.format(cityUrl, arr);
				System.err.println("url is : " + url);
				//List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
				List<Company> companiesInThisPage = null;
				if(companiesInThisPage.isEmpty()){
					cityURL.setUpdateDate(date);
					cityURLRepository.save(cityURL);
					break;
				}
				for(Company company:companiesInThisPage){
					company.setCityId(cityURL.getCity().getId());
					company.setResouceType(ResourceTypeEnum.Ganji.getId());
					System.out.println(company.getDescription());
					grabService.mergeCompanyData(company, ResourceTypeEnum.Ganji.getId());
					System.out.println(company.getPhoneSrc());
					System.out.println(company.getEmailSrc());
					System.out.println(company.getContactor());
					System.out.println(company.getAddress());
					System.out.println(company.getEmployeeCount());
					
//					companyRepository.save(company);
				}
				
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
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
	@Test
	public void testGrabCompany() throws Exception{
		try {
			List<City> Cities = cityRepository.findAll();
			for(City City:Cities){
				List<GanjiCompanyURL> list = ganjiCompanyURLRepository.findByCityId(City.getId());
				for(GanjiCompanyURL ganjiCompanyURL:list){
					Company company = envelopeCompany(ganjiCompanyURL);
					if(company != null){
						HtmlParserUtilForGanJi.getInstance().findCompanyDetails(company);
						grabService.mergeCompanyData(company, ResourceTypeEnum.Ganji.getId());
						ganjiCompanyURL.setHasGet(1);
						ganjiCompanyURLRepository.save(ganjiCompanyURL);
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*public static void main(String[] args) {
		try {
			Map<String, Map<String, String>> provinces = LocationUtil.getInstance().findGanjiCities();
			if (!provinces.isEmpty()) {
				for (Entry<String, Map<String, String>> et : provinces
						.entrySet()) {
					Map<String, String> map = et.getValue();
					for (Entry<String, String> city : map.entrySet()) {
						String url = city.getValue() + "meirongshi/o1/";
						System.out.println(url);
						List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
						for (Company c : companiesInThisPage) {
							System.err.println(c.getName());
							System.err.println(c.getArea());
							System.err.println(c.getAddress());
							System.err.println(c.getContactor());
							System.err.println(c.getEmployeeCount());
							System.err.println(c.getfEurl());
							System.err.println(c.getDescription());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
}
