package com.ls.grab;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.nodes.TagNode;
import org.htmlparser.tags.Div;
import org.htmlparser.util.ParserException;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Splitter;
import com.ls.entity.Company;

public class BaseHtmlParseUtil {
	
	public static Div findFirstOneWithClassName(String html, final String className) {
		Node[] nodes = null;
		try {
			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);
			
			nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {
				
				public boolean accept(Node node) {
					if (node instanceof Div && StringUtils.isNotBlank(((Div) node).getAttribute("class")) && ((Div) node).getAttribute("class").equalsIgnoreCase(className)) {
						return true;
					}
					return false;
				}
			}).toNodeArray();
			
		} catch (ParserException e) {
			return null;
		}
		
		if (nodes != null && nodes.length > 0) {
			return (Div) nodes[0];
		}
		
		return null;
	}
	
	public Node findNodeById(String html, final String divId) {

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
	public static  Node findNodeByClassId(String html, final String classId) {

		try {

			Parser htmlParser = new Parser();
			htmlParser.setInputHTML(html);

			Node[] nodes = htmlParser.extractAllNodesThatMatch(new NodeFilter() {

				public boolean accept(Node node) {

					if (node instanceof TagNode) {
						TagNode tag = (TagNode) node;

						String id = StringUtils.trimToEmpty(tag.getAttribute("class"));

						if (StringUtils.isNotBlank(id) && classId.equals(id)) {
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
	
	public void compositeCityAndProvince(HtmlPage mainPage, Company company) {

		try {
			String locationMeta = mainPage.getElementsByName("location").get(0).getAttribute("content");
			Iterable<String> locationElements = Splitter.on(";").omitEmptyStrings().split(locationMeta);

			for (String string : locationElements) {
				if (string.contains("=")) {

					String[] locationDetailElements = string.split("=");
					String locationKey = locationDetailElements[0];
					if (locationDetailElements.length == 2 && locationKey.equals("city")) {
						String locationValue = locationDetailElements[1];
						company.setCityName(locationValue);
					}
				}
			}
		} catch (Exception e) {

		}
	}
}
