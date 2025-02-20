package com.saptrishi.outomateshikshateachersapp;

import android.content.Context;
import android.content.Intent;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.View.Activity.HomeworkVideosActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.MainMenuActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.classwise_subjectupload;

import org.w3c.dom.Text;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import static java.util.Collections.*;

public class SeeTimeTableAdapter extends ArrayAdapter<TimeTableDataModel> implements View.OnClickListener {

    Context mContext;
    private ArrayList<TimeTableDataModel> dataSet;
//    ImageView viewImage;
    private int lastPosition = -1;
    String mydate;

    public SeeTimeTableAdapter(ArrayList<TimeTableDataModel> data, Context context) {
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

//        Animation animation = AnimationUtils.loadAnimation(mContext, (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
//        result.startAnimation(animation);
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
                Log.e( "six ",dataModel.getSubjectid());
                intent.putExtra("info", dataModel.getName());
                intent.putExtra("txtVersion", dataModel.getVersion_number());
                intent.putExtra("subject",dataModel.getVersion_number());
                intent.putExtra("FinalDayTimeTable_Id",dataModel.getFinalDayTimeTable_Id());
                intent.putExtra("Subjectid",dataModel.getSubjectid());
                Log.e("seven",dataModel.getFinalDayTimeTable_Id());
                intent.putExtra("etdate", dataModel.getDate());
                mContext.startActivity(intent);

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

    // View lookup cache
    public static class ViewHolder {
        public View btnSelectPhoto;
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        TextView info;
        TextView FrmTime;
        TextView ToTime;
        ImageButton teacher_uplaod;
//        ImageView viewImage;
//        ImageView open_camera;


    }

//        private void selectImage () {
//            final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//            builder.setTitle("Add Photo!");
//            builder.setItems(options, new DialogInterface.OnClickListener() {
//                @Override
//                public void onClick(DialogInterface dialog, int item) {
//                    if (options[item].equals("Take Photo")) {
//
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
//
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
//                        startActivityForResult(1, RESULT_OK, intent);
//
//                    } else if (options[item].equals("Choose from Gallery")) {
//
//                        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                        startActivityForResult(2, RESULT_OK, intent);
//
//                    } else if (options[item].equals("Cancel")) {
//                        dialog.dismiss();
//                    }
//                }
//            });
//            builder.show();
//        }


//        @SuppressLint("LongLogTag")
//        private void startActivityForResult ( int requestCode, int resultCode, Intent data) {
//            System.out.println("OnActivityCalled");
//
//
//            if (resultCode == RESULT_OK) {
//                if (requestCode == 1) {
//                    File f = new File(Environment.getExternalStorageDirectory().toString());
//                    for (File temp : f.listFiles()) {
//                        if (temp.getName().equals("temp.jpg")) {
//                            f = temp;
//                            break;
//                        }
//                    }
//                    try {
//                        Bitmap bitmap;
//                        BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
//                        bitmap = BitmapFactory.decodeFile(f.getAbsolutePath(),
//                                bitmapOptions);
//                        viewImage.setImageBitmap(bitmap);
//                        String path = Environment
//                                .getExternalStorageDirectory()
//                                + File.separator
//                                + "Phoenix" + File.separator + "default";
//                        f.delete();
//                        OutputStream outFile = null;
//                        File file = new File(path, String.valueOf(System.currentTimeMillis()) + ".jpg");
//                        try {
//                            outFile = new FileOutputStream(file);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, outFile);
//                            outFile.flush();
//                            outFile.close();
//                        } catch (FileNotFoundException e) {
//                            e.printStackTrace();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                } else if (requestCode == 2) {
//                    Uri selectedImage = data.getData();
//                    String[] filePath = {MediaStore.Images.Media.DATA};
//                    Cursor c = getContext().getContentResolver().query(selectedImage, filePath, null, null, null);
//                    c.moveToFirst();
//                    int columnIndex = c.getColumnIndex(filePath[0]);
//                    String picturePath = c.getString(columnIndex);
//                    c.close();
//                    Bitmap thumbnail = (BitmapFactory.decodeFile(picturePath));
//                    Log.w("path of image from gallery......******************.........", picturePath + "");
//                    viewImage.setImageBitmap(thumbnail);
//                }
//            }
//        }
}

