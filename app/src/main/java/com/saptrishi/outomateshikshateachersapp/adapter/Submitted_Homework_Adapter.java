package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.saptrishi.outomateshikshateachersapp.R;
import com.squareup.picasso.Picasso;

import java.util.List;




public class Submitted_Homework_Adapter extends BaseAdapter {

    List<String> Student_name;
    List<String> Homework_status;
    List<String> submitted_date;
    List<String> srno;
    Context context;


    public Submitted_Homework_Adapter( Context context,List<String> Student_name, List<String> Homework_status, List<String> submitted_date, List<String> srno)  {
        this.Student_name = Student_name;
        this.Homework_status = Homework_status;
        this.submitted_date = submitted_date;
        this.srno = srno;
        this.context=context;

    }

    @Override
    public int getViewTypeCount() {
        return getCount();
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getCount() {
        return Student_name.size();
    }
    @Override
    public Object getItem(int position) {
        return Student_name.get(position);
    }
    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
         ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder(); LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            convertView = inflater.inflate(R.layout.submitedhomework_layout, null, true);


            holder.serial=convertView.findViewById(R.id.tv_srno);
            holder.status=convertView.findViewById(R.id.iv_image);
            holder.date=convertView.findViewById(R.id.tv_date);
            holder.tname=convertView.findViewById(R.id.tv_name);
            holder.linearLayout=convertView.findViewById(R.id.layui);
            Log.e("listsss",Student_name.toString());
            Log.e("listsss",submitted_date.toString());
            Log.e("listsss",Homework_status.toString());

            holder.tname.setText(Student_name.get(position));
            holder.serial.setText(srno.get(position));
            holder.date.setText(submitted_date.get(position));
            if(Homework_status.get(position).equalsIgnoreCase("Incomplete")){
                holder.status.setBackgroundResource(R.drawable.task_complete);

            }else {
                holder.status.setBackgroundResource(R.drawable.incomplete);
            }
            if (position==0)
            {
                TextView textView=new TextView(context);
                textView.setText("Status");
                holder.linearLayout.addView(textView);
                holder.status.setVisibility(View.GONE);
            }
        }
        return convertView;
    }

    public class ViewHolder {


         TextView serial;
         LinearLayout linearLayout;
        TextView tname;
        ImageView status;
        TextView date;


    }

}