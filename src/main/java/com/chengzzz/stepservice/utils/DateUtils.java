package com.chengzzz.stepservice.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: Chenkf
 * @create: 2020/09/02
 **/
public class DateUtils {

    public final static String shortFormat          = "yyyyMMdd";

    public final static String newFormat            = "yyyy-MM-dd HH:mm:ss";



    public static Date parseDateNoTime(String sDate) throws ParseException {
        DateFormat dateFormat = new SimpleDateFormat(shortFormat);

        if ((sDate == null) || (sDate.length() < shortFormat.length())) {
            throw new ParseException("length too little", 0);
        }

        int sz = sDate.length();
        for (int i = 0; i < sz; i++) {
            if (Character.isDigit(sDate.charAt(i)) == false) {
                throw new ParseException("not all digit", 0);
            }
        }

        return dateFormat.parse(sDate);
    }


    public static String getNewFormatDateString(Date date) {
        DateFormat dateFormat = new SimpleDateFormat(newFormat);
        return getDateString(date, dateFormat);
    }

    public static String getDateString(Date date, DateFormat dateFormat) {
        if (date == null || dateFormat == null) {
            return null;
        }

        return dateFormat.format(date);
    }

}
