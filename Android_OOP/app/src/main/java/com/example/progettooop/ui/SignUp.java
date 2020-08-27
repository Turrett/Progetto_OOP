package com.example.progettooop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.progettooop.R;
import com.example.progettooop.ui.user.ModifyUserInfo;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    private EditText EditTextPassword,EditTextEmail, EditTextPasswordCheck;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    public FirebaseFirestore db;
    public Button btngoto, btnsignup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setTitle("Inizia la tua registrazione qui");
        EditTextPassword=(EditText) findViewById(R.id.edit_text_register_password);
        EditTextEmail=(EditText) findViewById(R.id.edit_text_registration_email);
        EditTextPasswordCheck = (EditText) findViewById(R.id.edit_text_confirm_password);
        progressBar = (ProgressBar) findViewById(R.id.progressbar_sign_up);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        btngoto = (Button) findViewById(R.id.button_go_to_sign_in);
        btngoto.setOnClickListener(this);
        btnsignup = (Button) findViewById(R.id.button_sign_up);
        btnsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.button_sign_up:
                registerUser();
                break;
            case R.id.button_go_to_sign_in:
                SignUp.this.finish();
                break;
            default:
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
        String passwordcheck = EditTextPasswordCheck.getText().toString().trim();
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

        if(!password.equals(passwordcheck)){
            EditTextPasswordCheck.setError("Doesn't match password");
            EditTextPasswordCheck.requestFocus();
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
        intent.putExtra("type","SignUp");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
