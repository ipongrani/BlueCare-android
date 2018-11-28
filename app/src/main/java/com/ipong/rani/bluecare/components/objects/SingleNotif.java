package com.ipong.rani.bluecare.components.objects;

public class SingleNotif {

    private String topic;
    private String patientReport;

    public SingleNotif(String topic, String patientReport){
        this.topic = topic;
        this.patientReport = patientReport;
    }

    public String getTopic() { return topic; }
    public void setTopic() { this.topic = topic; }

    public  String getPatientReport() { return patientReport; }
    public void setPatientReport() { this.patientReport = patientReport; }

}
