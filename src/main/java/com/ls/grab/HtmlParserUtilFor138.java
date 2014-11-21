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
import org.htmlparser.tags.ScriptTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableRow;
import org.htmlparser.tags.TableTag;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.ls.entity.Company;

public class HtmlParserUtilFor138 extends BaseHtmlParseUtil {

	
	private static  HtmlParserUtilFor138 htmlParserUtilFor138;
	private HtmlParserUtilFor138(){}
	private static String address = "http://www.138job.com/AjaxMethods/AjaxLoadContacts.aspx?action=companycontacts&hideEmailTxt=1&comId=";
	
	private static final WebClient webClient;
	static{
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
//		login138();
	}
	
	public static HtmlParserUtilFor138 getInstance(){
		if(htmlParserUtilFor138 == null){
			return new HtmlParserUtilFor138();
		}else{
			return htmlParserUtilFor138;
		}
	}
	
	public static void login138(){
		try {
			String url = "http://cas.138mr.com/login";

			webClient.getOptions().setJavaScriptEnabled(false);
			// Get the first page
			final HtmlPage loginPage = webClient.getPage(url);

			// Get the form that we are dealing with and within that form,
			// find the submit button and the field that we want to change.
			final List<HtmlForm> forms = loginPage.getForms();

			HtmlForm form = null;
			for (HtmlForm singleForm : forms) {
				if (singleForm.getAttribute("id").equals("formlogin")) {
					form = singleForm;
					break;
				}
			}

			final HtmlSubmitInput loginButton = form.getInputByName("btnLogin");
			final HtmlTextInput textField = form.getInputByName("txbUserName");
			final HtmlPasswordInput passwordField = form.getInputByName("txbUserPwd");

			// Change the value of the text field
			textField.setValueAttribute("liu_online@163.com");
			passwordField.setValueAttribute("789321");
			
			// click login button
			loginButton.click();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	public static String todayStr = sf.format(Calendar.getInstance().getTime());
	
	
	public List<Company> findPagedCompanyList(String url) {

		final List<Company> companyList = new ArrayList<Company>();
//		login138();
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
						company.setOteUrl(lt.getAttribute("href"));
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
						String htmlForPage = null;
						try {
							htmlForPage = webClient.getPage(company.getOteUrl()).getWebResponse().getContentAsString();
						} catch (FailingHttpStatusCodeException e1) {
							e1.printStackTrace();
						} catch (MalformedURLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						company.setDescription(findCompanyDescription(htmlForPage));
						company.setEmployeeCount(findCompanyEmployeeCount(htmlForPage));
						String testURL = company.getOteUrl();
						try {
							String id = getCompanyResourceId(webClient.getPage(testURL).getWebResponse().getContentAsString());
//							company.setResourceId(id);
							String contactDiv =  getContactDiv(id);
							parseContactDivForTelAndMobile(contactDiv,company);
							parseContactDivForContactPerson(contactDiv,company);
							parseContactDivForAddress(contactDiv,company);
						} catch (FailingHttpStatusCodeException e) {
							e.printStackTrace();
						} catch (MalformedURLException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
						StringBuilder sb = new StringBuilder();
						sb.append(company.getName()).append("--").append(company.getArea()).append("---").append(company.getAddress()).append("---").append(company.getPublishDate()).append("----").append(company.getContactor());
						System.err.println(sb.toString());
						System.err.println(company.getMobilePhoneSrc());
						System.err.println(company.getPhoneSrc());
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
	
	private String parseContactDivForAddress(String detailPageHtml,final Company company){


		final StringBuffer id = new StringBuffer();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof TableTag) {
						TableTag tableTag = (TableTag)node;
						Node [] nodes = tableTag.getChildrenAsNodeArray();
						if(nodes!=null){
							for(int i=0;i<nodes.length;i++){
								if(nodes[i] instanceof TableRow){
									TableRow tr = (TableRow)nodes[i];
									Node [] trNodes = tr.getChildrenAsNodeArray();
									if(trNodes!=null){
											if(trNodes[0] instanceof TableColumn){
												TableColumn td = (TableColumn)trNodes[0];
												if(td.getStringText()!=null && td.getStringText().contains("联系地址")){
													TableColumn tdDiv = (TableColumn)trNodes[1];
													Node tdnodes [] = tdDiv.getChildrenAsNodeArray();
													if(tdnodes!=null){
														if(tdnodes.length==1){
															company.setAddress(tdDiv.getStringText().replace("&nbsp;", ""));
														}else if(tdnodes.length>1){
															if(tdnodes[0] instanceof TextNode){
																TextNode tn = (TextNode)tdnodes[0];
																company.setAddress(tn.getText().replace("&nbsp;", ""));
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
					}
					return false;
				}
			});
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return id.toString();
	
	
	}
	
	private String parseContactDivForContactPerson(String detailPageHtml,final Company company){


		final StringBuffer id = new StringBuffer();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof TableTag) {
						TableTag tableTag = (TableTag)node;
						Node [] nodes = tableTag.getChildrenAsNodeArray();
						if(nodes!=null){
							for(int i=0;i<nodes.length;i++){
								if(nodes[i] instanceof TableRow){
									TableRow tr = (TableRow)nodes[i];
									Node [] trNodes = tr.getChildrenAsNodeArray();
									if(trNodes!=null){
											if(trNodes[0] instanceof TableColumn){
												TableColumn td = (TableColumn)trNodes[0];
												if(td.getStringText()!=null && td.getStringText().contains("面试联系人")){
													TableColumn tdDiv = (TableColumn)trNodes[1];
													String text = tdDiv.getStringText();
													if(text!=null && text.contains("(联系我时请说明")){
														company.setContactor(text.substring(0,text.trim().indexOf("(联系我时请说明")));
														return true;
													}
													return true;
													
												}
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
		return id.toString();
	
	
	}
	
	private String parseContactDivForTelAndMobile(String detailPageHtml,final Company company){

		final StringBuffer id = new StringBuffer();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof TableTag) {
						TableTag tableTag = (TableTag)node;
						Node [] nodes = tableTag.getChildrenAsNodeArray();
						if(nodes!=null){
							for(int i=0;i<nodes.length;i++){
								if(nodes[i] instanceof TableRow){
									//TableRow tr = (TableRow)nodes[0];
									TableRow tr = (TableRow)nodes[i];
									Node [] trNodes = tr.getChildrenAsNodeArray();
									if(trNodes!=null){
											if(trNodes[0] instanceof TableColumn){
												TableColumn td = (TableColumn)trNodes[0];
												if(td.getStringText()!=null && td.getStringText().contains("预约面试电话")){
													TableColumn tdDiv = (TableColumn)trNodes[1];
													Node spanNode = findNodeById(tdDiv.getStringText(),"tel_2");
													if(spanNode instanceof Span){
														Span span = (Span) spanNode;
														Node [] spanNodes = span.getChildrenAsNodeArray();
														if(spanNodes!=null){
															if(spanNodes[0] instanceof ImageTag){
																ImageTag mobile = (ImageTag)spanNodes[0];
																company.setMobilePhoneSrc(mobile.getAttribute("src"));
															}
															if(spanNodes.length>2){
															for(int n=1;n<spanNodes.length;n++){
															if(spanNodes[n] instanceof ImageTag){
																ImageTag tel = (ImageTag)spanNodes[n];
																company.setPhoneSrc(tel.getAttribute("src"));
															}
															}
															}
															return true;
															
														}
													}
													return true;
												}
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
		return id.toString();
	
	}
	
	
	private String getContactDiv(String resourceId){
		try {
			StringBuilder sb = new StringBuilder(address);
			sb.append(resourceId).append("&t=").append(Calendar.getInstance().getTimeInMillis());
			return webClient.getPage(sb.toString()).getWebResponse().getContentAsString();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	private String getCompanyResourceId(String detailPageHtml){
		final StringBuffer id = new StringBuffer();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof ScriptTag) {
						ScriptTag scriptTag = (ScriptTag)node;
						if(scriptTag.getStringText()!=null&&scriptTag.getStringText().trim().contains("var cid=")){
							String text = scriptTag.getStringText();
							int beginIndex = text.indexOf("=")+1;
							int endIndex = text.indexOf(";");
							id.append(text.substring(beginIndex,endIndex));
							return true;
						}
					}
					return false;
				}
			});
		} catch (ParserException e) {
			e.printStackTrace();
		}
		return id.toString();
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
