package com.sonnyburnettfun.getijden;

import android.os.Build;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

public class DatumTijd {

    static LocalDate getToday() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate nowDate = LocalDate.now();
            return nowDate;
        }
        return null;
    }

    static LocalTime getTodayTime() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalTime nowTime = LocalTime.now();
            return nowTime;
        }
        return null;
    }

    static String getTimeString(LocalTime tijd) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            String nowTime = tijd.toString().substring(0,5);
            String nowTime2 = nowTime.substring(0,2)+nowTime.substring(3,5);
            return nowTime2;
        }
        return null;
    }


    static String getDateString(LocalDate datum) {
        String nowMonth = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            nowMonth = Integer.toString(datum.getMonthValue());
            String nowDay = Integer.toString(datum.getDayOfMonth());
            return nowMonth+nowDay;
        }
        return null;
    }

    static boolean isTimeLaterThanTime(String time1, String time2) {
        if (Integer.parseInt(time1)+10000 > Integer.parseInt(time2)+10000) {
            return true;
        }
        return false;
    }

    static int timeDiffinMinutes(String time1, String time2) {
        int verschil = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime t1 = LocalTime.parse(time1.substring(0,2)+":"+time1.substring(2,4)+":00");
            LocalTime t2 = LocalTime.parse(time2.substring(0,2)+":"+time2.substring(2,4)+":00");
            verschil = (int) t1.until(t2, ChronoUnit.MINUTES);
            if (verschil < -1000) {
                verschil = verschil + 1440;
            }
        }
        return verschil;
    }

    static String addMinutesToTime(String time1, int minutes) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime t1 = LocalTime.parse(time1.substring(0, 2) + ":" + time1.substring(2, 4) + ":00");
            return getTimeString(t1.plusMinutes(minutes));
        }
        return "0000";

    }

    static boolean isTimeNextDay(String time1, int minutes) {
        int verschil = -1;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalTime t1 = LocalTime.parse(time1.substring(0,2)+":"+time1.substring(2,4)+":00");
            LocalTime t2 = t1.plusMinutes(minutes);
            if (verschil < -1000) {
                return true;
            }
        }
        return false;
    }

    static String makeNiceTime(String time) {
        return time.substring(0,2)+":"+time.substring(2,4);
    }
}
