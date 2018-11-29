package com.ipong.rani.bluecare.components;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.GetPatientsQuery;
import com.apollographql.apollo.sample.SubmitReportMutation;
import com.google.gson.Gson;
import com.ipong.rani.bluecare.MainActivity;
import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.Splash;
import com.ipong.rani.bluecare.apolloClient.BlueCareApolloClient;
import com.ipong.rani.bluecare.components.objects.Patient;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;

import javax.annotation.Nonnull;

public class AddUpdate extends AppCompatActivity {

    private Button btnSubmitReport;
    private EditText txtTopic;
    private EditText txtReport;
    private TextView updateName;
    private String aK;
    private SharedPreferences pref;
    private String tpc;
    private String rep;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_update);
        btnSubmitReport = (Button) findViewById(R.id.btnSubmitReport);
        updateName = (TextView) findViewById(R.id.updateName);
        txtTopic = (EditText) findViewById(R.id.eTxtTopic);
        txtReport = (EditText) findViewById(R.id.eTxtReport);


        pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
        aK = pref.getString("authToken", null);



        Log.d("ak here", aK);

        Bundle bundle = getIntent().getExtras();
        String firstName = bundle.getString("firstName");
        String lastName = bundle.getString("lastName");

        updateName.setText(firstName + " " + lastName);





        //rep = txtReport.getText().toString();
        btnSubmitReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    tpc = txtTopic.getText().toString();
                    rep = txtReport.getText().toString();


                    Log.d("submit", firstName + " " + lastName);
                    Log.d("ak here", aK);
                    Log.d("rep here", rep);
                    Log.d("TPC", tpc);

                    submitReport(aK, firstName, lastName, rep, tpc);


                } catch (Exception e) {
                    Log.d("err", e.toString());
                }

            }
        });


    }


    private void submitReport (String token, String pN, String lN, String pR, String pT) {

        BlueCareApolloClient.getBlueCareApolloClient().mutate(SubmitReportMutation.builder()
                ._authToken(token)
                .patientFirstName(pN)
                .patientLastName(lN)
                .patientReport(pR)
                .reportTopic(pT)
                .build())
                .enqueue(new ApolloCall.Callback<SubmitReportMutation.Data>(){
                    @Override
                    public void onResponse(@Nonnull com.apollographql.apollo.api.Response<SubmitReportMutation.Data> response) {

                        String msg = response.data().submitReport().msg().toString();

                        AddUpdate.this.runOnUiThread(() -> {

                            Toast.makeText( AddUpdate.this, msg, Toast.LENGTH_SHORT ).show();
                            new Handler().postDelayed(new Runnable() {

                                @Override
                                public void run() {

                                    Intent i = new Intent(AddUpdate.this, MainActivity.class);
                                    startActivity(i);
                                    finish();

                                }
                            }, 1000);

                        });
                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });
    }

}
