package com.lankr.spring.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class MyUserDetailService implements UserDetailsService{

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>() ;
		SimpleGrantedAuthority auth2 = new SimpleGrantedAuthority("ROLE_ADMIN") ;
		SimpleGrantedAuthority auth1 = new SimpleGrantedAuthority("ROLE_USER") ;
		User user = null ;
		if (username.equals("lankr")) {
			auths.add(auth1) ;
			auths.add(auth2) ;
			user = new User(1,"lankr", "123456", true, auths) ;
		}
		if ("wang".equals(username)) {
			auths.add(auth2) ;
			user = new User(1,"wang", "123456", true, auths) ;
		}
		return user;
	}

}
