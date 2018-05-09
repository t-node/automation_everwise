package ui.utils;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

import org.joda.time.DateTime;

import com.google.common.collect.Ordering;


import ui.BrowserElementImpl;
import ui.support.Config;

public class Tools {
	private static final org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(BrowserElementImpl.class);

	public static synchronized void runUGM() {
		try {
			String result = new HTTPClient().get(Config.getToolsURL() + "/ugm").toLowerCase().indexOf("success") >= 0
					? "UGM Ran Successfully"
					: "UGM Failed";
			// logger.info("ThreadID: "+Thread.currentThread().getId()+"
			// "+Thread.currentThread().getName()+" "+(result);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Deprecated
	public static String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("M/d/yyyy hh:");
		Date date = new Date();
		return dateFormat.format(date);

	}

	public static void changeTimeZone(String timeZone) {
		TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
		System.out.println(Calendar.getInstance().getTimeZone().getDisplayName());
		try {
			Process p = Runtime.getRuntime()
					.exec("tzutil /s \"" + Calendar.getInstance().getTimeZone().getDisplayName() + "\"");
			p.waitFor();
			System.out.println("Done.");
		} catch (Exception err) {
			err.printStackTrace();
		}
	}

	public static String getDateTime(String format) {
		return new SimpleDateFormat(format).format(new Date());

	}

	@Deprecated
	public static String getDateTime1() {
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:");
		Date date = new Date();

		return dateFormat.format(date);

	}

	public static String getSystemTimeZone(boolean modify) {
		String timeZone = DateTime.now().toString().substring(DateTime.now().toString().length() - 6,
				DateTime.now().toString().length());
		if (modify) {
			if (Integer.parseInt(timeZone.split(":")[0].substring(1)) < 10) {
				return timeZone.split(":")[0].substring(0, 1) + timeZone.split(":")[0].substring(2, 3) + ":"
						+ timeZone.split(":")[1];
			}
		}
		return timeZone;
	}

	public static String getSystemTimeZone() {
		return Calendar.getInstance().getTimeZone().getDisplayName();
	}

	public static String getTimeZone() {
		TimeZone tz = Calendar.getInstance().getTimeZone();
		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset()) - TimeUnit.HOURS.toMinutes(hours);
		// avoid -4:-30 issue
		minutes = Math.abs(minutes);

		String result = "";
		if (hours > 0) {
			result = String.format("(GMT+%d:%02d) %s", hours, minutes, tz.getID());
		} else {
			result = String.format("(GMT%d:%02d) %s", hours, minutes, tz.getID());
		}

		return result;

	}

	public static String getCurrentDate(String format) {
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static String getDateTime(int time, int minute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.HOUR_OF_DAY, time);
		cal.add(Calendar.MINUTE, minute);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(cal.getTime());
	}

	public static String getMilliSecondsForDate(String format, String date) {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		try {
			return simpleDateFormat.parse(date).getTime() + "";
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "0";
	}

	public static boolean isListSorted(List<String> list) {
		return Ordering.natural().isOrdered(list);

	}

	public static boolean inDescOrder(List<Long> originalList) {
		return IntStream.range(0, originalList.size() - 1)
				.allMatch(i -> originalList.get(i).compareTo(originalList.get((i + 1))) >= 0);

	}

	public static boolean inAscOrder(List<Long> originalList) {
		return IntStream.range(0, originalList.size() - 1)
				.allMatch(i -> originalList.get(i).compareTo(originalList.get(i + 1)) <= 0);

	}

	public static boolean inDescendingOrder(List<String> originalList) {
		return IntStream.range(0, originalList.size() - 1)
				.allMatch(i -> originalList.get(i).compareTo(originalList.get(i + 1)) >= 0);

	}

	public static boolean inAscendingOrder(List<String> originalList) {
		return IntStream.range(0, originalList.size() - 1)
				.allMatch(i -> originalList.get(i).compareTo(originalList.get(i + 1)) <= 0);

	}


}
