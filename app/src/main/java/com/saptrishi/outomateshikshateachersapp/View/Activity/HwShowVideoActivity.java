package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.saptrishi.outomateshikshateachersapp.R;

public class HwShowVideoActivity extends AppCompatActivity {
    WebView webViewHw;
    String VideoUrl;
    View decorView;
    String saVideoUrl;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showhw_videos_activity);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        WebView videoview = (WebView)findViewById(R.id.hw_videos_webview);
        Intent intent=getIntent();
        String url=intent.getStringExtra("vid_url");
        saVideoUrl=intent.getStringExtra("VideoUrl");
        try {
            videoview.setWebViewClient(new WebViewClient());
            videoview.getSettings().setJavaScriptEnabled(true);
//            webview.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
//            webview.getSettings().setPluginState(WebSettings.PluginState.ON);
//            webview.getSettings().setMediaPlaybackRequiresUserGesture(false);
//            webview.setWebChromeClient(new WebChromeClient());
//            webview.loadUrl("https://www.youtube.com");
        }
        catch (Exception e)
        {
            Log.e("exinklink",e.getMessage());
        }


    }
}
