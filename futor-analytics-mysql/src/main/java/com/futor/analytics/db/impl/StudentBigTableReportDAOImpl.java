package com.futor.analytics.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.futor.analytics.db.StudentBigTableReportDAO;
import com.futor.analytics.db.util.DBUtil;
import com.futor.analytics.domain.StudentTransactionType;
import com.futor.analytics.domain.StudentTransactionTypeEnum;

public class StudentBigTableReportDAOImpl implements StudentBigTableReportDAO {

	@Autowired
	DataSource	analyticsDataSource;
	
	private String allStudyDurationSql="select sum(actual_time_spent) as study_duration from student_big_table  where uid=? AND date_id BETWEEN ? AND ?";
	private String courseStudyDurationSql="select sum(actual_time_spent) as study_duration from student_big_table  where uid=? AND course_nid=? AND date_id BETWEEN ? AND ?";
	@Override
	public long getStudentStudyDuration(long userId, String courseNid,long startDateId,
			long endDateId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		long totalStudyduration=0;
		try{
			con = analyticsDataSource.getConnection();
			if(courseNid!=null){
				ps = con.prepareStatement(courseStudyDurationSql);
				ps.setLong(1, userId);
				ps.setString(2, courseNid);
				ps.setLong(3, startDateId);
				ps.setLong(4, endDateId);
				
			}else{
				ps = con.prepareStatement(allStudyDurationSql);
				ps.setLong(1, userId);
				ps.setLong(2, startDateId);
				ps.setLong(3, endDateId);
			}
			ps.setLong(1, userId);
			ps.setLong(2, startDateId);
			ps.setLong(3, endDateId);
			rs=ps.executeQuery();
			
			while(rs.next()){
				totalStudyduration =rs.getLong("study_duration");
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		
		return totalStudyduration;
	}

	private String allStudyTransactionDurationSql="select transaction_type,sum(actual_time_spent) from student_big_table where uid=? AND date_id BETWEEN ? AND ? group by transaction_type";
	private String courseStudyTransactionDurationSql="select transaction_type,sum(actual_time_spent) from student_big_table where uid=? AND course_nid=? AND date_id BETWEEN ? AND ? group by transaction_type";
	
	@Override
	public HashMap<String, Long> getStudentStudyTransactionDuration(long userId,String courseNid, long startDateId,
			long endDateId) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		HashMap<String, Long> transactionStudyTime = new HashMap<String, Long>();
		try {
			con = analyticsDataSource.getConnection();
			if(courseNid!=null){
				ps = con.prepareStatement(courseStudyTransactionDurationSql);
				ps.setLong(1, userId);
				ps.setString(2, courseNid);
				ps.setLong(3, startDateId);
				ps.setLong(4, endDateId);
				
			}else{
				ps = con.prepareStatement(allStudyTransactionDurationSql);
				ps.setLong(1, userId);
				ps.setLong(2, startDateId);
				ps.setLong(3, endDateId);
				
			}
			
			rs = ps.executeQuery();
			StudentTransactionType studentTransactionType =new StudentTransactionType();
			while (rs.next()) {
				if("RV".equalsIgnoreCase(rs.getString(1))){
					studentTransactionType.setRevisiontime(rs.getLong(2));
				}else if("PT".equalsIgnoreCase(rs.getString(1))){
					studentTransactionType.setPracticetime(rs.getLong(2));
				}else if("Study".equalsIgnoreCase(rs.getString(1))){
					studentTransactionType.setStudytime(rs.getLong(2));
				}
			}
			
			transactionStudyTime.put(StudentTransactionTypeEnum.REVISION.toString(), studentTransactionType.getRevisiontime());	
			transactionStudyTime.put(StudentTransactionTypeEnum.PRACTICE.toString(), studentTransactionType.getPracticetime());	
			transactionStudyTime.put(StudentTransactionTypeEnum.STUDY.toString(), studentTransactionType.getStudytime());	


		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		} finally {
			DBUtil.freeResources(rs, ps, con);
		}
		return transactionStudyTime;
	}

	private String allStudyWeeklyTrends="select sum(actual_time_spent) as totalTime,transaction_type ,dcd.week_starting_monday from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday,transaction_type order by dcd.week_starting_monday";
	private String courseStudyWeeklyTrends="select sum(actual_time_spent) as totalTime,transaction_type ,dcd.week_starting_monday from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday,transaction_type order by dcd.week_starting_monday";
	@Override
	public Map<String,Map<String, Long>> getStudentStudyTrends(long userId, String courseNid,long startDateId, long endDateId) throws Exception{
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Map<String, Long>> trendsMap = new LinkedHashMap<String,Map<String, Long>>();
		Map<String,StudentTransactionType> studyWeeklyTrendsMap = new LinkedHashMap<String,StudentTransactionType>();
		List<String> list = new ArrayList<String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNid!=null){
				ps = con.prepareStatement(courseStudyWeeklyTrends);
				ps.setLong(1, userId);
				ps.setString(2, courseNid);
				ps.setLong(3, startDateId);
				ps.setLong(4, endDateId);

			}else{
				ps = con.prepareStatement(allStudyWeeklyTrends);
				ps.setLong(1, userId);
				ps.setLong(2, startDateId);
				ps.setLong(3, endDateId);

				
			}
			
			rs = ps.executeQuery();
			
			while(rs.next()){
				String weekId = rs.getString("week_starting_monday");
				if(studyWeeklyTrendsMap.containsKey(weekId)){
					//Map<String,Long> map = studyWeeklyTrendsMap.get(weekId);
					//map.put(rs.getString("transaction_type"),rs.getLong("totalTime"));
					StudentTransactionType studentTransactionType=studyWeeklyTrendsMap.get(weekId);
					if("RV".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setRevisiontime(rs.getLong(2));
					}else if("PT".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setPracticetime(rs.getLong(2));
					}else if("Study".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setStudytime(rs.getLong(2));
					}
					
				}else{
					
					//HashMap<String,Long> map =new HashMap<String, Long>();
					//studyWeeklyTrendsMap.put(weekId, map);
					//map.put(rs.getString("transaction_type"),rs.getLong("totalTime"));

					StudentTransactionType studentTransactionType =new StudentTransactionType();
					list.add(weekId);
					if("RV".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setRevisiontime(rs.getLong(2));
					}else if("PT".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setPracticetime(rs.getLong(2));
					}else if("Study".equalsIgnoreCase(rs.getString(1))){
						studentTransactionType.setStudytime(rs.getLong(2));
					}
					studyWeeklyTrendsMap.put(weekId, studentTransactionType);
				}
				
			}
			
			Iterator itr = list.iterator();
			int counter=1;
			while(itr.hasNext()){
				String weekId=(String)itr.next();
				StudentTransactionType studentTransactionType=studyWeeklyTrendsMap.get(weekId);
				Map<String,Long> tmpMap=new HashMap<String, Long>();
				tmpMap.put(StudentTransactionTypeEnum.STUDY.toString(), studentTransactionType.getStudytime());
				tmpMap.put(StudentTransactionTypeEnum.PRACTICE.toString(), studentTransactionType.getPracticetime());
				tmpMap.put(StudentTransactionTypeEnum.REVISION.toString(), studentTransactionType.getRevisiontime());
				trendsMap.put("Week"+counter, tmpMap);
				counter++;
			}
			
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs,ps,con);
		}
		
		return trendsMap;
	}
	

}
