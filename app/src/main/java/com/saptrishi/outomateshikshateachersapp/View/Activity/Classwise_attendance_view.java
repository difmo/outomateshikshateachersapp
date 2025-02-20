package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Connectivity;

import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.Attendanceviewadapter;
import com.saptrishi.outomateshikshateachersapp.model.AttendanceviewModel;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class Classwise_attendance_view extends AppCompatActivity {
    RecyclerView stud_rv;
    Activity activity;
    RequestQueue requestQueue;
    String brid, classid, Attan_date, subjectid;
    List<AttendanceviewModel> attendancemodellist = new ArrayList<>();
    TextView classname1, subject1;
    SharedPreferences sharedPreferences;
    String branchID="";
    ImageView imageView;
    TextView date;
    Calendar myCalendar;
    DatePickerDialog.OnDateSetListener dateSetListener;
    AlertDialog.Builder builder;
    //new
//    List<String> student_name = new ArrayList<>();
//    List<Model> stu_id = new ArrayList<>();
//    List<String> student_id = new ArrayList<String>();
//    List<String> status = new ArrayList<String>();
//    List<String> stu_img = new ArrayList<String>();
//    List<String> student = new ArrayList<>();
//    List<String> classname = new ArrayList<>();
//    //List<String> classid = new ArrayList<>();
//    List<String> clsstrucid = new ArrayList<>();
//    List<String> SubjectID_FK = new ArrayList<>();
//    List<String> TeacherID_FK = new ArrayList<>();
//    List<String> SessionID = new ArrayList<>();

    MySqliteDataBase sqliteDataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classwise_attendance_view);
        activity = this;
//        Button btn_save = findViewById(R.id.btn_save1);
        ImageView backbutton = findViewById(R.id.backpressed);
        classname1 = findViewById(R.id.className);
        subject1 = findViewById(R.id.tv_subjectname);
        myCalendar = Calendar.getInstance();
        builder = new AlertDialog.Builder(this);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        branchID = sharedPreferences.getString("Branchid", ""); // branchid
        classid = getIntent().getStringExtra("classid");
        subjectid = getIntent().getStringExtra("subjectid");//<<<<<
        requestQueue = Volley.newRequestQueue(this);//This will take care of
        date = findViewById(R.id.tv_homeworkDate);//
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        date.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                opencalender();
            }
        });

        imageView = findViewById(R.id.btn_calender);
        stud_rv = findViewById(R.id.recycler_view);
        Intent intent = getIntent();
        String classname = intent.getStringExtra("class_name");
        String subjectname = intent.getStringExtra("subjectName");
        String dateodhomework = intent.getStringExtra("dateodhomework");
        classname1.setText(classname);
        subject1.setText(subjectname);// date u wiil see in classworkview

       /* Date date_new = serv_date(dateodhomework);
        String pattern = "yyyy-MM-dd";
        DateFormat df = new SimpleDateFormat(pattern);
        String todayAsString = df.format(date_new)*/
        ;//hurreeee
//        textView_date.setText(dateodhomework);
        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));

        getdata(branchID, date.getText().toString());

        //new
        sqliteDataBase = new MySqliteDataBase(this);

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        final String currentDate = sdf1.format(date);//new Date()
        Calendar cla = Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cla.getTime();
        Log.e("currentLocalTime", "" + currentLocalTime);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        final String localTime = dateFormat.format(currentLocalTime);


        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        final String branchid = sharedPreferences.getString("Branchid", "");
        // final String subj = sharedPreferences.getString("SubjectID_FK","");
        final String TeacherID_FK = sharedPreferences.getString("TeacherID_FK", "");
        final String sess = sharedPreferences.getString("SessionID ", "");
        final String clsstrucid_send = intent.getStringExtra("classid");// new 5
        final String subj = intent.getStringExtra("subjectid");

//        btn_save.setOnClickListener(new View.OnClickListener()
//        {
//            @Override
//            public void onClick(View view) {
//                Toast.makeText(activity, "Update!", Toast.LENGTH_SHORT).show();
//
//                if (Connectivity.isConnected(Classwise_attendance_view.this)) {
//                    Sending_AttendenceToDB sending_attendenceToDB = new Sending_AttendenceToDB(Classwise_attendance_view.this);
//                    for (int i = 0; i < attendancemodellist.size(); i++) {
//                        SessionID.add(sess);
//
//                        if (attendancemodellist.get(i).getStudentid() != null) {
//                            sending_attendenceToDB.senddatatovolley(clsstrucid_send, attendancemodellist.get(i).getStudentid(),
//                                    subj, attendancemodellist.get(i).getAttan_Status(), TeacherID_FK, currentDate, localTime, branchid, SessionID.get(i));// change 27 nov
//                        } else {//not used
//                            sending_attendenceToDB.senddatatovolley(clsstrucid_send, AttendenceAdapter.st_id.get(i).getStudentID(),
//                                    subj, "A", TeacherID_FK, currentDate, localTime, branchid, SessionID.get(i));// change 27 nov
//                        }
//                    }
//                } else {
//                    for (int i = 0; i < AttendenceAdapter.st_id.size(); i++) {
//                        if (AttendenceAdapter.st_id.get(i).getSelected()) {
//                            Log.e("clsstrucid_send P", clsstrucid_send + "");
//                            Log.e("Unselected12", AttendenceAdapter.st_id.get(i).getStudentID());
//                            Log.e("SubjectID_FK_send", subj + "");
//                            Log.e("Unselected12", "P");
//                            Log.e("TeacherID_FK_send", TeacherID_FK + "");
//                            Log.e("SessionIDA", SessionID + "");
//                            Log.e("currentDate", currentDate);
//                            Log.e("Unselected12", localTime);
//                            Log.e("Unselected12", branchid);
//                            Log.e("Unselected12", sess + "");
//                            sqliteDataBase.attendenceinsertion(clsstrucid_send, AttendenceAdapter.st_id.get(i).getStudentID(),
//                                    subj, "P", TeacherID_FK, currentDate, localTime, branchid, SessionID.get(i));//change 27
//                        } else {
//                            Log.e("clsstrucid_send A", clsstrucid_send + "");
//                            Log.e("Unselected12", AttendenceAdapter.st_id.get(i).getStudentID());
//                            Log.e("SUBJECTp", subj + "");
//                            Log.e("Unselected12", "A");
//                            //Log.e("Unselected12", TeacherID_FK_send + "");
//                            Log.e("currentDate", currentDate);
//                            Log.e("Unselected12", localTime);
//                            Log.e("SessionIDp", SessionID + "");
//                            Log.e("Unselected12", branchid);
//                            Log.e("Unselected12", SessionID.get(i));
//                            sqliteDataBase.attendenceinsertion(clsstrucid_send, AttendenceAdapter.st_id.get(i).getStudentID(),
//                                    subj, "A", TeacherID_FK, currentDate, localTime, branchid, SessionID.get(i)); // change 27 SubjectID_FK_send
//
//                            //  sqliteDataBase.deleteattendancetable();// see added;
//                        }
//                    }
//
//                }
//            }
//        });

    }

    private void updateLabel() {

        String myFormat = "yyyy-MM-dd"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        date.setText(sdf.format(myCalendar.getTime()));
        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().before(cal.getTime()) || myCalendar.getTime().equals(cal.getTime())) {
            if (!Connectivity.isConnected(getApplicationContext())) {
                //showdata(date.getText().toString());
                Snackbar.make(findViewById(android.R.id.content), "No Internet", Snackbar.LENGTH_SHORT).show();
            } else {
                getdata(branchID, date.getText().toString());

//                Toast.makeText(activity, "Call Server!", Toast.LENGTH_SHORT).show();
            }
        } else {
                builder.setMessage("You have no Permission Attendance on this date..!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(Classwise_attendance_view.this, MainMenuActivity.class));
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
//            Toast.makeText(Classwork_Activity.this,"No record Found !",Toast.LENGTH_SHORT).show();
        }


//        getDatas();


    }


    private void opencalender() {
        new DatePickerDialog(Classwise_attendance_view.this, R.style.DialogTheme, dateSetListener, myCalendar
                .get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }


    public Date serv_date(String date_) {
        // String string = "January 2, 2010";
        DateFormat format = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date date = null;
        try {
            date = format.parse(date_);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void getdata(String brid, String Attan_date)
    {
        // String url = "http://teacherappservice.outomate.com/TeacherAppService.svc/Stu_AttMrklist/brid/10/classid/204/Attan_date/2020-12-07";
        String url = Url_Symbol.url + "Stu_AttMrklist/brid/" + brid + "/classid/" + classid + "/Attan_date/" + Attan_date + "/subjectid/" + subjectid + "6";//brid, classid, Attan_date;
        Log.e("ClassViewAttendance", url);
        //http://teacherappservice.outomate.com/TeacherAppService.svc/Attendence/empid/306/branchid/10
        //http://teacherappservice.outomate.com/TeacherAppService.svc/Stu_AttMrklist/brid/10/classid/204/Attan_date/2020-12-07
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                attendancemodellist.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        String ermsg = jsonObject.getString("errormessage");


                        if (ermsg.equalsIgnoreCase("Correct")) {

                            String Attan_Status = jsonObject.getString("Attan_Status");
                            Log.e("Attan_Status", "" + Attan_Status);
                            String Attan_date = jsonObject.optString("Attan_date");
                            Log.e("Attan_date", "" + Attan_date);
                            String FatherMobileNo = jsonObject.getString("FatherMobileNo");
                            Log.e("FatherMobileNo", "" + FatherMobileNo);
                            String MotherMobileNo = jsonObject.getString("MotherMobileNo");
                            Log.e("MotherMobileNo", "" + MotherMobileNo);
                            String Session_Id_FK = jsonObject.getString("Session_Id_FK");
                            Log.e("Session_Id_FK", "" + Session_Id_FK);
                            String clientid = jsonObject.getString("clientid");
                            Log.e("clientid", "" + clientid);
                            String errormessage = jsonObject.getString("errormessage");
                            Log.e("errormessage", "" + errormessage);
                            String studentid = jsonObject.getString("studentid");
                            Log.e("studentid", "" + studentid);
                            String studentname = jsonObject.getString("studentname");
                            Log.e("studentname", "" + studentname);
                            String stu_desc = jsonObject.getString("Description");
                            Log.e("stu_desc", "" + stu_desc);


                            AttendanceviewModel attendanceviewmodel = new AttendanceviewModel();
                            attendanceviewmodel.setAttan_date(Attan_date);
                            attendanceviewmodel.setClientid(clientid);
                            attendanceviewmodel.setFatherMobileNo(FatherMobileNo);
                            attendanceviewmodel.setMotherMobileNo(MotherMobileNo);
                            attendanceviewmodel.setSession_Id_FK(Session_Id_FK);
                            attendanceviewmodel.setStudentid(studentid);
                            attendanceviewmodel.setStudentname(studentname);
                            attendanceviewmodel.setErrormessage(errormessage);
                            attendanceviewmodel.setAttan_Status(Attan_Status);
                            attendanceviewmodel.setStuAttDscription(stu_desc);
                            attendancemodellist.add(attendanceviewmodel);

                        } else {
                            Toast toast = Toast.makeText(activity, "No Record Is Found!", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.e("catch", e + "");
                    }

                }

                if (attendancemodellist != null && attendancemodellist.size() > 0) {
                    Attendanceviewadapter Recyclerview_Sip_Swp = new Attendanceviewadapter(activity, attendancemodellist);
                    stud_rv.setHasFixedSize(true);
                    stud_rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                    stud_rv.setAdapter(Recyclerview_Sip_Swp);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(activity, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        finish();
    }

}

