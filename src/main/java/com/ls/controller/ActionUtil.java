package com.ls.controller;

import java.util.Map;

public class ActionUtil {


	public static String getReqParaByMap(Map<String, String[]> map, String name) {

		if (map != null && map.size() > 0) {
			if (name != null && name.length() > 0) {
				if (map.get(name) != null && map.get(name).length > 0) {
					return map.get(name)[0];
				}
			}
		}
		return null;
	}

	public static String[] getReqsParaByMap(Map<String, String[]> map, String name) {

		if (map != null && map.size() > 0) {
			if (name != null && name.length() > 0) {
				if (map.get(name) != null && map.get(name).length > 0) {
					return map.get(name);
				}
			}
		}
		return null;
	}

}
