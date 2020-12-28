package com.sonnyburnettfun.getijden;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TidesList extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public TidesListAdapter mAdapter;
    public List<Waterstand> mTides;
    public TextView mTextViewPlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tides_activity);

        Log.e("msg", "De start van de TideList Activity");

        mTextViewPlace = findViewById(R.id.recycler_place);

        Intent intent = getIntent();
        List<Waterstand> mTides = new ArrayList<Waterstand>();
        String placeName = intent.getStringExtra(MainActivity.SELECTED_PLACE);
        mTextViewPlace.setText(placeName);
        String tideListString = intent.getStringExtra(MainActivity.SELECTED_PLACE);

        if (placeName.equals("Bergen")) {
            List<Waterstand> ijmuiden, denhelder;
            ijmuiden = JSONfile.getWaterstanden(getApplicationContext(), "IJmuiden");
            denhelder = JSONfile.getWaterstanden(getApplicationContext(), "Den Helder");
            mTides = Tides.estimateBergenTides(ijmuiden, denhelder);
        }
        else {
            mTides = JSONfile.getWaterstanden(getApplicationContext(), tideListString);
        }

        int firstToShow = 0;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            firstToShow = Tides.getIndexofFirstTide(mTides, DatumTijd.getDateString(LocalDate.now()));
        }


        mRecyclerView = findViewById(R.id.tides_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TidesListAdapter(this, mTides);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        layoutManager.scrollToPosition(firstToShow);



    }
}