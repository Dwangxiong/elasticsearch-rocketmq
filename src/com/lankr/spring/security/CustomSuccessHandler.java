package com.lankr.spring.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

public class CustomSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{

	private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy() ;
	
	@Override
	protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
			throws IOException, ServletException {
		String targetUrl = detemineTargetUrl(authentication) ;
		
		if (response.isCommitted()) {
			System.out.println("Can't redirect!");
			return ;
		}
		
		redirectStrategy.sendRedirect(request, response, targetUrl);
	}

	private String detemineTargetUrl(Authentication authentication) {
		String url = "" ;
		Collection<? extends GrantedAuthority> authorites = authentication.getAuthorities() ;
		List<String> roles = new ArrayList<String>() ;
		for (GrantedAuthority aa : authorites) {
			roles.add(aa.getAuthority()) ;
		}
		
		if (isDba(roles)) {
			url = "/db" ;
		} else if (isAdmin(roles)) {
			url = "/welcome" ;
		} else if (isUser(roles)) {
			url = "/login" ;
		} else {
			url = "/accessDenied" ;
		}
		return url;
	}

	private boolean isUser(List<String> roles) {
		if (roles.contains("ROLE_USER")) 
			return true ;
		return false;
	}

	private boolean isAdmin(List<String> roles) {
		if (roles.contains("ROLE_ADMIN")) 
			return true ;
		return false;
	}

	private boolean isDba(List<String> roles) {
		if (roles.contains("ROLE_DBA")) 
			return true ;
		return false;
	}
	
	public void setRedirectStrategy(RedirectStrategy redirectStrategy) {
        this.redirectStrategy = redirectStrategy;
    }
 
    protected RedirectStrategy getRedirectStrategy() {
        return redirectStrategy;
    }
 

}
