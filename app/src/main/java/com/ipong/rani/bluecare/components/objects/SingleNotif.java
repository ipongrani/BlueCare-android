package com.ipong.rani.bluecare.components.objects;

public class SingleNotif {

    private String topic;
    private String patientReport;
    private String date;

    public SingleNotif(String topic, String patientReport, String date){
        this.topic = topic;
        this.patientReport = patientReport;
        this.date = date;
    }

    public String getTopic() { return topic; }
    public void setTopic() { this.topic = topic; }

    public String getDate() { return date; }
    public void setDate() { this.date = date; }

    public String getPatientReport() { return patientReport; }
    public void setPatientReport() { this.patientReport = patientReport; }

}
