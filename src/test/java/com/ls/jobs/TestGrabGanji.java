package com.ls.jobs;

import java.text.MessageFormat;
import java.util.List;

import com.ls.entity.Company;
import com.ls.grab.HtmlParserUtilForGanJi;

public class TestGrabGanji {
	public static void main(String []args){
		try {
			for(int i=1;i<2;i++){
			System.out.println("************************************************** page "+i+"***************************************begin");
			String testURL = "http://s.138job.com/hire/{0}?keyword=&workadd=1273&keywordtype=1&position=0";
			List<Company> companiesInThisPage = HtmlParserUtilForGanJi.getInstance().findPagedCompanyList(MessageFormat.format(testURL, i));
			System.out.println("************************************************** page "+i+"***************************************end");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
