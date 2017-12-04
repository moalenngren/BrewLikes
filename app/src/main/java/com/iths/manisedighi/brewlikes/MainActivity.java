package com.iths.manisedighi.brewlikes;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class MainActivity extends BottomNavigationBaseActivity {

    private Context context = MainActivity.this;

    ImageView logo;
    //Error message the user gets if not having the correct version of the phone
    private static final String TAG = "MainActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupBottomNavigation();
        //Copy and paste this toolbar to every activity!
        Toolbar toolbar = findViewById(R.id.toolbarTop);
        setSupportActionBar(toolbar);
        logo = findViewById(R.id.logoImageView);
        //Hides the BrewLikes text from the upper toolbar
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }
/*
        final ImageView beerSound = this.findViewById(R.id.brewlikes_main_image);
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.open_bottle_sound);
        beerSound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.setVolume(1.0f, 1.0f);
                mp.start();
                Intent cameraIntent = new Intent(getApplicationContext(), RankingActivity.class);
                startActivity(cameraIntent);
            }
        });

    } */

    /*
        public void onBrewLikesImageClicked(View view) {
            Intent cameraIntent = new Intent(this, RankingActivity.class);
            startActivity(cameraIntent);
        }
        */

        //Test button that launches MapActivity. Remove this when the bottom nav bar is implemented!!
        public void onTestMapButtonClicked(View v){
            if(isGpsServicesAvailable()){
                initMapActivity();
            }
        }

        //Initializes MapActivity
        private void initMapActivity(){
            Intent intent = new Intent(this, MapActivity.class);
            intent.putExtra("ID", 1);
            startActivity(intent);
        }

        /**
        Method that checks if user has the correct version of Google Services.
         If true - user can use GPS, if false - user can't use GPS.
         */
        public boolean isGpsServicesAvailable(){
        Log.d(TAG, "isGpsServicesAvailable(): checking Google Services version");
        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MainActivity.this);

        if(available == ConnectionResult.SUCCESS){
            Log.d(TAG, "isGPSServicesAvailable: Google Services are OK!");
            return true;
        } else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            Log.d(TAG, "isGpsServicesAvailable(): Google Services failed, but we'll fix it!");
            Dialog d = GoogleApiAvailability.getInstance().getErrorDialog(MainActivity.this, available, ERROR_DIALOG_REQUEST);
            d.show();
        } else{
            Toast.makeText(this, "You've got the wrong version and can't use maps. Sorry!", Toast.LENGTH_SHORT).show();
        }
        return false;
}
















    /**
     * Upper toolbar with icons
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.about_and_camera_icons, menu);
        return true;
    }

    /**
     * Handles what happens when the icons in the toolbar are clicked
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.aboutIcon){
            Intent intent = new Intent(this, AboutActivity.class);
            startActivity(intent);
            return true;

        } else if(id == R.id.cameraIcon){
            Intent cameraIntent = new Intent(this, RankingActivity.class);
            startActivity(cameraIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}






