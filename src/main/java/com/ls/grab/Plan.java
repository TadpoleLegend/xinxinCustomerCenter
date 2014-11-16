package com.ls.grab;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Plan {

	private static final Properties PLAN_PROPERTIES = new Properties();

	static {
		try {
			PLAN_PROPERTIES.load(new FileInputStream(new File("/plan.properties")));

		} catch (FileNotFoundException e) {
		} catch (IOException e) {
		}
	}

	public static String getProperty(String key) {
		return PLAN_PROPERTIES.getProperty(key);
	}
	
	public static void main(String[] args) {
		System.out.println(getProperty("58Plan"));
	}
}
