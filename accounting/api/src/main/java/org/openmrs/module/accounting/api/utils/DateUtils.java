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
}
