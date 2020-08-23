package com.example.progettooop.ui.recensioni;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.example.progettooop.R;

public class RecensioniActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recensione);
        setTitle("Scrivi qui la tua recensione");

    }
}
