package com.example.progettooop.ui.advertisement;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.progettooop.R;
import com.example.progettooop.ui.recensioni.RecensioniActivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class InfoProduct extends AppCompatActivity {

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.infoproduct_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                InfoProduct.this.finish();
                break;
            default:
                break;

        }
        return true;
    }
}
