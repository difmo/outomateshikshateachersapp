package com.saptrishi.outomateshikshateachersapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import com.android.volley.TimeoutError;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class GiveHomeworkActivity extends AppCompatActivity {

    TextView edittext;
    TextView tv_versionnames;
    Calendar myCalendar;
    private ArrayList<GiveHomeworkDataModel> userInfos;
    private GiveHomeworkAdapter customListAdapter;
    private ListView customListView;
    MySqliteDataBase mySqliteDataBase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give_homework);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        customListView = (ListView) findViewById(R.id.list);
        userInfos = new ArrayList<>();
        mySqliteDataBase = new MySqliteDataBase(this);
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.hide();
        LinearLayout backbutton = findViewById(R.id.backpressed);
//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
//        TextView childName = findViewById(R.id.showchildname);
        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        String childid = sharedPreferences.getString("childrenid", "");
//        childName.setText(sharedPreferences.getString("childName", ""));

        TextView childName = findViewById(R.id.showchildname);
//        childName.setText(sharedPreferences.getString("empname", ""));
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("chk", "0");
        ed.apply();
        edittext = (TextView) findViewById(R.id.homeWorkDate);
//        ExpandList = (ExpandableListView) findViewById(R.id.expandableListView);
        ImageView search = findViewById(R.id.btnsearch);
//        search.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new DatePickerDialog(HomeWorkActivity.this,R.style.DialogTheme,date,myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

//        ExpandList.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
//            @Override
//            public boolean onChildClick(ExpandableListView parent, View v,
//                                        int groupPosition, int childPosition, long id) {
//
//                ExpandListChild child= (ExpandListChild) ExpAdapter.getChild(groupPosition,childPosition);
//                final String selected = child.getName();
//                Toast.makeText(
//                        getApplicationContext(),
//
//                        selected+" :  "+":"
//                                , Toast.LENGTH_SHORT).show();
//                return false;
//            }
//        });
        myCalendar = Calendar.getInstance();

        updateLabel();
//        customListView.setAdapter(customListAdapter);
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
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
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(GiveHomeworkActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                         new DatePickerDialog(HomeWorkActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new DatePickerDialog(GiveHomeworkActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        customListView=(ListView)findViewById(R.id.custom_list_view);
//        userInfos=new ArrayList<>();
//        customListAdapter=new HomeWorkCustomListAdapter(userInfos,this);
//        customListView.setAdapter(customListAdapter);


    }

    // getting all the datas
//    private void getDatas() {
////        for(int count=0;count<names.length;count++){
//        mySqliteDataBase = new MySqliteDataBase(this);
//
//        SharedPreferences sfa = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        final String Stuid = sfa.getString("childrenid", "");
//        Cursor cursor = mySqliteDataBase.fetchHomeworkTable(edittext.getText().toString(),Stuid);
//        Log.e("cursorCount", cursor.getCount()+"");
//        customListView.setAdapter(null);
//        userInfos.clear();
//        if (cursor.moveToFirst()) {
//            do {
//                Log.e("subject", cursor.getString(1));
//                String Subject = cursor.getString(0);
//                String DueDate = cursor.getString(1);
//                String subjectId=cursor.getString(2);
//                userInfos.add(new UserInfo(Subject, DueDate, photos[0],subjectId));
//            } while (cursor.moveToNext());
//        } else
//        {
//            if (!Connectivity.isConnected(getApplicationContext()))
//                Snackbar.make(findViewById(android.R.id.content), "No Data .... Try again with internet on", Snackbar.LENGTH_LONG).show();
//
//            Log.e("subject", "nothing to show");
//
//        }
//
//        customListView.setAdapter(null);
//
//        customListAdapter = new HomeWorkCustomListAdapter(userInfos, this,edittext.getText().toString(),Stuid);
//        customListView.setAdapter(null);
//        customListAdapter.notifyDataSetChanged();
//        customListView.setAdapter(customListAdapter);
//        customListAdapter.notifyDataSetChanged();
////        }
//    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        customListView.setAdapter(null);
        edittext.setText(sdf.format(myCalendar.getTime()));
        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().before(cal.getTime()) || myCalendar.getTime().equals(cal.getTime())) {
            if (!Connectivity.isConnected(getApplicationContext())) {
                showdata(edittext.getText().toString());
                Snackbar.make(findViewById(android.R.id.content), "No Internet ", Snackbar.LENGTH_SHORT).show();
            } else {
                getHomeWorkfromdb(edittext.getText().toString());
            }
        } else {
          Toast toast =  Toast.makeText(GiveHomeworkActivity.this, "No record Found !", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    private void getHomeWorkfromdb(final String Date) {

//        final ProgressDialog pDialog = new ProgressDialog(this);
//
//        pDialog.setTitle("Loading...");
//        pDialog.setMessage("Loading your homework");
//        pDialog.setCancelable(false);
//        pDialog.setCanceledOnTouchOutside(false);
//        pDialog.show();


        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String branchid = sf.getString("Branchid", "");
        final String Stuid = sf.getString("empid", "");


        String url = Url_Symbol.url + "onedaytimetable/teacherid/" + Stuid + "/branchid/" + branchid + "/date/" + Date;

//        http://teacherappservice.outomate.com/TeacherAppService.svc/onedaytimetable/teacherid/126/branchid/7/date/07-aug-2019

//        String url="http://shikshaappservice.outomate.com/ShikshaAppService.svc/oneweektimetable/teacherid/126/branchid/7/date/07-aug-2019";

//        oneweektimetable/teacherid/{teacherid}/branchid/{branchid}/date/{date}
        Log.e("homeworkURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(GiveHomeworkActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mySqliteDataBase.deletegivehomework(edittext.getText().toString());
                userInfos.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("timetableresponse", response + "");
                        Log.e("homeworkErrormsg", jsonObject.get("errormessage") + "");
                        String ermsg = jsonObject.getString("errormessage");
                        if (ermsg.equals("Correct")) {

                            Log.e("timetableresponse1", response + "");
                            String Branchid = jsonObject.getString("Branchid");
                            String ClassNm = jsonObject.getString("ClassNm");
                            String CreateDate = jsonObject.getString("CreateDate");
                            String DayID_FK = jsonObject.getString("DayID_FK");
                            String FinalDayTimeTable_Id = jsonObject.getString("FinalDayTimeTable_Id");
                            String Fk_gpclsid = jsonObject.getString("Fk_gpclsid");
                            String Period_Name = jsonObject.getString("Period_Name");
                            String ScheduleDate = jsonObject.getString("ScheduleDate");
                            String SessionID = jsonObject.getString("SessionID");
                            String SubjectID_FK = jsonObject.getString("SubjectID_FK");
                            String SubjectMasterID = jsonObject.getString("SubjectMasterID");
                            String SubjectName = jsonObject.getString("SubjectName");
                            String TeacherID_FK = jsonObject.getString("TeacherID_FK");
                            String TimeTableMID_FK = jsonObject.getString("TimeTableMID_FK");
                            String clsstrucid = jsonObject.getString("clsstrucid");
                            String empname = jsonObject.getString("empname");
                            String id = jsonObject.getString("id");

                            mySqliteDataBase.insertGiveHomeworkDetails(Branchid, ClassNm, CreateDate, DayID_FK, FinalDayTimeTable_Id, Fk_gpclsid, Period_Name, ScheduleDate, TeacherID_FK, SessionID, SubjectID_FK, SubjectMasterID, SubjectName, TimeTableMID_FK, clsstrucid, empname, id);

                            SharedPreferences sharedPreferences = getSharedPreferences("tag", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("sessionid", SessionID);
                            editor.putString("empname", empname);
                            editor.putString("clsstrucid", clsstrucid);
                            editor.putString("ClassNm", ClassNm);
                            editor.putString("TeacherID_FK", TeacherID_FK);
                            editor.putString("SubjectID_FK", SubjectID_FK);
                            editor.putString("SubjectMasterID", SubjectMasterID);
                            editor.putString("clsstrucid", clsstrucid);
                            editor.putString("SubjectName", SubjectName);
                            editor.putString("Branchid",Branchid);
                            editor.putString("CreateDate",CreateDate);
                            editor.putString("FinalDayTimeTable_Id",FinalDayTimeTable_Id);
                            editor.apply();

//userInfos.add(new GiveHomeworkDataModel(jsonObject.getString("ClassNm"), jsonObject.getString("Period_Name"), jsonObject.getString("SubjectName")));

                        } else {


                            Toast.makeText(GiveHomeworkActivity.this, jsonObject.getString("errormessage"), Toast.LENGTH_SHORT).show();
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

                }
                showdata(edittext.getText().toString());

//                pDialog.hide();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                mySqliteDataBase.insertHomeworkDetails("1","1","1","1","1");
//                pDialog.hide();
                {
                    if (error.getClass().equals(TimeoutError.class)) {
                        // Show timeout error message
                        Toast toast =Toast.makeText(GiveHomeworkActivity.this, "Try Again!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

//                Log.e("volleyerror", error + "");
                    Snackbar.make(findViewById(android.R.id.content), error + "", Snackbar.LENGTH_SHORT).show();
                }
//                Log.e("volleyerror", error + "");
//                Snackbar.make(findViewById(android.R.id.content), error + "", Snackbar.LENGTH_SHORT).show();
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

                userInfos.add(new GiveHomeworkDataModel(className, PeriodName, subjectname, empid, subjectid, classid, finaldaytimetableid, edittext.getText().toString()));

            } while (cursor.moveToNext());
        } else {
            if (!Connectivity.isConnected(getApplicationContext()))
                Snackbar.make(findViewById(android.R.id.content), "No Data .....", Snackbar.LENGTH_LONG).show();

            Log.e("subject", "nothing to show");

        }

        customListView.setAdapter(null);

        customListAdapter = new GiveHomeworkAdapter(userInfos, this);

        customListView.setAdapter(customListAdapter);
        customListAdapter.notifyDataSetChanged();

    }


    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(GiveHomeworkActivity.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();

    }

}