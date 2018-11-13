package com.ipong.rani.bluecare;

import org.json.JSONArray;

public class Patient {


    private String pName;

    private String pAge;

    private JSONArray condition;





    public Patient(String pName, String pAge) {
        this.pName = pName;
        this.pAge = pAge;
    }



    public String getpName() {
        return pName;
    }


    public void setpName(String pName) {
        this.pName = pName;
    }


    public String getpAge() {
        return pAge;
    }


    public void setpAge(String pAge) {
        this.pAge = pAge;
    }


    public void setCondition(JSONArray condition){
        this.condition = condition;
    }


    public JSONArray getCondition() {
        return condition;
    }
}
