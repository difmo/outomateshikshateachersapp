package com.saptrishi.outomateshikshateachersapp;

public class TimeTableDataModel {
    String name;
    String type;
    String version_number;
    String FrmTime;
   String ToTime;
   String Classsid;
   String FinalDayTimeTable_Id;
   String date;
   String Subjectid;
    public TimeTableDataModel( String name, String type, String version_number, String FrmTime,String ToTime ,String  Classsid ,String FinalDayTimeTable_Id,String date,String Subjectid) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
         this.FrmTime = FrmTime;
        this.ToTime = ToTime ;
        this.Classsid=Classsid;
        this.FinalDayTimeTable_Id=FinalDayTimeTable_Id;
        this.date=date;
        this.Subjectid=Subjectid;
    }


    public String getName() {
        return name;
    }


    public String getType() {
        return type;
    }


    public String getVersion_number() {
        return version_number;
    }

    public String FrmTime() {
        return  FrmTime;
    }

    public String ToTime ()  {
        return  ToTime ;
    }

    public String getClasssid() {
        return Classsid;
    }

    public void setClasssid(String classsid) {
        Classsid = classsid;
    }

    public String getFinalDayTimeTable_Id() {
        return FinalDayTimeTable_Id;
    }

    public void setFinalDayTimeTable_Id(String finalDayTimeTable_Id) {
        FinalDayTimeTable_Id = finalDayTimeTable_Id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSubjectid() {
        return Subjectid;
    }

    public void setSubjectid(String subjectid) {
        Subjectid = subjectid;
    }
}