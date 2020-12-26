package com.sonnyburnettfun.getijden;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class TidesListViewHolder extends RecyclerView.ViewHolder {


    public TextView tideJaar;
    public TextView tideDatum;
    public TextView tideTijd;
    public TextView tideTide;
    public TextView tideVal;


    public View view;

    public TidesListViewHolder(View itemView) {
        super(itemView);
        tideJaar = itemView.findViewById(R.id.jaar_veld);
        tideDatum = itemView.findViewById(R.id.datum_veld);
        tideTijd = itemView.findViewById(R.id.tijd_veld);
        tideTide = itemView.findViewById(R.id.tide_veld);
        tideVal = itemView.findViewById(R.id.val_veld);
        view = itemView;


    }

}

