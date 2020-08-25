package com.example.progettooop.ui;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.progettooop.R;
import com.example.progettooop.ui.advertisement.AddAdvFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainDashboard extends AppCompatActivity implements View.OnClickListener, BottomNavigationView.OnNavigationItemSelectedListener  {
    BottomNavigationView navView;
    boolean b = true;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavController navController;
        navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home,
                R.id.navigation_dashboard,
                R.id.navigation_addadv,
                R.id.navigation_search,
                R.id.navigation_user)
                .build();
        navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(this);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
    }


    @Override
    public void onClick(View view) {
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        navView.postDelayed(() -> {
            int itemId = item.getItemId();
            if (itemId == R.id.navigation_addadv) {
                startActivity(new Intent(this, AddAdvFragment.class));
            }
            finish();
        }, 300);
        return true;
    }

}

