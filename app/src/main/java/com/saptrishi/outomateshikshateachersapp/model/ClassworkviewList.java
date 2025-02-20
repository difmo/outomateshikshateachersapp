package com.saptrishi.outomateshikshateachersapp.model;

import java.util.List;

public class ClassworkviewList {

    String CW_Upload_path_img;
    String ContName;
    String ContytypID;
    String Errormsg;
    String Filename;
    String TopicName;
    String VideoLink;

    List<ClassworkviewList> filelistcw;

    public List<ClassworkviewList> getFilelistcw() {
        return filelistcw;
    }

    public void setFilelistcw(List<ClassworkviewList> filelistcw) {
        this.filelistcw = filelistcw;
    }

    public String getCW_Upload_path_img() {
        return CW_Upload_path_img;
    }

    public void setCW_Upload_path_img(String CW_Upload_path_img) {
        this.CW_Upload_path_img = CW_Upload_path_img;
    }

    public String getContName() {
        return ContName;
    }

    public void setContName(String contName) {
        ContName = contName;
    }

    public String getContytypID() {
        return ContytypID;
    }

    public void setContytypID(String contytypID) {
        ContytypID = contytypID;
    }

    public String getErrormsg() {
        return Errormsg;
    }

    public void setErrormsg(String errormsg) {
        Errormsg = errormsg;
    }

    public String getFilename() {
        return Filename;
    }

    public void setFilename(String filename) {
        Filename = filename;
    }

    public String getTopicName() {
        return TopicName;
    }

    public void setTopicName(String topicName) {
        TopicName = topicName;
    }

    public String getVideoLink() {
        return VideoLink;
    }

    public void setVideoLink(String videoLink) {
        VideoLink = videoLink;
    }
}
