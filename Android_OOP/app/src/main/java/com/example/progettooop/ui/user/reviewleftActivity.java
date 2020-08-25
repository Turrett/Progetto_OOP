package com.example.progettooop.ui.user;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.reviewleft;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

//import com.example.progettooop.ui.recycleViewAdapters.ReviewleftAdapter;

public class reviewleftActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            View root = getLayoutInflater().inflate(R.layout.layout_reviewleft, null);
            setContentView(root);
            setTitle("Leggi qui cosa si pensa di te");
           /* ArrayList<reviewleft> review = new ArrayList<reviewleft>();
            searchreviewtorecycler(root, review);*/
        }

        public void searchreviewtorecycler(View v, ArrayList<reviewleft> r){
            db = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();


        }
}


