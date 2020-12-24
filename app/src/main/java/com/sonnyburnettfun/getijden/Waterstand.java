package com.sonnyburnettfun.getijden;

import java.util.ArrayList;

public class Waterstand {
    String year;
    String date;
    String time;
    String tide;  // HW=vloed of LW=eb
    String val;   // cm tov NAP


    public Waterstand(String year, String date, String time, String tide, String val) {
        this.year = year;
        this.date = date;
        this.time = time;
        this.tide = tide;
        this.val = val;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTide() {
        return tide;
    }

    public void setTide(String tide) {
        this.tide = tide;
    }

    public String getVal() {
        return val;
    }

    public void setVal(String val) {
        this.val = val;
    }
}
