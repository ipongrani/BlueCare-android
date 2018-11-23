package com.ipong.rani.bluecare;

import android.annotation.SuppressLint;
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
    private static final String TAG = "PersonListAdapter";
    private Context mContext;
    private List<Patient> patientList = new ArrayList<>();
    int mResource;

    public PatientAdapter(@NonNull Context context, @SuppressLint("SupportAnnotationUsage") @LayoutRes ArrayList<Patient> list) {
        super(context, 0 , list);
        mContext = context;
        patientList = list;

    }

    public PatientAdapter( Context context, int resource, ArrayList<Patient> object) {
        super(context, resource, object);

        mContext = context;
        mResource = resource;

    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.patient_layout,parent,false);

//        Patient currentItem = patientList.get(position);

        String pName = getItem( position ).getpName();
        String pAge = getItem( position ).getpAge();
        String condition = getItem( position ).getCondition();

        Patient patient = new Patient( pName, pAge, condition );
        LayoutInflater inflater = LayoutInflater.from( mContext );
        convertView = inflater.inflate( mResource, parent, false );


//        String pMedicalID = getItem( position ).getMedicalID();
//        String pBloodType = getItem( position ).getBloodType();
//        String pBloodPressure = getItem( position ).getBloodPressure();
//        String pAllergies = getItem( position ).getAllergies();
//        String pDiet= getItem( position ).getDiet();
//        String pPill = getItem( position ).getPill();
//        String pTimePill = getItem( position ).getTimePill();
//        String pMedTime = getItem( position ).getMedTime();
//        String pStatus = getItem( position ).getStatus();



//        TextView name = (TextView) listItem.findViewById(R.id.txtViewName);
//        name.setText(currentItem.getpName());

        TextView tvName = (TextView) convertView.findViewById(R.id.dataPatientName);
        TextView tvAge = (TextView) convertView.findViewById(R.id.dataAge);
//        TextView tvMedicalID = (TextView) convertView.findViewById(R.id.dataMedicalID);
//        TextView tvBloodType = (TextView) convertView.findViewById(R.id.dataBloodType);
//        TextView tvBloodPressure = (TextView) convertView.findViewById(R.id.dataBloodPressure);
//        TextView tvAllergies = (TextView) convertView.findViewById(R.id.dataAllergies);
//        TextView tvDiet = (TextView) convertView.findViewById(R.id.dataDiet);
//        TextView tvPill = (TextView) convertView.findViewById(R.id.dataPill);
//        TextView tvTimePill = (TextView) convertView.findViewById(R.id.dataTimePill);
//        TextView tvMedIssue = (TextView) convertView.findViewById(R.id.dataMedIssue);
//        TextView tvStatus = (TextView) convertView.findViewById(R.id.dataStatus);

        TextView tvCondition = (TextView) convertView.findViewById(R.id.dataCondition);



//        TextView pAge = (TextView) listItem.findViewById(R.id.txtViewAge);
//        pAge.setText(currentItem.getpAge());

        //TextView pStatus = (TextView) convertView.findViewById(R.id.txtViewConditonStatus);
        //pStatus.setText(currentItem.getpConditionStatus());

        tvName.setText( pName );
        tvAge.setText( pAge );
//        tvMedicalID.setText( pMedicalID );
//        tvBloodType.setText( pBloodType );
//        tvBloodPressure.setText( pBloodPressure );
//        tvAllergies.setText( pAllergies );
//        tvDiet.setText( pDiet );
//        tvPill.setText( pPill );
//        tvTimePill.setText( pTimePill );
//        tvMedIssue.setText( pMedTime );
//        tvStatus.setText( pStatus );
        tvCondition.setText( condition );


//        return listItem;
        return convertView;
    }

}
