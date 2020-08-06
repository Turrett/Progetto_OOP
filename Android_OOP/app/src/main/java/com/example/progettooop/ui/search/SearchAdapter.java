package com.example.progettooop.ui.search;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;

public class SearchAdapter  extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {

    private Product[] products;

    public SearchAdapter(Product[] products) {
        this.products=products;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.annunci_per_search, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Product myListData = products[position];
        holder.username.setText(products[position].getName());
        holder.qty.setText(products[position].getQuantity());
        holder.expire.setText(products[position].getExpiration());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"click on item: "+products.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return products.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView qty;
        public TextView expire;
        public RelativeLayout relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.username = (TextView) itemView.findViewById(R.id.name_text);
            this.qty = (TextView) itemView.findViewById(R.id.quantity_show);
            this.expire=(TextView) itemView.findViewById(R.id.expiration_show);
            relativeLayout = (RelativeLayout)itemView.findViewById(R.id.show_Rl);
        }
    }
}

