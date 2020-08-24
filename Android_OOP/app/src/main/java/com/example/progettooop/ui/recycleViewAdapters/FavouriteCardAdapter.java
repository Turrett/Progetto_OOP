package com.example.progettooop.ui.recycleViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.wishedProd;
import com.example.progettooop.ui.dashboard.ProductRequestActivity;
import com.example.progettooop.ui.user.ViewUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class FavouriteCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<wishedProd> products;
    private Context context;
    public static int TYPE_ACCEPTED =2;
    private static int TYPE_POSTED =1;

    public FavouriteCardAdapter(Context ct , ArrayList<wishedProd> prodotti) {
        products=prodotti;
        context=ct;
    }

    @Override
    public int getItemViewType(int position) {
        if (products.get(position).getState().equals("posted"))
            return 1;
        else
            return 2;
    }

    public static class FavouriteViewHolder extends RecyclerView.ViewHolder{
        TextView prod,qty,exp,username;
        Button delete,order,gotouser;

        public FavouriteViewHolder(@NonNull View itemView) {
            super(itemView);
            prod=itemView.findViewById(R.id.prodnamealt);
            qty=itemView.findViewById(R.id.prodqtaalt);
            exp=itemView.findViewById(R.id.prodexpalt);
            username=itemView.findViewById(R.id.productauthor);
            delete=itemView.findViewById(R.id.btnelimina);
            order=itemView.findViewById(R.id.btninvia);
            gotouser=itemView.findViewById(R.id.btngotouser);

        }
    }

    public static class FavouriteViewHolderAccepted extends RecyclerView.ViewHolder {
        TextView prod,qty,exp,status;
        Button delete;
        public FavouriteViewHolderAccepted(@NonNull View itemView) {
            super(itemView);
            prod=itemView.findViewById(R.id.prodnamealt);
            qty=itemView.findViewById(R.id.prodqtaalt);
            exp=itemView.findViewById(R.id.prodexpalt);
            status=itemView.findViewById(R.id.txtstatoannuncio);
            delete=itemView.findViewById(R.id.btnelimina);


        }

        private void setProductAccepted(wishedProd product){
            prod.setText(product.getName());
            qty.setText(product.getQuantity());
        }
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType==TYPE_POSTED){
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.watchlist_card,parent,false);
        return new FavouriteViewHolder(view);
        }
        else{
            LayoutInflater inflater =LayoutInflater.from(context);
            View view =inflater.inflate(R.layout.card_statordinato,parent,false);
            return new FavouriteViewHolderAccepted(view);
        }
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==TYPE_POSTED){
        holder.prod.setText(products.get(position).getName());
        holder.qty.setText(products.get(position).getQuantity());
        holder.exp.setText(products.get(position).getExpiration());

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("utenti").document(products.get(position).getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document=task.getResult();
                holder.username.setText(document.get("username").toString());
            }
        });//TODO adding a calculated field into product to reduce accesses to the server



        holder.order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProductRequestActivity.class);
                i.putExtra("productId",products.get(position).getProductId());
                context.startActivity(i);
            }
        });

     holder.delete.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View view) {
             db.collection("watchlist")
                     .document(products.get(position).getWishedId())
                     .delete()
                     .addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void aVoid) {
                             products.remove(position);
                             notifyItemRemoved(position);
                             notifyItemRangeChanged(position, getItemCount());
                             holder.itemView.setVisibility(View.GONE);
                         }
                     });
         }
     });

        holder.gotouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewUserData.class);
                i.putExtra("UserId",products.get(position).getUserId());
                context.startActivity(i);
            }
        });
        }

        else{

        }
    }



    @Override
    public int getItemCount() {
        return products.size();
    }



}
