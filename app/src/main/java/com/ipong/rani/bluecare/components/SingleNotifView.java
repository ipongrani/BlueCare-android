package com.ipong.rani.bluecare.components;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.components.objects.SingleNotif;

public class SingleNotifView extends AppCompatActivity {


    private LinearLayout SingleNotifView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_notif_view);
        SingleNotifView = (LinearLayout) findViewById(R.id.SingleNotifLayout);

        Bundle bundle = getIntent().getExtras();

        String topic = bundle.getString("topic");
        String date = bundle.getString("date").toString();
        String report = bundle.getString("report");

        final TextView txtVwD = new TextView(this);
        txtVwD.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        txtVwD.setGravity(Gravity.LEFT);
        txtVwD.setPadding(10,10,10,10);
        txtVwD.setText(date);
        txtVwD.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 18);
        SingleNotifView.addView(txtVwD);

        final TextView txtVwT = new TextView(this);
        txtVwT.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        txtVwT.setGravity(Gravity.LEFT);
        txtVwT.setPadding(10,10,10,10);
        txtVwT.setText(topic);
        txtVwT.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 22);
        SingleNotifView.addView(txtVwT);

        final TextView txtVwR = new TextView(this);
        txtVwR.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300));
        txtVwR.setGravity(Gravity.LEFT);
        txtVwR.setPadding(20,20,20,20);
        txtVwR.setText(report);
        txtVwR.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        SingleNotifView.addView(txtVwR);





        Log.d("bundle single notif", topic + " " + date + " " + report);

    }
}
