package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;

import androidx.core.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.saptrishi.outomateshikshateachersapp.View.Activity.HomeworkVideosActivity;
import com.saptrishi.outomateshikshateachersapp.multipleImageSelection.ImagesActivity;
import com.saptrishi.outomateshikshateachersapp.multipleImageSelection.PdfActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static androidx.core.app.ActivityCompat.startActivityForResult;
import static androidx.core.content.FileProvider.getUriForFile;

public class GiveHomeworkAdapter extends ArrayAdapter<GiveHomeworkDataModel> implements View.OnClickListener {

    private ArrayList<GiveHomeworkDataModel> dataSet;
    Context mContext;

    public final String APP_TAG = "OutomateTeacherApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    File photoFile;


//    ImageView viewImage;


    // View lookup cache
    private static class ViewHolder {
        public View btnSelectPhoto;
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView info;
        ImageView viewImage;
        ImageView option;
        ImageView iconSelectPhoto;
        ImageView youtubeImage;
        Button b;
//        ImageView viewImage;
//        ImageView open_camera;


    }


    public GiveHomeworkAdapter(ArrayList<GiveHomeworkDataModel> data, Context context) {
        super(context, R.layout.row_item_givehw_layout, data);
        this.dataSet = data;
        this.mContext = context;


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());


    }

    @Override
    public void onClick(View v) {


        int position = (Integer) v.getTag();
        Object object = getItem(position);
        GiveHomeworkDataModel dataModel = (GiveHomeworkDataModel) object;


        switch (v.getId()) {


        }

    }

    static final int REQUEST_TAKE_PHOTO = 1;

    private int lastPosition = -1;
    static final int REQUEST_IMAGE_CAPTURE = 1;

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final GiveHomeworkDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag

        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_givehw_layout, parent, false);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (TextView) convertView.findViewById(R.id.subject_tv);
            viewHolder.youtubeImage=(ImageView)convertView.findViewById(R.id.youtubeImage);

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date con_date = null;
            Date to = null;

            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Calendar cal = Calendar.getInstance();

            String today = sdf.format(cal.getTime());


            viewHolder.option = (ImageView) convertView.findViewById(R.id.option);
            viewHolder.iconSelectPhoto = (ImageView) convertView.findViewById(R.id.iconSelectPhoto);

            try {
                con_date = sdf.parse(dataModel.getDaateofHomework());
                to = sdf.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (con_date.before(to)) {
                viewHolder.iconSelectPhoto.setVisibility(View.GONE);
                Log.e("dateiftru", " : selecteddate " + con_date + " today " + today);
            } else {
                Log.e("dateelsetru", " : " + con_date + " " + today);
            }


            viewHolder.viewImage = (ImageView) convertView.findViewById(R.id.viewImage);

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
        viewHolder.info.setText(dataModel.getVersion_number()); // This set background images

        if(dataModel.getVersion_number().equalsIgnoreCase("Physics")) {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.physics,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("Chemistry"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.chemistry,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("Biology"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.biology,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("Music"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.music,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("English"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.english,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("History"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.histroy,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("S.ST"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.socialstudy,0,0,0);
        }

        else if(dataModel.getVersion_number().equalsIgnoreCase("IT"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.its,0,0,0);
        }
        else if(dataModel.getVersion_number().equalsIgnoreCase("Hindi"))
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.hindi,0,0,0);
        }
        else
        {
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.other,0,0,0);
        }



        
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
        viewHolder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (view.getId()) {
//                    case R.id.option:
//                        showPopupMenu(view, position, dataModel.getempid(), dataModel.getSubjectid(), dataModel.getClassid(), dataModel.getFinaldaytimetableid(), dataModel.getDaateofHomework());
//                        break;
                }
            }
        });

        viewHolder.iconSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                switch (view.getId()) {
//                    case R.id.iconSelectPhoto:
//                        showPopupMenu1(view, position, dataModel.getempid(), dataModel.getSubjectid(), dataModel.getClassid(), dataModel.getFinaldaytimetableid(),dataModel.getVersion_number(),dataModel.getName());
//                        break;
//                }
            }
        });
        viewHolder.youtubeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent=new Intent(getContext(),HomeworkVideosActivity.class);
                intent.putExtra("Timetableid",dataModel.getFinaldaytimetableid());
                Log.e("d1ftId",""+dataModel.getFinaldaytimetableid());
                intent.putExtra("classid",dataModel.getClassid());
                Log.e("d2clId",""+dataModel.getClassid());
                intent.putExtra("subid", dataModel.getSubjectid());
                Log.e("d3suId",""+dataModel.getSubjectid());
                getContext().startActivity(intent);


            }
        });
        return convertView;
    }

    private void showPopupMenu1(final View view, final int i, final String getempid, final String subjectid, final String classid, final String finaldaytimetableid, String version_number, String txtName) {
        PopupMenu popupMenu1 = new PopupMenu(mContext, view);
        popupMenu1.getMenuInflater().inflate(R.menu.option_menu2, popupMenu1.getMenu());
        popupMenu1.show();
        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

//                switch (menuItem.getItemId()) {
//                    case R.id.takephoto:{
////                        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
////                        mContext.startActivity(intent);
//
////                        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                        if (takePictureIntent.resolveActivity(mContext.getPackageManager()) != null) {
////                            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
////                        }
//
////                        dispatchTakePictureIntent();
//
//                        Intent intent = new Intent(mContext, ClickPhotoActivity.class);
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        mContext.startActivity(intent);
//                    }
//
//                    break;
//                    case R.id.gallery:
//                        Intent intent = new Intent(mContext, ImagesActivity.class); // new changed
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        mContext.startActivity(intent);
//                        break;
//
//
//
//                case R.id.pdf:
//                    Intent intent1 = new Intent(mContext, PdfActivity.class); // new changed
//                    intent1.putExtra("getempid", getempid);
//                    Log.e("dEmployeeId",""+getempid);
//                    intent1.putExtra("subjectid", subjectid);
//                    Log.e("dSubjectId",""+subjectid);
//                    intent1.putExtra("classid", classid);
//                    Log.e("dClass",""+classid);
//                    intent1.putExtra("finaldaytimetableid", finaldaytimetableid);
//                    Log.e("dFinalDayTime",""+finaldaytimetableid);
//                    intent1.putExtra("subname",version_number);
//                    Log.e("dSubname",""+version_number);
//                    intent1.putExtra("classnamethere",txtName);
//                    Log.e("sks",""+txtName);
//                    mContext.startActivity(intent1);
//                break;
//
//
//            }
                return true;
            }
        });

    }

    private void showPopupMenu(final View view, final int i, final String getempid, final String subjectid, final String classid, final String finaldaytimetableid, final String dateofHomework) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.option_menu1, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
//                    case R.id.delete: {
//                        Intent intent = new Intent(mContext, EditHomeWorkActivity.class);
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        intent.putExtra("dateodhomework", dateofHomework);
//                        mContext.startActivity(intent);
//                        break;
//                    }
//                    case R.id.checkhomework: {
//                        Intent intent = new Intent(mContext, Submited_Homework_List.class);
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        mContext.startActivity(intent);
//                        break;
//                    }
                }

                return true;
            }
        });


    }

}