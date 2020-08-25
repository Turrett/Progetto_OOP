package com.example.progettooop.ui.dashboard.watchlist;

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
import com.example.progettooop.ui.recensioni.RecensioniActivity;
import com.example.progettooop.ui.user.ViewUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.WriteBatch;

import java.util.ArrayList;

public class WatchlistCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<wishedProd> products;
    private Context context;
    private static int TYPE_ACCEPTED =2;
    private static int TYPE_POSTED =1;


    public WatchlistCardAdapter(Context ct , ArrayList<wishedProd> prodotti) {
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

    public class FavouriteViewHolder extends RecyclerView.ViewHolder{
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
        public void setProductPosted (ArrayList<wishedProd> products,Context context, int position) {
            prod.setText(products.get(position).getName());
        qty.setText(products.get(position).getQuantity());
        exp.setText(products.get(position).getExpiration());

        FirebaseFirestore db =FirebaseFirestore.getInstance();
        db.collection("utenti").document(products.get(position).getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot document=task.getResult();
                username.setText(document.get("username").toString());
            }
        });//TODO adding a calculated field into product to reduce accesses to the server



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ProductRequestActivity.class);
                i.putExtra("watchlistId",products.get(position).getWishedId());
                context.startActivity(i);
            }
        });

     delete.setOnClickListener(new View.OnClickListener() {
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
                                itemView.setVisibility(View.GONE);
                            }
                        });

            }
        });

        gotouser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewUserData.class);
                i.putExtra("UserId",products.get(position).getUserId());
                context.startActivity(i);
            }
        });
    }
}

    public class FavouriteViewHolderAccepted extends RecyclerView.ViewHolder {
        private static final int TYPE_POSTED = 1;
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
        private void setProductAccepted(ArrayList<wishedProd>products,int position){
            prod.setText(products.get(position).getName());
            qty.setText(products.get(position).getQuantity());
            exp.setText(products.get(position).getExpiration());
            status.setText(products.get(position).getState());
            //TODO sostituire questo if con una card a parte
            if (products.get(position).getState().equals("retired")){
             delete.setText("RECENSISCI");
             delete.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     Intent intent = new Intent(view.getContext(), RecensioniActivity.class);
                     intent.putExtra("UserAddingId",products.get(position).getUserAddingId());
                     intent.putExtra("UserPostingId",products.get(position).getUserId());
                     intent.putExtra("ProductId",products.get(position).getProductId());
                     intent.putExtra("watchlistId",products.get(position).getWishedId());
                     context.startActivity(intent);
                 }
             });
            }
            else {
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        WriteBatch batch = db.batch();

                        if (products.get(position).getState().equals("accepted")) {
                            DocumentReference doc1 = db.collection("annuncio")
                                    .document(products.get(position).getProductId());
                            batch.update(doc1, "state", "posted");
                        }

                        DocumentReference doc2 = db.getInstance().collection("watchlist")
                                .document(products.get(position).getWishedId());
                        batch.delete(doc2);

                        batch.commit().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                products.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, getItemCount());
                                itemView.setVisibility(View.GONE);
                            }
                        });

                    }
                });
            }
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
        if(getItemViewType(position)==TYPE_POSTED) {
            ((FavouriteViewHolder)holder).setProductPosted(products,context,position);
        }
        else {
            ((FavouriteViewHolderAccepted)holder).setProductAccepted(products,position);
        }
    }



    @Override
    public int getItemCount() {
        return products.size();
    }



}
