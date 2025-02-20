package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.annotation.NonNull;
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

import com.saptrishi.outomateshikshateachersapp.R;

public class CallingApp extends AppCompatActivity {
    private TextView number_input;
    private Button call_btn;

    //Request Code
    private int request_Code = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calling_app);

      //  number_input = (TextView) findViewById(R.id.number_input);
//        call_btn = (Button) findViewById(R.id.call_btn);

        call_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int check_permission = ContextCompat.checkSelfPermission(CallingApp.this, Manifest.permission.CALL_PHONE);

                if(check_permission == PackageManager.PERMISSION_GRANTED){
                    makePhoneCall();
                }else{
                    ActivityCompat.requestPermissions(CallingApp.this, new String[] {Manifest.permission.CALL_PHONE},request_Code );
                }
            }
        });
    }

    private void makePhoneCall() {

        String phone_Number = number_input.getText().toString();

        if(phone_Number.trim().length()>0 ){            // it will trim or dlt empty spaces

            String dial = "tel:" + phone_Number;
            startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
        }else {
            Toast.makeText(this,"Enter Phone Number",Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 77:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText(this, "You Don't have permission", Toast.LENGTH_LONG).show();
                }
        }
    }
}

