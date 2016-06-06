package com.lankr.mybatis.mapper;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.lankr.vo.Hospital;

public interface HospitalMapper {
	public List<Hospital> selectAllHospital(@Param("id")int id, @Param("size")int size);
}
