package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.saptrishi.outomateshikshateachersapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HomeworkImageAdapter extends BaseAdapter {
    Context context;
    List<String> logos;
    LayoutInflater inflter;
    public HomeworkImageAdapter(Context applicationContext, List<String> logos) {
        this.context = applicationContext;
        this.logos = logos;
        inflter = (LayoutInflater.from(applicationContext));
    }
    @Override
    public int getCount() {
        return logos.size();
    }
    @Override
    public Object getItem(int i) {
        return null;
    }
    @Override
    public long getItemId(int i) {
        return 0;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.activity_gridview, null); // inflate the layout
        ImageView icon = (ImageView) view.findViewById(R.id.icon); // get the reference of ImageView
        Picasso.get().load(logos.get(i)).into(icon);
        return view;
    }
}
