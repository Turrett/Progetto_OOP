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
import com.example.progettooop.ui.recycleViewAdapters.reviewleftCardAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Objects;

public class reviewleftActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RecyclerView recyclerView;
    private RecyclerView result;
    private View view;


        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            view = getLayoutInflater().inflate(R.layout.layout_reviewleft, null);
            setContentView(R.layout.layout_reviewleft);
            setTitle("Leggi qui cosa si pensa di te");

            //inizializzo le variabili degli elementi nella view
            result = findViewById(R.id.result_reviewleft);
           ArrayList<reviewleft> review = new ArrayList<reviewleft>();
            searchreviewtorecycler(review);
        }

        public void searchreviewtorecycler(ArrayList<reviewleft> r){
            db = FirebaseFirestore.getInstance();
            auth = FirebaseAuth.getInstance();

            db.collection("recensione")
                    .whereEqualTo("UserReviewedId", auth.getUid())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {
                                        r.add(new reviewleft(document.getString("BodyReview"),
                                                 document.getString("ProductReviewId"),
                                                 document.get("RatingReview", Float.class)
                                        ));
                                }

                                reviewleftCardAdapter myAdapter = new reviewleftCardAdapter(view.getContext(), r);
                                result.setAdapter(myAdapter);
                                result.setLayoutManager(new LinearLayoutManager(view.getContext()));

                            } else {
                                Toast.makeText(reviewleftActivity.this, "task not successfull", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(reviewleftActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });








        }
}


