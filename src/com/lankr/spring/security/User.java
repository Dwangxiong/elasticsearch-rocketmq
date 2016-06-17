package com.lankr.spring.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails{
	
	private int id ;
	
	private String username ;
	
	private String password ;
	
	private boolean isActive ;
	
	private Collection<? extends GrantedAuthority> authorities ;
	
	public User(int id, String username, String password, boolean isActive) {
		super() ;
		this.id = id ;
		this.username = username ;
		this.password = password ;
		this.isActive = isActive ;
	}
	
	public User(int id, String username, String password, boolean isActive, Collection<? extends GrantedAuthority> authorities) {
		super() ;
		this.id = id ;
		this.username = username ;
		this.password = password ;
		this.isActive = isActive ;
		this.authorities = authorities ;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities ;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.isActive ;
	}

}
