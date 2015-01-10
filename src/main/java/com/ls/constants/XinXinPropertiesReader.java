package com.ls.constants;

import java.io.IOException;
import java.util.Properties;


public class XinXinPropertiesReader {
	
	public static Properties hanthinkProperties;
	public static final String fileName = "xinxin.properties";
	
	public static String getString(String key) {
		
		if (hanthinkProperties == null) {
			hanthinkProperties = new Properties();
			try {
				hanthinkProperties.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName));
			} catch (IOException e) {
				return null;
			}
		} 
		
		return hanthinkProperties.getProperty(key);
	}

}
