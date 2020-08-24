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
import com.example.progettooop.ui.Objects.Product;
import com.example.progettooop.ui.user.ViewUserData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HomeAndSearchCardAdapter extends RecyclerView.Adapter<HomeAndSearchCardAdapter.MyViewHolder>  {
    private ArrayList< Product> products;
    Context context;

    public HomeAndSearchCardAdapter(Context ct , ArrayList<Product> prodotti){

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
      FirebaseFirestore db =FirebaseFirestore.getInstance();
      db.collection("utenti").document(products.get(position).getUserId()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
          @Override
          public void onComplete(@NonNull Task<DocumentSnapshot> task) {
              DocumentSnapshot document=task.getResult();
              holder.userid.setText(document.get("username").toString());
          }
      });//TODO adding a calculated field into product to reduce accesses to the server

        holder.addToFavorite.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                FirebaseAuth auth = FirebaseAuth.getInstance();
                Date date = new Date();
                SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy 'at' hh:mm:ss a zzz");

                Map<String, Object> prod = new HashMap<>();
                prod.put("UserAddingId",auth.getUid());
                prod.put("UserPostingId",products.get(position).getUserId());
                prod.put("ProductId",products.get(position).getProductId());
                prod.put("date",ft.format(date));
                prod.put("quantity",products.get(position).getQuantity());
                prod.put("expire",products.get(position).getExpiration());
                prod.put("name",products.get(position).getName());
                prod.put("state",products.get(position).getState());

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
      holder.productId =products.get(position).getProductId();
        holder.goToUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(view.getContext(), ViewUserData.class);
                i.putExtra("UserId",products.get(position).getUserId());
                context.startActivity(i);
            }
        });

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

           goToUser =itemView.findViewById(R.id.btngotouser);




       }
   }

}
