package com.ipong.rani.bluecare;




import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.AuthMutation;
import com.apollographql.apollo.sample.GetAssignmentQuery;
import com.apollographql.apollo.sample.GetPatientsQuery;
import com.apollographql.apollo.sample.LogoutKeyMutation;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static android.widget.Toast.LENGTH_SHORT;


public class MainActivity extends AppCompatActivity {


    private TextView display;
    private String res1;
    ListView thisListView;
    Intent thisIntent;
    PatientAdapter thisAdapter;
    final ArrayList<Patient> patientList = new ArrayList<>();

    //Widget
    private NavigationView navigationView;
//    private CoordinatorLayout coordinatorLayout;
//    private RecyclerView postList;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        final String aK = pref.getString("authKey",null);

        display = (TextView) findViewById(R.id.txtDisplay);
        Button logout = (Button) findViewById(R.id.btnLogout);
        thisListView = (ListView) findViewById(R.id.patient_list);

        navigationView = (NavigationView) findViewById( R.id.navigationMenu );
        drawerLayout = (DrawerLayout) findViewById( R.id.drawable_layout );


        thisListView = (ListView) findViewById(R.id.patient_list);

        getPatients(aK);
        thisAdapter = new PatientAdapter(this,patientList);

        /* Action bar*/
        mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar );
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle( "Home" );
        /* END */

        actionBarDrawerToggle = new ActionBarDrawerToggle( MainActivity.this, drawerLayout, R.string
                .drawer_open, R.string.drawer_close );

        drawerLayout.addDrawerListener( actionBarDrawerToggle );
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled( true );

        View navView = navigationView.inflateHeaderView( R.layout.navigation_header );

        navigationView.setNavigationItemSelectedListener( new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector( item );

                return false;
            }
        } );






        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sharedPreferenceFile = new File("/data/data/" + getPackageName()+ "/shared_prefs/ACTIVE_USER.xml");

                logoutKey(aK);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().apply();
                sharedPreferenceFile.delete();
                Intent i = new Intent(MainActivity.this, Splash.class);
                startActivity(i);

            }
        });



    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(actionBarDrawerToggle.onOptionsItemSelected( item )){
            return true;
        }
        return super.onOptionsItemSelected( item );
    }

    private void UserMenuSelector(MenuItem item) {
        switch (item.getItemId()){

            case R.id.nav_home:
                Toast.makeText( this, "Patient Records", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.profile:
                Toast.makeText( this, "Profile Activity", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.facility:
                Toast.makeText( this, "Facility Activity", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.contactUs:
                Toast.makeText( this, "Contact Us Activity", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.aboutUs:
                Toast.makeText( this, "About Us Activity", Toast.LENGTH_SHORT ).show();
                break;

            case R.id.btnLogout:
//                logoutKey(aK);
                Toast.makeText( this, "Log out", Toast.LENGTH_SHORT ).show();

                break;

        }
    }


    private ArrayList<Patient> getPatients(String key) {

        //final ArrayList<Patient> pl = new ArrayList<>();

        BlueCareApolloClient.getBlueCareApolloClient().query(GetPatientsQuery.builder()
                ._authKey(key)
                .build())
                .enqueue(new ApolloCall.Callback<GetPatientsQuery.Data>() {

                  @Override
                  public void onResponse(@Nonnull com.apollographql.apollo.api.Response<GetPatientsQuery.Data> response) {

                      res1 = response.data().getPatients().msg().toString();
                      List res = response.data().getPatients().patientRecords();
                      String name;
                      String age;


                      for(int i = 0; i != res.size(); i++) {
                          Object obj = (Object) res.get(i);
                          Gson gson = new Gson();
                          String jsonString = gson.toJson(obj);

                          try {
                              JSONObject patientRecords = new JSONObject(jsonString);
                               name = patientRecords.getString("name");
                               age = patientRecords.getString("age");

                              JSONArray cond = patientRecords.getJSONArray("conditions");
                              Patient x = new Patient(name, age);

                              x.setCondition(cond);
                              patientList.add(x);


                          } catch (JSONException e) {
                              e.printStackTrace();
                          }
                      }

                      MainActivity.this.runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              // patientList.add(new Patient(name, cName, cStatus));
                              thisListView.setAdapter(thisAdapter);

                              thisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                      Patient currentPatient = patientList.get(position);

                                      //Log.d("clicker: ", patientList.get(position).getCondition().toString());


                                      thisIntent = new Intent(MainActivity.this, SinglePatientView.class);
                                      thisIntent.putExtra("patientName", currentPatient.getpName());
                                      thisIntent.putExtra("patientConditions", currentPatient.getCondition().toString());
                                      startActivity(thisIntent);

                                  }

                              });
                          }
                      });

                  }

                  @Override
                  public void onFailure(@Nonnull ApolloException e) {
                      Log.d("onResponse", e.toString());
                  }
          });

        return patientList;
    }


    private void authenticateUser(){

        BlueCareApolloClient.getBlueCareApolloClient().mutate(AuthMutation.builder()
                ._authKey("Something")
                .build())
                .enqueue(new ApolloCall.Callback<AuthMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AuthMutation.Data> response) {
                        res1 = response.data().authUser().msg().toString();

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display.setText(res1);
                            }
                        });
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });

    }


    private void logoutKey(String key){

        BlueCareApolloClient.getBlueCareApolloClient().mutate(LogoutKeyMutation.builder()
                ._authKey(key)
                .build())
                .enqueue(new ApolloCall.Callback<LogoutKeyMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<LogoutKeyMutation.Data> response) {
                        res1 = response.data().logoutKey().msg().toString();

                        Log.d("logoutKey: ",res1);

                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                display.setText(res1);
                            }
                        });
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });

    }

}



