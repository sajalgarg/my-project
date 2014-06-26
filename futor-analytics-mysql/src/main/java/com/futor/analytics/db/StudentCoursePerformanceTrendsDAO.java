package com.futor.analytics.db;

import java.util.Map;

public interface StudentCoursePerformanceTrendsDAO {

	Map<String,Object> getCoursePerformanceTrends(long userId,String courseNid,long startDateId,long endDateId)throws Exception;
	
}