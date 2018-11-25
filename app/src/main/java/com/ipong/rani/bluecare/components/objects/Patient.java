package com.ipong.rani.bluecare.components.objects;

import org.json.JSONArray;
import org.json.JSONObject;

public class Patient {


    private String pName;
    private String pAge;
    private String pStatus;

    private JSONObject patientRecord;

//    private JSONArray condition;
    private String condition;
//    private String status;

    private String pMedicalId;
    private String pBloodType;
    private String pBloodPressure;
    private String pAllergies;
    private String pDiet;
    private String pPill;
    private String pTimePill;
    private String pMedTime;

/*
    public Patient(String pName, String pAge, JSONArray condition, String pMedicalId, String
            pBloodType, String pBloodPressure, String pAlergies, String pDiet, String pPill, String pTimePill, String pMedTime) {
        this.pName = pName;
        this.pAge = pAge;
//        this.condition = condition;
        this.pMedicalId = pMedicalId;
        this.pBloodType = pBloodType;
        this.pBloodPressure = pBloodPressure;
        this.pAllergies = pAlergies;
        this.pDiet = pDiet;
        this.pPill = pPill;
        this.pTimePill = pTimePill;
        this.pMedTime = pMedTime;
    }

    */

    public Patient(String pName, String pStatus, String condition, JSONObject patientRecord) {
        this.pName = pName;
        this.pStatus = pStatus;
        this.condition = condition;
        this.patientRecord = patientRecord;

    }



    //***    Getter Data  ***//
    public String getpName() {
    return pName;
}
    public String getpAge() {
        return pAge;
    }
    public String getpStatus() { return pStatus; }
    public String getCondition() {
        return condition;
    }
    public JSONObject getPatientRecord() { return patientRecord; }



//    public JSONArray getCondition() {
//        return condition;
//    }



//    public String getpMedicalId() {
//        return pMedicalId;
//    }
//
//    public String getpBloodType() {
//        return pBloodType;
//    }
//
//    public String getpBloodPressure() {
//        return pBloodPressure;
//    }
//
//    public String getpAllergies() {
//        return pAllergies;
//    }
//
//    public String getpDiet() {
//        return pDiet;
//    }
//
//    public String getpPill() {
//        return pPill;
//    }
//
//    public String getpTimePill() {
//        return pTimePill;
//    }
//
//    public String getpMedTime() {
//        return pMedTime;
//    }


    //END


    //***    Setter Data  ***//
    public void setpName(String pName) {
        this.pName = pName;
    }
    public void setpAge(String pAge) {
        this.pAge = pAge;
    }
    public void setCondition(String condition){
        this.condition = condition;
    }
    public void setPatientRecord() {  this.patientRecord = patientRecord; }



//    public void setpMedicalId(String pMedicalId) {
//        this.pMedicalId = pMedicalId;
//    }
//
//    public void setpBloodType(String pBloodType) {
//        this.pBloodType = pBloodType;
//    }
//
//    public void setpBloodPressure(String pBloodPressure) {
//        this.pBloodPressure = pBloodPressure;
//    }
//
//    public void setpAllergies(String pAllergies) {
//        this.pAllergies = pAllergies;
//    }
//
//    public void setpDiet(String pDiet) {
//        this.pDiet = pDiet;
//    }
//
//    public void setpPill(String pPill) {
//        this.pPill = pPill;
//    }
//
//    public void setpTimePill(String pTimePill) {
//        this.pTimePill = pTimePill;
//    }
//
//    public void setpMedTime(String pMedTime) {
//        this.pMedTime = pMedTime;
//    }




    // END
}