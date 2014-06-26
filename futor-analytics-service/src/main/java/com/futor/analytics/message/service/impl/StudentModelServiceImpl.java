package com.futor.analytics.message.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.message.db.StudentModelDAO;
import com.futor.analytics.message.service.StudentModelService;

public class StudentModelServiceImpl implements StudentModelService{
	@Autowired
	StudentModelDAO studentModelDAO;
	@Override
	public long getConceptTime(long userId, String courseNid) throws Exception {
		
		return studentModelDAO.getConceptTime(userId, courseNid);
	}

}
