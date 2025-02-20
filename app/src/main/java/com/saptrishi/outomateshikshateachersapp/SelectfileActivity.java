package com.saptrishi.outomateshikshateachersapp;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.multipleImageSelection.ImagesActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SelectfileActivity extends AppCompatActivity {

    TextView textview;
    TextView tv_versionnames;
    ImageView image;
    Button upload;

    Bitmap bitmap;
    String imageString;
    private static final int PICK_IMAGE_REQUEST = 99;
    private static final int PERMISSION_REQUEST_CODE = 1;
    TextView edittext, className, subjectName;
    Calendar myCalendar;
    MySqliteDataBase mySqliteDataBase;
    String getempid;
    String subjectid;
    String classid;
    String finaldaytimetableid;
    public boolean chk = true;
    int PICK_IMAGE_MULTIPLE = 1;// change

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selectfile);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
//        actionBar.hide();
        LinearLayout backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Intent intent = getIntent();
        getempid = intent.getStringExtra("getempid");// imp
        subjectid = intent.getStringExtra("subjectid");
        classid = intent.getStringExtra("classid");
        finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");

        mySqliteDataBase = new MySqliteDataBase(this);

        edittext = (TextView) findViewById(R.id.homeWorkDate);
        className = (TextView) findViewById(R.id.className);
        subjectName = (TextView) findViewById(R.id.subjectname);
        image = (ImageView) findViewById(R.id.image);
        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

        String strimg = sharedPreferences.getString("imglocation", "");

        assert strimg != null;
        if (!strimg.isEmpty()) {
            byte[] encodeByte = Base64.decode(strimg, Base64.DEFAULT);
            bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            image.setImageBitmap(bitmap);
        }
        TextView childName = findViewById(R.id.showchildname);
        childName.setText(sharedPreferences.getString("empname", ""));
        Cursor cursor1 = mySqliteDataBase.fetchparticularHomework(getempid, subjectid, classid, finaldaytimetableid);
        cursor1.moveToFirst();

        className.setText(cursor1.getString(2));
        subjectName.setText(cursor1.getString(12));

        upload = (Button) findViewById(R.id.upload);

        myCalendar = Calendar.getInstance();


//        image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showFileChooser();
//                upload.setVisibility(View.VISIBLE);
//            }
//        });

//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo1);
//        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
//        byte[] imageBytes = baos.toByteArray();
//        Log.e("byte", imageBytes + "");
//        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
//        Log.e("Base64", imageString);
//        uploaduserimage();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

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

        {
            // Code for above or equal 23 API Oriented Device
            // Your Permission granted already .Do next code

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edittext.getText().toString().isEmpty()) {
                        Toast.makeText(SelectfileActivity.this, "Kindly Mention Submission date before Upload", Toast.LENGTH_LONG).show();// Kindly enter date new toasr
                    } else {
                        if (image.getResources().getResourceName(R.id.image).equals(R.drawable.uploadfile)) {

                            Toast.makeText(SelectfileActivity.this, "Please select a file ", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("img", image.getResources() + "");
                            Log.e("img12", image.getResources().getResourceName(R.id.image) + "");

                            Intent intent = new Intent(SelectfileActivity.this, ImagesActivity.class);// new
                            intent.putExtra("getempid", getempid);
                            intent.putExtra("subjectid", subjectid);
                            intent.putExtra("classid", classid);
                            intent.putExtra("finaldaytimetableid", finaldaytimetableid);
                            intent.putExtra("date", edittext.getText().toString());//date
                            startActivity(intent);
                            // sendTokenToServer();
                        }

                    }


                    // showFileChooser();
                }
            });

            upload.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edittext.getText().toString().isEmpty()) {
                        Toast toast = Toast.makeText(SelectfileActivity.this, "Kindly Mention Submission date before Upload", Toast.LENGTH_LONG);// Kindly enter date new toast
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();


                    } else {
                        if (image.getResources().getResourceName(R.id.image).equals(R.drawable.uploadfile)) {

                            Toast.makeText(SelectfileActivity.this, "Please select a file ", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("img", image.getResources() + "");
                            Log.e("img12", image.getResources().getResourceName(R.id.image) + "");


                            sendTokenToServer();
                        }

                    }
                }
            });
        }


        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
//                         new DatePickerDialog(HomeWorkActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new DatePickerDialog(SelectfileActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
//        customListView=(ListView)findViewById(R.id.custom_list_view);
//        userInfos=new ArrayList<>();
//        customListAdapter=new HomeWorkCustomListAdapter(userInfos,this);
//        customListView.setAdapter(customListAdapter);


    }

    // getting all the datas
//    private void getDatas() {
////        for(int count=0;count<names.length;count++){
//        mySqliteDataBase = new MySqliteDataBase(this);
//
//        SharedPreferences sfa = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        final String Stuid = sfa.getString("childrenid", "");
//        Cursor cursor = mySqliteDataBase.fetchHomeworkTable(edittext.getText().toString(),Stuid);
//        Log.e("cursorCount", cursor.getCount()+"");
//        customListView.setAdapter(null);
//        userInfos.clear();
//        if (cursor.moveToFirst()) {
//            do {
//                Log.e("subject", cursor.getString(1));
//                String Subject = cursor.getString(0);
//                String DueDate = cursor.getString(1);
//                String subjectId=cursor.getString(2);
//                userInfos.add(new UserInfo(Subject, DueDate, photos[0],subjectId));
//            } while (cursor.moveToNext());
//        } else
//        {
//            if (!Connectivity.isConnected(getApplicationContext()))
//                Snackbar.make(findViewById(android.R.id.content), "No Data .... Try again with internet on", Snackbar.LENGTH_LONG).show();
//
//            Log.e("subject", "nothing to show");
//
//        }
//
//        customListView.setAdapter(null);
//
//        customListAdapter = new HomeWorkCustomListAdapter(userInfos, this,edittext.getText().toString(),Stuid);
//        customListView.setAdapter(null);
//        customListAdapter.notifyDataSetChanged();
//        customListView.setAdapter(customListAdapter);
//        customListAdapter.notifyDataSetChanged();
////        }
//    }

    private void updateLabel() {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().after(cal.getTime()))
            edittext.setText(sdf.format(myCalendar.getTime()));
        else {
            Toast.makeText(this, "wrong date", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && data != null && data.getData() != null)//change
        {
            Uri filePath = data.getData();
            try {
                //Getting the Bitmap from Gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);


                ByteArrayOutputStream baos = new ByteArrayOutputStream();
//                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo1);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                Log.e("byte", imageBytes + "");
                imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                Log.e("Base64", imageString);

                //Setting the Bitmap to ImageView
                chk = true;

                image.setImageBitmap(bitmap);
            } catch (IOException e) {
                Log.e("byte", e + "");
                e.printStackTrace();
            }
        }
    }


//    @Override
//    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case PERMISSION_REQUEST_CODE:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    Toast.makeText(SelectfileActivity.this, "Permission Granted Successfully! ", Toast.LENGTH_LONG).show();
//                } else {
//                    Toast.makeText(SelectfileActivity.this, "Permission Denied 🙁 ", Toast.LENGTH_LONG).show();
//                }
//                break;
//        }
//    }


    private void showFileChooser() {

        upload.setVisibility(View.VISIBLE);

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);//change
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);//change

    }

    public void sendTokenToServer() {

        Calendar mycal = Calendar.getInstance();

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String todayDate = sdf.format(mycal.getTime());

        Cursor cursor = mySqliteDataBase.fetchparticularHomework(getempid, subjectid, classid, finaldaytimetableid);
        cursor.moveToFirst();


        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            //this is the url where you want to send the request
            String url = Url_Symbol.url + "/UploadFile";
            Log.e("edittext", edittext.getText().toString());
            if (!edittext.getText().toString().trim().isEmpty()) {

                String img = getStringImage(bitmap);
                if (img != null) {

                    final ProgressDialog pDialog = new ProgressDialog(this);

                    pDialog.setTitle("Uploading...");
                    pDialog.setMessage("Uploading your file");
                    pDialog.setCancelable(false);
                    pDialog.setCanceledOnTouchOutside(false);
                    pDialog.show();
                    JSONObject jsonBody = new JSONObject();
                    jsonBody.put("stream64", img);
                    jsonBody.put("FinalDayTimeTable_Id_fk", cursor.getString(5));
                    jsonBody.put("clsstrucid", cursor.getString(15));
                    jsonBody.put("SubjectMasterID", cursor.getString(11));
                    jsonBody.put("DOH", todayDate);
                    jsonBody.put("BranchId", cursor.getString(1));
                    jsonBody.put("DOS", edittext.getText().toString());
                    jsonBody.put("Createdby", cursor.getString(13));
                    jsonBody.put("SubjectName", cursor.getString(12));
                    jsonBody.put("Classname", cursor.getString(2));

                    final String requestBody = jsonBody.toString();
                    // Request a string response from the provided URL.
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Display the response string.
                            Log.e("Shantanu", response);// url
                            pDialog.dismiss();

                            if (response.equals("200")) {
//                               Log.e("ResponseinElse", response);
                                //Log.e("ResponseSuccess", response);
                                SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imglocation", "");
                                editor.apply();
                                image.setImageResource(R.drawable.uploadfile);
                                Toast.makeText(SelectfileActivity.this, "Success", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("ResponseinElse", response);
//
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            pDialog.dismiss();

                            VolleyLog.d("", "Error...." + error);
                            Log.e("VolleyError", error + "");
                            NetworkResponse response = error.networkResponse;
                            if (error instanceof ServerError && response != null) {
                                try {
                                    String res = new String(response.data,
                                            HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                                    // Now you can use any deserializer to make sense of data
                                    JSONObject obj = new JSONObject(res);
                                } catch (UnsupportedEncodingException e1) {
                                    // Couldn't properly decode data to string
                                    e1.printStackTrace();
                                } catch (JSONException e2) {
                                    // returned data is not JSONObject?
                                    e2.printStackTrace();
                                }
                            }


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
                    // Add the request to the RequestQueue.
//                    queue.add(stringRequest);

                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            0,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    queue.add(stringRequest);
                } else {
                    Toast.makeText(SelectfileActivity.this, "Select home Work image !", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SelectfileActivity.this, "Select Date !", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getStringImage(Bitmap bitmap) {
        String temp = null;
        try {
            //Log.e("bitmapGetString()", "" + bitmap);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            temp = Base64.encodeToString(b, Base64.DEFAULT);

        } catch (Exception ex) {
            //Toast.makeText(this, "Kindly enter a image", Toast.LENGTH_SHORT).show();
        }
        return temp;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(SelectfileActivity.this, GiveHomeworkActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        finish();
        super.onBackPressed();
    }
}
