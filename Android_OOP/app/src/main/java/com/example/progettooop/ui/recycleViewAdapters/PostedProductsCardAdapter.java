package com.example.progettooop.ui.recycleViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.DashProduct;
import com.example.progettooop.ui.dashboard.SeeProductRequests;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostedProductsCardAdapter extends RecyclerView.Adapter<PostedProductsCardAdapter.DashViewHolder>{

    private ArrayList<DashProduct> prodotti;
    Context context;

    public PostedProductsCardAdapter(Context ct , ArrayList <DashProduct> prod){
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
    public void onBindViewHolder(@NonNull PostedProductsCardAdapter.DashViewHolder holder, int position) {

        holder.name.setText(prodotti.get(position).getNamedash());
        holder.quantity.setText(prodotti.get(position).getQuantitydash());
        holder.expire.setText(prodotti.get(position).getExpirationdash());
        //sets the number of requests
        holder.db.collection("richieste")
                .whereEqualTo("productId",prodotti.get(position).getProductId())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                               Integer count=0;
                                               for (QueryDocumentSnapshot document : task.getResult())
                                               {
                                                   count = count +1;
                                               }
                                               holder.numRequests.setText(count.toString());
                                           }
                                       });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                holder.db.collection("annuncio")
                        .document(prodotti.get(position).getProductId())
                        .delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(context,"product eliminated",Toast.LENGTH_SHORT).show();
                                prodotti.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                holder.itemView.setVisibility(View.GONE);
                            }
                        });
            }
        });

        holder.seeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO making the "VEDI TUTTE" button to work
                Intent intent = new Intent(view.getContext(), SeeProductRequests.class);
                intent.putExtra("productId",prodotti.get(position).getProductId());
                context.startActivity(intent);
            }
        });
    }


    @Override
    public int getItemCount() {
        return prodotti.size();
    }


    public static class DashViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire,numRequests;
        Button delete,seeAll;
        FirebaseFirestore db;


        public DashViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_dash);
            quantity =  itemView.findViewById(R.id.qta_dash);
            expire = itemView.findViewById(R.id.exp_dash);
            delete = itemView.findViewById(R.id.btndelete);
            seeAll =itemView.findViewById(R.id.btnshowall);
            numRequests =itemView.findViewById(R.id.numrichieste);
            db = FirebaseFirestore.getInstance();

        }
    }

}
