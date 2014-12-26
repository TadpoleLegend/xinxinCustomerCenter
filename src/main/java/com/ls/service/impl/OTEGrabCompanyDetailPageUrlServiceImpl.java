package com.ls.service.impl;

import java.text.MessageFormat;
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
import com.ls.entity.CityURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.entity.GrabDetailUrlLog;
import com.ls.entity.OteCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.BaseHtmlParseUtil;
import com.ls.repository.CityURLRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.repository.GanjiCompanyURLRepository;
import com.ls.repository.GrabDetailUrlLogRepository;
import com.ls.repository.OteCompanyURLRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.util.XinXinUtils;
import com.ls.vo.ResponseVo;

@Service("OTEGrabCompanyDetailPageUrlService")
@Scope("prototype")
public class OTEGrabCompanyDetailPageUrlServiceImpl implements GrabCompanyDetailPageUrlService {
	@Autowired
	private CityURLRepository cityURLRepository;

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;
	
	@Autowired
	private OteCompanyURLRepository oteCompanyURLRepository;

	@Autowired
	private GrabDetailUrlLogRepository grabDetailUrlLogRepository;
	
	private Logger logger = LoggerFactory.getLogger(OTEGrabCompanyDetailPageUrlServiceImpl.class);
	
	public ResponseVo grabUrl(String postdate) {

		List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.OneThreeEight.getId());

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
	
	//http://s.138job.com/hire/{1}?keyword=&workadd=1326&keywordtype=1&position=0&&releasedate={2}
	
	private boolean noMoreResults(String html) {
		
		return BaseHtmlParseUtil.findFirstOneWithClassName(html, "no_result_tips") != null;
	}
	
	private void grabSingleCity(String postdate, CityURL cityURL, WebClient webClient, Parser htmlParser) {

		Integer currentCityId = cityURL.getCity().getId();

		int currentPageIndex = 1;
		while (true) {
			
			try {
				String pageUrl = MessageFormat.format(cityURL.getUrl(), "", "" + currentPageIndex, "");
				
				if (StringUtils.isNotBlank(postdate)) {
					pageUrl += postdate;
				}
				
				final HtmlPage customerListPage = webClient.getPage(pageUrl);
				
				if (noMoreResults(customerListPage.getWebResponse().getContentAsString())) {
					break;
				}

				int startCompanyIndex = 2, endCompanyIndex = 17;
				for (; startCompanyIndex < endCompanyIndex; startCompanyIndex ++) {
					
					String companyUrlPath = "/html/body/div[4]/div[3]/div[1]/div[" + startCompanyIndex + "]/h1/span[2]/a";
					try {
						List<?> companyLink = customerListPage.getByXPath(companyUrlPath);
						if (null != companyLink && !companyLink.isEmpty()) {
							
							if (companyLink.get(0) instanceof HtmlAnchor) {
								HtmlAnchor htmlAnchor = ( HtmlAnchor ) companyLink.get(0);
								String href = htmlAnchor.getHrefAttribute();
								
								String name = htmlAnchor.getFirstChild().asText();
								
								String resourceId = XinXinUtils.getOteResourceIdFromUrl(href);
								
								OteCompanyURL existedCompanyURL = oteCompanyURLRepository.findByCompanyId(resourceId);
								
								if (existedCompanyURL == null) {
									OteCompanyURL oteCompanyURL = OteCompanyURL.create();
									oteCompanyURL.setUrl(href);
									oteCompanyURL.setName(name);
									oteCompanyURL.setCityId(currentCityId);
									oteCompanyURL.setCompanyId(resourceId);
									
									oteCompanyURLRepository.saveAndFlush(oteCompanyURL);
									
									System.out.println("Page " + currentPageIndex + " added -- " + oteCompanyURL.toString());
								} else {
									System.out.println("Page " + currentPageIndex + " existed -- " + existedCompanyURL.toString());
								}
							}
						}
					} catch (Exception e) {
						logger.error("grab url fail with url " + pageUrl + e.getMessage());
					} 
				}
				
				currentPageIndex++;
				
				// safe quite for avoid deap loop errors
				if (currentPageIndex > 1300) {
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
