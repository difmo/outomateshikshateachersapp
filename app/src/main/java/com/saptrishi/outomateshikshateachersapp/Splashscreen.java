package com.saptrishi.outomateshikshateachersapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.LoginActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.newUserActivity;

import java.util.Locale;

public class Splashscreen extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Activity activity;
    private static final int APP_PACKAGE_DOT_COUNT = 3; // number of dots present in package name
    private static final String DUAL_APP_ID_999 = "999";
    private static final char DOT = '.';


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        activity = this;

        //this code will prevent from cloning
        checkAppCloning();
        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        SharedPreferences prefs = getSharedPreferences("Settings", MODE_PRIVATE);
        String name = prefs.getString("My Lang", "");
        Log.e( "language",name );   // LANGUAGE
        {
            Locale locale = new Locale(name);
            Locale.setDefault(locale);
            Configuration config = new Configuration();
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics() );
            SharedPreferences .Editor editors = getSharedPreferences("Settings",MODE_PRIVATE).edit();
            editors.putString("My Lang", name);
            editors.apply();

        }
        //existing code
        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String j = sharedPreferences.getString("logid", "");

        //Default is 0 so autologin is disabled
        if (j != "" ) {
            Log.i("Loogged in", "your in");
            Intent activity = new Intent(getApplicationContext(), MainMenuActivity.class);
            startActivity(activity);
        } else if (j == ""){
            Log.i("Not luck", "your not in");
            Intent activity = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(activity);
        }

//// put the following code for the logout button
//        SharedPreferences sharedPreferences;
//        sharedPreferences = getActivity().getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putInt("key", 0);
//        editor.apply();
    }

    private void checkAppCloning()
    {
        String path = getFilesDir().getPath();
    //    Toast.makeText(activity, path+"", Toast.LENGTH_LONG).show();
        if (path.contains(DUAL_APP_ID_999))
        {
            killProcess();
        } else
        {
            int count = getDotCount(path);
            if (count > APP_PACKAGE_DOT_COUNT)
            {
                killProcess();
            }
        }
    }

    private int getDotCount(String path)
    {
        int count = 0;
        for (int i = 0; i < path.length(); i++)
        {
            if (count > APP_PACKAGE_DOT_COUNT)
            {
                break;
            }
            if (path.charAt(i) == DOT)
            {
                count++;
            }
        }
        return count;
    }

    private void killProcess()
    {
        //Toast.makeText(activity, "KILL PROCESS!!", Toast.LENGTH_SHORT).show();
        finish();
        //Process.killProcess(Process.myPid());
        android.os.Process.killProcess(android.os.Process.myPid());
        super.onBackPressed();

    }
}
