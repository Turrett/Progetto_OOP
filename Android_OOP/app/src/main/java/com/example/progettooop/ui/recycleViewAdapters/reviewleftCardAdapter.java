package com.example.progettooop.ui.recycleViewAdapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.Objects.reviewleft;
import com.example.progettooop.ui.recensioni.RecensioniActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
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

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            DocumentReference doc1 = db.collection("watchlist")
                    .document(Objects.requireNonNull(review.get(position).getTextprodotto()));
            doc1.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if(documentSnapshot.exists()){
                        holder.txtproduct.setText(documentSnapshot.getString("name"));
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @SuppressLint("ShowToast")
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(context,"Fail Loading Data",Toast.LENGTH_SHORT);

                }

            });
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
