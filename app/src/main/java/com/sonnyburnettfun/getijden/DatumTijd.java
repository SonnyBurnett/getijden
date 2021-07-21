package com.sonnyburnettfun.getijden;

import android.os.Build;
import android.util.Log;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class DatumTijd {

    static LocalDate getToday() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDate nowDate = LocalDate.now();
            return nowDate;
        }
        return null;
    }

    static LocalDateTime getTodayFull() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime nowDate = LocalDateTime.now();
            return nowDate;
        }
        return null;
    }

    static String getTodayYear() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
             return Integer.toString(LocalDate.now().getYear());
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


    static String getMonth(String datum) {
        return datum.substring(0,2);
    }

    static String getDay(String datum) {
        return datum.substring(2,4);
    }

    static String getHours(String tijd) {
        return tijd.substring(0,2);
    }

    static String getMinutes(String tijd) {
        return tijd.substring(2,4);
    }

    static String getMonthName(String datum) {
        Integer monthNum = Integer.parseInt(getMonth(datum));
        switch (monthNum) {
            case (1): return "Januari";
            case (2): return "Februari";
            case (3): return "Maart";
            case (4): return "April";
            case (5): return "Mei";
            case (6): return "Juni";
            case (7): return "Juli";
            case (8): return "Augustus";
            case (9): return "September";
            case (10): return "Oktober";
            case (11): return "November";
            case (12): return "December";
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
        String nowMonth, nowDay = null;
        int tmpMonth, tmpDay = 0;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            tmpMonth = datum.getMonthValue();
            tmpDay = datum.getDayOfMonth();

            if (Integer.toString(tmpMonth).length() == 1) {
                nowMonth = "0"+Integer.toString(tmpMonth);
            }
            else {
                nowMonth = Integer.toString(tmpMonth);
            }

            if (Integer.toString(tmpDay).length() == 1) {
                nowDay = "0"+Integer.toString(tmpDay);
            }
            else {
                nowDay = Integer.toString(tmpDay);
            }

            return nowMonth+nowDay;
        }
        return null;
    }

    static LocalDateTime makeItLocalDateTime(String jaar, String datum, String tijd ) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            return LocalDateTime.parse(jaar+"-"+getMonth(datum)+"-"+getDay(datum)+"T"+getHours(tijd)+":"+getMinutes(tijd)+":00");
        }
        return null;
    }

    static boolean isTimeearlierThanTime(Waterstand w, LocalDateTime now) {

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime d1 = makeItLocalDateTime(w.year,w.date,w.time);
            if (d1.isAfter(now)) {
                return false;
            }
        }
        return true;
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
