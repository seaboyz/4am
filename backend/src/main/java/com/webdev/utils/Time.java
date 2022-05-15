package com.webdev.utils;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Time {

    static  SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    static SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    static java.sql.Date parseDate(String date) {
        try {
            return new Date(DATE_FORMAT.parse(date).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

    static java.sql.Timestamp parseTimestamp(String timestamp) {
        try {
            return new Timestamp(DATE_TIME_FORMAT.parse(timestamp).getTime());
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
    }

}
