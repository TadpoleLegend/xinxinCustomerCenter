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
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.HeadingTag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.visitors.NodeVisitor;
import org.htmlparser.visitors.TextExtractingVisitor;
import org.springframework.beans.factory.annotation.Autowired;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Splitter;
import com.ls.entity.City;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.Province;
import com.ls.repository.CityRepository;
import com.ls.repository.ProvinceRepository;
import com.ls.util.ChineseConverter;
import com.ls.util.XinXinUtils;

public class HtmlParserUtilFor58 extends BaseHtmlParseUtil {

	@Autowired
	private ProvinceRepository provinceRepository;

	@Autowired
	private CityRepository cityRepository;

	private SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	private Calendar cal = Calendar.getInstance();
	private static HtmlParserUtilFor58 htmlParserUtilFor58;

	private HtmlParserUtilFor58() {

	}

	private static final WebClient webClient;
	static {
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
		webClient.getOptions().setCssEnabled(false);
	}

	public static HtmlParserUtilFor58 getInstance() {

		if (htmlParserUtilFor58 == null) {
			return new HtmlParserUtilFor58();
		} else {
			return htmlParserUtilFor58;
		}
	}

	public List<FeCompanyURL> findPagedCompanyList(String url) {

		final List<FeCompanyURL> companyList = new ArrayList<FeCompanyURL>();

		try {
			HtmlPage mainPage = webClient.getPage(url);
			String wholeCityPageHTML = mainPage.getWebResponse().getContentAsString();
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(wholeCityPageHTML);

			NodeVisitor nodeVisitor = new NodeVisitor(){

				@Override
				public void visitTag(Tag tag) {

					super.visitTag(tag);

					if (TagFinderUtil.findCompanyLink(tag)) {
						LinkTag linkTag = (LinkTag)tag;
						FeCompanyURL company = new FeCompanyURL();
						company.setName(StringUtils.trimToEmpty(tag.getAttribute("title")));
						company.setUrl(tag.getAttribute("href"));

						Node nodeLink = linkTag.getParent().getParent();

						Node[] nodes = nodeLink.getChildren().toNodeArray();

						for (int i = 0; i < nodes.length; i++) {
							Node node = nodes[i];

							if (node instanceof DefinitionListBullet) {

								DefinitionListBullet nodeTranslated = (DefinitionListBullet)node;
								String className = nodeTranslated.getAttribute("class");
								if (className != null && className.equals("w96")) {
									company.setArea(nodeTranslated.getStringText());
								}
								if (className != null && className.equals("w68")) {
									String puStr = nodeTranslated.getStringText();
									if (!XinXinUtils.stringIsEmpty(puStr)) {
										if (puStr.contains("小时")) {
											company.setPublishDate(sf.format(Calendar.getInstance().getTime()));
										} else if (puStr.contains("今天")) {
											company.setPublishDate(sf.format(cal.getTime()));
										} else {
											String[] darr = puStr.split("-");
											if (darr != null && darr.length == 2) {
												String mStr = darr[0];
												int mint = Integer.parseInt(mStr);
												int cmonth = cal.get(Calendar.MONTH) + 1;
												if (mint > cmonth) {
													company.setPublishDate((cal.get(Calendar.YEAR) - 1) + "-" + puStr);
												} else {
													company.setPublishDate(cal.get(Calendar.YEAR) + "-" + puStr);
												}
											}
										}
									} else {
										company.setPublishDate(sf.format(cal.getTime()));
									}
								}
							}

						}
						String testURL = company.getUrl();
						int index = testURL.indexOf("58.com");
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

		Map<String, String> map = XinXinUtils.mergeDuplicateCompanyInOnePageFor58(companyList);
		List<FeCompanyURL> returnCompanyList = reduceDuplicateCompany(companyList, map);
		return returnCompanyList;
	}

	private List<FeCompanyURL> reduceDuplicateCompany(List<FeCompanyURL> companyList, Map<String, String> map) {

		List<FeCompanyURL> returnCompanyList = new ArrayList<FeCompanyURL>();
		for (FeCompanyURL company : companyList) {
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

	public void findCompanyDetails(Company company) {

		try {
			String testURL = company.getfEurl();
			HtmlPage mainPage = webClient.getPage(testURL);

			compositeCityAndProvince(mainPage, company);

			String htmlDetail = mainPage.getWebResponse().getContentAsString();

			parseDetails(company, htmlDetail);
			company.setDescription(findCompanyDescription(htmlDetail));

			findCompanyName(company, htmlDetail);

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
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
				boolean emailSrc = false;
				boolean contactor = false;
				boolean companyAddress = false;
				boolean employeecount = false;

				public boolean accept(Node node) {

					if (!phoneNum) {
						phoneNum = findContactorPhoneNumberImgSrc(company, node);
					}
					if (!emailSrc) {
						emailSrc = findContactorEmailImgSrc(company, node);
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
					if (phoneNum && emailSrc && contactor && companyAddress && employeecount) {
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

	public void findCompanyName(Company company, String detailPageHtml) {

		try {
			Div nameDiv = findFirstOneWithClassName(detailPageHtml, "compT");
			if (nameDiv != null) {
				Node nodes[] = nameDiv.getChildrenAsNodeArray();
				if (nodes != null) {
					for (Node node : nodes) {
						if (node instanceof HeadingTag) {
							HeadingTag ht = (HeadingTag)node;
							Node htNodes[] = ht.getChildrenAsNodeArray();
							if (htNodes != null) {
								for (Node htNode : htNodes) {
									if (htNode instanceof LinkTag) {
										LinkTag lt = (LinkTag)htNode;
										company.setName(lt.getStringText());
										return;
									}
								}
							}

						}

					}
				}
			}

		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean findContactorPhoneNumberImgSrc(Company company, Node node) {

		try {
			if (node instanceof TableRow) {
				TableRow row = (TableRow)node;
				Node[] nodeList = row.getChildren().toNodeArray();
				// find header, find column
				boolean contactorHeaderFound = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					// find title hardly
					if (!contactorHeaderFound && current instanceof TableHeader) {
						TableHeader th = (TableHeader)current;
						String tdConent = th.getStringText();
						// found!!!!!!
						if (tdConent.trim().contains("联系电话")) {
							contactorHeaderFound = true;
						}
					}
					// find his name after title found!!
					if (contactorHeaderFound && current instanceof TableColumn) {
						TableColumn td = (org.htmlparser.tags.TableColumn)current;
						if (td.getAttribute("class") != null && td.getAttribute("class").equals("telNum")) {
							Node[] list = td.getChildren().toNodeArray();
							boolean cellPhoneFound = false;

							for (Node img : list) {

								if (img instanceof ImageTag) {

									ImageTag imageTag = (ImageTag)img;

									if (!cellPhoneFound) {
										company.setMobilePhoneSrc(imageTag.getImageURL());
										cellPhoneFound = true;
										continue;
									}
									company.setPhoneSrc(imageTag.getImageURL());
								}
							}
						}
					}
				}
			}
		} catch (Exception ex) {

			return false;
		}

		return false;
	}

	public boolean findContactorEmailImgSrc(Company company, Node node) {

		if (node instanceof TableRow) {
			TableRow row = (TableRow)node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader)current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("邮箱")) {
						contactorHeaderFound = true;
					}
				}
				// find his email after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn)current;
					Node[] list = td.getChildren().toNodeArray();
					for (Node img : list) {
						if (img instanceof ImageTag) {
							ImageTag imageTag = (ImageTag)img;
							company.setEmailSrc(imageTag.getImageURL());
							return true;
						}
					}
				}

			}
		}
		return false;
	}

	public boolean findContactorName(Company company, Node node) {

		if (node instanceof TableRow) {
			TableRow row = (TableRow)node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader)current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("联系人")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn)current;
					company.setContactor(td.getStringText().trim());
					return true;
				}
			}
		}
		return false;
	}

	public boolean findCompanyAddress(Company company, Node node) {

		if (node instanceof TableRow) {
			TableRow row = (TableRow)node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader)current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("公司地址")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn)current;
					if (td.getAttribute("class") != null && td.getAttribute("class").equals("adress")) {
						Node[] list = td.getChildren().toNodeArray();
						for (Node span : list) {
							if (span instanceof Span) {
								Span spanTag = (Span)span;
								company.setAddress(spanTag.getStringText().trim());
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

		if (node instanceof TableRow) {
			TableRow row = (TableRow)node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader)current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("公司规模")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn)current;
					company.setEmployeeCount(td.getStringText().trim());
					return true;
				}
			}
		}
		return false;

	}

	public String findCompanyDescription(String detailPageHtml) {

		try {
			Div descriptionDiv = findFirstOneWithClassName(detailPageHtml, "compIntro");

			if (null == descriptionDiv) {
				return "";
			}

			Parser parser = new Parser(descriptionDiv.getStringText());
			TextExtractingVisitor textExtractingVisitor = new TextExtractingVisitor();
			parser.visitAllNodesWith(textExtractingVisitor);

			String descriptionIWant = textExtractingVisitor.getExtractedText();

			return ChineseConverter.simplized(descriptionIWant.trim());

		} catch (FailingHttpStatusCodeException e) {
			return "";
		} catch (Exception e) {
			return "";
		}
	}

}
