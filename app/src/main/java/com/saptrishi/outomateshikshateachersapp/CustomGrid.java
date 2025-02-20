package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.View.Activity.Localehelper;
import com.saptrishi.outomateshikshateachersapp.View.Activity.Update;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CustomGrid extends BaseAdapter {
    private final List<String> web;
    private final List<String> flag;
    private final List<String> Imageid;
    private Context mContext;

    public CustomGrid(Context c, List<String> web, List<String> Imageid, List<String> flag) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.flag = flag;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return web.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
        {

            grid = new View(mContext);

            grid = inflater.inflate(R.layout.grid_single, null);
            TextView textView = (TextView) grid.findViewById(R.id.grid_text);
            ImageView imageView = (ImageView) grid.findViewById(R.id.grid_image);
            for (int i = 0; i < web.size(); i++)
            {
                Log.e("flag", "" + web.get(i));

                if (flag.get(i).toString().trim().equals("FALSE")) {
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Time Table"))) {   //Time Table
                    textView.setText(R.string.timetable);
                     //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }


                else if ((flag.get(i).equals("True")&& web.get(position).equals("Attendance"))) {   //Attendance

                    textView.setText(R.string.attendanace);
                    //   textView.setText(R.string.);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Video"))) {   //Video

                    textView.setText(R.string.video);

                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Home Work"))) {   // Home Work


                    textView.setText(R.string.homework);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Class Work"))) {   //Class Work


                    textView.setText(R.string.classwork);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Notice"))) {   //Notice


                    textView.setText(R.string.notice);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("Chat"))) {   //Chat


                    textView.setText(R.string.chat);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }
                else if ((flag.get(i).equals("True")&& web.get(position).equals("CT Attendance"))) {   //CT Attendance


                    textView.setText(R.string.ctattendance);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }   else if ((flag.get(i).equals("True")&& web.get(position).equals("ST Attendance"))) {   //ST Attendance


                    textView.setText(R.string.stattendance);
                    //   grid.setVisibility(View.GONE);
//                        textView.setVisibility(View.GONE);
//                        imageView.setVisibility(View.GONE);
                }








                else {
                    textView.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);
                    textView.setText(web.get(position));
                }

            }

//            textView.setText(web.get(position));
//            imageView.setImageResource(Imageid[position]);
//            implementation 'com.squareup.picasso:picasso:2.5.2'
            Picasso.get().load(Imageid.get(position)).into(imageView);
        } else {
            grid = (View) convertView;
        }

        return grid;
    }
}