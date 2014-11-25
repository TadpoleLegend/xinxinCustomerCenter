package com.ls.util;

import com.ls.entity.User;
import com.ls.vo.ResponseVo;

import net.sf.json.JSONObject;


public class XinXinUtils {

	public static <T> T getJavaObjectFromJsonString(String jsonString, Class<T> classType) {

		@SuppressWarnings("unchecked")
		T javaObject = (T) JSONObject.toBean(JSONObject.fromObject(jsonString),classType);

		return javaObject;
	}
	
	public static ResponseVo makeGeneralErrorResponse(Exception e) {
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
		
		user.setLocations(null);
		user.setPhoneCallHistory(null);
		user.setRoles(null);
		user.setUserCitys(null);
	}
}
