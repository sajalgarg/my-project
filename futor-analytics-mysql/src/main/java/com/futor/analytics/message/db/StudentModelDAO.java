package com.futor.analytics.message.db;

public interface StudentModelDAO {

	long getConceptTime(long userId, String courseNid) throws Exception;
}
