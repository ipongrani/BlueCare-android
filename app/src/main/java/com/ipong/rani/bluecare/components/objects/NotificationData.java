package com.ipong.rani.bluecare.components.objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class NotificationData {

    private JSONArray notification;

    public NotificationData(JSONArray notification) {

        this.notification = notification;

    }

    public JSONArray getNotification() { return notification; }
    public void setNotification() { this.notification = notification; }

}
