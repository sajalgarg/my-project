package com.futor.analytics.db;

import java.util.HashMap;
import java.util.Map;

public interface StudentBigTableReportDAO {

	long getStudentStudyDuration(long userId,String courseNid,long startDateId,long endDateID) throws Exception;
	HashMap<String, Long> getStudentStudyTransactionDuration(long userId,String courseNid,long startDateId,long endDateID) throws Exception;
	Map<String,Map<String, Long>> getStudentStudyTrends(long userId,String courseNid,long startDateId,long endDateID) throws Exception;
}
