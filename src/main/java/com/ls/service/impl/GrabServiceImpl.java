package com.ls.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ImmutableList;
import com.ls.entity.City;
import com.ls.entity.CityURL;
import com.ls.entity.Company;
import com.ls.entity.CompanyResource;
import com.ls.entity.NegativeCompany;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.GrapImgUtil;
import com.ls.grab.HtmlParserUtilPlanB;
import com.ls.grab.HttpClientGrabUtil;
import com.ls.repository.CityRepository;
import com.ls.repository.CityURLRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.CompanyResourceRepository;
import com.ls.repository.NegativeCompanyRepository;
import com.ls.service.GrabService;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;
import com.ls.vo.GrabStatistic;

@Service("grabService")
public class GrabServiceImpl implements GrabService {
	
	@Autowired
	private CityURLRepository cityURLRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private NegativeCompanyRepository negativeCompanyRepository;
	
	@Autowired
	private CityRepository cityRepository;
	
	public List<String> findFeCityURLs() {
		
		List<String> list = ImmutableList.of("http://su.58.com/", "http://nj.58.com/");
		
		return list;
	}

	/**
	 * grab company name and URL to database 
	 */
	public void grabCompanyResource(String siteURL) {
		
	}

	public void grabAllCompanyResource() {

		List<CityURL> allCityURLs = cityURLRepository.findAll();
		Random random = new Random();
		int i = 0;
		
		for (CityURL cityURL : allCityURLs) {
			
			try {
				String urlInDb = cityURL.getUrl();
				
				String pagedCompanyURL = urlInDb.endsWith("/") ? urlInDb + "meirongshi/pn" : urlInDb + "/meirongshi/pn";
				
				
				
				String pagedCompanyURLWithPageNumber = pagedCompanyURL + i;
				String pagedCompanyHTML = HttpClientGrabUtil.fetchHTMLwithURL(pagedCompanyURLWithPageNumber);
				
				List<Company> companies = HtmlParserUtilPlanB.findPagedCompanyList(pagedCompanyHTML);
				
				for (Company company : companies) {
					
					CompanyResource companyResource = new CompanyResource();
					companyResource.setUrl(company.getfEurl());
					companyResource.setName(company.getName());
					companyResource.setType("58");
					
					companyResourceRepository.save(companyResource);
				}
				
				i++;
				
				if (i == 3) {
					break;
				}
				
				int waitSeconds = random.nextInt(5000);
				Thread.sleep(waitSeconds);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
	}

	public List<Company> grabCompanyInPage(String indexPageURL) {
		
		String pagedCompanyHTML = HttpClientGrabUtil.fetchHTMLwithURL(indexPageURL);
		
		List<Company> companies = HtmlParserUtilPlanB.findPagedCompanyList(pagedCompanyHTML);
		
		return companies;
	}

	public Company grabCompanyDetail(String detailPageUrl) {
		Company company = new Company();
		
		if (StringUtils.isBlank(detailPageUrl)) {
			throw new IllegalArgumentException("detail page url is empty");
		}
		String detailPageHtml =  HttpClientGrabUtil.fetchHTMLwithURL(detailPageUrl);
		
		return company;
	}

	/**
	 * 只抓10分钟,悠着点
	 */
	public GrabStatistic grabCompanyInformationByUrl(String url, Date publishDateEnd) {
		long start = System.currentTimeMillis();
		
		GrabStatistic grabStatistic = new GrabStatistic();
		
		// grab util the publish date
		int pageNumber = 0;
		
		int proccessCount = 0;
		int saved = 0;
		int error = 0;
		int duplicate = 0;
		while (true) {
			proccessCount ++;
			String pageURL = url + "meirongshi/pn" + pageNumber;
			
			String html = HttpClientGrabUtil.fetchHTMLwithURL(pageURL);
			
			List<Company> basicCompany = HtmlParserUtilPlanB.findPagedCompanyList(html);
			//List<City> citys = cityRepository.findByUrl(url);
			List<City> citys = null;
			City myCity = citys.get(0);
			for (Company company : basicCompany) {
					if (isDulpicate(company)) {
						duplicate ++;
						continue;
					}
					
					try {
						
						String companyDetailUrl = company.getfEurl();
						String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

						String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
						company.setContactor(contactor);

						String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
						company.setPhoneSrc(phoneImgSrc);
						
						String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
						company.setAddress(address);
						
						if (StringUtils.isNotBlank(phoneImgSrc)) {
							String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
							company.setPhoneSrc(imgFileNameAfterGrabed);
						}
						
						String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
						company.setEmailSrc(emailImgSrc);
						
						if (StringUtils.isNotBlank(emailImgSrc)) {
							String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
							company.setEmailSrc(emailImgFileNameAfterGrabed);
						}
						
						company.setCityId(myCity.getId());
						company.setProvinceId(myCity.getProvince().getId());
						
					} catch (Exception e) {
						error ++;
					}
					
					if (ifCompanyDataValueable(company)){
						
						List<Company> companyInDb = companyRepository.findByNameAndFEurl(company.getName(), company.getfEurl());
						
						if (null == companyInDb || companyInDb.isEmpty()) {
							
							try {
								companyRepository.save(company);
								saved ++;
							} catch (Exception e) {
								//
								error ++;
							}
							
						} else {
							duplicate ++;
						}
						
						
						
					} else {
						error ++;
					}
					
					String grabingPublishDate = company.getPublishDate();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyy-MM-DD");
				try {
					// break condition

					if (StringUtils.isNotBlank(grabingPublishDate)) {

						boolean ifChoosenDateBeforePublishDate = simpleDateFormat.parse("2014-" + grabingPublishDate).before(publishDateEnd);

						if (ifChoosenDateBeforePublishDate) {
							grabStatistic.setSaved(saved);
							grabStatistic.setTotalReaded(proccessCount);
							grabStatistic.setDuplicate(duplicate);

							return grabStatistic;
						}
					}
				} catch (ParseException e) {
						//
					}
			}
			
			pageNumber ++;
			
			long now = System.currentTimeMillis();
			
			long timeLasts = now - start;
			
			if( timeLasts > 1000 * 60 * 10) {
				
				grabStatistic.setSaved(saved);
				grabStatistic.setTotalReaded(proccessCount);
				grabStatistic.setDuplicate(duplicate);

				return grabStatistic;
			}
		}
		
		
	}
	
	private boolean ifCompanyDataValueable(Company company) {
		if (StringUtils.isBlank(company.getName()) || StringUtils.isBlank(company.getContactor()) || StringUtils.isBlank(company.getfEurl()) || StringUtils.isEmpty(company.getPhoneSrc())) 
		{
			return false;
		}
		
		return true;
	}
	
	private boolean isDulpicate(Company company) {
		List<Company> companies = companyRepository.findByNameAndContactorAndArea(company.getName(), company.getContactor(), company.getArea());
		
		if(companies == null || companies.size() == 0) return false;
		
		
		return true;
	}
	
	private NegativeCompany envelopNegativeCompany(Company company,String recourceType){
		NegativeCompany nc = new NegativeCompany();
		try {
			nc.setCityId(company.getCityId());
			nc.setSourceType(recourceType);
			if(ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)){
				nc.setResourceId(company.getoTEresourceId());
				nc.setUrl(company.getOteUrl());
			}else if(ResourceTypeEnum.Ganji.getId().equals(recourceType)){
				nc.setResourceId(company.getGanjiresourceId());
				nc.setUrl(company.getGanjiUrl());
			}else if(ResourceTypeEnum.FiveEight.getId().equals(recourceType)){
				nc.setResourceId(company.getfEresourceId());
				nc.setUrl(company.getfEurl());
			}
			nc.setSb_count(1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return nc;
		
	}
	
	public void mergeCompanyData(Company company,String recourceType){
		try {
			Company dataBaseCompany = null;
			if(ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)){
				dataBaseCompany = this.companyRepository.findCompanyFor138GrabJob(company.getCityId(), company.getoTEresourceId(),company.getName());
			}else if(ResourceTypeEnum.Ganji.getId().equals(recourceType)){
				dataBaseCompany = this.companyRepository.findCompanyForGanjiGrabJob(company.getCityId(), company.getGanjiresourceId(),company.getName());
			}else if(ResourceTypeEnum.FiveEight.getId().equals(recourceType)){
				dataBaseCompany = this.companyRepository.findCompanyFor58GrabJob(company.getCityId(), company.getfEresourceId(),company.getName());
			}
			if(dataBaseCompany == null){
				try {
					String dateTime = DateUtils.getDateFormate(Calendar.getInstance().getTime(),"yyyy-MM-dd hh:mm:ss");
					if(XinXinUtils.stringIsEmpty(company.getPhoneSrc()) && XinXinUtils.stringIsEmpty(company.getMobilePhoneSrc())){
						NegativeCompany nc = envelopNegativeCompany(company,recourceType);
						NegativeCompany dbNC = this.negativeCompanyRepository.findNegativeCompany(company.getCityId(), nc.getResourceId(), recourceType);
						if(dbNC == null){
							nc.setGrabDate(dateTime);
							this.negativeCompanyRepository.save(nc);
						}
					}else{
						company.setGrabDate(dateTime);
						this.companyRepository.save(company);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}else{
				ruleSaveForCompany(dataBaseCompany,company,recourceType);
				try {
					this.companyRepository.save(dataBaseCompany);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 138 is the basic inforamtion website
	 * if 138 not have the company, then set ganji website as the second choice
	 * the last choice is 58 website
	 * @param dbcompany
	 * @param websiteCompany
	 * @param recourceType
	 */
	
	private void ruleSaveForCompany(Company dbCompany,Company websiteCompany,String recourceType){
		if(ResourceTypeEnum.OneThreeEight.getId().equals(recourceType)){
			generateDBCompany(dbCompany,websiteCompany);
		}else if(ResourceTypeEnum.Ganji.getId().equals(recourceType)){
			if(dbCompany.getoTEresourceId()!=null){
				dbCompany.setfEresourceId(websiteCompany.getfEresourceId()==null?dbCompany.getfEresourceId():websiteCompany.getfEresourceId());
				dbCompany.setfEurl(websiteCompany.getfEurl()==null?dbCompany.getfEurl():websiteCompany.getfEurl());
			}else{
				generateDBCompany(dbCompany,websiteCompany);
			}
		}else if(ResourceTypeEnum.FiveEight.getId().equals(recourceType)){
			if(dbCompany.getoTEresourceId()!=null || dbCompany.getGanjiresourceId()!=null){
				dbCompany.setGanjiresourceId(websiteCompany.getGanjiresourceId()==null?dbCompany.getGanjiresourceId():websiteCompany.getGanjiresourceId());
				dbCompany.setGanjiUrl(websiteCompany.getGanjiUrl()==null?dbCompany.getGanjiUrl():websiteCompany.getGanjiUrl());
			}else{
				generateDBCompany(dbCompany,websiteCompany);
			}
		}
		dbCompany.setGrabDate(DateUtils.getDateFormate(Calendar.getInstance().getTime(),"yyyy-MM-dd hh:mm:ss"));
		
	}
	
	private void generateDBCompany(Company dbCompany,Company websiteCompany){
		
		dbCompany.setoTEresourceId(websiteCompany.getoTEresourceId()==null?dbCompany.getoTEresourceId():websiteCompany.getoTEresourceId());
		dbCompany.setfEresourceId(websiteCompany.getfEresourceId()==null?dbCompany.getfEresourceId():websiteCompany.getfEresourceId());
		dbCompany.setGanjiresourceId(websiteCompany.getGanjiresourceId()==null?dbCompany.getGanjiresourceId():websiteCompany.getGanjiresourceId());
		
		dbCompany.setName(websiteCompany.getName()==null?dbCompany.getName():websiteCompany.getName());
		dbCompany.setContactor(websiteCompany.getContactor()==null?dbCompany.getContactor():websiteCompany.getContactor());
		dbCompany.setEmail(websiteCompany.getEmail()==null?dbCompany.getEmail():websiteCompany.getEmail());
		dbCompany.setEmailSrc(websiteCompany.getEmailSrc()==null?dbCompany.getEmailSrc():websiteCompany.getEmailSrc());
		dbCompany.setPhone(websiteCompany.getPhone()==null?dbCompany.getPhone():websiteCompany.getPhone());
		dbCompany.setPhoneSrc(websiteCompany.getPhoneSrc()==null?dbCompany.getPhoneSrc():websiteCompany.getPhoneSrc());
		dbCompany.setMobilePhone(websiteCompany.getMobilePhone()==null?dbCompany.getMobilePhone():websiteCompany.getMobilePhone());
		dbCompany.setMobilePhoneSrc(websiteCompany.getMobilePhoneSrc()==null?dbCompany.getMobilePhoneSrc():websiteCompany.getMobilePhoneSrc());
		dbCompany.setAddress(websiteCompany.getAddress()==null?dbCompany.getAddress():websiteCompany.getAddress());
		dbCompany.setArea(websiteCompany.getArea()==null?dbCompany.getArea():websiteCompany.getArea());
		dbCompany.setfEurl(websiteCompany.getfEurl()==null?dbCompany.getfEurl():websiteCompany.getfEurl());
		dbCompany.setOteUrl(websiteCompany.getOteUrl()==null?dbCompany.getOteUrl():websiteCompany.getOteUrl());
		dbCompany.setGanjiUrl(websiteCompany.getGanjiUrl()==null?dbCompany.getGanjiUrl():websiteCompany.getGanjiUrl());
		dbCompany.setEmployeeCount(websiteCompany.getEmployeeCount()==null?dbCompany.getEmployeeCount():websiteCompany.getEmployeeCount());
		dbCompany.setDescription(websiteCompany.getDescription()==null?dbCompany.getDescription():websiteCompany.getDescription());
		
	}
	
}
