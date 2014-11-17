package com.ls.grab;

import java.io.IOException;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TextNode;
import org.htmlparser.tags.Bullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ls.entity.Company;

public class HtmlParserUtilFor138 extends BaseHtmlParseUtil {

	
	private static  HtmlParserUtilFor138 htmlParserUtilFor138;
	private HtmlParserUtilFor138(){}
	
	private static final WebClient webClient;
	static{
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
	}
	
	public static HtmlParserUtilFor138 getInstance(){
		if(htmlParserUtilFor138 == null){
			return new HtmlParserUtilFor138();
		}else{
			return htmlParserUtilFor138;
		}
	}
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	public static String todayStr = sf.format(Calendar.getInstance().getTime());
	
	
	public List<Company> findPagedCompanyList(String url) {

		final List<Company> companyList = new ArrayList<Company>();
		try {
			HtmlPage mainPage = webClient.getPage(url);
			String wholeCityPageHTML = mainPage.getWebResponse().getContentAsString();
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(wholeCityPageHTML);
			NodeVisitor nodeVisitor = new NodeVisitor() {

				@Override
				public void visitTag(Tag tag) {

					super.visitTag(tag);

					if (TagFinderUtil.findCompanyFor138(tag)) {
						Span span = (Span) tag;
						LinkTag lt = (LinkTag)span.getChild(0);
						Company company = new Company();
						company.setName(StringUtils.trimToEmpty(lt.getStringText()));
						company.setfEurl(lt.getAttribute("href"));
						Node nodeLink = span.getParent();
						Node[] nodes = nodeLink.getChildren().toNodeArray();
						for (int i = 0; i < nodes.length; i++) {
							Node node = nodes[i];
							if (node instanceof Span) {
								
								Span nodeTranslated = (Span) node;
								String className = nodeTranslated.getAttribute("class");
								if (className!= null && className.equals("d")) {
									company.setPublishDate(nodeTranslated.getStringText());
									break;
								}
							}
							
						}
						Node grandP = span.getParent().getParent();
						Node[] grandnodes = grandP.getChildren().toNodeArray();
						for (int i = 0; i < grandnodes.length; i++) {
							boolean grandPFlag = false;
							Node grandN = grandnodes[i];
							if (grandN instanceof Div) {
								Div nodeTranslated = (Div) grandN;
								String className = nodeTranslated.getAttribute("class");
								if (className!= null && className.equals("job_gk")) {
									Node [] nodeTranslatedChilds  = nodeTranslated.getChildrenAsNodeArray();
									for(int j=0;j<nodeTranslatedChilds.length;j++){
										if(nodeTranslatedChilds[j] instanceof Div){
											boolean nodeTranslatedChildsFlag = false;
											Div areaDiv = (Div)nodeTranslatedChilds[j];
											if("job_area".equals(areaDiv.getAttribute("class"))){
												Node areaNodes [] =  areaDiv.getChildrenAsNodeArray();
												for(Node nd:areaNodes){
													boolean areaNodeFlag =false;
													if(nd instanceof Span){
														Span areaSpan = (Span)nd;
														Node lastNodes [] = areaSpan.getChildrenAsNodeArray();
														if(lastNodes!=null && lastNodes.length>0){
															boolean flag  = false;
															if(lastNodes[1] instanceof  TextNode){
																TextNode df = (TextNode)lastNodes[1];
																if(df.getText().contains("地区")){
																	flag = true;
																}
																if(flag){
																	TextNode tn = (TextNode)lastNodes[3];
																	company.setArea(tn.getText());
																	areaNodeFlag = true;
																	nodeTranslatedChildsFlag = true;
																	grandPFlag = true;
																	break;
																}
															}
														}
													}
													if(areaNodeFlag){
														break;
													}
												}
											}
											if(nodeTranslatedChildsFlag){
												break;
											}
										}
									}
									
								}
							}
							if(grandPFlag){
								break;
							}
							
						}
						String htmlForPage = HttpClientGrabUtil.fetchHTMLwithURL(company.getfEurl());
						String phoneImgSrc = findContactorPhoneNumberImgSrc(htmlForPage);
						if(phoneImgSrc!=null&&phoneImgSrc.trim().length()>0){
							if(phoneImgSrc.startsWith("/")&&!phoneImgSrc.contains("www.ganji.com")){
								company.setPhoneImgSrc("http://www.ganji.com"+phoneImgSrc);
							}else{
								company.setPhoneImgSrc(phoneImgSrc);
							}
						}
						company.setContactor(findContactorName(htmlForPage));
						company.setAddress(findCompanyAddress(htmlForPage));
						company.setDescription(findCompanyDescription(htmlForPage));
						company.setEmployeeCount(findCompanyEmployeeCount(htmlForPage));
						String testURL = company.getfEurl();
						//findCompanyDetail(HttpClientGrabUtil.fetchHTMLwithURL(testURL),company);
						try {
							findCompanyDetail(webClient.getPage(testURL).getWebResponse().getContentAsString(),company);
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
						/*System.err.println(company.getName());
						System.err.println(company.getArea());
						System.err.println(company.getPublishDate());
						System.err.println(company.getfEurl());*/
						companyList.add(company);
						return;
					}
				}
			};

			htmlParser.visitAllNodesWith(nodeVisitor);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return companyList;
	}
	public static void findCompanyDetail(String detailPageHtml,final Company company) {


		final StringBuilder returnBuilder = new StringBuilder();

		try {
			System.out.println(detailPageHtml);
			Node contactInfo = findNodeById(detailPageHtml,"contact");
			Node companyInfo = findNodeByClassId(detailPageHtml,"companyinfo com_intr");
			if(companyInfo instanceof Div){
				Div info = (Div)companyInfo;
				company.setDescription(info.getStringText());
			}
			if(contactInfo instanceof Span){
				Span contact =(Span)contactInfo;
				System.err.println(contact.getStringText());
				Node nodes []= contact.getChildrenAsNodeArray();
				for(Node node:nodes){
					if(node instanceof TableTag){
						TableTag tableTag = (TableTag)node;
						Node tbody = tableTag.getChild(0);
						System.err.println(tbody.getClass().getName());
					}
				}
				
			}
		if(companyInfo instanceof Div){
			Div companyDiv = (Div)companyInfo;
			System.err.println(companyDiv.getStringText());
		}

		} catch (Exception e) {
			e.printStackTrace();
		}


	
	}
	
	
	public String findCompanyAddress(String detailPageHtml) {

		final StringBuilder returnBuilder = new StringBuilder();

		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Bullet) {
						Bullet bullet = (Bullet) node;
						if(bullet.getChildren()!=null){
						Node[] nodeList = bullet.getChildren().toNodeArray();

						// find header, find column
						boolean foundFlag = false;
						for (int i = 0; i < nodeList.length; i++) {
							Node current = nodeList[i];
							if (!foundFlag && current instanceof TextNode) {
								TextNode definitionListBullet = (TextNode) current;
								String tdConent = definitionListBullet.getText();
								// found!!!!!!
								if (tdConent.trim().contains("公司地址")) {
									foundFlag = true;
								}
							}
							
							// find his name after title found!!
							if (foundFlag) {
								if(nodeList.length>=i+3){
									Node addressCurrent = nodeList[i+2];
									if (addressCurrent instanceof TextNode) {
										TextNode addreeTag = (TextNode) addressCurrent;
										returnBuilder.append(addreeTag.getText());
										return true;
									}
								}
							}

						}
					}
					}
					return false;
				}
			});

		} catch (ParserException e) {
			e.printStackTrace();
		}

		return returnBuilder.toString();

	}
	
	
	public String findContactorPhoneNumberImgSrc(String detailPageHtml) {

		final StringBuilder contactorsPhoneImgSrcBuilder = new StringBuilder();

		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Bullet) {
						Bullet bullet = (Bullet) node;
						if(bullet.getChildren()!=null){
						Node[] nodeList = bullet.getChildren().toNodeArray();

						// find header, find column
						boolean contactorHeaderFound = false;
						for (int i = 0; i < nodeList.length; i++) {
							Node current = nodeList[i];
							if (!contactorHeaderFound && current instanceof TextNode) {
								TextNode definitionListBullet = (TextNode) current;
								String tdConent = definitionListBullet.getText();
								// found!!!!!!
								if (tdConent.trim().contains("联系电话")) {
									contactorHeaderFound = true;
								}
							}
							
							// find his name after title found!!
							if (contactorHeaderFound) {
								if(nodeList.length>=i+2){
									Node imgCurrent = nodeList[i+1];
									if (imgCurrent instanceof ImageTag) {
										ImageTag imageTag = (ImageTag) imgCurrent;
										if("absmiddle".equals(imageTag.getAttribute("align"))){
											contactorsPhoneImgSrcBuilder.append(imageTag.getImageURL());
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
			});

		} catch (ParserException e) {
			e.printStackTrace();
		}

		return contactorsPhoneImgSrcBuilder.toString();

	}

	

	public String findContactorName(String detailPageHtml) {
		final StringBuilder contactorsBuilder = new StringBuilder();

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);
			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Bullet) {
						Bullet bullet = (Bullet) node;
						if(bullet.getChildren()!=null){
						Node[] nodeList = bullet.getChildren().toNodeArray();

						// find header, find column
						boolean contactorHeaderFound = false;
						for (int i = 0; i < nodeList.length; i++) {
							Node current = nodeList[i];
							
							if (!contactorHeaderFound && current instanceof TextNode) {
								TextNode definitionListBullet = (TextNode) current;
								String tdConent = definitionListBullet.getText();
								// found!!!!!!
								if (tdConent.trim().contains("联系人")) {
									contactorHeaderFound = true;
								}
							}
							
							// find his name after title found!!
							if (contactorHeaderFound) {
								if(nodeList.length>=(3+i)){
									Node contactorCurrent = nodeList[i+2];
									if (contactorCurrent instanceof TextNode) {
										TextNode contactorTag = (TextNode) contactorCurrent;
											contactorsBuilder.append(contactorTag.getText());
										return true;
									}
								}else{
									String text = current.getText();
									if(text!=null){
										String nText = text.trim().replace("联系人：", "");
										contactorsBuilder.append(nText);
									}
									return true;
								}
							}

						}
					}
					}
					return false;
				}
			});

		} catch (ParserException e) {
			e.printStackTrace();
		}

		return contactorsBuilder.toString();
	}

	

	
	public String findCompanyEmployeeCount(String detailPageHtml) {
		final StringBuilder contactorsBuilder = new StringBuilder();

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);
			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Bullet) {
						Bullet bullet = (Bullet) node;
						if(bullet.getChildren()!=null){
						Node[] nodeList = bullet.getChildren().toNodeArray();

						// find header, find column
						boolean contactorHeaderFound = false;
						for (int i = 0; i < nodeList.length; i++) {
							Node current = nodeList[i];
							
							if (!contactorHeaderFound && current instanceof TextNode) {
								TextNode definitionListBullet = (TextNode) current;
								String tdConent = definitionListBullet.getText();
								// found!!!!!!
								if (tdConent.trim().contains("公司规模")) {
									contactorHeaderFound = true;
								}
							}
							
							// find his name after title found!!
							if (contactorHeaderFound) {
								if(nodeList.length>=(2+i)){
									Node contactorCurrent = nodeList[i+1];
									if (contactorCurrent instanceof TextNode) {
										TextNode contactorTag = (TextNode) contactorCurrent;
											contactorsBuilder.append(contactorTag.getText());
										return true;
									}
								}else{
									String text = current.getText();
									if(text!=null){
										String nText = text.trim().replace("公司规模：", "");
										contactorsBuilder.append(nText);
									}
									return true;
								}
							}

						}
					}
					}
					return false;
				}
			});

		} catch (ParserException e) {
			e.printStackTrace();
		}

		return contactorsBuilder.toString();
	}
	
	public String findCompanyDescription(String html) {
		 Node node = findNodeById(html,"company_description");
		 if(node != null && node instanceof ParagraphTag){
			 ParagraphTag pt = (ParagraphTag)node;
			 return pt.getStringText();
		 }
		 return null;
	}


}
