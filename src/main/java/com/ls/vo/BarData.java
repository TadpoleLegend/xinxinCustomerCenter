package com.ls.vo;

import java.io.Serializable;


public class BarData implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String cityName;
	private Integer count;
	
	public String getCityName() {
	
		return cityName;
	}
	
	public void setCityName(String cityName) {
	
		this.cityName = cityName;
	}
	
	public Integer getCount() {
	
		return count;
	}
	
	public void setCount(Integer count) {
	
		this.count = count;
	}
	

}
