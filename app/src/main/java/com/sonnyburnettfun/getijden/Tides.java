package com.sonnyburnettfun.getijden;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
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

        String jaar,datum,tijd,tide,val;
        int diff;
        for (int i = 0; i<w1.size(); i++) {
            t1 = w1.get(i);
            t2 = w2.get(i);
            jaar = t1.year;
            diff = (int) ((DatumTijd.timeDiffinMinutes(t1.time, t2.time))/100)*40;

            if (t1.date.equals(t2.date)) {
                datum = t1.date;
            }
            else {
                if (DatumTijd.isTimeNextDay(t1.time, diff))
                    datum = t2.date;
                else
                    datum = t1.date;
            }

            tijd = DatumTijd.addMinutesToTime(t1.time, diff);
            tide = t1.tide;
            val = Integer.toString(Integer.parseInt(t1.val)-11);

            t3 = new Waterstand(jaar, datum, tijd, tide, val);

            bergen.add(t3);

            //Log.e("msg", "w1 " + t1.date + " " + t1.time + " " + t1.tide + " " + t1.val + "w2 " + t2.date + " " + t2.time + " " + t2.tide + " " + t2.val);
            //Log.e("msg", t1.time + "," + t2.time + "," + t3.time + "," + DatumTijd.timeDiffinMinutes(t1.time, t2.time) + "," + DatumTijd.timeDiffinMinutes(t2.time, t3.time));
            //Log.e("msg", t1.date + "," + t1.time + "," + t2.time + "," + (Integer.parseInt(t1.val) - Integer.parseInt(t2.val)) );

        }
        return bergen;
    }



}
