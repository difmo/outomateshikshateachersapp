package com.saptrishi.outomateshikshateachersapp.model;

public class HwVideos{
    private String studentname;
    private String topicname;
    private String vidDate;
    private String vidUrl;

    public HwVideos(String studentname, String topicname, String vidDate, String vidUrl) {
        this.studentname = studentname;
        this.topicname = topicname;
        this.vidDate = vidDate;
        this.vidUrl = vidUrl;
    }

    public String getStudentname(){
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getVidDate() {
        return vidDate;
    }

    public void setVidDate(String vidDate) {
        this.vidDate = vidDate;
    }

    public String getVidUrl() {
        return vidUrl;
    }

    public void setVidUrl(String vidUrl) {
        this.vidUrl = vidUrl;
    }
}
