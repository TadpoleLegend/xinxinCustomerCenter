package com.ls.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.ls.constants.XinXinConstants;
import com.ls.entity.Company;
import com.ls.entity.CompanyAdditional;
import com.ls.entity.LearningHistory;
import com.ls.entity.Phase;
import com.ls.entity.PhoneCallHistory;
import com.ls.entity.Problem;
import com.ls.entity.User;
import com.ls.enums.CustomerStatusEnum;
import com.ls.repository.CompanyAdditionalRepository;
import com.ls.repository.CompanyRepository;
import com.ls.repository.LearningHistoryRepository;
import com.ls.repository.PhoneCallHistoryRepository;
import com.ls.repository.ProblemRepository;
import com.ls.service.CompanyService;
import com.ls.util.XinXinUtils;
import com.ls.vo.AdvanceSearch;
import com.ls.vo.CompanySearchVo;
import com.ls.vo.PagedElement;
import com.ls.vo.ResponseVo;

@Component("companyAction")
public class CompanyAction extends BaseAction {

	private static final long serialVersionUID = 7818205738152334717L;

	private List<Company> companies;

	private PagedElement<Company> company;

	private Company c;

	private CompanyAdditional companyAdditional;
	private List<Problem> problems;
	private PhoneCallHistory phoneCall;
	private List<PhoneCallHistory> historyRecords;
	private List<LearningHistory> learningHistories;
	
	private BarChartData barChartData;

	@Resource(name = "companyService")
	private CompanyService companyService;

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private CompanyAdditionalRepository companyAdditionalRepository;

	@Autowired
	private ProblemRepository problemRepository;

	@Autowired
	private PhoneCallHistoryRepository phoneCallHistoryRepository;

	@Autowired
	private LearningHistoryRepository learningHistoryRepository;

	public String checkOrUncheckProblem() {

		String companyJson = getParameter("companyJson");
		String problemJson = getParameter("problemJson");
		String checkFlag = getParameter("checkFlag");

		companyService.checkOrUncheckProblem(companyJson, problemJson, checkFlag);

		setResponse(ResponseVo.newSuccessMessage(null));

		return SUCCESS;
	}

	public String loadAllCompany() {

		String pageNumbersString = getParameter("pageNumber");
		if (null == pageNumbersString) {
			pageNumbersString = "1";
		}

		Integer pageNumber = Integer.valueOf(pageNumbersString);

		companies = companyService.findAllCompanies();

		return SUCCESS;
	}

	public String loadCompanyInPage() {

		try {
			String pageNumbersString = getParameter("pageNumber");
			if (null == pageNumbersString) {
				pageNumbersString = "1";
			}

			String companyNameParam = getParameter("seachCompany");
			String contactorParam = getParameter("searchContactor");
			String starParam = getParameter("starInput");
			String allStarCheckboxParam = getParameter("allStar");
			String distinctParam = getParameter("searchDistinct");
			String cityId = getParameter("cityId");
			String provinceId = getParameter("provinceId");
			String starLevelOperator = getParameter("starLevelOperator");
			String searchId = getParameter("searchId");
			String customerStatus = getParameter("customerStatus");
			String selectedProblemCategory = getParameter("selectedProblemCategory");
			String selectedDataSourceType = getParameter("selectedDataSourceType");

			AdvanceSearch advanceSearch = XinXinUtils.getJavaObjectFromJsonString(getParameter("advanceSearch"), AdvanceSearch.class);

			CompanySearchVo companySearchVo = new CompanySearchVo();
			companySearchVo.setCompanyNameParam(companyNameParam);
			companySearchVo.setContactorParam(contactorParam);
			companySearchVo.setStarParam(starParam);
			companySearchVo.setAllStarCheckboxParam(allStarCheckboxParam);
			companySearchVo.setDistinctParam(distinctParam);
			companySearchVo.setCityId(cityId);
			companySearchVo.setProvinceId(provinceId);
			companySearchVo.setPageNumber(pageNumbersString);
			companySearchVo.setStarLevelOperator(starLevelOperator);
			companySearchVo.setSearchId(searchId);
			companySearchVo.setCustomerStatus(customerStatus);
			companySearchVo.setSelectedProblemCategory(selectedProblemCategory);
			companySearchVo.setAdvanceSearch(advanceSearch);
			companySearchVo.setSelectedDataSourceType(selectedDataSourceType);
			
			Page<Company> result = companyService.getCompanyInPage(companySearchVo);

			company = new PagedElement<Company>(result);
		} catch (Exception e) {

			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			
			return ERROR;
			
		}

		return SUCCESS;
	}

	public String saveCompany() {

		String newCompanyJson = getParameter("newCompanyJson");
		String mannually = getParameter("mannually");
		Company company = XinXinUtils.getJavaObjectFromJsonString(newCompanyJson, Company.class);

		if (StringUtils.isNotBlank(mannually) && Boolean.valueOf(mannually)) {
			
			Integer currentUserId = commonService.getCurrentLoggedInUser().getId();
			
			company.setOwnerUserId(currentUserId);
		}

		// set defaults for new company
		if (company.getId() == null) {

			company.setGrabDate(XinXinConstants.FULL_DATE_FORMATTER.format(new Date()));
			company.setActive(true);
			company.setIsTracked(false);
			company.setStatus(CustomerStatusEnum.NO_WILLING_CUSTOMER.getId());
		}

		companyRepository.saveAndFlush(company);

		setResponse(XinXinUtils.makeGeneralSuccessResponse());

		return SUCCESS;
	}

	public String loadAddtionalCompanyInformation() {

		String companyId = getParameter("companyId");

		if (StringUtils.isBlank(companyId)) {
			return ERROR;

		} else {
			companyAdditional = companyService.findCompanyAddtionalInformationByCompanyId(Integer.valueOf(companyId));

			if (companyAdditional != null) {
				companyAdditional.setCompany(null);
			}
		}

		return SUCCESS;
	}

	public String saveAddtionalCompanyInformation() {

		String companyId = getParameter("company_id");
		// if (StringUtils.isEmpty(companyId)) {
		// return ERROR;
		// }

		Company company = companyRepository.findOne(Integer.valueOf(companyId));

		CompanyAdditional addition = XinXinUtils.getJavaObjectFromJsonString(getParameter("additionalCompanyInformation"), CompanyAdditional.class);

		addition.setCompany(company);

		companyAdditional = companyService.saveAdditionalCompanyInformation(addition);
		companyAdditional.setCompany(null);

		return SUCCESS;
	}

	public String changeStarLevel() {

		String companyId = getParameter("company_id");
		String star = getParameter("star");

		Company company = companyRepository.findOne(Integer.valueOf(companyId));
		company.setStar(Integer.valueOf(star));

		companyRepository.saveAndFlush(company);

		setResponse(ResponseVo.newSuccessMessage("200"));

		return SUCCESS;
	}

	public String loadCompanyProblems() {

		String companyId = getParameter("companyId");
		Company company = companyRepository.findOne(Integer.valueOf(companyId));

		problems = company.getProblems();

		for (Problem problem : problems) {
			problem.setCompanies(null);
		}

		return SUCCESS;
	}

	public String savePhoneCallHistory() {

		try {

			String newPhoneCall = getParameter("phoneCallJson");
			String companyId = getParameter("companyId");

			phoneCall = companyService.savePhoneCallHistory(newPhoneCall, companyId);

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (ParseException e) {

			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		phoneCall.setCompany(null);
		phoneCall.setUser(null);

		setResponse(ResponseVo.newSuccessMessage("保存成功！"));

		return SUCCESS;
	}

	public String removeLearningHistory() {

		try {

			String learningHistoryId = getParameter("learningHistoryId");

			learningHistoryRepository.delete(Integer.valueOf(learningHistoryId));

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		setResponse(ResponseVo.newSuccessMessage("200"));

		return SUCCESS;
	}

	public String removePhoneCallHistory() {

		try {

			String phoneCallId = getParameter("phoneCallId");

			phoneCallHistoryRepository.delete(Integer.valueOf(phoneCallId));

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		setResponse(ResponseVo.newSuccessMessage("200"));

		return SUCCESS;
	}

	public String loadPhoneCallHistory() {

		try {

			String companyId = getParameter("companyId");

			Company company = companyRepository.findOne(Integer.valueOf(companyId));

			historyRecords = phoneCallHistoryRepository.findByCompany(company);

			if (historyRecords != null) {

				for (PhoneCallHistory history : historyRecords) {
					history.setCompany(null);

					User user = history.getUser();
					if (user != null) {
						XinXinUtils.cleanUser(user);
					}
				}
			}

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		setResponse(ResponseVo.newSuccessMessage("200"));

		return SUCCESS;
	}

	public String loadLearningHistory() {

		try {

			String companyId = getParameter("companyId");

			Company company = companyRepository.findOne(Integer.valueOf(companyId));

			learningHistories = learningHistoryRepository.findByCompany(company);

			if (learningHistories != null) {

				for (LearningHistory history : learningHistories) {
					history.setCompany(null);

					Phase phase = history.getPhase();
					phase.setLearningHistories(null);
				}
			}

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		setResponse(ResponseVo.newSuccessMessage("200"));

		return SUCCESS;
	}

	public String saveLearningHistory() {

		try {

			String learningHistoryJson = getParameter("learningJson");
			String companyId = getParameter("companyId");

			Company company = companyRepository.findOne(Integer.valueOf(companyId));

			JSONObject learningHistoryJsonObject = JSONObject.fromObject(learningHistoryJson);

			LearningHistory learningHistory = XinXinUtils.getJavaObjectFromJsonString(learningHistoryJson, LearningHistory.class);

			if (learningHistory.getPhase() == null) {
				setResponse(ResponseVo.newFailMessage("你没有选择期数"));
				return SUCCESS;
			}

			String firstPayDate = learningHistoryJsonObject.getString("firstPayDate");
			if (StringUtils.isNotBlank(firstPayDate)) {
				learningHistory.setFirstPayDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(firstPayDate));
			} else {
				learningHistory.setFirstPayDate(null);
			}

			String startDate = learningHistoryJsonObject.getString("startDate");
			if (StringUtils.isNotBlank(startDate) && !startDate.equals("null")) {
				learningHistory.setStartDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(startDate));
			} else {
				learningHistory.setStartDate(null);
			}

			String endDate = learningHistoryJsonObject.getString("endDate");
			if (StringUtils.isNotBlank(endDate) && !endDate.equals("null")) {
				learningHistory.setEndDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(endDate));
			} else {
				learningHistory.setEndDate(null);
			}

			String payDebtDate = learningHistoryJsonObject.getString("payDebtDate");
			if (StringUtils.isNotBlank(payDebtDate) && !payDebtDate.equals("null")) {
				learningHistory.setPayDebtDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(payDebtDate));
			} else {
				learningHistory.setPayDebtDate(null);
			}

			String signUpDate = learningHistoryJsonObject.getString("signUpDate");
			if (StringUtils.isNotBlank(signUpDate) && !signUpDate.equals("null")) {
				learningHistory.setSignUpDate(XinXinConstants.SIMPLE_DATE_FORMATTER.parse(signUpDate));
			} else {
				learningHistory.setSignUpDate(null);
			}

			learningHistory.setCompany(company);
			learningHistoryRepository.saveAndFlush(learningHistory);

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		} catch (Exception e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));

			return SUCCESS;
		}

		setResponse(ResponseVo.newSuccessMessage("培训记录保存成功！"));

		return SUCCESS;
	}

	public String changeCompanyStatus() {

		try {

			String companyId = getParameter("companyId");
			String statusId = getParameter("statusId");

			ResponseVo responseVo = companyService.changeCompanyStatus(companyId, statusId);

			setResponse(responseVo);

		} catch (NumberFormatException e) {
			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;

		} catch (Exception e) {

			setResponse(XinXinUtils.makeGeneralErrorResponse(e));
			return SUCCESS;
		}

		return SUCCESS;
	}

	public String findTop10CityCompanyCount() {
		
		List<?> barDatas = companyRepository.findTop10CityCompanyCount();
		List<String> labelList = Lists.newArrayList();
		List<String> countList = Lists.newArrayList();
		
		for (Object barData : barDatas) {
			labelList.add(((Object[])barData)[0].toString());
			countList.add(((Object[])barData)[1].toString());
		}
		
		barChartData = new BarChartData();
		
		barChartData.setLabels(labelList);
		
		Data data = new Data();
		data.setData(countList);
		
		barChartData.setDatasets(ImmutableList.of(data));
	
		return SUCCESS;
		
	}
	
	public List<Company> getCompanies() {

		return companies;
	}

	public void setCompanies(List<Company> companies) {

		this.companies = companies;
	}

	public PagedElement<Company> getCompany() {

		return company;
	}

	public void setCompany(PagedElement<Company> company) {

		this.company = company;
	}

	public Company getC() {

		return c;
	}

	public void setC(Company c) {

		this.c = c;
	}

	public CompanyAdditional getCompanyAdditional() {

		return companyAdditional;
	}

	public void setCompanyAdditional(CompanyAdditional companyAdditional) {

		this.companyAdditional = companyAdditional;
	}

	public List<Problem> getProblems() {

		return problems;
	}

	public void setProblems(List<Problem> problems) {

		this.problems = problems;
	}

	public PhoneCallHistory getPhoneCall() {

		return phoneCall;
	}

	public void setPhoneCall(PhoneCallHistory phoneCall) {

		this.phoneCall = phoneCall;
	}

	public List<PhoneCallHistory> getHistoryRecords() {

		return historyRecords;
	}

	public void setHistoryRecords(List<PhoneCallHistory> historyRecords) {

		this.historyRecords = historyRecords;
	}

	public List<LearningHistory> getLearningHistories() {

		return learningHistories;
	}

	public void setLearningHistories(List<LearningHistory> learningHistories) {

		this.learningHistories = learningHistories;
	}

	
	public BarChartData getBarChartData() {
	
		return barChartData;
	}

	
	public void setBarChartData(BarChartData barChartData) {
	
		this.barChartData = barChartData;
	}


	public class BarChartData {
		private List<String> labels;
		private List<Data> datasets;
		
		public List<String> getLabels() {
		
			return labels;
		}
		
		public void setLabels(List<String> labels) {
		
			this.labels = labels;
		}
		
		public List<Data> getDatasets() {
		
			return datasets;
		}
		
		public void setDatasets(List<Data> datasets) {
		
			this.datasets = datasets;
		}
		
	}
	
	
	public class Data {
		private String fillColor = "rgba(220,220,220,0.5)";
		private String strokeColor = "rgba(220,220,220,0.8)";
		private String highlightFill = "rgba(220,220,220,0.75)";
		private String highlightStroke = "rgba(220,220,220,1)";
		
		private List<String> data = new ArrayList<String>();
		
		public String getFillColor() {
		
			return fillColor;
		}
		
		public void setFillColor(String fillColor) {
		
			this.fillColor = fillColor;
		}
		
		public String getStrokeColor() {
		
			return strokeColor;
		}
		
		public void setStrokeColor(String strokeColor) {
		
			this.strokeColor = strokeColor;
		}
		
		public List<String> getData() {
		
			return data;
		}
		
		public void setData(List<String> data) {
		
			this.data = data;
		}

		
		public String getHighlightFill() {
		
			return highlightFill;
		}

		
		public void setHighlightFill(String highlightFill) {
		
			this.highlightFill = highlightFill;
		}

		
		public String getHighlightStroke() {
		
			return highlightStroke;
		}
		
		public void setHighlightStroke(String highlightStroke) {
		
			this.highlightStroke = highlightStroke;
		}
	}
	
}