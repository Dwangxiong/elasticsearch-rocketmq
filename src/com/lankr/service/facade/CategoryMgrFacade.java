package com.lankr.service.facade;

import java.sql.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.lankr.model.Category;
import com.lankr.model.Hospital;

public interface CategoryMgrFacade {
	public Category selectById(int id);
}
