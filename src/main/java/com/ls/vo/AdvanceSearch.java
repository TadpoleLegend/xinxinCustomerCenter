package com.ls.vo;

import org.apache.commons.lang.StringUtils;


public class AdvanceSearch {

	private String appointStartDate;
	private String appointEndDate;
	private String birthdayType;
	private String birthDayValue;
	private String phase; 
	
	public String getAppointStartDate() {
	
		return appointStartDate;
	}
	
	public void setAppointStartDate(String appointStartDate) {
	
		this.appointStartDate = appointStartDate;
	}
	
	public String getAppointEndDate() {
	
		return appointEndDate;
	}
	
	public void setAppointEndDate(String appointEndDate) {
	
		this.appointEndDate = appointEndDate;
	}
	
	public String getBirthdayType() {
	
		return birthdayType;
	}
	
	public void setBirthdayType(String birthdayType) {
	
		this.birthdayType = birthdayType;
	}
	
	public String getBirthDayValue() {
	
		return birthDayValue;
	}
	
	public void setBirthDayValue(String birthDayValue) {
	
		this.birthDayValue = birthDayValue;
	}

	
	public String getPhase() {
	
		return phase;
	}

	
	public void setPhase(String phase) {
	
		this.phase = phase;
	}
	
	
	public boolean isEverythingBlank() {
		
		if (StringUtils.isEmpty(appointStartDate) && StringUtils.isEmpty(appointEndDate) && StringUtils.isEmpty(birthDayValue) && StringUtils.isEmpty(phase)) {
			return true;
		}
		
		return false;
	}
	
}
