package com.ls.interceptors;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 * check if the user has the authority to perform the action.
 * if not, he'll be rejected.
 * 
 * @author jjiang
 *
 */
public class PermissionCheckInterceptor implements Interceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4054740155334364496L;

	public void destroy() {

	}

	public void init() {

	}

	public String intercept(ActionInvocation invocation) throws Exception {

		String actionName = invocation.getInvocationContext().getName();
		return null;
	}

}
