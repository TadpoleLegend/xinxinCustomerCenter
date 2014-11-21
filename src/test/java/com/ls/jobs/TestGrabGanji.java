package com.ls.jobs;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.ls.entity.Company;
import com.ls.grab.HtmlParserUtilForGanJi;
import com.ls.grab.LocationUtil;

public class TestGrabGanji {
	public static void main(String[] args) {
		try {
			Map<String, Map<String, String>> provinces = LocationUtil.getInstance().findGanjiCities();
			if (!provinces.isEmpty()) {
				for (Entry<String, Map<String, String>> et : provinces
						.entrySet()) {
					Map<String, String> map = et.getValue();
					for (Entry<String, String> city : map.entrySet()) {
						String url = city.getValue() + "meirongshi/o1/";
						System.out.println(url);
						List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(url);
						for (Company c : companiesInThisPage) {
							System.err.println(c.getName());
							System.err.println(c.getArea());
							System.err.println(c.getAddress());
							System.err.println(c.getContactor());
							System.err.println(c.getEmployeeCount());
							System.err.println(c.getfEurl());
							System.err.println(c.getDescription());
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
