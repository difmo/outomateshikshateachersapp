package com.saptrishi.outomateshikshateachersapp.jitsivideocall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;

//import org.jitsi.meet.sdk.JitsiMeet;
//import org.jitsi.meet.sdk.JitsiMeetActivity;
//import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;

public class Jitsivideo extends AppCompatActivity {
    TextView tv_versionnames;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jitsivideo);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        ImageView backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        URL serverURL;
        try {
            serverURL = new URL("https://meet.jit.si/shantanu321");
        } catch (MalformedURLException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid server URL!");
        }
//        JitsiMeetConferenceOptions defaultOptions
//                = new JitsiMeetConferenceOptions.Builder()
//                .setServerURL(serverURL)
//                .setWelcomePageEnabled(true)
//                .setFeatureFlag("meeting-password.enabled", true)
//                .setFeatureFlag("chat.enabled", false)
//                .setFeatureFlag("recording.enabled", false)
//                .setFeatureFlag("live-streaming.enabled", true)
//                .setFeatureFlag("help", false)
//                .build();
//
//        JitsiMeet.setDefaultConferenceOptions(defaultOptions);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(Jitsivideo.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        //finish();
    }
    public void onButtonClick(View v) {
        EditText editText = findViewById(R.id.conferenceName);
        String text = editText.getText().toString();

        if (text.length() > 0) {
            // Build options object for joining the conference. The SDK will merge the default
            // one we set earlier and this one when joining.
//            JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
//
//                    .setRoom(text)
//                    .build();
//
//            // Launch the new activity with the given options. The launch() method takes care
//            // of creating the required Intent and passing the options.
//            JitsiMeetActivity.launch(this, options);
        }
    }
}