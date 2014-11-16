package com.ls.grab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;


public class HtmlUnitGrabAuthan {

	public static void main(String[] args) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		String url = "https://auchan.chinab2bi.com/security/login.hlt";
		
		 final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		 webClient.getOptions().setJavaScriptEnabled(false);
		    // Get the first page
		    final HtmlPage page1 = webClient.getPage(url);

		    // Get the form that we are dealing with and within that form, 
		    // find the submit button and the field that we want to change.
		    final List<HtmlForm> forms =  page1.getForms();
		    
		    HtmlForm form = null;
		    for(HtmlForm singleForm : forms) {
		    	if (singleForm.getAttribute("id").equals("loginForm")) {
		    		form = singleForm;
				}
		    }

		    final HtmlSubmitInput button = form.getInputByName("Submit");
		    final HtmlTextInput textField = form.getInputByName("j_username");
		    final HtmlPasswordInput passwordField = form.getInputByName("j_password");

		    // Change the value of the text field
		    textField.setValueAttribute("AC1356");
		    passwordField.setValueAttribute("shyf1356");

		    // Now submit the form by clicking the button and get back the second page.
		    button.click();
		    
		    final HtmlPage indexPage = webClient.getPage("https://auchan.chinab2bi.com/mainLogon.hlt");
		    
		    final HtmlPage orderResultPage = webClient.getPage("https://auchan.chinab2bi.com/auchan/sellOrderMainQry/query.hlt?page.togglestatus=null&accountModel.vendorNo=1356&accountModel.poTypeCheck=&accountModel.poId=&accountModel.dateType=0&accountModel.dateStart=2014-10-01&accountModel.dateEnd=2014-10-26&accountModel.planRecptDateStart=2014-10-01&accountModel.planRecptDateEnd=2014-10-26&accountModel.realRecptDateStart=2014-10-01&accountModel.realRecptDateEnd=2014-10-26&accountModel.orderStatus=&accountModel.storeNo=&accountModel.poType=&page.pageSize=10&page.pageNo=1&page.totalPages=9&page.jumpNumber=1");
		    
		    System.out.println(orderResultPage.getWebResponse().getContentAsString());
		    
		    webClient.closeAllWindows();
	}
}
