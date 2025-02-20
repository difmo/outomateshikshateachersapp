package com.saptrishi.outomateshikshateachersapp;

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
import android.os.Handler;
import android.provider.MediaStore;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
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
import com.saptrishi.outomateshikshateachersapp.Service.FilePath;
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
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.requireNonNull;

public class UploadClassworkActivity extends AppCompatActivity {

    LinearLayout  video, document;//ppt, pdf;
    //   int PDF_REQ_CODE = 100;
    String filePath = "", extention = "", con_id;
    ImageView thumbnail;
    TextView filename;
    Button    changefile;
    Button la_submit;
    EditText topicname;
    private static final String TAG = "UploadClassworkActivity";
    List<String> ContentTypeID = new ArrayList<>();
    List<String> contentname = new ArrayList<>();
    Uri picUri;
    ProgressDialog progressDialog;
    //new code
    String base64img = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_classwork);
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(UploadClassworkActivity.this,R.style.BottomSheetDialogTheme);
        View sheetView = getLayoutInflater().inflate(R.layout.bottonlayout, null);
         bottomSheetDialog.setContentView(sheetView);
         bottomSheetDialog.setCancelable(true);
          bottomSheetDialog.getDismissWithAnimation();
        video = sheetView.findViewById(R.id.video);
        document = sheetView.findViewById(R.id.document);
       bottomSheetDialog.show();

        progressDialog=new ProgressDialog(UploadClassworkActivity.this);

        thumbnail = findViewById(R.id.thumbnail);
        filename = findViewById(R.id.file_name);
        changefile = findViewById(R.id.changefile);
        topicname = findViewById(R.id.topicname);
        la_submit = findViewById(R.id.submit);

        la_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();
                SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                String branchid = sf.getString("Branchid", "");
                //Toast.makeText(UploadClassworkActivity.this, "dd", Toast.LENGTH_SHORT).show();
                String subjectid = intent.getStringExtra("subjectid");
                String classid = intent.getStringExtra("classid");
                String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
                String dateodhomework = intent.getStringExtra("dateodhomework");
                String fileExtention = extention;
                String contenttypeid = con_id;
                String VideoLink = " null";

                String ClassName = intent.getStringExtra("classname");
                String SubjectName = intent.getStringExtra("subjectname");
                String sessionid = "0";// no null
                String Createdby = intent.getStringExtra("getempid");
                //String stream64 = encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));
                if (topicname.getText().toString().trim().isEmpty()) {
                    topicname.setError("Please Topic");
                    return;
                } else {
                    String topicnme = topicname.getText().toString().trim();
                    senddata(finaldaytimetableid, subjectid, classid, branchid, dateodhomework, contenttypeid,
                            fileExtention, VideoLink, topicnme, ClassName, SubjectName, sessionid, Createdby, base64img);
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
        changefile.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(UploadClassworkActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.bottonlayout, null);
                mBottomSheetDialog.setCanceledOnTouchOutside(true);
                mBottomSheetDialog.setCancelable(true);
                mBottomSheetDialog.getDismissWithAnimation();
                mBottomSheetDialog.setContentView(sheetView);
                video = sheetView.findViewById(R.id.video);
                document = sheetView.findViewById(R.id.document);
                document.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        mBottomSheetDialog.dismiss();

                        showFileChooser();

                    }
                });
                mBottomSheetDialog.show();
                video.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        //open edittext
                        mBottomSheetDialog.dismiss();
                        final BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(UploadClassworkActivity.this);
                        View sheetView = getLayoutInflater().inflate(R.layout.video_link, null);
                        mBottomSheetDialog.setContentView(sheetView);
                        mBottomSheetDialog.setCancelable(true);
                        final EditText topicn = sheetView.findViewById(R.id.topic);
                        final EditText video_link = sheetView.findViewById(R.id.video_link);
                        final Button submit = sheetView.findViewById(R.id.submit);
                        mBottomSheetDialog.show();

                        submit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = getIntent();
                                SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                                SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                                String branchid = sf.getString("Branchid", "");
                                String subjectid = intent.getStringExtra("subjectid");
                                String classid = intent.getStringExtra("classid");
                                String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
                                String dateodhomework = intent.getStringExtra("dateodhomework");
                                String fileExcetion = extention;
                                String contenttypeid = "1";
                                String VideoLink = video_link.getText().toString().trim();
                                String ClassName = intent.getStringExtra("classname");
                                String SubjectName = intent.getStringExtra("subjectname");
                                String sessionid = "0";
                                String Createdby = intent.getStringExtra("getempid");
                                String stream64 = "";
                                if (topicn.getText().toString().trim().isEmpty()) {
                                    topicname.setError("Please Topic");
                                } else if (VideoLink.isEmpty()) {
                                    video_link.setError("Please Fill");
                                } else {
                                    String topicnme = topicname.getText().toString().trim();
                                    senddata(finaldaytimetableid, subjectid, classid, branchid, dateodhomework, contenttypeid, fileExcetion, VideoLink, topicnme, ClassName, SubjectName, sessionid, Createdby, stream64);
                                }
                            }
                        });




                    }
                });

            }
        });


        video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open edittext
               bottomSheetDialog.dismiss();
                BottomSheetDialog mBottomSheetDialog = new BottomSheetDialog(UploadClassworkActivity.this);
                View sheetView = getLayoutInflater().inflate(R.layout.video_link, null);
                mBottomSheetDialog.setContentView(sheetView);
                mBottomSheetDialog.setCancelable(true);
                final EditText topicn = sheetView.findViewById(R.id.topic);
                final EditText video_link = sheetView.findViewById(R.id.video_link);
                final Button submit = sheetView.findViewById(R.id.submit);
                mBottomSheetDialog.show();

                submit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = getIntent();
                        SharedPreferences sf = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                        SharedPreferences sfw = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                        String branchid = sf.getString("Branchid", "");
//                        Toast.makeText(UploadClassworkActivity.this, "dd", Toast.LENGTH_SHORT).show();
                        String subjectid = intent.getStringExtra("subjectid");
                        String classid = intent.getStringExtra("classid");
                        String finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
                        String dateodhomework = intent.getStringExtra("dateodhomework");
                        String fileExcetion = extention;
                        String contenttypeid = "1";
                        String VideoLink = video_link.getText().toString().trim();
                        String ClassName = intent.getStringExtra("classname");
                        String SubjectName = intent.getStringExtra("subjectname");
                        String sessionid = "0";
                        String Createdby = intent.getStringExtra("getempid");
                        String stream64 = "";
                        if (topicn.getText().toString().trim().isEmpty()) {
                            topicname.setError("Please Topic");
                        } else if (VideoLink.isEmpty()) {
                            video_link.setError("Please Fill");
                        } else {
                            String topicnme = topicname.getText().toString().trim();
                            Log.e("error ",VideoLink);
                            senddata(finaldaytimetableid, subjectid, classid, branchid, dateodhomework,
                                    contenttypeid, fileExcetion, VideoLink, topicnme, ClassName, SubjectName,
                                    sessionid, Createdby, stream64);
                        }
                    }
                });


            }
        });
        document.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               bottomSheetDialog.dismiss();
                showFileChooser();
            }
        });


        getcontentid();
    }

    public void bottomview() {

    }

    private void senddata(String FinalDayTimeTable_Id_fk, String SubjectID, String ClassID,
                          String Branchid, String DatebyStudy, final String ContytypID,
                          String fileExtention, final String VideoLink, String TopicName, String ClassName,
                          String SubjectName, final String sessionid, String Createdby, String stream64) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("FinalDayTimeTable_Id_fk", FinalDayTimeTable_Id_fk);

            jsonObject.put("SubjectID", SubjectID);
            jsonObject.put("ClassID", ClassID);
            jsonObject.put("Branchid", Branchid);
            jsonObject.put("DatebyStudy", DatebyStudy);
            jsonObject.put("ContytypID", ContytypID);//"2"
            jsonObject.put("fileExcetion", fileExtention);//"jpg"
            jsonObject.put("VideoLink", VideoLink);

            jsonObject.put("TopicName", TopicName);
            jsonObject.put("ClassName", ClassName);
            jsonObject.put("SubjectName", SubjectName);
            jsonObject.put("sessionid", sessionid);
            jsonObject.put("Createdby", Createdby);
            jsonObject.put("stream64", base64img);

            final String requestbody = jsonObject.toString();

            final String url = Url_Symbol.url + "UploadFileCW";
            Log.e("success", "upload" + url);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>()
            {
                @Override
                public void onResponse(String response) {
                    Toast toast = Toast.makeText(UploadClassworkActivity.this, "Uploaded", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    progressDialog.hide();

                    if (response.equals("200")) {
                        Log.e("responsejj", "200" + response);
                        Log.e("responsejj", "videolink not found " +VideoLink );
                        toast = Toast.makeText(UploadClassworkActivity.this, "File Sent Successfully!!", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                        Log.e("sessionid", sessionid);
                        startActivity(new Intent(UploadClassworkActivity.this, Classwork_Activity.class));
                        finish();
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

            filePath = FilePath.getPath(UploadClassworkActivity.this, picUri);//commented
            // Log.i("lk", " " + filePath);
//                     encodeFileToBase64Binary(new File(FilePath.getPath(getApplicationContext(), picUri)));
            String[] name = filePath.split("/");// the file path used in above version android 7(API 24)
            filename.setText(name[name.length - 1]);

            if (filePath.endsWith(".pdf")) {
                extention = ".pdf";
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    thumbnail.setImageDrawable(getDrawable(R.drawable.pdf));
                    con_id = "3";
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
