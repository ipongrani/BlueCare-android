package com.ipong.rani.bluecare;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientAdapter extends ArrayAdapter<Patient> {

    private Context mContext;
    private List<Patient> patientList = new ArrayList<>();

    public PatientAdapter(@NonNull Context context, @LayoutRes ArrayList<Patient> list) {
        super(context, 0 , list);
        mContext = context;
        patientList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.patient_layout,parent,false);

        Patient currentItem = patientList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.txtViewName);
        name.setText(currentItem.getpName());

        TextView pAge = (TextView) listItem.findViewById(R.id.txtViewAge);
        pAge.setText(currentItem.getpAge());

        //TextView pStatus = (TextView) listItem.findViewById(R.id.txtViewConditonStatus);
        //pStatus.setText(currentItem.getpConditionStatus());

        return listItem;
    }

}
