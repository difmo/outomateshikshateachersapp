package com.saptrishi.outomateshikshateachersapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Gravity;
import android.widget.Toast;

import com.saptrishi.outomateshikshateachersapp.View.Activity.MainActivity;

public class CheckPermissionActivity extends AppCompatActivity {


    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int PHONE_PERMISSION_CODE = 102;
    private static final int STATE_PERMISSION_CODE = 102;
    Boolean camera=false;
    Boolean storage=false;
    Boolean phone=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_permission);
        Toolbar toolbar = findViewById(R.id.toolbar);


        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_PHONE_STATE
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

        if (hasPermissions(this,PERMISSIONS))
        {
            startActivity(new Intent(CheckPermissionActivity.this,MainActivity.class));
            finish();
        }
        else
        {
            Toast toast =Toast.makeText(this, "Kindly give all permissions...", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
            finish();
        }


    }

    private void Permissioncheck() {
        checkPermission(Manifest.permission.READ_PHONE_STATE, PHONE_PERMISSION_CODE);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,STORAGE_PERMISSION_CODE);
        checkPermission(Manifest.permission.CAMERA,CAMERA_PERMISSION_CODE);
        checkPermission(Manifest.permission.READ_PHONE_STATE,STATE_PERMISSION_CODE);

    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(CheckPermissionActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(CheckPermissionActivity.this,
                    new String[]{permission},
                    requestCode);
        }
    }

//    @Override
//    public void onRequestPermissionsResult(int requestCode,
//                                           @NonNull String[] permissions,
//                                           @NonNull int[] grantResults) {
//        super
//                .onRequestPermissionsResult(requestCode,
//                        permissions,
//                        grantResults);
//
//
//        if (requestCode == CAMERA_PERMISSION_CODE) {
//
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(MainActivity.this,
////                        "Camera Permission Granted",
////                        Toast.LENGTH_SHORT)
////                        .show();
//                camera=true;
//
//            } else {
//                Log.e("cameraPermission1",grantResults.length+" vasl "+grantResults[0]);
//
//                Toast.makeText(CheckPermissionActivity.this,
//                        "Camera Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        } else if (requestCode == STORAGE_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(MainActivity.this,
////                        "Storage Permission Granted",
////                        Toast.LENGTH_SHORT)
////                        .show();
//                       storage=true;
//            } else {
//                Toast.makeText(CheckPermissionActivity.this,
//                        "Storage Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        } else if (requestCode == PHONE_PERMISSION_CODE) {
//            if (grantResults.length > 0
//                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
////                Toast.makeText(MainActivity.this,
////                        "Storage Permission Granted",
////                        Toast.LENGTH_SHORT)
////                        .show();
//             phone=true;
//
//            } else {
//                Toast.makeText(CheckPermissionActivity.this,
//                        "Storage Permission Denied",
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        }
//
//       if (phone && camera && storage)
//       {
//           startActivity(new Intent(CheckPermissionActivity.this,MainActivity.class));
//           finish();
//       }
//    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

}
