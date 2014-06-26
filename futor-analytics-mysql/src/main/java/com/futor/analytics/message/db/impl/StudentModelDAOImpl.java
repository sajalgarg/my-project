package com.futor.analytics.message.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.util.DBUtil;
import com.futor.analytics.message.db.StudentModelDAO;

public class StudentModelDAOImpl implements StudentModelDAO {

	@Autowired
	DataSource	analyticsDataSource;
	private String studentConceptTimeSql="SELECT AVG(a.total_time) FROM (SELECT SUM(actual_time_spent) total_time FROM student_big_table sbt WHERE uid=? AND course_nid=? AND transaction_type IN (?,?) GROUP BY concept_nid)a";

	@Override
	public long getConceptTime(long userId, String courseNid) throws Exception{
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long averageConceptTime=0;
		try{
			con = analyticsDataSource.getConnection();
			ps = con.prepareStatement(studentConceptTimeSql);
			ps.setLong(1, userId);
			ps.setString(2, courseNid);
			ps.setString(3, "PT");
			ps.setString(4, "Video");
			rs=ps.executeQuery();
			while(rs.next()){
				averageConceptTime=rs.getLong(1);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception();
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return averageConceptTime;
	}

	
}
