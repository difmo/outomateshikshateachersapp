package com.saptrishi.outomateshikshateachersapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.NoticeActivity;

public class Help_activity extends AppCompatActivity {
    TextView email, persional_call, alt_call, address, weblink;
    TextView appName;
    TextView versionName;
    ImageView call;
    Context context;
    private TextView number_input;
    private Button call_btn;

    //Request Code
    private int request_Code = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_help_activity );
        number_input = findViewById( R.id.alt_call );

        email = findViewById( R.id.email );
        LinearLayout backbutton = findViewById( R.id.backpressed );
        backbutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        } );
        persional_call = findViewById( R.id.alt_call );
        alt_call = findViewById( R.id.alt_call );
        appName = findViewById( R.id.appname );
        call = findViewById( R.id.calllisner );
        call.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int check_permission = ContextCompat.checkSelfPermission( Help_activity.this, Manifest.permission.CALL_PHONE );

                if (check_permission == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    ActivityCompat.requestPermissions( Help_activity.this, new String[]{Manifest.permission.CALL_PHONE}, request_Code );
                }


            }
        } );
        versionName = findViewById( R.id.applicationversion );
        appName.setText( getResources().getString( R.string.app_name ) );
        versionName.setText( BuildConfig.VERSION_NAME );
        address = findViewById( R.id.address );
        weblink = findViewById( R.id.weblink );
        address.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String address = "https://www.google.com/maps/place/Saptrishi+Infosystems+Pvt.+Ltd/@26.858525,80.961715,15z/data=!4m5!3m4!1s0x0:0x10326480a409f72e!8m2!3d26.858525!4d80.961715?hl=en-US";
                Intent i = new Intent( Intent.ACTION_VIEW );
                i.setData( Uri.parse( address ) );
                startActivity( i );

            }
        } );
        persional_call.setOnClickListener( new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                Intent dialIntent = new Intent( Intent.ACTION_DIAL );
                dialIntent.setData( Uri.parse( "tel:" + "9935629797" ) );
                startActivity( dialIntent );
            }
        } );
//        alt_call.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent dialIntent = new Intent(Intent. ACTION_DIAL ) ;
//                    dialIntent.setData(Uri. parse ( "tel:" + "522-4103099" )) ;
//                    startActivity(dialIntent) ;
//                }
//            });

        email.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String to = "rishi@saptrishionline.com";
                Intent email = new Intent( Intent.ACTION_SEND );
                email.putExtra( Intent.EXTRA_EMAIL, new String[]{to} );
                email.putExtra( Intent.EXTRA_SUBJECT, "subject" );
                email.putExtra( Intent.EXTRA_TEXT, "message" );
                //need this to prompts email client only
                email.setType( "message/rfc822" );

                startActivity( Intent.createChooser( email, "Choose an Email client :" ) );
            }
        } );

        weblink.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "http://saptrishi.net/";
                Intent i = new Intent( Intent.ACTION_VIEW );
                i.setData( Uri.parse( url ) );
                startActivity( i );
            }
        } );


//            View footerLayout = findViewById(R.id.fotter_for_all);
//        footerLayout.setOnClickListener(new View.OnClickListener(){
//                @Override
//                public void onClick(View view) {
//                    String url = "http://saptrishi.net/";
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                }
//            });

    }

    private void makePhoneCall() {
        String phone_Number = number_input.getText().toString();

        if (phone_Number.trim().length() > 0) {            // it will trim or dlt empty spaces

            String dial = "tel:" + phone_Number;
            startActivity( new Intent( Intent.ACTION_CALL, Uri.parse( dial ) ) );
        } else {
            Toast.makeText( this, "Enter Phone Number", Toast.LENGTH_LONG ).show();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity( new Intent( Help_activity.this, MainMenuActivity.class ) );
        overridePendingTransition( R.anim.left_toright, R.anim.right_toleft );
        finish();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult( requestCode, permissions, grantResults );
        switch (requestCode) {
            case 77:
                if (grantResults.length >= 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall();
                } else {
                    Toast.makeText( this, "You Don't have permission", Toast.LENGTH_LONG ).show();
                }
        }
    }
}

