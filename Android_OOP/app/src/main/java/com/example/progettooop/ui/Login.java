package com.example.progettooop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.progettooop.R;

public class Login extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_login);

    findViewById(R.id.sign_in_button).setOnClickListener(this);
}

    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.sign_in_button:
                startActivity(new Intent(this,SignUp.class));
                break;
        }

    }
}