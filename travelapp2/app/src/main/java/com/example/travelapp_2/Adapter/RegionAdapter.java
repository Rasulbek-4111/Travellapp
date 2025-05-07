package com.example.travelapp_2.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.travelapp_2.Model.RegionModel;
import com.example.travelapp_2.R;

import java.util.ArrayList;

public class RegionAdapter extends RecyclerView.Adapter<RegionAdapter.ViewHolder> {
    private ArrayList<RegionModel> regions;
    private OnRegionClickListener listener;

    public interface OnRegionClickListener {
        void onRegionClick(RegionModel region);
    }

    public RegionAdapter(ArrayList<RegionModel> regions, OnRegionClickListener listener) {
        this.regions = regions;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_region, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RegionModel region = regions.get(position);
        holder.regionName.setText(region.getName());
        holder.regionDescription.setText(region.getDescription());
        
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onRegionClick(region);
            }
        });
    }

    @Override
    public int getItemCount() {
        return regions.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView regionName;
        TextView regionDescription;

        ViewHolder(View itemView) {
            super(itemView);
            regionName = itemView.findViewById(R.id.regionName);
            regionDescription = itemView.findViewById(R.id.regionDescription);
        }
    }
} 