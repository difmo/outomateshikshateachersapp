package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.HwvideosAdapter;
import com.saptrishi.outomateshikshateachersapp.model.HwVideos;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class HomeworkVideosActivity extends AppCompatActivity {
    SharedPreferences sf;
    ImageView imageViewBackButton;
    RecyclerView recyclerView;
    HwvideosAdapter hwvideosAdapter;
    List<HwVideos> hwVideosList;
    HwVideos hwVideos;
    LinearLayoutManager linearLayoutManager;
    RequestQueue requestQueue;
    String ClassStrucID, Errormsg, StuHwid, TopicName, TopicURL, homework_idpk, SDate, studentid, studentname;
    String brid, Timetableid, classid, subid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homework_videos_activity);
        imageViewBackButton = findViewById(R.id.imageViewBackButton);
        recyclerView = findViewById(R.id.hwvideosrecyclerview);
        hwVideosList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        requestQueue = Volley.newRequestQueue(HomeworkVideosActivity.this);

        sf = getSharedPreferences("ShikshaContainer1", MODE_PRIVATE);
        brid = sf.getString("Branchid", "");
        Log.e("dbId", "" + brid);

        Timetableid = getIntent().getStringExtra("Timetableid");
        Log.e("dTimetableid", "" + Timetableid);
        classid = getIntent().getStringExtra("classid");
        Log.e("dclassid", "" + classid);
        subid = getIntent().getStringExtra("subid");
        Log.e("dsubid", "" + subid);

        String url = "http://teacherappservice.outomate.com/TeacherAppService.svc/ShowytURL_student/brid/" + brid + "/Timetableid/" + Timetableid + "/classid/" + classid + "/subid/" + subid + "";
        Log.e("SKS_url", "" + url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        TopicName = jsonObject.getString("TopicName");
                        if (TopicName != "null" && !TopicName.equals("")) {
                            ClassStrucID = jsonObject.getString("ClassStrucID");
                            Log.e("ClassStrucID", "" + ClassStrucID);
                            Errormsg = jsonObject.getString("Errormsg");
                            Log.e("Errormsg", "" + Errormsg);
                            StuHwid = jsonObject.getString("StuHwid");
                            Log.e("StuHwid", "" + StuHwid);
                            TopicName = jsonObject.getString("TopicName");
                            Log.e("TopicName", "" + TopicName);
                            TopicURL = jsonObject.getString("TopicURL");
                            Log.e("TopicURL", "" + TopicURL);
                            homework_idpk = jsonObject.getString("homework_idpk");
                            Log.e("homework_idpk", "" + homework_idpk);
                            SDate = jsonObject.getString("hwsub");
                            Log.e("SDate", "" + SDate);
                            studentid = jsonObject.getString("studentid");
                            Log.e("studentid", "" + studentid);
                            studentname = jsonObject.getString("studentname");
                            Log.e("studentname", "" + studentname);

                            hwVideosList.add(new HwVideos(studentname, TopicName, SDate, TopicURL));
                            hwvideosAdapter = new HwvideosAdapter(HomeworkVideosActivity.this, hwVideosList);
                            recyclerView.setAdapter(hwvideosAdapter);
                            hwvideosAdapter.notifyDataSetChanged();
                        }
                        else
                            {
                            Toast.makeText(HomeworkVideosActivity.this, "No Data found!", Toast.LENGTH_SHORT).show();
                        }

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(stringRequest);


        imageViewBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
