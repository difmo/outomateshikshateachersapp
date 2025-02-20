package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.core.content.FileProvider;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.saptrishi.outomateshikshateachersapp.multipleImageSelection.ImagesActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClickPhotoActivity extends AppCompatActivity {


    public final String APP_TAG = "shikshaTeacherApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg", imageFileName = "";
    File photoFile;

    String getempid;
    String subjectid;
    String classid;
    String finaldaytimetableid;

    File image;//NEW
    String mCurrentPhotoPath;//NEW
    public static final int REQUEST_IMAGE_CAPTURE = 1034;//NEW

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_photo);

        Intent intent = getIntent();
        getempid = intent.getStringExtra("getempid");
        subjectid = intent.getStringExtra("subjectid");
        classid = intent.getStringExtra("classid");
        finaldaytimetableid = intent.getStringExtra("finaldaytimetableid");
        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        TextView childName = findViewById(R.id.showchildname);
        childName.setText(sharedPreferences.getString("empname", ""));

        //onLaunchCamera(findViewById(R.id.image));//OLD CAMERA
        takePicture(findViewById(R.id.image));

    }


    public void onLaunchCamera(View view) {
        // create Intent to take a picture and return control to the calling application
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Create a File reference to access to future access
        photoFile = getPhotoFileUri(photoFileName);

        // wrap File object into a content provider
        // required for API >= 24
        // See https://guides.codepath.com/android/Sharing-Content-with-Intents#sharing-files-with-api-24-or-higher
        Uri fileProvider = FileProvider.getUriForFile(ClickPhotoActivity.this, "com.saptrishi.outomateshikshateachersapp", photoFile);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileProvider);

        // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
        // So as long as the result is not null, it's safe to use the intent.
        if (intent.resolveActivity(getPackageManager()) != null) {
            // Start the image capture intent to take photo
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }
    }

    public File getPhotoFileUri(String fileName) {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);
        Log.e("errmkdirmkdirs", mediaStorageDir.mkdirs() + "");
        Log.e("errmkdirmedia", mediaStorageDir.exists() + "");
        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.e("errmkdir", "failed to create directory");
        }

        // Return the file target for the photo based on filename

        File file = new File(mediaStorageDir.getPath() + File.separator + fileName);
        Log.e("filemsg", file + "");

        //file:/storage/emulated/0/Pictures/IMG_20201012_231155_8809139294792730803.jpg
        //file:/storage/emulated/0/Android/data/com.saptrishi.outomateshikshateachersapp/files/Pictures/shikshaTeacherApp/photo.jpg
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            Uri contentUri = Uri.fromFile(mediaStorageDir); //out is your file you saved/deleted/moved/copied
            mediaScanIntent.setData(contentUri);
            this.sendBroadcast(mediaScanIntent);
        } else {
            sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        }


        return file;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                /*Uri takenPhotoUri = Uri.fromFile(getPhotoFileUri(imageFileName));
                Bitmap rawTakenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
                SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                rawTakenImage.compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] b = baos.toByteArray();
                String temp = Base64.encodeToString(b, Base64.DEFAULT);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("imglocation", temp);
                editor.apply();

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                File resizedFile = getPhotoFileUri("resized" + photoFileName);
                try {
                    resizedFile.createNewFile();
                } catch (IOException e) {
                    Log.e("catch1", e + "");
                    e.printStackTrace();
                }
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(resizedFile);
                } catch (FileNotFoundException e) {
                    Log.e("catch2", e + "");
                    e.printStackTrace();
                }
                try {
                    assert fos != null;
                    fos.write(bytes.toByteArray());
                } catch (IOException e) {
                    Log.e("catch3", e + "");
                    e.printStackTrace();
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    Uri contentUri = Uri.fromFile(resizedFile); //out is your file you saved/deleted/moved/copied
                    mediaScanIntent.setData(contentUri);
                    this.sendBroadcast(mediaScanIntent);
                } else {
                    sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
                }
                try {
                    fos.close();
                } catch (IOException e) {
                    Log.e("catch4", e + "");
                    e.printStackTrace();
                }*/

               /* Intent intent = new Intent(ClickPhotoActivity.this, SelectfileActivity.class);

                intent.putExtra("getempid", getempid);
                intent.putExtra("subjectid", subjectid);
                intent.putExtra("classid", classid);
                intent.putExtra("finaldaytimetableid", finaldaytimetableid);

                startActivity(intent);*/


                Intent intent = new Intent(ClickPhotoActivity.this, ImagesActivity.class);// new
                intent.putExtra("getempid", getempid);
                intent.putExtra("subjectid", subjectid);
                intent.putExtra("classid", classid);
                intent.putExtra("finaldaytimetableid", finaldaytimetableid);
                intent.putExtra("date", "");//date
                intent.putExtra("mCurrentPhotoPath", mCurrentPhotoPath);
                startActivity(intent);
                finish();
            } else { // Result was a failure
                Toast toast = Toast.makeText(this, "Picture wasn't taken!", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
                startActivity(new Intent(this, GiveHomeworkActivity.class));
                finish();
            }
        }
    }

    // start the image capture Intent
    public void takePicture(View view) {
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


    public File createImageFile() {
        // Create an image file name
        String dateTime = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        imageFileName = "IMG_" + dateTime + "_";
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

}
