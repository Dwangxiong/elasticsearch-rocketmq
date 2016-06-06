package com.lankr.service.facadeImpl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;
import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.service.facade.HospitalMgrFacade;

public class HospitalMgrFacadeImpl implements HospitalMgrFacade{

	@Autowired
	private HospitalMapper hospitalMapper ;
	
	@Override
	public List<Hospital> selectAllHospital(int id, int size) {
		List<Hospital> hospitals = null ;
		hospitals = hospitalMapper.selectAllHospital(id, size) ;
		return hospitals;
	}

}
