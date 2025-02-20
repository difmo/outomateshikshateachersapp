package com.saptrishi.outomateshikshateachersapp.model;

public class AttendanceviewModel {
    String Attan_Status;
    String Attan_date;
    String FatherMobileNo;
    String MotherMobileNo;
    String Session_Id_FK;
    String clientid;
    String errormessage;
    String studentid;
    String studentname;
    String stuAttDscription;

    public String getAttan_Status() {
        return Attan_Status;
    }

    public void setAttan_Status(String attan_Status) {
        Attan_Status = attan_Status;
    }

    public String getAttan_date() {
        return Attan_date;
    }

    public void setAttan_date(String attan_date) {
        Attan_date = attan_date;
    }

    public String getFatherMobileNo() {
        return FatherMobileNo;
    }

    public void setFatherMobileNo(String fatherMobileNo) {
        FatherMobileNo = fatherMobileNo;
    }

    public String getMotherMobileNo() {
        return MotherMobileNo;
    }

    public void setMotherMobileNo(String motherMobileNo) {
        MotherMobileNo = motherMobileNo;
    }

    public String getSession_Id_FK() {
        return Session_Id_FK;
    }

    public void setSession_Id_FK(String session_Id_FK) {
        Session_Id_FK = session_Id_FK;
    }

    public String getClientid() {
        return clientid;
    }

    public void setClientid(String clientid) {
        this.clientid = clientid;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getStudentid() {
        return studentid;
    }

    public void setStudentid(String studentid) {
        this.studentid = studentid;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getStuAttDscription(){
        return stuAttDscription;
    }

    public void setStuAttDscription(String stuAttDscription) {
        this.stuAttDscription = stuAttDscription;
    }
}
