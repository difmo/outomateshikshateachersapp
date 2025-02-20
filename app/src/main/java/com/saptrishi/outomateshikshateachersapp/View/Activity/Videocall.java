package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.TimeTableDataModel;

import java.util.ArrayList;

public class Videocall extends ArrayAdapter<TimeTableDataModel> implements View.OnClickListener {

    Context mContext;
    private ArrayList<TimeTableDataModel> dataSet;
    //    ImageView viewImage;
    private int lastPosition = -1;
    String mydate;

    public Videocall(ArrayList<TimeTableDataModel> data, Context context) {
        super(context, R.layout.row_item_seetimetable, data);
        this.dataSet = data;
        this.mContext = context;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }


    @Override
    public void onClick(View v) {


        int position = (Integer) v.getTag();
        Object object = getItem(position);
        TimeTableDataModel dataModel = (TimeTableDataModel) object;


        switch (v.getId()) {

//            case R.id.item_info:

//                Snackbar.make(v, "Release date " +dataModel.getFeature(), Snackbar.LENGTH_LONG)
//                        .setAction("No action", null).show();

//                break;


        }

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position

        TimeTableDataModel dataModel = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
     ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_seetimetable, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (TextView) convertView.findViewById(R.id.subject_tv);
            viewHolder.FrmTime = (TextView) convertView.findViewById(R.id.time);
            viewHolder.ToTime = (TextView) convertView.findViewById(R.id.totimes);
            viewHolder.teacher_uplaod = (ImageButton) convertView.findViewById(R.id.classatte);

            //            viewHolder.b = (Button) convertView.findViewById(R.id.btnSelectPhoto);
//            viewHolder.viewImage = (ImageView) convertView.findViewById(R.id.viewImage);
//            viewHolder.open_camera = (ImageView) convertView.findViewById(R.id.open_camera);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }


        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText("");
        viewHolder.info.setText(dataModel.getVersion_number());
        viewHolder.FrmTime.setText(dataModel.FrmTime());
        viewHolder.ToTime.setText(dataModel.ToTime());





        viewHolder.teacher_uplaod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, classwise_subjectupload.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("classids",dataModel.getClasssid());
                Log.e("one",dataModel.getClasssid());
                Log.e("two",dataModel.getName());
                Log.e("there",dataModel.getType());
                Log.e("four",dataModel.getVersion_number());
                Log.e( "five ",dataModel.getDate());
                intent.putExtra("info", dataModel.getName());
                intent.putExtra("txtVersion", dataModel.getVersion_number());
                intent.putExtra("subject",dataModel.getVersion_number());
                intent.putExtra("FinalDayTimeTable_Id",dataModel.getFinalDayTimeTable_Id());
                Log.e("six",dataModel.getFinalDayTimeTable_Id());
                intent.putExtra("etdate", dataModel.getDate());
                mContext.startActivity(intent);

            }
        });


        return convertView;
    }

    // View lookup cache
    private static class ViewHolder {
        public View btnSelectPhoto;
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView info;
        TextView FrmTime;
        TextView ToTime;
        ImageButton teacher_uplaod;


    }

}