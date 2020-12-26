package com.sonnyburnettfun.getijden;

import android.content.Context;
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


    @Override
    public void onBindViewHolder(final TidesListViewHolder holder, final int position) {
        final Waterstand tide = tidesList.get(position);        // find the position in the list
        holder.tideJaar.setText(tide.getYear());
        holder.tideDatum.setText(tide.getDate());
        holder.tideTijd.setText(tide.getTime());
        holder.tideTide.setText(tide.getTide());
        holder.tideVal.setText(tide.getVal());
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

