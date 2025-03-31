package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.GiveHomeworkDataModel;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.Classwiseadapter;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ClasswiseattendanceActivity extends AppCompatActivity {
    ListView customListView;
    TextView textView_date;
    ImageView imageView;
    String clsstrucid;
    Calendar myCalendar;
    MySqliteDataBase mySqliteDataBase;
    DatePickerDialog.OnDateSetListener dateSetListener;
    AlertDialog.Builder builder;
    private ArrayList<GiveHomeworkDataModel> userInfos;
    private Classwiseadapter customListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwiseattendance);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();

        customListView = findViewById(R.id.list_view);
        myCalendar = Calendar.getInstance();
        mySqliteDataBase = new MySqliteDataBase(this);
        ImageView backbutton = findViewById(R.id.backpressed);
//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        userInfos = new ArrayList<>();
        textView_date = findViewById(R.id.tv_homeworkDate);
        imageView = findViewById(R.id.btn_calender);
        builder = new AlertDialog.Builder(this);
        updateLabel();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencalender();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencalender();
            }
        });


    }

    private void opencalender() {
        new DatePickerDialog(ClasswiseattendanceActivity.this, R.style.DialogTheme, dateSetListener, myCalendar
                .get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        customListView.setAdapter(null);
        textView_date.setText(sdf.format(myCalendar.getTime()));
        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().before(cal.getTime()) || myCalendar.getTime().equals(cal.getTime())) {
            if (!Connectivity.isConnected(getApplicationContext())) {
                showdata(textView_date.getText().toString());
                Snackbar.make(findViewById(android.R.id.content), "No Internet", Snackbar.LENGTH_SHORT).show();
            } else {
                getHomeWorkfromdb(textView_date.getText().toString());
            }
        } else {
            builder.setMessage("You have no Permission Attendance on this date..!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(ClasswiseattendanceActivity.this, MainMenuActivity.class));
                            finish();
                        }
                    }).setNegativeButton("Select Date", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();
//          Toast.makeText(Classwork_Activity.this,"No record Found !",Toast.LENGTH_SHORT).show();
        }


//        getDatas();


    }

    private void getHomeWorkfromdb(final String Date){
        final SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        Log.e("datedk",""+Date);//14-Jun-2021

//        final ProgressDialog pDialog = new ProgressDialog(this);

//        pDialog.setTitle("Loading...");
//        pDialog.setMessage("Loading your homework");
//        pDialog.setCancelable(false);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.show();




        String branchid = sf.getString("Branchid", "");
        final String Stuid = sf.getString("empid", "");

        String url = Url_Symbol.url + "onedaytimetable/teacherid/" + Stuid + "/branchid/" + branchid + "/date/" + Date;
        Log.e("homeworkURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(ClasswiseattendanceActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mySqliteDataBase.deletegivehomework(textView_date.getText().toString());
                userInfos.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("timetableresponse", response + "");
                        Log.e("homeworkErrormsg", jsonObject.get("errormessage") + "");
                        String ermsg = jsonObject.getString("errormessage");
                        if (!ermsg.equals("Profile Detail Does Not Match !")) {

                            Log.e("timetableresponse1", response + "");
                            String Branchid = jsonObject.getString("Branchid");
                            String ClassNm = jsonObject.getString("ClassNm");// class name
                            String CreateDate = jsonObject.getString("CreateDate");
                            String DayID_FK = jsonObject.getString("DayID_FK");
                            String FinalDayTimeTable_Id = jsonObject.getString("FinalDayTimeTable_Id");
                            String Fk_gpclsid = jsonObject.getString("Fk_gpclsid");
                            String Period_Name = jsonObject.getString("Period_Name");
                            String ScheduleDate = jsonObject.getString("ScheduleDate");
                            String SessionID = jsonObject.getString("SessionID");
                            String SubjectID_FK = jsonObject.getString("SubjectID_FK");
                            String SubjectMasterID = jsonObject.getString("SubjectMasterID");
                            String SubjectName = jsonObject.getString("SubjectName");// subject name

                            String TeacherID_FK = jsonObject.getString("TeacherID_FK");
                            String TimeTableMID_FK = jsonObject.getString("TimeTableMID_FK");
                            clsstrucid = jsonObject.getString("clsstrucid");
                            Log.e("clsstrucid", clsstrucid);
                            String empname = jsonObject.getString("empname");
                            String id = jsonObject.getString("id");
                           /* SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", MODE_PRIVATE);// new 27nov
                            SharedPreferences.Editor editor = sharedPreferences.edit();// new 27 nov
                            editor.putString("SessionID ", SessionID);// 27nov
                            editor.putString("clsstrucid", clsstrucid);// 27 nov
                            editor.putString("TeacherID_FK", TeacherID_FK);
                            Log.e("cllss", clsstrucid);
                            editor.putString("SubjectID_FK", SubjectID_FK);// 27 nov
                            editor.apply();*/
                            mySqliteDataBase.insertGiveHomeworkDetails(Branchid, ClassNm, CreateDate, DayID_FK, FinalDayTimeTable_Id, Fk_gpclsid, Period_Name, ScheduleDate, TeacherID_FK, SessionID, SubjectID_FK, SubjectMasterID, SubjectName, TimeTableMID_FK, clsstrucid, empname, id);

                            SharedPreferences.Editor ed=sf.edit();
                            ed.putString("dkdate",Date);
                             ed.putString("empname",empname);
                            ed.apply();
//userInfos.add(new GiveHomeworkDataModel(jsonObject.getString("ClassNm"), jsonObject.getString("Period_Name"), jsonObject.getString("SubjectName")));

                        } else {

                            builder.setMessage("You have no Class for this date...!!")
                                    .setCancelable(false)
                                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            startActivity(new Intent(ClasswiseattendanceActivity.this, MainMenuActivity.class));
                                            finish();
                                        }
                                    }).setNegativeButton("Select Date", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            AlertDialog alert = builder.create();
                            alert.show();

//                            Toast.makeText(Classwork_Activity.this,jsonObject.getString("errormessage"),Toast.LENGTH_SHORT).show();
//                            else
//                                Toast.makeText(GiveHomeworkActivity.this, jsonObject.getString("errormessage") + "", Toast.LENGTH_SHORT).show();

                            Log.e("errormsg", jsonObject.getString("errormessage"));
                        }
//
//                        customListAdapter = new GiveHomeworkAdapter(userInfos, getApplicationContext());
//
//                        customListView.setAdapter(customListAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("catch", e + "");
                    }
                    SharedPreferences sftd = getSharedPreferences("Dinesh", MODE_PRIVATE);
                    SharedPreferences.Editor edtt = sftd.edit();
                    edtt.putString("clsssdkid", clsstrucid);// 27 nov
                    Log.e("cllss",""+ clsstrucid);

                }

                showdata(textView_date.getText().toString());

//                pDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mySqliteDataBase.insertHomeworkDetails("1","1","1","1","1");
//                pDialog.hide();
                Log.e("volleyerror", error + "");
                Snackbar.make(findViewById(android.R.id.content), error + "", Snackbar.LENGTH_SHORT).show();
            }
        });
//        requestQueue.add(jsonArrayRequest);
        jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                0,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(jsonArrayRequest);
    }

    private void showdata(String DateofHomeWork) {

        mySqliteDataBase = new MySqliteDataBase(this);

        SharedPreferences sfa = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        final String Stuid = sfa.getString("childrenid", "");
        Cursor cursor = mySqliteDataBase.fetchGiveHomework(DateofHomeWork);
        Log.e("cursorCount", cursor.getCount() + "");
        customListView.setAdapter(null);
        userInfos.clear();

        if (cursor.moveToFirst()) {
            do {
//                Log.e("subject", cursor.getString(1));
                String className = cursor.getString(2);
                String PeriodName = cursor.getString(7);
                String subjectname = cursor.getString(12);
                String empid = cursor.getString(13);
                String subjectid = cursor.getString(11);
                String classid = cursor.getString(15);
                String finaldaytimetableid = cursor.getString(5);

                userInfos.add(new GiveHomeworkDataModel(className, PeriodName, subjectname, empid, subjectid, classid, finaldaytimetableid, textView_date.getText().toString()));

            } while (cursor.moveToNext());
        } else {
            if (!Connectivity.isConnected(getApplicationContext()))
                Snackbar.make(findViewById(android.R.id.content), "No Data .....", Snackbar.LENGTH_LONG).show();



        }

        customListView.setAdapter(null);

        customListAdapter = new Classwiseadapter(userInfos, this);

        customListView.setAdapter(customListAdapter);
        customListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(ClasswiseattendanceActivity.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();


    }
}