package com.saptrishi.outomateshikshateachersapp.utils;

import android.app.Application;

public class MyApplication extends Application {

    private static MyApplication mInstance;
    public static boolean conn;


    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;


    }

    public static synchronized MyApplication getInstance() {
        return mInstance;
    }

    public void setConnectivityListener(ConnectivityReceiver.ConnectivityReceiverListener listener) {
        ConnectivityReceiver.connectivityReceiverListener = listener;
    }


   }