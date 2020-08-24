package com.example.progettooop.ui.recycleViewAdapters;

import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public class SampleRecycler extends RecyclerView.Adapter<SampleRecycler.SampleHolder> {

    // SampleHolder.java
    public class SampleHolder extends RecyclerView.ViewHolder {
        public SampleHolder(View itemView) {
            super(itemView);
        }
    }
    @Override
    public SampleHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(SampleHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}