package com.ls.util;

import net.sf.json.JSONObject;


public class XinXinUtils {

	public static <T> T getJavaObjectFromJsonString(String jsonString, Class<T> classType) {

		@SuppressWarnings("unchecked")
		T javaObject = (T) JSONObject.toBean(JSONObject.fromObject(jsonString),classType);

		return javaObject;
	}
}
