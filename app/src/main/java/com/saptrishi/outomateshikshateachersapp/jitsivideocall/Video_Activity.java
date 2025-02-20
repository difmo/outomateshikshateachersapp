package com.saptrishi.outomateshikshateachersapp.jitsivideocall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class Video_Activity extends AppCompatActivity {


    TextView edittext;
    Calendar myCalendar;
    private ArrayList<VideoCallDataModel> userInfos;
    private VideoCallAdapter customListAdapter;
    private ListView customListView;
    MySqliteDataBase mySqliteDataBase;
    List<String> classname = new ArrayList<>();
    List<String> classid = new ArrayList<>();
    List<String> clsstrucid = new ArrayList<>();
    List<String> SubjectID_FK = new ArrayList<>();
    List<String> TeacherID_FK = new ArrayList<>();
    List<String> SessionID = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_);
        customListView = (ListView) findViewById(R.id.list);
        userInfos = new ArrayList<>();
        mySqliteDataBase = new MySqliteDataBase(this);
//        ActionBar actionBar = getSupportActionBar();
//        assert actionBar != null;
//        actionBar.hide();
        LinearLayout backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
//        TextView childName = findViewById(R.id.showchildname);
        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        String childid = sharedPreferences.getString("childrenid", "");
//        childName.setText(sharedPreferences.getString("childName", ""));

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("chk", "0");
        ed.apply();
        edittext = (TextView) findViewById(R.id.homeWorkDate);


        TextView childName = findViewById(R.id.showchildname);
//        childName.setText(sharedPreferences.getString("empname", ""));

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
//                ArrayList<ExpandListGroup> ExpListItems = SetStandardGroups();

//                ExpAdapter = new ExpandListAdapter(HomeWorkActivity.this, ExpListItems);
//                ExpandList.setAdapter(ExpAdapter);
//                ExpAdapter.notifyDataSetChanged();
            }

        };
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(Video_Activity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
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
                new DatePickerDialog(Video_Activity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        customListView=(ListView)findViewById(R.id.custom_list_view);
//        userInfos=new ArrayList<>();
//        customListAdapter=new HomeWorkCustomListAdapter(userInfos,this);
//        customListView.setAdapter(customListAdapter);

    }

    private void updateLabel() {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        customListView.setAdapter(null);
        edittext.setText(sdf.format(myCalendar.getTime()));


        if (!Connectivity.isConnected(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "No Internet Connection ", Snackbar.LENGTH_SHORT).show();
        } else {
            getHomeWorkfromdb(edittext.getText().toString());

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


        mySqliteDataBase = new MySqliteDataBase(this);

        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String branchid = sf.getString("Branchid", "");
        final String Stuid = sf.getString("empid", "");

        String url = Url_Symbol.url + "oneweektimetable/teacherid/" + Stuid + "/branchid/" + branchid + "/date/" + Date;
//        String url="http://shikshaappservice.outomate.com/ShikshaAppService.svc/stu_homework/stuid/155/brid/7/hwdate/30-07-2019";

//        oneweektimetable/teacherid/{teacherid}/branchid/{branchid}/date/{date}
        Log.e("homeworkURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(Video_Activity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
//                mySqliteDataBase.deleteHomework(Date,Stuid);
                userInfos.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("timetableresponse", response + "");
                        Log.e("homeworkURL", jsonObject.get("errormessage") + "");
                        String ermsg = jsonObject.getString("errormessage");
                        if (ermsg.equals("Correct")) {

                            Log.e("timetableresponse1", response + "");
//
                            userInfos.add(new VideoCallDataModel(jsonObject.getString("ClassNm"), jsonObject.getString("Period_Name"), jsonObject.getString("SubjectName")));
                            classid.add(jsonObject.getString("clsstrucid"));
                            classname.add(jsonObject.getString("ClassNm"));
                            clsstrucid.add(jsonObject.getString("clsstrucid"));
                            SubjectID_FK.add(jsonObject.getString("SubjectID_FK"));
                            TeacherID_FK.add(jsonObject.getString("TeacherID_FK"));
                            SessionID.add(jsonObject.getString("SessionID"));
                            Log.e("userinfos1", jsonObject.getString("ClassNm"));
                        } else {

                            Toast.makeText(Video_Activity.this, "Time Table Not Found", Toast.LENGTH_SHORT).show();     //Toast.makeText(SeeTimeTableActivity.this,jsonObject.getString("errormessage"),Toast.LENGTH_SHORT).show(); new toast
                        }

                        customListAdapter = new VideoCallAdapter(userInfos, getApplicationContext());

                        customListView.setAdapter(customListAdapter);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("catch", e + "");
                    }

                }

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
        requestQueue.add(jsonArrayRequest);

    }


    @Override
    public void onBackPressed() {

        startActivity(new Intent(Video_Activity.this, MainMenuActivity.class));
        finish();
    }

}
