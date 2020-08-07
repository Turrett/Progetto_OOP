package com.example.progettooop.ui.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    Product products[];
    Context context;

    public MyAdapter(Context ct , Product Prodotti[]){

        products=Prodotti;
        context=ct;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(context);
        View view =inflater.inflate(R.layout.product_card,parent,false);
        return  new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
      holder.name.setText(products[position].getName());
      holder.quantity.setText(products[position].getQuantity());
      holder.expire.setText(products[position].getExpiration());

    }

    @Override
    public int getItemCount() {
        return products.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView name,quantity,expire;
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           expire = itemView.findViewById(R.id.card_expire);
           name = itemView.findViewById(R.id.card_product);
           quantity =  itemView.findViewById(R.id.card_quantrity);
       }
   }

}
