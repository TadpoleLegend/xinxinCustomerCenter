package com.ls.util;

import org.htmlparser.Node;
import org.htmlparser.Tag;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.TableColumn;
import org.htmlparser.tags.TableHeader;
import org.htmlparser.tags.TableRow;
import org.htmlparser.visitors.NodeVisitor;

import com.ls.vo.CompanyDetailVo;

public class CompanyDetailNodeVisitor extends NodeVisitor {

	private CompanyDetailVo companyDetailVo = new CompanyDetailVo();

	@Override
	public void visitTag(Tag tag) {

		super.visitTag(tag);

		fillContactorPhoneNumberImgSrc(tag);

	}

	public void fillContactorPhoneNumberImgSrc(Tag node) {

		if (node instanceof TableRow) {
			
			TableRow row = (TableRow)node;
			Node[] nodeList = row.getChildren().toNodeArray();

			boolean contactorHeaderFound = false;

			for (int i = 0; i < nodeList.length; i++) {
				Node current = nodeList[i];
				if (!contactorHeaderFound && current instanceof TableHeader) {
					TableHeader th = (TableHeader)current;
					String tdConent = th.getStringText();
					if (tdConent.trim().contains("联系电话")) {
						contactorHeaderFound = true;
					}
				}

				if (contactorHeaderFound && current instanceof TableColumn) {
					TableColumn td = (org.htmlparser.tags.TableColumn)current;
					if (td.getAttribute("class") != null && td.getAttribute("class").equals("telNum")) {
						Node[] list = td.getChildren().toNodeArray();
						boolean cellPhoneFound = false;
						for (Node img : list) {
							if (img instanceof ImageTag) {
								
								ImageTag imageTag = (ImageTag)img;
								
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

	public CompanyDetailVo getCompanyDetailVo() {

		return companyDetailVo;
	}
}
