package com.saptrishi.outomateshikshateachersapp.ClassWorkView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.snackbar.Snackbar;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.Classwork_Activity;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.Classworkviewadapter;
import com.saptrishi.outomateshikshateachersapp.model.ClassworkviewList;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ClassWorkView extends AppCompatActivity {
    TextView textView_date, tv_subjectname;
    TextView tv_versionnames;
    ImageView imageView;
    Calendar myCalendar;
    String className;// new
    SharedPreferences sf;// new
    TextView textViewClassName;//new
    RecyclerView custom_rv;
    Activity activity;
    RequestQueue requestQueue;
    List<ClassworkviewList> ClassviewModel = new ArrayList<>();

    DatePickerDialog.OnDateSetListener dateSetListener;
    AlertDialog.Builder builder;
    String getempid = "", subjectid = "", classid = "", subjectName = "" ;
  String Brid ;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_work_view);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        custom_rv = findViewById(R.id.recycler_view);
        activity = this;
        LinearLayout backbutton = findViewById(R.id.backpressed);
        requestQueue = Volley.newRequestQueue(this);
        textView_date = findViewById(R.id.tv_homeworkDate);
        imageView = findViewById(R.id.btn_calender);
        tv_subjectname = findViewById(R.id.tv_subjectname);
        sf=getSharedPreferences("classworkViewItem",MODE_PRIVATE);// new
        className= getIntent().getStringExtra("class_name");// new
        //get values from prev activity
        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        getempid = getIntent().getStringExtra("getempid");
        subjectid = getIntent().getStringExtra("subjectid");
        Brid =  getIntent().getStringExtra("branchid");
        classid = getIntent().getStringExtra("classid");
        subjectName = getIntent().getStringExtra("subjectName");
        textViewClassName=(TextView)findViewById(R.id.className);// new

        textViewClassName.setText(className);// new

        tv_subjectname.setText(subjectName);

        myCalendar = Calendar.getInstance();

        builder = new AlertDialog.Builder(this);

        //String myFormat = "yyyy-MM-dd"; //In which you need put here
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        textView_date.setText(sdf.format(myCalendar.getTime()));
        //service call
//        String myFormat_server = "yyyy-MM-dd";
//        SimpleDateFormat sdf12 = new SimpleDateFormat(myFormat_server, Locale.US);
//        String ser_date=sdf12.format(myCalendar.getTime());
        getdata(serv_date (), getempid, subjectid, classid,Brid);
        //getdata((textView_date.getText().toString()), getempid, subjectid, classid);


//        updateLabel();
        dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };


        textView_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencalender();
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opencalender();
            }
        });

        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public String serv_date ()
    {
        String ser_date="";
        String myFormat_server = "yyyy-MM-dd";
        SimpleDateFormat sdf12 = new SimpleDateFormat(myFormat_server, Locale.US);
        ser_date=sdf12.format(myCalendar.getTime());
        return ser_date;
    }
    private void opencalender() {
        new DatePickerDialog(ClassWorkView.this, R.style.DialogTheme, dateSetListener, myCalendar
                .get(Calendar.YEAR),
                myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
    }

    private void updateLabel() {
        //String myFormat = "yyyy-MM-dd"; //In which you need put here
        String myFormat = "dd-MMM-yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//        customListView.setAdapter(null);
        textView_date.setText(sdf.format(myCalendar.getTime()));
        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().before(cal.getTime()) || myCalendar.getTime().equals(cal.getTime())) {
            if (!Connectivity.isConnected(getApplicationContext())) {
                //              showdata(textView_date.getText().toString());
                Snackbar.make(findViewById(android.R.id.content), "No Internet", Snackbar.LENGTH_SHORT).show();
            } else {
                getdata(serv_date (), getempid, subjectid, classid,Brid);
                //getdata(textView_date.getText().toString(), getempid, subjectid, classid);
            }
        } else {
            builder.setMessage("You have no class on this date..!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            startActivity(new Intent(ClassWorkView.this, Classwork_Activity.class));
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
            Toast.makeText(ClassWorkView.this,"No record Found !",Toast.LENGTH_SHORT).show();
        }


//        getDatas();


    }

    private void getdata( String date, String getempid, String subjectid, String classid, String brd) {
        //  private void getHomeWorkfromdb(final String Date) {
        //SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        //String brd=sf.getString("branchidd","");
        String url = Url_Symbol.url+"ShowCW_Techdetail/DOStudy/" + date + "/Empid/" + getempid + "/class_struid/" + classid + "/subjectid/" + subjectid + "/Brid/"+brd+"";
        Log.e("ClassWork", url);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ClassviewModel.clear();

                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = (JSONObject) response.get(i);
                                String ermsg = jsonObject.getString("Errormsg");
                                if (ermsg.equalsIgnoreCase("Correct")) {

                                    String CW_Upload_path_img = jsonObject.getString("CW_Upload_path_img");
                                    String ContName = jsonObject.getString("ContName");
                                    String ContytypID = jsonObject.getString("ContytypID");
                                    String Errormsg = jsonObject.getString("Errormsg");
                                    String Filename = jsonObject.getString("Filename");
                                    String TopicName = jsonObject.getString("TopicName");
                                    String VideoLink = jsonObject.getString("VideoLink");

                                    ClassworkviewList classviewmodel = new ClassworkviewList();
                                    classviewmodel.setCW_Upload_path_img(CW_Upload_path_img);
                                    classviewmodel.setContName(ContName);
                                    classviewmodel.setContytypID(ContytypID);
                                    classviewmodel.setErrormsg(Errormsg);
                                    classviewmodel.setFilename(Filename);
                                    classviewmodel.setTopicName(TopicName);
                                    classviewmodel.setVideoLink(VideoLink);
                                    ClassviewModel.add(classviewmodel);

                                    List<ClassworkviewList> classviewModelChild = new ArrayList<>();
                                    JSONArray filelistcw = jsonObject.getJSONArray("filelistcw");
                                    for (int j = 0; j < filelistcw.length(); j++) {
                                        JSONObject jsonObjectChild = (JSONObject) filelistcw.get(j);

                                        String CW_Upload_path_imgChild = jsonObjectChild.getString("CW_Upload_path_img");
                                        String ContytypIDChild = jsonObjectChild.getString("ContytypID");
                                        String FilenameChild = jsonObjectChild.getString("Filename");
                                        String TopicNameChild = jsonObjectChild.getString("TopicName");
                                        String VideoLinkChild = jsonObjectChild.getString("VideoLink");

                                        ClassworkviewList classviewmodel_child = new ClassworkviewList();
                                        classviewmodel_child.setCW_Upload_path_img(CW_Upload_path_imgChild);
                                        classviewmodel_child.setContytypID(ContytypIDChild);
                                        classviewmodel_child.setFilename(FilenameChild);
                                        classviewmodel_child.setTopicName(TopicNameChild);
                                        classviewmodel_child.setVideoLink(VideoLinkChild);
                                        classviewModelChild.add(classviewmodel_child);
                                    }
                                    ClassviewModel.get(i).setFilelistcw(classviewModelChild);

                                } else {
                                    if (ermsg != null && !ermsg.equalsIgnoreCase(""))
                                    {
                                        // Toast.makeText(activity, ermsg, Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(activity, "Error the Service!", Toast.LENGTH_SHORT).show();
                                    }

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.e("catch", e + "");
                            }

                        }
                        if (ClassviewModel != null && ClassviewModel.size() > 0) {//adapter
                            Classworkviewadapter Recyclerview_Sip_Swp = new Classworkviewadapter(activity, ClassviewModel);
                            custom_rv.setHasFixedSize(true);
                            custom_rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            custom_rv.setAdapter(Recyclerview_Sip_Swp);
                        } else {
                            ClassviewModel.clear();
                            Classworkviewadapter Recyclerview_Sip_Swp = new Classworkviewadapter(activity, ClassviewModel);
                            custom_rv.setHasFixedSize(true);
                            custom_rv.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
                            custom_rv.setAdapter(Recyclerview_Sip_Swp);
                            Toast.makeText(activity, "No Record Found!", Toast.LENGTH_SHORT).show();
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
    protected void onResume() {
        super.onResume();

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    1);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    2);
        }
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(ClassWorkView.this, Classwork_Activity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
    }
}