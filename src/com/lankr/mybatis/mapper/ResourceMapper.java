package com.lankr.mybatis.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.Resource;

public interface ResourceMapper {
	public List<Resource> selectAllResource(@Param("id")int id, @Param("size")int size);
}
