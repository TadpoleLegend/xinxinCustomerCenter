package com.ls.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesUtil {
	public static String getProperty(String key,String file){
		if (file == null || file.trim().length()==0) return null;
		Properties pt = new Properties();
		InputStream is = null;
		try {
			is = PropertiesUtil.class.getClassLoader().getResourceAsStream(file);
			pt.load(is);
			return pt.getProperty(key);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(is!=null){
				try{
					is.close();
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}
