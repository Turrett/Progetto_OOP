package com.example.progettooop.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;
import com.example.progettooop.ui.user.UserFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private ArrayList< Product> products;
    Context context;

    public MyAdapter(Context ct , ArrayList < Product> prodotti){

        products=prodotti;
        context=ct;


    }

        // here i create my card
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.home_card,parent,false);
        return  new MyViewHolder(view);
    }

    // here i add information to my card
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.name.setText(products.get(position).getName());
      holder.quantity.setText(products.get(position).getQuantity());
      holder.expire.setText(products.get(position).getExpiration());
      holder.userid.setText(products.get(position).getUserId());
      holder.productId =products.get(position).getProduct();

    }

    @Override
    public int getItemCount() {
        return products.size();
    }


// here i declare what are the attributes of the card and how it behaves
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire,userid;
        Button  goToUser;
        FloatingActionButton addToFavorite;
        String productId;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           expire = itemView.findViewById(R.id.productexpire);
           name = itemView.findViewById(R.id.productname);
           quantity =  itemView.findViewById(R.id.productquantity);
           userid = itemView.findViewById(R.id.productauthor);

           addToFavorite = itemView.findViewById(R.id.add_to_fav_btn);
           addToFavorite.setOnClickListener(new View.OnClickListener() {

               @Override
               public void onClick(View view) {
                   FirebaseFirestore db = FirebaseFirestore.getInstance();
                   Map<String, Object> prod = new HashMap<>();
                   prod.put("User",userid.getText());
                   prod.put("Product",productId);
                   prod.put("date",System.currentTimeMillis());

                   db.collection("watchlist")
                           .document()
                           .set(prod, SetOptions.merge())
                           .addOnSuccessListener(new OnSuccessListener<Void>() {
                               @Override
                               public void onSuccess(Void aVoid) {
                                   Toast.makeText(view.getContext(),"added to favourites",Toast.LENGTH_SHORT).show();

                               }
                           })
                   .addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(view.getContext(),"cannot add to favourites",Toast.LENGTH_SHORT).show();
                       }
                   });

               }
           });

           goToUser =itemView.findViewById(R.id.btngotouser);
           goToUser.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(view.getContext(),"bla",Toast.LENGTH_SHORT).show();
                   UserFragment userFragment = new UserFragment();
                   FragmentManager fragmentManager = new FragmentManager(){} ;
                   fragmentManager.beginTransaction().replace(R.id.list_home,userFragment);
               }
           });



       }
   }

}
