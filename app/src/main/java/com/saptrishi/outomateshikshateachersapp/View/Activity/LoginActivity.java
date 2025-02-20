package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import com.google.android.material.snackbar.Snackbar;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.utils.Config;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Random;


public class LoginActivity extends AppCompatActivity
//        implements InAppUpdateManager.InAppUpdateHandler
{
    boolean doubleBackToExitPressedOnce=false;
    EditText branchId, loginId, password;
    Button btn;
    TextView tv_versionnames;
    TextView newUser;
    MySqliteDataBase mySqliteDataBase;
    SharedPreferences sharedPreferences;
    CheckBox checkbox;
    String currentVersion;
   TextView lang;
    String flagct ;
//    InAppUpdateManager inAppUpdateManager;
    @Override
        protected void onCreate(Bundle savedInstanceState)
     {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
         tv_versionnames=findViewById(R.id.tv_versionname);
         tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
//        ActionBar actionBar  =   getSupportActionBar();
//        actionBar.setTitle(getResources().getString(R.string.app_name));
//         Adding this line will prevent taking screenshot in your app
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,
//                WindowManager.LayoutParams.FLAG_SECURE);// secure web application
        try {
            currentVersion = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
//        new GetVersionCode().execute();
         lang = findViewById(R.id.language);
        lang.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                showchangeLanguageDialog();
            }
        });
         checkbox = findViewById(R.id.checkbox);
        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);


        mySqliteDataBase = new MySqliteDataBase(LoginActivity.this);
        ActionBar actionBar = getSupportActionBar();

        actionBar.hide();


        final int PERMISSION_ALL = 1;
        final String[] PERMISSIONS = {
                android.Manifest.permission.READ_PHONE_STATE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                android.Manifest.permission.CAMERA
        };

        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }


        if (!Connectivity.isConnected(getApplicationContext())) {
            final Dialog dialog = new Dialog(LoginActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.connection_dialog);
            Button dialogButton = (Button) dialog
                    .findViewById(R.id.dialogButtonOK);

            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();

                }
            });

            dialog.show();
        }

        branchId = findViewById(R.id.et_brid);
        loginId = findViewById(R.id.et_loginid);
        password = findViewById(R.id.et_password);

        branchId.setText(sharedPreferences.getString("brid", ""));
        loginId.setText(sharedPreferences.getString("logid", ""));
        password.setText(sharedPreferences.getString("password1", ""));

        btn = findViewById(R.id.loginButton);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                if (hasPermissions(LoginActivity.this,PERMISSIONS))
//                {

                    checkbox_clicked();
                    if (branchId.getText().toString().trim().isEmpty() && loginId.getText().toString().trim().isEmpty()) {
                        branchId.setError(" Enter App.ID");
                        loginId.setError("Enter Employee ID");
                        branchId.requestFocus();
                    } else {
                        if (password.getText().toString().trim().isEmpty()) {
                            password.setError("Enter Password");
                            password.requestFocus();
                        } else {
                            btn.setBackgroundColor(Color.BLUE);
                            if (!Connectivity.isConnected(getApplicationContext())) {
                                doOnWithoutInterNet();


                            } else {
                                sendDataTodb(branchId.getText().toString().trim(), loginId.getText().toString().trim(), password.getText().toString().trim());

                            }

                        }

                    }
//                }
//                else
//                {
//                    Toast.makeText(LoginActivity.this, "Kindly give all permissions", Toast.LENGTH_SHORT).show();
//                    ActivityCompat.requestPermissions(LoginActivity.this, PERMISSIONS, PERMISSION_ALL);
//
//                }
            }
        });

        newUser = findViewById(R.id.txtnewuser);
        newUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = branchId.getText().toString();
                String login = loginId.getText().toString();
                Intent intent = new Intent(LoginActivity.this,newUserActivity.class);
               intent.putExtra("branchid", str);
                intent.putExtra("login", login);
                startActivity(intent);

            }
        });
//        inAppUpdateManager = InAppUpdateManager.Builder(this, 101)
//                .resumeUpdates(true)
//                .mode(Constants.UpdateMode.IMMEDIATE)
//                .snackBarAction("An update has been Downloaded")
//                .snackBarAction("RESTART")
//                .handler(this);
//        inAppUpdateManager.checkForAppUpdate();


       }

    private void showchangeLanguageDialog()
    {  //// language changes
        final String[] listitems = {"English","Hindi"};
    AlertDialog.Builder mBuilder = new AlertDialog.Builder(LoginActivity.this);
mBuilder.setTitle("Choose Language...");
mBuilder.setSingleChoiceItems(listitems, -1, new DialogInterface.OnClickListener() {
    @Override
    public void onClick(DialogInterface dialog, int i) {
        if (i == 0)
        {
            setLocale("en");
            recreate();
        }
        if (i == 1)
        {
            setLocale("hi");
            recreate();



        }
    }
});
AlertDialog mDialog = mBuilder.create();
mDialog.show();
    }

    private void setLocale(String lang) {
Locale locale = new Locale(lang);
Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config,getBaseContext().getResources().getDisplayMetrics() );
        SharedPreferences .Editor editors = getSharedPreferences("Settings",MODE_PRIVATE).edit();
        editors.putString("My Lang", lang);
        editors.apply();

    }

    public static boolean hasPermissions(Context context, String... permissions)
        {
        if (context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
         }

        public void checkbox_clicked()
        {
        SharedPreferences.Editor editor = sharedPreferences.edit();
            SharedPreferences .Editor editors = getSharedPreferences("Settings",MODE_PRIVATE).edit();

        if (checkbox.isChecked()) {
            editor.putString("brid", branchId.getText().toString().trim());
            editors.putString("My Lang","");
            editor.putString("logid", loginId.getText().toString().trim());
            editor.putString("password1", password.getText().toString().trim());
            editor.apply();
            // true,do the task

        } else {
            editor.putString("brid", "");
            editor.putString("logid", "");
            editor.putString("password1", "");
            editor.apply();
        }

       }

        private void sendDataTodb(final String branchid, final String loginid, final String password) {


        final ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setTitle("Loading...");
        pDialog.setMessage("Validating your fields");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();

        String url = Url_Symbol.url + "Login/appid/" + branchid + "/staffclientid/" + loginid + "/pass/" + password;
        Log.e("loginUR ffd L", url);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("check", response + "");
                try {
                    mySqliteDataBase.deleteMasterTable();
                    int i, chk;
                    chk = 0;
                    for (i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String errormessage = jsonObject.getString("errormessage");

                        if (errormessage.equals("Correct")) {
                            mySqliteDataBase.deleteMasterTable();
                            String url = jsonObject.getString("Logo");
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("schoolurl", url);
                            editor.apply();
                            String BranchId = jsonObject.getString("BranchId");
                            Log.e("Branchidas",BranchId);
                            String Logo = jsonObject.getString("Logo");
                            String SchoolName = jsonObject.getString("SchoolName");
                            String SchoolNameShort = jsonObject.getString("SchoolNameShort");
                            String Staff_ClientId = jsonObject.getString("Staff_ClientId");
                            String Staff_Id_Fk = jsonObject.getString("Staff_Id_Fk");
                            String Staff_Name = jsonObject.getString("Staff_Name");
                            flagct = jsonObject.getString("FlagCT");
                            String Teacherdob = jsonObject.getString("dob");
                            String doj = jsonObject.getString("emp_email");
                            String emp_id = jsonObject.getString("emp_id");
                            String empname = jsonObject.getString("empname");
                            String mobno = jsonObject.getString("mobno");
                            String father = jsonObject.getString("emp_Father");
                            String per_add = jsonObject.getString("emp_localAdd");
                            String par_add = jsonObject.getString("emp_PraAdd");
                            String qualification = jsonObject.getString("emp_edu");
                            String bloodgroup = jsonObject.getString("emp_bloodgrp");
                            String proile = jsonObject.getString("emp_Img");
//                            String mobno = jsonObject.getString("mobno");

                            editor.putString("empid", emp_id);
//                            editor.putString("childrenid", Staff_ClientId);
                            editor.putString("empname", empname);
                            editor.putString("Branchid", BranchId);
                            editor.apply();


                            Cursor c = mySqliteDataBase.fetchstudentExistence(Staff_ClientId);
                            if (!c.moveToFirst()) {
//
                                sendtoken(emp_id);

                                mySqliteDataBase.insertDatainMasterTable(BranchId, Logo, SchoolName, SchoolNameShort, Staff_ClientId, Staff_Id_Fk, Staff_Name, Teacherdob, doj, emp_id, empname, mobno,father,qualification,per_add,par_add,proile,bloodgroup,flagct);

                                editor.putString("empname", empname);
                                editor.apply();
                            }
                        } else {
                            chk = 1;
                            Log.e("errorLoginelse", errormessage + "");
                           Toast.makeText(LoginActivity.this,"Login Failed Please Check User Id & Password Or Contact your School", Toast.LENGTH_LONG).show();// Toast.makeText(LoginActivity.this, errormessage + "", Toast.LENGTH_SHORT).show();// toast new
                            btn.setBackground(getDrawable(R.drawable.buttonbackground));
                        }
//                            mySqliteDataBase.insertDatainMasterTable(BranchID,BranchLogo,BranchName,ClassID,ClassNm,EmailID,FatherMobileNo,MotherMobileNo,ParentId,Rollno,clientid,classStructureID,dob,fatherName,motherName,section,studentID,studentName,F_SessionId);
                    }

                    if (i == response.length() && chk == 0) {

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("branchid", branchid);
                        editor.putString("branchid1", branchid);
                        editor.putString("loginid", loginid);
                        editor.putString("password", password);
                        editor.putString("chk", "1");
                        editor.apply();
                        Log.e("parentidsave", sharedPreferences.getString("parentid", ""));
                        Log.e("passwordsave", sharedPreferences.getString("password", ""));
                        Log.e("branchsave", sharedPreferences.getString("branchid", ""));
                        vollypermission();
                        pDialog.cancel();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("LoginActivitycatch", e + "");
                }
//                progressView.setVisibility(View.GONE);
                pDialog.hide();
            }
        }, new Response.ErrorListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onErrorResponse(VolleyError error) {
                btn.setBackground(getDrawable(R.drawable.buttonbackground));
//                progressView.setVisibility(View.GONE);
                pDialog.hide();
                Snackbar.make(findViewById(android.R.id.content), "We are not able to connect to our server please try again later...", Snackbar.LENGTH_LONG).show();
//                Toast.makeText(LoginActivity.this, "We are not able to connect to our server please try again later...", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivityerror", error.getMessage() + "");
            }


        });
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);

         }
        public void sendtoken(String empid) {
        MainActivity m1 = new MainActivity();
        String Token = sharedPreferences.getString("token12", "1");
        Log.e("tokeninMethod", Token);
            String firstTime = sharedPreferences.getString("firstTime", "0");
            if (firstTime.equals("0")) {
                Random myRandom = new Random();
                String rand=String.valueOf(myRandom.nextInt(100));
                m1.sendTokenToServer(Token, empid, rand, this);
                SharedPreferences.Editor editor = getSharedPreferences("ShikshaContainer1", MODE_PRIVATE).edit();
                editor.putString("firstTime", "1");
                editor.apply();
            }
         }



         public void doOnWithoutInterNet() {
        Log.e("dataLogin", "data not sync not connected");
//         String id= schoolid1.getText().toString().trim()+schoolid2.getText().toString().trim();
        String branchid = branchId.getText().toString().trim();
        String loginid = loginId.getText().toString().trim();
        String pass = password.getText().toString().trim();

        Cursor c = mySqliteDataBase.fetchMasterTable();
        if (!c.moveToFirst()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                btn.setBackground(getDrawable((R.drawable.buttonbackground)));
            }
            Toast.makeText(LoginActivity.this, "Internet Connection No Available", Toast.LENGTH_SHORT).show();// Toast new

        } else {
            Log.e("parentid1", sharedPreferences.getString("branchid1", ""));
            Log.e("loginid", sharedPreferences.getString("loginid", ""));
            Log.e("password", sharedPreferences.getString("password", ""));
            String branchidchk = sharedPreferences.getString("branchid1", "*");
            String loginidchk = sharedPreferences.getString("loginid", "*");
            String ppass = sharedPreferences.getString("password", "*");
            if (loginid.equals(loginidchk) && pass.equals(ppass) && branchid.equals(branchidchk)) {
                SharedPreferences.Editor ed = sharedPreferences.edit();
                ed.putString("chk", "1");
                ed.apply();
                Toast.makeText(LoginActivity.this, "Your data is not Sync Check your Internet Conncetion", Toast.LENGTH_LONG).show();// new toast
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                finish();
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    btn.setBackground(getDrawable((R.drawable.buttonbackground)));
                }
                Toast.makeText(LoginActivity.this, "You are not registered with this id........", Toast.LENGTH_SHORT).show();
            }

        }
    }

//    @SuppressLint("HardwareIds")
//    private String getDeviceIemi() {
//
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//
//
//            ActivityCompat.requestPermissions(this,new String [] {Manifest.permission.READ_PHONE_STATE},1001);
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//        }
//        else
//        {
//                return telephonyManager.getDeviceId();
//        }
//
////        try
////        {
//        return  null;
////        }
////        catch(Exception ex)
////        {
////            getDeviceIemi();
////
////        }
//
//
////        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
////        try {
////            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
////                // TODO: Consider calling
////                //    ActivityCompat#requestPermissions
////                // here to request the missing permissions, and then overriding
////                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
////                //                                          int[] grantResults)
////                // to handle the case where the user grants the permission. See the documentation
////                // for ActivityCompat#requestPermissions for more details.
////                Toast.makeText(MainActivity.this, "Kindly give Phone permission", Toast.LENGTH_SHORT).show();
////                askPermission();
////
////            } else {
////                final String dev_IEMI = tm.getDeviceId();
////
////                return dev_IEMI;
////            }
////        } catch (Exception ex) {
////            Toast.makeText(MainActivity.this, "Kindly give Phone permission", Toast.LENGTH_SHORT).show();
////        }
////
////        return null;
////
//    }
//

//    class GetVersionCode extends AsyncTask<Void, String, String> {
//
//        @Override
//
//        protected String doInBackground(Void... voids) {
//
//            String newVersion = null;
//
//            try {
//                Document document = Jsoup.connect("https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName() + "&hl=en")
//                        .timeout(30000)
//                        .userAgent("Mozilla/5.0 (Windows; U; WindowsNT 5.1; en-US; rv1.8.1.6) Gecko/20070725 Firefox/2.0.0.6")
//                        .referrer("http://www.google.com")
//                        .get();
//                if (document != null) {
//                    Elements element = document.getElementsContainingOwnText("Current Version");
//                    for (Element ele : element) {
//                        if (ele.siblingElements() != null) {
//                            Elements sibElemets = ele.siblingElements();
//                            for (Element sibElemet : sibElemets) {
//                                newVersion = sibElemet.text();
//                            }
//                        }
//                    }
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//            return newVersion;
//
//        }
//
//        @Override
//        protected void onPostExecute(String onlineVersion) {
//            super.onPostExecute(onlineVersion);
//            if (onlineVersion != null && !onlineVersion.isEmpty()) {
//                if (Float.valueOf(currentVersion) < Float.valueOf(onlineVersion)) {
//
//                    Toast.makeText(LoginActivity.this, "New update available", Toast.LENGTH_SHORT).show();
////                    startActivity(new Intent(MainActivity.this, Update.class));
////                    finish();
//                    showUpdateDialog();
////                     Toast.makeText(MainActivity.this, error + "", Toast.LENGTH_SHORT).show();
//                }
//            }
//            String mname = "https://play.google.com/store/apps/details?id=" + LoginActivity.this.getPackageName();
//
//            Log.e("update", " Current version " + currentVersion + " playstore version " + onlineVersion);
//
//
//        }
//    }
//
//    boolean isForceUpdate = true;
//
//    public void showUpdateDialog() {
//        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(LoginActivity.this);
//
//        alertDialogBuilder.setTitle(LoginActivity.this.getString(R.string.app_name));
//        alertDialogBuilder.setMessage("New Update is available kindly update it from playstore");
//        alertDialogBuilder.setCancelable(false);
//        alertDialogBuilder.setPositiveButton("update now", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int id) {
//                LoginActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
////                LoginActivity.this.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
////
//                dialog.cancel();
//            }
//        });
//        alertDialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                if (isForceUpdate) {
//                    finish();
//                }
//                dialog.dismiss();
//            }
//        });
//        alertDialogBuilder.show();
//    }

    public void vollypermission(){


        final ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setTitle("Loading...");
        pDialog.setMessage("Validating your fields");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
//        String Branchid=sharedPreferences.getString("branchidurl","");
        String url=Url_Symbol.url_sikhsha+"GetAppPermission/AppName/Teacher/brid/"+sharedPreferences.getString("Branchid","");
        Log.e("permission",url);
        final RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("per_response123",response+"");
                mySqliteDataBase.deletepermissiontable();
                for(int i=0;i<response.length();i++)
                {
                    try {
                        JSONObject jsonObject=response.getJSONObject(i);
                        String AppModuleID=jsonObject.getString("AppModuleID");
                        String AppsublnksID=jsonObject.getString("AppsublnksID");
                        String ModuleImg=jsonObject.getString("ModuleImg");
                        String ModuleName=jsonObject.getString("ModuleName");
                        String WhichApp=jsonObject.getString("WhichApp");
                        String errormessage=jsonObject.getString("errormessage");
                        String subPgname=jsonObject.getString("subPgname");
                        mySqliteDataBase.permissioninsertion(AppModuleID,AppsublnksID,ModuleImg,ModuleName,WhichApp,errormessage,subPgname,flagct);
                        Log.e("inserted","in serted"+subPgname);
                    pDialog.cancel();



                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy", Locale.US);
                Calendar myCalendar = Calendar.getInstance();
                bindclasses(sdf.format(myCalendar.getTime()));
                startActivity(new Intent(LoginActivity.this, MainMenuActivity.class));
                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("per_error123",error+"");
            }
        });
        requestQueue.add(jsonArrayRequest);

        pDialog.cancel();
    }

    private void bindclasses(final String Date) {


        final MySqliteDataBase sqliteDataBase = new MySqliteDataBase(this);

        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String branchid = sf.getString("Branchid", "");
        final String Stuid = sf.getString("empid", "");

        String url = Url_Symbol.url + "oneweektimetable/teacherid/" + Stuid + "/branchid/" + branchid + "/date/" + Date;


        Log.e("homeworkURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                sqliteDataBase.detelettimetable();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("timetableresponse",response+"");
                        Log.e("homeworkURL", jsonObject.get("errormessage") + "");
                        String ermsg = jsonObject.getString("errormessage");
                        if (ermsg.equals("Correct")) {

                            Log.e("timetableresponse1",response+"");
//
//                            userInfos.add(new TimeTableDataModel(jsonObject.getString("ClassNm"), jsonObject.getString("Period_Name"), jsonObject.getString("SubjectName")));

                         sqliteDataBase.inserttimetable(jsonObject.getString("clsstrucid"),jsonObject.getString("ClassNm"),jsonObject.getString("SubjectID_FK"),jsonObject.getString("TeacherID_FK"));
//
//                            classid.add(jsonObject.getString("clsstrucid"));
//                            classname.add(jsonObject.getString("ClassNm"));
//                            clsstrucid.add(jsonObject.getString("clsstrucid"));
//                            SubjectID_FK.add(jsonObject.getString("SubjectID_FK"));
//                            TeacherID_FK.add(jsonObject.getString("TeacherID_FK"));
//                            SessionID.add(jsonObject.getString("SessionID"));
                            Log.e("userinfos1",jsonObject.getString("ClassNm"));
                        } else {

//                            Toast.makeText(LoginActivity.this,jsonObject.getString("errormessage"),Toast.LENGTH_SHORT).show();
                        }
//
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("catch", e + "");
                    }

                }
//
//                ArrayAdapter arrayAdapter=new ArrayAdapter(student_attendance.this,android.R.layout.simple_dropdown_item_1line,classname);
//                spinner.setAdapter(arrayAdapter);
//                pDialog.cancel();

//                for(int i=0;i<classname.size();i++)
//                {
//                    classes[i]=classname.get(i);
//                }


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("volleyerror", error + "");
                Snackbar.make(findViewById(android.R.id.content), error + "", Snackbar.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonArrayRequest);

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce){
            Intent startMain = new Intent(Intent.ACTION_MAIN);
            startMain.addCategory(Intent.CATEGORY_HOME);
            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(startMain);
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce=true;
        Toast.makeText(this, "Press back again to exit the app.", Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        },2000);
    }

//    @Override
//    public void onInAppUpdateError(int code, Throwable error) {
//
//    }

//    @Override
//    public void onInAppUpdateStatus(InAppUpdateStatus status) {
//
//        if (status.isDownloaded()) {
//            View view = getWindow().getDecorView().findViewById(android.R.id.content);
//            Snackbar snackbar = Snackbar.make(view,
//                    "An Update just been Downloaded", Snackbar.LENGTH_INDEFINITE);
//            snackbar.setAction("", new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    inAppUpdateManager.completeUpdate();
//                }
//            });
//            snackbar.show();
//        }
//    }
}

