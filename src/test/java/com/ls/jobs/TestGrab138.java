package com.ls.jobs;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButtonInput;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;

//@RunWith(SpringJUnit4ClassRunner.class)
public class TestGrab138 {

	@Test
	public void testGrab138CompanyList() {
		try {
			String baseCompanyListUrl = "http://www.138job.com/";

			final WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage mainPage = webClient.getPage(baseCompanyListUrl);
			
			
			final List<HtmlForm> forms = mainPage.getForms();

			HtmlForm searchForm = null;
			for (HtmlForm singleForm : forms) {
				if (singleForm.getAttribute("name").equals("hiresimplesearch")) {
					searchForm = singleForm;
				}
			}

			final HtmlButtonInput areaInput = (HtmlButtonInput) searchForm.getElementsByAttribute("input", "id", "search_nameWorkadd").get(0);

			final HtmlSubmitInput submitInput = (HtmlSubmitInput) searchForm.getElementsByAttribute("input", "class", "resume_btn_5").get(0);

			areaInput.setValueAttribute("上海");
			submitInput.click();
			
			String nextUrl = "http://s.138job.com/hire/1?workadd=1001&keywordtype=1&keyword=";
			
			HtmlPage companyListPage = webClient.getPage(nextUrl);
			
			//This result is the Shanghai company list page one
			//System.out.println(companyListPage.getWebResponse().getContentAsString());
			
			String exampleCompanyURL = "http://www.138job.com/shtml/Company/18408/H_347494.shtml";
			
		    HtmlPage exampeCompanyPage = webClient.getPage(exampleCompanyURL);
		    String html = exampeCompanyPage.getWebResponse().getContentAsString();
		    
		    System.out.println(html);
		    
		    
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGrabAddress() {
		try {
			String address = "http://www.138job.com/AjaxMethods/AjaxLoadContacts.aspx?action=hirecontacts&comId=1414561&hireId=347494";
			final WebClient webClient = new WebClient(BrowserVersion.CHROME);
			webClient.getOptions().setJavaScriptEnabled(false);
			final HtmlPage mainPage = webClient.getPage(address);
			
			System.out.println(mainPage.getWebResponse().getContentAsString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
