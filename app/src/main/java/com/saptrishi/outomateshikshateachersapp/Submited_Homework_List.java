package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.adapter.Submitted_Homework_Adapter;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Submited_Homework_List extends AppCompatActivity {
    List<String> Student_name =new ArrayList<>();
    List<String> Homework_status =new ArrayList<>();
    List<String> submitted_date =new ArrayList<>();
    List<String> homework_idpk =new ArrayList<>();
    List<String> StuHwid =new ArrayList<>();
    List<String> srno =new ArrayList<>();
    RequestQueue requestQueue;
    ListView list_homework;
    List<String> classstrucid =new ArrayList<>();
    List<String> studentid =new ArrayList<>();
    String Stubjectid,classid,finaldaytimetableid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submited__homework__list);
        list_homework=findViewById(R.id.list_homework);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        LinearLayout backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        Intent intent=getIntent();
        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String branchid = sf.getString("Branchid", "");
        final String getempid=intent.getStringExtra("getempid");
         Stubjectid=intent.getStringExtra("subjectid");
         classid=intent.getStringExtra("classid");
         finaldaytimetableid=intent.getStringExtra("finaldaytimetableid");
        getstudentlist(branchid,finaldaytimetableid,classid,Stubjectid);

        list_homework.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(Submited_Homework_List.this, ""+position, Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(Submited_Homework_List.this,ShowHomeworkImage.class);
                intent1.putExtra("subjectid",Stubjectid);
                intent1.putExtra("getempid",getempid);
                intent1.putExtra("classid",classid);
                intent1.putExtra("finaldaytimetableid",finaldaytimetableid);
                intent1.putExtra("studentid",studentid.get((position-1)));
                intent1.putExtra("classstrucid",classstrucid.get((position-1)));
                intent1.putExtra("studentname",Student_name.get((position)));
                intent1.putExtra("homework_idpk",homework_idpk.get((position-1)));
                intent1.putExtra("StuHwid",StuHwid.get((position-1)));
                Log.e("aaahomework_idpk",homework_idpk.toString());
                Log.e("aaaStuHwid",StuHwid.toString());
                startActivity(intent1);
                finish();
            }
        });
    }

    public void getstudentlist(String branchid,String timetableid,String classid,String subjectid){
//        http://teacherappservice.outomate.com/TeacherAppService.svc/Showhw_student/brid/10/Timetableid/3638/classid/204/subid/2267

        String Url= Url_Symbol.url+"Showhw_student/brid/"+branchid+"/Timetableid/"+timetableid+"/classid/"+classid+"/subid/"+subjectid;
        Log.e("URL",Url);

        final JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("respondse stulist",response+"");

                try {

                    for (int i=0;i<response.length();i++)
                    {
                        JSONObject jsonObject=response.getJSONObject(i);

                        classstrucid.add(jsonObject.getString("ClassStrucID"));
                        studentid.add(jsonObject.getString("studentid"));
                        if(jsonObject.getString("homework_idpk").length()==0)
                        {
                            homework_idpk.add("0");
                        }
                        else{
                            homework_idpk.add(jsonObject.getString("homework_idpk"));
                        }

                        if(jsonObject.getString("StuHwid").length()==0)
                        {
                            StuHwid.add("0");
                        }
                        else{
                            StuHwid.add(jsonObject.getString("StuHwid"));
                        }


                        if(i==0){

                            srno.add("Sr. No.");
                            Student_name.add("Student Name");
                            Homework_status.add("Status");
                            submitted_date.add("Sub. Date");

                        srno.add((i+1)+"");
                        Student_name.add(jsonObject.getString("studentname"));
                        if (jsonObject.getString("hwsub").length()==0) {
                            Homework_status.add("Incomplete");
                            submitted_date.add("NA");
                        }
                        else {
                            Homework_status.add("Complete");
                            submitted_date.add(jsonObject.getString("hwsub"));
                        }
                        Log.e("listsss",jsonObject.getString("hwsub").length()+"AA");
                        }
                        else {
                            srno.add((i+1)+"");
                            Student_name.add(jsonObject.getString("studentname"));
                            if (jsonObject.getString("hwsub").length()==0) {
                                Homework_status.add("Incomplete");
                                submitted_date.add("NA");
                            }
                            else {
                                Homework_status.add("Complete");
                                submitted_date.add(jsonObject.getString("hwsub"));
                            }
                            Log.e("listsss",jsonObject.getString("hwsub").length()+"AA");
                        }
                    }


                    Submitted_Homework_Adapter submitted_homework_adapter=new Submitted_Homework_Adapter(Submited_Homework_List.this,Student_name,Homework_status,submitted_date,srno);
                    list_homework.setAdapter(submitted_homework_adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("respondse stulist",error+"e");
            }
        });
        requestQueue.add(jsonArrayRequest);





    }

    @Override
    public void onBackPressed() {

        startActivity(new Intent(Submited_Homework_List.this, GiveHomeworkActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
    }



}