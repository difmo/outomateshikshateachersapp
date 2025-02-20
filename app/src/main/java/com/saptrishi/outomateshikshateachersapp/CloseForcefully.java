package com.saptrishi.outomateshikshateachersapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class CloseForcefully extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_close_forcefully);
        finish();
        System.exit(0);
    }
}