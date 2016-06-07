package com.lankr.service.facadeImpl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lankr.model.Category;
import com.lankr.model.Hospital;
import com.lankr.mybatis.mapper.CategoryMapper;
import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.service.facade.CategoryMgrFacade;
import com.lankr.service.facade.HospitalMgrFacade;

public class CategoryMgrFacadeImpl implements CategoryMgrFacade{

	@Autowired
	private CategoryMapper categoryMapper ;
	
	@Override
	public Category selectById(int id) {
		Category category = categoryMapper.selectById(id) ;
		return category ;
	}

}
