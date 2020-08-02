package com.example.progettooop.ui;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;




public class FirebaseUtil  {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private Context context ;
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public int lastOperationResult = 0;

    public FirebaseUtil(Context context){
        this.context= context;
        mAuth = FirebaseAuth.getInstance();
    }

    public int RegisterUser (String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context.getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            lastOperationResult = FirebaseUtil.SUCCESS;

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(context.getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(context.getApplicationContext(), "User not Registered Correctly", Toast.LENGTH_SHORT).show();
                            }
                            lastOperationResult =FirebaseUtil.FAIL;
                        }
                    }

    });
        return lastOperationResult;
    }

    public int LogIn(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    lastOperationResult=FirebaseUtil.SUCCESS;
                    Toast.makeText(context, "success", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(context, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    lastOperationResult=FirebaseUtil.FAIL;
                }

            }
        });
        return lastOperationResult;
    }


}
