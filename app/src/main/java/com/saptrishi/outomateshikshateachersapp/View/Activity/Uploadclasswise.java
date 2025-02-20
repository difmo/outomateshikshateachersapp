package com.saptrishi.outomateshikshateachersapp.View.Activity;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
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
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.util.Objects.requireNonNull;

public class Uploadclasswise extends AppCompatActivity {
    LinearLayout video, document;//ppt, pdf;
    //   int PDF_REQ_CODE = 100;
    String filePath = "", extention = "", con_id;
    String subjectname,timetableid;
    String subjectidfk;
    ImageView thumbnail;
    TextView filename, changefile;
    Button la_submit;
    EditText topicname;
    private static final String TAG = "Uploadclasswise";
    List<String> ContentTypeID = new ArrayList<>();
    List<String> contentname = new ArrayList<>();
    Uri picUri;
    ProgressDialog progressDialog;
    String Subjectid;
    String classids;
    //new code
    String base64img = "";
    String TeacherID_FK,ClassNm , Createdby;
   String mydates,skdate;
   String stuid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploadclasswise);
        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(Uploadclasswise.this);
        View sheetView = getLayoutInflater().inflate(R.layout.uploadclasswisembottom, null);
        mBottomSheetDialog.setContentView(sheetView);
        mBottomSheetDialog.setCancelable(false);
        video = sheetView.findViewById(R.id.video);
        document = sheetView.findViewById(R.id.document);
        mBottomSheetDialog.show();

        SharedPreferences sharedPreferences = getSharedPreferences("Timetableactivity", MODE_PRIVATE);
        TeacherID_FK = sharedPreferences.getString("TeacherID_FK", "");
        Log.e("sks ",TeacherID_FK );
//        ClassNm = sharedPreferences.getString("ClassNm", "");
//        Toast.makeText(Uploadclasswise.this, "TEST2:"+TeacherID_FK, Toast.LENGTH_LONG).show();
        Intent intent = getIntent();
        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        String branchid = sfw.getString("Branchid", "");
        //Toast.makeText(Uploadclasswise.this, "dd", Toast.LENGTH_SHORT).show();
        subjectname = intent.getStringExtra("subject");
        subjectidfk= intent.getStringExtra("Subjectid");
        ClassNm = intent.getStringExtra("ClassNm");
        stuid = intent.getStringExtra("stuid");
        Subjectid = intent.getStringExtra("Subjectid");
        timetableid = intent.getStringExtra("finalDayIdFk") ;
        String subjectid = intent.getStringExtra("subjectid");
        String classid = intent.getStringExtra("classid");
        String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
        String dateodhomework = intent.getStringExtra("dateodhomework");
        mydates = intent.getStringExtra("etdate");
        Log.e( "datesks",""+mydates );
        String myFormat = "MM-dd-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
       skdate=sdf.format(Date.parse(mydates));
        Log.e( "changedate",""+skdate );
        progressDialog = new ProgressDialog(Uploadclasswise.this);

        thumbnail = findViewById(R.id.thumbnail);
        filename = findViewById(R.id.file_name);
        changefile = findViewById(R.id.changefile);
        topicname = findViewById(R.id.topicname);
        la_submit = findViewById(R.id.submit);



        changefile.setOnClickListener(new View.OnClickListener() {
                                          @Override
                                          public void onClick(View v) {
                                              final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(Uploadclasswise.this);
                                              View sheetView = getLayoutInflater().inflate(R.layout.uploadclasswisembottom, null);
                                              mBottomSheetDialog.setContentView(sheetView);
                                              mBottomSheetDialog.setCancelable(false);
                                              document = sheetView.findViewById(R.id.document);
                                              document.setOnClickListener(new View.OnClickListener() {

                                                  @Override
                                                  public void onClick(View v) {
                                                      mBottomSheetDialog.dismiss();

                                                      showFileChooser();

                                                  }
                                              });
                                              mBottomSheetDialog.show();

                                          }
                                      });



                la_submit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {


//
                String fileExtention = extention;
                String contenttypeid = "1";
                String VideoLink = " null";
                 classids = intent.getStringExtra("classids");

                String ClassName = intent.getStringExtra("classname");
                String SubjectName = intent.getStringExtra("subjectname");
                String sessionid = "0";// no null
                String Createdby = intent.getStringExtra("getempid");
                 filename.setText(extention);
                //String stream64 = encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));
                if  (filename.getText().toString().trim().isEmpty()) {
                    filename.setError("Please Topic");
                    return;
                } else {

                    String topicnme = topicname.getText().toString().trim();
                    senddata(branchid,classids,skdate,TeacherID_FK,contenttypeid,ClassNm,fileExtention,timetableid,base64img);
                }
            }
        });


        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
                showFileChooser();
            }
        });


        getcontentid();
    }

    public void bottomview() {

    }

    private void senddata(String branchid, String classids, String skdate, String createdby, String contenttypeid, String className, String fileExtention, String timetableid, String base64img) {
ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Uploading...");
        progressDialog.show();
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("FinalDayTimeTable_Id_fk", timetableid);
            jsonObject.put("ClassID", classids);
            jsonObject.put("Branchid", branchid);
            jsonObject.put("DatebyStudy",skdate);
            jsonObject.put("ContytypID", contenttypeid);//"2"
            jsonObject.put("fileExcetion", fileExtention);//"jpg"
            jsonObject.put("ClassName", className);
            jsonObject.put("SubjectID", subjectidfk);
            Log.e( "dineshkumar" ,subjectidfk );
            jsonObject.put("Createdby", createdby);
            jsonObject.put("stream64", base64img);
            jsonObject.put("StudentId", stuid);
            Log.e("Shaana ",stuid );
            final String requestbody = jsonObject.toString();

            final String url = Url_Symbol.url + "UploadResult";
            Log.e("success", "upload" + url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(Uploadclasswise.this, "Uploaded", Toast.LENGTH_SHORT).show();

                    if (response.equals("200"))
                    {
                        progressDialog.cancel();
                        Log.e("responsejj", "200" + response);
//                        Log.e("responsejj", "videolink not found " + VideoLink);
                        Toast toast =  Toast.makeText(Uploadclasswise.this, "File Sent Successfully!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
//                        Log.e("sessionid", sessionid);
                        startActivity(new Intent(Uploadclasswise.this,classwise_subjectupload.class));
                        finish();
                        Intent intent=new Intent(Uploadclasswise.this, classwise_subjectupload.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("classids",classids);
                        intent.putExtra("info",  className);
                        intent.putExtra("txtVersion", subjectname);
                         intent.putExtra("stuid",stuid);
                        Log.e("gfd",stuid );
                         intent.putExtra("subject",subjectname);
                        intent.putExtra("FinalDayTimeTable_Id", timetableid);
                        intent.putExtra("FinalDayTimeTable_Id", timetableid);

                        intent.putExtra("etdate",mydates);
                        startActivity(intent);

                    }
                    else
                        {
                        Log.e("response", "Failed" + response);
                        Toast.makeText(Uploadclasswise.this, "File Not Uploaded", Toast.LENGTH_SHORT).show();
                        progressDialog.cancel();

                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("vo_error", error + "");
                    Toast.makeText(Uploadclasswise.this, "File Not Uploaded", Toast.LENGTH_SHORT).show();
                    progressDialog.cancel();

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

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("application/pdf");// */*
//        intent.setAction(Intent.ACTION_GET_CONTENT);
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

            filePath = FilePath.getPath(Uploadclasswise.this, picUri);//commented
            // Log.i("lk", " " + filePath);
//                     encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));
            String[] name = filePath.split("/");// the file path used in above version android 7(API 24)
            filename.setText(name[name.length - 1]);

            if (filePath.endsWith(".pdf")) {
                extention = ".pdf";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    thumbnail.setImageDrawable(getDrawable(R.drawable.pdfes));
                    con_id = "1";//3
                }

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
