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
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {
    private ArrayList< Product> products;
    Context context;


    public MyAdapter(Context ct , ArrayList < Product> prodotti){

        products=prodotti;
        context=ct;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.home_card,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.name.setText(products.get(position).getName());
      holder.quantity.setText(products.get(position).getQuantity());
      holder.expire.setText(products.get(position).getExpiration());
      holder.userid.setText(products.get(position).getUserId());

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire,userid;
        Button  goToUser;
        FloatingActionButton addToFavorite;
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

               }
           });

           goToUser =itemView.findViewById(R.id.btngotouser);
           goToUser.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   Toast.makeText(view.getContext(),"bla",Toast.LENGTH_SHORT).show();
                   UserFragment userFragment = new UserFragment();
                   FragmentManager fragmentManager ;

               }
           });



       }
   }

}
