package com.example.progettooop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import com.example.progettooop.ui.FirebaseUtil;

import com.example.progettooop.R;

import com.example.progettooop.ui.user.ModifyUserInfo;
import com.google.firebase.auth.*;
import com.google.firebase.database.*;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
 private EditText EditTextPassword,EditTextEmail;
    private ProgressBar progressBar;
    public FirebaseUtil firebaseUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditTextPassword=(EditText) findViewById(R.id.edit_text_register_password);
        EditTextEmail=(EditText) findViewById(R.id.edit_text_registration_email);
       progressBar = (ProgressBar) findViewById(R.id.progressbar_sign_up);
       firebaseUtil = new FirebaseUtil(getApplicationContext());
        findViewById(R.id.button_sign_up).setOnClickListener(this);
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

        int result = firebaseUtil.RegisterUser(email,password);
        System.out.println(result);
        if (result == firebaseUtil.SUCCESS) {
            Intent intent =new Intent(SignUp.this, ModifyUserInfo.class);
            startActivity(intent);}
        progressBar.setVisibility(View.GONE);

    }
}