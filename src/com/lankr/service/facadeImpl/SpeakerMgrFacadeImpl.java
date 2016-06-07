package com.lankr.service.facadeImpl;

import java.sql.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;
import com.lankr.model.Speaker;
import com.lankr.mybatis.mapper.HospitalMapper;
import com.lankr.mybatis.mapper.SpeakerMapper;
import com.lankr.service.facade.HospitalMgrFacade;
import com.lankr.service.facade.SpeakerMgrFacade;

public class SpeakerMgrFacadeImpl implements SpeakerMgrFacade{

	@Autowired
	private SpeakerMapper speakerMapper ;
	
	@Override
	public Speaker selectById(int id) {
		Speaker speaker = speakerMapper.selectById(id) ;
		return speaker;
	}

}
