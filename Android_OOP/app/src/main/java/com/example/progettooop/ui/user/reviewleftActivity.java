package com.example.progettooop.ui.user;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.ui.Objects.DashProduct;
import com.example.progettooop.ui.Objects.reviewleft;
import com.example.progettooop.R;
import com.example.progettooop.ui.recycleViewAdapters.PostedProductsCardAdapter;
import com.example.progettooop.ui.recycleViewAdapters.ReviewleftAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

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
            ArrayList<reviewleft> review = new ArrayList<reviewleft>();
            searchreviewtorecycler(root, review);
        }

        public void searchreviewtorecycler(View v, ArrayList<reviewleft> r){
            db = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();


        }
}


