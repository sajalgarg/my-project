package com.futor.analytics.service.util;

import java.util.Calendar;
import java.util.Date;

import com.futor.analytics.util.DateUtil;
import com.futor.analytics.util.StudentReportEnum;

public class ServiceUtil {
	
	public static String getStartDate(long startTime,StudentReportEnum studentEnum){
		String endDate=null;
		if("WEEK".equalsIgnoreCase(studentEnum.toString())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(startTime));
			cal.add(Calendar.DAY_OF_MONTH, -7);
			endDate = DateUtil.getDate(cal.getTime());
		}else if("MONTH".equalsIgnoreCase(studentEnum.toString())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(startTime));
			cal.add(Calendar.MONTH, -1);
			endDate = DateUtil.getDate(cal.getTime());
			System.out.println(endDate);
		}else if("OVERALL".equalsIgnoreCase(studentEnum.toString())){
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date(startTime));
			cal.add(Calendar.MONTH, -3);
			endDate = DateUtil.getDate(cal.getTime());
			System.out.println(endDate);
		}
		return endDate;
	}

	public static void main(String[] args){
		Date d = new Date();
		getStartDate(d.getTime(),StudentReportEnum.MONTH);
	}
}
