package com.saptrishi.outomateshikshateachersapp.multipleImageSelection;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
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
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.SelectfileActivity;
import com.saptrishi.outomateshikshateachersapp.model.ImageList;
import com.saptrishi.outomateshikshateachersapp.utils.Url_Symbol;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ImagesActivity extends AppCompatActivity {

    public static final int REQUEST_IMAGE_CAPTURE = 1;
    public static final int PICK_IMAGES = 2;
    public static final int PICK_PDF = 3;
    public static final int STORAGE_PERMISSION = 100;
    MySqliteDataBase mySqliteDataBase;//new
    ArrayList<ImageModel> imageList;
    ArrayList<String> selectedImageList;
    RecyclerView imageRecyclerView, selectedImageRecyclerView;
    int[] resImg = {R.drawable.ic_camera_white_30dp};
    String[] title = {"Camera"};
    String mCurrentPhotoPath;
    TextView edittext, className, subjectName;
    String getempid;//new
    String subjectid;//new
    Calendar myCalendar;
    String classid;//new
    String dateStr = "";
    String finaldaytimetableid;
    SelectedImageAdapter selectedImageAdapter;
    ImageAdapter imageAdapter;
    String[] projection = {MediaStore.MediaColumns.DATA};
    File image;
    Button done;
    //new
    List<ImageList> lstImg = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);
        edittext = (TextView) findViewById(R.id.homeWorkDate);
        if (isStoragePermissionGranted()) {

            Intent intent = getIntent();//new
            getempid = intent.getStringExtra("getempid");
            subjectid = intent.getStringExtra("subjectid");
            classid = intent.getStringExtra("classid");
            finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
            dateStr = intent.getStringExtra("date");
            mCurrentPhotoPath = intent.getStringExtra("mCurrentPhotoPath");
            init();
            getAllImages();
            setImageList();
            setSelectedImageList();
            if (mCurrentPhotoPath != null) {
                addImage(mCurrentPhotoPath);
            }

        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        Permissioncheck();
    }

    public void init() {
        imageRecyclerView = findViewById(R.id.recycler_view);
        selectedImageRecyclerView = findViewById(R.id.selected_recycler_view);
        done = findViewById(R.id.Upload);
        myCalendar = Calendar.getInstance();
        selectedImageList = new ArrayList<>();
        imageList = new ArrayList<>();
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
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittext.getText().toString().isEmpty()) {
                    Toast.makeText(ImagesActivity.this, "Kindly Mention Submission date before Upload", Toast.LENGTH_LONG).show();// Kindly enter date new toast
                } else {
                    dateStr = edittext.getText().toString();
                    // mySqliteDataBase = new MySqliteDataBase(ImagesActivity.this);
                    for (int i = 0; i < selectedImageList.size(); i++) {
                        if (selectedImageList.get(0) != null && selectedImageList.get(0).contains("file:")) {
                            String Str = selectedImageList.get(0).replace("file:", "");
                            File file = new File(Str);
                            ImageList imageList = new ImageList();//<-------------- solution
                            imageList.setStuhwimg(getByteCode(file));
                            lstImg.add(imageList);
                            break;
                        }
                        //Toast.makeText(getApplicationContext(), selectedImageList.get(i), Toast.LENGTH_LONG).show();
                        File file = new File(selectedImageList.get(i));
                        ImageList imageList = new ImageList();//<-------------- solution
                        imageList.setStuhwimg(getByteCode(file));
                        lstImg.add(imageList);
                    }
                    sendTokenToServer();
                    //   Toast.makeText(ImagesActivity.this, "Please select a file ", Toast.LENGTH_SHORT).show();

                }

            }

        });


        edittext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // TODO Auto-generated method stub
//                         new DatePickerDialog(HomeWorkActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                new DatePickerDialog(ImagesActivity.this, R.style.DialogTheme, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel() {

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        Calendar cal = Calendar.getInstance();

        if (myCalendar.getTime().after(cal.getTime()))
            edittext.setText(sdf.format(myCalendar.getTime()));
        else {
            Toast.makeText(this, "Wrong Date !", Toast.LENGTH_SHORT).show();
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

    ///////////////
    public void sendTokenToServer() {

        Calendar mycal = Calendar.getInstance();

        String myFormat = "dd-MMM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        String todayDate = sdf.format(mycal.getTime());
        mySqliteDataBase = new MySqliteDataBase(this);
        Cursor cursor = mySqliteDataBase.fetchparticularHomework(getempid, subjectid, classid, finaldaytimetableid);
        cursor.moveToFirst();


        try {
            // Instantiate the RequestQueue.
            RequestQueue queue = Volley.newRequestQueue(this);
            //this is the url where you want to send the request
            //TeacherAppService.outomate.com/TeacherAppService.svc/uploadHW_base64string
            String url = Url_Symbol.url + "uploadHW_base64string";
            Log.e("success", url );

            String img = "";//getStringImage(bitmap);

            if (img != null) {

                final ProgressDialog pDialog = new ProgressDialog(this);

                pDialog.setTitle("Uploading...");
                pDialog.setMessage("Uploading your file");
                pDialog.setCancelable(false);
                pDialog.setCanceledOnTouchOutside(false);
                pDialog.show();

                JSONObject jsonBody = new JSONObject();

                //jsonBody.put("stream64", img);
                jsonBody.put("FinalDayTimeTable_Id_fk", cursor.getString(5));// cursor.getString(5)
                Log.e("errormessage", cursor.getString(5));
                jsonBody.put("clsstrucid", cursor.getString(15));//cursor.getString(15)
                Log.e("errormessage", cursor.getString(15));
                jsonBody.put("SubjectMasterID", cursor.getString(11));//cursor.getString(11)
                Log.e("errormessage", cursor.getString(11));
                jsonBody.put("DOH", todayDate);//todayDate
                Log.e("errormessage",  todayDate);
                jsonBody.put("BranchId", cursor.getString(1));//cursor.getString(1)
                Log.e("errormessage", cursor.getString(1));
                jsonBody.put("DOS", dateStr);//edittext.getText().toString()
                Log.e("errormessage", dateStr);
                jsonBody.put("Createdby", cursor.getString(13));//cursor.getString(13)
                Log.e("errormessage",  cursor.getString(13));
                jsonBody.put("SubjectName", cursor.getString(12));// cursor.getString(12)
                Log.e("errormessage",cursor.getString(12));
                jsonBody.put("Classname", cursor.getString(2));//cursor.getString(2)
                Log.e("errormessage", cursor.getString(2));
                ///testing
                JSONArray jsonArrayLikes = new JSONArray();
                for (int i = 0; i < lstImg.size(); i++) {
                    JSONObject jsonLikes = new JSONObject();
                    jsonLikes.put("stuhwimg", lstImg.get(i).getStuhwimg());//lstImg.get(i).getstuhwimg();
                    jsonArrayLikes.put(jsonLikes);
                }

                jsonBody.put("HWStream64", jsonArrayLikes);//list of// 64 array

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
                                Log.e("errormessage", errormessage );
                            }
                            if (errormessage != null && errormessage.trim().equalsIgnoreCase("Correct_With File uploaded")) {

                                Toast.makeText(ImagesActivity.this, "Uploaded Successfully!", Toast.LENGTH_SHORT).show();
                                SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("imglocation", "");
                                editor.apply();

                                finish();
                            } else {
                                Toast.makeText(ImagesActivity.this, "Uploaded Failed!", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener()

                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR UPLOADING", error.getMessage());
                        pDialog.dismiss();
                        Toast.makeText(ImagesActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                ////////////////////////

                saveBookingsRequest.setRetryPolicy(new DefaultRetryPolicy(
                        0,
                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(saveBookingsRequest);
            } else {
                Toast.makeText(ImagesActivity.this, "Select home Work image !", Toast.LENGTH_SHORT).show();
            }

        } catch (
                JSONException e) {
            e.printStackTrace();
            Toast.makeText(ImagesActivity.this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
    private void Permissioncheck() {
        checkPermission(Manifest.permission.READ_PHONE_STATE, 1);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 2);
        checkPermission(Manifest.permission.CAMERA, 3);
        checkPermission(Manifest.permission.READ_PHONE_STATE, 4);

    }

    public void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(ImagesActivity.this, permission)
                == PackageManager.PERMISSION_DENIED) {

            // Requesting the permission
            ActivityCompat.requestPermissions(ImagesActivity.this,
                    new String[]{permission},
                    requestCode);
        }
    }
    ///////////////

    public void setImageList() {
        imageRecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 4));
        imageAdapter = new ImageAdapter(getApplicationContext(), imageList);
        imageRecyclerView.setAdapter(imageAdapter);

        imageAdapter.setOnItemClickListener(new ImageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                if (position == 0) {
                    takePicture();
                }
                //else if (position == 1) {
//                    getPickImageIntent();
//                }    adding for folder
//
                else {
                    try {
                        if (!imageList.get(position).isSelected) {
                            selectImage(position);
                        } else {
                            unSelectImage(position);
                        }
                    } catch (ArrayIndexOutOfBoundsException ed) {
                        ed.printStackTrace();
                    }
                }
            }
        });
        setImagePickerList();
    }

    public void setSelectedImageList() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        selectedImageRecyclerView.setLayoutManager(layoutManager);
        selectedImageAdapter = new SelectedImageAdapter(this, selectedImageList);
        selectedImageRecyclerView.setAdapter(selectedImageAdapter);
    }

    // Add Camera and Folder in ArrayList
    public void setImagePickerList() {
        for (int i = 0; i < resImg.length; i++) {
            ImageModel imageModel = new ImageModel();
            imageModel.setResImg(resImg[i]);
            imageModel.setTitle(title[i]);
            imageList.add(i, imageModel);
        }
        imageAdapter.notifyDataSetChanged();
    }

    // get all images from external storage
    public void getAllImages() {
        imageList.clear();
        Cursor cursor = getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, null, null, null);
        while (cursor.moveToNext()) {
            String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
            ImageModel ImageModel = new ImageModel();
            ImageModel.setImage(absolutePathOfImage);
            imageList.add(ImageModel);
        }
        cursor.close();
    }

    // start the image capture Intent
    public void takePicture() {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Continue only if the File was successfully created;
        File photoFile = createImageFile();
        if (photoFile != null) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
            startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void getPickImageIntent() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(intent, PICK_IMAGES);
    }

    // Add image in SelectedArrayList
    public void selectImage(int position) {
        // Check before add new item in ArrayList;
        if (!selectedImageList.contains(imageList.get(position).getImage())) {
            imageList.get(position).setSelected(true);
            selectedImageList.add(0, imageList.get(position).getImage());
            selectedImageAdapter.notifyDataSetChanged();
            imageAdapter.notifyDataSetChanged();
        }
    }

    // Remove image from selectedImageList
    public void unSelectImage(int position) {
        for (int i = 0; i < selectedImageList.size(); i++) {
            if (imageList.get(position).getImage() != null) {
                if (selectedImageList.get(i).equals(imageList.get(position).getImage())) {
                    imageList.get(position).setSelected(false);
                    selectedImageList.remove(i);
                    selectedImageAdapter.notifyDataSetChanged();
                    imageAdapter.notifyDataSetChanged();
                }
            }
        }
    }

    public File createImageFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".jpg", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }
    public File createPDFFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMG_" + dateTime + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        try {
            image = File.createTempFile(imageFileName, ".pdf", storageDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;
    }



    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                if (mCurrentPhotoPath != null) {
                    addImage(mCurrentPhotoPath);
                }
//file:/storage/emulated/0/Pictures/IMG_20201012_231155_8809139294792730803.jpg
            } else if (requestCode == PICK_IMAGES) {
                if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();
                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();
                        getImageFilePath(uri);
                    }
                } else if (data.getData() != null) {
                    Uri uri = data.getData();
                    getImageFilePath(uri);
                }
            }
        }
    }

    // Get image file path
    public void getImageFilePath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            while (cursor.moveToNext()) {
                String absolutePathOfImage = cursor.getString(cursor.getColumnIndex(MediaStore.MediaColumns.DATA));
                if (absolutePathOfImage != null) {
                    checkImage(absolutePathOfImage);
                } else {
                    checkImage(String.valueOf(uri));
                }
            }
        }
    }

    // add image in selectedImageList and imageList
    public void checkImage(String filePath) {
        // Check before adding a new image to ArrayList to avoid duplicate images
        if (!selectedImageList.contains(filePath)) {
            for (int pos = 0; pos < imageList.size(); pos++) {
                if (imageList.get(pos).getImage() != null) {
                    if (imageList.get(pos).getImage().equalsIgnoreCase(filePath)) {
                        imageList.remove(pos);
                    }
                }
            }
            addImage(filePath);
        }
    }

    // add image in selectedImageList and imageList
    public void addImage(String filePath) {
        ImageModel imageModel = new ImageModel();
        imageModel.setImage(filePath);
        imageModel.setSelected(true);
        imageList.add(2, imageModel);
        selectedImageList.add(0, filePath);
        selectedImageAdapter.notifyDataSetChanged();
        imageAdapter.notifyDataSetChanged();
    }

    public boolean isStoragePermissionGranted() {
        int ACCESS_EXTERNAL_STORAGE = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if ((ACCESS_EXTERNAL_STORAGE != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION);
            return false;
        }
        return true;
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            init();
            getAllImages();
            setImageList();
            setSelectedImageList();
        }
    }


    public void onClick(View view) {
        lstImg.clear();


        // mySqliteDataBase = new MySqliteDataBase(ImagesActivity.this);
        for (int i = 0; i < selectedImageList.size(); i++) {
            Toast.makeText(getApplicationContext(), selectedImageList.get(i), Toast.LENGTH_LONG).show();
            File file = new File(selectedImageList.get(i));
            ImageList imageList = new ImageList();//<-------------- solution
            imageList.setStuhwimg(getByteCode(file));
            lstImg.add(imageList);
        }

        sendTokenToServer();

    }
}

