package com.sonnyburnettfun.getijden;

import android.util.Log;

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



    static int getPreviousTide(String nuDatum, String nuTijd, List<Waterstand> tides) {

        int firstIndexDate = Tides.getIndexofFirstTide(tides, nuDatum);

        if (DatumTijd.isTimeLaterThanTime(tides.get(firstIndexDate).time, nuTijd)) {
            Log.e("msg", "De vorige tide was gisteren");
            return firstIndexDate-1;
        }
        int i = firstIndexDate;

        while (tides.get(i).date.equals(nuDatum)) {
            if (DatumTijd.isTimeLaterThanTime(tides.get(i).time, nuTijd)) {
                return (i-1);
            }
            i++;
        }
        Log.e("msg", "De vorige tide is de laatste van deze dag");
        return i;
    }

    static void logPrintTide(Waterstand tide) {
        Log.e("data", tide.year+ " " + tide.date + " " + tide.time + " " + tide.tide + " " + tide.val);
    }

    static List<Waterstand> estimateBergenTides(List<Waterstand> w1, List<Waterstand> w2) {

        ArrayList<Waterstand> bergen = new ArrayList<>();

        Waterstand t1, t2, t3;
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
