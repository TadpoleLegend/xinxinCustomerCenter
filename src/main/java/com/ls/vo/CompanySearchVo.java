package com.ls.vo;

public class CompanySearchVo {
	private String companyNameParam;
	private String contactorParam;
	private String starParam;
	private String allStarCheckboxParam;
	private String distinctParam;
	private String pageNumber;
	private String cityId;
	private String provinceId;
	private String starLevelOperator;
	private String searchId;
	private String customerStatus;
	private String selectedProblemCategory;
	private String selectedDataSourceType;
	
	private AdvanceSearch advanceSearch;
	
	public AdvanceSearch getAdvanceSearch() {
	
		return advanceSearch;
	}
	
	public void setAdvanceSearch(AdvanceSearch advanceSearch) {
	
		this.advanceSearch = advanceSearch;
	}

	public String getSelectedProblemCategory() {
	
		return selectedProblemCategory;
	}
	
	public String getSelectedDataSourceType() {
		return selectedDataSourceType;
	}

	public void setSelectedDataSourceType(String selectedDataSourceType) {
		this.selectedDataSourceType = selectedDataSourceType;
	}

	public void setSelectedProblemCategory(String selectedProblemCategory) {
	
		this.selectedProblemCategory = selectedProblemCategory;
	}
	public String getStarLevelOperator() {
		return starLevelOperator;
	}
	public void setStarLevelOperator(String starLevelOperator) {
		this.starLevelOperator = starLevelOperator;
	}
	public String getCompanyNameParam() {
		return companyNameParam;
	}
	public void setCompanyNameParam(String companyNameParam) {
		this.companyNameParam = companyNameParam;
	}
	public String getContactorParam() {
		return contactorParam;
	}
	public void setContactorParam(String contactorParam) {
		this.contactorParam = contactorParam;
	}
	public String getStarParam() {
		return starParam;
	}
	public void setStarParam(String starParam) {
		this.starParam = starParam;
	}
	public String getAllStarCheckboxParam() {
		return allStarCheckboxParam;
	}
	public void setAllStarCheckboxParam(String allStarCheckboxParam) {
		this.allStarCheckboxParam = allStarCheckboxParam;
	}
	public String getDistinctParam() {
		return distinctParam;
	}
	public void setDistinctParam(String distinctParam) {
		this.distinctParam = distinctParam;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
	public String getCityId() {
		return cityId;
	}
	public void setCityId(String cityId) {
		this.cityId = cityId;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getSearchId() {
		return searchId;
	}
	public void setSearchId(String searchId) {
		this.searchId = searchId;
	}
	public String getCustomerStatus() {
		return customerStatus;
	}
	public void setCustomerStatus(String customerStatus) {
		this.customerStatus = customerStatus;
	}
	
}
