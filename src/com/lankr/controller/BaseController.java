package com.lankr.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;
import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.service.facade.HospitalMgrFacade;
import com.lankr.service.facade.ResourceMgrFacade;
import com.lankr.spring.security.MyUserDetailService;

public class BaseController {
	
	@Autowired
	protected HospitalMgrFacade hospitalMgrFacade ;
	@Autowired
	protected ResourceMgrFacade resourceMgrFacade ;
	
	public List<Hospital> selectAllHospital(int id, int size) {
		List<Hospital> hospitals = hospitalMgrFacade.selectAllHospital(id, size) ;
		return hospitals ;
	}
	
	public String getCurrentUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName() ;
	}
	
}
