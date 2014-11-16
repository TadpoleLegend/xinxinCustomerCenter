package com.ls.grab;

import org.htmlparser.Tag;


public class TagFinderUtil {
	
	public static boolean findCompanyLink(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("a") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("fl");
		
		return find;
	}

	public static boolean findCompanyName(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("div") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("company");
		
		return find;
	}
}
