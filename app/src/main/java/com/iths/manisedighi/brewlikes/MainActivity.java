package com.iths.manisedighi.brewlikes;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity {

    private Context context = MainActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();
    }

    /**
     * A method that sets up the bottom navigation
     */
    private void setupBottomNavigation(){
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        BottomNavigationHelper.manipulateBottomNavigation(bottomNavigationViewEx);
        BottomNavigationHelper.activateBottomNavigation(context, bottomNavigationViewEx);
    }
}