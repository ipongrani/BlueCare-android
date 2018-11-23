package com.ipong.rani.bluecare;




import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.AuthMutation;
import com.apollographql.apollo.sample.GetAssignmentQuery;
import com.apollographql.apollo.sample.GetPatientsQuery;
import com.apollographql.apollo.sample.LogoutKeyMutation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.nio.channels.Channel;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import static android.widget.Toast.LENGTH_SHORT;

import com.google.firebase.iid.FirebaseInstanceId;



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


    // ** Notification **//
    //1. Notification Channel
    //2. Notification Builder
    //3. Notification Manager

    public static final String CHANNEL_ID = "Notification FireBase";
    private static final String CHANNEL_NAME = "Notification FireBase";
    private static final String CHANNEL_DESC = "Notificaiton FireBase something";
    private TextView textNotify;

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
        Button showNotify = (Button) findViewById(R.id.showNotifi);
        final TextView textNotify = (TextView) findViewById( R.id.textViewToken );


        getPatients(aK);
        thisAdapter = new PatientAdapter(this,patientList);

        /* Action bar*/
        mToolbar = (Toolbar) findViewById( R.id.main_page_toolbar );
        setSupportActionBar(mToolbar);

        getSupportActionBar().setTitle( "Home" );


        actionBarDrawerToggle = new ActionBarDrawerToggle( MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close );

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

        /* END */


        /*Notification button*/
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){  //N is version of Android
            NotificationChannel channel = new NotificationChannel( CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT );
            channel.setDescription( CHANNEL_DESC );
            NotificationManager manager = getSystemService( NotificationManager.class );
            manager.createNotificationChannel( channel );

        }


        showNotify.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayNotification();
            }
        } );

        FirebaseMessaging.getInstance().subscribeToTopic("updates");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener( new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()){
                            String token = task.getResult().getToken();
                            textNotify.setText( "Token: " + token);
                            Log.d("Token",token );
                        } else {
//                            textNotify.setText( task.getException().getMessage() );
                            Log.d( "Fail" ,"Token is not generated");
                        }
                    }
                } );

        /*End*/


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


    /* Notification publish*/
    public void displayNotification() {
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder( this, CHANNEL_ID )
                    .setSmallIcon( R.drawable.ic_notification )
                    .setContentTitle( "It's maybe working..." )
                    .setContentText("First Notification..")
                    .setPriority( NotificationCompat.PRIORITY_DEFAULT );

        NotificationManagerCompat mNotificationMgr = NotificationManagerCompat.from( this );
        mNotificationMgr.notify( 1, mBuilder.build() );
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

                startActivity(new Intent(MainActivity.this, PatientRecord.class));

                break;


            case R.id.contactUs:
                Toast.makeText( this, "Contact Us Activity", Toast.LENGTH_SHORT ).show();

                startActivity(new Intent(MainActivity.this, ContactUs.class));

                break;

            case R.id.aboutUs:
                Toast.makeText( this, "About Us Activity", Toast.LENGTH_SHORT ).show();
//                mainLayout.setBackgroundColor(Color.rgb( 255, 11, 44 ) );
                startActivity(new Intent(MainActivity.this, AboutUs.class));


            break;

            case R.id.btnLogout:
                Toast.makeText( this, "Log out", Toast.LENGTH_SHORT ).show();

                break;

        }
    }

    /*END*/



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
                      String pName;
                      String pAge;
                      String condition;

                      for(int i = 0; i != res.size(); i++) {
                          Object obj = (Object) res.get(i);
                          Gson gson = new Gson();
                          String jsonString = gson.toJson(obj);

                          try {
                              JSONObject patientRecords = new JSONObject(jsonString);
                               pName = patientRecords.getString("name");
                               pAge = patientRecords.getString("age");

                               condition = patientRecords.getString("condition");


//                              JSONArray condition = patientRecords.getJSONArray("conditions");
                              Patient x = new Patient(pName, pAge, condition);

                              x.setCondition(condition);
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



