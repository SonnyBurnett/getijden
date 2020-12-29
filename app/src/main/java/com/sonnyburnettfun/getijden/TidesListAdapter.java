package com.sonnyburnettfun.getijden;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;


public class TidesListAdapter extends RecyclerView.Adapter<TidesListViewHolder> {


    private Context context;
    public List<Waterstand> tidesList;

    public TidesListAdapter(Context context, List<Waterstand> tidesList) {
        this.context = context;
        this.tidesList = tidesList;
    }


    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final TidesListViewHolder holder, final int position) {
        final Waterstand tide = tidesList.get(position);        // find the position in the list
        holder.tideJaar.setText(tide.getYear());
        holder.tideDatum.setText(DatumTijd.getDay(tide.getDate()) + "-" + DatumTijd.getMonth(tide.getDate()));
        holder.tideTijd.setText(DatumTijd.getHours(tide.getTime()) + ":" + DatumTijd.getMinutes(tide.getTime()));
        holder.tideTide.setText(tide.getTide());
        holder.tideVal.setText(tide.getVal());
        if (tide.getTide().equals("HW")) {
            holder.tideJaar.setBackgroundColor(R.color.vloedkleurlijst);
            holder.tideDatum.setBackgroundColor(R.color.vloedkleurlijst);
            holder.tideTijd.setBackgroundColor(R.color.vloedkleurlijst);
            holder.tideTide.setBackgroundColor(R.color.vloedkleurlijst);
            holder.tideVal.setBackgroundColor(R.color.vloedkleurlijst);
        }
        else {
            holder.tideJaar.setBackgroundColor(R.color.ebkleurlijst);
            holder.tideDatum.setBackgroundColor(R.color.ebkleurlijst);
            holder.tideTijd.setBackgroundColor(R.color.ebkleurlijst);
            holder.tideTide.setBackgroundColor(R.color.ebkleurlijst);
            holder.tideVal.setBackgroundColor(R.color.ebkleurlijst);

        }
    }

    @Override
    public TidesListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.tide_row_layout, parent, false);
        return new TidesListViewHolder(v);
    }

    @Override
    public int getItemCount() {
        return tidesList.size();
    }

}

