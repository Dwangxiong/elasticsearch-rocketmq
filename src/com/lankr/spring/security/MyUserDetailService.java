package com.lankr.spring.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthoritiesContainer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * userdetail用于加载用户的信息并进行返回以便于spring security进行验证，加载数据库的user信息可以在此进行
 * 返回的user只是信息，此类不进行验证，而是交给其他类去验证
 * 支持加密信息的处理
 * @author Lankr
 *
 */
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
			return new User(1,"lankr", "123456", true, auths) ;
		} else if ("wang".equals(username)) {
			auths.add(auth1) ;
			return new User(2,"wang", "123456", true, auths) ;
		}
		return new User(3,"lankr", "lankr", true, auths);
	}

}
