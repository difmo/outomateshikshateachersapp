package com.saptrishi.outomateshikshateachersapp;

public class GiveHomeworkDataModel {
    String name;
    String type;
    String version_number;
    String emp;
    String subjectid;
    String classid;
    String finaldaytimetableid;
    String daateofHomework;


    public GiveHomeworkDataModel(String name, String type, String version_number,String emp,String subjectid,String classid,String finaldaytimetableid,String daateofHomework ) {
        this.name=name;
        this.type=type;
        this.version_number=version_number;
        this.emp=emp;
        this.subjectid=subjectid;
        this.classid=classid;
        this.finaldaytimetableid=finaldaytimetableid;
        this.daateofHomework=daateofHomework;

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

    public String getempid() {
        return emp;
    }


    public String getSubjectid() {
        return subjectid;
    }


    public String getClassid() {
        return classid;
    }

    public String getFinaldaytimetableid() {
        return finaldaytimetableid;
    }

    public String getDaateofHomework()
    {
        return daateofHomework;
    }

}