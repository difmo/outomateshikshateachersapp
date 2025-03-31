package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.R;

import java.util.List;

public class CustomCalendarAdapter extends BaseAdapter {
    private final Context context;
    private final List<String> dates;
    private final List<String> statusList;
    private final LayoutInflater inflater;

    public CustomCalendarAdapter(Context context, List<String> dates, List<String> statusList) {
        this.context = context;
        this.dates = dates;
        this.statusList = statusList;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dates.size();
    }

    @Override
    public Object getItem(int position) {
        return dates.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_calendar_cell, parent, false);
        }

        TextView tvDate = convertView.findViewById(R.id.tvDate);

        if (position >= dates.size() || position >= statusList.size()) {
            tvDate.setText("");
            convertView.setBackgroundColor(Color.TRANSPARENT);
            return convertView;
        }

        String dateText = dates.get(position);
        tvDate.setText(dateText.isEmpty() ? "" : dateText);

        String status = statusList.get(position);
        switch (status) {
            case "Present":
                convertView.setBackgroundResource(R.color.presentColor);
                break;
            case "Absent":
                convertView.setBackgroundResource(R.color.absentColor);
                break;
            case "HalfDay":
                convertView.setBackgroundResource(R.color.halfDayColor);
                break;
            case "Holiday":
                convertView.setBackgroundResource(R.color.holidayColor);
                break;
            default:
                convertView.setBackgroundColor(Color.TRANSPARENT);
                break;
        }

        return convertView;
    }
}
