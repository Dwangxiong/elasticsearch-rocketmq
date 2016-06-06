package com.lankr.controller;

import java.io.IOException;
import java.sql.Date;
import java.util.List;

import javax.servlet.ServletInputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.service.facade.HospitalMgrFacade;
import com.lankr.vo.Hospital;

public class BaseController {
	
	@Autowired
	protected HospitalMgrFacade hospitalMgrFacade ;
	
	public List<Hospital> getAllHospital(int id, int size) {
		List<Hospital> hospitals = hospitalMgrFacade.selectAllHospital(id, size) ;
		return hospitals ;
	}
	
	
	
}
