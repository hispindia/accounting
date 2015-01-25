package org.openmrs.module.accounting.api.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {
	
	public static Date getEnd(Date date) {
		if (date == null) {
			return null;
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return c.getTime();
	}
	
	public static Date getDateFromStr(String date) {
		Date result = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			sdf.setLenient(false);
			result = sdf.parse(date);
		}
		catch (Exception e) {
			System.out.println("Error: " + e.toString());
		}
		
		return result;
	}
	
	public static Date addDate(Date date, int number) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, number);
		return cal.getTime();
	}
	
	public static Date getDatePart(Date date){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c.getTime();
	}
	
	public static String getStringFromDate(Date date){
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		return formatter.format(date);
	}
	
	public static boolean isFutureDate(Date date) {
		Date curDate = Calendar.getInstance().getTime();
		if (curDate.compareTo(date) < 0 ) {
			return true;
		} else {
			return false;
		}
				
	}
	public static boolean isCurrentPeriod(Date startDate, Date endDate) {
		Date cur = Calendar.getInstance().getTime();
		// start <= cur  <= end 
		// start > cur || end < cur
		
		if (startDate.compareTo(cur) > 0 || endDate.compareTo(cur) < 0 ) 
			return false;
		else 
			return true;
	}
}
