package com.example.progettooop.ui.search;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.ListFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.progettooop.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class SearchActivity extends AppCompatActivity {

    /*private EditText searchtxt;
    private ImageButton searchbtn;
    private RecyclerView resultlist;
    private DatabaseReference db;
    private FirebaseRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseDatabase.getInstance().getReference("Users");


        searchtxt = (EditText) findViewById(R.id.search_field);
        searchbtn = (ImageButton) findViewById(R.id.search_btn);
        resultlist = (RecyclerView) findViewById(R.id.result_list);
        resultlist.setLayoutManager(new LinearLayoutManager(this));
        resultlist.setHasFixedSize(true);

        searchbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String searchText = searchtxt.getText().toString();

                firebaseUserSearch(searchText);

            }
        });

    }




    // View Holder Class

    public class ElementsViewHolder extends RecyclerView.ViewHolder {

        public TextView user_name;
        public TextView product;


        public ElementsViewHolder(View itemView) {
            super(itemView);
            user_name = (TextView) itemView.findViewById(R.id.name_text);
            product = (TextView) itemView.findViewById(R.id.status_text);
        }

        public void setUsername(String string){
            user_name.setText(string);
        }

        public void setProduct(String string){
            product.setText(string);
        }



        private void firebaseUserSearch(String searchText) {

        Toast.makeText(SearchActivity.this, "Started Search", Toast.LENGTH_LONG).show();

        Query firebaseSearchQuery = db.orderByChild("name").startAt(searchText);

        FirebaseRecyclerAdapter<Elements, ElementsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Elements, UsersViewHolder>(

                Elements.class,
                R.layout.annunci_per_search,
                ElementsViewHolder.class,
                firebaseSearchQuery

        ) {

            @Override
            protected void populateViewHolder(ElementsViewHolder viewHolder, Elements model, int position) {


                viewHolder.setDetails(getApplicationContext(), model.getName(), model.getStatus(), model.getImage());

            }
        };

        resultlist.setAdapter(firebaseRecyclerAdapter);

    }



    }*/



}


