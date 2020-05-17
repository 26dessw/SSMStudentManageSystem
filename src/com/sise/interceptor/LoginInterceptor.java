package com.sise.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;



/**
 * 登录过滤拦截器
 * @author llq
 *
 */
public class LoginInterceptor  implements HandlerInterceptor{

	//请求发生之后
	@Override
	public void afterCompletion(HttpServletRequest arg0,
			HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	//请求发生时
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1,
			Object arg2, ModelAndView arg3) throws Exception {
		// TODO Auto-generated method stub
		
	}

	//请求发生之前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object arg2) throws Exception {
		
		String url = request.getRequestURI();
		//System.out.println("进入拦截器，url = " + url);
		Object user = request.getSession().getAttribute("user");
		if(user == null){
			//表示未登录或者登录状态失效
			System.out.println("未登录或登录失效，url = " + url);
			
			//判断是否属于ajax请求
			if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
				
				Map<String, String> ret = new HashMap<String, String>();
				ret.put("type", "error");
				ret.put("msg", "登录状态已失效，请重新去登录!");
				response.getWriter().write(JSONObject.fromObject(ret).toString());
				return false;
			}
			response.sendRedirect(request.getContextPath() + "/system/login");
			return false;
		}
		return true;
	}

}
