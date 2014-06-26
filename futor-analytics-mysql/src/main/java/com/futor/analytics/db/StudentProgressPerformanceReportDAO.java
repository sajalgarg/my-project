package com.futor.analytics.db;

import java.util.Map;

public interface StudentProgressPerformanceReportDAO {

	Map<String,String> getConceptsPractised(long userId,String courseNId,long currentStartDateId,long currentEndDateID,long lastStartDateId,long lastEndDateID) throws Exception;
	Map<String,String> getConceptsRevised(long userId,String courseNId,long currentStartDateId,long currentEndDateID,long lastStartDateId,long lastEndDateID) throws Exception;
	Map<String,String> getAttemptHistory(long userId,String courseNId,long currentStartDateId,long currentEndDateID) throws Exception;
	double getOverAllScore(long userId,String courseNId,long currentStartDateId,long currentEndDateID) throws Exception;
	Map<String,String> getPerformanceStatus(long userId,String courseNId,long currentStartDateId,long currentEndDateID) throws Exception;
	Map<String,String> getConceptsPractisedTrends(long userId,String courseNId,long currentStartDateId,long currentEndDateID) throws Exception;
	Map<String,String> getConceptsRevisedTrends(long userId,String courseNId,long currentStartDateId,long currentEndDateID) throws Exception;

}
