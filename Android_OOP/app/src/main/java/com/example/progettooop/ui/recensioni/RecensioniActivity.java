package com.example.progettooop.ui.recensioni;

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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.firestore.WriteBatch;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class RecensioniActivity extends AppCompatActivity {
    TextView txtposting, txtprod, error;
    EditText recensione;
    RatingBar rating;
    Bundle extras;

    public FirebaseFirestore db;
    private StorageReference ref;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recensione);
        setTitle("Scrivi qui la tua recensione");
        txtposting = findViewById(R.id.txtuserposting);
        recensione = findViewById(R.id.txtrecensione);
        error = findViewById(R.id.txterror);
        rating =findViewById(R.id.rating_recensione);
        txtprod =findViewById(R.id.txtprodid);
        /*
        teoricamente, qui ho impostato i testi delle textview
        quindi, nel db posso direttamente prendere txtadding.getText()
        per recuperare i valori di cui ho bisogno
         */
         extras = getIntent().getExtras();
        assert extras != null;

        txtposting.setText(extras.getString("UserPostingId"));
        txtprod.setText(extras.getString("ProductId"));

        Button btnsave = findViewById(R.id.btnrecensione);
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
        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat ("E dd.MM.yyyy 'at' hh:mm:ss a zzz");

        Map<String, Object> review = new HashMap<>();
        review.put("UserPostingReviewId", extras.getString("UserAddingId"));
        review.put("UserReviewedId", extras.getString("UserPostingId"));
        review.put("ProductReviewId", extras.getString("ProductId"));
        review.put("BodyReview", rece);
        review.put("RatingReview", ratereview);
        review.put("date",ft.format(date));
        review.put("state", "reviewed");

        WriteBatch batch =db.batch();

        DocumentReference doc1=db.collection("watchlist").document(extras.getString("watchlistId"));
        DocumentReference doc2=db.collection("annuncio").document(extras.getString("ProductId"));
        DocumentReference doc3 = db.collection("recensione").document();

        batch.delete(doc1);
        batch.update(doc2,"state","reviewed");
        batch.set(doc3,review, SetOptions.merge());

        batch.commit().addOnSuccessListener(new OnSuccessListener<Void>() {
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
