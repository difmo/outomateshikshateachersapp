package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Chat_Topic_Activity extends AppCompatActivity {


    ListView listView;
    SimpleAdapter adapter;
    int[] to = {R.id.query_no, R.id.Topics, R.id.createdby,R.id.textView_date};
    String[] from = {"Query", "Topics", "CreatedBy","Date"};
    List<HashMap<String, String>> topicslist = new ArrayList<HashMap<String, String>>();
    List<HashMap<String, String>> senddetails = new ArrayList<HashMap<String, String>>();
    List queryno = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat__topic_);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ImageView backpressed = findViewById(R.id.backpressed);
//        backpressed.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });
        LinearLayout linearLayout = findViewById(R.id.actionbar);
        TextView textView = new TextView(this);
        textView.setText("All Queries ");
        textView.setTextSize(24);
        textView.setTypeface(textView.getTypeface(), Typeface.BOLD);//new
        //  textView.setBottom(21);// new
        textView.setGravity(Gravity.CENTER);//new
//        linearLayout.setPadding(0, -1, 0, 0);
        textView.setPadding(12, -5, 12, 1);
        textView.setTextColor(getResources().getColor(R.color.white));
//        linearLayout.addView(textView);
        listView = findViewById(R.id.topics_list);
        setvaluesinlist();

//        featchdata();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast toast = Toast.makeText(Chat_Topic_Activity.this, "he", Toast.LENGTH_SHORT);

                Intent intent = new Intent(Chat_Topic_Activity.this, ChatActivity.class);
//                SharedPreferences sharedPreferences=getSharedPreferences("Chat",MODE_PRIVATE);
//                SharedPreferences.Editor editor=sharedPreferences.edit();
//                editor.clear();
//                editor.putString("Queryno",senddetails.get(position).get("QryNo"));
//                editor.putString("ChatTopicName",senddetails.get(position).get("ChatTopicName"));
//                editor.putString("Chat_ResQryText",senddetails.get(position).get("Chat_ResQryText"));
//                editor.putString("Createdate",senddetails.get(position).get("Createdate"));
//                editor.putString("RespDate",senddetails.get(position).get("RespDate"));
//                editor.putString("ChatQry",senddetails.get(position).get("ChatQry"));
//                editor.putString("OtherTopicName",senddetails.get(position).get("OtherTopicName"));
//                editor.putString("QryCreatedbyId",senddetails.get(position).get("QryCreatedbyId"));
//                editor.commit();
                intent.putExtra("Query", topicslist.get(position).get("Query"));
                intent.putExtra("Topicqryid", topicslist.get(position).get("Topicqryid"));
                startActivity(intent);
                finish();
            }
        });


    }

    public void setvaluesinlist() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        final SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        final String branchid = sharedPreferences.getString("Branchid", "");
        String teacherid = sharedPreferences.getString("empid", "");
        String url = Url_Symbol.url_sikhsha + "GetChatTopicqryshow/TopicFor/Tech/brid/" + branchid + "/CreatedbyId/" + teacherid;
        Log.e("chatUrl", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("chat_response", response + "");
                MySqliteDataBase mySqliteDataBase = new MySqliteDataBase(Chat_Topic_Activity.this);
//                topicslist.clear();
                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        HashMap hashMap = new HashMap();
                        hashMap.put("Query", jsonObject.getString("QryNo"));
                        hashMap.put("Topics", jsonObject.getString("ChatTopicName"));
                        hashMap.put("CreatedBy", jsonObject.getString("QryCreatedbyId"));
                        hashMap.put("Topicqryid", jsonObject.getString("Topicqryid"));
                        hashMap.put("Date",jsonObject.getString("Createdate"));
                        topicslist.add(hashMap);

//                        HashMap hm=new HashMap();
//                        hm.put("QryNo",jsonObject.getString("QryNo"));
//                        hm.put("ChatTopicName",jsonObject.getString("ChatTopicName"));
//                        hm.put("Chat_ResQryText",jsonObject.getString("QryResp"));
//                        hm.put("Createdate",jsonObject.getString("Createdate"));
//                        hm.put("RespDate",jsonObject.getString("RespDate"));
//                        hm.put("ChatQry",jsonObject.getString("ChatQry"));
//                        hm.put("QryCreatedbyId",jsonObject.getString("QryCreatedbyId"));
//                        hm.put("OtherTopicName",jsonObject.getString("OtherTopicName"));
//                        senddetails.add(hm);

//                        String BranchId=jsonObject.getString("BranchId");
//                        String ChatQry=jsonObject.getString("ChatQry");
//                        String ChatTopicId_Fk=jsonObject.getString("ChatTopicId_Fk");
//                        String ChatTopicName=jsonObject.getString("ChatTopicName");
//                        String Chat_ResQryText=jsonObject.getString("Chat_ResQryText");
//                        String ClassNm=jsonObject.getString("ClassNm");
//                        String Createdate=jsonObject.getString("Createdate");
//                        String Flg=jsonObject.getString("Flg");
//                        String FwdEmpId=jsonObject.getString("FwdEmpId");
//                        String LoopValue=jsonObject.getString("LoopValue");
//                        String OtherTopicName=jsonObject.getString("OtherTopicName");
//                        String QryCreatedbyId=jsonObject.getString("QryCreatedbyId");
//                        String QryNo=jsonObject.getString("QryNo");
//                        String QryRespBy=jsonObject.getString("QryRespBy");
//                        String QryStatus=jsonObject.getString("QryStatus");
//                        String RespDate=jsonObject.getString("RespDate");
//                        String RespFlg=jsonObject.getString("RespFlg");
//                        String RespQryno=jsonObject.getString("RespQryno");
//                        String TopicName=jsonObject.getString("TopicName");
//                        String Topicqryid=jsonObject.getString("Topicqryid");
//                        String empname=jsonObject.getString("empname");
//                        String errormessage=jsonObject.getString("errormessage");
//                        String studentname=jsonObject.getString("studentname");


//
//                        mySqliteDataBase.insertchat(BranchId,ChatQry,ChatTopicId_Fk,ChatTopicName,Chat_ResQryText,ClassNm,Createdate,Flg,FwdEmpId,LoopValue,OtherTopicName,QryCreatedbyId,QryNo,QryRespBy,QryStatus,RespDate,RespFlg,RespQryno,TopicName,Topicqryid,empname,errormessage,studentname);
//Log.e("inserted","Inserted");

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Log.e("senddetrails", senddetails + "");
                Collections.reverse(topicslist); // ADD THIS LINE TO REVERSE ORDER!
                adapter = new SimpleAdapter(Chat_Topic_Activity.this, topicslist, R.layout.topic_list_layout, from, to);
                listView.setAdapter(adapter);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("chat_error", error + "");
            }
        });
        requestQueue.add(jsonArrayRequest);


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Chat_Topic_Activity.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        finish();
        super.onBackPressed();
    }
}
