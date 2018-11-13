package com.ipong.rani.bluecare;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SinglePatientView extends AppCompatActivity {

    TextView nTitle;
    JSONArray conditions;
    ListView thisListView;
    PatientConditionAdapter thisAdapter;
    final ArrayList<PatientCondition> conditionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_patient_view);
        nTitle = (TextView) findViewById(R.id.nameTitle);

        Bundle bundle = getIntent().getExtras();
        String pName = bundle.getString("patientName");
        String pConditions = bundle.getString("patientConditions");
        thisListView = (ListView) findViewById(R.id.condition_list);

        nTitle.setText(pName);

        thisAdapter = new PatientConditionAdapter(this, conditionList);


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


    }


}
