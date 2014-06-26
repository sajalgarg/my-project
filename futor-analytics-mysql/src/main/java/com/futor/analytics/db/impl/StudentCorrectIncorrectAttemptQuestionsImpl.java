package com.futor.analytics.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.StudentCorrectIncorrectAttemptQuestionsDAO;
import com.futor.analytics.db.util.DBUtil;


public class StudentCorrectIncorrectAttemptQuestionsImpl implements StudentCorrectIncorrectAttemptQuestionsDAO{
	@Autowired
	DataSource	analyticsDataSource;
	@Override
	public Map<String,Object> correctIncorrectQuestionDetails(String courseId,long userId) throws Exception{
		System.out.println("futor analytics db impl");
		System.out.println("public Map<String,Object> correctIncorrectQuestionDetails(String courseId,String userId"+courseId+"userId"+userId);
		Map<String,Object> correctincorrectMap=new HashMap<String,Object>();
		Connection connection=null;
		ResultSet rs=null;
		PreparedStatement ps=null;
		String sql="SELECT  (SELECT COUNT(is_correct)FROM   student_big_table where uid=? and course_nid=? and is_correct='0') AS correct,(SELECT COUNT(is_correct)FROM   student_big_table where uid=? and course_nid=? and is_correct='1') AS Incorrect FROM dual";
		try{
			connection=analyticsDataSource.getConnection();
			ps=connection.prepareStatement(sql);
			ps.setLong(1, userId);
			ps.setString(2, courseId);
			ps.setLong(3, userId);
			ps.setString(4,courseId);
			rs=ps.executeQuery();
			if(rs.next())
			{
				correctincorrectMap.put("correct", rs.getString(1));
				correctincorrectMap.put("Incorrect", rs.getString(2));
			}

		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, connection);
		}
		return correctincorrectMap;
		
	}
}
