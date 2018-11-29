package com.ipong.rani.bluecare.components.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ipong.rani.bluecare.R;
import com.ipong.rani.bluecare.components.objects.NotificationData;
import com.ipong.rani.bluecare.components.objects.Patient;
import com.ipong.rani.bluecare.components.objects.SingleNotif;
import com.ipong.rani.bluecare.firebaseNotification.Notification;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class NotificationsAdapter extends ArrayAdapter<SingleNotif> {

    private Context mContext;
    private List<SingleNotif> notificationDataList;

    public NotificationsAdapter(@NonNull Context context, @LayoutRes ArrayList<SingleNotif> list) {
        super(context, 0 , list);
        mContext = context;
        notificationDataList = list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItem = convertView;
        if(listItem == null)
            listItem = LayoutInflater.from(mContext).inflate(R.layout.notification_layout,parent,false);

        SingleNotif currentItem = notificationDataList.get(position);


        TextView topic = (TextView) listItem.findViewById(R.id.txtViewTopic);
        topic.setText(currentItem.getTopic());


        TextView report = (TextView) listItem.findViewById(R.id.txtViewRep);
        report.setText(currentItem.getPatientReport());


        ImageView dImg = (ImageView) listItem.findViewById((R.id.imgNotification));
        Picasso.get().load("https://images.pexels.com/photos/1282169/pexels-photo-1282169.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=650&w=940").into(dImg);



        return listItem;
    }

}
