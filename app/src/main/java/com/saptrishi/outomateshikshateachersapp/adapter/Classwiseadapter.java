package com.saptrishi.outomateshikshateachersapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

//import com.saptrishi.outomateshikshateachersapp.ClasswiseAttandance.Classwise_attend_Adapter;
//import com.saptrishi.outomateshikshateachersapp.ClasswiseAttandance.Viewclasseditatten;
import com.saptrishi.outomateshikshateachersapp.ClickPhotoActivity;
import com.saptrishi.outomateshikshateachersapp.GiveHomeworkDataModel;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.SelectfileActivity;
import com.saptrishi.outomateshikshateachersapp.Student_attendance;
import com.saptrishi.outomateshikshateachersapp.UploadClassworkActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.Classwise_attendance_view;
import com.saptrishi.outomateshikshateachersapp.View.Activity.ClasswiseattendanceActivity;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static android.content.Context.MODE_PRIVATE;

public class Classwiseadapter extends ArrayAdapter<GiveHomeworkDataModel> implements View.OnClickListener {
    private ArrayList<GiveHomeworkDataModel> dataSet;
    Context mContext;
    //   private static final int PICK_IMAGE_REQUEST = 99;
    public final String APP_TAG = "OutomateTeacherApp";
    public final static int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 1034;
    public String photoFileName = "photo.jpg";
    String clsstrucid;
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
        RelativeLayout relativeLayout ;
        Button b;
//        ImageView viewImage;
//        ImageView open_camera;


    }


    public Classwiseadapter(ArrayList<GiveHomeworkDataModel> data, Context context) {
        super(context, R.layout.row_item_givehomework, data);
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
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        // Get the data item for this position
        final GiveHomeworkDataModel dataModel = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        final View result;

        if (convertView == null) {


            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item_studentattendance, parent, false);
            viewHolder.relativeLayout =  (RelativeLayout) convertView.findViewById(R.id.eng);
            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (TextView) convertView.findViewById(R.id.subject_tv);
            SharedPreferences dd = mContext.getSharedPreferences("Dinesh", Context.MODE_PRIVATE);//sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
            clsstrucid = dd.getString("clsssdkid", "");

            SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy");
            Date con_date = null;
            Date to = null;

            String myFormat = "dd-MMM-yyyy"; //In which you need put here
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            Calendar cal = Calendar.getInstance();

            String today = sdf.format(cal.getTime());


            viewHolder.option = (ImageView) convertView.findViewById(R.id.option);// 3dots line
            viewHolder.iconSelectPhoto = (ImageView) convertView.findViewById(R.id.iconSelectPhoto); // camera

            try {
                con_date = sdf.parse(dataModel.getDaateofHomework());
                to = sdf.parse(today);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            if (con_date.before(to)) {
                //have to visible
//                viewHolder.iconSelectPhoto.setVisibility(View.GONE);

                Log.e("photonot", " : selecteddate " + con_date + " today " + today);
            } else {
                Log.e("photocoming", " : " + con_date + " " + today);
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
        viewHolder.info.setText(dataModel.getVersion_number());

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
            viewHolder.info.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.attendancelist,0,0,0);
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
        //  viewHolder.option.setVisibility(View.VISIBLE); //  new visible
        viewHolder.option.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                switch (view.getId()) {
//                    case R.id.option:
//                        showPopupMenu(view, position, dataModel.getName(), dataModel.getempid(), dataModel.getSubjectid(), dataModel.getClassid(), dataModel.getFinaldaytimetableid(), dataModel.getDaateofHomework(), dataModel.getVersion_number());
//                        break;
//                }
            }
        });
        // viewHolder.iconSelectPhoto.setVisibility(View.VISIBLE);  //  camera visibilty gone
        viewHolder.iconSelectPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
//                switch (view.getId()) {
//                    case R.id.iconSelectPhoto:
//                        Intent intent = new Intent(mContext,  Student_attendance.class);
//                        intent.putExtra("position", position);
//                        intent.putExtra("clsstrucid", clsstrucid);
//                        intent.putExtra("getempid", dataModel.getempid());
//                        intent.putExtra("subjectid", dataModel.getSubjectid());
//                        intent.putExtra("classid", dataModel.getClassid());
//                        intent.putExtra("finaldaytimetableid", dataModel.getFinaldaytimetableid());
//                        intent.putExtra("dateodhomework", dataModel.getDaateofHomework());
//                        intent.putExtra("subjectname", dataModel.getVersion_number());
//                        intent.putExtra("classname", dataModel.getName());
//                        intent.putExtra("periodname", dataModel.getType());
//                        mContext.startActivity(intent);
//
//                        break;
//                }
            }
        });
        return convertView;
    }

//    private void showPopupMenu1(final View view, final int i, final String getempid, final String subjectid, final String classid, final String finaldaytimetableid) {
//        PopupMenu popupMenu1 = new PopupMenu(mContext, view);
//        popupMenu1.getMenuInflater().inflate(R.menu.class_wise_attand_menu, popupMenu1.getMenu()); // class wise option menu
//        popupMenu1.show();
//        popupMenu1.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem menuItem) {
//
//                switch (menuItem.getItemId()) {
//                    case R.id.takephoto: {
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
//                        Intent intent = new Intent(mContext, SelectfileActivity.class);
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        mContext.startActivity(intent);
//                        break;
//
//                }
//                return true;
//            }
//        });
//
//    }

    private void showPopupMenu(final View view, final int i, final String classname, final String getempid, final String subjectid, final String classid, final String finaldaytimetableid, final String dateofHomework, final String finalSubjectName) {
        PopupMenu popupMenu = new PopupMenu(mContext, view);
        popupMenu.getMenuInflater().inflate(R.menu.class_wise_attand_menu, popupMenu.getMenu()); // menu classwork
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {

                switch (menuItem.getItemId()) {
//                    case R.id.optionviewone: {
//                        Intent intent = new Intent(mContext, Classwise_attendance_view.class); // Classworkview class
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("class_name", classname);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("subjectName", finalSubjectName);
//                        intent.putExtra("dateodhomework", dateofHomework);
//                        mContext.startActivity(intent);
//                        // mContext.startActivity(new Intent(mContext, ClassWorkView.class));
//                        break;
//                    }

//                    case R.id.optionclassatten:
//                        Intent intent = new Intent(mContext, Student_attendance.class);
//                        intent.putExtra("getempid", getempid);
//                        intent.putExtra("subjectid", subjectid);
//                        intent.putExtra("classid", classid);
//                        intent.putExtra("finaldaytimetableid", finaldaytimetableid);
//                        intent.putExtra("subjectName", finalSubjectName);
//                        intent.putExtra("class_name", classname);
//                        intent.putExtra("clsstrucid", clsstrucid);
//                        Log.e("csdsds", clsstrucid);
//                        mContext.startActivity(intent);
//                        break;


                    //case R.id.see_uploaded_classwork:
                    //  {
//                        Intent intent=new Intent(mContext,Submited_Homework_List.class);
//                        intent.putExtra("getempid",getempid);
//                        intent.putExtra("subjectid",subjectid);
//                        intent.putExtra("classid",classid);
//                        intent.putExtra("finaldaytimetableid",finaldaytimetableid);
//                        mContext.startActivity(intent);
                    //  mContext.startActivity(new Intent(mContext,Uploaded_Homework_See.class));
                    //break;
                    //    }
                }

                return true;
            }
        });

    }


}
