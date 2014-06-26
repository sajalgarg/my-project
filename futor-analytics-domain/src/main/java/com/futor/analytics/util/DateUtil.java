package com.futor.analytics.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

	public static String getCurrentDate(){
		Date date = Calendar.getInstance().getTime();
		return dateFormat.format(date);
	}
	
	public static String getDate(Date date){
		return dateFormat.format(date);
	}
	
	public static Date getDate(String date){
		try {
			return dateFormat.parse(date);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
}
