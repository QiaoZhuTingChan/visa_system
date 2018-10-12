package com.jyd.bms.tool;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jyd.bms.common.Environment;

public class DateUtils {
	private static Logger log;
	private static final String datePattern;
	private static final String timestampPattern;
	private static final String timePattern;
	private static final String timestampNoSecondPattern;
	private static final String timeNoSecondPattern;
	private static final String yearMonthPattern;
	private static final String yearMonthNoSeparatorPattern;
	private static final double LOCAL_UTC_TIME;

	static {
		DateUtils.log = LoggerFactory.getLogger((Class) DateUtils.class);
		datePattern = Environment.CONFIGURATION.getString("global.dateFormat");
		timestampPattern = Environment.CONFIGURATION.getString("global.dateTimeFormat");
		timePattern = Environment.CONFIGURATION.getString("global.timeFormat");
		timestampNoSecondPattern = Environment.CONFIGURATION.getString("global.dateTimeFormat1");
		timeNoSecondPattern = Environment.CONFIGURATION.getString("global.timeFormat1");
		yearMonthPattern = Environment.CONFIGURATION.getString("global.yearMonthFormat");
		yearMonthNoSeparatorPattern = Environment.CONFIGURATION.getString("global.yearMonthNoSeparatorFormat");
		LOCAL_UTC_TIME = Environment.CONFIGURATION.getDouble("local.utc.time");
	}

	public static Date parseDate(final String date) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(date, new String[] { DateUtils.datePattern });
		} catch (Exception ex) {
			DateUtils.log.error("Date format occurs error .", (Throwable) ex);
			return null;
		}
	}

	public static String formatDate(final Date date) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, DateUtils.datePattern);
	}

	@Deprecated
	public static String format(final Date date) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, DateUtils.datePattern);
	}

	public static Date parseTimestamp(final String timestamp) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(timestamp,
					new String[] { DateUtils.timestampPattern });
		} catch (Exception ex) {
			DateUtils.log.error("Date format occurs error .", (Throwable) ex);
			return null;
		}
	}

	public static String formatTimestamp(final Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		return DateFormatUtils.format((Date) timestamp, DateUtils.timestampPattern);
	}

	public static Date parseTime(final String time) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(time, new String[] { DateUtils.timePattern });
		} catch (Exception ex) {
			DateUtils.log.error("Date format occurs error .", (Throwable) ex);
			return null;
		}
	}

	public static String formatTime(final Time time) {
		if (time == null) {
			return "";
		}
		return DateFormatUtils.format((Date) time, DateUtils.timePattern);
	}

	public static Date parseYearMonth(final String yearMonth) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(yearMonth,
					new String[] { DateUtils.yearMonthPattern });
		} catch (Exception ex) {
			DateUtils.log.error("Date format occurs error .", (Throwable) ex);
			return null;
		}
	}

	public static String formatYearMonth(final Date date) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, DateUtils.yearMonthPattern);
	}

	public static Date parseYearMonthNoSeparator(final String yearMonthNoSeparator) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(yearMonthNoSeparator,
					new String[] { DateUtils.yearMonthNoSeparatorPattern });
		} catch (Exception ex) {
			DateUtils.log.error("Date format occurs error .", (Throwable) ex);
			return null;
		}
	}

	public static String formatYearMonthNoSeparator(final Date date) {
		if (date == null) {
			return "";
		}
		return DateFormatUtils.format(date, DateUtils.yearMonthNoSeparatorPattern);
	}

	public static String formatTimestampNoSecond(final Timestamp timestamp) {
		if (timestamp == null) {
			return "";
		}
		return DateFormatUtils.format((Date) timestamp, DateUtils.timestampNoSecondPattern);
	}

	public static String formatTime(final Timestamp time) {
		if (time == null) {
			return "";
		}
		return DateFormatUtils.format((Date) time, DateUtils.timePattern);
	}

	public static String formatTimeNoSecond(final Timestamp time) {
		if (time == null) {
			return "";
		}
		return DateFormatUtils.format((Date) time, DateUtils.timeNoSecondPattern);
	}

	public static Date parseTimeNoSecond(final String time) {
		try {
			return org.apache.commons.lang.time.DateUtils.parseDate(time,
					new String[] { DateUtils.timeNoSecondPattern });
		} catch (ParseException e) {
			DateUtils.log.error("parse time no second occurs error .", (Throwable) e);
			return null;
		}
	}

	public static void trimToDate(final Calendar calendar) {
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		calendar.set(14, 0);
	}

	public static Timestamp rollDays(final int n) {
		final long dayMils = 86400000L;
		return new Timestamp(System.currentTimeMillis() + dayMils * n);
	}

	public static List<String> getYearMonthsOfDateInterval(final Date first, final Date end) {
		final List<String> dateList = new ArrayList<String>();
		final Calendar firstCalendar = Calendar.getInstance();
		final Calendar endCalendar = Calendar.getInstance();
		firstCalendar.setTime(first);
		endCalendar.setTime(end);
		firstCalendar.add(2, 1);
		endCalendar.add(2, -1);
		while (!firstCalendar.after(endCalendar)) {
			dateList.add(DateFormatUtils.format(firstCalendar.getTime(),
					Environment.CONFIGURATION.getString("global.yearMonthFormat")));
			firstCalendar.add(2, 1);
		}
		return dateList;
	}

	public static Date getFirstOrEndDayOfMonth(final boolean bool) {
		return getFirstOrEndDayOfMonth(new Date(), bool);
	}

	public static Date getFirstOrEndDayOfMonth(final Date date, final boolean bool) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		trimToDate(calendar);
		calendar.set(5, 1);
		if (!bool) {
			calendar.roll(2, 1);
			calendar.roll(6, -1);
		}
		return calendar.getTime();
	}

	public static Date formatDate(final Date initDate, final int days) {
		final Calendar strCal = new GregorianCalendar();
		Date formatDate = null;
		strCal.setTime(initDate);
		final Calendar endCal = new GregorianCalendar(strCal.get(1), strCal.get(2), strCal.get(5) + days);
		if (endCal != null) {
			formatDate = endCal.getTime();
		}
		return formatDate;
	}

	public static String getTaiwanTime(final Date date) {
		return getTaiwanYearMonth(date, "\u5e74", "\u6708");
	}

	public static String getTaiwanYearMonth(Date date, final String midfix, final String suffix) {
		if (date == null) {
			date = new Date();
		}
		final Calendar c = Calendar.getInstance();
		c.setTime(date);
		final int year = c.get(1) - 1911;
		final int month = c.get(2) + 1;
		String result = String.valueOf(year) + midfix + month;
		if (suffix != null) {
			result = String.valueOf(result) + suffix;
		}
		return result;
	}

	public static Date addYears(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addYears(date, amount);
	}

	public static Date addMonths(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addMonths(date, amount);
	}

	public static Date addWeeks(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addWeeks(date, amount);
	}

	public static Date addDays(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addDays(date, amount);
	}

	public static Date addHours(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addHours(date, amount);
	}

	public static Date addMinutes(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addMinutes(date, amount);
	}

	public static Date addSeconds(final Date date, final int amount) {
		return org.apache.commons.lang.time.DateUtils.addSeconds(date, amount);
	}

	public static Date getToday() {
		final Calendar calendar = Calendar.getInstance();
		calendar.clear();
		final Calendar today = Calendar.getInstance();
		calendar.set(today.get(1), today.get(2), today.get(5));
		return calendar.getTime();
	}

	public static boolean isSameYear(final Date date1, final Date date2) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(1) == cal2.get(1);
	}

	public static boolean isSameMonth(final Date date1, final Date date2) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);
		final Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		return cal1.get(2) == cal2.get(2);
	}

	public static boolean isSameDay(final Date date1, final Date date2) {
		return org.apache.commons.lang.time.DateUtils.isSameDay(date1, date2);
	}

	public static boolean isSameSeason(final Date date1, final Date date2) {
		return getSeason(date1).equals(getSeason(date2));
	}

	public static boolean isSameBiannual(final Date date1, final Date date2) {
		return getBiannual(date1).equals(getBiannual(date2));
	}

	public static boolean isLeapYear(final Date date) {
		final Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		final int year = cal.get(1);
		return (year % 4 == 0 && year % 100 != 0) || year % 400 == 0;
	}

	public static Season getSeason(final Date date) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		final int month = cal1.get(2);
		if (month >= 0 && month <= 2) {
			return Season.SPRING;
		}
		if (month >= 3 && month <= 5) {
			return Season.SUMMER;
		}
		if (month >= 6 && month <= 8) {
			return Season.FALL;
		}
		return Season.WINTER;
	}

	public static Biannual getBiannual(final Date date) {
		final Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date);
		final int month = cal1.get(2);
		if (month >= 0 && month <= 5) {
			return Biannual.FIRST_HALF_YEAR;
		}
		return Biannual.SECOND_HALF_YEAR;
	}

	public static DateFormatType getDateFormatType(final String date) {
		Date result = null;
		try {
			result = org.apache.commons.lang.time.DateUtils.parseDate(date,
					new String[] { DateUtils.timestampPattern });
			return DateFormatType.DATE_TIME_FORMAT;
		} catch (Exception e1) {
			try {
				result = org.apache.commons.lang.time.DateUtils.parseDate(date, new String[] { DateUtils.datePattern });
				return DateFormatType.DATE_FORMAT;
			} catch (Exception e2) {
				try {
					result = org.apache.commons.lang.time.DateUtils.parseDate(date,
							new String[] { DateUtils.timePattern });
					return DateFormatType.TIME_FORMAT;
				} catch (Exception e3) {
					try {
						result = org.apache.commons.lang.time.DateUtils.parseDate(date,
								new String[] { DateUtils.yearMonthPattern });
						return DateFormatType.YEAR_MOTNH_FORMAT;
					} catch (Exception e4) {
						try {
							result = org.apache.commons.lang.time.DateUtils.parseDate(date,
									new String[] { DateUtils.yearMonthNoSeparatorPattern });
							return DateFormatType.YEAR_MOTNH_NO_SEPARATOR_FORMAT;
						} catch (Exception e5) {
							return DateFormatType.ERROR_FORMAT;
						}
					}
				}
			}
		}
	}

	public static int getDays(final Date startDate, final Date endDate) {
		return (int) (Math.abs((endDate.getTime() - startDate.getTime()) / 86400000L) + 1L);
	}

	public static boolean checkDatesIsRepetition(final List<Date> startDates, final List<Date> endDates) {
		final int startDatesSize = startDates.size();
		if (startDatesSize > 0 && startDatesSize == endDates.size()) {
			java.sql.Date startDate1 = null;
			java.sql.Date endDate1 = null;
			java.sql.Date startDate2 = null;
			java.sql.Date endDate2 = null;
			for (int i = 0; i < startDatesSize; ++i) {
				startDate1 = new java.sql.Date(startDates.get(i).getTime());
				endDate1 = new java.sql.Date(endDates.get(i).getTime());
				if (startDate1.after(endDate1)) {
					DateUtils.log.debug("===startDate: {}, endDate: {}====", (Object) startDate1, (Object) endDate1);
					return true;
				}
				for (int j = i + 1; j < startDatesSize; ++j) {
					startDate2 = new java.sql.Date(startDates.get(j).getTime());
					endDate2 = new java.sql.Date(endDates.get(j).getTime());
					if ((!startDate1.after(startDate2) || !startDate1.after(endDate2))
							&& (!endDate1.before(startDate2) || !endDate1.before(endDate2))) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public static boolean checkTimestampsIsRepetition(final List<Timestamp> startDates,
			final List<Timestamp> endDates) {
		final int startDatesSize = startDates.size();
		if (startDatesSize > 0 && startDatesSize == endDates.size()) {
			Timestamp startDate1 = null;
			Timestamp endDate1 = null;
			Timestamp startDate2 = null;
			Timestamp endDate2 = null;
			for (int i = 0; i < startDatesSize; ++i) {
				startDate1 = startDates.get(i);
				endDate1 = endDates.get(i);
				if (startDate1.compareTo(endDate1) >= 0) {
					return true;
				}
				for (int j = i + 1; j < startDatesSize; ++j) {
					startDate2 = startDates.get(j);
					endDate2 = endDates.get(j);
					if ((startDate1.compareTo(startDate2) > 0 || endDate1.compareTo(startDate2) > 0)
							&& (startDate1.compareTo(endDate2) < 0 || endDate1.compareTo(endDate2) < 0)) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

	public static String getTaiwanDayOfWeek(final Date date) {
		return DateFormatUtils.format(date, "E", Locale.TAIWAN).substring(2);
	}

	public static Date getFirstOrEndDayOfYear(final boolean bool) {
		return getFirstOrEndDayOfYear(new Date(), bool);
	}

	public static Date getFirstOrEndDayOfYear(final Date date, final boolean bool) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		trimToDate(calendar);
		if (bool) {
			calendar.set(6, 1);
		} else {
			calendar.roll(1, 1);
			calendar.set(6, 0);
		}
		return calendar.getTime();
	}

	public static int getMonths(final Date startDate, final Date endDate) {
		final Calendar calendar = Calendar.getInstance();
		calendar.setTime(startDate);
		final int startYear = calendar.get(1);
		final int startMonth = calendar.get(2);
		calendar.setTime(endDate);
		final int endYear = calendar.get(1);
		final int endMonth = calendar.get(2);
		int result = 0;
		if (startYear != endYear) {
			result = (endYear - startYear) * 12;
		}
		result += endMonth - startMonth;
		return result;
	}

	public static Timestamp changeLocalTimeToUtcTime(final double outerUtcTime) {
		return changeToLocalUtcTime(null, outerUtcTime);
	}

	public static Timestamp changeToLocalUtcTime(final Date outerDate, final double outerUtcTime) {
		Date result = null;
		double difference = 0.0;
		if (outerDate == null) {
			result = new Date();
			difference = outerUtcTime - DateUtils.LOCAL_UTC_TIME;
		} else {
			result = new Date(outerDate.getTime());
			difference = DateUtils.LOCAL_UTC_TIME - outerUtcTime;
		}
		if (difference != 0.0) {
			final int hour = (int) (Object) new Double(difference);
			result = org.apache.commons.lang.time.DateUtils.addHours(result, hour);
			final double mod = difference % 1.0;
			if (mod != 0.0) {
				final int minutes = (int) (Object) new Double(mod * 60.0);
				result = org.apache.commons.lang.time.DateUtils.addMinutes(result, minutes);
			}
		}
		return new Timestamp(result.getTime());
	}

	public static Timestamp getCurrentStandardUtcTime() {
		return changeLocalTimeToStandardUtcTime(new Date());
	}

	public static Timestamp changeLocalTimeToStandardUtcTime(final Date localTime) {
		final double difference = 0.0 - DateUtils.LOCAL_UTC_TIME;
		Date result = null;
		final int hour = (int) (Object) new Double(difference);
		result = org.apache.commons.lang.time.DateUtils.addHours(localTime, hour);
		final double mod = difference % 1.0;
		if (mod != 0.0) {
			final int minutes = (int) (Object) new Double(mod * 60.0);
			result = org.apache.commons.lang.time.DateUtils.addMinutes(result, minutes);
		}
		return new Timestamp(result.getTime());
	}

	public static Timestamp changeUtcStandardTimeToLocalTime(final Date utcStandardTime) {
		final int hour = (int) (Object) new Double(DateUtils.LOCAL_UTC_TIME);
		Date result = org.apache.commons.lang.time.DateUtils.addHours(utcStandardTime, hour);
		final double mod = DateUtils.LOCAL_UTC_TIME % 1.0;
		if (mod != 0.0) {
			final int minutes = (int) (Object) new Double(mod * 60.0);
			result = org.apache.commons.lang.time.DateUtils.addMinutes(result, minutes);
		}
		return new Timestamp(result.getTime());
	}

	enum Biannual {
		FIRST_HALF_YEAR("FIRST_HALF_YEAR", 0, "first-half-year"), SECOND_HALF_YEAR("SECOND_HALF_YEAR", 1,
				"second-half-year");

		private String biannual;

		private Biannual(final String s, final int n, final String biannual) {
			this.biannual = biannual;
		}
	}

	enum DateFormatType {
		DATE_TIME_FORMAT("DATE_TIME_FORMAT", 0, "dateTimeFormat"), DATE_FORMAT("DATE_FORMAT", 1,
				"dateFormat"), TIME_FORMAT("TIME_FORMAT", 2, "timeFormat"), YEAR_MOTNH_FORMAT("YEAR_MOTNH_FORMAT", 3,
						"yearMonthFormat"), YEAR_MOTNH_NO_SEPARATOR_FORMAT("YEAR_MOTNH_NO_SEPARATOR_FORMAT", 4,
								"yearMonthNoSeparatorFormat"), DATE_TIME_NO_SECOND_FORMAT("DATE_TIME_NO_SECOND_FORMAT",
										5, "timestampNoSecondPattern"), TIME_NO_SECOND_FORMAT("TIME_NO_SECOND_FORMAT",
												6,
												"timeNoSecondPattern"), ERROR_FORMAT("ERROR_FORMAT", 7, "ERROR_FORMAT");

		private String dateFormatType;

		private DateFormatType(final String s, final int n, final String dateFormatType) {
			this.dateFormatType = dateFormatType;
		}
	}

	enum Season {
		SPRING("SPRING", 0, "spring"), SUMMER("SUMMER", 1, "summer"), FALL("FALL", 2, "fall"), WINTER("WINTER", 3,
				"winter");

		private String season;

		private Season(final String s, final int n, final String season) {
			this.season = season;
		}
	}

	public static int countAge(Date birthday) throws Exception {
		if (birthday == null) {
			return 0;
		}
		Calendar cal = Calendar.getInstance();
		if (cal.before(birthday)) {
			throw new IllegalArgumentException("The birthday is invalid!");
		}
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);
		int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
		cal.setTime(birthday);

		int yearBirth = cal.get(Calendar.YEAR);
		int monthBirth = cal.get(Calendar.MONTH);
		int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

		int age = yearNow - yearBirth;

		if (monthNow <= monthBirth) {
			if (monthNow == monthBirth) {
				if (dayOfMonthNow < dayOfMonthBirth) {
					age--;
				}
			} else {
				age--;
			}
		}
		return age;
	}

	public static String countWorkLength(Date workDate) throws Exception {
		Calendar cal = Calendar.getInstance();
		if (cal.before(workDate)) {
			throw new IllegalArgumentException("The workDate is invalid!");
		}
		Date date = new Date();
		cal.setTime(date);
		int yearNow = cal.get(Calendar.YEAR);
		int monthNow = cal.get(Calendar.MONTH);

		cal.setTime(workDate);
		int yearWork = cal.get(Calendar.YEAR);
		int monthWork = cal.get(Calendar.MONTH);

		int year = yearNow - yearWork;
		int month = monthWork - monthNow;
		if (month < 0) {
			year = year - 1;
			month = month + 12;
		}

		String workLength = String.valueOf(year) + "年" + String.valueOf(month) + "月";
		return workLength;
	}

	/**
	 * 设置月份第一天和最后一天
	 * 
	 * @param yearMonth
	 *            年月
	 * @param firstDate
	 *            开始日期
	 * @param endDate
	 *            结束日期
	 * @throws ParseException
	 */
	/*public static void setMonthFirstDateAndEndDate(String yearMonth, Date firstDate, Date endDate)
			throws ParseException {
		String year = yearMonth.substring(0, 4);
		String month = yearMonth.substring(4, 6);

		DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String dateString = year + "-" + month + "-01";

		try {
			firstDate = formatter.parse(dateString);
		} catch (ParseException e) {
			throw e;
		}

		Calendar calendar = new GregorianCalendar();
		calendar.setTime(firstDate);
		calendar.add(calendar.DAY_OF_MONTH, 1);
		calendar.add(calendar.DATE, -1);
		endDate = calendar.getTime();
	}*/
}
