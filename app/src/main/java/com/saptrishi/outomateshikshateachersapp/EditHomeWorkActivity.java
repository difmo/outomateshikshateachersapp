package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EditHomeWorkActivity extends AppCompatActivity {


    private ArrayList<ShowEditHW_Datamodel> userInfos;
    private ShowEdit_Adapter showEdit_adapter;
    private ListView customListView;
    TextView setDate;
    String finaldaytimetableid,dateodhomework;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_home_work);

        customListView=(ListView)findViewById(R.id.listHW);
        userInfos= new ArrayList<>();
        setDate=(TextView)findViewById(R.id.tvedit_homeworkDate);


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

        Intent intent=getIntent();
        String getempid=intent.getStringExtra("getempid");
        String subjectid=intent.getStringExtra("subjectid");
        String classid=intent.getStringExtra("classid");

        SharedPreferences sharedPreferences=getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        TextView childName = findViewById(R.id.showchildname);
        childName.setText(sharedPreferences.getString("empname", ""));

          finaldaytimetableid=intent.getStringExtra("finaldaytimetableid");
         dateodhomework=intent.getStringExtra("dateodhomework");
        setDate.setText(dateodhomework);
        MySqliteDataBase mySqliteDataBase=new MySqliteDataBase(this);

        getHomeWorkfromdb(getempid, subjectid,classid,dateodhomework);

        TextView className=(TextView)findViewById(R.id.className);
        TextView subjectName=(TextView)findViewById(R.id.subjectname);

        Cursor cursor1 = mySqliteDataBase.fetchparticularHomework(getempid, subjectid, classid, finaldaytimetableid);
        if (cursor1.moveToFirst())
        {
            className.setText(cursor1.getString(2));
            subjectName.setText(cursor1.getString(12));

        }
        else
        {
            Toast.makeText(this, "nothing in table", Toast.LENGTH_SHORT).show();
        }

    //    Toast.makeText(this, dateodhomework+"", Toast.LENGTH_SHORT).show();


    }


    private void getHomeWorkfromdb(final String getempid,final String subjectid,final String classid, final String Date) {
        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        userInfos.clear();
        String branchid = sf.getString("Branchid", "");
        final String Stuid = sf.getString("empid", "");//Showhw/doh/14-Aug-2019/brid/7/empid/126/classid/118/subid/2
        final String url = Url_Symbol.url + "Showhw/doh/" + Date + "/brid/" + branchid + "/empid/" + Stuid+"/classid/"+classid+"/subid/"+subjectid;
        Log.e("homeworkURL edit", url);
        RequestQueue requestQueue = Volley.newRequestQueue(EditHomeWorkActivity.this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = (JSONObject) response.get(i);
                        Log.e("timetableresponse",response+"");
                        Log.e("homeworkErrormsg", jsonObject.get("Errormsg") + "");
                        String ermsg = jsonObject.getString("Errormsg");
                        if (ermsg.equals("Correct")) {

                            Log.e("timetableresponse1",response+"");
                            String Filename=jsonObject.getString("Filename");
                            String HWid=jsonObject.getString("HWid");
                            String URL=jsonObject.getString("URL");
                            String dateofSubmission=jsonObject.getString("show_dos");
                            userInfos.add(new ShowEditHW_Datamodel(Filename,HWid,URL,Stuid,subjectid,classid,finaldaytimetableid,dateodhomework,dateofSubmission));
                            //mySqliteDataBase.insertGiveHomeworkDetails(Branchid,ClassNm,CreateDate,DayID_FK,FinalDayTimeTable_Id,Fk_gpclsid,Period_Name,ScheduleDate,TeacherID_FK,SessionID,SubjectID_FK,SubjectMasterID,SubjectName,TimeTableMID_FK,clsstrucid,empname,id);
                        } else {
                            Toast.makeText(EditHomeWorkActivity.this,jsonObject.getString("errormessage"),Toast.LENGTH_SHORT).show();
                            Log.e("errormsg",jsonObject.getString("errormessage"));
                        }
                    } catch (JSONException e) {

                        e.printStackTrace();
                        Log.e("catch", e + "");
//                         Toast.makeText(getApplicationContext(),"PDF Is Submit!",Toast.LENGTH_SHORT).show();

                    }

                }
                showEdit_adapter = new ShowEdit_Adapter(userInfos, EditHomeWorkActivity.this);

                customListView.setAdapter(showEdit_adapter);
                showEdit_adapter.notifyDataSetChanged();

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditHomeWorkActivity.this, GiveHomeworkActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
        super.onBackPressed();
    }
}
