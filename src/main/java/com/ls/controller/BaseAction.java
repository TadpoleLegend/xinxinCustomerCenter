package com.ls.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.struts2.interceptor.ParameterAware;

import com.ls.service.CommonService;
import com.ls.vo.ResponseVo;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;


public class BaseAction extends ActionSupport implements ParameterAware {

	private static final long serialVersionUID = 1L;
	
	@Resource(name = "commonService")
	public CommonService commonService;
	
	private ResponseVo response;
	Map<String, Object> application;
	Map<String, Object> session;

	private Long userId = -1L;
	private String userName = "";

	private String message;

	private Map<String, String[]> requestParameters = null;

	public String getMessage() {

		return message;
	}

	public void setMessage(String message) {

		this.message = message;
	}

	public BaseAction() {

	}

	public void setupSession() {

		if (session == null) {
			session = ActionContext.getContext().getSession();
		}
	}

	public void setupApplication() {

		if (application == null) {
			application = ActionContext.getContext().getApplication();
		}
	}

	public Long getUserId() {

		return userId;
	}

	public void setUserId(Long userId) {

		this.userId = userId;
	}

	public String getUserName() {

		return userName;
	}

	public void setUserName(String userName) {

		this.userName = userName;
	}

	public void setParameters(Map<String, String[]> parameters) {

		this.requestParameters = parameters;

	}

	public Map<String, String[]> getParameters() {

		if (requestParameters == null) {
			requestParameters = new HashMap<String, String[]>();
		}

		return requestParameters;

	}

	public String getParameter(String key) {

		return ActionUtil.getReqParaByMap(getParameters(), key);
	}

	public String[] getParameters(String key) {

		if (getParameters().size() > 0) {
			return getParameters().get(key);
		} else {
			return null;
		}
	}

	public Map<String, Object> getSession() {

		return session;
	}


	public Map<String, String> clearEmptyValue(Map<String, String> paraMap) {

		Map<String, String> resultMap = new HashMap<String, String>();
		Set<Map.Entry<String, String>> entrySet = paraMap.entrySet();
		for (Map.Entry<String, String> entry : entrySet) {
			if (!("".equalsIgnoreCase(entry.getValue()) || "-1".equalsIgnoreCase(entry.getValue()))) {
				resultMap.put(entry.getKey(), entry.getValue());
			}
		}

		return resultMap;
	}

	public ResponseVo getResponse() {
		return response;
	}

	public void setResponse(ResponseVo response) {
		this.response = response;
	}

}
