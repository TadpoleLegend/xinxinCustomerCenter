package com.ls.grab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.visitors.NodeVisitor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlHeading1;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlParagraph;
import com.ls.entity.Company;
import com.ls.entity.GanjiCompanyURL;
import com.ls.util.XinXinUtils;

public class HtmlParserUtilForGanJi extends BaseHtmlParseUtil {

	private static HtmlParserUtilForGanJi htmlParserUtilForGanJi;

	private HtmlParserUtilForGanJi() {

	}

	public static HtmlParserUtilForGanJi getInstance() {

		if (htmlParserUtilForGanJi == null) {
			return new HtmlParserUtilForGanJi();
		} else {
			return htmlParserUtilForGanJi;
		}
	}

	private static final WebClient webClient;
	static {
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
	}
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	public static String todayStr = sf.format(Calendar.getInstance().getTime());

	public List<GanjiCompanyURL> findPagedCompanyList(String url) {

		final List<GanjiCompanyURL> companyList = new ArrayList<GanjiCompanyURL>();
		try {
			HtmlPage mainPage = webClient.getPage(url);
			String wholeCityPageHTML = mainPage.getWebResponse().getContentAsString();
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(wholeCityPageHTML);
			NodeVisitor nodeVisitor = new NodeVisitor(){

				@Override
				public void visitTag(Tag tag) {

					super.visitTag(tag);
					if (TagFinderUtil.findCompanyForGanji(tag)) {
						DefinitionListBullet definitionListBullet = (DefinitionListBullet)tag;
						LinkTag lt = (LinkTag)definitionListBullet.getChild(0);
						GanjiCompanyURL company = new GanjiCompanyURL();
						company.setName(StringUtils.trimToEmpty(lt.getStringText()));
						company.setUrl(lt.getAttribute("href"));
						Node nodeLink = definitionListBullet.getParent();
						Node[] nodes = nodeLink.getChildren().toNodeArray();

						for (int i = 0; i < nodes.length; i++) {
							Node node = nodes[i];
							if (node instanceof DefinitionListBullet) {

								DefinitionListBullet nodeTranslated = (DefinitionListBullet)node;
								String className = nodeTranslated.getAttribute("class");
								if (className != null && className.equals("pay")) {
									company.setArea(nodeTranslated.getStringText());
								}
								if (className != null && className.equals("pub-time")) {
									String nodeText = nodeTranslated.getStringText();
									if ("今天".equals(nodeText)) {
										company.setPublishDate(todayStr);
									} else {
										company.setPublishDate(nodeTranslated.getStringText());
									}
								}
							}
						}
						
						String testURL = company.getUrl();
						int index = testURL.indexOf("gongsi");
						if (index != -1) {
							String sub = testURL.substring(index + 7);
							int sIndex = sub.indexOf("/");
							if (sIndex != -1) {
								company.setCompanyId(sub.substring(0, sIndex));
							}
						}
						companyList.add(company);
					}
				}
			};

			htmlParser.visitAllNodesWith(nodeVisitor);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> map = XinXinUtils.mergeDuplicateCompanyInOnePageForGanji(companyList);
		List<GanjiCompanyURL> returnCompanyList = reduceDuplicateCompany(companyList, map);
		return returnCompanyList;
	}

	private List<GanjiCompanyURL> reduceDuplicateCompany(List<GanjiCompanyURL> companyList, Map<String, String> map) {

		List<GanjiCompanyURL> returnCompanyList = new ArrayList<GanjiCompanyURL>();
		for (GanjiCompanyURL company : companyList) {
			if (map.containsKey(company.getCompanyId())) {
				try {
					map.remove(company.getCompanyId());
					returnCompanyList.add(company);
				} catch (FailingHttpStatusCodeException e) {
					e.printStackTrace();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return returnCompanyList;
	}

	public void findCompanyDetails(Company company) throws FailingHttpStatusCodeException, MalformedURLException, IOException {

		String detailUrl = company.getGanjiUrl();

		HtmlPage mainPage = webClient.getPage(detailUrl);

		String htmlDetail = mainPage.getWebResponse().getContentAsString();

		compositeCityAndProvince(mainPage, company);

		parseDetails(company, htmlDetail);

		parseDescription(mainPage, company);

		if (StringUtils.isEmpty(company.getName())) {
			parseName(mainPage, company);
		}

	}

	
	public void parseDescription(HtmlPage mainPage, Company company) {
		
		String descriptionXPath = "/html/body/div[3]/div[4]/div[1]/div[1]/p";
		
		try {
			List<?> descriptionNodes = mainPage.getByXPath(descriptionXPath);
			if (null != descriptionNodes && !descriptionNodes.isEmpty()) {
				
				if (descriptionNodes.get(0) instanceof HtmlParagraph) {
					HtmlParagraph htmlParagraph = ( HtmlParagraph ) descriptionNodes.get(0);
					
					String descriptionText = htmlParagraph.getFirstChild().asText().trim();
					
					if (descriptionText.length() > 2000) {
						descriptionText = descriptionText.substring(0, 1999);
					}
					company.setDescription(descriptionText);
				}
			}
		} catch (Exception e) {

			company.setDescription("");
			
		} 
	}
	
	public void parseName(HtmlPage mainPage, Company company) {
		
		String[] nameXPath ={ "/html/body/div[3]/div[2]/h1", "/html/body/div[2]/div[1]/div/div[1]/h1", "/html/body/div[2]/div[1]/div[2]/h1" };
		for (String singleNamePath : nameXPath) {
			
			try {
				List<?> nameNodes = mainPage.getByXPath(singleNamePath);
				
				if (null != nameNodes && !nameNodes.isEmpty()) {
					
					if (nameNodes.get(0) instanceof HtmlHeading1) {
						HtmlHeading1 htmlParagraph = ( HtmlHeading1 ) nameNodes.get(0);
						
						String name = htmlParagraph.getFirstChild().asText().trim();
						if (StringUtils.isNotBlank(name)) {
							company.setName(name);
							break;
						}	
					}
				}
			} catch (Exception e) {

				company.setName("");
				
			} 
		}
		
		
	}

	public String parseDetails(final Company company, String detailPageHtml) {

		final StringBuilder contactorsPhoneImgSrcBuilder = new StringBuilder();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);
			htmlParser.extractAllNodesThatMatch(new NodeFilter(){

				private static final long serialVersionUID = -93037936232004146L;
				boolean phoneNum = false;
				boolean contactor = false;
				boolean companyAddress = false;
				boolean employeecount = false;

				public boolean accept(Node node) {

					if (!phoneNum) {
						phoneNum = findContactorPhoneNumberImgSrc(company, node);
					}
					if (!contactor) {
						contactor = findContactorName(company, node);
					}
					if (!companyAddress) {
						companyAddress = findCompanyAddress(company, node);
					}
					if (!employeecount) {
						employeecount = findCompanyEmployeeCount(company, node);
					}
					if (phoneNum && contactor && companyAddress && employeecount) {
						return true;
					}
					return false;
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		return contactorsPhoneImgSrcBuilder.toString();
	}

	public boolean findCompanyAddress(Company company, Node node) {

		if (node instanceof Bullet) {
			Bullet bullet = (Bullet)node;
			if (bullet.getChildren() != null) {
				Node[] nodeList = bullet.getChildren().toNodeArray();
				// find header, find column
				boolean foundFlag = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					if (!foundFlag && current instanceof TextNode) {
						TextNode definitionListBullet = (TextNode)current;
						String tdConent = definitionListBullet.getText();
						// found!!!!!!
						if (tdConent.trim().contains("公司地址")) {
							foundFlag = true;
						}
					}
					// find his name after title found!!
					if (foundFlag) {
						if (nodeList.length >= i + 3) {
							Node addressCurrent = nodeList[i + 2];
							if (addressCurrent instanceof TextNode) {
								TextNode addreeTag = (TextNode)addressCurrent;
								company.setAddress(addreeTag.getText());
								return true;
							}
						}
					}

				}
			}
		}
		return false;
	}

	public boolean findContactorPhoneNumberImgSrc(Company company, Node node) {

		if (node instanceof Bullet) {
			Bullet bullet = (Bullet)node;
			if (bullet.getChildren() != null) {
				Node[] nodeList = bullet.getChildren().toNodeArray();
				// find header, find column
				boolean contactorHeaderFound = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					if (!contactorHeaderFound && current instanceof TextNode) {
						TextNode definitionListBullet = (TextNode)current;
						String tdConent = definitionListBullet.getText();
						// found!!!!!!
						if (tdConent.trim().contains("联系电话")) {
							contactorHeaderFound = true;
						}
					}
					// find his name after title found!!
					if (contactorHeaderFound) {
						if (nodeList.length >= i + 2) {
							Node imgCurrent = nodeList[i + 1];
							if (imgCurrent instanceof ImageTag) {
								ImageTag imageTag = (ImageTag)imgCurrent;
								if ("absmiddle".equals(imageTag.getAttribute("align"))) {
									String phoneImgSrc = imageTag.getImageURL();
									if (phoneImgSrc != null && phoneImgSrc.trim().length() > 0) {
										if (phoneImgSrc.startsWith("/") && !phoneImgSrc.contains("www.ganji.com")) {
											company.setMobilePhoneSrc("http://www.ganji.com" + phoneImgSrc);
										} else {
											company.setMobilePhoneSrc(phoneImgSrc);
										}
									}
								}
								return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	public boolean findContactorName(Company company, Node node) {

		if (node instanceof Bullet) {
			Bullet bullet = (Bullet)node;
			if (bullet.getChildren() != null) {
				Node[] nodeList = bullet.getChildren().toNodeArray();
				// find header, find column
				boolean contactorHeaderFound = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					if (!contactorHeaderFound && current instanceof TextNode) {
						TextNode definitionListBullet = (TextNode)current;
						String tdConent = definitionListBullet.getText();
						// found!!!!!!
						if (tdConent.trim().contains("联系人")) {
							contactorHeaderFound = true;
						}
					}
					// find his name after title found!!
					if (contactorHeaderFound) {
						if (nodeList.length >= (3 + i)) {
							Node contactorCurrent = nodeList[i + 2];
							if (contactorCurrent instanceof TextNode) {
								TextNode contactorTag = (TextNode)contactorCurrent;
								company.setContactor(contactorTag.getText());
								return true;
							}
						} else {
							String text = current.getText();
							if (text != null) {
								String nText = text.trim().replace("联系人：", "");
								company.setContactor(nText);
								return true;
							}
						}
					}

				}
			}
		}
		return false;
	}

	public boolean findCompanyEmployeeCount(Company company, Node node) {

		if (node instanceof Bullet) {
			Bullet bullet = (Bullet)node;
			if (bullet.getChildren() != null) {
				Node[] nodeList = bullet.getChildren().toNodeArray();
				// find header, find column
				boolean contactorHeaderFound = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					if (!contactorHeaderFound && current instanceof TextNode) {
						TextNode definitionListBullet = (TextNode)current;
						String tdConent = definitionListBullet.getText();
						// found!!!!!!
						if (tdConent.trim().contains("公司规模")) {
							contactorHeaderFound = true;
						}
					}
					// find his name after title found!!
					if (contactorHeaderFound) {
						if (nodeList.length >= (2 + i)) {
							Node contactorCurrent = nodeList[i + 1];
							if (contactorCurrent instanceof TextNode) {
								TextNode contactorTag = (TextNode)contactorCurrent;
								company.setEmployeeCount(contactorTag.getText());
								return true;
							}
						} else {
							String text = current.getText();
							if (text != null) {
								String nText = text.trim().replace("公司规模：", "");
								company.setEmployeeCount(nText);
							}
							return true;
						}
					}

				}
			}
		}
		return false;
	}

	public String findCompanyDescription(String html) {

		Node node = findNodeById(html, "company_description");
		if (node != null && node instanceof ParagraphTag) {
			ParagraphTag pt = (ParagraphTag)node;
			return pt.getStringText().trim();
		}
		return null;
	}

}
