package com.ruoyi.web.controller.tool;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class QuarterUtil {

    //获取每个季度天数
    public static int getQuarterDayNum(String quarter) {
        if (quarter == null || quarter.equals("")) {
            return 0;
        }
        String[] str = quarter.split("年");
        if (str[1].equals("Q1")) {
            return 90;
        } else if (str[1].equals("Q2")) {
            return 91;
        } else if (str[1].equals("Q3")) {
            return 92;
        } else if (str[1].equals("Q4")) {
            return 92;
        } else {
            return 0;
        }
    }

    //获取每个考核期间天数
    private static int getTermDayNum(String term) {
        if (term == null || term.equals("")) {
            return 0;
        }
        String[] terms = term.split("-");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");

        int result = 0;

        Calendar calst = Calendar.getInstance();
        Calendar caled = Calendar.getInstance();
        try {
            calst.setTime(sdf.parse(terms[0]));
            caled.setTime(sdf.parse(terms[1]));
        } catch (ParseException e) {
            return 0;
        }
        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
        //得到两个日期相差的天数
        int days = ((int) (caled.getTime().getTime() / 1000) - (int) (calst.getTime().getTime() / 1000)) / 3600 / 24;

        return days + 1;
    }

    //根据日期获取季度
    public static String getQuarterByDate(Date date) {
        String quarter = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int month = calendar.get(Calendar.MONTH) + 1;
        if (month >= 1 && month < 4) {
            quarter = "Q1";
        } else if (month >= 4 && month < 7) {
            quarter = "Q2";
        } else if (month >= 7 && month < 10) {
            quarter = "Q3";
        } else if (month >= 10 && month <= 12) {
            quarter = "Q4";
        } else {
            return null;
        }
        int year = calendar.get(Calendar.YEAR);
        quarter = String.valueOf(year).substring(2) + "年" + quarter;
        return quarter;
    }

    //根据时间期间，获取季度
    public static String getQuarterByTerm(String term) {
        String quarter = null;
        if (term == null || term.equals("")) {
            return null;
        }
        String[] terms = term.split("-");
        String[] dateStr = terms[0].split("\\.");
        if (dateStr[1].equals("1")) {
            //第一季度
            quarter = "Q1";
        } else if (dateStr[1].equals("4")) {
            //第二季度
            quarter = "Q2";
        } else if (dateStr[1].equals("7")) {
            //第三季度
            quarter = "Q3";
        } else if (dateStr[1].equals("10")) {
            //第四度
            quarter = "Q4";
        }
        quarter = dateStr[0].substring(2) + "年" + quarter;
        return quarter;
    }

}
