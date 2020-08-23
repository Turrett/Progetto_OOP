package com.example.progettooop.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Request;
import com.example.progettooop.ui.recycleViewAdapters.ProductRequestAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SeeProductRequestsActivity extends AppCompatActivity {
    String ProductId;
    ArrayList<Request> requests ;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_watchlist);

        requests=new ArrayList<>();
        recyclerView = findViewById(R.id.rec_cardrequest);

        Bundle extra = getIntent().getExtras();
        ProductId = extra.getString("productId");
        RequestsToRecyclerView(requests,ProductId);
    }

    public void RequestsToRecyclerView(ArrayList<Request> requests, String ProductId){
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("richieste")
                .whereEqualTo("productId",ProductId)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            for (QueryDocumentSnapshot document:task.getResult()){
                                requests.add(new Request(
                                        document.getString("productId"),
                                        document.getString("userId"),
                                        document.getString("message"),
                                        document.getString("when"),
                                        document.getId()));
                            }
                        }
                        ProductRequestAdapter myAdapter = new ProductRequestAdapter(requests,getApplicationContext());
                        recyclerView.setAdapter(myAdapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                    }
                });
    }
}

