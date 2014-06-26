package com.futor.analytics.service;

import java.util.HashMap;
import java.util.Map;

import com.futor.analytics.util.StudentReportEnum;

public interface StudentBigTableReportService {

	long getStudentStudyDuration(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	HashMap<String, Long> getStudentStudyTransactionDuration(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;
	Map<String,Map<String, Long>> getStudentStudyTrends(long userId,String courseNid,long startTime,StudentReportEnum reportParam) throws Exception;	
}
