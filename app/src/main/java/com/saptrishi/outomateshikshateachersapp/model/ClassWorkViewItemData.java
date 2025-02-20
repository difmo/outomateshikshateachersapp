package com.saptrishi.outomateshikshateachersapp.model;

public class ClassWorkViewItemData{

    private String classworkviewImageLinkTopicName;
    private String classworkviewVideLinkTopicName;
    private String classworkviewFileLinkTopicName;

    public ClassWorkViewItemData(String classworkviewImageLinkTopicName, String classworkviewVideLinkTopicName, String classworkviewFileLinkTopicName) {
        this.classworkviewImageLinkTopicName = classworkviewImageLinkTopicName;
        this.classworkviewVideLinkTopicName = classworkviewVideLinkTopicName;
        this.classworkviewFileLinkTopicName = classworkviewFileLinkTopicName;
    }

    public String getClassworkviewImageLinkTopicName() {
        return classworkviewImageLinkTopicName;
    }

    public void setClassworkviewImageLinkTopicName(String classworkviewImageLinkTopicName) {
        this.classworkviewImageLinkTopicName = classworkviewImageLinkTopicName;
    }

    public String getClassworkviewVideLinkTopicName() {
        return classworkviewVideLinkTopicName;
    }

    public void setClassworkviewVideLinkTopicName(String classworkviewVideLinkTopicName) {
        this.classworkviewVideLinkTopicName = classworkviewVideLinkTopicName;
    }

    public String getClassworkviewFileLinkTopicName() {
        return classworkviewFileLinkTopicName;
    }

    public void setClassworkviewFileLinkTopicName(String classworkviewFileLinkTopicName) {
        this.classworkviewFileLinkTopicName = classworkviewFileLinkTopicName;
    }
}
