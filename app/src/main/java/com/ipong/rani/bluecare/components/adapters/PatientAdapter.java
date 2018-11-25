package com.ipong.rani.bluecare.components.adapters;

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

import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.components.objects.Patient;
import com.squareup.picasso.Picasso;

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

        TextView pStatus = (TextView) listItem.findViewById(R.id.txtViewAge);
        pStatus.setText(currentItem.getpStatus());

        ImageView dImg = (ImageView) listItem.findViewById((R.id.imgMember));
        Picasso.get().load("https://images.pexels.com/photos/1282169/pexels-photo-1282169.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940").into(dImg);

        //TextView pStatus = (TextView) listItem.findViewById(R.id.txtViewConditonStatus);
        //pStatus.setText(currentItem.getpConditionStatus());

        return listItem;
    }

}
