package com.example.progettooop.ui.dashboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.progettooop.R;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.DashViewHolder>{

    private ArrayList<DashProduct> prodotti;
    Context context;

    public CardAdapter(Context ct , ArrayList <DashProduct> prod){
        prodotti = prod;
        context=ct;
    }

    @NonNull
    @Override
    public DashViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.product_card_dashboard,parent,false);
        return new DashViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull CardAdapter.DashViewHolder holder, int position) {
        holder.name.setText(prodotti.get(position).getName());
        holder.quantity.setText(prodotti.get(position).getQuantity());
        holder.expire.setText(prodotti.get(position).getExpiration());

    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }


    public static class DashViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire;
        public DashViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_dash);
            quantity =  itemView.findViewById(R.id.qta_dash);
            expire = itemView.findViewById(R.id.exp_dash);
        }
    }

}
