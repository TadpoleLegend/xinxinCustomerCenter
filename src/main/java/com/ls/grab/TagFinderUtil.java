package com.ls.grab;

import org.htmlparser.Tag;
import org.htmlparser.tags.Span;


public class TagFinderUtil {
	
	public static boolean findCompanyLink(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("a") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("fl");
		
		return find;
	}
	
	public static boolean findNextButtonLink(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("a") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("next");
		
		return find;
	}
	public static boolean findCompanyName(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("div") && tag.getAttribute("class") != null && tag.getAttribute("class").equals("company");
		
		return find;
	}
	public static boolean findCompanyForGanji(Tag tag) {
		
		boolean find = tag.getTagName().equalsIgnoreCase("dd") && "company".equals(tag.getAttribute("class"));
		
		return find;
	}
	public static boolean findCompanyFor138(Tag tag) {
		
		//boolean find = tag.getTagName().equalsIgnoreCase("span") && "company".equals(tag.getAttribute("c"));
		boolean find = (tag instanceof Span) && "c".equals(tag.getAttribute("class"));
		return find;
	}
}
