package com.ipong.rani.bluecare;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PatientConditionAdapter extends ArrayAdapter<PatientCondition> {
    private Context mContext;

    private List<PatientCondition> conditionList = new ArrayList<>();

    public PatientConditionAdapter(@NonNull Context context, @LayoutRes ArrayList<PatientCondition> list) {
        super(context, 0 , list);
        mContext = context;
        conditionList = list;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;

        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.condition_layout,parent,false);

        PatientCondition currentItem = conditionList.get(position);


        TextView name = (TextView) listItem.findViewById(R.id.txtCondName);
        name.setText(currentItem.getConditonName());

        TextView status = (TextView) listItem.findViewById(R.id.txtCondStatus);
        status.setText(currentItem.getConditionStatus());


        return listItem;
    }

}
