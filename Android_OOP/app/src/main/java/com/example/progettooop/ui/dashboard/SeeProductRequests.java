package com.example.progettooop.ui.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettooop.R;
import com.example.progettooop.ui.Objects.Request;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SeeProductRequests extends AppCompatActivity {
    String ProductId;
    ArrayList<Request> requests ;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ordine);

        requests=new ArrayList<>();

        Bundle extra = getIntent().getExtras();
        ProductId = extra.getString("productId");

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
                    }
                });
    }
}
