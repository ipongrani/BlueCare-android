package com.ipong.rani.bluecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.LogoutKeyMutation;
import com.ipong.rani.bluecare.apolloClient.BlueCareApolloClient;
import com.ipong.rani.bluecare.components.objects.Patient;

import java.io.File;
import java.util.ArrayList;

import javax.annotation.Nonnull;

public class PatientRecord extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView navigationView;

    private static final String TAG =  "PatientRecord";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_record );

        Log.d(TAG, "onCreate: Started.");
        ListView mListView = (ListView) findViewById(R.id.patient_list);




        navigationView = (NavigationView) findViewById( R.id.navigationMenu3 );
        drawerLayout = (DrawerLayout) findViewById( R.id.drawable_layout3 );


        actionBarDrawerToggle = new ActionBarDrawerToggle( PatientRecord.this, drawerLayout, R.string.drawer_open, R.string.drawer_close );

        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
//        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

       // View navView = navigationView.inflateHeaderView( R.layout.navigation_header );

        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector( item );

                return false;
            }
        } );

        /* END */
/*
        //Create the patient Record sample
        Patient john = new Patient( "John", "101", "condition" );
        Patient john1 = new Patient( "John1", "102", "sick1" );
        Patient john2 = new Patient( "John2", "103", "sick2" );
        Patient john3 = new Patient( "John3", "104", "sick3" );
        Patient john4 = new Patient( "John4", "105", "sick4" );
        Patient john5 = new Patient( "John5", "106", "sick5" );
        Patient john6 = new Patient( "John6", "107", "sic6" );
        Patient john7 = new Patient( "John7", "108", "sick7" );
        Patient john8 = new Patient( "John8", "109", "sick8" );
        Patient john9 = new Patient( "Joh9", "110", "sick9" );
        Patient john10 = new Patient( "John10", "111", "sick11" );
        Patient john11 = new Patient( "John11", "112", "sick12" );
        Patient john12 = new Patient( "John101", "113", "sick13" );
        Patient john13 = new Patient( "John13", "114", "sick14" );

        //Add patient record
        ArrayList<Patient> patientsList = new ArrayList<>();

        patientsList.add( john );
        patientsList.add( john1 );
        patientsList.add( john2 );
        patientsList.add( john3 );
        patientsList.add( john4 );
        patientsList.add( john5 );
        patientsList.add( john6 );
        patientsList.add( john7 );
        patientsList.add( john8 );
        patientsList.add( john9 );
        patientsList.add( john10 );
        patientsList.add( john11 );
        patientsList.add( john12 );
        patientsList.add( john13);
*/
        //PatientAdapter adapter = new PatientAdapter( this, R.layout.adapter_view_layout_patient,patientsList);
        //mListView.setAdapter(adapter);

    }


    /*Menu bar*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected( item )){
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void UserMenuSelector(MenuItem item) {
        int selectedId = item.getItemId();
        RelativeLayout mainLayout = (RelativeLayout) findViewById( R.id.menuRight );
        switch (selectedId){

            case R.id.nav_home:
                Toast.makeText( this, "Patient Records", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(PatientRecord.this, PatientRecord.class));
                break;

            case R.id.contactUs:
                Toast.makeText( PatientRecord.this, "Contact Us Activity", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(PatientRecord.this, AboutUs.class));

                break;

            case R.id.aboutUs:
                Toast.makeText( this, "About Us Activity", Toast.LENGTH_SHORT ).show();
                startActivity(new Intent(PatientRecord.this, AboutUs.class));

                break;

            case R.id.logOut:
                Toast.makeText( this, "Log out", Toast.LENGTH_SHORT ).show();
                final SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
                final String aK = pref.getString("authKey",null);

                File sharedPreferenceFile = new File("/data/data/" + getPackageName()+ "/shared_prefs/ACTIVE_USER.xml");

                logoutKey(aK);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().apply();
                sharedPreferenceFile.delete();
                Intent i = new Intent(PatientRecord.this, Splash.class);
                startActivity(i);

                break;

        }
    }

    /*END*/


    private void logoutKey(String key){

        BlueCareApolloClient.getBlueCareApolloClient().mutate(LogoutKeyMutation.builder()
                ._authKey(key)
                .build())
                .enqueue(new ApolloCall.Callback<LogoutKeyMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<LogoutKeyMutation.Data> response) {
                      String res1 = response.data().logoutKey().msg().toString();

                        Log.d("logoutKey: ",res1);
                        /*
                        MemberActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //display.setText(res1);
                            }
                        });
                        */
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });

    }

}
