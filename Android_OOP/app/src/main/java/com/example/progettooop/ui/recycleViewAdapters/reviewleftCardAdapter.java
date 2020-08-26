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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.Objects;

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

        // holder.txtproduct.setText(review.get(position).getTextprodotto());

       /* FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("annuncio")
                .document(review.get(position).getTextprodotto())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        assert document != null;
                        holder.txtproduct.setText(Objects.requireNonNull(document.get("name")).toString());
                    }
                });*/
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
