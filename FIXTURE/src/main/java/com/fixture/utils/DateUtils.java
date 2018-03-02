package com.fixture.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


/**
 * 日期处理
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2016年12月21日 下午12:53:33
 */
public class DateUtils {
	
	/** 日期格式 **/
    public interface DATE_PATTERN {
        String HHMMSS = "HHmmss";
        String HH_MM_SS = "HH:mm:ss";
        String YYYYMMDD = "yyyyMMdd";
        String YYYY_MM_DD = "yyyy-MM-dd";
        String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
        String YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";
        String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    }
	public static String format(Date date) {
        return format(date, DATE_PATTERN.YYYY_MM_DD);
    }

    public static String format(Date date, String pattern) {
        if(date != null){
            SimpleDateFormat df = new SimpleDateFormat(pattern);
            return df.format(date);
        }
        return null;
    }
    /**
	 * 获取日期
	 * 
	 * @return
	 */
	public static final String getDate() {
		return format(new Date());
	}

	/**
	 * 获取日期时间
	 * 
	 * @return
	 */
	public static final String getDateTime() {
		return format(new Date(), DATE_PATTERN.YYYY_MM_DD_HH_MM_SS);
	}

	/**
	 * 获取日期
	 * 
	 * @param pattern
	 * @return
	 */
	public static final String getDateTime(String pattern) {
		return format(new Date(), pattern);
	}

	/**
	 * 日期计算
	 * 
	 * @param date
	 * @param field
	 * @param amount
	 * @return
	 */
	public static final Date addDate(Date date, int field, int amount) {
		if (date == null) {
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(field, amount);
		return calendar.getTime();
	}

	/**
	 * 
	 * @Method: convertStringToDate
	 * @Description: 把字符串按照给定格式进行格式化
	 * @param dateStr
	 *            要格式化的日期字符串
	 * @param format
	 *            可以使用内置预定义的格式
	 * @return
	 * @throws ParseException
	 * @return Date 格式化的日期对象
	 * @throws
	 */
	public static Date stringToDate(String dateStr, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateStr);
	}
	/**
	 * 
	 * @Method: compareDay 
	 * @Description: 对比日期天数
	 * @param date1起
	 * @param date2至
	 * @return
	 * @throws
	 */
	public static int compareDay(Date d1, Date d2) {
		Calendar c = Calendar.getInstance();
		c.setTime(d1);
		long time1 = c.getTimeInMillis();
		c.setTime(d2);
		long time2 = c.getTimeInMillis();
		long between_days = (time2 - time1) / (1000 * 3600 * 24);
		return (int) between_days;
	}
	/**
	 * 字符串转换为日期:不支持yyM[M]d[d]格式
	 * 
	 * @param date
	 * @return
	 */
	public static final Date stringToDate(String date) {
		if (date == null) {
			return null;
		}
		String separator = String.valueOf(date.charAt(4));
		String pattern = "yyyyMMdd";
		if (!separator.matches("\\d*")) {
			pattern = "yyyy" + separator + "MM" + separator + "dd";
			if (date.length() < 10) {
				pattern = "yyyy" + separator + "M" + separator + "d";
			}
		} else if (date.length() < 8) {
			pattern = "yyyyMd";
		}
		pattern += " HH:mm:ss.SSS";
		pattern = pattern.substring(0, Math.min(pattern.length(), date.length()));
		try {
			return new SimpleDateFormat(pattern).parse(date);
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 间隔天数
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getDayBetween(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		start.set(Calendar.HOUR_OF_DAY, 0);
		start.set(Calendar.MINUTE, 0);
		start.set(Calendar.SECOND, 0);
		start.set(Calendar.MILLISECOND, 0);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		end.set(Calendar.HOUR_OF_DAY, 0);
		end.set(Calendar.MINUTE, 0);
		end.set(Calendar.SECOND, 0);
		end.set(Calendar.MILLISECOND, 0);

		long n = end.getTimeInMillis() - start.getTimeInMillis();
		return (int) (n / (60 * 60 * 24 * 1000l));
	}

	/**
	 * 间隔月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetween(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		return n;
	}

	/**
	 * 间隔月，多一天就多算一个月
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static final Integer getMonthBetweenWithDay(Date startDate, Date endDate) {
		if (startDate == null || endDate == null || !startDate.before(endDate)) {
			return null;
		}
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		int year1 = start.get(Calendar.YEAR);
		int year2 = end.get(Calendar.YEAR);
		int month1 = start.get(Calendar.MONTH);
		int month2 = end.get(Calendar.MONTH);
		int n = (year2 - year1) * 12;
		n = n + month2 - month1;
		int day1 = start.get(Calendar.DAY_OF_MONTH);
		int day2 = end.get(Calendar.DAY_OF_MONTH);
		if (day1 <= day2) {
			n++;
		}
		return n;
	}
	
	/**
	 * 
	 * Description: 计算两个时间的间隔，分钟
	 *
	 * @param date
	 * @return 
	 * @see
	 */
	public static Map<String,String> getTimeBetween(String date)
	{
	    SimpleDateFormat formt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    Map<String,String> th = new HashMap<String,String>();
        try
        {
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            Date da = formt.parse(date);
            Date nowDate=new Date();
            long nowTime=nowDate.getTime(); 
            long lastTime=da.getTime();
            long time=nowTime-lastTime;
            long day=time/nd;
            long hour=time%nd/nh;
            long min=time % nd % nh / nm;
            if(day>=1)
            {
                th.put("spaceTime", String.valueOf(day)+"."+hour);
                th.put("spaceUnit", "天");
            }
           
            else if(hour>0 && hour<=24)
            {
                th.put("spaceTime", String.valueOf(hour)+"."+min);
                th.put("spaceUnit", "小时");
            }
           
            else if(min<60)
            {
                th.put("spaceTime", String.valueOf(min));
                th.put("spaceUnit", "分钟");
            }
            else
            {
                th.put("spaceTime", "0");
                th.put("spaceUnit", "分钟");
            }
        }
        catch (ParseException e)
        {
        }
        
        return th;
	}
	
	/**
	 * 格式化时间差
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static String cgformat(Date date) throws ParseException {
		Date now = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		String res = "";
		// 相差天数
		int day = DateUtils.plusDay(now, calendar.getTime());
		switch (day) {
		case 0:
			int minute = plus(now, date);
			if (minute > 180) {
				res = convertDateToString(date, "HH:mm");
			} else if (minute > 120) {
				res = "2小时前";
			} else if (minute > 90) {
				res = "1.5小时前";
			} else if (minute > 60) {
				res = "1小时前";
			} else if (minute > 30) {
				res = "半小时前";
			} else if (minute > 25) {
				res = "25分钟前";
			} else if (minute > 15) {
				res = "15分钟前";
			} else if (minute > 5) {
				res = "5分钟前";
			} else if (minute > 3) {
				res = "3分钟前";
			} else {
				res = "刚刚";
			}
			break;
		case 1:
			res = convertDateToString(date, "HH:mm");
			break;
		case 2:
			res = convertDateToString(date, "HH:mm");
			break;
		default:
			res = convertDateToString(date, "MM/dd HH:mm");
			break;
		}
		return res;
		//
		// int date = calendar.get(Calendar.DATE);
		// if(calendar.get(Calendar.DATE))
		// Date nowD = convertStringToDate(convertDateToString("yyyy-MM-dd"),
		// "yyyy-MM-dd");
		// long cha = date.getTime() - nowD.getTime();
		// if (cha > 0) {
		// return "今天" + convertDateToString(date, "H") + "点";
		// } else {
		// if (Math.abs(cha) < 24L * ONE_HOUR) {
		// return "昨天" + convertDateToString(date, "H") + "点";
		// } else {
		// return convertDateToString(date, "yyyy-MM-dd HH:mm:ss");
		// }
		// }
	}
	
	/**
	 * 
	 * @Method: plus
	 * @Description: 求两个时间类型的分钟差
	 * @return Integer
	 * @throws
	 */
	public static int plus(Date date1, Date date2) {
		Long diff = date1.getTime() - date2.getTime();
		Long days = diff / (1000 * 60);
		return days.intValue();
	}

	public static int plusDay(Date date1, Date date2) throws ParseException {
		Long diff = date1.getTime() - date2.getTime();
		Long days = diff / (1000 * 3600 * 24);
		return days.intValue();
	}
	
	/**
	 * 
	 * @Method: convertDateToString
	 * @Description: 获取特定格式的日期字符串
	 * @param date
	 *            日期
	 * @param format
	 *            格式
	 * @return
	 * @throws
	 */
	public static String convertDateToString(Date date, String format) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		String dateAsString = dateFormat.format(date);
		return dateAsString;
	}
	
	 /**
     * 时间比大小
     * @param t1
     * @param t2
     * @return 0:t1=t2; <0:t1<t2; >0:t1>t2;
     */
    public static int timeCompare(String t1,String t2){
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        Calendar c1=Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        try {
            c1.setTime(formatter.parse(t1));
            c2.setTime(formatter.parse(t2));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /*eg.
         * i=r1.compareTo(r);//比较两个时间的先后r1是当前时间 r是你指定的时间
        if(i==0)System.out.println("ri=r");
        if(i<0)System.out.println("ri<r");
        if(i>0)System.out.println("ri>r");*/

        int result=c1.compareTo(c2);
        return result;
    }
    
    /**
     * 
    * @Title: getDays 
    * @Description: 求两个时间   yyyy-mm-dd hh:mm 格式的时间 差的 天数  x.x天
    * @param t1
    * @param t2
    * @return    设定文件 
    * @return String    返回类型 
    * @throws 
    * @author wyh
     */
    public static String getDays(String startDate,String endDate){
    	int m = plus(DateUtils.stringToDate(endDate), DateUtils.stringToDate(startDate));
    	double s = m/(60*24.0);
    	BigDecimal b = new BigDecimal(String.valueOf(s));
		b = b.divide(BigDecimal.ONE,1,BigDecimal.ROUND_CEILING);
        return String.valueOf(b);
    }
    
    /**
   	 * 方法名 ：modify<BR>
   	 * 方法说明 ：对日期对象进行修改<BR>
   	 * 备注 ：<BR>
   	 * 1、date为空（null）抛出异常 <BR>
   	 * 2、year,month,day,hour,minute,second都可以使用正负数来增加或者减少对应单位<BR>
   	 * 
   	 * @param year
   	 *            要增加或者减少的年(零表示不修改,负数表示减少)<BR>
   	 * @param month
   	 *            要增加或者减少的月(零表示不修改,负数表示减少)<BR>
   	 * @param day
   	 *            要增加或者减少的日(零表示不修改,负数表示减少)<BR>
   	 * @param hour
   	 *            要增加或者减少的时(零表示不修改,负数表示减少)<BR>
   	 * @param minute
   	 *            要增加或者减少的分(零表示不修改,负数表示减少)<BR>
   	 * @param second
   	 *            要增加或者减少的秒(零表示不修改,负数表示减少)<BR>
   	 * @return 修改好的日期对象<BR>
     * @author wyh
     */
	public static Date modify(Date date, int year, int month, int day, int hour, int minute, int second) {

		// 校验参数
		if (date == null) {
			throw new IllegalArgumentException("Args can not be null!");
		}

		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.YEAR, year);
		c.add(Calendar.MONTH, month);
		c.add(Calendar.DAY_OF_MONTH, day);
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.MINUTE, minute);
		c.add(Calendar.SECOND, second);

		return c.getTime();
	}
	/**
	 * 
	 * @Method: getFirstDateOfMonth 
	 * @Description: 日期的月份第一天
	 * @param dateStr 日期
	 * @param format 格式，输出同输入
	 * @throws ParseException
	 * @throws
	 */
	public static String getFirstDateOfMonth(String dateStr, String format) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		if (dateStr == null) {
			calendar.setTime(new Date());
		} else {
			calendar.setTime(convertStringToDate(dateStr, format));
		}
		calendar.set(Calendar.DATE, 1);
		return new SimpleDateFormat(format).format(calendar.getTime());
	}

	/**
	 * @throws ParseException
	 * 
	 * @Method: getLastDateOfMonth
	 * @Description: 获取本月最大日期
	 * @param dateStr 日期
	 * @param format 格式，输出同输入
	 * @return String
	 * @throws
	 */
	public static String getLastDateOfMonth(String dateStr, String format) throws ParseException {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(convertStringToDate(dateStr, format));
		calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
		return new SimpleDateFormat(format).format(calendar.getTime());
	}
	public static Date convertStringToDate(String dateStr, String format) throws ParseException {
		return new SimpleDateFormat(format).parse(dateStr);
	}
}
