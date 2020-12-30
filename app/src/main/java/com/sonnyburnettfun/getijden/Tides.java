package com.sonnyburnettfun.getijden;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Tides {


    static int getIndexofFirstTide(List<Waterstand> tideList, String datum) {
        Iterator<Waterstand> iterator = tideList.iterator();
        int i = 0;
        while (iterator.hasNext()) {
            Waterstand tide = iterator.next();
            if (tide.getDate().equals(datum)) {
                return i;
            }
            i++;
        }
        return -1;    }



    static int getPreviousTide(String jaar, String nuDatum, String nuTijd, List<Waterstand> tides) {

        int firstIndexDate = 0;

        while (!tides.get(firstIndexDate).year.equals(jaar) && firstIndexDate < tides.size()) {
            firstIndexDate++;
        }

        while (firstIndexDate < tides.size() && !tides.get(firstIndexDate).date.equals((nuDatum))) {
            firstIndexDate++;
        }

        while (DatumTijd.isTimeearlierThanTime(tides.get(firstIndexDate), DatumTijd.getTodayFull())) {
            Log.e("msg", tides.get(firstIndexDate).time + " is eerder dan " + nuTijd + " doorzoeken");
            firstIndexDate++;
        }
        Log.e("msg", tides.get(firstIndexDate).time + " is later dan " + nuTijd + " dit is de volgende");
        Log.e("msg", tides.get(firstIndexDate-1).time + " is eerder dan " + nuTijd + " dit is de vorige");


        return (firstIndexDate-1);

    }


    static void logPrintTide(Waterstand tide) {
        Log.e("data", tide.year+ " " + tide.date + " " + tide.time + " " + tide.tide + " " + tide.val);
    }


    static List<Waterstand> estimateBergenTides(List<Waterstand> w1, List<Waterstand> w2) {

        ArrayList<Waterstand> bergen = new ArrayList<>();

        Waterstand t1, t2, t3;

        Log.e("msg", "w1 ijmuiden is groot: " + w1.size() + " w2 is den helder is groot: " + w2.size());

        String jaar = "";
        String datum = "";
        String tijd = "";
        String tide = "";
        String val = "";
        LocalDateTime ldt1 = null;
        LocalDateTime ldt2 = null;
        LocalDateTime newDateTime = null;
        int diff = 0;
        int verschil = 0;
        int tmpMonth, tmpDay, tmpHour, tmpMinutes;
        String monthString, dayString, hourString, minuteString;
        int calculatedVerschil = 0;
        for (int i = 0; i<w1.size(); i++) {
            t1 = w1.get(i);
            t2 = w2.get(i);
            ldt1 = DatumTijd.makeItLocalDateTime(t1.year,t1.date, t1.time);
            ldt2 = DatumTijd.makeItLocalDateTime(t2.year,t2.date,t2.time);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                verschil = (int) ldt1.until(ldt2, ChronoUnit.MINUTES);
            }

            calculatedVerschil = (int) (verschil/100)*40;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                newDateTime = ldt1.plusMinutes(calculatedVerschil);
                jaar = Integer.toString(newDateTime.getYear());
                tmpMonth = newDateTime.getMonthValue();
                tmpDay = newDateTime.getDayOfMonth();
                if (Integer.toString(tmpMonth).length() == 1) {
                    monthString = "0"+Integer.toString(tmpMonth);
                }
                else {
                    monthString = Integer.toString(tmpMonth);
                }

                if (Integer.toString(tmpDay).length() == 1) {
                    dayString = "0"+Integer.toString(tmpDay);
                }
                else {
                    dayString = Integer.toString(tmpDay);
                }
                datum = monthString+dayString;

                tmpHour = newDateTime.getHour();
                tmpMinutes = newDateTime.getMinute();
                if (Integer.toString(tmpHour).length() == 1) {
                    hourString = "0"+Integer.toString(tmpHour);
                }
                else {
                    hourString = Integer.toString(tmpHour);
                }

                if (Integer.toString(tmpMinutes).length() == 1) {
                    minuteString = "0"+Integer.toString(tmpMinutes);
                }
                else {
                    minuteString = Integer.toString(tmpMinutes);
                }
                tijd = hourString+minuteString;

            }


            tide = t1.tide;
            val = Integer.toString(Integer.parseInt(t1.val)-11);

            //Log.e("data", jaar + " " + datum + " " + tijd + " " + tide + " " + val);

            t3 = new Waterstand(jaar, datum, tijd, tide, val);

            bergen.add(t3);

            //Log.e("msg", "w1 " + t1.date + " " + t1.time + " " + t1.tide + " " + t1.val + "w2 " + t2.date + " " + t2.time + " " + t2.tide + " " + t2.val);
            //Log.e("msg", t1.time + "," + t2.time + "," + t3.time + "," + DatumTijd.timeDiffinMinutes(t1.time, t2.time) + "," + DatumTijd.timeDiffinMinutes(t2.time, t3.time));
            //Log.e("msg", t1.date + "," + t1.time + "," + t2.time + "," + (Integer.parseInt(t1.val) - Integer.parseInt(t2.val)) );

        }
        return bergen;
    }



}
