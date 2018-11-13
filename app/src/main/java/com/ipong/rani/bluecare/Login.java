package com.ipong.rani.bluecare;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.sample.AuthMutation;

import javax.annotation.Nonnull;

public class Login extends AppCompatActivity {

    TextView display;
    String res1;
    //String k;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button submitKey = (Button) findViewById(R.id.btnAuth);
        final EditText keyInput = (EditText) findViewById(R.id.inputKey);
        display = (TextView) findViewById(R.id.txtTitle);



        submitKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{

                    String k = keyInput.getText().toString().trim();
                    if (k.trim().length() == 0)
                        throw new Exception();

                    authenticateUser(k);
                } catch( Exception err) {
                    display.setText("Please enter Key");
                }

            }
        });

    }

    private void authenticateUser(String key){

        final String k = key;

        BlueCareApolloClient.getBlueCareApolloClient().mutate(AuthMutation.builder()
                ._authKey(k)
                .build())
                .enqueue(new ApolloCall.Callback<AuthMutation.Data>() {
                    @Override
                    public void onResponse(@Nonnull Response<AuthMutation.Data> response) {
                        //res1 = response.data().authUser().token().toString();

                        try{

                            res1 = response.data().authUser().token().toString();

                            if (res1.trim().length() == 0 || res1 == null) {
                                throw new Exception();
                            } else {

                                final String membership = response.data().authUser().membership().toString();

                                SharedPreferences pref = getApplicationContext().getSharedPreferences("ACTIVE_USER", MODE_PRIVATE);
                                SharedPreferences.Editor editor = pref.edit();

                                editor.putString("authToken", res1);
                                editor.putString("authKey", k);
                                editor.putString("membership", membership);
                                editor.commit();

                                if(membership.equals("Staff")){
                                    Intent i = new Intent(Login.this, StaffActivity.class);
                                    startActivity(i);
                                } else if(membership.equals("Member")){
                                    Intent i = new Intent(Login.this, MainActivity.class);
                                    startActivity(i);
                                }


                                Login.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        display.setText(membership);
                                    }
                                });

                               // Intent i = new Intent(Login.this, MainActivity.class);
                               // startActivity(i);

                            }

                        } catch( Exception err) {
                            Login.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    display.setText("Invalid Key");
                                }
                            });
                        }


                    }

                    @Override
                    public void onFailure(@Nonnull ApolloException e) {
                        Log.d("onResponse", e.toString());
                    }
                });

    }
}
