package com.lankr.interceptor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;


public class MyInterceptor extends HandlerInterceptorAdapter {

	protected static Log logger = LogFactory.getLog(MyInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI() ;
		System.out.println("----------------" + uri);
		System.out.println(request.getRemoteAddr());
		if (uri != null && uri != "" && uri.contains("/api/search")) {
			return super.preHandle(request, response, handler);
		} else {
			String ip = request.getHeader("Host") ;
			System.out.println("----------------" + ip);
			if ("localhost".equals(ip) || "127.0.0.1".equals(ip)) {
				return super.preHandle(request, response, handler);
			}
			String attackIp= request.getHeader("X-Forwarded-For");  
	        if (attackIp== null || attackIp.length() == 0 || "unknown".equalsIgnoreCase(attackIp))  
	        	attackIp= request.getHeader("Proxy-Client-IP");  
	        if (attackIp== null || attackIp.length() == 0 || "unknown".equalsIgnoreCase(attackIp))  
	        	attackIp= request.getHeader("WL-Proxy-Client-IP");  
	        if (attackIp== null || attackIp.length() == 0 || "unknown".equalsIgnoreCase(attackIp))  
	        	attackIp= request.getHeader("HTTP_CLIENT_IP");  
	        if (attackIp== null || attackIp.length() == 0 || "unknown".equalsIgnoreCase(attackIp))  
	        	attackIp= request.getHeader("HTTP_X_FORWARDED_FOR");  
	        if (attackIp== null || attackIp.length() == 0 || "unknown".equalsIgnoreCase(attackIp))  
	        	attackIp= request.getRemoteAddr();  
	        System.out.println(attackIp);
			logger.error(attackIp + request.getRequestURI() + request.getQueryString() + "非法的请求");
			return false ;
		}
	}

}