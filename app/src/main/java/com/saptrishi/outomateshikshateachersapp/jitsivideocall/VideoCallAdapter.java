package com.saptrishi.outomateshikshateachersapp.jitsivideocall;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.saptrishi.outomateshikshateachersapp.R;

import java.util.ArrayList;

public class VideoCallAdapter extends ArrayAdapter<VideoCallDataModel> implements View.OnClickListener {
    Context mContext;
    private ArrayList<VideoCallDataModel> dataSet;
    private int lastPosition = -1;


    public VideoCallAdapter(ArrayList<VideoCallDataModel> data, Context context) {
        super(context, R.layout.row_item_videocall, data);
        this.dataSet = data;
        this.mContext = context;
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

    }

    @Override
    public void onClick(View v) {


        int position = (Integer) v.getTag();
        Object object = getItem(position);
        VideoCallDataModel dataModel = (VideoCallDataModel) object;


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
        VideoCallDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_videocall, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (TextView) convertView.findViewById(R.id.subject_tv);
            viewHolder.imagerec = convertView.findViewById(R.id.imagevideorec);
            //             viewHolder.b = (Button) convertView.findViewById(R.id.btnSelectPhoto);
//            viewHolder.viewImage = (ImageView) convertView.findViewById(R.id.viewImage);
//            viewHolder.open_camera = (ImageView) convertView.findViewById(R.id.open_camera);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
        lastPosition = position;

        viewHolder.txtName.setText(dataModel.getName());
        viewHolder.txtType.setText(dataModel.getType());
        viewHolder.txtVersion.setText("");
        viewHolder.info.setText(dataModel.getVersion_number());
        viewHolder.imagerec.setOnClickListener(new View.OnClickListener() {  // m context from another activity
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, Jitsivideo.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);

                //  selectImage();
            }
        });


        //        viewHolder.b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {

//                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//                mContext.startActivity(intent);
//                selectImage();
//            }
//        });
//        viewHolder.info.setTag(position);
//         Return the completed view to render on scree

        return convertView;
    }

    private static class ViewHolder {
        public View btnSelectPhoto;
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView info;
        ImageView imagerec;
    }


}


