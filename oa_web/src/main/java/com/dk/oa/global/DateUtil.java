package com.dk.oa.global;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/*
日期转换工具类
* */
public class DateUtil {
    public static String getCurrentDateStr(){
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");
        return sdf.format(date);
    }
    //date转化成string
    public static String formatDate(Date date,String format){
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:SS");
        if (date!=null){
            result = sdf.format(date);
        }
        return result;
    }
    //string转化成date类型
    public static Date getDate(String date){
        Date newdate = new Date(date);
        return newdate;
    }
}
