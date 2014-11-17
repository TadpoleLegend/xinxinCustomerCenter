package com.ls.interceptors;

import java.util.Map;



import org.apache.log4j.Logger;

import com.ls.constants.XinXinConstants;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * check if the user is logged in or the session is active
 * 
 * @author jjiang
 *
 */
public class UserSessionCheckInterceptor implements Interceptor {

	private static final long serialVersionUID = 6856641421923003795L;

	private static Logger logger = Logger.getLogger(UserSessionCheckInterceptor.class);

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation actionInvocation) throws Exception {

		Map<String, Object> session = actionInvocation.getInvocationContext().getSession();
		if (session != null) {
			if (!session.containsKey(XinXinConstants.CURRENT_USER)) {
				logger.info("No user with the session");
				addActionError(actionInvocation, null);
				return Action.LOGIN;
			}

		} else {
			logger.info("session is NULL");
			addActionError(actionInvocation, null);
			return Action.LOGIN;
		}

		return actionInvocation.invoke();
	}

	private void addActionError(ActionInvocation invocation, String message) {

		Object action = invocation.getAction();
		if (action instanceof ActionSupport) {
			((ActionSupport)action).addActionError(((ActionSupport)action).getText("USER_NOT_LOGGED_IN"));
		}
	}
}