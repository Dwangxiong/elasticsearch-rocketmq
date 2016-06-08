package com.lankr.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.City;

public interface CityMapper {
	City selectById(@Param("id")int id) ;
}
