package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class newUserActivity extends AppCompatActivity {
    TextView tv_versionnames;

    LinearLayout linearLayoutParentID, linearLayoutotp, linearLayoutpassword;
    EditText loginid, etotp, branchid, newpassword, conpassword;
    Button btn;
    String  otp;
    ImageView viewPassword;
    int chk = 0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_user);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
         sharedPreferences=getSharedPreferences("ShikshaContainer1",MODE_PRIVATE);
        linearLayoutParentID = findViewById(R.id.llparentID);
        linearLayoutotp = findViewById(R.id.llotp);
        linearLayoutpassword = findViewById(R.id.llsetpassword);

        branchid = findViewById(R.id.et_newbrcode);
        loginid = findViewById(R.id.et_newlogin);
        etotp = findViewById(R.id.mobotp);
        newpassword = findViewById(R.id.et_newpassword);

        conpassword = findViewById(R.id.et_conewpassword);
        viewPassword = findViewById(R.id.ivViewpassword);
        Intent intent = getIntent();
        String str = intent.getStringExtra("branchid");
        branchid.setText(str);
        String login = intent.getStringExtra("login");
        loginid.setText(login);
        btn = findViewById(R.id.submit);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {


                if (!loginid.getText().toString().trim().isEmpty() && !branchid.getText().toString().trim().isEmpty()) {
                    String branch = branchid.getText().toString().trim();
                    String login= loginid.getText().toString().trim();
                    genratingotp(branch,login);
                    linearLayoutParentID.setVisibility(View.GONE);
                    linearLayoutotp.setVisibility(View.VISIBLE);




                    viewPassword.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(etotp.getInputType() == InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) {
                                etotp.setInputType( InputType.TYPE_CLASS_TEXT |
                                        InputType.TYPE_TEXT_VARIATION_PASSWORD);
                            }else {
                                etotp.setInputType( InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD );
                            }
                            etotp.setSelection(etotp.getText().length());
                        }

                    });


                    btn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            otp = etotp.getText().toString().trim();
                            Log.e("pid", otp);
                            String mno=sharedPreferences.getString("fathernumber","");
                            if (!otp.isEmpty() && !mno.isEmpty()) {


//                                loginfo/otp/1245/mobile/9795758575

                                String url=Url_Symbol.url+"loginfo/otp/"+otp+"/mobile/"+mno;

                                RequestQueue requestQueue=Volley.newRequestQueue(newUserActivity.this);
                                JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                                    @Override
                                    public void onResponse(JSONArray response) {

                                        try {
                                            JSONObject jsonObject= (JSONObject) response.get(0);

                                            Log.e("responseafterotp",response+"");
                                            if (jsonObject.get("errormessage").equals("Correct"))
                                            {
//                                                Toast.makeText(newUserActivity.this, "next level", Toast.LENGTH_SHORT).show();
                                                settingPassword();
                                            }
                                            else
                                            {
                                                Toast.makeText(newUserActivity.this, jsonObject.get("errormessage")+"", Toast.LENGTH_SHORT).show();
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
//                                requestQueue.add(jsonArrayRequest);
                                jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                                        0,
                                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                                requestQueue.add(jsonArrayRequest);


                            } else {
                                etotp.setError("Kindly fill all fields");
                                etotp.requestFocus();
                                Toast.makeText(newUserActivity.this, "Kindly fill Otp", Toast.LENGTH_SHORT).show();
                            }


                        }
                    });

                } else {
                    branchid.setError("Fill a App ID" );
                    loginid.setError("Fill Employee ID");
                    branchid.requestFocus();
                }
            }
        });

    }

    private void settingPassword() {

        linearLayoutotp.setVisibility(View.GONE);
        linearLayoutpassword.setVisibility(View.VISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPassword = newpassword.getText().toString().trim();
                String conPassword = conpassword.getText().toString().trim();
                if (newPassword.equals(conPassword) && !newPassword.isEmpty()) {

//                    Stu_Password/staffclit_id/003}/app_brid/00000/spass/12456

                    String url=Url_Symbol.url+"Stu_Password/staffclit_id/"+loginid.getText().toString().trim()+"/app_brid/"+branchid.getText().toString().trim()+"/spass/"+newPassword;
                    RequestQueue requestQueue=Volley.newRequestQueue(newUserActivity.this);
                    JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.e("responsesetOtp",response+"");
                            startActivity(new Intent(newUserActivity.this, LoginActivity.class));
                            finish();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    });
//                    requestQueue.add(jsonArrayRequest);
                    jsonArrayRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(jsonArrayRequest);


                } else {
                    conpassword.setError("Kindly enter same password!!!");
                    conpassword.requestFocus();
                }
            }
        });
    }

    private void genratingotp(String branch, String login) {

        final ProgressDialog pDialog = new ProgressDialog(this);

        pDialog.setTitle("Loading...");
        pDialog.setMessage("Otp send on registered number");
        pDialog.setCancelable(false);
        pDialog.setCanceledOnTouchOutside(false);
        pDialog.show();
//        loginfo/logid/003/brid/00000

        String url= Url_Symbol.url+ "loginfo/logid/"+login+"/brid/"+branch;
        Log.e("urlgenratingotp",url);

        RequestQueue requestQueue=Volley.newRequestQueue(newUserActivity.this);

        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    Log.e("responsesetOtp",response+"");
                    JSONObject jsonObject= (JSONObject) response.get(0);

                    if (jsonObject.get("errormessage").equals("Correct"))
                    {
                        Log.e("Otp",jsonObject.get("otp")+" is the Otp............................................" );
                        Log.e("fmobileno",jsonObject.get("mobno").toString());
                        SharedPreferences.Editor editor =sharedPreferences.edit();
                        editor.putString("fathernumber",jsonObject.get("mobno")+"");
                        editor.apply();
                        pDialog.dismiss();
                    }
                    else
                    {
                        pDialog.dismiss();
                        Toast.makeText(newUserActivity.this, jsonObject.get("errormessage")+" kindly retry", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(newUserActivity.this,newUserActivity.class));
                        finish();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(newUserActivity.this, error+"", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
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
        super.onBackPressed();
        startActivity(new Intent(newUserActivity.this, LoginActivity.class));
        finish();
    }
}
