package com.ipong.rani.bluecare;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.ipong.rani.bluecare.member.MemberActivity;
import com.ipong.rani.bluecare.staff.StaffActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        SharedPreferences sharedpreferences = getSharedPreferences("ACTIVE_USER", Context.MODE_PRIVATE);

        //File f = new File("/data/data/BlueCare/shared_prefs/Name_of_your_preference.xml");

        if (sharedpreferences.contains("authToken")) {
            Log.d("TAG", "SharedPreferences Name_of_your_preference : exist");
            final String membership = sharedpreferences.getString("membership", null);

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                     //This method will be executed once the timer is over
                    if(membership.equals("Member")){
                        Intent i = new Intent(Splash.this, MemberActivity.class);
                        startActivity(i);
                        finish();
                    } else if (membership.equals("Staff")){
                        Intent i = new Intent(Splash.this, StaffActivity.class);
                        startActivity(i);
                        finish();
                    }

                }
            }, 1000);
        } else {
            Log.d("TAG", "Setup default preferences");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    Intent i = new Intent(Splash.this, Login.class);
                    startActivity(i);
                    finish();

                }
            }, 1000);
        }

        /*
        SharedPreferences prefs = getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        String userKey = prefs.getString("userKey", null);

        Log.d("userKey here:", userKey);

        if (userKey == null) {
            //String name = prefs.getString("name", "No name defined");//"No name defined" is the default value.

            Log.d("empty key!", "nothing inside");

            //int idName = prefs.getInt("idName", 0); //0 is the default value.
        }
        */

        /*
        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash.this, MemberActivity.class);
                startActivity(i);
                finish();
            }
        }, 4000);
        */
    }
}
