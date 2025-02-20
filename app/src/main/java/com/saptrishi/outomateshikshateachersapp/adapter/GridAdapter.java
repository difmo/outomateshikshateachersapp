package com.saptrishi.outomateshikshateachersapp.adapter;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.saptrishi.outomateshikshateachersapp.GiveHomeworkActivity;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.SeeTimeTableActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.AttendanceActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.NoticeActivity;
//import com.saptrishi.outomateshikshateachersapp.View.Activity.NoticeActivity;

import java.util.ArrayList;

;

public class GridAdapter extends BaseAdapter {

    public static Activity activity;
    ArrayList names;

    public GridAdapter(Activity activity, ArrayList names) {
        this.activity = activity;
        this.names = names;
    }

    @Override
    public int getCount() {
        return names.size();
    }

    @Override
    public Object getItem(int position) {
        return names.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {
        if (v == null) {
            LayoutInflater vi = LayoutInflater.from(activity);
            v = vi.inflate(R.layout.grid_layout, null);
        }
        TextView textView = (TextView) v.findViewById(R.id.namePlacer);
        ImageView imageView = (ImageView) v.findViewById(R.id.imageHolder);

        if (names.get(position).toString().equals("ATTENDANCE")) {
            imageView.setImageResource(R.drawable.attendance_new);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, AttendanceActivity.class));
                    activity.finish();

                }
            });


        } else if (names.get(position).toString().equals("NOTICE")) {
            imageView.setImageResource(R.drawable.notice_icon_new);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    activity.startActivity(new Intent(activity, NoticeActivity.class));
                    activity.finish();

                }
            });



        } else if (names.get(position).toString().equals("HOMEWORK")) {
            imageView.setImageResource(R.drawable.home_work_new);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, GiveHomeworkActivity.class));
                    activity.finish();
//                    Toast.makeText(activity,"HomeWork selected",Toast.LENGTH_SHORT).show();
                }
            });


        }
        else if (names.get(position).toString().equals("TIME TABLE")) {
            imageView.setImageResource(R.drawable.time_table);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    activity.startActivity(new Intent(activity, SeeTimeTableActivity.class));
                    activity.finish();

                }
            });

        }

        textView.setText(names.get(position).toString());

        return v;
    }
}
