package com.futor.analytics.service;

import java.util.Map;

import com.futor.analytics.util.StudentReportEnum;

public interface StudentProgressperformanceReportService {
	Map<String,String> getConceptsPractised(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,String> getConceptsRevised(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,String> getAttemptHistory(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	double getOverAllScore(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,String> getPerformanceStatus(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,String> getConceptsPractisedTrends(long userId,String courseNId,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,String> getConceptsRevisedTrends(long userId,String courseNId,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String, Object> getCoursePerformanceTrends(long userId,String courseNId,StudentReportEnum reportParam) throws Exception;
	Map<String, Object> getCorrectIncorrectQuestionDetails(long userId,String courseNid)throws Exception;

}
