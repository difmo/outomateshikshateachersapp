package com.saptrishi.outomateshikshateachersapp.adapter;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.model.AttendanceviewModel;


import java.util.List;

public class Attendanceviewadapter extends RecyclerView.Adapter<Attendanceviewadapter.ViewHolder> {

    Context context;
    List<AttendanceviewModel> jsonObject;


    public Attendanceviewadapter(Context applicationContext, List<AttendanceviewModel> jsonObject_) {
        context = applicationContext;
        jsonObject = jsonObject_;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.attandanceviews, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        try {
            holder.attan_status.setText(jsonObject.get(position).getAttan_Status());
            holder.stud_name.setText(jsonObject.get(position).getStudentname());
            holder.tv_attan_desc.setText(jsonObject.get(position).getStuAttDscription());

//            holder.attan_status.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(holder.attan_status.isChecked()){
//                        holder.attan_status.setText("A");
//                        jsonObject.get(position).setAttan_Status("A");
//                    }else{
//
//                        holder.attan_status.setText("P");
//                        jsonObject.get(position).setAttan_Status("P");
//                    }
//                }
//            });

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Override
    public int getItemCount() {
        return jsonObject.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView stud_name;
        TextView attan_status;
        TextView tv_attan_desc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            attan_status = itemView.findViewById(R.id.status_attendance);
            stud_name = itemView.findViewById(R.id.tv_stuname);
            tv_attan_desc=itemView.findViewById(R.id.tv_att_description);
        }


    }
}