package com.ipong.rani.bluecare.components.objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class Patient {

    private JSONObject patientRecord;


    public Patient(JSONObject patientRecord) {
        this.patientRecord = patientRecord;
    }


    public JSONObject getPatientRecord() { return patientRecord; }
    public void setPatientRecord() {  this.patientRecord = patientRecord; }


}