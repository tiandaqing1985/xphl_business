package com.ruoyi.system.task;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Test {

	public static void main(String[] args) throws Exception {
		System.out.println("当前月份："+Calendar.MONTH);
/*//		参数：
		String dateStr = "2016-05-18";

//		1、获取string对应date日期：

		Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dateStr);

//		2、获取date对应的Calendar对象

		Calendar ca = Calendar.getInstance();

		ca.setTime(date);

//		3、可以从ca中获取各种该日期的属性值：

		int day = ca.get(Calendar.DATE);//

		int week = ca.get(Calendar.WEEK_OF_YEAR);//一年中的第几周

		int month = ca.get(Calendar.MONTH);//第几个月

		int year = ca.get(Calendar.YEAR);//年份数值
		
		System.out.println("年："+year+" 月："+(month+1)+" 日："+day+" 周"+week);*/
		
	}

}
