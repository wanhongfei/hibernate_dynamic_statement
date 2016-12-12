package com.jd.vf.hibernate.dystatement.util;

import lombok.Getter;
import lombok.NonNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @author wwhhf
 * @comment 时间工具类
 * @since 2016年6月13日
 */
public class DateUtil {

	/**
	 * date->java.sql.Date
	 *
	 * @param date
	 * @return
	 */
	public static java.sql.Date date2sqlDate(Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * date->java.sql.Date
	 *
	 * @param date
	 * @return
	 */
	public static Date sqlDate2Date(java.sql.Date date) {
		return new Date(date.getTime());
	}

	/**
	 * 改变时间格式(date)
	 *
	 * @param time
	 * @return
	 * @throws ParseException
	 * @author wwhhff11
	 * @since 2016/03/02
	 */
	public static Date string2Date(@NonNull String time, @NonNull DatePatternEnum pattern)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern.getPattern());
		return format.parse(time);
	}

	/**
	 * 改变时间格式(date)
	 *
	 * @param time
	 * @return
	 * @throws ParseException
	 * @author wwhhff11
	 * @since 2016/03/02
	 */
	public static Date string2Date(@NonNull String time, @NonNull String pattern)
			throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.parse(time);
	}

	/**
	 * 改变时间格式(String)
	 *
	 * @param date
	 * @author wwhhff11
	 * @returnformat
	 * @since 2016/03/02
	 */
	public static String date2String(@NonNull Date date, @NonNull DatePatternEnum pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern.getPattern());
		return format.format(date);
	}

	/**
	 * 改变时间格式(String)
	 *
	 * @param date
	 * @author wwhhff11
	 * @returnformat
	 * @since 2016/03/02
	 */
	public static String date2String(@NonNull Date date, @NonNull String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * whf 得到服务器当前的时间
	 *
	 * @return
	 */
	public static Date getCurrentDate() {
		Calendar calendar = Calendar.getInstance();
		return calendar.getTime();
	}

	/**
	 * @param timeUnit
	 * @param time
	 * @return
	 * @throws Exception
	 * @author wwhhf
	 * @comment
	 * @since 2016年6月11日
	 */
	public static Long time2Millisecond(@NonNull TimeUnit timeUnit,
	                                    @NonNull Long time)
			throws Exception {
		if (timeUnit.compareTo(TimeUnit.SECONDS) == 0) {
			time = time * 1000;
		} else if (timeUnit.compareTo(TimeUnit.MINUTES) == 0) {
			time = time * 60 * 1000;
		} else if (timeUnit.compareTo(TimeUnit.HOURS) == 0) {
			time = time * 60 * 60 * 1000;
		} else if (timeUnit.compareTo(TimeUnit.DAYS) == 0) {
			time = time * 24 * 60 * 60 * 1000;
		} else {
			throw new Exception("time can't trans to Millisecond");
		}
		return time;
	}

	/**
	 * 获取当前年份
	 *
	 * @param date
	 * @return
	 */
	public static String getCurrentYear(@NonNull Date date) {
		return date.getYear() + 1900 + "";
	}

	/**
	 * Created by hongfei.whf on 2016/8/31.
	 */
	public static enum DatePatternEnum {

		LINE_ALL("yyyy-MM-dd HH:mm:ss"),
		SLASH_ALL("yyyy/MM/dd HH:mm:ss"),
		LINE_DATE("yyyy-MM-dd"),
		SLASH_DATE("yyyy/MM/dd"),
		HOUR_MINUTE_SECOND("HH:mm:ss");

		@Getter
		private String pattern;

		DatePatternEnum(String pattern) {
			this.pattern = pattern;
		}

		@Override
		public String toString() {
			return "DatePatternEnum{" +
					"pattern='" + pattern + '\'' +
					'}';
		}
	}

}
