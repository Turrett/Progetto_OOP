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

        /*
        teoricamente, qui ho impostato i testi delle textview
        quindi, nel db posso direttamente prendere txtadding.getText()
        per recuperare i valori di cui ho bisogno
         */

        Bundle extras = getIntent().getExtras();
        assert extras != null;
        txtadding.setText(extras.getString("UserAddingId"));
        txtposting.setText(extras.getString("UserPostingId"));
        txtprod.setText(extras.getString("ProductId"));

        Button btnsave = root.findViewById(R.id.btnrecensione);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addreview();
            }
        });

    }

    private void addreview(){

        //controllo la presenza o meno della recensione
        String rece = recensione.getText().toString();
        if(rece.isEmpty()){
            recensione.setError("inserisci il testo della recensione!");
            recensione.requestFocus();
            return;
        }

        //controllo la presenza o meno delle stelline
        float ratereview = rating.getRating();
        if(ratereview<1){
            error.setError("Inserisci una recensione!");
            rating.requestFocus();
            return;
        }

        /*se arrivo qui vuol dire che è stato completato tutto in modo corretto
        e qindi posso aggiungere i dati nel documento "recensioni"
         */

        db = FirebaseFirestore.getInstance();

        Map<String, Object> review = new HashMap<>();
        review.put("UserPostingReviewId", txtadding.getText());
        review.put("UserReviewedId", txtposting.getText());
        review.put("ProductReviewId", txtprod.getText());
        review.put("BodyReview", rece);
        review.put("RatingReview", ratereview);
        review.put("state", "reviewed");


        db.collection("recensioni")
                .document()
                .set(review, SetOptions.merge())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(RecensioniActivity.this, "La tua recensione è stata salvata correttamente", Toast.LENGTH_SHORT).show();
                        RecensioniActivity.this.finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "errore nel salvataggio della recensione", e);

                    }
                });
    }

}
