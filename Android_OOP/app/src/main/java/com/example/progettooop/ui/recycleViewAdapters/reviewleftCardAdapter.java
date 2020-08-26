package com.example.progettooop.ui.recycleViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.Objects.reviewleft;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class reviewleftCardAdapter extends RecyclerView.Adapter<reviewleftCardAdapter.ViewHolderRecensione> {

    private ArrayList<reviewleft> review;
    Context context;

    public reviewleftCardAdapter(Context ct , ArrayList<reviewleft> r){
        review=r;
        context=ct;
    }

    @NonNull
    @Override
    public ViewHolderRecensione onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.reviewleft_card,parent,false);
        return  new ViewHolderRecensione(view);
    }


    //qui aggiungo le informazioni alla card
    @Override
    public void onBindViewHolder(@NonNull ViewHolderRecensione holder, int position) {
        holder.txtbody.setText(review.get(position).getTextreview());
        holder.txtproduct.setText(review.get(position).getTextprodotto());
        holder.rate.setRating(review.get(position).getRatingleft());
    }

    @Override
    public int getItemCount() {
        return review.size();

    }


    //classe nella quale dichiaro e definisco gli elementi della card
    public static class ViewHolderRecensione extends RecyclerView.ViewHolder{
        TextView txtbody, txtproduct;
        RatingBar rate;
        public ViewHolderRecensione(@NonNull View itemView) {
            super(itemView);
            txtbody = itemView.findViewById(R.id.reviewbody);
            txtproduct = itemView.findViewById(R.id.txtprod);
            rate = itemView.findViewById(R.id.ratereview);
        }
    }

}
