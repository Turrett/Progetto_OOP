package com.example.progettooop.ui;
import android.content.Context;
import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.progettooop.ui.user.ModifyUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


import static android.content.ContentValues.TAG;


public class FirebaseUtil  {
    private FirebaseAuth mAuth;
    private Context context ;
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public int lastOperationResult = 1;
    public FirebaseFirestore db;

    public FirebaseUtil(Context context){
        this.context= context;
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        lastOperationResult=0;
    }





    public void insertUserFromAuth(){
        Map<String, Object> user = new HashMap<>();
        user.put( "email", Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        user.put("Uid",mAuth.getCurrentUser().getUid());

        db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser().getUid()))
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"correctly committed data to server",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "DocumentSnapshot" );
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Error adding document", e);
            }
        });
    }

    public void addDataToUser(String phone,String username,String indirizzo){


    }


}
