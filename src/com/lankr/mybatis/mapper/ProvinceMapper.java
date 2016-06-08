package com.lankr.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.Province;

public interface ProvinceMapper {
	Province selectById(@Param("id")int id) ;
}
