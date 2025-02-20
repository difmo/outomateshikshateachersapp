package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.util.Log;

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
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class Sending_AttendenceToDB {
    RequestQueue requestQueue;
    Context mcontext;

    public Sending_AttendenceToDB(Context context) {
        this.mcontext = context;
    }

    public void sendtodb() throws JSONException {
        if (Connectivity.isConnected(mcontext)) {
            MySqliteDataBase sqliteDataBase = new MySqliteDataBase(mcontext);
//
            Cursor cursor = sqliteDataBase.featchattendance();

            if (cursor.moveToFirst()) {
                do {
                    senddatatovolley(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7), cursor.getString(8),cursor.getString(9));
                    Log.e("cursorvlaue", cursor.getString(0) + "," + cursor.getString(1) + "," + cursor.getString(2) + "," + cursor.getString(3) + "," + cursor.getString(4) + "," + cursor.getString(5) + "," + cursor.getString(6) + "," + cursor.getString(7) + "," + cursor.getString(8)+","+cursor.getString(9));
                }
                while (cursor.moveToNext());
                sqliteDataBase.deleteattendancetable();

            }

        }

    }


    public void senddatatovolley(String clsstrucid, String Stuid, String subjectid, String att_status, String empid, String att_date, String att_time, String branchid, String sessionid, String description) {


        requestQueue = Volley.newRequestQueue(mcontext);
        String url = Url_Symbol.url + "clswiseAttendance";
        Log.e("dkcheck",""+url);
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("ClsStruId", clsstrucid);
            Log.e("clsstrucid",""+clsstrucid);
            jsonObject.put("StuId", Stuid);
            Log.e("Stuid",""+Stuid);
            jsonObject.put("SubjectId", subjectid);
            Log.e("subjectid",""+subjectid);
            jsonObject.put("Attan_Status", att_status);
            Log.e("att_status",""+att_status);
            jsonObject.put("EmpId", empid);
            Log.e("empid",""+empid);
            jsonObject.put("Attan_date", att_date);
            Log.e("att_date",""+att_date);//2021-06-15
            jsonObject.put("Attan_time", att_time);
            Log.e("att_time",""+att_time);
            jsonObject.put("Attan_brid", branchid);
            Log.e("branchid",""+branchid);
            jsonObject.put("Attan_Sessionid", sessionid);
            Log.e("sessionid",""+sessionid);
            jsonObject.put("Description", description);
            Log.e("Description",""+description);


        } catch (JSONException e) {
            e.printStackTrace();
        }
        final String requestBody = jsonObject.toString();
//http://teacherappservice.outomate.com/TeacherAppService.svc/clswiseAttendance
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("Success", "" + response);
                if (response.equals("200")) {
                    Log.e("Success", "success" + response);

                } else {
                    Log.e("Failed", "failed" + response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("error123", error + "");
            }
        }) {

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() {
                try {
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
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


    }


    public void setvaluesinlist() {
        final RequestQueue requestQueue = Volley.newRequestQueue(mcontext);

        SharedPreferences sharedPreferences = mcontext.getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String branchid = sharedPreferences.getString("Branchid", "");
        String teacherid = sharedPreferences.getString("empid", "");
        String url = Url_Symbol.url_sikhsha + "GetChatTopicqryshow/TopicFor/Tech/brid/" + branchid + "/CreatedbyId/" + teacherid;
        Log.e("chatUrl", url);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                Log.e("chat_response", response + "");

                for (int i = 0; i < response.length(); i++) {

                    try {
                        JSONObject jsonObject = response.getJSONObject(i);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("chat_error", error + "");
            }
        });
        requestQueue.add(jsonArrayRequest);


    }


}
