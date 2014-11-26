package com.ls.grab;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.tags.DefinitionListBullet;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.visitors.NodeVisitor;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.ls.entity.Company;
import com.ls.enums.ResourceTypeEnum;
import com.ls.util.XinXinUtils;

public class HtmlParserUtilFor58 extends BaseHtmlParseUtil {


	private static  HtmlParserUtilFor58 htmlParserUtilFor58;
	private HtmlParserUtilFor58(){}
	private static final WebClient webClient;
	static{
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.getOptions().setJavaScriptEnabled(false);
	}
	
	public static HtmlParserUtilFor58 getInstance(){
		if(htmlParserUtilFor58 == null){
			return new HtmlParserUtilFor58();
		}else{
			return htmlParserUtilFor58;
		}
	}
	
	
	
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

					if (TagFinderUtil.findCompanyLink(tag)) {
						LinkTag linkTag = (LinkTag) tag;
						Company company = new Company();
						company.setName(StringUtils.trimToEmpty(tag.getAttribute("title")));
						company.setfEurl(tag.getAttribute("href"));
						
						Node nodeLink = linkTag.getParent().getParent();
						
						Node[] nodes = nodeLink.getChildren().toNodeArray();
						
						for (int i = 0; i < nodes.length; i++) {
							Node node = nodes[i];
							
							if (node instanceof DefinitionListBullet) {
								
								DefinitionListBullet nodeTranslated = (DefinitionListBullet) node;
								String className = nodeTranslated.getAttribute("class");
								if (className!= null && className.equals("w96")) {
									company.setArea(nodeTranslated.getStringText());
								}
								if (className!= null && className.equals("w68")) {
									company.setPublishDate(nodeTranslated.getStringText());
								}
							}
							
						}
						String testURL = company.getfEurl();
						int index = testURL.indexOf("58.com");
						if(index!=-1){
							String sub = testURL.substring(index+7);
							int sIndex = sub.indexOf("/");
							if(sIndex!=-1){
									company.setfEresourceId(sub.substring(0,sIndex));
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
		
		Map<String,String> map = XinXinUtils.mergeDuplicateCompanyInOnePage(companyList,ResourceTypeEnum.FiveEight.getId());
		List<Company> returnCompanyList = reduceDuplicateCompany(companyList,map);
		return returnCompanyList;
	}

	
	private List<Company> reduceDuplicateCompany( List<Company> companyList,Map<String,String> map){
		List<Company> returnCompanyList = new ArrayList<Company>();
		for(Company company:companyList){
			if(map.containsKey(company.getfEresourceId())){
				try {
					String testURL = company.getfEurl();
					HtmlPage mainPage = webClient.getPage(testURL);
					String htmlDetail = mainPage.getWebResponse().getContentAsString();
					findCompanyDetails(company,htmlDetail);
					company.setDescription(findCompanyDescription(htmlDetail));
					map.remove(company.getfEresourceId());
					returnCompanyList.add(company);
				} catch (FailingHttpStatusCodeException e) {
					e.printStackTrace();
				}  catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		return returnCompanyList;
	}
	
	
	public String findCompanyDetails(final Company company,String detailPageHtml) {
		final StringBuilder contactorsPhoneImgSrcBuilder = new StringBuilder();
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;
				boolean phoneNum = false;
				boolean emailSrc = false;
				boolean contactor = false;
				boolean companyAddress = false;
				boolean employeecount = false;
				public boolean accept(Node node) {
					if(!phoneNum){
						phoneNum = findContactorPhoneNumberImgSrc(company,node);
					}
					if(!emailSrc){
						emailSrc = findContactorEmailImgSrc(company,node);
					}
					if(!contactor){
						contactor = findContactorName(company,node);
					}
					if(!companyAddress){
						companyAddress = findCompanyAddress(company,node);
					}
					if(!employeecount){
						employeecount = findCompanyEmployeeCount(company,node);
					}
					if(phoneNum && emailSrc && contactor && companyAddress && employeecount){
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
	
	
	public boolean findContactorPhoneNumberImgSrc(Company company,Node node){
		try{
			if (node instanceof TableRow) {
				TableRow row = (TableRow) node;
				Node[] nodeList = row.getChildren().toNodeArray();
				// find header, find column
				boolean contactorHeaderFound = false;
				for (int i = 0; i < nodeList.length; i++) {
					Node current = nodeList[i];
					// find title hardly
					if (!contactorHeaderFound && current instanceof TableHeader) {
						TableHeader th = (TableHeader) current;
						String tdConent = th.getStringText();
						// found!!!!!!
						if (tdConent.trim().contains("联系电话")) {
							contactorHeaderFound = true;
						}
					}
					// find his name after title found!!
					if (contactorHeaderFound && current instanceof TableColumn) {
						TableColumn td = (org.htmlparser.tags.TableColumn) current;
						if (td.getAttribute("class") != null && td.getAttribute("class").equals("telNum")) {
							Node[] list = td.getChildren().toNodeArray();
							for (Node img : list) {
								if (img instanceof ImageTag) {
									ImageTag imageTag = (ImageTag) img;
									company.setPhoneSrc(imageTag.getImageURL());
									return true;
								}
							}
						}
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}finally{
			return false;
		}
	}
	
	
	public boolean findContactorEmailImgSrc(Company company,Node node){
		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("邮箱")) {
						contactorHeaderFound = true;
					}
				}
				// find his email after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					Node[] list = td.getChildren().toNodeArray();
					for (Node img : list) {
						if (img instanceof ImageTag) {
							ImageTag imageTag = (ImageTag) img;
							company.setEmailSrc(imageTag.getImageURL());
							return true;
						}
					}
				}

			}
		}
		return false;
	}
	
	
	public boolean findContactorName(Company company,Node node){
		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("联系人")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					company.setContactor(td.getStringText().trim());
					return true;
				}
			}
		}
		return false;
	}
	
	
	public boolean findCompanyAddress(Company company,Node node){
		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("公司地址")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					if (td.getAttribute("class") != null && td.getAttribute("class").equals("adress")) {
						Node[] list = td.getChildren().toNodeArray();
						for (Node span : list) {
							if (span instanceof Span) {
								Span spanTag = (Span) span;
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
	
	
	public boolean findCompanyEmployeeCount(Company company,Node node){
		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();
			// find header, find column
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				// find title hardly
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					// found!!!!!!
					if (tdConent.trim().contains("公司规模")) {
						contactorHeaderFound = true;
					}
				}
				// find his name after title found!!
				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
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
			return descriptionDiv == null ? "" : descriptionDiv.getStringText();
		} catch (FailingHttpStatusCodeException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
