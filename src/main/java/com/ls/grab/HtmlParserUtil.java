package com.ls.grab;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.ImageTag;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.LinkTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.ParserException;
import org.htmlparser.visitors.NodeVisitor;

import com.google.common.io.Files;
import com.ls.entity.Company;

public class HtmlParserUtil {

	public static void main(String[] args) {

		File file = new File("D:\\Jerry\\58.txt");

		try {
			String string = Files.toString(file, Charset.defaultCharset());

			System.out.println(findContactorName(string));

			System.out.println(findContactorPhoneNumberImgSrc(string));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static List<Company> findPagedCompanyList(String wholeCityPageHTML) {

		final List<Company> companyList = new ArrayList<Company>();

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(wholeCityPageHTML);

			NodeVisitor nodeVisitor = new NodeVisitor() {

				@Override
				public void visitTag(Tag tag) {

					super.visitTag(tag);

					if (TagFinderUtil.findCompanyLink(tag)) {
						Company company = new Company();
						company.setName(tag.getText());
						company.setfEurl(tag.getAttribute("href"));

						companyList.add(company);

					}
				}
			};

			htmlParser.visitAllNodesWith(nodeVisitor);

		} catch (ParserException e) {

			e.printStackTrace();
		}

		return companyList;
	}

	public static String findCompanyName(String detailPageHtml) {

		final StringBuilder comanyName = new StringBuilder();

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			NodeVisitor nodeVisitor = new NodeVisitor() {

				@Override
				public void visitTag(Tag tag) {

					super.visitTag(tag);

					if (TagFinderUtil.findCompanyName(tag)) {

						Node[] childList = tag.getChildren().toNodeArray();

						for (Node node : childList) {

							if (node instanceof LinkTag) {
								LinkTag nodeLink = (LinkTag) node;
								comanyName.append(StringUtils.trimToEmpty(nodeLink.getStringText()));
							}
						}
					}
				}
			};

			htmlParser.visitAllNodesWith(nodeVisitor);

		} catch (ParserException e) {

			e.printStackTrace();
		}

		return comanyName.toString();
	}

	public static String findContactorPhoneNumberImgSrc(String detailPageHtml) {
		String wholeUrl = null;

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Span) {
						Span div = (Span) node;
						String divId = StringUtils.trimToEmpty(div.getAttribute("id"));

						if (StringUtils.isNotBlank(divId) && divId.equals("ck2")) {
							return true;
						}
					}
					return false;
				}
			}).toNodeArray();

			if (null != nodes && nodes.length > 0) {
				Node foundNode = nodes[0];
				Node[] children = foundNode.getChildren().toNodeArray();
				for (int i = 0; i < children.length; i++) {
					Node node = children[i];

					if (node instanceof ImageTag) {
						String basicImgSrc = ((ImageTag) node).getAttribute("src");
						System.out.println(basicImgSrc);

						// <input id="pagenum"
						// value="C29C0040637C187E41C97E412398A6D8A"
						// type="hidden" />
						Node hiddenPageNumber = findNodeById(detailPageHtml, "pagenum");

						String hiddenPageNumberValue = null;
						if (hiddenPageNumber instanceof InputTag) {
							hiddenPageNumberValue = ((InputTag) hiddenPageNumber).getAttribute("value");
						}

						wholeUrl = basicImgSrc + hiddenPageNumberValue;
					}
				}
			}

		} catch (ParserException e) {

		}

		return wholeUrl;
	}

	public static String findContactorName(String detailPageHtml) {
		
		String wholeUrl = null;

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(detailPageHtml);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				private static final long serialVersionUID = -93037936232004146L;

				public boolean accept(Node node) {
					if (node instanceof Span) {
						Span div = (Span) node;
						String divId = StringUtils.trimToEmpty(div.getAttribute("id"));

						if (StringUtils.isNotBlank(divId) && divId.equals("ck2")) {
							return true;
						}
					}
					return false;
				}
			}).toNodeArray();

			if (null != nodes && nodes.length > 0) {
				Node foundNode = nodes[0];
				Node[] children = foundNode.getChildren().toNodeArray();
				for (int i = 0; i < children.length; i++) {
					Node node = children[i];

					if (node instanceof Span) {
						if (((Span) node).getAttribute("class").equals("conTip")) {
							String nameText = ((Span) node).getStringText().trim();
							
							String[] elements = nameText.split(" ");
							return elements[0];
						}
					}
				}
			}

		} catch (ParserException e) {

		}

		return wholeUrl;
	}

	public static Node findNodeById(String html, final String divId) {

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				public boolean accept(Node node) {
					
					if (node instanceof TagNode) {
						TagNode tag = (TagNode) node;

						String id = StringUtils.trimToEmpty(tag.getAttribute("id"));

						if (StringUtils.isNotBlank(id) && divId.equals(id)) {
							return true;
						}
					}
					return false;
				}
			}).toNodeArray();

			if (null != nodes && nodes.length > 0) {
				Node foundNode = nodes[0];
				return foundNode;
			}

		} catch (ParserException e) {

			e.printStackTrace();
		}

		return null;
	}
}
