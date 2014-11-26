package com.ls.constants;

import java.text.SimpleDateFormat;


public class XinXinConstants {
	
	public static final String CURRENT_USER = "CURRENT_USER";
	
	enum Problem_Category {
		CUSTOMER_PROBLEM
	}
	public static final String FULL_DATE_FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";
	
	public static final String SIMPLE_DATE_FORMAT_STRING = "yyyy-MM-dd";

	public static final SimpleDateFormat FULL_DATE_FORMATTER = new SimpleDateFormat(FULL_DATE_FORMAT_STRING);
	
	public static final SimpleDateFormat SIMPLE_DATE_FORMATTER = new SimpleDateFormat(FULL_DATE_FORMAT_STRING);
	
	public static final String COMPANY_TYPE = "company_type";
}
