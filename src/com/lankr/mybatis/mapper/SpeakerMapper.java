package com.lankr.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.lankr.model.Speaker;

public interface SpeakerMapper {
	Speaker selectById(@Param("id")int id) ;
}
