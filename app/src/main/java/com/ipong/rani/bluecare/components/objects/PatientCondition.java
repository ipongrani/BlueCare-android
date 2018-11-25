package com.ipong.rani.bluecare.components.objects;



public class PatientCondition {

    private String conditonName;
    private String conditionStatus;


    public PatientCondition(String cName, String cStatus){
        this.conditonName = cName;
        this.conditionStatus = cStatus;
    }



    public void setConditonName (String cName) { this.conditonName = cName; }

    public String getConditonName() { return conditonName; }

    public void setConditionStatus(String cStatus) { this.conditionStatus = cStatus; }

    public String getConditionStatus() { return conditionStatus; }





}
