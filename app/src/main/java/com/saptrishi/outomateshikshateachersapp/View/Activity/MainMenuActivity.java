package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.ContextCompat;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.util.Log;
import android.view.View;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.View.Fragments.ChildProfileFragment;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.GridAdapter;
import com.saptrishi.outomateshikshateachersapp.dasdhboard;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainMenuActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ChildProfileFragment.OnFragmentInteractionListener {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private static final int STORAGE_PERMISSION_CODE = 100;
    private static final int CAMERA_PERMISSION_CODE = 101;
    //    LinearLayout notice, attendance;
    TextView instituteName, childName, childClass;
//    TextView tv_versionnames;
    MySqliteDataBase mySqliteDataBase;
    //    ChildviewDialogFragment dialogFragment;
    FragmentTransaction ftrans;
    SharedPreferences sf;
    NavigationView navigationView;
    public static Activity activity;
    ArrayList<String> basicFields;
    GridAdapter adapter;
    GridView gridView;
    Button btn_attandance;
//    ReviewManager manager;
//    ReviewInfo reviewInfo;
    Context context;
    Resources resources;
    TextView dialog_language, Myprofiles ;
String selectedlanguage = "english";
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

//        manager = ReviewManagerFactory.create(MainMenuActivity.this);
//        Task<ReviewInfo>request = manager.requestReviewFlow();
//        request.addOnCompleteListener(new OnCompleteListener<ReviewInfo>() {
//            @Override
//            public void onComplete(@NonNull Task<ReviewInfo> task) {
//                if (task.isSuccessful()){
//                    reviewInfo = task.getResult();
//                    Task<Void>flow = manager.launchReviewFlow(MainMenuActivity.this,reviewInfo);
//                    flow.addOnSuccessListener(new OnSuccessListener<Void>() {
//                        @Override
//                        public void onSuccess(Void result) {
//
//                        }
//                    });
//                } else {
//                    Toast.makeText(MainMenuActivity.this, "", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        dasdhboard dasdhboard = new dasdhboard();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentManager.popBackStack();
        fragmentTransaction.replace(R.id.dash_rl, dasdhboard).commit();


//        btn_attandance=findViewById(R.id.btn_attandence);
//        btn_attandance.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(MainMenuActivity.this, "ads", Toast.LENGTH_SHORT).show();
//            }
//        });


        basicFields = new ArrayList<>();
        activity = this;

        // Check permissions
        checkPermission(Manifest.permission.CAMERA, CAMERA_PERMISSION_CODE);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        gridView = (GridView) findViewById(R.id.grid);
        basicFields.add("ATTENDANCE");
        basicFields.add("NOTICE");
        basicFields.add("HOMEWORK");
        basicFields.add("TIME TABLE");
        adapter = new GridAdapter(this, basicFields);
        gridView.setAdapter(adapter);
        mySqliteDataBase = new MySqliteDataBase(this);
        sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        if (Connectivity.isConnected(getApplicationContext())) {
//            sendDataTodb();
//            onDataConnected();
            new back().execute();
        }
//        childName = findViewById(R.id.showchildname);
//        childName.setText(sf.getString("empname",""));


//        childName.setText(sf.getString("empname", ""));
        childClass = findViewById(R.id.showChildClass);
        navigationView = findViewById(R.id.nav_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        instituteName = findViewById(R.id.instituteName);
         Myprofiles = findViewById(R.id.myProfile);


     //
        settingValuestoViews();

        ImageView imageView = findViewById(R.id.collegeImage);
        Picasso.get().load(sf.getString("schoolurl", "")).fit()
                .placeholder(R.drawable.shikshalogo)
                .error(R.drawable.shikshalogo)
                .into(imageView);

//
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tvstudentName);
        TextView navUserclass = headerView.findViewById(R.id.tvstudentDetails);
        Cursor cursor = mySqliteDataBase.fetchMasterTable();
        cursor.moveToFirst();
        String name = cursor.getString(11);
        String staffid = cursor.getString(5);
        navUsername.setText(name);
        navUserclass.setText(staffid);


        String chk = sf.getString("chk", "");
        String lang = sf.getString("hindi","");
        Log.e("shantanukumarsingh","data send"+lang );

//        if (chk.equals("1")) {
//            dialogFragment = new ChildviewDialogFragment();
//
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("notAlertDialog", true);
//
//            dialogFragment.setArguments(bundle);
//
//            dialogFragment.setCancelable(false);
//
//            ftrans = getSupportFragmentManager().beginTransaction();
//
//            ftrans.addToBackStack(null);
//
//            dialogFragment.show(ftrans, "dialog");
//
//        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        // on selecting notice menu from Dash Board...........
//        {
//            notice.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                    if (Connectivity.isConnected(getApplicationContext())) {
//                        sendDataTodb();
//                        final ProgressDialog pDialog1 = new ProgressDialog(MainMenuActivity.this);
//                        pDialog1.setTitle("Loading...");
//                        pDialog1.setMessage("Loading your Notice");
//                        pDialog1.setCancelable(false);
//                        pDialog1.setCanceledOnTouchOutside(false);
//                        pDialog1.show();
//                        Thread thread = new Thread() {
//                            public void run() {
//                                try {
//
//                                    sleep(3000);
//                                } catch (InterruptedException e) {
//                                    e.printStackTrace();
//                                } finally {
//
//                                    MainMenuActivity.this.runOnUiThread(new Runnable() {
//                                        @Override
//                                        public void run() {
//                                            pDialog1.hide();
//                                            String Stuid=sf.getString("childrenid","");
//                                            Cursor c=mySqliteDataBase.fetchNotice(Stuid);
//                                            if (c.moveToFirst()) {
//                                                startActivity(new Intent(MainMenuActivity.this, NoticeActivity.class));
//                                                finish();
//                                            } else {
//                                                Log.e("OnNoiceselecMainMenuAct", "NO Notice");
//                                                Toast.makeText(MainMenuActivity.this, "try again later right now we have no Notice", Toast.LENGTH_SHORT).show();
//                                            }
//                                        }
//                                    });
//
//                                }
//                            }
//
//
//                        };
//
//
//                        thread.start();
//
//                    }
//                    else {
//                        String Stuid=sf.getString("childrenid","");
//                        Cursor c=mySqliteDataBase.fetchNotice(Stuid);
//                        if (c.moveToFirst()) {
//                            startActivity(new Intent(MainMenuActivity.this, NoticeActivity.class));
//                            finish();
//                        } else {
//                            Log.e("OnNoiceselecMainMenuAct", "NO Notice");
//                            Toast.makeText(MainMenuActivity.this, "try again later right now we have no Notice", Toast.LENGTH_SHORT).show();
//                        }
//                    }
////                    Cursor c = mySqliteDataBase.fetchNotice();
////                    if (c.moveToFirst()) {
////                        startActivity(new Intent(MainMenuActivity.this, NoticeActivity.class));
////                        finish();
////                    } else {
////                        Log.e("OnNoiceselecMainMenuAct", "NO Notice");
////                        Toast.makeText(MainMenuActivity.this, "try again later right now we have no Notice", Toast.LENGTH_SHORT).show();
////                    }
//                }
//            });
//        }
        // on selecting attendance menu from Dash Board...........

//        {
//            attendance.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String childid = sf.getString("childrenid", "");
//                    Log.e("childrenIdmmenuacti", childid);
//
//                    Cursor c = mySqliteDataBase.fetchAttendance(childid);
//                    if (c.moveToFirst()) {
//                        startActivity(new Intent(MainMenuActivity.this, AttendanceActivity.class));
//                        finish();
//                    } else {
//                        Toast.makeText(MainMenuActivity.this, "try again later right now we have no Attendance.", Toast.LENGTH_SHORT).show();
//                    }
//
//
//                }
//            });
//
//            LinearLayout home = (LinearLayout) findViewById(R.id.homework);
//            home.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(MainMenuActivity.this, HomeWorkActivity.class));
//                    finish();
//                }
//            });
//
//        }



    }

    // Check permission function
    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(MainMenuActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {
            // Request the permission
            ActivityCompat.requestPermissions(MainMenuActivity.this,
                    new String[]{permission},
                    requestCode);
        } else {
//            Toast.makeText(this, permission + " already granted!", Toast.LENGTH_SHORT).show();
        }
    }



    public void sendDataTodb() {

        // String url="http://192.168.0.117/ShikshaApp_service/ShikshaAppService.svc/Stu_Sendsms/sms_maxid/0/brid/1";

        String branchid = sf.getString("Branchid", "");
        String Stuid = sf.getString("empid", "");


//        final ProgressDialog pDialog = new ProgressDialog(this);
//
//        pDialog.setTitle("Loading...");
//        pDialog.setMessage("Loading your Notice");
//        pDialog.setCancelable(false);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.show();
        String childid = sf.getString("empid", "");
        Cursor cursor = mySqliteDataBase.fetchMaxNoticeID(childid);
        int maxid;
        if (cursor.moveToFirst()) {
            maxid = cursor.getInt(0);
        } else
            maxid = 0;

        Log.e("loginURL", maxid + "");
//        String url = Url_Symbol.url + "Stu_Sendsms/sms_maxid/" + maxid + "/brid/" + branchid;
        String url = Url_Symbol.url + "Stu_Sendsms/sms_maxid/" + maxid + "/brid/" + branchid + "/Stuid/" + Stuid;
        Log.e("loginURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onResponse(JSONArray response) {
                Log.e("check", response + "");
                try {
//                    mySqliteDataBase.deleteNotice();
                    int i;
                    for (i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String errormessage = jsonObject.getString("errormessage");
                        if (errormessage.equals("Correct")) {
                            String SmsContent = jsonObject.getString("SmsContent");
                            String msgdatetime = jsonObject.getString("msgdatetime");
                            String Stuid = jsonObject.getString("Stuid");
                            int smsid = Integer.parseInt(jsonObject.getString("smsid"));
                            mySqliteDataBase.insertchildNotice(SmsContent, msgdatetime, smsid, Stuid);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e("LoginActivitycatch", e + "");
                }
                Thread thread = new Thread() {
                    public void run() {
                        try {
                            sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                };
                thread.start();
//                pDialog.hide();

            }
        }, new Response.ErrorListener() {

            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onErrorResponse(VolleyError error) {

//                pDialog.hide();
                Toast.makeText(MainMenuActivity.this, error + "", Toast.LENGTH_SHORT).show();
                Log.e("LoginActivityerror", error + "");
            }


        });
        requestQueue.add(jsonArrayRequest);


    }


//     Inflate the menu; this adds items to the action bar if it is present.
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.main_menu, menu);
//        return true;
//    }

    //LOG OUT ka code...............................
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//
//
//        int id = item.getItemId();
//
//
//        if (id == R.id.logout) {
//
//            SharedPreferences.Editor editor = sf.edit();
//            editor.remove("parentid");
//            editor.remove("password");
//            editor.clear();
//            editor.apply();
//
//            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
//            finish();
//
//            return true;
//        }
//        if (id == R.id.rateme) {
//            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
//            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//            // To count with Play market backstack, After pressing back button,
//            // to taken back to our application, we need to add following flags to intent.
//            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
//                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
//                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
//            try {
//                startActivity(goToMarket);
//            } catch (ActivityNotFoundException e) {
//                startActivity(new Intent(Intent.ACTION_VIEW,
//                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
//            }
//
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
    // Handle the permission result
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            switch (requestCode) {
                case CAMERA_PERMISSION_CODE:
//                    Toast.makeText(this, "Camera Permission Granted", Toast.LENGTH_SHORT).show();
                    break;

                case STORAGE_PERMISSION_CODE:
//                    Toast.makeText(this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
                    break;
            }
        } else {
            switch (requestCode) {
                case CAMERA_PERMISSION_CODE:
//                    Toast.makeText(this, "Camera Permission Denied", Toast.LENGTH_SHORT).show();
                    break;

                case STORAGE_PERMISSION_CODE:
//                    Toast.makeText(this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
//                    Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//                    Uri uri = Uri.fromParts("package", getPackageName(), null);
//                    intent.setData(uri);
//                    startActivity(intent);
                    break;
            }
        }
    }

    public void onBackPressed() {

//        android.app.FragmentManager fm = getFragmentManager();
//        FrameLayout frameLayout = findViewById(R.id.dashBoardContainer);
//        frameLayout.setVisibility(View.GONE);
//        if (fm.getBackStackEntryCount() > 0) {
//            fm.popBackStack();
//        } else {
//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
//            if (drawer.isDrawerOpen(GravityCompat.START)) {
//                drawer.closeDrawer(GravityCompat.START);
//            } else
//                super.onBackPressed();
//        }
        super.onBackPressed();
        ChildProfileFragment childProfile = (ChildProfileFragment) getSupportFragmentManager().findFragmentByTag("CHILD_PROFILE");
        if (childProfile != null && childProfile.isVisible()) {
            startActivity(new Intent(this, MainMenuActivity.class));
            this.overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);

        }else {

        AlertDialog.Builder builder
                = new AlertDialog
                .Builder(MainMenuActivity.this);

        // Set the message show for the Alert time
        builder.setMessage(R.string.exit);

        // Set Alert Title
        builder.setTitle(R.string.alert);

        // Set Cancelable false
        // for when the user clicks on the outside
        // the Dialog Box then it will remain show
        builder.setCancelable(false);

        // Set the positive button with yes name
        // OnClickListener method is use of
        // DialogInterface interface.

        builder
                .setPositiveButton(
                        R.string.yes,
                        new DialogInterface
                                .OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {



                                // When the user click yes button
                                // then app will close
                                Intent intent = new Intent(MainMenuActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        }).setNegativeButton(
                R.string.no,
                new DialogInterface
                        .OnClickListener() {

                    @Override
                    public void onClick(DialogInterface
                                                dialog,
                                        int which) {

                        // If user click no
                        // then dialog box is canceled.
                        dialog.cancel();
                    }
                });

        // Create the Alert dialog
        AlertDialog alertDialog = builder.create();

        // Show the Alert Dialog box
        alertDialog.show();
//        if (getFragmentManager().getBackStackEntryCount() == 0) {
//            this.finish();
//        } else {
//            getFragmentManager().popBackStack();
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else if (getFragmentManager().getBackStackEntryCount() > 0) {
//            getFragmentManager().popBackStack();
//        } else {
//            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
//            finish();
//        }
        }
    }

    //setting values......... on navigation BAR and
    public void settingValuestoViews() {
//        childName.setText(sf.getString("childName", " "));
//        childClass.setText(sf.getString("childClass", " "));
//        View headerView = navigationView.getHeaderView(0);
//        navigationView.setNavigationItemSelectedListener(this);
//        TextView navUsername = headerView.findViewById(R.id.tvstudentName);
//        TextView navUserclass = headerView.findViewById(R.id.tvstudentDetails);
//        navUsername.setText(sf.getString("childName", ""));
//        navUserclass.setText(sf.getString("childClass", " "));


        Cursor c = mySqliteDataBase.fetchMasterTable();
        c.moveToFirst();
        String branchName = c.getString(3);
        instituteName.setText(branchName);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item)
    {

        Log.e("onNAvigationItemSelec", item + "");

        int id = item.getItemId();

        if (id == R.id.myProfile) {

            FrameLayout frameLayout = findViewById(R.id.dashBoardContainer);
            frameLayout.setVisibility(View.VISIBLE);

            ChildProfileFragment childProfileFragment = new ChildProfileFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.dashBoardContainer, childProfileFragment,"CHILD_PROFILE").addToBackStack(null).commit();


            DrawerLayout drawer = findViewById(R.id.drawer_layout);
           drawer.closeDrawer(GravityCompat.START);
           return true;

        }
        else if (id == R.id.logout) {

            SharedPreferences.Editor editor = sf.edit();
//            editor.remove("parentid");
//            editor.remove("password");
//            editor.clear();
            editor.putInt("key", 0);
            editor.apply();

            startActivity(new Intent(MainMenuActivity.this, LoginActivity.class));
            finish();

            return true;
        }
        else if (id == R.id.rateme) {
            Uri uri = Uri.parse("market://details?id=" + this.getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            // To count with Play market backstack, After pressing back button,
            // to taken back to our application, we need to add following flags to intent.
            goToMarket.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY |
                    Intent.FLAG_ACTIVITY_NEW_DOCUMENT |
                    Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
            try {
                startActivity(goToMarket);
            } catch (ActivityNotFoundException e) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("http://play.google.com/store/apps/details?id=" + this.getPackageName())));
            }


        }

        return super.onOptionsItemSelected(item);



            //            fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).addToBackStack(null).commit();

//            Intent intent = new Intent(this, ViewChildActivity.class);
//
//            startActivity(intent);
//            finish();


//            dialogFragment = new ChildviewDialogFragment();
//            Bundle bundle = new Bundle();
//            bundle.putBoolean("notAlertDialog", true);
//            dialogFragment.setArguments(bundle);
//            dialogFragment.setCancelable(false);
//            ftrans = getSupportFragmentManager().beginTransaction();
//            ftrans.addToBackStack(null);
//            dialogFragment.show(ftrans, "dialog");
//            SharedPreferences.Editor ed = sf.edit();
//            ed.putString("chk", "1");
//            ed.apply();
//            startActivity(new Intent(MainMenuActivity.this, MainMenuActivity.class));
//            finish();

//        else if (id==R.id.backButtonclose)
//        {
//            Log.e("backPressed","BackPressed");
//            DrawerLayout drawer = findViewById(R.id.drawer_layout);
//            drawer.closeDrawer(GravityCompat.START);
//
//        }


//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
    }



//    private class DownloadImageFromInternet extends AsyncTask<String, Void, Bitmap> {
//        ImageView imageView;
//
//        public DownloadImageFromInternet(ImageView imageView) {
//            this.imageView = imageView;
//           // Toast.makeText(getApplicationContext(), "Please wait, it may take a few minute...", Toast.LENGTH_SHORT).show();
//        }
//
//        protected Bitmap doInBackground(String... urls) {
//            String imageURL = urls[0];
//            Bitmap bimage = null;
//            try {
//                InputStream in = new java.net.URL(imageURL).openStream();
//                bimage = BitmapFactory.decodeStream(in);
//
//            } catch (Exception e) {
//                Log.e("Error Message", e.getMessage());
//                e.printStackTrace();
//            }
//            return bimage;
//        }
//
//        protected void onPostExecute(Bitmap result) {
//
//            Log.e("imageresult",result+"");
//            if ((result != null))
//            imageView.setImageBitmap(result);
//        }
//    }


    public void onDataConnected() {

        String branchid = sf.getString("Branchid", "");
        final String empid = sf.getString("empid", "");

        // Attendence/empid/{empid}/branchid/{branchid}

        String url = Url_Symbol.url + "Attendence/empid/" + empid + "/branchid/" + branchid;

        Log.e("urlattendancetofetch", url);

        final RequestQueue rq = Volley.newRequestQueue(this);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.e("urlattendance", response + "");
                    mySqliteDataBase.deletechildAttendance();
                    int i;
                    for (i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("PunchDate", "" + jsonObject.getString("PunchDate"));
                        Log.e("PunchTime", "" + jsonObject.getString("PunchTime"));
//                        Log.e("StuAtt_id", "" + jsonObject.getString("StuAtt_id"));
                        Log.e("studentAtterrmsg", "" + jsonObject.getString("errormessage"));
                        Log.e("In_OutStatus", "" + jsonObject.getString("In_OutStatus"));
//                        Log.e("studentID", "" + jsonObject.getString("studentid"));
                        String AttStatus = jsonObject.getString("AttStatus");
                        Log.e("AttStatus", "" + jsonObject.getString("AttStatus"));
                        String PunchDate = jsonObject.getString("PunchDate");
                        String PunchTime = jsonObject.getString("PunchTime");
                        String StuAtt_id = jsonObject.getString("UserRefId");
                        String In_OutStatus = jsonObject.getString("In_OutStatus");
                        String UserRefId = jsonObject.getString("UserRefId");
                        String Todate = jsonObject.getString("Todate");
//                        SharedPreferences.Editor ed=sf.edit();
//                        ed.putString("childid",studentID);
//                        ed.apply();

                        if (jsonObject.getString("errormessage").equals("Correct")) {
//                            mySqliteDataBase.insertchildAttendance(attendanceDate, attendanceTime, UserRefId, inOutStatus, UserRefId);
                            mySqliteDataBase.insertchildAttendance(PunchDate, PunchTime, StuAtt_id, In_OutStatus, UserRefId, AttStatus, Todate);
                        } else {
                            //    Toast.makeText(MainMenuActivity.this,jsonObject.getString("errormessage")+"",Toast.LENGTH_SHORT).show(); Toast login activity
                            //Toast.makeText(Objects.requireNonNull(getActivity()).getApplicationContext(), jsonObject.getString("errormessage")+"", Toast.LENGTH_SHORT).show();
                        }

//                    }
//                    Cursor c=mySqliteDataBase.fetchMasterTable();
//                    Cursor cursor=mySqliteDataBase.fetchAttendance(empid);
//
////                    Cursor cursorNotice=mySqliteDataBase.fetchNotice();
//                    String Stuid=sf.getString("childrenid","");
//                    Cursor cursorNotice=mySqliteDataBase.fetchNotice(Stuid);
//
//
//                    MainMenuActivity mainMenuActivity=new MainMenuActivity();
////                    mainMenuActivity.sendDataTodb();
//                    Log.e("r1",response.length()+" plus"+i);
//                    Log.e("r2",c.moveToFirst()+"");
//                    Log.e("r3",cursor.moveToFirst()+"");
//                    Log.e("r4",cursorNotice.moveToFirst()+"");
//
//                    if (i==response.length() && c.moveToFirst() && (cursor.moveToFirst() || cursorNotice.moveToFirst()))
//                    {
//
//                        dismiss();
//
//                    }
//                    else
//                    {
//                        dismiss();
//                        Toast.makeText(getActivity().getApplicationContext(), "No Data try again later :)", Toast.LENGTH_SHORT).show();
////                        dismiss();
//                    }
                    }

                } catch (JSONException e) {

                    Toast.makeText(MainMenuActivity.this, e + "", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                //     Toast.makeText(MainMenuActivity.this, error+"", Toast.LENGTH_SHORT).show(); login activity toast
                Log.e("urlattendance", error + "");

            }
        });
//        rq.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        rq.add(jsonArrayRequest);
    }

// This function is called when the user accepts or decline the permission.
    // Request Code is used to check which permission called this function.
    // This request code is provided when the user is prompt for permission.


    class back extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {
            sendDataTodb();
            onDataConnected();
            return null;

        }
    }

    public void navigateToAttendance() {
        Intent intent = new Intent(this, AttendanceActivity.class);
        startActivity(intent);
    }

}


