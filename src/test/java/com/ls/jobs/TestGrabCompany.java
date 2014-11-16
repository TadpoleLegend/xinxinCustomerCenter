package com.ls.jobs;

import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.io.Files;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.Problem;
import com.ls.entity.CompanyResource;
import com.ls.entity.Province;
import com.ls.grab.GrapImgUtil;
import com.ls.grab.HtmlParserUtilPlanB;
import com.ls.grab.HttpClientGrabUtil;
import com.ls.repository.CompanyRepository;
import com.ls.repository.ProblemRepository;
import com.ls.repository.CompanyResourceRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.service.GrabService;
import com.ls.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrabCompany {

	private File file = new File("D:\\Jerry\\58.txt");

	@Autowired
	private CompanyRepository companyRepository;
	
	@Autowired
	private ProblemRepository problemRepository;
	
	@Autowired
	private CompanyResourceRepository companyResourceRepository;
	
	@Autowired
	private ProvinceRepository provinceRepository;
	
	@Resource(name = "grabService")
	private GrabService grabService;

	@Test
	public void testGrabCompanyList() throws Exception{

		String testURL = "http://su.58.com/meirongshi/pn0";

		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);
		
		File file = new File("wholePagedcompanyList.html");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(htmlForPage);
		fileWriter.close();
		
	//	String htmlForPage = Files.toString(file, Charset.defaultCharset());
		
	//	System.out.println(htmlForPage);
		
		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		Assert.assertTrue(!companiesInThisPage.isEmpty());

		for (Company company : companiesInThisPage) {

			System.out.println(company.toString());
		}
	}

	@Test
	public void testGrabCompanyDetail138() throws Exception{

		String testURL = "http://www.138job.com/shtml/Company/18307/C_1212781.shtml";

		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);
		
		File file = new File("C_1212781.html");
		if (!file.exists()) {
			file.createNewFile();
		}
		
		FileWriter fileWriter = new FileWriter(file);
		fileWriter.write(htmlForPage);
		fileWriter.close();
	}
	
	@Test
	public void testGrabCompanyName() throws Exception {

		String htmlForPage = Files.toString(file, Charset.defaultCharset());

		Company testcomCompany = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage).get(0);

		Assert.assertNotNull(testcomCompany.getName());
		Assert.assertNotNull(testcomCompany.getfEurl());
	}

	@Test
	public void testGrabDetailedPage() throws Exception {
		// http://qy.58.com/9880155593991/?PGTID=14042836308430.7793496957798185&ClickID=2
		String testURL = "http://qy.58.com/13571834103302/";
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL, "detailedCompanyPageHtml.txt");

		Assert.assertNotNull(htmlForPage);

	}

	@Test
	public void testGrabContractorInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactor = HtmlParserUtilPlanB.findContactorName(html);

		Assert.assertEquals("", contactor);
	}

	@Test
	public void testGrabEmployeeCountInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactor = HtmlParserUtilPlanB.findCompanyEmployeeCount(html);
		
		System.out.println(contactor);
		Assert.assertEquals("1-49人", contactor);
	}
	
	@Test
	public void testGrabContractorPhoneImgSrcInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorPhone = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(html);

		Assert.assertEquals(contactorPhone, "http://image.58.com/showphone.aspx?t=v55&v=82319568EEFB8401DCC4BEA34EC3736B6");
	}

	@Test
	public void testGrabContractorEmailImgSrcInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorEmail = HtmlParserUtilPlanB.findContactorEmailImgSrc(html);

		Assert.assertEquals(contactorEmail, "http://image.58.com/showphone.aspx?t=v55&v=ADE0982AA4122C1859F727629C15A292B2421493BF67452E");
	}

	@Test
	public void testGrabContractorAddressInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String contactorPhone = HtmlParserUtilPlanB.findCompanyAddress(html);

		Assert.assertEquals(contactorPhone, "");
	}
	
	@Test
	public void testGrabCompanyDescriptionInDetailedPage() throws Exception {
		String html = Files.toString(new File("detailedCompanyPageHtml.txt"), Charset.defaultCharset());
		String description = HtmlParserUtilPlanB.findCompanyDescription(html);

		//Assert.assertEquals(description, "");
		System.out.println(description);
	}
	@Test
	public void testGrabImg() throws Exception {
		String src = "http://image.58.com/showphone.aspx?t=v55&v=82319568EEFB84016CC4BEA34EC3736B6";
		String imgFileName = GrapImgUtil.grabImgWithSrc(src);
		
		System.out.println(imgFileName);
	}

	@Test
	public void testGrabJob() throws Exception {

		String testURL = "http://su.58.com/meirongshi/pn0";

		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(testURL);

		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Company company : companiesInThisPage) {
			
			synchronized (this) {
				try {
					String companyDetailUrl = company.getfEurl();
					String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

					String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
					company.setContactor(contactor);

					String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
					company.setPhoneImgSrc(phoneImgSrc);
					
					String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
					company.setAddress(address);

					String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
					company.setPhoneSrc(imgFileNameAfterGrabed);
					
					String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
					company.setEmailSrc(emailImgSrc);
					
					if (StringUtils.isNotBlank(emailImgSrc)) {
						String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
						company.setEmailSrc(emailImgFileNameAfterGrabed);
					}
					
					System.out.println(company);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				companyRepository.save(company);
				
			}
		}
		
	//	FileWriter fileWriter = new FileWriter(new File("companies.txt"));
	//	fileWriter.write(stringBuilder.toString());
	//	fileWriter.close();
	}
	
	
	@Test
	public void testGrabJobWithFile() throws Exception {
		
		File file = new File("wholePagedcompanyList.html");
		
		String htmlForPage = Files.toString(file, Charset.defaultCharset());

		List<Company> companiesInThisPage = HtmlParserUtilPlanB.findPagedCompanyList(htmlForPage);
		
		StringBuilder stringBuilder = new StringBuilder();
		for (Company company : companiesInThisPage) {
			
			synchronized (this) {
				try {
					String companyDetailUrl = company.getfEurl();
					String detailPageHtml = HttpClientGrabUtil.fetchHTMLwithURL(companyDetailUrl);

					String contactor = HtmlParserUtilPlanB.findContactorName(detailPageHtml);
					company.setContactor(contactor);

					String phoneImgSrc = HtmlParserUtilPlanB.findContactorPhoneNumberImgSrc(detailPageHtml);
					company.setPhoneImgSrc(phoneImgSrc);
					
					String address = HtmlParserUtilPlanB.findCompanyAddress(detailPageHtml);
					company.setAddress(address);

					String imgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(phoneImgSrc);
					company.setPhoneSrc(imgFileNameAfterGrabed);
					
					String emailImgSrc = HtmlParserUtilPlanB.findContactorEmailImgSrc(detailPageHtml);
					company.setEmailSrc(emailImgSrc);
					
					if (StringUtils.isNotBlank(emailImgSrc)) {
						String emailImgFileNameAfterGrabed = GrapImgUtil.grabImgWithSrc(emailImgSrc);
						company.setEmailSrc(emailImgFileNameAfterGrabed);
					}
					
					System.out.println(company);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				//companyRepository.save(company);
				
				//stringBuilder.append(company.toString() + "\n");
			}
		}
		
	}

	@Test
	public void testSaveCompany() throws Exception {
		Company testCompany = new Company();
		testCompany.setAddress("长宁区玉屏南路");
		testCompany.setContactor("江李明");
		testCompany.setEmail("sdfasdfasdf");
		testCompany.setPhone("1123345564");
		
		companyRepository.save(testCompany);
	}
	
	@Test
	public void testSaveCompanyURL() throws Exception {
		
		grabService.grabAllCompanyResource();
	}
	@Test
	public void testSaveCompanyURLone() throws Exception {
		
		CompanyResource companyResource = new CompanyResource();
		companyResource.setName("姑苏区欧泉伊妮美容美体馆 ");
		companyResource.setUrl("http://qy.58.com/24230403986438/?PGTID=14047374119020.5358016782187727&ClickID=1");
		companyResource.setType("58");
		
		CompanyResource result = companyResourceRepository.save(companyResource);
		
		System.out.println(result.toString());
	}
	
	@Test
	public void testSaveProblem() throws Exception {
		Problem problem = new Problem();
		problem.setName("Jerry太帅");
		
		Problem result = problemRepository.save(problem);
		
		System.out.println(result.toString());
		
		Company testCompany = new Company();
		testCompany.setAddress("aaa");
		testCompany.setContactor("");
		testCompany.setEmail("jjiang");
		testCompany.setPhone("1123345564");
		List<Problem> problems = ImmutableList.of(result);
		testCompany.setProblems(problems);
		
		Company companyResult = companyRepository.save(testCompany);
		
		System.out.println(companyResult.toString());
		
		
	}
	
	@Test
	public void testGrabCities() throws Exception {
		String urlString = "http://www.58.com/changecity.aspx";
		String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(urlString);
		
		List<Province> provinces = HtmlParserUtilPlanB.findCities(htmlForPage);
		
		for (Province province : provinces) {
			
			provinceRepository.save(province);
		}
		
		
	}
	
	@Test
	public void testGrabByService() throws Exception {
		String city = "http://su.58.com/";
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		grabService.grabCompanyInformationByUrl(city, simpleDateFormat.parse("2014-7-17"));
	}
	
	@Test
	public void testSaveFile() throws Exception {
		
		System.out.println(System.getProperty("user.dir"));
		File file = new File("D:\\workspace\\littleshop\\src\\main\\webapp\\img\\aa.txt");
		if (!file.exists()) {
			file.createNewFile();
		}
	}
	
	@Test
	public void testFindCompanyWithNameContactorArea() {
		List<Company> companies = companyRepository.findByNameAndContactorAndArea("姑苏区欧泉伊妮美容美体馆", "唐先生", "苏州");
		Assert.assertTrue(companies.size() > 0);
		
		System.out.println(companies);
		
	}
	
}
