package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.saptrishi.outomateshikshateachersapp.R;

public class Update extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update2);
        showUpdateDialog();
    }
    boolean isForceUpdate=true;
    public void showUpdateDialog() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(Update.this);
        alertDialogBuilder.setTitle(Update.this.getString(R.string.app_name));
        alertDialogBuilder.setMessage("New Update is available kindly update it from playstore");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("update now", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Update.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                dialog.cancel();
            }
        });
        alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (isForceUpdate) {
                    finish();
                }
                dialog.dismiss();
            }
        });
        alertDialogBuilder.show();
    }
}
