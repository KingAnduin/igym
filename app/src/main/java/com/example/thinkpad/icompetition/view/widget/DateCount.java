package com.example.thinkpad.icompetition.view.widget;

import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * Created By hjg on 2018/12/10
 * 计算两日期之间的间隔
 */
public class DateCount {

    public static long count(String date1, String date2, String format){
        SimpleDateFormat formater = new SimpleDateFormat(format);
        long counts = 0l;
        try {
            long d1 = formater.parse(date1).getTime();
            long d2 = formater.parse(date2).getTime();
            counts = (d2-d1)/(1000 * 60 * 60 * 24);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return counts;
    }
}
