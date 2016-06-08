package com.lankr.mybatis.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.Hospital;

public interface HospitalMapper {
	List<Hospital> selectAllHospital(@Param("id")int id, @Param("size")int size);
	Hospital selectById(@Param("id")int id) ;
}
