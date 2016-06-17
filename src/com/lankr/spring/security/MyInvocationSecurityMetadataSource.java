package com.lankr.spring.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import com.fasterxml.jackson.dataformat.yaml.snakeyaml.constructor.Construct;
import com.lankr.spring.security.tool.AntUrlPathMatcher;
import com.lankr.spring.security.tool.UrlMatcher;

public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource{
	
	private UrlMatcher urlMatcher = new AntUrlPathMatcher() ;
	private static Map<String, Collection<ConfigAttribute>> resourceMap = null ;
	
	//tomcat启动时加载一次
	public MyInvocationSecurityMetadataSource() {
		loadResourceDefine() ;
	} 
	
	//tomcat加载时启动 加载所有url和权限的对应关系
	private void loadResourceDefine() {
		resourceMap = new HashMap<String,Collection<ConfigAttribute>>() ;
		Collection<ConfigAttribute> atts = new ArrayList<ConfigAttribute>() ;
		ConfigAttribute ca = new SecurityConfig("ROLE_USER") ;
		atts.add(ca) ;
		resourceMap.put("/view/hello.html", atts) ;
		Collection<ConfigAttribute> attsno = new ArrayList<ConfigAttribute>() ;
		ConfigAttribute cano = new SecurityConfig("ROLE_NO") ;
		attsno.add(cano) ;
		resourceMap.put("/view/world.html", attsno) ;
	}

	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return null;
	}

	//参数是要访问的url，返回这个url对应的所有权限
	@Override
	public Collection<ConfigAttribute> getAttributes(Object obj) throws IllegalArgumentException {
		String url = ((FilterInvocation)obj).getRequestUrl() ;
		Iterator<String> iterator = resourceMap.keySet().iterator() ;
		while (iterator.hasNext()) {
			String resUrl = iterator.next() ;
			if (urlMatcher.pathMatchersUrl(resUrl, url)) {
				return resourceMap.get(resUrl) ;
			}
		}
		return null;
	}

	@Override
	public boolean supports(Class<?> arg0) {
		return true;
	}

}
