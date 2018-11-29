package com.ipong.rani.bluecare.components;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.components.adapters.NotificationsAdapter;
import com.ipong.rani.bluecare.components.objects.NotificationData;
import com.ipong.rani.bluecare.components.objects.PatientCondition;
import com.ipong.rani.bluecare.components.adapters.PatientConditionAdapter;
import com.ipong.rani.bluecare.components.objects.SingleNotif;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SingleDependentView extends AppCompatActivity {

    private TextView pNameLabel;
    private TextView pNameContent;
    private TextView pStatus;
    private TextView pDobLabel;
    private TextView pDobContent;
    private Button btnAddUpdate;
    private LinearLayout mdBio;
    private LinearLayout mainLayout;
    private JSONArray conditions;
    private ListView thisListView;
    private NotificationsAdapter thisAdapter;
    private final ArrayList<PatientCondition> conditionList = new ArrayList<>();
    private final ArrayList<SingleNotif> updateDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_dependent_view);
        //pNameLabel = (TextView) findViewById(R.id.pNameLabel);
        //pNameContent = (TextView) findViewById(R.id.pNameContent);
        //pStatus = (TextView) findViewById(R.id.pStatus);
        //pDobLabel = (TextView) findViewById(R.id.pDobLabel);
        //pDobContent = (TextView) findViewById(R.id.pDobContent);
        //mdBio = (LinearLayout) findViewById(R.id.mdBioLayout);
        mainLayout = (LinearLayout) findViewById(R.id.personalInfoLayout);
        //btnAddUpdate = (Button) findViewById(R.id.btnAddUpdate);


        Bundle bundle = getIntent().getExtras();
        String pR = bundle.getString("patientRecord");
        String reports = bundle.getString("reports");



        try {
            JSONObject record = new JSONObject(pR);
            JSONObject medicalBio = (JSONObject) record.getJSONArray("medicalBio").get(0);
            JSONArray rep = new JSONArray(reports);
            Log.d("dependent jsonArray",rep.toString());

            //Log.d("medical bio", medicalBio.toString());
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


            /*
            final TextView notifTitle = new TextView(this);
            notifTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
            notifTitle.setGravity(Gravity.CENTER_VERTICAL);
            notifTitle.setPadding(20,20,0,0);
            notifTitle.setText("Updates:");
            notifTitle.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 24);
            mainLayout.addView(notifTitle);
            */



            //ListView DynamicListView = new ListView(this);
            ListView DynamicListView = (ListView) findViewById(R.id.updateList);
            //DynamicListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  150));

            for(int x= 0; rep.length() != x; x++) {

                JSONObject notif = rep.getJSONObject(x);

                Log.d("tpc", notif.getString("topic"));

                SingleNotif ntf = new SingleNotif(notif.getString("topic"), notif.getString("patientReport"));
                updateDataList.add(ntf);
            }

            Log.d("ntf list", updateDataList.toString());

            thisAdapter = new NotificationsAdapter(this, updateDataList);

            DynamicListView.setAdapter(thisAdapter);

            //mainLayout.addView(DynamicListView);


            //pNameLabel.setText("Name: ");
            //pNameContent.setText(record.getString("firstName") + " " + record.getString("lastName"));
            //pStatus.setText("Status: " + record.getString("status"));
            //pDobLabel.setText("D.O.B: ");
            //pDobContent.setText(record.getString("birthDate"));




            /*
            final TextView rowTextView1 = new TextView(this);
            final TextView rowTextView2 = new TextView(this);
            final TextView rowTextView3 = new TextView(this);
            final TextView rowTextView4 = new TextView(this);
            final TextView rowTextView5 = new TextView(this);
            final TextView rowTextView6 = new TextView(this);
            final TextView rowTextView7 = new TextView(this);
            */

            /*
            rowTextView.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView1.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView2.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView3.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView4.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView5.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView6.setText("Medical Id: " + medicalBio.getString("medicalId"));
            rowTextView7.setText("Medical Id: " + medicalBio.getString("medicalId"));
            */

            /*
            mdBio.addView(rowTextView);
            mdBio.addView(rowTextView1);
            mdBio.addView(rowTextView2);
            mdBio.addView(rowTextView3);
            mdBio.addView(rowTextView4);
            mdBio.addView(rowTextView5);
            mdBio.addView(rowTextView6);
            mdBio.addView(rowTextView7);
            */






            //PatientCondition x = new PatientCondition(record.getString("conditions"));
            //conditionList.add(x)

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String pConditions = bundle.getString("patientConditions");
        //thisListView = (ListView) findViewById(R.id.condition_list);

       // Log.d("pR",pR);

        //nTitle.setText(pName);
        //thisAdapter = new PatientConditionAdapter(this, conditionList);

/*
        try {
            conditions = new JSONArray(pConditions);

            for (int y = 0; y != conditions.length(); y++){
                JSONObject cond = new JSONObject(conditions.get(y).toString());
                String name = cond.getString("name");
                String status = cond.getString("status");


                PatientCondition x = new PatientCondition(name, status);
                conditionList.add(x);

                thisListView.setAdapter(thisAdapter);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

*/
    }


}
