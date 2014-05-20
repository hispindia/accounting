package org.openmrs.module.accounting.api.utils;

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
}
