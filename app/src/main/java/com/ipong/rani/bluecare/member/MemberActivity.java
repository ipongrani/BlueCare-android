package com.ipong.rani.bluecare.member;




import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.AuthMutation;
import com.apollographql.apollo.sample.GetPatientsQuery;
import com.apollographql.apollo.sample.LogoutKeyMutation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ipong.rani.bluecare.AboutUs;
import com.ipong.rani.bluecare.ContactUs;
import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.Splash;
import com.ipong.rani.bluecare.apolloClient.BlueCareApolloClient;
import com.ipong.rani.bluecare.components.objects.Patient;
import com.ipong.rani.bluecare.components.adapters.PatientAdapter;
import com.ipong.rani.bluecare.PatientRecord;


public class MemberActivity extends AppCompatActivity {


    private TextView display;
    private String res1;
    private ListView thisListView;
    private Intent thisIntent;
    private PatientAdapter thisAdapter;
    private final ArrayList<Patient> patientList = new ArrayList<>();
    private String[] nameList;
    private Menu slideMenu;
    private String aK;
    private SharedPreferences pref;

    //Widget
    private NavigationView navigationView;
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
        setContentView(R.layout.activity_member);

        pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        aK = pref.getString("authKey", null);

        Log.d("ak here", aK);


        thisListView = (ListView) findViewById(R.id.patient_list_main);
        navigationView = (NavigationView) findViewById(R.id.navigationMenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);



        final TextView textNotify = (TextView) findViewById(R.id.textViewToken);


        getPatients(aK);
        thisAdapter = new PatientAdapter(this, patientList);

        /* Action bar*/
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");


        actionBarDrawerToggle = new ActionBarDrawerToggle(MemberActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //View navView = navigationView.inflateHeaderView( R.layout.navigation_header );


        slideMenu = navigationView.getMenu();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                UserMenuSelector(item);

                return false;
            }
        });



        /* END */


        /*Notification button*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  //N is version of Android
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }


        FirebaseMessaging.getInstance().subscribeToTopic("relative");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                            //textNotify.setText( "Token: " + token);
                            // Log.d("Token",token );
                        } else {
//                            textNotify.setText( task.getException().getMessage() );
                            Log.d("Fail", "Token is not generated");
                        }
                    }
                });



        /*End*/

/*
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sharedPreferenceFile = new File("/data/data/" + getPackageName()+ "/shared_prefs/ACTIVE_USER.xml");

                logoutKey(aK);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().apply();
                sharedPreferenceFile.delete();
                Intent i = new Intent(MemberActivity.this, Splash.class);
                startActivity(i);

            }
        });

*/



    }
        /*Menu bar*/


        @Override
        public boolean onCreateOptionsMenu(Menu menu){
            // Inflate the menu; this adds items to the action bar if it is present.
            //getMenuInflater().inflate(R.menu.navigation_menu, menu);

            super.onCreateOptionsMenu(menu);

            //String[] list = new String[]{"add","new","menu","here"};
            menu.add("Log Out");
            menu.add("Log Out");
            menu.add("Log Out");
            menu.add("Log Out");


            return true;
        }



        @Override
        public boolean onOptionsItemSelected (MenuItem item){

            if (actionBarDrawerToggle.onOptionsItemSelected(item)) {

                return true;
            } else {

                topMenuSelector(item);
            }

            return super.onOptionsItemSelected(item);

        }




        private void topMenuSelector (MenuItem item){
            Log.d("top item menu: ", item.getTitle().toString());

            String selectTop = item.getTitle().toString();

            switch (selectTop) {
                case "Log Out":
                    File sharedPreferenceFile = new File("/data/data/" + getPackageName()+ "/shared_prefs/ACTIVE_USER.xml");

                    logoutKey(aK);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear().apply();
                    sharedPreferenceFile.delete();
                    Intent i = new Intent(MemberActivity.this, Splash.class);
                    startActivity(i);
                break;
            }

        }



        private void UserMenuSelector (MenuItem item){

            int selected = item.getItemId();
            String title = item.getTitle().toString();

            Log.d("selected: ", title);

            if(title.equals("patientMenu")) {
                Log.d("selected title: ", title);
            } else {
                Patient currentPatient = patientList.get(selected);
                Log.d("selected profile: ", currentPatient.getpName() + " " + currentPatient.getpStatus());
                Log.d("object patient:", currentPatient.getPatientRecord().toString());

                thisIntent = new Intent(MemberActivity.this, SingleDependentView.class);
                thisIntent.putExtra("patientName", currentPatient.getpName());
                thisIntent.putExtra("patientConditions", currentPatient.getCondition().toString());
                startActivity(thisIntent);
            }


        }

        /*END*/


        private ArrayList<Patient> getPatients (String key){

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
                            String pStatus;
                            String condition;

                            nameList = new String[res.size()];

                            for (int i = 0; i != res.size(); i++) {
                                Object obj = (Object) res.get(i);
                                Gson gson = new Gson();
                                String jsonString = gson.toJson(obj);

                                Log.d("jsonstring here:", jsonString);


                                try {

                                    JSONObject patientRecords = new JSONObject(jsonString);



                                    pName = patientRecords.getString("firstName");
                                    pStatus = patientRecords.getString("status");
                                    condition = patientRecords.getString("conditions");

                                    nameList[i] = pName;

                                    //Log.d("namelist in loop: ", nameList[i].toString());
                                    //JSONArray condition = patientRecords.getJSONArray("conditions");

                                    Patient x = new Patient(pName, pStatus, condition, patientRecords);
                                    x.setCondition(condition);
                                    patientList.add(x);


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            MemberActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    // patientList.add(new Patient(name, cName, cStatus));
                                    //Log.d("nameList: ", nameList.toString());

                                    for (int j = 0; j < nameList.length; j++) {
                                        slideMenu.add(0, j, 0,nameList[j]);

                                    }


                                    thisListView.setAdapter(thisAdapter);

                                    thisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                            Patient currentPatient = patientList.get(position);

                                            //Log.d("clicker: ", patientList.get(position).getCondition().toString());


                                            thisIntent = new Intent(MemberActivity.this, SingleDependentView.class);
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


        private void authenticateUser () {

            BlueCareApolloClient.getBlueCareApolloClient().mutate(AuthMutation.builder()
                    ._authKey("Something")
                    .build())
                    .enqueue(new ApolloCall.Callback<AuthMutation.Data>() {
                        @Override
                        public void onResponse(@Nonnull Response<AuthMutation.Data> response) {
                            res1 = response.data().authUser().msg().toString();

                            MemberActivity.this.runOnUiThread(new Runnable() {
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


        private void logoutKey (String key){

            BlueCareApolloClient.getBlueCareApolloClient().mutate(LogoutKeyMutation.builder()
                    ._authKey(key)
                    .build())
                    .enqueue(new ApolloCall.Callback<LogoutKeyMutation.Data>() {
                        @Override
                        public void onResponse(@Nonnull Response<LogoutKeyMutation.Data> response) {
                            res1 = response.data().logoutKey().msg().toString();

                            Log.d("logoutKey: ", res1);

                            MemberActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //display.setText(res1);
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


