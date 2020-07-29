package com.example.progettooop;

import android.os.Bundle;
<<<<<<< HEAD
<<<<<<< HEAD
import android.view.View;
=======
>>>>>>> parent of aad5a5a... navigazione
=======
>>>>>>> parent of aad5a5a... navigazione

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
<<<<<<< HEAD
<<<<<<< HEAD


=======
>>>>>>> parent of aad5a5a... navigazione
=======
>>>>>>> parent of aad5a5a... navigazione

public class MainActivity extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_search)
                .build();

    }

}