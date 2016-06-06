package com.lankr.service.facadeImpl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;
import com.lankr.model.Resource;
import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.mybatis.mapper.ResourceMapper;
import com.lankr.service.facade.HospitalMgrFacade;
import com.lankr.service.facade.ResourceMgrFacade;

public class ResourceMgrFacadeImpl implements ResourceMgrFacade{

	@Autowired
	private ResourceMapper resourceMapper ;
	
	@Override
	public List<Resource> selectAllResource(int id, int size) {
		List<Resource> resources = null ;
		resources = resourceMapper.selectAllResource(id, size) ;
		return resources;
	}

}
