package com.example.progettooop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.example.progettooop.R;

import com.example.progettooop.ui.user.ModifyUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
 private EditText EditTextPassword,EditTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    public FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditTextPassword=(EditText) findViewById(R.id.edit_text_register_password);
        EditTextEmail=(EditText) findViewById(R.id.edit_text_registration_email);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_sign_up);

       findViewById(R.id.button_sign_up).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }


    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_sign_up:
                registerUser();
                break;
            case R.id.button_go_to_sign_in:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }

    //@Override
    /*public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser) {
    }*/


    //login function given mail and password
    private void registerUser(){

        String email =  EditTextEmail.getText().toString().trim();
        String password =  EditTextPassword.getText().toString().trim();
        //mail cannot be empty
        if (email.isEmpty()){
            EditTextEmail.setError("Email is required");
            EditTextEmail.requestFocus();
            return;
        }
        //main has to respect a certain form
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            EditTextEmail.setError("Please enter a valid email");
            EditTextEmail.requestFocus();
            return;
       }
        ///password has to be 6 word minimum
        if(password.length()<6){
            EditTextPassword.setError("You need to put in a longer password, 6 character minimum");
            EditTextPassword.requestFocus();
            return;
        }
        //password cannot be empty
        if (password.isEmpty()){
            EditTextPassword.setError("Password is required");
            EditTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                            insertUserFromAuth();

                        } else {
                            if (task.getException() instanceof FirebaseAuthUserCollisionException){
                                Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
                            }
                            else {
                                Toast.makeText(getApplicationContext(), "User not Registered Correctly", Toast.LENGTH_SHORT).show();
                            }
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });

    }

    private void insertUserFromAuth(){
        Map<String, Object> user = new HashMap<>();
        user.put( "email", Objects.requireNonNull(mAuth.getCurrentUser()).getEmail());
        user.put("Uid",mAuth.getCurrentUser().getUid());

        db.collection("utenti")
                .document(Objects.requireNonNull(mAuth.getCurrentUser().getUid()))
                .set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(),"correctly committed data to server",Toast.LENGTH_SHORT).show();
                Log.d(TAG, "DocumentSnapshot" );
                progressBar.setVisibility(View.GONE);
                changeUi();
            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                        progressBar.setVisibility(View.GONE);
                    }
                });
}

private void changeUi(){

    Intent intent =new Intent(SignUp.this, ModifyUserInfo.class);
    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);}
}
