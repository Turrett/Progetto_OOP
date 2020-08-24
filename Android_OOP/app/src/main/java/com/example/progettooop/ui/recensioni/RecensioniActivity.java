package com.example.progettooop.ui.recensioni;

import android.content.Intent;
import android.media.Rating;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.AddAdvFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class RecensioniActivity extends AppCompatActivity {
    TextView txtadding, txtposting, txtprod, error;
    EditText recensione;
    RatingBar rating;

    private FirebaseAuth mAuth;
    public FirebaseFirestore db;
    private StorageReference ref;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = getLayoutInflater().inflate(R.layout.layout_recensione,null);
        setContentView(root);
        setTitle("Scrivi qui la tua recensione");
        txtadding =root.findViewById(R.id.txtuseradding);
        txtposting = root.findViewById(R.id.txtuserposting);
        recensione = root.findViewById(R.id.txtrecensione);
        error = (TextView) root.findViewById(R.id.txterror);
        rating = (RatingBar) root.findViewById(R.id.rating_recensione);

        Bundle extras = getIntent().getExtras();
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        if (Objects.equals(Objects.requireNonNull(extras).getString("type"), "watchlist")){
            //caricavalori();
        }

        Button btnsave = root.findViewById(R.id.btnrecensione);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addreview();
            }
        });

    }

    private void addreview(){
        String rece = recensione.getText().toString();
        if(rece.isEmpty()){
            recensione.setError("inserisci il testo della recensione!");
            recensione.requestFocus();
            return;
        }

        float rate = rating.getRating();
        if(rate<1){
            error.setError("Inserisci una recensione!");
            rating.requestFocus();
            return;
        }

        //robe per aggiungere i dati nel db


    }

   /* private void caricavalori(){
        db.collection("watchlist")
                .whereEqualTo("UserAddingId", mAuth.getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        DocumentSnapshot document = task.getResult();
                        txtadding.setText(extras.getString("UserAddingId"));
                        txtposting.setText(extras.getString("UserPostingId"));
                        txtprod.setText(extras.getString("ProductId"));
                    }
                });
    }*/
}
