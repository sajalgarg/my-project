package com.futor.analytics.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.futor.analytics.db.StudentProgressPerformanceReportDAO;
import com.futor.analytics.db.util.DBUtil;


public class StudentProgressPerformanceReportDAOImpl implements StudentProgressPerformanceReportDAO{

	@Autowired
	DataSource	analyticsDataSource;
	
	private String courseConceptsPracticedSql="select (select count(DISTINCT(concept_nid)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? ) as t1, (select count(DISTINCT(base_node_id)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as t2 from dual";
	private String allConceptsPracticedSql="select (select count(DISTINCT(concept_nid)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? ) as t1, (select count(DISTINCT(base_node_id)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as t2 from dual";

	
	@Override
	public Map<String,String> getConceptsPractised(long userId, String courseNId,
			long currentStartDateId, long currentEndDateID, long lastStartDateId,
			long lastEndDateID) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> conceptPractisedMap= new HashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseConceptsPracticedSql);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
				ps.setLong(5, userId);
				ps.setString(6, courseNId);
				ps.setLong(7, lastStartDateId);
				ps.setLong(8, lastEndDateID);
			}else{
				ps = con.prepareStatement(allConceptsPracticedSql);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
				ps.setLong(4, userId);
				ps.setLong(5, lastStartDateId);
				ps.setLong(6, lastEndDateID);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				conceptPractisedMap.put("Current", rs.getString(1));
				conceptPractisedMap.put("Last", rs.getString(2));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return conceptPractisedMap;
	}

	private String courseConceptsRevisedSql="select (select count(DISTINCT(concept_nid)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='RV' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? ) as t1, (select count(DISTINCT(base_node_id)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as t2 from dual";
	private String allConceptsRevisedSql="select (select count(DISTINCT(concept_nid)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='RV'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? ) as t1, (select count(DISTINCT(base_node_id)) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND concept_progress_status=1  AND transaction_type='PT'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as t2 from dual";


	@Override
	public Map<String, String> getConceptsRevised(long userId, String courseNId,
			long currentStartDateId, long currentEndDateID, long lastStartDateId,
			long lastEndDateID) throws Exception {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> conceptRevisedMap= new HashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseConceptsRevisedSql);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
				ps.setLong(5, userId);
				ps.setString(6, courseNId);
				ps.setLong(7, lastStartDateId);
				ps.setLong(8, lastEndDateID);
			}else{
				ps = con.prepareStatement(allConceptsRevisedSql);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
				ps.setLong(4, userId);
				ps.setLong(5, lastStartDateId);
				ps.setLong(6, lastEndDateID);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				conceptRevisedMap.put("Current", rs.getString(1));
				conceptRevisedMap.put("Last", rs.getString(2));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return conceptRevisedMap;
	}

	private String allAttemptHistory="select (select count(is_correct) from student_big_table sbt,dimension_calendar_dates dcd where  uid=? AND is_correct=1   AND base_node_type='question' AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as correct_qs,(select count(is_correct) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND is_correct=0   AND base_node_type='question' AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as uncorrect_qs";
	private String courseAttemptHistory="select (select count(is_correct) from student_big_table sbt,dimension_calendar_dates dcd where  uid=? AND is_correct=1  AND  course_nid=? AND base_node_type='question' AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as correct_qs,(select count(is_correct) from student_big_table sbt,dimension_calendar_dates dcd where uid=? AND is_correct=0  AND  course_nid=? AND base_node_type='question' AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?) as uncorrect_qs";

	@Override
	public Map<String, String> getAttemptHistory(long userId, String courseNId,
			long currentStartDateId, long currentEndDateID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> attemptHistoryMap= new HashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseAttemptHistory);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
				ps.setLong(5, userId);
				ps.setString(6, courseNId);
				ps.setLong(7, currentStartDateId);
				ps.setLong(8, currentEndDateID);
			}else{
				ps = con.prepareStatement(allAttemptHistory);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
				ps.setLong(4, userId);
				ps.setLong(5, currentStartDateId);
				ps.setLong(6, currentEndDateID);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				attemptHistoryMap.put("Correct", rs.getString(1));
				attemptHistoryMap.put("Uncorrect", rs.getString(2));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return attemptHistoryMap;
	}

	private String courseOverallScore="select course_perf_score  from student_big_table sbt,dimension_calendar_dates dcd where  uid=? AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? order by dcd.timestamp desc limit 0,1"; 
	private String allCourseOverallScore="select AVG(b.score) from (select a.course_perf_score as score from (select course_perf_score from student_big_table sbt,dimension_calendar_dates dcd where  uid=?  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ?  order by dcd.timestamp desc)a  group by a.course_nid)b"; 
	@Override
	public double getOverAllScore(long userId, String courseNId,
			long currentStartDateId, long currentEndDateID) throws Exception {
	
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		double score=0;
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseOverallScore);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
			}else{
				ps = con.prepareStatement(allCourseOverallScore);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				score= rs.getDouble(1);
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return score;
	}
	private String allPerformanceStatus="select concept_perf_status,count(concept_perf_status) from student_big_table sbt,dimension_calendar_dates dcd where  uid=? AND course_progress_status=1 AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by concept_perf_status";
	private String coursePerformanceStatus="select concept_perf_status,count(concept_perf_status) from student_big_table sbt,dimension_calendar_dates dcd where  uid=? AND course_nid=? AND course_progress_status=1 AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by concept_perf_status";
	@Override
	public Map<String, String> getPerformanceStatus(long userId,
			String courseNId, long currentStartDateId, long currentEndDateID) throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> overallScoreMap= new HashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(coursePerformanceStatus);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
			}else{
				ps = con.prepareStatement(allPerformanceStatus);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
			}
			
			rs=ps.executeQuery();
			
			while(rs.next()){
				overallScoreMap.put(rs.getString(1), rs.getString(2));
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return overallScoreMap;
	}
	private String courseConceptsPractisedTrends="SELECT COUNT(DISTINCT(concept_nid)),dcd.week_starting_monday FROM student_big_table sbt,dimension_calendar_dates dcd WHERE uid=? AND concept_progress_status=1 AND transaction_type='PT' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday order by dcd.week_starting_monday";
	private String allConceptsPractisedTrends ="SELECT COUNT(DISTINCT(concept_nid)),dcd.week_starting_monday FROM student_big_table sbt,dimension_calendar_dates dcd WHERE uid=? AND concept_progress_status=1 AND transaction_type='PT'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday order by dcd.week_starting_monday";
	@Override
	public Map<String, String> getConceptsPractisedTrends(long userId,
			String courseNId, long currentStartDateId, long currentEndDateID)
			throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> conceptsPractisedMap= new LinkedHashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseConceptsPractisedTrends);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
			}else{
				ps = con.prepareStatement(allConceptsPractisedTrends);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
			}
			
			rs=ps.executeQuery();
			int counter =1;
			while(rs.next()){
				conceptsPractisedMap.put("Week"+counter, rs.getString(1));
				counter++;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return conceptsPractisedMap;
	}
	private String courseConceptsRevisedTrends="SELECT COUNT(DISTINCT(concept_nid)),dcd.week_starting_monday FROM student_big_table sbt,dimension_calendar_dates dcd WHERE uid=? AND concept_progress_status=1 AND transaction_type='RV' AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday order by dcd.week_starting_monday";
	private String allConceptsRevisedTrends="SELECT COUNT(DISTINCT(concept_nid)),dcd.week_starting_monday FROM student_big_table sbt,dimension_calendar_dates dcd WHERE uid=? AND concept_progress_status=1 AND transaction_type='RV'  AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? group by dcd.week_starting_monday order by dcd.week_starting_monday";
	
	@Override
	public Map<String, String> getConceptsRevisedTrends(long userId,
			String courseNId, long currentStartDateId, long currentEndDateID)
			throws Exception {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,String> conceptsRevisedMap= new LinkedHashMap<String,String>();
		try{
			con = analyticsDataSource.getConnection();
			if(courseNId!=null){
				ps = con.prepareStatement(courseConceptsRevisedTrends);
				ps.setLong(1, userId);
				ps.setString(2, courseNId);
				ps.setLong(3, currentStartDateId);
				ps.setLong(4, currentEndDateID);
			}else{
				ps = con.prepareStatement(allConceptsRevisedTrends);
				ps.setLong(1, userId);
				ps.setLong(2, currentStartDateId);
				ps.setLong(3, currentEndDateID);
			}
			
			rs=ps.executeQuery();
			int counter=1;
			while(rs.next()){
				conceptsRevisedMap.put("Week"+counter, rs.getString(1));
				counter++;
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception(ex.getCause());
		}finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return conceptsRevisedMap;
	}

	
	
	
}
