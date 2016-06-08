package com.lankr.service.facadeImpl;

import org.springframework.beans.factory.annotation.Autowired;

import com.lankr.model.Province;
import com.lankr.mybatis.mapper.ProvinceMapper;
import com.lankr.service.facade.ProvinceMgrFacade;

public class ProvinceMgrFacadeImpl implements ProvinceMgrFacade {
	
	@Autowired
	protected ProvinceMapper provinceMapper ;

	@Override
	public Province selectById(int id) {
		Province province = provinceMapper.selectById(id) ;
		
		return province;
	}

}
