package com.gh_hitech.devicecontroller.utils;

import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

/**
 * @author yijigu
 */
public class DateUtil {
	
	/**
	 * 获取当前时间
	 * @return String: 时间  格式  yyyy-MM-dd HH:mm:ss
	 */
	public static String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH) + 1);
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH));
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		String second = String.valueOf(c.get(Calendar.SECOND));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":"
				+ mins + ":" + second);
		return sbBuffer.toString();
	}

	/**
	 * 获取当前时间
	 * @param format 时间格式
	 * @return
	 */
	public static String getCurrentDate(String format) {
		//获取当前时间
		Date curDate = new Date(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String time = formatter.format(curDate);
		return time;
	}

	public static String getCurrentDate(String date, String format) {
		//获取当前时间
		Date curDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		String time = formatter.format(curDate);
		return time;
	}

	/**
	 * 获取当前时间
	 * @return  long类型时间
	 */
	public static long getCurrentDate(){
		long time= System.currentTimeMillis();
		return time;
	}

	/**
	 * 根据time获取指定格式的时间
	 * @param time  要转换的long类型时间
	 * @param format  转换后的时间格式
	 * @return
	 */
	public static String getDate4Long(long time, String format){
		try {
			Date date = new Date(time);
			SimpleDateFormat formatter = null;
			if(TextUtils.isEmpty(format))
				formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			else
				formatter = new SimpleDateFormat(format);
				String formatTime = formatter.format(date);
			return formatTime;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}


	/**
	 * 把String类型的时间转换为long类型的时间
	 * @param time  时间
	 * @param format  时间格式
	 * @return
	 */
	public static long getLongTime4String(String time , String format){
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		Date currentTime;
		long timeLong=0;
		try {
			currentTime = formatter.parse(time);
			timeLong=currentTime.getTime();
			return timeLong;
		} catch (ParseException e) {
			return timeLong;
		}
	}


	/**
	 * 根据格式返回当前时间 - 偏移天数
	 * @param format  时间格式  如: yyyy-MM-dd HH:mm
	 * @return
	 */
	public static String getOffsetDayForString(String format, int offsetDay){
		String time = null;
		try {
			//获取当前时间
			Date curDate   =   new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24 * offsetDay));
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			time = formatter.format(curDate);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return time;
	}


	/**
	 * 获取某个月份的天数 
	 * @param year  年
	 * @param month  月
	 * @return
	 */
	public static int getDayOfMonth(int year,int month){
		String strDate = year + "-" + month;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Calendar calendar = new GregorianCalendar();
		try {
			Date date1 = sdf.parse(strDate);
			//放入你的日期
			calendar.setTime(date1);
			return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		} catch (ParseException e) {
			e.printStackTrace();
			return  31 ;
		} 
		
	}
	
	 /**
	  *method 将字符串类型的日期转换为一个timestamp（时间戳记java.sql.Timestamp）
	   dateString 需要转换为timestamp的字符串
	   dataTime timestamp
	  */
	 public static java.sql.Timestamp string2Time(String dateString) throws ParseException {
		   DateFormat dateFormat;
		 //设定格式
		 dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
		  //dateFormat = new SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.ENGLISH);
		  dateFormat.setLenient(false);
		 //util类型
		 Date timeDate = dateFormat.parse(dateString);
		 //Timestamp类型,timeDate.getTime()返回一个long型
		 java.sql.Timestamp dateTime = new java.sql.Timestamp(timeDate.getTime());
		  return dateTime;
	 }
	 
	 
	 /**
	  * @category 转换日期格式
	  * @param beforeFormat  转换前日期格式
	  * @param befroeTime 	 转换前的日期
	  * @param afterFormat   转换后的日期格式
	  * @return
	  */
	 public static String changeTimeFormat(String beforeFormat, String befroeTime, String afterFormat){
		 try {
			 String afterTime = null;
			 SimpleDateFormat formatter = new SimpleDateFormat(beforeFormat, Locale.ENGLISH);
			 Date beforeTime_data = formatter.parse(befroeTime);
			 formatter = new SimpleDateFormat(afterFormat, Locale.ENGLISH);
			 afterTime = formatter.format(beforeTime_data);
			 return afterTime;
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	 }
	 
	 
	 
	 /**
	  * 计算剩余天数
	  * @return  剩余的天数
	  */
	 public static int calculateResidueDay(String datFormat, String date){
		 if(date != null && datFormat != null){
			SimpleDateFormat formatter = new SimpleDateFormat(datFormat, Locale.ENGLISH);
			formatter.setLenient(false);
			try {
				Date mDate = formatter.parse(date);
				long time = mDate.getTime();
				long currentDate =  getCurrentDate();
				//到期时间大于当前时间  则状态正常
				if(time > currentDate){
					long residueTime = time - currentDate;
					int day = getLong2Day(residueTime);
					return day;
				}else{
					return -1;
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		return 0; 
	 }
	 
	 /**
	  * 把Long 类型转换为天
	  * @param date
	  * @return
	  */
	 public static int getLong2Day(long date){
		 long oneDay = 60 * 60 * 24 * 1000; 
		 if(date > oneDay){
			int day =  (int) (date / oneDay);
			return day;
		 }
		 return 0;
	 }

	/**
	 * 获取年龄
	 * @param birthday
	 * @param datFormat
	 * @return
	 */
	public static int getAge(String birthday, String datFormat){
		try {
			if(TextUtils.isEmpty(birthday) || TextUtils.isEmpty(datFormat))
				return 0;
			Calendar current = Calendar.getInstance();
			int currentYear = current.get(Calendar.YEAR);
			SimpleDateFormat formatter = new SimpleDateFormat(datFormat, Locale.ENGLISH);
			Date date = formatter.parse(birthday);
			Calendar old = Calendar.getInstance();
			old.setTime(date);
			int oldYear = old.get(Calendar.YEAR);
			return  currentYear - oldYear;
		} catch (ParseException e) {
			e.printStackTrace();
			return 0;
		}

	}
	/**
	 * 时间戳转日期格式
	 * @param timestamp 时间戳
	 * @return yyyy-MM-dd
	 */
	public static String getTimestampForData(String timestamp) {
		  
		SimpleDateFormat sdf=new SimpleDateFormat("MM-dd kk:mm");
		String sd = sdf.format(new Date(Long.parseLong(timestamp)));
		return sd;
		
	}

    /**
     * 友好显示（今天，明天，后天）
     * @param date
     * @return
     */
    public static String getFriendTime(String date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String d=getCurrentDate("yyyy-MM-dd");
        try {
            Date date1 = sdf.parse(d);
            Date date2 = sdf.parse(date);
            long l=date2.getTime()-date1.getTime();
            if (l==1000*24*60*60){
                return  "明天";
            }else if (l==1000*24*60*60*2){
                return  "后天";
            }else if (l==0){
                return "今天";
            }else {
                return date;
            }
        } catch (ParseException e) {
            return "";
        }
    }

	/**
	 * 获取经过实践
	 * @param time
	 * @return
	 */
	public static String getPassTime(long time){
		long nowTime = getCurrentDate();
		if (nowTime > time){
			long passTime = nowTime - time;
			float minute = passTime / 1000 / 60;
			float hour = passTime / 1000 / 60 / 60;
			float day = passTime / 1000 / 60 / 60 / 24;
			if (minute  < 1 && minute > 0) {
				return "刚刚";
			}
			else if(minute > 1 && minute < 60) {
				return String.format("%d分钟前",(int)minute);
			}
			else if(minute > 60 && hour < 24) {
				return String.format("%d小时前",(int)hour);
			}
			else if(hour > 24 && day > 0) {
				return String.format("%d天前", (int) day);
			}
		}
		return "未知";
	}


	/**
	 * 获得一个月 有几个自然周（周一到周日 七天 为一个自然周）
	 * 本月第一个周一 是第一周的开始
	 * 结尾不足七天 为最后一周
	 * @param date
	 * @return
     */
	public static  int  getMonthOfWeeks(String date) {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date1 = null;
		try {
		date1 = dateFormat.parse(date);
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(date1);
		int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
		int  firstM=0;
		for (int i = 1; i <= days; i++) {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
			Date date2 = dateFormat1.parse(date + "-" + i);
			calendar.clear();
			calendar.setTime(date2);
			int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
			// 如果是周一
			if (k == 2) {
				firstM=i;
				break;
			}
		}
			int m=(days-firstM+1)/7;
			int end=(days-firstM+1)%7==0?0:1;
			return m+end;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  -1;
	}

	/**
	 * 日期格式转换  2016-10-10 11:11:00 转为 2016-10-10
	 * @oldFormat 旧的日期格式
	 * @newFormat  新的日期格式
	 * @dateStr dateStr
	 * 如 2016-10-10 11:11:00 转为 2016-10-10
	 */
	public static String formatToString(String dateStr, String oldFormat, String newFormat) {
		SimpleDateFormat OldDateFormat = new SimpleDateFormat(oldFormat);
		SimpleDateFormat NewFormat = new SimpleDateFormat(newFormat);
		String date=null;
		try {
			date=NewFormat.format(OldDateFormat.parse(dateStr));
		} catch (ParseException e) {
			e.printStackTrace();
			return "";
		}
		return date.toString();
	}


	/**
	 * 当前日期 是第几周 的描述
	 * @ date 当前 2016-10 yyyy-MM
	 * @ mDay 当前天
	 * @return  年 月 周
	 */
	public static  int[]  getMonthOfWeek(String date, int mDay) {
		int arrays[]=new int[3];
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
		Date date1 = null;
		try {
			date1 = dateFormat.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date1);
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH) + 1;
			int lastMonth=calendar.get(Calendar.MONTH);
			arrays[0]=year;
			int days = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
			int  firstM=0;
			for (int i = 1; i <= days; i++) {
				DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
				Date date2 = dateFormat1.parse(date + "-" + i);
				calendar.clear();
				calendar.setTime(date2);
				int k = new Integer(calendar.get(Calendar.DAY_OF_WEEK));
				// 如果是周一
				if (k == 2) {
					firstM=i;
					break;
				}
			}
			// 上个月的最后一周
			if( firstM >mDay){
				arrays[1]=lastMonth;
				arrays[2]=getMonthOfWeeks(year+"-"+lastMonth);
				return  arrays;
			}else{// 这个月的
				int m=(mDay-firstM+1)/7;
				int end=(mDay-firstM+1)%7==0?0:1;
				arrays[1]=month;
				arrays[2]=m+end;
				return  arrays;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  null;
	}


	/**
	 * 根据 日期 格式 获取 日期的 年 和 月
	 * 2016-10-01
	 * @param date
	 * @param format
     * @return
     */
	public static  int[] getYearAndMonth(String date, String format) {
		int arrays[]=new int[2];
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date1 = null;
		try {
			date1 = dateFormat.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date1);
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH) + 1;
			arrays[0]=year;
			arrays[1]=month;
			return  arrays;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  null;
	}

	/**
	 * 与当期时间进行比较
	 * 2016-10-01
	 * @return
	 */
	public static int compareToCurrentTime(String compareTime) {

		try{

			SimpleDateFormat df = new   SimpleDateFormat("yyyy-MM-dd   HH:mm:ss");
			Date compare = df.parse(compareTime);
			Date current  = new Date();
			// 除以1000是为了转换成秒
			Long between = (current.getTime() - compare.getTime())/1000;

			if(between >= 0){

				return 1;
			}else{

				return 0;
			}
		}catch (Exception e){

		}
		return 1;

	}

	public static  int[] getYearAndMonthAndDay(String date, String format) {
		int[] arrays=new int[3];
		DateFormat dateFormat = new SimpleDateFormat(format);
		Date date1 = null;
		try {
			date1 = dateFormat.parse(date);
			Calendar calendar = new GregorianCalendar();
			calendar.setTime(date1);
			int year=calendar.get(Calendar.YEAR);
			int month=calendar.get(Calendar.MONTH) + 1;
			int day=calendar.get(Calendar.DAY_OF_MONTH);
			arrays[0]=year;
			arrays[1]=month;
			arrays[2]=day;
			return  arrays;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return  null;
	}

	/**
	 * 本月第一天
	 * @param format 格式
	 * @return
     */
	public static  String getMonthFirstDay(String format) {
		Calendar calendar  =   new  GregorianCalendar();
		calendar.set( Calendar.DATE,  1 );
		SimpleDateFormat simpleFormate =  new  SimpleDateFormat( format);
		return simpleFormate.format(calendar.getTime());
	}

	/**
	 * 本月最后一天
	 * @param format 格式
	 * @return
	 */
	public static  String getMonthLastDay(String format) {
		Calendar calendar  =   new  GregorianCalendar();
		int maxDay = calendar.getActualMaximum(Calendar.DATE);
		calendar.set( Calendar.DATE, maxDay);
		SimpleDateFormat simpleFormate =  new  SimpleDateFormat(format);
		return simpleFormate.format(calendar.getTime());
	}


	/**
	 * 日期比较大小
	 * @param startTime
	 * @param endTime
	 * @param format
	 * @return
	 */
	public static long  dateDiff(String startTime, String endTime, String format) {
		// 按照传入的格式生成一个simpledateformate对象
		SimpleDateFormat sd = new SimpleDateFormat(format);
		// 一天的毫秒数
		long nd = 1000 * 24 * 60 * 60;
		// 一小时的毫秒数
		long nh = 1000 * 60 * 60;
		// 一分钟的毫秒数
		long nm = 1000 * 60;
		// 一秒钟的毫秒数
		long ns = 1000;
		long diff;
		long day = 0;
		try {
			// 获得两个时间的毫秒时间差异
			diff = sd.parse(endTime).getTime()
					- sd.parse(startTime).getTime();
			// 计算差多少天
			day = diff / nd;
			// 计算差多少小时
			long hour = diff % nd / nh;
			// 计算差多少分钟
			long min = diff % nd % nh / nm;
			// 计算差多少秒
			long sec = diff % nd % nh % nm / ns;
			// 输出结果
			System.out.println("时间相差：" + day + "天" + hour + "小时" + min
					+ "分钟" + sec + "秒。");
			if (day>=1) {
				return 1;
			}else {
				if (day==0) {
					return 0;
				}else {
					return -1;
				}

			}

		} catch (ParseException e) {
			e.printStackTrace();
		}
		return 0;

	}


}
