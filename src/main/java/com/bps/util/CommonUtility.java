package com.bps.util;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

import com.bps.persistence.tables.LifeCycle;

public class CommonUtility {

	public static LifeCycle getLifeCycle(Operation operation, String user) {
		LifeCycle lifeCycle = new LifeCycle();
		Calendar c = Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH);
		switch (operation) {
		case CREATE:
			lifeCycle.setCreatedOn(c);
			lifeCycle.setUpdatedOn(c);
			lifeCycle.setCreatedBy(user);
			lifeCycle.setUpdatedBy(user);
			break;
		case UPDATE:
			lifeCycle.setUpdatedOn(c);
			lifeCycle.setUpdatedBy(user);
			break;
		default:
			break;
		}
		return lifeCycle;
	}

	/*private static String getCurrentTime() {
		Calendar s = Calendar.getInstance(TimeZone.getTimeZone(CommonConstants.UTC), Locale.ENGLISH);
		DateFormat df = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss z");
		df.setTimeZone(TimeZone.getTimeZone("UTC"));
		return df.format(s.getTime());
	}*/
}
