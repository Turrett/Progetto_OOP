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
import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
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
        View view =inflater.inflate(R.layout.product_card,parent,false);
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
       public MyViewHolder(@NonNull View itemView) {
           super(itemView);
           expire = itemView.findViewById(R.id.card_expire);
           name = itemView.findViewById(R.id.card_product);
           quantity =  itemView.findViewById(R.id.card_quantrity);
           userid = itemView.findViewById(R.id.card_username);
       }
   }

}
