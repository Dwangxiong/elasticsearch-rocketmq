package com.lankr.service.facadeImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lankr.model.City;
import com.lankr.mybatis.mapper.CityMapper;
import com.lankr.service.facade.CityMgrFacade;

public class CityMgrFacadeImpl implements CityMgrFacade {

	@Autowired
	protected CityMapper cityMapper ;
	
	@Override
	public City selectById(int id) {
		City city = cityMapper.selectById(id) ;
		return city;
	}

}
