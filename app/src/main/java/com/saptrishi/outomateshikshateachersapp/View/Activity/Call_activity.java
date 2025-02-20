package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.R;

public class Call_activity extends AppCompatActivity {
    private TextView number_input, numberinput1, number_input3;
    private Button call_btn1, call_btn2, call_btn3;
     TextView tv_versionnames;
    //Request Code
    private int request_Code = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_activity);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        number_input = (TextView) findViewById(R.id.firstnumber);
        numberinput1 = (TextView) findViewById(R.id.second_number2);
        number_input3 = (TextView) findViewById(R.id.number_input3);


        call_btn1 = (Button) findViewById(R.id.call_btns);
        call_btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check_permission = ContextCompat.checkSelfPermission(Call_activity.this, Manifest.permission.CALL_PHONE);

                if (check_permission == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    ActivityCompat.requestPermissions(Call_activity.this, new String[]{Manifest.permission.CALL_PHONE}, request_Code);
                }
            }
        });


        call_btn2 = (Button) findViewById(R.id.call_btns2);
        call_btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check_permission = ContextCompat.checkSelfPermission(Call_activity.this, Manifest.permission.CALL_PHONE);

                if (check_permission == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall1();
                } else {
                    ActivityCompat.requestPermissions(Call_activity.this, new String[]{Manifest.permission.CALL_PHONE}, request_Code);
                }
            }
        });
        call_btn3 = (Button) findViewById(R.id.call_btns3);
        call_btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check_permission = ContextCompat.checkSelfPermission(Call_activity.this, Manifest.permission.CALL_PHONE);

                if (check_permission == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall2();
                } else {
                    ActivityCompat.requestPermissions(Call_activity.this, new String[]{Manifest.permission.CALL_PHONE}, request_Code);
                }
            }
        });

    }

    private void makePhoneCall() {

        String phone_Number = number_input.getText().toString();

        if (phone_Number.trim().length() > 0) {            // it will trim or dlt empty spaces

            String dial = "tel:" + phone_Number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_LONG).show();
        }

    }

    private void makePhoneCall1() {

        String phone_Number = numberinput1.getText().toString();

        if (phone_Number.trim().length() > 0) {            // it will trim or dlt empty spaces

            String dial = "tel:" + phone_Number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_LONG).show();
        }

    }

    private void makePhoneCall2() {

        String phone_Number = numberinput1.getText().toString();

        if (phone_Number.trim().length() > 0) {            // it will trim or dlt empty spaces

            String dial = "tel:" + phone_Number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        } else {
            Toast.makeText(this, "Enter Phone Number", Toast.LENGTH_LONG).show();
        }

    }
}