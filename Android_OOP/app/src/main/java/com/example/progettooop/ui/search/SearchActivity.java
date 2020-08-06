package com.example.progettooop.ui.search;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.Product;
import com.google.firebase.database.DatabaseReference;


public class SearchActivity extends AppCompatActivity {

    private EditText mSearchField;
    private ImageButton mSearchBtn;

    private RecyclerView mResultList;

    private DatabaseReference mUserDatabase;

    private Product[] products;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_search);

        //mUserDatabase = FirebaseDatabase.getInstance().getReference("annuncio");


        //mSearchField = (EditText) findViewById(R.id.search_field);
        //mSearchBtn = (ImageButton) findViewById(R.id.search_btn);

        //mResultList = (RecyclerView) findViewById(R.id.result_list);
        //mResultList.setHasFixedSize(true);
        //mResultList.setLayoutManager(new LinearLayoutManager(this));

        products[0]=new Product("latte","10","10/09/2020");
        products[1]=new Product("latte uht ","5","10/09/2010");



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.result_list);
        SearchAdapter adapter = new SearchAdapter(products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        /*mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*String searchText = mSearchField.getText().toString();
                firebaseUserSearch(searchText);*/


        //  }
        //});

    }



    // private void firebaseUserSearch(String searchText) {

    //Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        /*Query firebaseSearchQuery = mUserDatabase
                .orderByChild("username")
                .startAt("\uf8ff" + searchText)
                .endAt(searchText + "\uf8ff")
                .limitToFirst(10);



        FirebaseRecyclerOptions<Product> options =
                new FirebaseRecyclerOptions.Builder<Product>()
                        .setQuery(firebaseSearchQuery, Product.class)
                        .build();*/
/*ì
        products[0]=new Product("latte","10","10/09/2020");
        products[0]=new Product("latte uht ","5","10/09/2010");



        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.result_list);
        SearchAdapter adapter = new SearchAdapter(products);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);*/

        /*firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Product, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Product model) {


            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
                View listItem= layoutInflater.inflate(R.layout.annunci_per_search, parent, false);
                ViewHolder viewHolder = new ViewHolder(listItem);
                return viewHolder;
            }


        };*/



/*
    // View Holder Class

    public static class ViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public ViewHolder(View itemView) {
            super(itemView);

            mView = itemView;

        }

        public void setDetails(String userName, String quantity, String Expire) {

            TextView user_name = (TextView) mView.findViewById(R.id.name_text);
            TextView qty = mView.findViewById(R.id.quantity_show);
            TextView expire = mView.findViewById(R.id.expiration_show);


            user_name.setText(userName);
            qty.setText(quantity);
            expire.setText(Expire);

        }
    }*/
}



