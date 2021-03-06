package com.ls.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.common.base.Splitter;
import com.ls.constants.XinXinConstants;
import com.ls.entity.Company;
import com.ls.entity.FeCompanyURL;
import com.ls.entity.GanjiCompanyURL;
import com.ls.entity.OteCompanyURL;
import com.ls.entity.User;
import com.ls.vo.ResponseVo;

public class XinXinUtils {

	private static ShaPasswordEncoder shaPasswordEncoder = new ShaPasswordEncoder(256);

	public static String getEncodedPassword(String password, String username) {

		return shaPasswordEncoder.encodePassword(password, username);
	}

	public static <T> T getJavaObjectFromJsonString(String jsonString, Class<T> classType) {

		@SuppressWarnings("unchecked")
		T javaObject = (T)JSONObject.toBean(JSONObject.fromObject(jsonString), classType);

		return javaObject;
	}

	public static ResponseVo makeGeneralErrorResponse(Exception e) {

		if (e == null) {
			return ResponseVo.newFailMessage("操作失败");
		}
		ResponseVo errorResponseVo = ResponseVo.newFailMessage("你的操作在处理时发生了错误." + e.getMessage());

		return errorResponseVo;
	}

	public static ResponseVo makeGeneralSuccessResponse() {

		ResponseVo errorResponseVo = ResponseVo.newSuccessMessage("<b>操作成功!</b>");

		return errorResponseVo;
	}

	public static User getDevelopmentUser() {

		User user = new User();
		user.setId(1);
		user.setName("Jerry Jiang");

		return user;
	}

	public static void cleanUser(User user) {

		user.setPhoneCallHistory(null);
		user.setRoles(null);
		user.setCities(null);
		user.setApplyingWillingCustomers(null);
		
	}

	public static Map<String, String> mergeDuplicateCompanyInOnePageFor138(List<OteCompanyURL> companyList) {

		Map<String, String> map = new HashMap<String, String>();
		for (OteCompanyURL company : companyList) {
			map.put(company.getCompanyId(), company.getCompanyId());
		}
		return map;
	}

	public static Map<String, String> mergeDuplicateCompanyInOnePageForGanji(List<GanjiCompanyURL> companyList) {

		Map<String, String> map = new HashMap<String, String>();
		for (GanjiCompanyURL company : companyList) {
			map.put(company.getCompanyId(), company.getCompanyId());
		}
		return map;
	}

	public static Map<String, String> mergeDuplicateCompanyInOnePageFor58(List<FeCompanyURL> companyList) {

		Map<String, String> map = new HashMap<String, String>();
		for (FeCompanyURL company : companyList) {
			map.put(company.getCompanyId(), company.getCompanyId());
		}
		return map;
	}

	public static boolean stringIsEmpty(String str) {

		if (str != null && str.trim().length() > 0) {
			return false;
		}
		return true;
	}

	public static Date getStandardDate(String dateString) {

		try {
			return XinXinConstants.FULL_DATE_FORMATTER.parse(dateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getStandardDate(Date date) {

		String standardDateString = XinXinConstants.FULL_DATE_FORMATTER.format(date);
		return getStandardDate(standardDateString);
	}

	public static Date getStandardSimpleDate(Date date) {

		String standardDateString = XinXinConstants.SIMPLE_DATE_FORMATTER.format(date);
		try {
			return XinXinConstants.SIMPLE_DATE_FORMATTER.parse(standardDateString);
		} catch (ParseException e) {
			return null;
		}
	}

	public static Date getNow() {

		return getStandardDate(new Date());
	}

	public static String getSimpleTodayString() {

		Date now = new Date();
		return XinXinConstants.SIMPLE_DATE_FORMATTER.format(now);
	}

	public static boolean isUserHasRole(String role) {

		SecurityContextHolderAwareRequestWrapper securityContextHolderAwareRequestWrapper = new SecurityContextHolderAwareRequestWrapper(ServletActionContext.getRequest(), "ROLE_");

		return securityContextHolderAwareRequestWrapper.isUserInRole(role);

	}

	public static String getCurrentUserName() {

		SecurityContextHolderAwareRequestWrapper securityContextHolderAwareRequestWrapper = new SecurityContextHolderAwareRequestWrapper(ServletActionContext.getRequest(), "ROLE_");
		return securityContextHolderAwareRequestWrapper.getUserPrincipal().getName();
	}

	public static boolean checkIfCompanyIsStupid(Company company) {

		if (StringUtils.isBlank(company.getContactor())) {
			return true;
		}

		if (StringUtils.isBlank(company.getPhoneSrc()) && StringUtils.isBlank(company.getMobilePhoneSrc()) && StringUtils.isBlank(company.getPhone()) && StringUtils.isBlank(company.getMobilePhone())) {
			return true;
		}

		return false;

	}

	public static String parseFEResourceId(String url) {

		int index = url.indexOf("58.com");

		if (index != -1) {
			String sub = url.substring(index + 7);
			int sIndex = sub.indexOf("/");
			if (sIndex != -1) {
				return sub.substring(0, sIndex);
			}
		}

		return "";
	}

	public static String parseCityUrl(String url) {

		Iterable<String> iterable = Splitter.on("/").omitEmptyStrings().split(url);

		for (String singleString : iterable) {
			if (singleString.contains("58.com")) {
				return "http://" + singleString;
			}
		}

		return "";
	}

	public static String getOteResourceIdFromUrl(String href) {

		int resourceIdStart = href.indexOf("_");
		int resourceIdEnd = href.indexOf(".shtml");

		String resourceId = href.substring(resourceIdStart + 1, resourceIdEnd);

		return resourceId;
	}

	public static Object getFirstElementByXPath(HtmlPage htmlPage, String xPath) {

		try {
			List<?> nodes = htmlPage.getByXPath(xPath);
			if (null != nodes && !nodes.isEmpty()) {

				return nodes.get(0);
			}
		} catch (Exception e) {

		}

		return null;
	}
	
	public static void main(String[] args) {
		String tt = "[\"2100.00\",104,0,0]";
		JSONArray array = JSONArray.fromObject(tt);
		
		System.out.println(array.getInt(1));
	}
}
