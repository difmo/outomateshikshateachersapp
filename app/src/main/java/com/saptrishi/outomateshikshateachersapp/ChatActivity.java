package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

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
import com.saptrishi.outomateshikshateachersapp.adapter.ShowMsgAdapter;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    public static String chatidnumber;
    static TextView tv_createdby, tv_topicname, tv_topicno, tv_subtopic;
    TextView tv_querymsg, tv_responseQuery;
    TextView tv_queryDate, tv_resDate;
    static EditText editText;
    static LinearLayout linearLayout, subtopic_layout;
    ImageView iv_send;
    static Button send_button;
    static RequestQueue requestQueue;
    static LinearLayout chat_layout;
    String branchid, teacherid;
    static String TopicId, Topicquiryid, queryno, qrycreatedbyid, branch, flag;

    static List<String> listQuerycreatedbyid = new ArrayList<>();
    static List<String> listChatQry = new ArrayList<>();
    static List<String> listCreatedate = new ArrayList<>();
    static List<String> flaglist = new ArrayList<>();

    static ListView msg_list;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        chatidnumber = intent.getStringExtra("Query");
        final SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        branchid = sharedPreferences.getString("Branchid", "");
        teacherid = sharedPreferences.getString("empid", "");

        {
            Toolbar toolbar = findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            LinearLayout linearLayout = findViewById(R.id.actionbar);
            TextView textView = new TextView(this);
            textView.setText("Chat Support");
            textView.setGravity(Gravity.CENTER);
            textView.setTextSize(25);
            textView.setTextColor(getResources().getColor(R.color.white));
            linearLayout.addView(textView);
        }
        tv_createdby = findViewById(R.id.createdby);
        tv_topicname = findViewById(R.id.Topics);
        tv_topicno = findViewById(R.id.query_no);
        tv_subtopic = findViewById(R.id.subtopic);
        subtopic_layout = findViewById(R.id.subtopic_layout);
        subtopic_layout.setVisibility(View.GONE);
        editText = findViewById(R.id.et_reply);
        send_button = findViewById(R.id.btn_send);
        chat_layout = findViewById(R.id.chat_layout);
        msg_list = findViewById(R.id.msg_list);

//        Parcelable state = msg_list.onSaveInstanceState();

        requestQueue = Volley.newRequestQueue(this);
        featchdatafromvolley(ChatActivity.this, chatidnumber);

        ImageView backpressed = findViewById(R.id.backpressed);
        backpressed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        send_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String chatmsg = editText.getText().toString().trim();
                if (chatmsg.isEmpty()) {
                    editText.setError("Enter Message First");
                    editText.requestFocus();
                } else {

                    sentresponse(chatmsg);
                }


            }
        });
    }


    public static void featchdatafromvolley(final Context context, String chatidnumber) {

        listQuerycreatedbyid.clear();
        listChatQry.clear();
        listCreatedate.clear();
        flaglist.clear();


        final SharedPreferences sharedPreferences = context.getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String branchid = sharedPreferences.getString("Branchid", "");
        final String teacherid = sharedPreferences.getString("empid", "");
        String url = Url_Symbol.url_sikhsha + "GetChatqryreply_show/TopicFor/Tech/brid/" + branchid + "/CreatedbyId/" + teacherid + "/qryno/" + chatidnumber;
        Log.e("url11111", url);

        final JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("chatresponse", response + "");
                try {
                    JSONObject jsonObject = response.getJSONObject(0);
                    Log.e("josnss", jsonObject.toString());
                    JSONArray jsonArray = jsonObject.getJSONArray("QryResp");


                    for (int i = 0; i < response.length(); i++) {
                        Log.e("jons", jsonObject.getString("QryNo"));
                        tv_topicno.setText(jsonObject.getString("QryNo"));
                        tv_topicname.setText(jsonObject.getString("ChatTopicName"));
                        tv_createdby.setText(jsonObject.getString("QryCreatedbyId"));
                        if (jsonObject.getString("ChatTopicName").equalsIgnoreCase("Other")) {
                            subtopic_layout.setVisibility(View.VISIBLE);
                            tv_subtopic.setText(jsonObject.getString("OtherTopicName"));
                        }
//
//                        LinearLayout linearLayout = new LinearLayout(ChatActivity.this);
//                        linearLayout.setOrientation(LinearLayout.VERTICAL);
//                        linearLayout.setBackground(getResources().getDrawable(R.drawable.right_msg));
//                        linearLayout.setPadding(80, 10, 30, 10);
//                        linearLayout.setLayoutParams(layoutParams);
//
//
//                        TextView respby = new TextView(ChatActivity.this);
//                        respby.setText(jsonObject.getString("QryCreatedbyId"));
//                        respby.setTextColor(getResources().getColor(R.color.cyan));
//                        respby.setTextSize(16);
//                        respby.setPadding(0, 10, 0, 5);
//                        TextView textView = new TextView(ChatActivity.this);
//                        textView.setText(jsonObject.getString("ChatQry"));
//                        TextView qrydate = new TextView(ChatActivity.this);
//                        qrydate.setGravity(Gravity.RIGHT);
//                        qrydate.setText(jsonObject.getString("Createdate"));
//                        qrydate.setTextColor(getResources().getColor(R.color.color_gray_light));
//                        qrydate.setTextSize(10);
//                        linearLayout.addView(respby);
//                        linearLayout.addView(textView);
//                        linearLayout.addView(qrydate);
//                        chat_layout.addView(linearLayout);
                        listQuerycreatedbyid.add(jsonObject.getString("QryCreatedbyId"));
                        listChatQry.add(jsonObject.getString("ChatQry"));
                        listCreatedate.add(jsonObject.getString("Createdate"));
                        flaglist.add("Adm");
                        TopicId = jsonObject.getString("ChatTopicId_Fk");
                        Topicquiryid = jsonObject.getString("Topicqryid");
                        queryno = jsonObject.getString("QryNo");
                        qrycreatedbyid = teacherid;
                        branch = jsonObject.getString("BranchId");

                    }

                    for (int j = 0; j < jsonArray.length(); j++) {
                        JSONObject jsonObject1 = jsonArray.getJSONObject(j);
                        listQuerycreatedbyid.add(jsonObject1.getString("QryRespBy"));
                        listChatQry.add(jsonObject1.getString("Chat_ResQryText"));
                        listCreatedate.add(jsonObject1.getString("RespDate"));
                        flaglist.add(jsonObject1.getString("RespFlg"));
//                        String RespFlg = jsonObject1.getString("RespFlg");
//                        String Chat_ResQryText = jsonObject1.getString("Chat_ResQryText");
//                        String RespDate = jsonObject1.getString("RespDate");
//                        String QryRespBy = jsonObject1.getString("QryRespBy");
//
//                        Log.e("resoiwewe", RespDate + ",," + RespFlg + ",," + Chat_ResQryText + ",," + QryRespBy);
//
//                        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(
//                                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//                        layoutParams1.setMargins(200, 0, 10, 20);
//                        layoutParams1.gravity = Gravity.RIGHT;
//
//                        if (RespFlg.equalsIgnoreCase("Tech")) {
//                            Log.e("chatmsss", 1 + "TECH");
//                            if (!Chat_ResQryText.equalsIgnoreCase("")) {
//
//                                LinearLayout linearLayout = new LinearLayout(ChatActivity.this);
//                                linearLayout.setOrientation(LinearLayout.VERTICAL);
//                                linearLayout.setPadding(35, 15, 50, 15);
//                                linearLayout.setLayoutParams(layoutParams1);
//                                linearLayout.setBackground(getResources().getDrawable(R.drawable.left_msg));
//                                linearLayout.setGravity(Gravity.RIGHT);
//
//                                TextView respby = new TextView(ChatActivity.this);
//                                respby.setText(QryRespBy);
//                                respby.setTextColor(getResources().getColor(R.color.cyan));
//                                respby.setTextSize(16);
//                                respby.setPadding(0, 10, 0, 5);
//                                TextView textView = new TextView(ChatActivity.this);
//                                textView.setText(Chat_ResQryText);
//                                TextView respdate = new TextView(ChatActivity.this);
//                                respdate.setText(RespDate);
//                                respdate.setGravity(Gravity.RIGHT | Gravity.BOTTOM);
//                                respdate.setTextSize(10);
//                                respdate.setTextColor(getResources().getColor(R.color.color_gray_light));
//
//                                linearLayout.addView(respby);
//                                linearLayout.addView(textView);
//                                linearLayout.addView(respdate);
//                                chat_layout.addView(linearLayout);
//
//                            }
//
//                        } else {
//                            Log.e("chatmsss", 1 + "ADM");
//                            LinearLayout linearLayout = new LinearLayout(ChatActivity.this);
//                            linearLayout.setOrientation(LinearLayout.VERTICAL);
//                            linearLayout.setBackground(getResources().getDrawable(R.drawable.right_msg));
//                            linearLayout.setPadding(80, 30, 30, 0);
//                            linearLayout.setLayoutParams(layoutParams);
//
//                            TextView respby = new TextView(ChatActivity.this);
//                            respby.setText(QryRespBy);
//                            respby.setTextColor(getResources().getColor(R.color.cyan));
//                            respby.setTextSize(16);
//                            respby.setPadding(0, 10, 0, 5);
//                            TextView textView = new TextView(ChatActivity.this);
//                            textView.setText(Chat_ResQryText);
//                            TextView qrydate = new TextView(ChatActivity.this);
//                            qrydate.setGravity(Gravity.RIGHT);
//                            qrydate.setPadding(0, 0, 10, 30);
//                            qrydate.setText(RespDate);
//                            qrydate.setTextColor(getResources().getColor(R.color.color_gray_light));
//                            qrydate.setTextSize(10);
//                            linearLayout.addView(respby);
//                            linearLayout.addView(textView);
//                            linearLayout.addView(qrydate);
//                            chat_layout.addView(linearLayout);
////
//                        }
                    }
                    Log.e("LIstesss", listQuerycreatedbyid.size() + "\n" + listChatQry.size() + "\n" + listCreatedate.size() + "\n" + flaglist.size() + "\n");
                    final ShowMsgAdapter showMsgAdapter = new ShowMsgAdapter(context, flaglist, listQuerycreatedbyid, listChatQry, listCreatedate);
                    msg_list.setAdapter(showMsgAdapter);
//                    msg_list.smoothScrollToPosition(flaglist.size());
                    msg_list.post(new Runnable() {
                        @Override
                        public void run() {
                            // Select the last row so it will scroll into view...
                            msg_list.setSelection(showMsgAdapter.getCount() - 1);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("chaterror", error + "");
            }
        });
        requestQueue.add(jsonArrayRequest);


    }


    public void sentresponse(String chat_msg) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String url = Url_Symbol.url_sikhsha + "Chat_qryreply";
        Log.e("res_url", url);
        JSONObject jsonObject = new JSONObject();
        try {
            Log.e("aa3222ChatTopicId_Fk", TopicId);
            Log.e("aa3222Topicqryid", Topicquiryid);
            Log.e("aa3222ChatQry", chat_msg);
            Log.e("aa3222QryNo", queryno);
            Log.e("aa3222QryCreatedbyId", qrycreatedbyid);
            Log.e("aa3222BranchId", branch);
            Log.e("aa3222Flg", "Tech");

            jsonObject.put("ChatTopicId_Fk", TopicId);
            jsonObject.put("Topicqryid", Topicquiryid);
            jsonObject.put("ChatQry", chat_msg);
            jsonObject.put("QryNo", queryno);
            jsonObject.put("QryCreatedbyId", qrycreatedbyid);
            jsonObject.put("BranchId", branch);
            jsonObject.put("Flg", "Tech ");

            final String requestbody = jsonObject.toString();


            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.equals("200")) {
                        Log.e("response", "200" + response);
//                        iv_send.setVisibility(View.VISIBLE);
                        featchdatafromvolley(ChatActivity.this, chatidnumber);
                        editText.setText("");
                    } else {
                        Log.e("response", "Failed" + response);
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("vo_error", error + "");
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
            requestQueue.add(stringRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChatActivity.this, Chat_Topic_Activity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
        super.onBackPressed();
    }
}
