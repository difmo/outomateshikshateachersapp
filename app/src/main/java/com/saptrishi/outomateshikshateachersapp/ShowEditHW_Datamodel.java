package com.saptrishi.outomateshikshateachersapp;

public class ShowEditHW_Datamodel {
    String hw_img;
    String hwid;
    String hwurl;
    String emp;
    String subjectid;
    String classid;
    String finaldaytimetableid;
    String daateofHomework;
    String dateOfSubmission;
//    ustid,subjectid,classid,finaldaytimetableid,dateodhomework


    public ShowEditHW_Datamodel(String hw_img, String hwid, String hwurl, String emp, String subjectid, String classid, String finaldaytimetableid, String daateofHomework, String dateofSubmission) {
        this.hw_img=hw_img;
        this.hwid=hwid;
        this.hwurl=hwurl;

        this.emp=emp;
        this.subjectid=subjectid;
        this.classid=classid;
        this.finaldaytimetableid=finaldaytimetableid;
        this.dateOfSubmission=dateofSubmission;
        this.daateofHomework=daateofHomework;

    }


    public String gethw_img() {
        return hw_img;
    }


    public String gethwid() {
        return hwid;
    }


    public String gethwurl() {
        return hwurl;
    }
    public String getDateOfSubmission(){return dateOfSubmission;}

    public String refgetemp(){ return  emp;}

    public  String refgetsubjectid(){ return subjectid;}

    public String refgetclasid(){return classid;}

    public String refgetfinaldaytimeTableid(){return  finaldaytimetableid;}

    public String refgetdateofHomework(){return  daateofHomework;}

}