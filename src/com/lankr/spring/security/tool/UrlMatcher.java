package com.lankr.spring.security.tool;

public interface UrlMatcher {
	Object compile(String paramString) ;
	boolean pathMatchersUrl(Object paramObject, String paramString) ;
	String getUniversalMatchPattren() ;
	boolean requiresLowerCaseUrl() ;
}
