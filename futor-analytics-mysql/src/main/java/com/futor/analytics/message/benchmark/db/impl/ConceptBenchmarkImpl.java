package com.futor.analytics.message.benchmark.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.util.DBUtil;
import com.futor.analytics.message.benchmark.db.ConceptBenchmark;

public class ConceptBenchmarkImpl implements ConceptBenchmark {

	@Autowired
	DataSource	analyticsDataSource;
	
	String conceptBenchmarkSQL="select AVG(study_time+practice_time) from course_time_benchmark where course_nid=?";
	
	@Override
	public long getConceptBenchmarkTime(String courseNid) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long conceptbenchMarkTime=0;
		try{
			con = analyticsDataSource.getConnection();
			ps = con.prepareStatement(conceptBenchmarkSQL);
			ps.setString(1, courseNid);
			rs=ps.executeQuery();
			while(rs.next()){
				conceptbenchMarkTime=rs.getLong(1);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception();
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return conceptbenchMarkTime;
	}

}
