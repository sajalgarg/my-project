package com.futor.analytics.service.impl;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.StudentCorrectIncorrectAttemptQuestionsDAO;
import com.futor.analytics.db.StudentCoursePerformanceTrendsDAO;
import com.futor.analytics.db.StudentProgressPerformanceReportDAO;
import com.futor.analytics.service.StudentProgressperformanceReportService;
import com.futor.analytics.service.util.ServiceUtil;
import com.futor.analytics.util.DateUtil;
import com.futor.analytics.util.StudentReportEnum;

public class StudentProgressperformanceReportServiceImpl implements StudentProgressperformanceReportService{

	@Autowired
	StudentProgressPerformanceReportDAO studentProgressPerformanceReportDAO;
	@Autowired
	StudentCoursePerformanceTrendsDAO studentCoursePerformanceTrendsDAO;
	@Autowired
	StudentCorrectIncorrectAttemptQuestionsDAO studentCorrectIncorrectAttemptQuestionsDAO;

	@Override
	public Map<String, String> getConceptsPractised(long userId,
			String courseNid, long startTime, StudentReportEnum reportParam)
					throws Exception {
		//startTime is currentDate in milliseconds 
		//convert to 20140613 format 
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		Date dt = DateUtil.getDate(startDateId);
		String lastStartDateId=ServiceUtil.getStartDate(dt.getTime(),reportParam);

		return studentProgressPerformanceReportDAO.getConceptsPractised(userId, courseNid, Long.parseLong(startDateId), Long.parseLong(endDateId),Long.parseLong(lastStartDateId), Long.parseLong(endDateId));


	}

	@Override
	public Map<String, String> getConceptsRevised(long userId,
			String courseNid, long startTime, StudentReportEnum reportParam)
					throws Exception {
		//startTime is currentDate in milliseconds 
		//convert to 20140613 format 
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		Date dt = DateUtil.getDate(startDateId);
		String lastStartDateId=ServiceUtil.getStartDate(dt.getTime(),reportParam);

		return studentProgressPerformanceReportDAO.getConceptsRevised(userId, courseNid, Long.parseLong(startDateId), Long.parseLong(endDateId),Long.parseLong(lastStartDateId), Long.parseLong(endDateId));
	}

	@Override
	public Map<String, String> getAttemptHistory(long userId, String courseNid,
			long startTime, StudentReportEnum reportParam) throws Exception {
		//startTime is currentDate in milliseconds 
		//convert to 20140613 format 
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		return studentProgressPerformanceReportDAO.getAttemptHistory(userId, courseNid, Long.parseLong(startDateId), Long.parseLong(endDateId));
	}

	@Override
	public double getOverAllScore(long userId, String courseNid,
			long startTime, StudentReportEnum reportParam) throws Exception {
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		return studentProgressPerformanceReportDAO.getOverAllScore(userId, courseNid,  Long.parseLong(startDateId), Long.parseLong(endDateId));
	}

	@Override
	public Map<String, String> getPerformanceStatus(long userId,
			String courseNid, long startTime, StudentReportEnum reportParam)
					throws Exception {
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		return studentProgressPerformanceReportDAO.getPerformanceStatus(userId, courseNid,  Long.parseLong(startDateId), Long.parseLong(endDateId));
	}

	@Override
	public Map<String, String> getConceptsPractisedTrends(long userId,
			String courseNId, long startTime, StudentReportEnum reportParam)
					throws Exception {
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		return studentProgressPerformanceReportDAO.getConceptsPractisedTrends(userId, courseNId, Long.parseLong(startDateId), Long.parseLong(endDateId));
	}

	@Override
	public Map<String, String> getConceptsRevisedTrends(long userId,
			String courseNId, long startTime, StudentReportEnum reportParam)
					throws Exception {
		String endDateId = DateUtil.getDate(new Date(startTime));		
		String startDateId = ServiceUtil.getStartDate(startTime,reportParam);
		return studentProgressPerformanceReportDAO.getConceptsRevisedTrends(userId, courseNId,  Long.parseLong(startDateId), Long.parseLong(endDateId));
	}


	@Override
	public Map<String, Object> getCoursePerformanceTrends(long userId,String courseNid,StudentReportEnum reportParam)
			throws Exception {
		long endTime = new Date().getTime();
		String startDateId = ServiceUtil.getStartDate(endTime,reportParam);
		System.out.println("StartDateId :::::::::::::::: "+startDateId);
		String endDateId = DateUtil.getDate(new Date(endTime));
		System.out.println("EndDateId ::::::::::::::::: "+endDateId);
		return studentCoursePerformanceTrendsDAO.getCoursePerformanceTrends(userId,courseNid,Long.parseLong(startDateId),Long.parseLong(endDateId));
	}

	@Override
	public Map<String, Object> getCorrectIncorrectQuestionDetails(long userId,String courseNid) throws Exception{
		return studentCorrectIncorrectAttemptQuestionsDAO.correctIncorrectQuestionDetails(courseNid, userId);
	}

}
