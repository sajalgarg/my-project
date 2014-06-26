package com.futor.analytics.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.StudentBigTableReportDAO;
import com.futor.analytics.service.StudentBigTableReportService;
import com.futor.analytics.service.util.ServiceUtil;
import com.futor.analytics.util.DateUtil;
import com.futor.analytics.util.StudentReportEnum;

public class StudentBigTableReportServiceImpl implements StudentBigTableReportService{

	@Autowired
	private StudentBigTableReportDAO studentBigTableReportDAO;
	@Override
	public long getStudentStudyDuration(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception{
		//startTime is currentDate in milliseconds 
		//getting days before currenbt date
		String startDateId  = ServiceUtil.getStartDate(startTime,reportParam);
		String endDateID = DateUtil.getDate(new Date(startTime));
		return studentBigTableReportDAO.getStudentStudyDuration(userId, courseNid,Long.parseLong(startDateId),Long.parseLong(endDateID));
		
	}

	@Override
	public HashMap<String, Long> getStudentStudyTransactionDuration(long userId,String coursNid,long startTime,StudentReportEnum reportParam) throws Exception{
		//startTime is currentDate in milliseconds 
				String  startDateId = ServiceUtil.getStartDate(startTime,reportParam);
				String endDateID = DateUtil.getDate(new Date(startTime));
		return studentBigTableReportDAO.getStudentStudyTransactionDuration(userId, coursNid, Long.parseLong(startDateId),Long.parseLong(endDateID));
	}

	@Override
	public Map<String,Map<String, Long>> getStudentStudyTrends(long userId,String coursNid,long startTime,StudentReportEnum reportParam) throws Exception{
		//startTime is currentDate in milliseconds 
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		String endDateID = DateUtil.getDate(new Date(startTime));
		return studentBigTableReportDAO.getStudentStudyTrends(userId,coursNid,Long.parseLong(startDateId),Long.parseLong(endDateID));
	}
	

}
