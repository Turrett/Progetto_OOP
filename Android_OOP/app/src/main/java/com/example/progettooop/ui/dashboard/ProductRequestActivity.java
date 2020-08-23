package com.example.progettooop.ui.dashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettooop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class ProductRequestActivity extends AppCompatActivity {
 EditText when,message;
 Button send;
    FirebaseFirestore db;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_ordine);
        when = findViewById(R.id.editorder);
        message =findViewById(R.id.editmessage);
        send=findViewById(R.id.btnsend);
        db=FirebaseFirestore.getInstance();
        auth=FirebaseAuth.getInstance();
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                save();
            }
        });
    }

    private void save(){
        String When =when.getText().toString();
        if (When.isEmpty()){
            when.setError("inserisci una possibile data di ritiro");
            when.requestFocus();
            return;
        }
        String Message =this.message.getText().toString();
        if (Message.isEmpty()){
            message.setError("inserisci una possibile data di ritiro");
            message.requestFocus();
            return;
        }

        Bundle extra = getIntent().getExtras();
        assert extra != null;

        Map<String, Object> request = new HashMap<>();
        request.put("userId",auth.getUid());
        request.put("productId",extra.get("productId"));
        request.put("when",When);
        request.put("Message",Message);
        request.put("status","requested");

        db.collection("richieste")
                .document()
                .set(request, SetOptions.merge())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(),"correctly requested",Toast.LENGTH_SHORT).show();
                    }
                });

    }



}