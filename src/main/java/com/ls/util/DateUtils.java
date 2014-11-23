package com.ls.util;

import java.util.Date;

public class DateUtils {
	public static int minusDate(Date date1,Date date2){
		try{
			return (int)(date1.getTime()-date2.getTime())/(3600*24*1000);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		return 0;
	}
}
