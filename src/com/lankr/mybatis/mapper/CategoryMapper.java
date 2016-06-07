package com.lankr.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.Category;

public interface CategoryMapper {
	Category selectById(@Param("id")int id) ;
}
