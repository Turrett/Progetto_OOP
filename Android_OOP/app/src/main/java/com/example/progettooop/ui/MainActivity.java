package com.example.progettooop.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.progettooop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private EditText EditTextPassword, EditTextEmail;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.sign_in_button).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);

        EditTextPassword = (EditText) findViewById(R.id.edit_text_login_password);
        EditTextEmail = (EditText) findViewById(R.id.edit_text_login_mail);
        progressBar =(ProgressBar) findViewById(R.id.progressbar_log_in);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login_button:
                LogUser();
                break;

            case R.id.sign_in_button:
               startActivity(new Intent(this, SignUp.class));
                break;
        }
    }


    private void LogUser() {

        String email = EditTextEmail.getText().toString().trim();
        String password = EditTextPassword.getText().toString().trim();
        //mail cannot be empty
        if (email.isEmpty()) {
            EditTextEmail.setError("Email is required");
            EditTextEmail.requestFocus();
            return;
        }
        //main has to respect a certain form
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            EditTextEmail.setError("Please enter a valid email");
            EditTextEmail.requestFocus();
            return;
        }
        ///password has to be 6 word minimum
        if (password.length() < 6) {
            EditTextPassword.setError("You need to put in a longer password, 6 character minimum");
            EditTextPassword.requestFocus();
            return;
        }

// it activates the loading logo
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(MainActivity.this,mainDashboard.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }
                else {
                progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    private void updateUI(Object o) {
    }


}
