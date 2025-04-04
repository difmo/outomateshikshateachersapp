package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
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
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.SeeTimeTableActivity;
import com.saptrishi.outomateshikshateachersapp.SeeTimeTableAdapter;
import com.saptrishi.outomateshikshateachersapp.TimeTableDataModel;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class video_classwise extends AppCompatActivity {
    TextView edittext;
    TextView tv_versionnames;
    Calendar myCalendar;
    MySqliteDataBase mySqliteDataBase;
    List<String> classname = new ArrayList<>();
    List<String> classid = new ArrayList<>();
    List<String> clsstrucid = new ArrayList<>();
    List<String> SubjectID_FK = new ArrayList<>();
    List<String> TeacherID_FK = new ArrayList<>();
    List<String> SessionID = new ArrayList<>();
    List<String> FrmTime = new ArrayList<>();
    private ArrayList<TimeTableDataModel> userInfos;
    private SeeTimeTableAdapter customListAdapter;
    private ListView customListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_classwise);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
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

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("chk", "0");
        ed.apply();
        edittext = (TextView) findViewById(R.id.homeWorkDate);


        TextView childName = findViewById(R.id.showchildname);

        ImageView search = findViewById(R.id.btnsearch);

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
//
            }

        };
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(video_classwise.this, R.style.DialogTheme, date, myCalendar
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
                new DatePickerDialog(video_classwise.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

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



        mySqliteDataBase = new MySqliteDataBase(this);

        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String branchid = sf.getString("Branchid", "");

        final String Stuid = sf.getString("empid", "");

        String url = Url_Symbol.url + "oneweektimetable/teacherid/" + Stuid + "/branchid/" + branchid + "/date/" + Date;
//        String url="http://shikshaappservice.outomate.com/ShikshaAppService.svc/stu_homework/stuid/155/brid/7/hwdate/30-07-2019";

//        oneweektimetable/teacherid/{teacherid}/branchid/{branchid}/date/{date}
        Log.e("homeworkURL", url);
        RequestQueue requestQueue = Volley.newRequestQueue(video_classwise.this);
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
                            userInfos.add(new TimeTableDataModel(jsonObject.getString("ClassNm"), jsonObject.getString("Period_Name"), jsonObject.getString("SubjectName"), jsonObject.getString("FrmTime"), jsonObject.getString("ToTime"),jsonObject.getString("clsstrucid"),jsonObject.getString("FinalDayTimeTable_Id"),edittext.getText().toString(),jsonObject.getString("SubjectID_FK")));
                            classid.add(jsonObject.getString("clsstrucid"));
                            classname.add(jsonObject.getString("ClassNm"));
                            clsstrucid.add(jsonObject.getString("clsstrucid"));
                            SubjectID_FK.add(jsonObject.getString("SubjectID_FK"));
                            TeacherID_FK.add(jsonObject.getString("TeacherID_FK"));
                            SessionID.add(jsonObject.getString("SessionID"));
                            FrmTime.add(jsonObject.getString("FrmTime"));
//                            Toast.makeText(SeeTimeTableActivity.this, "TEST:"+jsonObject.getString("TeacherID_FK"), Toast.LENGTH_LONG).show();
                            SharedPreferences sharedPreferences = getSharedPreferences("Timetableactivity", MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("clsstrucid", jsonObject.getString("clsstrucid"));
                            Log.e("Shante", jsonObject.getString("clsstrucid"));
                            editor.putString(" ClassNm", jsonObject.getString("ClassNm"));
                            editor.putString(" SubjectID_FK", jsonObject.getString("SubjectID_FK"));
                            editor.putString("TeacherID_FK", jsonObject.getString("TeacherID_FK"));
                            editor.putString("SessionID", jsonObject.getString("SessionID"));
                            editor.putString("FrmTime", jsonObject.getString("FrmTime"));
                            editor.putString("ClassNm",jsonObject.getString("ClassNm"));
                            Log.e("Shantanu", jsonObject.getString("FrmTime"));
                            editor.commit();


                            Log.e("userinfos1", jsonObject.getString("ClassNm"));
                        } else {

                            Toast toast = Toast.makeText(video_classwise.this, "Time Table Not Found", Toast.LENGTH_SHORT);     //Toast.makeText(SeeTimeTableActivity.this,jsonObject.getString("errormessage"),Toast.LENGTH_SHORT).show(); new toast
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();

                        }

                        customListAdapter = new SeeTimeTableAdapter(userInfos, getApplicationContext());

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

        super.onBackPressed();
        startActivity(new Intent(video_classwise.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        finish();
    }

}
