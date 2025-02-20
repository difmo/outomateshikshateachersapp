package com.saptrishi.outomateshikshateachersapp;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.widget.ImageView;

//import com.github.chrisbanes.photoview.PhotoViewAttacher;

import com.squareup.picasso.Picasso;


public class FullImageActivity extends AppCompatActivity {
        ImageView selectedImage;

   // PhotoViewAttacher pAttacher;
    private ScaleGestureDetector mScaleGestureDetector;
    private float mScaleFactor = 1.0f;
    private ScaleGestureDetector scaleGestureDetector = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image);
        selectedImage =  findViewById(R.id.selectedImage); // init a ImageView
        Intent intent = getIntent();
        Picasso.get().load(intent.getStringExtra("image")).into(selectedImage);
        initControls();
    }
    private void initControls()
    {
        if(selectedImage == null)
        {
            selectedImage = (ImageView)findViewById(R.id.selectedImage);
            Intent intent = getIntent();
            Picasso.get().load(intent.getStringExtra("image")).into(selectedImage);
        }

        if(scaleGestureDetector == null)
        {

            OnPinchListener onPinchListener = new OnPinchListener(FullImageActivity.this, selectedImage);


            scaleGestureDetector = new ScaleGestureDetector(getApplicationContext(), onPinchListener);
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        scaleGestureDetector.onTouchEvent(event);
        return true;
    }

}

