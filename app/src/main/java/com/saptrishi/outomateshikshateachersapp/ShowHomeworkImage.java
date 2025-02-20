package com.saptrishi.outomateshikshateachersapp;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.adapter.HomeworkImageAdapter;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ShowHomeworkImage extends AppCompatActivity {
    GridView image_grid;
    List<String> urllist = new ArrayList();
    RequestQueue requestQueue;
    TextView tv_subject, stu_classname, stu_name;
    EditText et_remark;
    ImageButton btn_sendremark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_homework_image);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        image_grid = findViewById(R.id.image_grid);
        tv_subject = findViewById(R.id.tv_subject);
        stu_classname = findViewById(R.id.stu_classname);
        stu_name = findViewById(R.id.stu_name);
        et_remark = findViewById(R.id.et_remark);
        btn_sendremark = findViewById(R.id.btn_sendremark);
        LinearLayout backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        String subjectid = intent.getStringExtra("subjectid");
       //String classstrucid =intent.getStringExtra("classstrucid");
        String classid = intent.getStringExtra("classstrucid"); // new add
        String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
        final String studentid = intent.getStringExtra("studentid");
        String studentname = intent.getStringExtra("studentname");
        String getempid = intent.getStringExtra("getempid");
        final String homework_idpk = intent.getStringExtra("homework_idpk");
        final String StuHwid = intent.getStringExtra("StuHwid");
        stu_name.setText(studentname);

        Log.e("Student_id", studentid);


        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        final String branchid = sf.getString("Branchid", "");
        final String Teacherid = sf.getString("empid", "");
        featchimages(branchid, finaldaytimetableid, classid, subjectid, studentid);
        MySqliteDataBase mySqliteDataBase = new MySqliteDataBase(this);

        btn_sendremark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (et_remark.getText().toString().trim().isEmpty()) {
                    et_remark.setError("Please fill");
                } else {
                    sendremark(StuHwid, homework_idpk, et_remark.getText().toString().trim(), Teacherid, studentid, branchid);
                }
            }
        });


        Cursor cursor1 = mySqliteDataBase.fetchparticularHomework(getempid, subjectid, classid, finaldaytimetableid);
        if (cursor1.moveToFirst()) {
            stu_classname.setText(cursor1.getString(2));
            tv_subject.setText(cursor1.getString(12));

        } else {
            Toast.makeText(this, "nothing in table", Toast.LENGTH_SHORT).show();
        }

        image_grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // set an Intent to Another Activity
                Intent intent = new Intent(ShowHomeworkImage.this, FullImageActivity.class);
                intent.putExtra("image", urllist.get(position)); // put image data in Intent
                startActivity(intent); // start Intent
            }
        });


    }


    public void featchimages(String branchid, String tableid, String classid, String subjectid, String stuid) {
//        "http://teac
//        herappservice.outomate.com/TeacherAppService.svc/Showhw_stuImg/brid/10/Timetableid/3638/classid/204/subjectid/62/stuid/2267"

        String url = Url_Symbol.url + "Showhw_stuImg/brid/" + branchid + "/Timetableid/" + tableid + "/classid/" + classid + "/subjectid/" + subjectid + "/stuid/" + stuid;
        Log.e("imageUrl", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("imageUrl res", response + "");
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);

                        urllist.add(jsonObject.getString("StuImgPath"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                HomeworkImageAdapter customAdapter = new HomeworkImageAdapter(getApplicationContext(), urllist);
                image_grid.setAdapter(customAdapter);

                Log.e("urllist", urllist + "");
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("imageUrl err", error + "");
            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    public void sendremark(String StuHwIdPk, String homework_idpk, String HWRemark_text, String TeacherId, String HW_stuid, String Hw_brid) {
        String Urll = Url_Symbol.url + "HwRemarkinsert";
        Log.e("aa StuHwIdPk", StuHwIdPk);
        Log.e("aa homework_idpk", homework_idpk);
        Log.e("aa HWRemark_text", HWRemark_text);
        Log.e("aa TeacherId", TeacherId);
        Log.e("aa HW_stuid", HW_stuid);
        Log.e("aa Hw_brid", Hw_brid);
        final ProgressDialog progressDialog = new ProgressDialog(ShowHomeworkImage.this);
        progressDialog.setMessage("Sending..."); // Setting Message
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        progressDialog.show(); // Display Progress Dialog
        progressDialog.setCancelable(false);
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("StuHwIdPk", StuHwIdPk);
            jsonObject.put("homework_idpk", homework_idpk);
            jsonObject.put("HWRemark_text", HWRemark_text);
            jsonObject.put("TeacherId", TeacherId);
            jsonObject.put("HW_stuid", HW_stuid);
            jsonObject.put("Hw_brid", Hw_brid);
            Log.e("aa jsonObject", jsonObject.toString());
            final String requestbody = jsonObject.toString();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, Urll, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.e("aa response", response);
                    progressDialog.dismiss();
                    if (response.equals("200")){
                        et_remark.setText("");
                        Toast.makeText(ShowHomeworkImage.this, "Remak has  been sent.", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(ShowHomeworkImage.this,GiveHomeworkActivity.class));
                    }else {
                        Toast.makeText(ShowHomeworkImage.this, "remak has not sent. pls retry!", Toast.LENGTH_SHORT).show();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("aa  error", error + "");
                    progressDialog.dismiss();
                    Toast.makeText(ShowHomeworkImage.this, "" + error, Toast.LENGTH_SHORT).show();
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() {
                    try {
                        return requestbody == null ? null : requestbody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestbody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null) {
                        responseString = String.valueOf(response.statusCode);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                    10000,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue.add(stringRequest);


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onBackPressed() {

        startActivity(new Intent(ShowHomeworkImage.this, GiveHomeworkActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
    }

}