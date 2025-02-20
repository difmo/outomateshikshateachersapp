package com.saptrishi.outomateshikshateachersapp.utils;

import android.content.Context;

/**
 * Created by pawan on 1/31/2018.
 */

public class NotificationData {

    private String title, message , icon;
    private Context context;

    public NotificationData(Context context, String title , String message , String icon){
        this.context = context;
        this.title = title;
        this.message = message;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
