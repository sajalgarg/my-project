package com.futor.analytics.db.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import com.futor.analytics.db.StudentCoursePerformanceTrendsDAO;
import com.futor.analytics.db.util.DBUtil;

public class StudentCoursePerformanceTrendsDAOImpl implements
		StudentCoursePerformanceTrendsDAO {

	@Autowired
	DataSource	analyticsDataSource;
	
	private String coursePerfTrendsSql = "SELECT a.course_perf_score AS score,a.week_starting_monday FROM (SELECT course_perf_score,week_starting_monday FROM student_big_table sbt,dimension_calendar_dates dcd WHERE uid=? AND course_nid=? AND sbt.date_id=dcd.date_id AND sbt.date_id BETWEEN ? AND ? ORDER BY dcd.timestamp DESC)a GROUP BY a.week_starting_monday order by a.week_starting_monday";
	
	@Override
	public Map<String, Object> getCoursePerformanceTrends(long userId,
			String courseNid, long startDateId, long endDateId)throws Exception{

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Map<String,Object> coursePerfTrendsMap = new LinkedHashMap<String, Object>();
		Map<String,Double> perfMap = null;
		try{
			System.out.println("StudentCoursePerformanceTrendsDAOImpl : courseNid : "+courseNid + "CourseNid : "+courseNid+" StartDateID :::: "+startDateId+" EndDateId :::: "+endDateId);
			System.out.println("coursePerfTrendsSql Query : "+coursePerfTrendsSql.toString());
			con = analyticsDataSource.getConnection();
			System.out.println("Con : "+con);
			ps = con.prepareStatement(coursePerfTrendsSql);
			int index = 0;
			ps.setLong(++index,userId);
			ps.setString(++index,courseNid);
			ps.setLong(++index,startDateId);
			ps.setLong(++index,endDateId);
			
			rs = ps.executeQuery();
			int weekCnt = 0;
			while(rs.next()){
				perfMap = new LinkedHashMap<String,Double>();
				++weekCnt;
				perfMap.put("score",rs.getDouble("score"));
				coursePerfTrendsMap.put("Week"+weekCnt,perfMap);
			}
		}
		finally{
			DBUtil.freeResources(rs, ps, con);
		}
		return coursePerfTrendsMap;
	}

}
