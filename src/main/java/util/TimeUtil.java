package util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {

	public static int getTodayWay() {

		Calendar calendar = Calendar.getInstance();
		int way = calendar.get(Calendar.DAY_OF_WEEK)-1;
		if (way == 0) {
			way = 7;
		}
		return way;
	}
	
	public static int getTomorrowWay() {
		int way = getTodayWay();
		way++;
		if(way==8) {
			way = 1;
		}
		return way;
	}


	//增加num天
	public static Date addDay(Date date, int num) {
		Calendar startDT = Calendar.getInstance();
		startDT.setTime(date);
		startDT.add(Calendar.DATE, num);
		return startDT.getTime();
	}

	
	
	//获得两个时间相差的毫秒
	public static long getDiffTimeMills(int hour,int minute,int nowhour,int nowminute) {
		
		if(hour<nowhour||hour==nowhour&&minute<nowminute) {
			return -1;
		}
		
		if(hour==nowhour) {
			return (minute-nowminute)*60*1000;
		}
		
		if(minute<nowminute) {
			minute+=60;
			hour--;
			return (hour-nowhour)*60*60*1000+(minute-nowminute)*60*1000;
		}
		
		return (hour-nowhour)*60*60*1000+(minute-nowminute)*60*1000;
		
	}
	
	
	public static String getNowTime() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String timeString = simpleDateFormat.format(date);
		return timeString;

		
	}
	
	public static String getTodayTimeStr() {
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String timeString = simpleDateFormat.format(date);
		return timeString;

	}

	public static String getTomorrowTimeStr() {
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(calendar.DATE, 1);
		date = calendar.getTime();

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String timeString = simpleDateFormat.format(date);
		return timeString;

	}

	// 获得时间转化代码
	public static int getStartTimeCode(int way, int timeid) {

		int code = -1;
		if (way == 2) {
			if (timeid == 1) {
				code = 9;
			} else if (timeid == 2) {
				code = 16;
			} else {
				code = 19;
			}

		} else {
			if (timeid == 1) {
				code = 9;
			} else if (timeid == 2) {
				code = 14;
			} else {
				code = 19;
			}
		}
		return code;
	}

	// 获得时间转化代码
	public static int getEndTimeCode(int way, int timeid) {

		int code = -1;
		if (way == 2) {
			if (timeid == 1) {
				code = 12;
			} else if (timeid == 2) {
				code = 18;
			} else {
				code = 21;
			}

		} else {
			if (timeid == 1) {
				code = 13;
			} else if (timeid == 2) {
				code = 18;
			} else {
				code = 21;
			}
		}
		return code;
	}
	
}
