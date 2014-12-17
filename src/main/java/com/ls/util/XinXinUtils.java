package com.ls.util;

import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;

import com.ls.constants.XinXinConstants;
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
		T javaObject = (T) JSONObject.toBean(JSONObject.fromObject(jsonString),classType);

		return javaObject;
	}
	
	public static ResponseVo makeGeneralErrorResponse(Exception e) {
		if (e == null) {
			return ResponseVo.newFailMessage("操作失败");
		}
		ResponseVo errorResponseVo = ResponseVo.newFailMessage("你的操作在处理时发生了错误！ 异常的消息是 ： " + e.getMessage());
		
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
	}
	
	public static Map<String,String> mergeDuplicateCompanyInOnePageFor138(List<OteCompanyURL> companyList){
		Map<String,String> map = new HashMap<String,String>();
		for(OteCompanyURL company:companyList){
				map.put(company.getCompanyId(), company.getCompanyId());
			}
		return map;
	}
	public static Map<String,String> mergeDuplicateCompanyInOnePageForGanji(List<GanjiCompanyURL> companyList){
		Map<String,String> map = new HashMap<String,String>();
		for(GanjiCompanyURL company:companyList){
				map.put(company.getCompanyId(), company.getCompanyId());
			}
		return map;
	}
	public static Map<String,String> mergeDuplicateCompanyInOnePageFor58(List<FeCompanyURL> companyList){
		Map<String,String> map = new HashMap<String,String>();
		for(FeCompanyURL company:companyList){
				map.put(company.getCompanyId(), company.getCompanyId());
			}
		return map;
	}
	
	public static boolean stringIsEmpty(String str){
		if(str !=null && str.trim().length()>0){
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
	

}
