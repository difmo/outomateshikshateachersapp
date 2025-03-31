package com.saptrishi.outomateshikshateachersapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.R;

import java.util.List;

public class WeekDaysAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> weekDays;

    public WeekDaysAdapter(Context context, List<String> weekDays) {
        this.context = context;
        this.weekDays = weekDays;
    }

    @Override
    public int getCount() {
        return weekDays.size();
    }

    @Override
    public Object getItem(int position) {
        return weekDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_week_day, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.tvWeekDay);
        textView.setText(weekDays.get(position));

        return convertView;
    }
}

