package com.ipong.rani.bluecare;




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
import android.widget.TextView;

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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.ipong.rani.bluecare.apolloClient.BlueCareApolloClient;
import com.ipong.rani.bluecare.components.SingleDependentView;
import com.ipong.rani.bluecare.components.SingleNotifView;
import com.ipong.rani.bluecare.components.SinglePatientView;
import com.ipong.rani.bluecare.components.adapters.NotificationsAdapter;
import com.ipong.rani.bluecare.components.objects.NotificationData;
import com.ipong.rani.bluecare.components.objects.Patient;
import com.ipong.rani.bluecare.components.adapters.PatientAdapter;
import com.ipong.rani.bluecare.components.objects.SingleNotif;


public class MainActivity extends AppCompatActivity {


    private TextView display;
    private String res1;
    private ListView thisListView;
    private Intent thisIntent;
    private PatientAdapter thisAdapter;
    private NotificationsAdapter notifAdapter;
    private final ArrayList<Patient> patientList = new ArrayList<>();
    private final ArrayList<NotificationData> notificationDataList = new ArrayList<>();
    private final ArrayList<SingleNotif> singleNotifList = new ArrayList<>();
    private String[] nameList;
    private String[] lastNameList;
    private Menu slideMenu;
    private String aK;
    private String kK;
    private String membership;
    private SharedPreferences pref;

    //Widget
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    // ** NotificationData **//
    //1. NotificationData Channel
    //2. NotificationData Builder
    //3. NotificationData Manager

    public static final String CHANNEL_ID = "NotificationData FireBase";
    private static final String CHANNEL_NAME = "NotificationData FireBase";
    private static final String CHANNEL_DESC = "Notificaiton FireBase something";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        aK = pref.getString("authToken", null);
        kK = pref.getString("authKey", null);
        membership = pref.getString("membership", null);
        thisListView = (ListView) findViewById(R.id.patient_list_main);
        navigationView = (NavigationView) findViewById(R.id.navigationMenu);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawable_layout);


        getPatients(aK);
        thisAdapter = new PatientAdapter(this, patientList);
        notifAdapter = new NotificationsAdapter(this, singleNotifList);


        /* Action bar*/
        mToolbar = (Toolbar) findViewById(R.id.main_page_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Home");


        actionBarDrawerToggle = new ActionBarDrawerToggle(MainActivity.this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
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


        /*NotificationData button*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {  //N is version of Android
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(CHANNEL_DESC);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);

        }




       // FirebaseMessaging.getInstance().subscribeToTopic("relative");

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (task.isSuccessful()) {
                            String token = task.getResult().getToken();
                        } else {
                            Log.d("Fail", "Token is not generated");
                        }
                    }
                });




    }


        /*Menu bar*/
        @Override
        public boolean onCreateOptionsMenu (Menu menu) {
            super.onCreateOptionsMenu(menu);
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
                    logoutKey(kK);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.clear().apply();
                    sharedPreferenceFile.delete();
                    Intent i = new Intent(MainActivity.this, Splash.class);
                    startActivity(i);
                break;
            }

        }



        private void UserMenuSelector (MenuItem item){

            int selected = item.getItemId();
            String title = item.getTitle().toString();


            if(title.equals("Patient List")) {
                Log.d("selected title: ", title);
            } else {
                Patient currentPatient = patientList.get(selected);
                NotificationData currentNotificationData = notificationDataList.get(selected);

                JSONObject patientRecord = currentPatient.getPatientRecord();
                JSONArray n = currentNotificationData.getNotification();

                if(membership.equals("Staff")){
                    thisIntent = new Intent(MainActivity.this, SinglePatientView.class);
                    thisIntent.putExtra("patientRecord", patientRecord.toString());
                    thisIntent.putExtra("reports", n.toString());
                    startActivity(thisIntent);
                } else {
                    thisIntent = new Intent(MainActivity.this, SingleDependentView.class);
                    thisIntent.putExtra("patientRecord", patientRecord.toString());
                    thisIntent.putExtra("reports", n.toString());
                    startActivity(thisIntent);
                }


            }


        }


        /*END*/


        private ArrayList<Patient> getPatients (String key){

            BlueCareApolloClient.getBlueCareApolloClient().query(GetPatientsQuery.builder()
                    ._authToken(key)
                    .build())
                    .enqueue(new ApolloCall.Callback<GetPatientsQuery.Data>() {

                        @Override
                        public void onResponse(@Nonnull com.apollographql.apollo.api.Response<GetPatientsQuery.Data> response) {

                            res1 = response.data().getPatients().msg().toString();
                            List res = response.data().getPatients().patientRecords();
                            String pName;
                            String lName;

                            nameList = new String[res.size()];
                            lastNameList = new String[res.size()];

                            for (int i = 0; i != res.size(); i++) {

                                Object obj = (Object) res.get(i);
                                Gson gson = new Gson();
                                String jsonString = gson.toJson(obj);

                                try {

                                    JSONObject patientRecord = new JSONObject(jsonString);

                                    Log.d("patientRecord in main", patientRecord.toString());

                                    pName = patientRecord.getString("firstName");
                                    lName = patientRecord.getString("lastName");

                                    nameList[i] = pName;
                                    lastNameList[i] = lName;

                                    NotificationData n = new NotificationData(new JSONArray(patientRecord.getString("reports")));
                                    Patient x = new Patient(patientRecord);

                                    patientList.add(x);
                                    notificationDataList.add(n);

                                    Log.d("reports in main", patientRecord.getString("reports").toString());


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int j = 0; j < notificationDataList.size(); j++) {
                                        JSONArray ntf = notificationDataList.get(j).getNotification();
                                        try {
                                            for (int x = 0; x < ntf.length(); x++ ) {

                                            SingleNotif sn = new SingleNotif(ntf.getJSONObject(x).getString("topic"),
                                                                             ntf.getJSONObject(x).getString("patientReport"),
                                                                             ntf.getJSONObject(x).getString("datePublished"));
                                            singleNotifList.add(sn);

                                            }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }



                                    for (int j = 0; j < nameList.length; j++) {

                                        slideMenu.add(0, j, 0,nameList[j] + " " + lastNameList[j]);

                                        String tpc = nameList[j].replaceAll("\\s+","") + lastNameList[j];
                                        FirebaseMessaging.getInstance().subscribeToTopic(tpc);

                                    }


                                    thisListView.setAdapter(notifAdapter);


                                    thisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                                            if(membership.equals("Staff")) {
                                                SingleNotif currentNotif = singleNotifList.get(position);
                                                thisIntent = new Intent(MainActivity.this, SingleNotifView.class);
                                                thisIntent.putExtra("topic", currentNotif.getTopic());
                                                thisIntent.putExtra("date", currentNotif.getDate());
                                                thisIntent.putExtra("report", currentNotif.getPatientReport());
                                                startActivity(thisIntent);
                                            } else {
                                                SingleNotif currentNotif = singleNotifList.get(position);
                                                thisIntent = new Intent(MainActivity.this, SingleNotifView.class);
                                                thisIntent.putExtra("topic", currentNotif.getTopic());
                                                thisIntent.putExtra("date", currentNotif.getDate());
                                                thisIntent.putExtra("report", currentNotif.getPatientReport());
                                                startActivity(thisIntent);
                                            }


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


        private void logoutKey (String key){

            BlueCareApolloClient.getBlueCareApolloClient().mutate(LogoutKeyMutation.builder()
                    ._authKey(key)
                    .build())
                    .enqueue(new ApolloCall.Callback<LogoutKeyMutation.Data>() {
                        @Override
                        public void onResponse(@Nonnull Response<LogoutKeyMutation.Data> response) {
                            res1 = response.data().logoutKey().msg().toString();

                            Log.d("logoutKey: ", res1);

                            MainActivity.this.runOnUiThread(new Runnable() {
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



