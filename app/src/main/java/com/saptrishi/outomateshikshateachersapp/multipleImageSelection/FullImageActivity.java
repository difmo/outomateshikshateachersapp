package com.saptrishi.outomateshikshateachersapp.multipleImageSelection;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.saptrishi.outomateshikshateachersapp.R;

public class FullImageActivity extends AppCompatActivity {
    ImageView myImage, back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_image2);
        myImage = findViewById(R.id.image);
        back = findViewById(R.id.back);
        Glide.with(this)
                .load(getIntent().getStringExtra("image"))
                .placeholder(R.color.codeGray)
                .into(myImage);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
                finish();
                overridePendingTransition(0,0);
            }
        });
    }
}