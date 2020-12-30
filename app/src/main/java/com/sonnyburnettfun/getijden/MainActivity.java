package com.sonnyburnettfun.getijden;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public Button jaarbutton, dagbutton, maandbutton, uurbutton, minuutbutton, plaatsbutton;
    public TextView prevtidename, nowtidename, nexttidename;
    public TextView prevtidetime, nowtidetime, nexttidetime;
    public TextView prevtidehight, nowtidehight, nexttidehight;
    public LinearLayout totaal;
    public List<String> PLACES = Arrays.asList("IJmuiden", "Bergen", "Den Helder", "Texel");
    public int currentPlaceIndex = 0;
    public String currentPlace, currentFile;
    public List<Waterstand> waterstanden, bergen, texel, ijmuiden, denhelder;

    public static final String SELECTED_PLACE = "com.sonnyburnettfun.getijden.SELECTED_PLACE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        identifyMainActivityFields();

        if (savedInstanceState != null) {
            Log.e("msg", "HEEEEE Er is een davedInstanceState!");
            currentPlaceIndex = savedInstanceState.getInt("PlaceIndex");
        }

        loadTideDatainLists();
        setTideDatainFields();


        plaatsbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setTideDatainFields();            }
        });

        jaarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numberString = Integer.toString(currentPlaceIndex);
                String tidelistString = JSONfile.convertListOfObjectsToJSONstring(waterstanden);

                Intent intent = new Intent(MainActivity.this, TidesList.class);
                intent.putExtra(SELECTED_PLACE, currentPlace );
                startActivity(intent);


            }
        });

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putInt("PlaceIndex", currentPlaceIndex);
        savedInstanceState.putBoolean("MyBoolean", true);
        savedInstanceState.putDouble("myDouble", 1.9);
        savedInstanceState.putInt("MyInt", 1);
        savedInstanceState.putString("MyString", "Welcome back to Android");
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
        int myInt2 = savedInstanceState.getInt("PlaceIndex");
        currentPlaceIndex = myInt2;
        Log.e("msg", "we are in onRestoreInstanceState");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                int result = data.getIntExtra("result", 0);
            }
        }
    }

    public void setTideDatainFields() {
        if (currentPlaceIndex < PLACES.size()-1) {
            currentPlaceIndex++;
        }
        else {
            currentPlaceIndex = 0;
        }
        switch (currentPlaceIndex) {
            case(0) :
                currentPlace = "IJmuiden";
                waterstanden = ijmuiden;
                break;
            case (1) :
                currentPlace = "Bergen";
                waterstanden = bergen;
                break;
            case(2) :
                currentPlace = "Den Helder";
                waterstanden = denhelder;
                break;
            case (3) :
                currentPlace = "Texel";
                waterstanden = texel;
                break;
        }
        String jaar = DatumTijd.getTodayYear();
        String dagString = DatumTijd.getDateString(DatumTijd.getToday());
        String tijdString = DatumTijd.getTimeString(DatumTijd.getTodayTime());
        int previousTideIndex = Tides.getPreviousTide(jaar, dagString, tijdString, waterstanden);
        int nextTideIndex = previousTideIndex+1;
        setActivityFields(jaar, dagString, tijdString, previousTideIndex, nextTideIndex);
    }

    public void loadTideDatainLists() {
        ijmuiden = JSONfile.getWaterstanden(getApplicationContext(), "IJmuiden");
        denhelder = JSONfile.getWaterstanden(getApplicationContext(), "Den Helder");
        texel = JSONfile.getWaterstanden(getApplicationContext(), "Texel");
        bergen = Tides.estimateBergenTides(ijmuiden, denhelder);
    }


    public void identifyMainActivityFields() {
        totaal = findViewById(R.id.totaal);
        jaarbutton = findViewById(R.id.jaarbutton);
        dagbutton = findViewById(R.id.dagbutton);
        maandbutton = findViewById(R.id.maandbutton);
        /*uurbutton = findViewById(R.id.uurbutton);
        minuutbutton = findViewById(R.id.minuutbutton);*/
        plaatsbutton = findViewById(R.id.plaatsbutton);
        prevtidename = findViewById(R.id.tideprevname);
        nowtidename = findViewById(R.id.tidenowname);
        nexttidename = findViewById(R.id.tidenextname);
        prevtidetime = findViewById(R.id.tideprevtime);
        nowtidetime = findViewById(R.id.tidenowtime);
        nexttidetime = findViewById(R.id.tidenexttime);
        prevtidehight = findViewById(R.id.tideprevhight);
        nowtidehight = findViewById(R.id.tidenowhight);
        nexttidehight = findViewById(R.id.tidenexthight);
    }


    public void setActivityFields(String jaar, String dagString, String tijdString, int previousTideIndex, int nextTideIndex) {
        plaatsbutton.setText(currentPlace);
        jaarbutton.setText(jaar);
        String achtergrond = "#f1948a";
        switch (currentPlaceIndex) {
            case(0):
                achtergrond = "#ba4a00";
                break;
            case (1) :
                achtergrond = "#a93226";
                break;
            case(2):
                achtergrond = "#e74c3c";
                break;
            case (3) :
                achtergrond = "#641e16";
                break;
        }
        plaatsbutton.setBackgroundColor(Color.parseColor(achtergrond));
        jaarbutton.setBackgroundColor(Color.parseColor(achtergrond));

        totaal.setBackgroundColor(Color.parseColor("#839192"));

        dagbutton.setText(dagString.substring(2,4));
        maandbutton.setText(DatumTijd.getMonthName(dagString.substring(0,2)));
        /*uurbutton.setText(tijdString.substring(0,2));
        minuutbutton.setText(tijdString.substring(2,4));*/

        dagbutton.setBackgroundColor(Color.parseColor("#839192"));
        maandbutton.setBackgroundColor(Color.parseColor("#839192"));
        /*uurbutton.setBackgroundColor(Color.parseColor("#839192"));
        minuutbutton.setBackgroundColor(Color.parseColor("#839192"));*/

        if (waterstanden.get(previousTideIndex).tide.equals("HW")) {
            prevtidename.setBackgroundColor(Color.parseColor("#00c3ff"));
            prevtidetime.setBackgroundColor(Color.parseColor("#00c3ff"));
            prevtidehight.setBackgroundColor(Color.parseColor("#00c3ff"));
            nexttidename.setBackgroundColor(Color.parseColor("#e0af1f"));
            nexttidetime.setBackgroundColor(Color.parseColor("#e0af1f"));
            nexttidehight.setBackgroundColor(Color.parseColor("#e0af1f"));
            prevtidename.setText("VLOED");
            nexttidename.setText("EB");
        }
        else {
            nexttidename.setBackgroundColor(Color.parseColor("#00c3ff"));
            nexttidetime.setBackgroundColor(Color.parseColor("#00c3ff"));
            nexttidehight.setBackgroundColor(Color.parseColor("#00c3ff"));
            prevtidename.setBackgroundColor(Color.parseColor("#e0af1f"));
            prevtidetime.setBackgroundColor(Color.parseColor("#e0af1f"));
            prevtidehight.setBackgroundColor(Color.parseColor("#e0af1f"));
            prevtidename.setText("EB");
            nexttidename.setText("VLOED");
        }
        int verschilEbenVloed = DatumTijd.timeDiffinMinutes(waterstanden.get(previousTideIndex).time, waterstanden.get(nextTideIndex).time);
        Log.e("msg","eb en vloed verschil: " + verschilEbenVloed);
        int verschilNuenVorige = DatumTijd.timeDiffinMinutes(waterstanden.get(previousTideIndex).time, tijdString);
        Log.e("msg", "vorige tij en nu verschil: " + verschilNuenVorige);

        float percentageVerlopen = ((float) verschilNuenVorige / (float) verschilEbenVloed)*100;

        nowtidehight.setText(Math.round(percentageVerlopen) + " % ");
        nowtidename.setBackgroundColor(Color.parseColor("#c5cae9"));
        nowtidetime.setBackgroundColor(Color.parseColor("#c5cae9"));
        nowtidehight.setBackgroundColor(Color.parseColor("#c5cae9"));

        prevtidetime.setText(DatumTijd.makeNiceTime(waterstanden.get(previousTideIndex).time));
        nowtidetime.setText(DatumTijd.makeNiceTime(tijdString));
        nexttidetime.setText(DatumTijd.makeNiceTime(waterstanden.get(nextTideIndex).time));

        prevtidehight.setText(waterstanden.get(previousTideIndex).val);
        nowtidename.setText("NU");
        nexttidehight.setText(waterstanden.get(nextTideIndex).val);

    }


}