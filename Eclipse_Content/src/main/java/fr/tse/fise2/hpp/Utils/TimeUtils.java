package fr.tse.fise2.hpp.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class TimeUtils {

	/**
	 * Method to convert time in seconds into a date
	 * 
	 * @param unix_seconds : time to convert in seconds
	 * @return the equivalent date
	 */
	public static String unixDate(long unix_seconds) {
		// convert seconds to milliseconds
		Date date = new Date(unix_seconds * 1000);
		// format of the date
		SimpleDateFormat jdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
		jdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		String java_date = jdf.format(date);
		return (String) java_date;
	}

	/**
	 * Method to evaluate the difference in days between two dates
	 * 
	 * @param timeNumberJ : reference time
	 * @param time2 : time to which compare the reference time
	 * @return the difference in days between the two dates
	 */
	public static double differenceBetweeenTwoTimes(Double timeNumberJ, Double time2) {
		double timeDifference = (timeNumberJ - time2) / (3600 * 24);
		return Math.abs(timeDifference);
	}

	/**
	 * Method to evaluate if the difference between two dates in over 14 days
	 * 
	 * @param timeNumberJ : reference time
	 * @param time2 : time to which compare the reference time
	 * @return if the reference time is too old
	 */
	public static boolean isTooOld(Double timeNumberJ, Double time2) {
		double timeDifference = differenceBetweeenTwoTimes(timeNumberJ, time2);
		return (timeDifference < 14 ? false : true);
	}

	/**
	 * Method to evaluate if the difference between two dates in over 7 days
	 * 
	 * @param timeNumberJ : reference time
	 * @param time2 : time to which compare the reference time
	 * @return if the reference time is older than 7 days
	 */
	public static boolean isMoreSevenDayOld(Double timeNumberJ, Double time2) {
		double timeDifference = differenceBetweeenTwoTimes(timeNumberJ, time2);
		return (timeDifference < 7 ? false : true);
	}

}
