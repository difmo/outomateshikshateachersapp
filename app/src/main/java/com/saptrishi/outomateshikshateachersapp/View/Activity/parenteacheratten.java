    package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.AttendenceAdapter;
//import com.saptrishi.outomateshikshateachersapp.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.Model;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.Sending_AttendenceToDB;
import com.saptrishi.outomateshikshateachersapp.Student_attendance;
import com.saptrishi.outomateshikshateachersapp.adapter.Parentchildadapter;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class parenteacheratten extends AppCompatActivity {
    ArrayAdapter AD;

    TextView classname1, subject1;
    TextView tv_versionnames;

    List<String> student_name = new ArrayList<>();
    List<Model> stu_id = new ArrayList<>();
    List<Model> stu_att_desc = new ArrayList<>();
    List<String> student_id = new ArrayList<String>();
    List<String> status = new ArrayList<String>();
    List<String> stu_img = new ArrayList<String>();
    List<String> student = new ArrayList<>();
    List<String> classname = new ArrayList<>();
    List<String> classid = new ArrayList<>();
    List<String> clsstrucid = new ArrayList<>();
    List<String> SubjectID_FK = new ArrayList<>();
    List<String> TeacherID_FK = new ArrayList<>();
    List<String> SessionID = new ArrayList<>();
    //    String []classes=new String[classname.size()];
    SharedPreferences sharedPreferences;
    MySqliteDataBase sqliteDataBase;
    ListView listView;
    //Spinner spinner;

    RequestQueue requestQueue;
    String SubjectID_FK_send, TeacherID_FK_send, clsstrucid_send, clasname = "Class";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parenteacheratten);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        classname1 = findViewById(R.id.className);
        subject1 = findViewById(R.id.tv_subjectname);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        sqliteDataBase = new MySqliteDataBase(this);

        // spinner = findViewById(R.id.spin_class);

        fetchclasses_from_sqlite();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        LinearLayout linearLayout = findViewById(R.id.actionbar);
        TextView textView = new TextView(this);
        textView.setText("Class Attendance");
        textView.setTextSize(25);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        textView.setGravity(Gravity.CENTER);
        textView.setTextColor(getResources().getColor(R.color.white));
        linearLayout.addView(textView);


        listView = findViewById(R.id.class_list);
        Button btn_save = findViewById(R.id.btn_save1);
        Intent intent = getIntent();
        String classname = intent.getStringExtra("classname");// changed
        String subjectname = intent.getStringExtra("subjectname");// changed
        classname1.setText(classname);
        subject1.setText(subjectname);


        //
//        intent.putExtra("classname",classname.get(i));
//        intent.putExtra("clsstrucid",clsstrucid.get(i));
//        intent.putExtra("SubjectID_FK",SubjectID_FK.get(i));
//        intent.putExtra("TeacherID_FK",TeacherID_FK.get(i));
//        intent.putExtra("SessionID",TeacherID_FK.get(i));
//
//        Intent intent=getIntent();
//        final String clsstructid=intent.getStringExtra("clsstrucid");
//        final String SubjectID_FK=intent.getStringExtra("SubjectID_FK");
//        final String TeacherID_FK=intent.getStringExtra("TeacherID_FK");
//        final String SessionID=intent.getStringExtra("SessionID");

        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

//        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//        Date date = new Date();
//        final String currentDate = sdf1.format(date);//new Date()
        Calendar cla = Calendar.getInstance(java.util.TimeZone.getTimeZone("GMT+1:00"));
        Date currentLocalTime = cla.getTime();
        Log.e("currentLocalTime", "" + currentLocalTime);
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        dateFormat.setTimeZone(java.util.TimeZone.getTimeZone("GMT+5:30"));
        final String localTime = dateFormat.format(currentLocalTime);


        final String dkdate=sharedPreferences.getString("dkdate","");
        Log.e("dkdate2",""+dkdate);
        final String branchid = sharedPreferences.getString("Branchid", "");
        // final String subj = sharedPreferences.getString("SubjectID_FK","");
        String TeacherID_FK = sharedPreferences.getString("TeacherID_FK", "");
        final String sess = sharedPreferences.getString("SessionID ", "");
        final String clsstrucid_send = intent.getStringExtra("classid");// new 5
        final String subj = intent.getStringExtra("subjectid");

        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String teacherid = sharedPreferences.getString("empid", "");
        TeacherID_FK=teacherid;
        Log.e("TeacherID",TeacherID_FK);
        Log.e("teacherid",teacherid);
        String finalTeacherID_FK = TeacherID_FK;
        btn_save.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
//                String s=AttendenceAdapter.st_id.get(0).getAttDesc();
//                Log.e("sssss","aaaa"+s);
                if (!Connectivity.isConnected(parenteacheratten.this)) {
                    Sending_AttendenceToDB sending_attendenceToDB = new Sending_AttendenceToDB(parenteacheratten.this);

                    for (int i = 0; i < Parentchildadapter.st_id.size(); i++) {
                        SessionID.add(sess);



                        if ( Parentchildadapter.st_id.get(i).getSelected()) {
//                        Log.e("Unselected12",clsstrucid_send+"");
//                        Log.e("Unselected12",AttendenceAdapter.st_id.get(i).getStudentID());
//                        Log.e("Unselected12",SubjectID_FK_send+"");
//                        Log.e("Unselected12","A");
//                        Log.e("Unselected12",TeacherID_FK_send+"");
//                        Log.e("Unselected12",currentDate);
//                        Log.e("Unselected12",localTime);
//                        Log.e("Unselected12",branchid);
//                        Log.e("Unselected12",SessionID.get(i));
////                            senddatatovolley(clsstrucid.get(i),AttendenceAdapter.st_id.get(i).getStudentID(),SubjectID_FK.get(i),"A",TeacherID_FK.get(i),currentDate,localTime,branchid,SessionID.get(i));
                            sending_attendenceToDB.senddatatovolley(clsstrucid_send, Parentchildadapter.st_id.get(i).getStudentID(), subj, "P", finalTeacherID_FK, dkdate, localTime, branchid, SessionID.get(i), Parentchildadapter.st_id.get(i).getAttDesc());// change 27 nov
//
//
//                          sqliteDataBase.attendenceinsertion(clsstrucid_send,AttendenceAdapter.st_id.get(i).getStudentID(),SubjectID_FK_send,"A",TeacherID_FK_send,currentDate,localTime,branchid,SessionID.get(i));
                        } else {
//                        Log.e("Unselected12",clsstrucid_send+"");
//                        Log.e("Unselected12",AttendenceAdapter.st_id.get(i).getStudentID());
//                        Log.e("Unselected12",SubjectID_FK_send+"");
//                        Log.e("Unselected12","P");
//                        Log.e("Unselected12",TeacherID_FK_send+"");
//                        Log.e("Unselected12",currentDate);
//                        Log.e("Unselected12",localTime);
//                        Log.e("Unselected12",branchid);
//                        Log.e("Unselected12",SessionID.get(i));
////                        sqliteDataBase.deleteattendancetable()
                            Log.e("toats",""+ Parentchildadapter.st_id.get(i).getAttDesc());
                            sending_attendenceToDB.senddatatovolley(clsstrucid_send, Parentchildadapter.st_id.get(i).getStudentID(),
                                    subj, "A",TeacherID_FK_send, dkdate, localTime, branchid, SessionID.get(i), Parentchildadapter.st_id.get(i).getAttDesc());// change 27 nov


                        }
                    }
                } else {
                    for (int i = 0; i < Parentchildadapter.st_id.size(); i++) {
                        if ( Parentchildadapter.st_id.get(i).getSelected()) {
                            Log.e("clsstrucid_send P", clsstrucid_send + "");
                            Log.e("Unselected12",  Parentchildadapter.st_id.get(i).getStudentID());
                            Log.e("SubjectID_FK_send", subj + "");
                            Log.e("Unselected12", "P");
                            Log.e("TeacherID_FK_send", finalTeacherID_FK + "");
                            Log.e("SessionIDA", SessionID + "");
                            Log.e("currentDate", dkdate);
                            Log.e("Unselected12", localTime);
                            Log.e("Unselected12", branchid);
                            Log.e("Unselected12", sess + "");
////                            senddatatovolley(clsstrucid.get(i),AttendenceAdapter.st_id.get(i).getStudentID(),SubjectID_FK.get(i),"A",TeacherID_FK.get(i),currentDate,localTime,branchid,SessionID.get(i));
                            sqliteDataBase.attendenceinsertion(clsstrucid_send, Parentchildadapter.st_id.get(i).getStudentID(), subj, "P", finalTeacherID_FK, dkdate, localTime, branchid, SessionID.get(i), Parentchildadapter.st_id.get(i).getAttDesc());//change 27
                        }
                        else
                        {
                            Log.e("clsstrucid_send A", clsstrucid_send + "");
                            Log.e("Unselected12",  Parentchildadapter.st_id.get(i).getStudentID());
                            Log.e("SUBJECTp", subj + "");
                            Log.e("Unselected12", "A");
                            Log.e("Unselected12", TeacherID_FK_send + "");
                            Log.e("currentDate", dkdate);
                            Log.e("Unselected12", localTime);
                            Log.e("SessionIDp", SessionID + "");
                            Log.e("Unselected12", branchid);
                            Log.e("Unselected12", SessionID.get(i));
                            Log.e("Unselected13","a:"+  Parentchildadapter.st_id.get(i).getAttDesc());
////                           sqliteDataBase.deleteattendancetable()
//                            AttendenceAdapter.st_id.get(i).getAttDesc() put Here desc
                            //http://teacherappservice.outomate.com/TeacherAppService.svc/clswiseAttendance
                            sqliteDataBase.attendenceinsertion(clsstrucid_send,  Parentchildadapter.st_id.get(i).getStudentID(), subj, "A", finalTeacherID_FK, dkdate, localTime, branchid, SessionID.get(i), Parentchildadapter.st_id.get(i).getAttDesc()); // change 27 SubjectID_FK_send
                        }
                    }

                }
                Toast.makeText(parenteacheratten.this, "Attendance Saved", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(parenteacheratten.this, MainMenuActivity.class));
                finish();
            }
        });

        // spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//
//                if (position == 0) {
//                    listView.setAdapter(null);
//                } else {
//
//                    clasname = classname.get(position);
//                    clsstrucid_send = clsstrucid.get(position);//<---------------------------CLASS ID
//                    SubjectID_FK_send = "";      /*as per dicussion with sadndeep sir*/  //value pass karna hai
//                    TeacherID_FK_send = TeacherID_FK.get(position);
////                  String SessionID_send=SessionID.get(position);
//                    featchdatafromvolly(clsstrucid_send);//copy it
//
//                    Log.e("spi_position", clsstrucid_send + "," + SubjectID_FK_send + "," + TeacherID_FK_send);
//                }
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        ImageView backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //here we have implemented new code
        final String clsstructid = getIntent().getStringExtra("classid");
        featchdatafromvolly(clsstructid);
        Log.e("featchdatafromvolly", "featchdatafromvolly");
    }

    public void fetchclasses_from_sqlite() {

        MySqliteDataBase mySqliteDataBase = new MySqliteDataBase(this);
        Cursor cursor = mySqliteDataBase.featchtimetable();
        classname.add("--Select Class--");
        clsstrucid.add("--Select Class--");
        TeacherID_FK.add("--Select Class--");
//        if (cursor.moveToFirst()) {
//            do {
//                classname.add(cursor.getString(cursor.getColumnIndex("classname")));
//                Log.e("classname", cursor.getString(cursor.getColumnIndex("classname")));
//                clsstrucid.add(cursor.getString(cursor.getColumnIndex("clsstruci")));
//                Log.e("classname", cursor.getString(cursor.getColumnIndex("clsstruci")));
//                TeacherID_FK.add(cursor.getString(cursor.getColumnIndex("TeacherID")));
//                Log.e("classname", cursor.getString(cursor.getColumnIndex("TeacherID")));
//            } while (cursor.moveToNext());
//
////
////            ArrayAdapter arrayAdapter = new ArrayAdapter(Student_attendance.this, android.R.layout.simple_dropdown_item_1line, classname);
////            spinner.setAdapter(arrayAdapter);
//        }

    }

    public void featchdatafromvolly(String classsturid) {
//          String classid=intent.getStringExtra("class");class

        final ProgressDialog pDialog = new ProgressDialog(parenteacheratten.this);

        pDialog.setTitle("Loading...");
        pDialog.setMessage("Validating your fields");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
        sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String branchid = sharedPreferences.getString("Branchid", "");
        // Toast.makeText(this, ""+classsturid+","+branchid, Toast.LENGTH_SHORT).show();

        String url = Url_Symbol.url + "Stu_ClassAttlist/brid/" + branchid + "/classid/" + classsturid;
        Log.e("stu_attendeceUrl", url);
        final Model model = new Model();
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("response_atte", response + "");
                student.clear();
                student_name.clear();
                stu_img.clear();
                status.clear();
                student_id.clear();
                SessionID.clear();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        String Studentname = jsonObject.getString("studentname");
                        String Studentid = jsonObject.getString("clientid");
                        String Stuid = jsonObject.getString("studentid");
                        String SessinID = jsonObject.getString("Session_Id_FK");
                        Log.e("student_name", Studentname);
                        student.add(Stuid);
                        student_name.add(Studentname);
                        stu_img.add("A");
                        status.add("p");
                        student_id.add(Studentid);
                        SessionID.add(SessinID);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                Log.e("student_lis", student + "");
                stu_id = getModel(true);
                Parentchildadapter attendenceAdapter = new Parentchildadapter(parenteacheratten.this, student_name, student_id, stu_id, stu_img, status);
                listView.setAdapter(attendenceAdapter);
                pDialog.cancel();
                CheckBox checkBox = findViewById(R.id.cb_selectall);
                checkBox.setChecked(true);
                checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        Log.e("c888b", isChecked + "");
                        if (isChecked == true) {
                            stu_id = getModel(true);
                            Parentchildadapter attendenceAdapter1 = new Parentchildadapter(parenteacheratten.this, student_name, student_id, stu_id, stu_img, status);//stu_id
                            Toast.makeText(parenteacheratten.this, "Present", Toast.LENGTH_LONG).show();
                            listView.setAdapter(attendenceAdapter1);
                        } else {
                            stu_id = getModel(false);
                            Parentchildadapter attendenceAdapter2 = new Parentchildadapter(parenteacheratten.this, student_name, student_id, stu_id, stu_img, status);
                            listView.setAdapter(attendenceAdapter2);
                        }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error_atte", error + "");
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private ArrayList<Model> getModel(boolean isSelect) {
        ArrayList<Model> list = new ArrayList<>();
        list.clear();
        for (int i = 0; i < student.size(); i++) {

            Model model = new Model();
            model.setSelected(isSelect);
            model.setStudentID(student.get(i));
            model.setAttDesc(student.get(i));
            list.add(model);
        }
        return list;
    }

    @Override
    public void onBackPressed() {
//        startActivity(new Intent(this, ClasswiseattendanceActivity.class));
        super.onBackPressed();
        startActivity(new Intent(this, Classteacheratten.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
    }
}
