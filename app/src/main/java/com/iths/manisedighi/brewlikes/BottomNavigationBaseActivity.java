package com.iths.manisedighi.brewlikes;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

/**
 * Created by emmapersson on 2017-11-22.
 */

public class BottomNavigationBaseActivity extends AppCompatActivity {

    private BottomNavigationViewEx bottomNavigationViewEx;
    private Context context = BottomNavigationBaseActivity.this;

    /**
     * A method that sets up the bottom navigation
     */
    protected void setupBottomNavigation(){
        bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        manipulateBottomNavigation();
        activateBottomNavigation();
    }

    /**
     * A method that when called manipulates some of
     * the settings for the bottom navigation
     */
    public void manipulateBottomNavigation(){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
    }

    /**
     * A method that handles clicks on the different icons in the navigation view
     */
    private void activateBottomNavigation(){
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()){
                    case R.id.ic_home:
                        item.setChecked(true);
                        Intent mainIntent = new Intent(context, MainActivity.class);
                        context.startActivity(mainIntent);
                        break;
                    case R.id.ic_category:
                        item.setChecked(true);
//                        Intent categoriesIntent = new Intent(context, CategoriesActivity.class);
//                        context.startActivity(categoriesIntent);
                        break;
                    case R.id.ic_toplist:
                        item.setChecked(true);
//                        Intent topListIntent = new Intent(context, TopListActivity.class);
//                        context.startActivity(topListIntent);
                        break;
                    case R.id.ic_mapview:
                        item.setChecked(true);
//                        Intent mapViewIntent = new Intent(context, MapviewActivity.class);
//                        context.startActivity(mapViewIntent);
                        break;
                }
                return false;
            }
        });
    }
}
