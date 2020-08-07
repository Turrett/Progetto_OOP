package com.example.progettooop.ui.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class Searchmod extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    private Product[] products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        mUserDatabase = FirebaseDatabase.getInstance().getReference("annuncio");


        mSearchField = (EditText) findViewById(R.id.search_field);
        mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        mResultList = (RecyclerView) findViewById(R.id.result_list);
        mResultList.setLayoutManager(new LinearLayoutManager(this));
        mResultList.setHasFixedSize(true);


        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);


          }
        });

    }



    private void firebaseUserSearch(String searchText) {

        Toast.makeText(Searchmod.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = mUserDatabase
                .orderByChild("username")
                .startAt("\uf8ff" + searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);


        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(firebaseSearchQuery, Product.class)
                        .build();


        FirebaseRecyclerAdapter<Product, ProductViewHolder> firebaseRecyclerAdapter=
        new FirebaseRecyclerAdapter<Product, ProductViewHolder>(options) {
            @NonNull
            @Override
            public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View listItem = layoutInflater.inflate(R.layout.fragment_activeadv, parent, true);
                ProductViewHolder viewHolder = new ProductViewHolder(listItem);
                return viewHolder;
            }

            @Override
            protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull Product model) {
               /* final Product myListData = products[position];
                holder.username.setText(products[position].getName());
                holder.qty.setText(products[position].getQuantity());
                holder.expire.setText(products[position].getExpiration());
                holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(view.getContext(), "click on item: " + products.toString(), Toast.LENGTH_LONG).show();

                    }

                });*/
                holder.setDetails(model.getName(),model.getQuantity(),model.getExpiration());
            }
        };
        mResultList.setAdapter(firebaseRecyclerAdapter);
    }




    // View Holder Class

    public class ProductViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public ProductViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(String userName, String quantity, String Expire) {

            /*TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView qty = mView.findViewById(R.id.quantity_show);
            TextView expire = mView.findViewById(R.id.expiration_show);*/


            /*user_name.setText(userName);
            qty.setText(quantity);
            expire.setText(Expire);*/

        }
    }
}



