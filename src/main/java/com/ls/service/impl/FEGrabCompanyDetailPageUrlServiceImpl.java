package com.ls.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.visitors.NodeVisitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ls.constants.XinXinConstants;
import com.ls.entity.CityURL;
import com.ls.entity.FeCompanyURL;
import com.ls.enums.ResourceTypeEnum;
import com.ls.grab.TagFinderUtil;
import com.ls.repository.CityURLRepository;
import com.ls.repository.FeCompanyURLRepository;
import com.ls.service.GrabCompanyDetailPageUrlService;
import com.ls.vo.ResponseVo;

@Service("FEGrabCompanyDetailPageUrlService")
@Scope("prototype")
public class FEGrabCompanyDetailPageUrlServiceImpl implements GrabCompanyDetailPageUrlService {

	@Autowired
	private CityURLRepository cityURLRepository;

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;

	private Logger logger = LoggerFactory.getLogger(FEGrabCompanyDetailPageUrlServiceImpl.class);

	private Integer currentCityId;

	private int grabedUrlCount = 0;

	NodeVisitor companyUrlListVisitor = new NodeVisitor() {

		public void visitTag(Tag tag) {

			super.visitTag(tag);

			if (TagFinderUtil.findCompanyLink(tag)) {

				LinkTag linkTag = (LinkTag) tag;
				FeCompanyURL yaojinboUrl = new FeCompanyURL();

				String testURL = tag.getAttribute("href");
				int index = testURL.indexOf("58.com");

				if (index != -1) {
					String sub = testURL.substring(index + 7);
					int sIndex = sub.indexOf("/");
					if (sIndex != -1) {
						String resouceId = sub.substring(0, sIndex);
						FeCompanyURL feCompanyURL = feCompanyURLRepository.findByCompanyId(resouceId);
						if (feCompanyURL != null) {
							return;
						} else {
							yaojinboUrl.setCompanyId(resouceId);
						}
					}
				}

				yaojinboUrl.setName(StringUtils.trimToEmpty(tag.getAttribute("title")));
				yaojinboUrl.setUrl(tag.getAttribute("href"));

				Node nodeLink = linkTag.getParent().getParent();

				Node[] nodes = nodeLink.getChildren().toNodeArray();

				for (int i = 0; i < nodes.length; i++) {

					Node node = nodes[i];
					if (node instanceof DefinitionListBullet) {

						DefinitionListBullet nodeTranslated = (DefinitionListBullet) node;
						String className = nodeTranslated.getAttribute("class");
						if (className != null && className.equals("w96")) {
							yaojinboUrl.setArea(nodeTranslated.getStringText());
						}
						if (className != null && className.equals("w68")) {
							if (nodeTranslated.getStringText().contains("今天") || nodeTranslated.getStringText().contains("小时")) {
								yaojinboUrl.setPublishDate(XinXinConstants.MONTH_AND_DAY_DATE_FORMATTER.format(new Date()));
							} else {
								yaojinboUrl.setPublishDate(nodeTranslated.getStringText());
							}
						}
					}

				}
				yaojinboUrl.setCityId(currentCityId);
				feCompanyURLRepository.save(yaojinboUrl);

				grabedUrlCount++;
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					logger.error("Sleeping failed " + e.getMessage());
				}
			}
		}
	};

	public ResponseVo grabUrl(String postdate) {

		List<CityURL> cityUrls = cityURLRepository.findByResourceType(ResourceTypeEnum.FiveEight.getId());

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		Parser htmlParser = new Parser();

		for (CityURL cityURL : cityUrls) {

			currentCityId = cityURL.getCity().getId();

			int currentPageIndex = 1;

			try {
				String baseMeirongshiUrl = cityURL.getBaseUrl() + currentPageIndex;
				
				if (StringUtils.isNotBlank(postdate)) {
					baseMeirongshiUrl += ( "?" + postdate);
				}
				final HtmlPage customerListPage = webClient.getPage(baseMeirongshiUrl);

				String listHtml = customerListPage.getWebResponse().getContentAsString();
				htmlParser.setInputHTML(listHtml);

				htmlParser.visitAllNodesWith(companyUrlListVisitor);

				if (!listHtml.contains("下一页")) {
					continue;
				}
				currentPageIndex++;

			} catch (Exception e) {
				logger.error("Grab URL failed by " + e.getMessage());
			}

		}
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			logger.error("Sleeping failed " + e.getMessage());
		}

		return ResponseVo.newSuccessMessage("Totally grabed " + grabedUrlCount + " records of url");
	}

}
