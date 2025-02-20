package com.saptrishi.outomateshikshateachersapp.multipleImageSelection;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Classwork_Activity;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.GiveHomeworkActivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.Service.FilePath;
import com.saptrishi.outomateshikshateachersapp.UploadClassworkActivity;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.security.auth.Subject;

import static java.util.Objects.requireNonNull;

public class PdfActivity extends AppCompatActivity {
    LinearLayout video, document;//ppt, pdf;
    //   int PDF_REQ_CODE = 100;
    String classnamethere;
    String filePath = "", extention = "", con_id;
    ImageView thumbnail;
    TextView filename, changefile;
    Button la_submit;
    EditText topicname;
    private static final String TAG = "UploaHomeworkActivity";
    List<String> ContentTypeID = new ArrayList<>();
    List<String> contentname = new ArrayList<>();
    Uri picUri;
    ProgressDialog progressDialog;
    //new code
    Calendar myCalendar;
    String base64img = "",todayDate="";
    TextView edittext;
    ImageView search;
    String dateodhomework;
    String FinalDayTimeTable_Id_fk, clsstrucid, SubjectMasterID, DOH, BranchId, DOS, Createdby, SubjectName, Classname, HWStream64;
    String getempidd ,
            subjectidd,
            classidd ,
            finaldaytimetableidld;
    String subname;
    //
//
//                "stream64":
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf);
        thumbnail = findViewById(R.id.thumbnail);
        filename = findViewById(R.id.file_name);
        changefile = findViewById(R.id.changefile);
        topicname = findViewById(R.id.topicname);
        la_submit = findViewById(R.id.submit);
        edittext = findViewById(R.id.edittext);
        search = findViewById(R.id.search);
//        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(PdfActivity.this);
//        View sheetView = getLayoutInflater().inflate(R.layout.bottonlayout, null);
//        mBottomSheetDialog.setContentView(sheetView);
//        mBottomSheetDialog.setCancelable(false);
//        video = sheetView.findViewById(R.id.video);
//        document = sheetView.findViewById(R.id.document);
//        mBottomSheetDialog.show();
//        progressDialog=new ProgressDialog(PdfActivity.this);
        Intent intent = getIntent();
        getempidd = intent.getStringExtra("getempid");
        subjectidd = intent.getStringExtra("subjectid");
        classidd = intent.getStringExtra("classid");
        finaldaytimetableidld = intent.getStringExtra("finaldaytimetableid");
        subname = intent.getStringExtra("subname");
        classnamethere = intent.getStringExtra("classnamethere");
        Log.e("suname",""+subname);



        SharedPreferences sft = getSharedPreferences("tag", MODE_PRIVATE);
//     SubjectMasterID = sft.getString("SubjectMasterID", "");
////        Log.e("SubjectMasterID", "" + SubjectMasterID);
        clsstrucid = sft.getString("clsstrucid", "");
        Log.e("clsstrucid", "" + clsstrucid);
//        Classname = sft.getString("ClassNm", "");
//        Log.e("Classname", "" + Classname);
        SubjectName = sft.getString("SubjectName", "");
        Log.e("SubjectName", "" + SubjectName);

        FinalDayTimeTable_Id_fk = sft.getString("", "");
        Log.e("FinalDayTimeTable_Id_fk", "" + FinalDayTimeTable_Id_fk);
        DOH = sft.getString("CreateDate", "");
        Log.e("DOH", "" + DOH);
        BranchId = sft.getString("Branchid", "");
        Log.e("BranchId", "" + BranchId);
        DOS = sft.getString("", "");
        Log.e("DOS", "" + DOS);
        Createdby = sft.getString("", "");
        Log.e("Createdby", "" + Createdby);


//        "FinalDayTimeTable_Id_fk":"6254",
//                "clsstrucid":"1397",
//                "SubjectMasterID":"203",
//                "DOH":"05-May-2021",
//                "BranchId":"10"
//                ,"DOS":"05-May-2021"
//                ,"Createdby":"306",
//
//                "SubjectName":"Physics"
//                ,"Classname":"IX-A",
//                "HWStream64":"",
//
//
//                "stream64":


        myCalendar = Calendar.getInstance();

        try {
            //updateLabel();
            Calendar mycal = Calendar.getInstance();

            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            todayDate = sdf.format(mycal.getTime());
        } catch (Exception e) {
            e.printStackTrace();
        }

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                try {
                    updateLabel();
                } catch (ParseException e) {
                    e.printStackTrace();

                }

            }

        };
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DatePickerDialog(PdfActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
//                         new DatePickerDialog(HomeWorkActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new DatePickerDialog(PdfActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        la_submit = findViewById(R.id.submit);

        la_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dateodhomework = edittext.getText().toString();
                if (TextUtils.isEmpty(dateodhomework)) {
                    Toast.makeText(PdfActivity.this, "pls select date", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = getIntent();
                    SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                    SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

//                String branchid = sf.getString("Branchid", "");
//                String subjectid = intent.getStringExtra("subjectid");
//                String classid = intent.getStringExtra("classid");
//                String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");

//            String dateodhomework = intent.getStringExtra("dateodhomework");
                    String fileExtention = extention;
                    String contenttypeid = con_id;
                    String VideoLink = " null";

//                String ClassName = intent.getStringExtra("classname");
//                String SubjectName = intent.getStringExtra("subjectname");
                    String sessionid = "0";// no null
                    Createdby = intent.getStringExtra("getempid");
                    //String stream64 = encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));

                    String topicnme = topicname.getText().toString().trim();
                    senddata(base64img);

                }

            }
        });

//        video.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(UploadClassworkActivity.this, "asas", Toast.LENGTH_SHORT).show();
//
//            }
//        });
        changefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//            final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(PdfActivity.this);
//            View sheetView = getLayoutInflater().inflate(R.layout.bottonlayout, null);
//            mBottomSheetDialog.setContentView(sheetView);
//            mBottomSheetDialog.setCancelable(false);
//            video = sheetView.findViewById(R.id.video);
//            document = sheetView.findViewById(R.id.document);
//            document.setOnClickListener(new View.OnClickListener() {
//
//                @Override
//                public void onClick(View v) {
//                    mBottomSheetDialog.dismiss();
//
//                    showFileChooser();
//
//                }
//            });
//            mBottomSheetDialog.show();
//            video.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //open edittext
//                    mBottomSheetDialog.dismiss();
//                    final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(PdfActivity.this);
//                    View sheetView = getLayoutInflater().inflate(R.layout.video_link, null);
//                    mBottomSheetDialog.setContentView(sheetView);
//                    mBottomSheetDialog.setCancelable(true);
//                    final EditText topicn = sheetView.findViewById(R.id.topic);
//                    final EditText video_link = sheetView.findViewById(R.id.video_link);
//                    final Button submit = sheetView.findViewById(R.id.submit);
//                    mBottomSheetDialog.show();
//
//                    submit.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            Intent intent = getIntent();
//                            SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//                            SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//                            String branchid = sf.getString("Branchid", "");
//                            String subjectid = intent.getStringExtra("subjectid");
//                            String classid = intent.getStringExtra("classid");
//                            String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
//                            String dateodhomework = intent.getStringExtra("dateodhomework");
//                            String fileExcetion = extention;
//                            String contenttypeid = "1";
//                            String VideoLink = video_link.getText().toString().trim();
//                            String ClassName = intent.getStringExtra("classname");
//                            String SubjectName = intent.getStringExtra("subjectname");
//                            String sessionid = "0";
//                            String Createdby = intent.getStringExtra("getempid");
//                            String stream64 = "";
//                            if (topicn.getText().toString().trim().isEmpty()) {
//                                topicname.setError("Please Topic");
//                            } else if (VideoLink.isEmpty()) {
//                                video_link.setError("Please Fill");
//                            } else {
//                                String topicnme = topicname.getText().toString().trim();
//                                senddata(finaldaytimetableid, subjectid, classid, branchid, dateodhomework, contenttypeid, fileExcetion, VideoLink, topicnme, ClassName, SubjectName, sessionid, Createdby, stream64);
//                            }
//                        }
//                    });
//
//
//
//
//                }
//            });


                showFileChooser();
            }
        });


//        video.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            //open edittext
//            mBottomSheetDialog.dismiss();
//            BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(PdfActivity.this);
//            View sheetView = getLayoutInflater().inflate(R.layout.video_link, null);
//            mBottomSheetDialog.setContentView(sheetView);
//            mBottomSheetDialog.setCancelable(true);
//            final EditText topicn = sheetView.findViewById(R.id.topic);
//            final EditText video_link = sheetView.findViewById(R.id.video_link);
//            final Button submit = sheetView.findViewById(R.id.submit);
//            mBottomSheetDialog.show();
//
//            submit.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Intent intent = getIntent();
//                    SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//                    SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//                    String branchid = sf.getString("Branchid", "");
////                        Toast.makeText(UploadClassworkActivity.this, "dd", Toast.LENGTH_SHORT).show();
//                    String subjectid = intent.getStringExtra("subjectid");
//                    String classid = intent.getStringExtra("classid");
//                    String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
//                    String dateodhomework = intent.getStringExtra("dateodhomework");
//                    String fileExcetion = extention;
//                    String contenttypeid = "1";
//                    String VideoLink = video_link.getText().toString().trim();
//                    String ClassName = intent.getStringExtra("classname");
//                    String SubjectName = intent.getStringExtra("subjectname");
//                    String sessionid = "0";
//                    String Createdby = intent.getStringExtra("getempid");
//                    String stream64 = "";
//                    if (topicn.getText().toString().trim().isEmpty()) {
//                        topicname.setError("Please Topic");
//                    } else if (VideoLink.isEmpty()) {
//                        video_link.setError("Please Fill");
//                    } else {
//                        String topicnme = topicname.getText().toString().trim();
//                        Log.e("error ",VideoLink);
//                        senddata(finaldaytimetableid, subjectid, classid, branchid, dateodhomework,
//                                contenttypeid, fileExcetion, VideoLink, topicnme, ClassName, SubjectName,
//                                sessionid, Createdby, stream64);
//                    }
//                }
//            });
//
//
//        }
//    });
//        document.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View v) {
//            mBottomSheetDialog.dismiss();
//            showFileChooser();
//        }
//    });


        getcontentid();
    }

    private void updateLabel() throws ParseException {
        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().after(cal.getTime()))
            edittext.setText(sdf.format(myCalendar.getTime()));
        else {
            Toast.makeText(this, "Wrong Date !", Toast.LENGTH_SHORT).show();
        }

       /* String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        edittext.setText(sdf.format(myCalendar.getTime()));
        final Date date = sdf.parse(sdf.format(myCalendar.getTime()));
        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_YEAR, 0);
        edittext.setText(sdf.format(calendar.getTime()));*/
    }


    private void senddata(String base64img) {

        JSONObject jsonBody = new JSONObject();
        JSONObject jsonObjectdk=new JSONObject();
        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            //this is the url where you want to send the request
            //TeacherAppService.outomate.com/TeacherAppService.svc/uploadHW_base64string
            String url = Url_Symbol.url + "uploadHW_base64string";


            String img = "";//getStringImage(bitmap);

            if (img != null) {

                final ProgressDialog pDialog = new ProgressDialog(this);

                pDialog.setTitle("Uploading...");
                pDialog.setMessage("Uploading your file");
                pDialog.setCancelable(false);
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();

//                getempidd ,
//                        subjectidd,
//                        classidd ,
//                        finaldaytimetableidld;
                //jsonBody.put("stream64", img);
                jsonBody.put("FinalDayTimeTable_Id_fk", finaldaytimetableidld);// cursor.getString(5)
                Log.e("FinalDayTimeTable_Id_fk", finaldaytimetableidld);
                jsonBody.put("clsstrucid", classidd);//cursor.getString(15)
                Log.e("clsstrucid", classidd);
                jsonBody.put("SubjectMasterID", subjectidd);//cursor.getString(11)
                Log.e("SubjectMasterID", subjectidd);

                jsonBody.put("DOH",todayDate);//todayDate
                Log.e("DOH",todayDate);
                jsonBody.put("BranchId", BranchId);//cursor.getString(1)
                Log.e("BranchId", BranchId);
                jsonBody.put("DOS", dateodhomework);//edittext.getText().toString()
                Log.e("DOS", dateodhomework);
                jsonBody.put("Createdby", Createdby);//cursor.getString(13)
                Log.e("Createdby", Createdby);
                jsonBody.put("SubjectName", subname);// cursor.getString(12)
                Log.e("SubjectName", "" +subname);
                jsonBody.put("Classname", classnamethere);//cursor.getString(2)
                Log.e("Classname", ""+classnamethere);


                ///testing
                jsonBody.put("stream64", base64img);
                JSONArray jsonArrayLikes = new JSONArray();
//                jsonObjectdk.put("stuhwimg","");
//                jsonArrayLikes.put(jsonObjectdk);
//                "HWStream64":[{"stuhwimg":""}]
                jsonBody.put("HWStream64", jsonArrayLikes);
                //list of// 64 array

                // final String requestBody = jsonBody.toString();
                // Request a string response from the provided URL.
                // StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

                ////////////////////////

                CustomJsonRequest saveBookingsRequest = new CustomJsonRequest(Request.Method.POST, url, jsonBody, new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            pDialog.dismiss();
                            String errormessage = "Correct_With File uploaded";
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonobject = response.getJSONObject(i);
                                errormessage = jsonobject.getString("errormessage");
                                Log.e("errormessage", errormessage);
                            }
                            if (errormessage != null && errormessage.trim().equalsIgnoreCase("Correct_With File uploaded")) {

                                Toast toast = Toast.makeText(PdfActivity.this, "Uploaded File Successfully!", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();
                                SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imglocation", "");
                                editor.apply();

                                finish();
                            } else {
                                Toast.makeText(PdfActivity.this, "Uploaded Failed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR UPLOADING", error.getMessage());
                        pDialog.dismiss();
                        Toast.makeText(PdfActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                ////////////////////////

                saveBookingsRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(saveBookingsRequest);
            } else {
                Toast.makeText(PdfActivity.this, "Select home Work image !", Toast.LENGTH_SHORT).show();
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
            Toast.makeText(PdfActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("*/*");// */*
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Files"), 5000);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5000 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            picUri = data.getData();

            //*********

            // if (getimage(picUri) != null) {
            //base64img = (getimage(uriPath));
            // base64img = (getimage(picUri));
            // }
            //**********

            filePath = FilePath.getPath(PdfActivity.this, picUri);//commented
             Log.i("lm", " " + filePath);
                     encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));
            String[] name = filePath.split("application/pdf");// the file path used in above version android 7(API 24)
            filename.setText(name[name.length - 1]);

            if (filePath.endsWith(".pdf")) {
                extention = ".pdf";
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                thumbnail.setImageDrawable(getDrawable(R.drawable.pdf));
                con_id = "3";
                //     }

                File file = new File(filePath);
                base64img = getByteCode(file);


            } else if (filePath.endsWith(".doc") || filePath.endsWith(".docx")) {
                extention = ".docx";
                con_id = "3";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    thumbnail.setImageDrawable(getDrawable(R.drawable.docx));
                }
                File file = new File(filePath);
                base64img = getByteCode(file);

            } else if (filePath.endsWith(".ppt") || filePath.endsWith(".pptx")) {
                extention = ".ppt";
                con_id = "3";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    thumbnail.setImageDrawable(getDrawable(R.drawable.ppt));
                }
                File file = new File(filePath);
                base64img = getByteCode(file);

            } else if (filePath.endsWith(".jpg") || filePath.endsWith(".jpeg") || filePath.endsWith(".png")) {
                InputStream imageStream = null;
                try {
                    imageStream = this.getContentResolver().openInputStream(picUri);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                base64img = getStringImage(yourSelectedImage);
                thumbnail.setImageBitmap(yourSelectedImage);// image set in ImageView
                // con_id = "2";//temp

                extention = ".jpg";
                con_id = "2";

            }
            Log.e("exte0as", extention);
        } else if (requestCode == 1) {
            //camera
            Uri uri = data.getData();
            thumbnail.setImageURI(uri);

        } else if (requestCode == 2) {
            //galley
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            thumbnail.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
        if (requestCode == 3) {
            String FilePath = data.getData().getPath();
            String FileName = data.getData().getLastPathSegment();         //Reetika
            int lastPos = FilePath.length() - FileName.length();
            String Folder = FilePath.substring(0, lastPos);

            // textFile.setText("Full Path: \n" + FilePath + "\n");
            //textFolder.setText("Folder: \n" + Folder + "\n");
            //textFileName.setText("File Name: \n" + FileName + "\n");
        }
    }

    private String encodeFileToBase64Binary(File yourFile) {
        int size = (int) yourFile.length();
        byte[] bytes = new byte[size];
        try {
            BufferedInputStream buf = new BufferedInputStream(new FileInputStream(yourFile));
            buf.read(bytes, 0, bytes.length);
            buf.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        String encoded = Base64.encodeToString(bytes, Base64.NO_WRAP);
        Log.e("encod23233e", encoded);
        return encoded;
    }

    private void getcontentid() {


        String url = "http://teacherappservice.outomate.com/TeacherAppService.svc/GetContentype";
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("content_response", response.toString());
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject jsonObject = response.getJSONObject(i);
                        ContentTypeID.add(jsonObject.getString("ContentTypeID"));
                        contentname.add(jsonObject.getString("ContName"));
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
        requestQueue.add(jsonArrayRequest);
/*{

"FinalDayTimeTable_Id_fk":"15",

"SubjectID":"2",

"ClassID":"1",

"Branchid":"15",

"DatebyStudy":"2020-09-10",

"ContytypID":"1",

"fileExcetion":"",

"VideoLink":"https://youtu.be/ea190QUCmdk",

"TopicName":"Testsipl",

"ClassName":"1",

"SubjectName":"Twest_sipl",

"sessionid":"2",

"Createdby":1,

"stream64":""

}*/
// Add the request to the RequestQueue.}
    }

    //new code

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);

    }

    private String getimage(Uri filePath) {
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(requireNonNull(this).getContentResolver(), filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Bitmap lastBitmap = null;
        lastBitmap = bitmap;
        thumbnail.setImageBitmap(lastBitmap);
        //encoding image to string
        return getStringImage(lastBitmap);
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


    //File to Base64
    public String getByteCode(File file) {
        String encoded = "";

        byte[] b = new byte[(int) file.length()];
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            fileInputStream.read(b);
            for (int i = 0; i < b.length; i++) {
                System.out.print((char) b[i]);
            }
            byte[] bytes = b;

            encoded = Base64.encodeToString(bytes, 0);
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found.");
            e.printStackTrace();
        } catch (IOException e1) {
            System.out.println("Error Reading The File.");
            e1.printStackTrace();
        }
        return encoded;
    }


}
