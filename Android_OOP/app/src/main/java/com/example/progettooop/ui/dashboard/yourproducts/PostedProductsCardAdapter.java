package com.example.progettooop.ui.dashboard.yourproducts;

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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class PostedProductsCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<DashProduct> prodotti;
    Context context;

    public PostedProductsCardAdapter(Context ct , ArrayList <DashProduct> prod){
        prodotti = prod;
        context=ct;
    }




    @Override
    public int getItemViewType(int position) {
        if (prodotti.get(position).getState().equals("accepted"))
            return 1;//product accepted
        if(prodotti.get(position).getState().equals("posted")||prodotti.get(position).getState().equals("requested"))
            return 2;//product posted or requested
        else
            return 0;
    }



    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType==2) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.product_card_dashboard, parent, false);
            return new DashPostedViewHolder(view);
        }
        if(viewType==1) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.layout_ritiro,parent,false);
            return new DashAcceptedViewHolder(view);
        }
        else
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(getItemViewType(position)==2) {
            ((DashPostedViewHolder)holder).runPost(prodotti,position,context);
        }
        if(getItemViewType(position)==1) {
            ((DashAcceptedViewHolder)holder).runAccept(prodotti,position,context);
        }
    }

    @Override
    public int getItemCount() {
        return prodotti.size();
    }




    public class DashAcceptedViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire,userBuying;
        Button retired;

        public DashAcceptedViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.prod_dash);//stesso nome può causare problemi ???
            quantity = itemView.findViewById(R.id.qta_dash);
            expire =itemView.findViewById(R.id.exp_dash);
            userBuying =itemView.findViewById(R.id.txtutilizzatore);

            retired=itemView.findViewById(R.id.buttonretire);
        }

        public void runAccept(ArrayList<DashProduct>prodotti , int position, Context context){
            name.setText(prodotti.get(position).getNamedash());
            quantity.setText(prodotti.get(position).getQuantitydash());
            expire.setText(prodotti.get(position).getExpirationdash());
            userBuying.setText(prodotti.get(position).getUsername());


            retired.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    FirebaseFirestore.getInstance().collection("wishlist")
                            .document(prodotti.get(position).getWishedID())
                            .update("state","retired");
                    FirebaseFirestore.getInstance().collection("annuncio")
                            .document(prodotti.get(position).getProductId())
                            .update("state","retired");
                }
            });
        }


    }



    public class DashPostedViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire,numRequests;
        Button delete,seeAll;
        FirebaseFirestore db;


        public DashPostedViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.prod_dash);
            quantity =  itemView.findViewById(R.id.qta_dash);
            expire = itemView.findViewById(R.id.exp_dash);
            delete = itemView.findViewById(R.id.btndelete);
            seeAll =itemView.findViewById(R.id.btnshowall);
            numRequests =itemView.findViewById(R.id.numrichieste);
            db = FirebaseFirestore.getInstance();

        }

        public void runPost(ArrayList<DashProduct>prodotti , int position, Context context){

            name.setText(prodotti.get(position).getNamedash());
            quantity.setText(prodotti.get(position).getQuantitydash());
            expire.setText(prodotti.get(position).getExpirationdash());
            //sets the number of requests
            db.collection("richieste")
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
                            numRequests.setText(count.toString());
                        }
                    });

            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    db.collection("annuncio")
                            .document(prodotti.get(position).getProductId())
                            .delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(context,"product eliminated",Toast.LENGTH_SHORT).show();
                                    prodotti.remove(position);
                                    notifyItemRemoved(position);
                                    notifyItemRangeChanged(position, getItemCount());
                                    itemView.setVisibility(View.GONE);
                                }
                            });
                }
            });

            seeAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(view.getContext(), SeeProductRequestsActivity.class);
                    intent.putExtra("productId",prodotti.get(position).getProductId());
                    context.startActivity(intent);
                }
            });
        }
    }


}
