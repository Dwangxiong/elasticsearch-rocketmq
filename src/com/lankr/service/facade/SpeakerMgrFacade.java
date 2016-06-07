package com.lankr.service.facade;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lankr.model.Hospital;
import com.lankr.model.Speaker;

public interface SpeakerMgrFacade {
	public Speaker selectById(int id);
}
