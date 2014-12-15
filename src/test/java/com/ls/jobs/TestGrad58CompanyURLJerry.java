package com.ls.jobs;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.visitors.NodeVisitor;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext.xml")
public class TestGrad58CompanyURLJerry {

	@Autowired
	private CityURLRepository cityURLRepository;

	@Autowired
	private FeCompanyURLRepository feCompanyURLRepository;

	@Resource(name = "FEGrabCompanyDetailPageUrlService")
	private GrabCompanyDetailPageUrlService grabCompanyDetailPageUrlService;

	private Integer currentCityId;

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
								yaojinboUrl.setPublishDate(XinXinConstants.SIMPLE_DATE_FORMATTER.format(new Date()));
							} else {
								yaojinboUrl.setPublishDate("2014-" + nodeTranslated.getStringText());
							}
						}
					}

				}
				yaojinboUrl.setCityId(currentCityId);
				feCompanyURLRepository.save(yaojinboUrl);
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	};

	@Test
	public void testGrabCompanyUrls() throws Exception {
		grabCompanyDetailPageUrlService.grabUrl(null);
	}

	@Test
	public void testGrabSingleCityCompanyUrls() throws Exception {

		final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
		webClient.getOptions().setThrowExceptionOnScriptError(false);

		Parser htmlParser = new Parser();

		int currentPageIndex = 1;
		try {
			while (true) {
				String baseMeirongshiUrl = "http://smx.58.com/meirongshi/pn" + currentPageIndex;

				final HtmlPage customerListPage = webClient.getPage(baseMeirongshiUrl);

				String listHtml = customerListPage.getWebResponse().getContentAsString();
				htmlParser.setInputHTML(listHtml);

				htmlParser.visitAllNodesWith(companyUrlListVisitor);

				if (!listHtml.contains("下一页")) {
					break;
				}
				currentPageIndex++;
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Test
	public void testHandlePublishDate() {
		String todayString = "今天";
		String hoursAgao = "10小时前";
		String lastYearExample = "1-23";

		try {
			System.out.println(XinXinConstants.MONTH_AND_DAY_DATE_FORMATTER.parse(lastYearExample));

			System.out.println(XinXinConstants.MONTH_AND_DAY_DATE_FORMATTER.format(new Date()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Test
	public void testgrabTwoDaysRecently() {
		grabCompanyDetailPageUrlService.grabTwoDaysRecently();
	}
}
