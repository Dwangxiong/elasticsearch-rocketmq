package com.lankr.service.facade;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;

public interface HospitalMgrFacade {
	public List<Hospital> selectAllHospital(int id, int size);
}
