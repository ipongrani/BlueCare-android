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
import com.ipong.rani.bluecare.components.adapters.PatientConditionAdapter;
import com.ipong.rani.bluecare.components.objects.PatientCondition;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SinglePatientView extends AppCompatActivity {


    private TextView pNameLabel;
    private TextView pNameContent;
    private TextView pStatus;
    private TextView pDobLabel;
    private TextView pDobContent;
    private LinearLayout mdBio;
    private LinearLayout mainLayout;
    private JSONArray conditions;
    private ListView thisListView;
    private Button btnAddUpdate;
    private JSONObject record;
    private JSONObject medicalBio;
    private PatientConditionAdapter thisAdapter;
    private final ArrayList<PatientCondition> conditionList = new ArrayList<>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_patient_view);

        mainLayout = (LinearLayout) findViewById(R.id.personalInfoLayoutStaff);
        btnAddUpdate = (Button) findViewById(R.id.btnAddUpdate);


        Bundle bundle = getIntent().getExtras();
        String pR = bundle.getString("patientRecord");


        try {
             record = new JSONObject(pR);
             medicalBio = (JSONObject) record.getJSONArray("medicalBio").get(0);

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


            //ListView DynamicListView = new ListView(this);
            ListView DynamicListView = (ListView) findViewById(R.id.updateListStaff);
            //DynamicListView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,  150));

            for(int x= 0; x != 20; x++){
                PatientCondition p = new PatientCondition("Something", "someStatus");
                conditionList.add(p);
            }

            Log.d("cond list", conditionList.toString());

            thisAdapter = new PatientConditionAdapter(this, conditionList);

            DynamicListView.setAdapter(thisAdapter);



            btnAddUpdate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d("btn Add","clicked");

                    try {

                        Intent u = new Intent(SinglePatientView.this, AddUpdate.class);
                        u.putExtra("firstName", record.getString("firstName").toString() );
                        u.putExtra("lastName",  record.getString("lastName").toString() );
                        startActivity(u);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            });


        } catch (JSONException e) {
            e.printStackTrace();
        }

        //String pConditions = bundle.getString("patientConditions");
        //thisListView = (ListView) findViewById(R.id.condition_list);

        Log.d("pR",pR);


    }
}
