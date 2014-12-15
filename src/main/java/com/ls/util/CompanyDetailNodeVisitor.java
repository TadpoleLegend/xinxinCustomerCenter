package com.ls.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.tags.Div;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.Span;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.visitors.NodeVisitor;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.google.common.base.Joiner;
import com.ls.vo.CompanyDetailVo;

public class CompanyDetailNodeVisitor extends NodeVisitor {
	
	private CompanyDetailVo companyDetailVo = new CompanyDetailVo();

	@Override
	public void visitTag(Tag tag) {

		super.visitTag(tag);
		
		fillCompanyName(tag);
		
		fillContactorPhoneNumberImgSrc(tag);
		
		fillContactorName(tag);
		
		fillContactorEmailImgSrc(tag);
		
		fillCompanyDescription(tag);

	}

	public void fillContactorPhoneNumberImgSrc(Tag node) {

		if (node instanceof TableRow) {

			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();

			for (int i = 0; i < nodeList.length; i++) {
				
				Node current = nodeList[i];
				boolean phoneFound = false;
				if (!phoneFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("联系电话")) {
						phoneFound = true;
					}
				}
				
				if (phoneFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					if (td.getAttribute("class") != null && td.getAttribute("class").equals("telNum")) {
						Node[] list = td.getChildren().toNodeArray();
						boolean cellPhoneFound = false;
						for (Node img : list) {
							if (img instanceof ImageTag) {

								ImageTag imageTag = (ImageTag) img;

								if (!cellPhoneFound) {
									companyDetailVo.setContactorCellPhoneImageSrc(imageTag.getImageURL());
									cellPhoneFound = true;
									continue;
								}

								companyDetailVo.setContactorFixPhoneImageSrc(imageTag.getImageURL());
							}
						}
					}
				}
			}
		}
	}

	public void fillContactorName(Tag node) {

		if (node instanceof TableRow) {

			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();

			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("联系人")) {
						contactorHeaderFound = true;
					}
				}

				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					companyDetailVo.setContactor(td.getStringText().trim());
				}
			}
		}
	}

	public void fillContactorEmailImgSrc(Tag node) {

		if (node instanceof TableRow) {

			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();

			boolean contactorHeaderFound = false;

			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("邮箱")) {
						contactorHeaderFound = true;
					}
				}

				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					Node[] list = td.getChildren().toNodeArray();

					for (Node img : list) {
						if (img instanceof ImageTag) {
							ImageTag imageTag = (ImageTag) img;
							companyDetailVo.setEmailImageSrc(imageTag.getImageURL());
						}
					}
				}

			}
		}
	}

	public void findCompanyAddress(Tag node) {

		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();
			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("公司地址")) {
						contactorHeaderFound = true;
					}
				}

				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					if (td.getAttribute("class") != null && td.getAttribute("class").equals("adress")) {
						Node[] list = td.getChildren().toNodeArray();
						for (Node span : list) {
							if (span instanceof Span) {
								Span spanTag = (Span) span;
								companyDetailVo.setAddress(spanTag.getStringText().trim());
							}
						}
					}
				}
			}
		}
	}

	public void findCompanyEmployeeCount(Tag node) {
		if (node instanceof TableRow) {
			TableRow row = (TableRow) node;
			Node[] nodeList = row.getChildren().toNodeArray();

			boolean contactorHeaderFound = false;
			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader) current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("公司规模")) {
						contactorHeaderFound = true;
					}
				}

				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn) current;
					companyDetailVo.setScale(td.getStringText().trim());
				}
			}
		}

	}

	public void fillCompanyDescription(Tag node) {
		
		if (node instanceof Div && StringUtils.isNotBlank(((Div) node).getAttribute("class")) && 
				((Div) node).getAttribute("class").equalsIgnoreCase("compIntro")) {
			
			try {
				Div descriptionDiv = (Div) node;
				
				Node[] descriptionNodes = descriptionDiv.getChildrenAsNodeArray();
				List<String> stringTexts = new ArrayList<String>();
				for (Node singleNode : descriptionNodes) {
					stringTexts.add(singleNode.getText());
				}
				companyDetailVo.setAddress(Joiner.on(" ").skipNulls().join(stringTexts));
				
			} catch (FailingHttpStatusCodeException e) {
				companyDetailVo.setAddress("");
			} catch (Exception e) {
				companyDetailVo.setAddress("");
			}
		}
		
	}
	
	public void fillCompanyName(Tag node) {
		if (node instanceof Div && StringUtils.isNotBlank(((Div) node).getAttribute("class")) && 
				((Div) node).getAttribute("class").equalsIgnoreCase("compT")) { 
			Div nameDiv = (Div) node;
			
			try {
				
				Node[] descriptionNodes = nameDiv.getChildrenAsNodeArray();
				//companyDetailVo.setName(singleNode.getText());
				
				for (Node divChildNode : descriptionNodes) {
					
					//if (divChildNode instanceof Headling) {
						
					//}
					//System.out.println(node2.getText());
				}
				
			} catch (FailingHttpStatusCodeException e) {
			} catch (Exception e) {
			}
		}
		
	}

	public CompanyDetailVo getCompanyDetailVo() {

		return companyDetailVo;
	}
}
