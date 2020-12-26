package com.sonnyburnettfun.getijden;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TidesList extends AppCompatActivity {
    public RecyclerView mRecyclerView;
    public TidesListAdapter mAdapter;
    final List<Waterstand> mTides = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_tides_activity);

        mRecyclerView = findViewById(R.id.tides_recyclerview);
        mRecyclerView.setHasFixedSize(true);
        mAdapter = new TidesListAdapter(this, mTides);
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
    }
}