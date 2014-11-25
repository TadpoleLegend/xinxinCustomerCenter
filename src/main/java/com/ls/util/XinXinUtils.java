package com.ls.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.ls.entity.Company;
import com.ls.entity.User;
import com.ls.enums.ResourceTypeEnum;
import com.ls.vo.ResponseVo;


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
	
	public static Map<String,String> mergeDuplicateCompanyInOnePage(List<Company> companyList,String resourceType){
		Map<String,String> map = new HashMap<String,String>();
		for(Company company:companyList){
			if(ResourceTypeEnum.OneThreeEight.getId().equals(resourceType)){
				map.put(company.getoTEresourceId(), company.getoTEresourceId());
			}else if(ResourceTypeEnum.Ganji.getId().equals(resourceType)){
				map.put(company.getGanjiresourceId(), company.getGanjiresourceId());
			}else if(ResourceTypeEnum.FiveEight.getId().equals(resourceType)){
				map.put(company.getfEresourceId(), company.getfEresourceId());
			}
		}
		return map;
	}
}
