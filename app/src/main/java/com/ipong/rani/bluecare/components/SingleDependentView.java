package com.ipong.rani.bluecare.components;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipong.rani.bluecare.MainActivity;
import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.components.adapters.NotificationsAdapter;
import com.ipong.rani.bluecare.components.objects.NotificationData;
import com.ipong.rani.bluecare.components.objects.Patient;
import com.ipong.rani.bluecare.components.objects.PatientCondition;
import com.ipong.rani.bluecare.components.adapters.PatientConditionAdapter;
import com.ipong.rani.bluecare.components.objects.SingleNotif;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleDependentView extends AppCompatActivity {


    private LinearLayout mainLayout;
    private NotificationsAdapter thisAdapter;
    private final ArrayList<SingleNotif> updateDataList = new ArrayList<>();
    private String membership;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dependent_view);
        //pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        //membership = pref.getString("membership", null);
        mainLayout = (LinearLayout) findViewById(R.id.personalInfoLayout);




        Bundle bundle = getIntent().getExtras();
        String pR = bundle.getString("patientRecord");
        String reports = bundle.getString("reports");



        try {
            JSONObject record = new JSONObject(pR);
            JSONObject medicalBio = (JSONObject) record.getJSONArray("medicalBio").get(0);
            JSONArray rep = new JSONArray(reports);
            Log.d("dependent jsonArray",rep.toString());


            Log.d("record keys", record.names().toString());
            Log.d("bio keys", medicalBio.names().toString());

            String[] pIList = new String[]{"firstName", "lastName", "age", "birthDate", "status"};
            final TextView profileTitle = new TextView(this);
            profileTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
            profileTitle.setGravity(Gravity.CENTER_VERTICAL);
            profileTitle.setPadding(20,20,0,0);
            profileTitle.setText("Personal Info:");
            profileTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            mainLayout.addView(profileTitle);

            for(int x = 0; x != pIList.length; x++){
                final TextView rowText = new TextView(this);
                rowText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
                rowText.setGravity(Gravity.CENTER_VERTICAL);
                rowText.setPadding(50,15,0,0);
                rowText.setText(pIList[x].toString() + ": " + record.getString(pIList[x].toString()));
                mainLayout.addView(rowText);
            }


            String[] mDList = new String[]{"medicalId", "bloodType", "bloodPressure", "allergies", "diet", "pill", "timePill"};
            final TextView mdTitle = new TextView(this);
            mdTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
            mdTitle.setGravity(Gravity.CENTER_VERTICAL);
            mdTitle.setPadding(20,20,0,0);
            mdTitle.setText("Medical Bio:");
            mdTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            mainLayout.addView(mdTitle);

            for(int x = 0; x != mDList.length; x++){
                final TextView rowText = new TextView(this);
                rowText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
                rowText.setGravity(Gravity.CENTER_VERTICAL);
                rowText.setPadding(50,15,0,0);
                rowText.setText(mDList[x].toString() + ": " + medicalBio.getString(mDList[x].toString()));
                mainLayout.addView(rowText);
            }





            ListView DynamicListView = (ListView) findViewById(R.id.updateList);


            for(int x= 0; rep.length() != x; x++) {

                JSONObject notif = rep.getJSONObject(x);

                Log.d("tpc", notif.getString("topic"));

                SingleNotif ntf = new SingleNotif(notif.getString("topic"), notif.getString("patientReport"), notif.getString("datePublished"));
                updateDataList.add(ntf);
            }

            Log.d("ntf list", updateDataList.toString());

            thisAdapter = new NotificationsAdapter(this, updateDataList);

            DynamicListView.setAdapter(thisAdapter);

            DynamicListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                        SingleNotif currentNotif = updateDataList.get(position);
                        Intent thisIntent = new Intent(SingleDependentView.this, SingleNotifView.class);
                        thisIntent.putExtra("topic", currentNotif.getTopic());
                        thisIntent.putExtra("date", currentNotif.getDate());
                        thisIntent.putExtra("report", currentNotif.getPatientReport());
                        startActivity(thisIntent);

                }

            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


}
