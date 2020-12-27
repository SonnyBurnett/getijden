package com.sonnyburnettfun.getijden;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

        mTextViewPlace = findViewById(R.id.recycler_place);

        Intent intent = getIntent();
        List<Waterstand> mTides = new ArrayList<Waterstand>();
        String indexText = intent.getStringExtra(MainActivity.EXTRA_NUMBER);

        switch (indexText) {
            case("0") :
                Log.e("msg", "0 index is: " + indexText);
                mTides = JSONfile.getWaterstanden(getApplicationContext(), "ijmuiden");
                mTextViewPlace.setText("IJmuiden");
                break;
            case("1") :
                Log.e("msg", "1 index is: " + indexText);
                mTides = JSONfile.getWaterstanden(getApplicationContext(), "texel");
                mTextViewPlace.setText("Bergen");
                break;
            case("2") :
                Log.e("msg", "2 index is: " + indexText);
                mTides = JSONfile.getWaterstanden(getApplicationContext(), "denhelder");
                mTextViewPlace.setText("Den Helder");
                break;
            case("3") :
                Log.e("msg", "3 index is: " + indexText);
                mTides = JSONfile.getWaterstanden(getApplicationContext(), "texel");
                mTextViewPlace.setText("Texel");
                break;
            default :
                Log.e("msg", "default index is: " + indexText);
                mTides = JSONfile.getWaterstanden(getApplicationContext(), "texel");
                mTextViewPlace.setText("Texel");
                break;
        }

        mTides = JSONfile.getWaterstanden(getApplicationContext(), "texel");
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