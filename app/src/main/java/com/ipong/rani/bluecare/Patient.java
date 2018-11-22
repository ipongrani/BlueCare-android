package com.ipong.rani.bluecare;

import org.json.JSONArray;

public class Patient {


    private String pName;

    private String pAge;

    private JSONArray condition;

    private String pMedicaId;
    private String pBloodType;
    private String pBloodPressure;
    private String pAlergies;
    private String pDiet;
    private String pPill;
    private String pTimePill;
    private String pMedTime;

    public Patient(String pName, String pAge, JSONArray condition, String pMedicaId, String pBloodType, String pBloodPressure, String pAlergies, String pDiet, String pPill, String pTimePill, String pMedTime) {
        this.pName = pName;
        this.pAge = pAge;
        this.condition = condition;
        this.pMedicaId = pMedicaId;
        this.pBloodType = pBloodType;
        this.pBloodPressure = pBloodPressure;
        this.pAlergies = pAlergies;
        this.pDiet = pDiet;
        this.pPill = pPill;
        this.pTimePill = pTimePill;
        this.pMedTime = pMedTime;
    }


    //***    Getter Data  ***//
    public String getpName() {
    return pName;
}

    public String getpAge() {
        return pAge;
    }

    public JSONArray getCondition() {
        return condition;
    }

    public String getpMedicaId() {
        return pMedicaId;
    }

    public String getpBloodType() {
        return pBloodType;
    }

    public String getpBloodPressure() {
        return pBloodPressure;
    }

    public String getpAlergies() {
        return pAlergies;
    }

    public String getpDiet() {
        return pDiet;
    }

    public String getpPill() {
        return pPill;
    }

    public String getpTimePill() {
        return pTimePill;
    }

    public String getpMedTime() {
        return pMedTime;
    }


    //END


    //***    Setter Data  ***//
    public void setpName(String pName) {
        this.pName = pName;
    }

    public void setpAge(String pAge) {
        this.pAge = pAge;
    }

    public void setpMedicaId(String pMedicaId) {
        this.pMedicaId = pMedicaId;
    }

    public void setpBloodType(String pBloodType) {
        this.pBloodType = pBloodType;
    }

    public void setpBloodPressure(String pBloodPressure) {
        this.pBloodPressure = pBloodPressure;
    }

    public void setpAlergies(String pAlergies) {
        this.pAlergies = pAlergies;
    }

    public void setpDiet(String pDiet) {
        this.pDiet = pDiet;
    }

    public void setpPill(String pPill) {
        this.pPill = pPill;
    }

    public void setpTimePill(String pTimePill) {
        this.pTimePill = pTimePill;
    }

    public void setpMedTime(String pMedTime) {
        this.pMedTime = pMedTime;
    }

    public void setCondition(JSONArray condition){
        this.condition = condition;
    }


    // END
}