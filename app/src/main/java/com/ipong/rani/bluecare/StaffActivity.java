package com.ipong.rani.bluecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
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

public class StaffActivity extends AppCompatActivity {

    String res1;
    Intent thisIntent;
    ListView thisListView;
    PatientAdapter thisAdapter;
    final ArrayList<Patient> patientList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff);

        Button logout = (Button) findViewById(R.id.btnLogoutStaff);
        final SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        final String aK = pref.getString("authKey",null);

        thisListView = (ListView) findViewById(R.id.staff_patients_list);


        getPatients(aK);


        thisAdapter = new PatientAdapter(this,patientList);


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File sharedPreferenceFile = new File("/data/data/" + getPackageName()+ "/shared_prefs/ACTIVE_USER.xml");
                SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);

                String aK = pref.getString("authKey",null);
                logoutKey(aK);
                SharedPreferences.Editor editor = pref.edit();
                editor.clear().apply();
                sharedPreferenceFile.delete();
                Intent i = new Intent(StaffActivity.this, Splash.class);
                startActivity(i);
            }
        });

    }



    private void logoutKey(String key) {

        BlueCareApolloClient.getBlueCareApolloClient().mutate(LogoutKeyMutation.builder()
                ._authKey(key)
                .build())
                .enqueue(new ApolloCall.Callback<LogoutKeyMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<LogoutKeyMutation.Data> response) {
                        res1 = response.data().logoutKey().msg().toString();

                        Log.d("logoutKey: ",res1);

                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });

    }


    private ArrayList<Patient> getPatients(String key) {

        //final ArrayList<Patient> pl = new ArrayList<>();

        BlueCareApolloClient.getBlueCareApolloClient().query(GetPatientsQuery.builder()
                ._authKey(key)
                .build())
                .enqueue(new ApolloCall.Callback<GetPatientsQuery.Data>() {

                    @Override
                    public void onResponse(@Nonnull com.apollographql.apollo.api.Response<GetPatientsQuery.Data> response) {

                        res1 = response.data().getPatients().toString();
                        List res = response.data().getPatients().patientRecords();
                        String pName;
                        String pAge;
//                        JSONArray condition;
                        String condition;





                        Log.d("staff res: ", res1);



                        for(int i = 0; i != res.size(); i++) {
                            Object obj = (Object) res.get(i);
                            Gson gson = new Gson();
                            String jsonString = gson.toJson(obj);

                            try {
                                JSONObject patientRecords = new JSONObject(jsonString);
                                pName = patientRecords.getString("name");
                                pAge = patientRecords.getString("age");
                                condition = patientRecords.getString("condition");

//                                JSONArray cond = patientRecords.getJSONArray("conditions");
                                Patient x = new Patient(pName, pAge, condition);

                                //x.setCondition(cond);
                                patientList.add(x);


                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }



                        StaffActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // patientList.add(new Patient(name, cName, cStatus));
                                thisListView.setAdapter(thisAdapter);


                                thisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                                        Patient currentPatient = patientList.get(position);

                                        //Log.d("clicker: ", patientList.get(position).getCondition().toString());


                                        //thisIntent = new Intent(StaffActivity.this, SinglePatientView.class);
                                        //thisIntent.putExtra("patientName", currentPatient.getpName());
                                        //thisIntent.putExtra("patientConditions", currentPatient.getCondition().toString());
                                        //startActivity(thisIntent);

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


}
