package com.ls.util;

import java.io.IOException;
import java.net.MalformedURLException;

import org.htmlparser.Parser;
import org.htmlparser.util.ParserException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ls.vo.CompanyDetailVo;


public class FiveEightOneCityDetailPageParser {
	
	public static CompanyDetailVo parseDetailFromUrl(String url) throws FailingHttpStatusCodeException, MalformedURLException, IOException {
		
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);
		
		final HtmlPage detailPage = webClient.getPage(url);
		
		CompanyDetailVo companyDetailVo = null;
		
		Parser htmlParser = new Parser();
		try {
			htmlParser.setInputHTML(detailPage.getWebResponse().getContentAsString());
			CompanyDetailNodeVisitor companyDetailNodeVisitor = new CompanyDetailNodeVisitor();
			htmlParser.visitAllNodesWith(companyDetailNodeVisitor);
			
			companyDetailVo = companyDetailNodeVisitor.getCompanyDetailVo();
			
		} catch (ParserException e) {
			return null;
		}
		
		return companyDetailVo;
		
	}

}
