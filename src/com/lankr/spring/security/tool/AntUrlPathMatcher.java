package com.lankr.spring.security.tool;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

public class AntUrlPathMatcher implements UrlMatcher{
	
	private boolean requiresLowerCaseUrl ;
	private PathMatcher pathMatcher ;
	
	public AntUrlPathMatcher() {
		this(true) ;
	}
	
	public AntUrlPathMatcher(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = true ;
		this.pathMatcher = new AntPathMatcher() ;
		this.requiresLowerCaseUrl = requiresLowerCaseUrl ;
	}

	@Override
	public Object compile(String paramString) {
		if (this.requiresLowerCaseUrl) {
			return paramString.toLowerCase() ;
		}
		return paramString ;
	}

	public void setRequiresLowerCaseUrl(boolean requiresLowerCaseUrl) {
		this.requiresLowerCaseUrl = requiresLowerCaseUrl ;
	}
	
	@Override
	public boolean pathMatchersUrl(Object paramObject, String paramString) {
		if (("/**".equals(paramObject)) || ("**").equals(paramObject)) {
			return true ;
		}
		return this.pathMatcher.match((String)paramObject, paramString);
	}

	@Override
	public String getUniversalMatchPattren() {
		return "/**";
	}

	@Override
	public boolean requiresLowerCaseUrl() {
		return this.requiresLowerCaseUrl;
	}
	
	@Override
	public String toString() {
		return super.getClass().getName() + "[requiresLowerCase='" + this.requiresLowerCaseUrl + "']" ;
	}

}
