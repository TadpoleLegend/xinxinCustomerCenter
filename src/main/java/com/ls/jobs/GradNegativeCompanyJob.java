package com.ls.jobs;

import java.util.Calendar;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ls.entity.Company;
import com.ls.entity.NegativeCompany;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.HtmlParserUtilFor138;
import com.ls.grab.HtmlParserUtilFor58;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.repository.CompanyRepository;
import com.ls.repository.NegativeCompanyRepository;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;

public class GradNegativeCompanyJob {
	
	private NegativeCompanyRepository negativeCompanyRepository;
	
	private CompanyRepository companyRepository;
	
	public NegativeCompanyRepository getNegativeCompanyRepository() {
		return negativeCompanyRepository;
	}

	public void setNegativeCompanyRepository(
			NegativeCompanyRepository negativeCompanyRepository) {
		this.negativeCompanyRepository = negativeCompanyRepository;
	}

	public CompanyRepository getCompanyRepository() {
		return companyRepository;
	}

	public void setCompanyRepository(CompanyRepository companyRepository) {
		this.companyRepository = companyRepository;
	}

	private void envelopCompany(Company company,NegativeCompany negativeCompany){
		try {
			company.setCityId(negativeCompany.getCityId());
//			company.setResouceType(negativeCompany.getSourceType());
			company.setArea(negativeCompany.getArea());
			company.setEmailSrc(negativeCompany.getEmailSrc());
			company.setName(negativeCompany.getName());
			if(ResourceTypeEnum.OneThreeEight.getId().equals(negativeCompany.getResourceType())){
				company.setoTEresourceId(negativeCompany.getResourceType());
				company.setOteUrl(negativeCompany.getUrl());
				company.setDescription(negativeCompany.getDescription());
				company.setEmployeeCount(negativeCompany.getEmployeeCount());
			}else if(ResourceTypeEnum.Ganji.getId().equals(negativeCompany.getResourceType())){
				company.setGanjiresourceId(negativeCompany.getResourceType());
				company.setGanjiUrl(negativeCompany.getUrl());
			}else if(ResourceTypeEnum.FiveEight.getId().equals(negativeCompany.getResourceType())){
				company.setfEresourceId(negativeCompany.getResourceType());
				company.setfEurl(negativeCompany.getUrl());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void execut(){
		try {
			WebClient webClient= new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(false);
			List<NegativeCompany> list = this.negativeCompanyRepository.findAll();
			if(!list.isEmpty()){
				for(NegativeCompany negativeCompany:list){
					Company company = new Company();
					if(ResourceTypeEnum.OneThreeEight.getId().equals(negativeCompany.getResourceType())){
						HtmlParserUtilFor138 parser = HtmlParserUtilFor138.getInstance();
						String detailPageHtml = parser.getContactDiv(negativeCompany.getResourceId());
						parser.parseContactDivForTelAndMobile(detailPageHtml,company);
					}else if(ResourceTypeEnum.Ganji.getId().equals(negativeCompany.getResourceType())){
						HtmlParserUtilForGanJi parse = HtmlParserUtilForGanJi.getInstance();
						String testURL = negativeCompany.getUrl();
						HtmlPage mainPage = webClient.getPage(testURL);
						String htmlDetail = mainPage.getWebResponse().getContentAsString();
						parse.findCompanyDetails(company, htmlDetail);
					}else if(ResourceTypeEnum.FiveEight.getId().equals(negativeCompany.getResourceType())){
						HtmlParserUtilFor58 parse = HtmlParserUtilFor58.getInstance();
						String testURL = negativeCompany.getUrl();
						HtmlPage mainPage = webClient.getPage(testURL);
						String htmlDetail = mainPage.getWebResponse().getContentAsString();
						parse.findCompanyDetails(company,htmlDetail);
					}
					if(XinXinUtils.stringIsEmpty(company.getPhoneSrc()) && XinXinUtils.stringIsEmpty(company.getMobilePhoneSrc())){
						continue;
					}
					envelopCompany(company,negativeCompany);
					mergeCompanyData(company,negativeCompany.getResourceType(),negativeCompany.getId());
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void mergeCompanyData(Company company,String recourceType,Integer negativeCompanyId){
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
						company.setGrabDate(dateTime);
						this.companyRepository.save(company);
						this.negativeCompanyRepository.delete(negativeCompanyId);
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
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private void generateDBCompany(Company dbCompany,Company websiteCompany){
		
		try {
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
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
