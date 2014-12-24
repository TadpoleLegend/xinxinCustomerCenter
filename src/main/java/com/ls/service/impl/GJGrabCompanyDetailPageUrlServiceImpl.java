package com.ls.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jetty.http.HttpURI;
import org.htmlparser.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.ls.entity.CityURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.entity.GrabDetailUrlLog;
import com.ls.enums.ResourceTypeEnum;
import com.ls.repository.CityURLRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.util.DateUtils;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Service("GJGrabCompanyDetailPageUrlService")
@Scope("prototype")
public class GJGrabCompanyDetailPageUrlServiceImpl implements GrabCompanyDetailPageUrlService {
	@Autowired
	private CityURLRepository cityURLRepository;

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;
	
	@Autowired
	private GanjiCompanyURLRepository ganjiCompanyURLRepository;

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;
	
	private Logger logger = LoggerFactory.getLogger(GJGrabCompanyDetailPageUrlServiceImpl.class);
	
	public ResponseVo grabUrl(String postdate) {

		List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.Ganji.getId());

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		Parser htmlParser = new Parser();

		for (CityURL cityURL : cityUrls) {
			grabSingleCity(postdate, cityURL, webClient, htmlParser);

		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Sleeping failed " + e.getMessage());
		}

		return ResponseVo.newSuccessMessage("Totally grabed ");
	}

	public ResponseVo grabSingleCityUrl(Integer cityUrlId) {


		CityURL cityURL = cityURLRepository.findOne(cityUrlId);
		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		Parser htmlParser = new Parser();
		
		grabSingleCity(null, cityURL, webClient, htmlParser);
		
		return null;
	
	}
	
	public void restAlittleWhile(Integer miliseconds) {
		try {
			Thread.sleep(miliseconds);
		} catch (InterruptedException e) {
			logger.error("Sleeping failed " + e.getMessage());
		}
	}
	
	private void grabSingleCity(String postdate, CityURL cityURL, WebClient webClient, Parser htmlParser) {

		Integer currentCityId = cityURL.getCity().getId();

		int currentPageIndex = 1;
		while (true) {
			
			try {
				String baseMeirongshiUrl = cityURL.getBaseUrl();
				
				restAlittleWhile(1100);
				
				if (StringUtils.isNotBlank(postdate)) {
					baseMeirongshiUrl += "u1";
				}
				
				baseMeirongshiUrl = (baseMeirongshiUrl + "o" + currentPageIndex);
				
				final HtmlPage customerListPage = webClient.getPage(baseMeirongshiUrl);

				String listHtml = customerListPage.getWebResponse().getContentAsString();

				if (listHtml.contains("请输入验证码继续访问")) {
					logger.error("Grab URL failed by ip blocked.");
					break;
					
				}
				
				int startCompanyIndex = 1, endCompanyIndex = 54;
				
				for (; startCompanyIndex < endCompanyIndex; startCompanyIndex ++) {
					
					String companyUrlPath = "//*[@id=\"list-job-id\"]/div[9]/dl[" + startCompanyIndex + "]/dd[1]/a";
					try {
						List<?> companyLink = customerListPage.getByXPath(companyUrlPath);
						if (null != companyLink && !companyLink.isEmpty()) {
							
							if (companyLink.get(0) instanceof HtmlAnchor) {
								HtmlAnchor htmlAnchor = ( HtmlAnchor ) companyLink.get(0);
								String href = htmlAnchor.getHrefAttribute();
								String name = htmlAnchor.getAttribute("title");
								
								HttpURI gjhHttpURI = new HttpURI(href);
								String resourceId = gjhHttpURI.getPath().replace("/", "").replace("gongsi", "");
								GanjiCompanyURL existedCompanyURL = ganjiCompanyURLRepository.findByCompanyId(resourceId);
								
								if (existedCompanyURL == null) {
									GanjiCompanyURL ganjiCompanyURL = GanjiCompanyURL.create();
									ganjiCompanyURL.setUrl(href);
									ganjiCompanyURL.setName(name);
									ganjiCompanyURL.setCityId(currentCityId);
									ganjiCompanyURL.setCompanyId(resourceId);
									
									ganjiCompanyURLRepository.saveAndFlush(ganjiCompanyURL);
								}
								
								
							}
						}
					} catch (Exception e) {
						
					} 
				}
				
				String nextPageButtonXPath = "//*[@id=\"list-job-id\"]/div[15]/ul/li[11]/a/span";
				try {
					List<?> nextpageButton = customerListPage.getByXPath(nextPageButtonXPath);
					if (null == nextpageButton || nextpageButton.isEmpty()) {
						break;
					} 
				} catch (Exception e) {
					
				} 
				
				currentPageIndex++;
				
				// safe quite for unexpected errors
				if (currentPageIndex > 61) {
					break;
				}

			} catch (Exception e) {
				logger.error("Grab URL failed by " + e.getMessage());
			}
		}
		
	}
	public void grabTwoDaysRecently() {

		GrabDetailUrlLog grabDetailUrlLog = new GrabDetailUrlLog();
		try {

			grabDetailUrlLog.setQueryParameter("recent three days");
			grabDetailUrlLog.setStartDate(XinXinUtils.getNow());

			ResponseVo response = this.grabUrl("o2");

			grabDetailUrlLog.setCreateDate(XinXinUtils.getNow());

			grabDetailUrlLog.setMessage(response.toString());

			logger.info(response.toString());
			grabDetailUrlLog.setStatus("success");
			grabDetailUrlLog.setType("ganji");

		} catch (Exception e) {

			grabDetailUrlLog.setStatus("fail");
			grabDetailUrlLog.setMessage(e.getMessage());
			grabDetailUrlLog.setCreateDate(XinXinUtils.getNow());
		}

		grabDetailUrlLogRepository.save(grabDetailUrlLog);
	
	}

}
