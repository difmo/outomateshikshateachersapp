package com.saptrishi.outomateshikshateachersapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;

import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//import com.saptrishi.outomateshikshateachersapp.ClasswiseAttandance.Classwiseattandance;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.View.Activity.AttendanceActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.Classteacheratten;
import com.saptrishi.outomateshikshateachersapp.View.Activity.ClasswiseattendanceActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.NoticeActivity;
import com.saptrishi.outomateshikshateachersapp.View.Activity.Parent_module;
import com.saptrishi.outomateshikshateachersapp.View.Activity.video_classwise;
import com.saptrishi.outomateshikshateachersapp.jitsivideocall.Jitsivideo;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static android.provider.MediaStore.ACTION_IMAGE_CAPTURE;

public class dasdhboard extends Fragment {
    public static final int REQUEST_CODE = 10;
    LinearLayout linearLayout, linearLayout2;
    MySqliteDataBase mySqliteDataBase;
    List<String> imglist = new ArrayList<>();
    List<String> modulname = new ArrayList<>();
    List<String> pagename = new ArrayList<>();
    List<String> flag = new ArrayList<>();
    GridView grid;
    ImageView imageProfile;
    String profileImageString;
    String branchid;
    SharedPreferences sf;
    SharedPreferences sfp;
    String branch;
    Context context;
    ImageView button1, button2, button3, help;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.dashboard, container, false);
        imageProfile = view.findViewById(R.id.user_image);
        TextView textView = view.findViewById(R.id.tv_teacherName);
        sf = getContext().getSharedPreferences("ShikshaContainer1", MODE_PRIVATE);
        sfp = getContext().getSharedPreferences("Teacher", MODE_PRIVATE);
        help = view.findViewById(R.id.help);
        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Help_activity.class);
                startActivity(intent);
            }
        });
        textView.setText(sf.getString("empname", ""));
        String branchid = sf.getString("Branchid", "");
        Log.e("Shantanukk", String.valueOf(branchid));
        grid = (GridView) view.findViewById(R.id.grid);
        button1 = view.findViewById(R.id.youtube);
        button2 = view.findViewById(R.id.facbook);
        button3 = view.findViewById(R.id.instagram);
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchid.equals("10")) {


                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/outomate_shiksha/?hl=en"));
                    dasdhboard.this.startActivity(webIntent);
                }
                else if (branchid.equals("24")) {

                    Toast.makeText(getActivity(), "Video link is coming soon!",
                            Toast.LENGTH_LONG).show();
                }

                else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/dpsbudaunofficial"));
                    dasdhboard.this.startActivity(webIntent);
                }


            }
        });


        button2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (branchid.equals("10")) {


                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/saptrishi19/"));
                    dasdhboard.this.startActivity(webIntent);
                }
                else if (branchid.equals("24")) {

                    Toast.makeText(getActivity(), "Video link is coming soon!",
                            Toast.LENGTH_LONG).show();
                }

                // https://www.facebook.com/saptrishi19///  Uri.parse("https://www.facebook.com/dpsbudaun"));
                else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/dpsbudaun"));
                    dasdhboard.this.startActivity(webIntent);
                }

            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (branchid.equals("10")) {
                    Toast.makeText(getActivity(), "Video is coming soon!",
                            Toast.LENGTH_LONG).show();
                }
                else if (branchid.equals("24")) {

                    Toast.makeText(getActivity(), "Video link is coming soon!",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    Intent webIntent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.youtube.com/c/DelhiPublicSchoolBudaun/videos"));
                    dasdhboard.this.startActivity(webIntent);
                }
            }
        });
        mySqliteDataBase = new MySqliteDataBase(getContext());


        profileImageString = sfp.getString("profileImageString", "");
        byte[] decodedString = Base64.decode(profileImageString, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        if (profileImageString.equals("")) {
            imageProfile.setImageResource(R.drawable.userclick);

        } else {
            imageProfile.setImageBitmap(decodedByte);
        }


        Sending_AttendenceToDB sending_attendenceToDB = new Sending_AttendenceToDB(getContext());
        try {
            sending_attendenceToDB.sendtodb();
        } catch (JSONException e) {
            e.printStackTrace();
        }


        setpermissions();
        sf = getActivity().getSharedPreferences("Dashboard", MODE_PRIVATE);
        return view;


    }


    @SuppressLint("Range")
    public void setpermissions() {

        Cursor cursor = mySqliteDataBase.featchpermision();
        Log.e("permission_data", cursor.getCount() + "");
//        if (cursor.getCount() == 0) {
//            //alert Dialogbox
//            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
////            builder.setMessage("You Have No bus service") .setTitle("Alert");
//            builder.setMessage("Sorry! You have no any authorities.")
//                    .setCancelable(false)
//                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
//                        public void onClick(DialogInterface dialog, int id) {
//                            dialog.cancel();
//                        }
//                    });
////                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
////                        public void onClick(DialogInterface dialog, int id) {
////                            //  Action for 'NO' Button
////                            dialog.cancel();
//////                            Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
//////                                    Toast.LENGTH_SHORT).show();
////                        }
////                    });
//            AlertDialog alert = builder.create();
//            //Setting the title manually
////            alert.setTitle("AlertDialogExample");
//            alert.show();
//        }
//         else {

            if (cursor.moveToFirst()) {
                do {
                    Log.e("powess", cursor.getString(cursor.getColumnIndex("ModuleImg")));
                    imglist.add(cursor.getString(cursor.getColumnIndex("ModuleImg")));


                    Log.e("powess", cursor.getString(cursor.getColumnIndex("ModuleName")));
                    modulname.add(cursor.getString(cursor.getColumnIndex("ModuleName")));

                    Log.e("powess", cursor.getString(cursor.getColumnIndex("subPgname")));
                    pagename.add(cursor.getString(cursor.getColumnIndex("subPgname")));
                    flag.add(cursor.getString(cursor.getColumnIndex("flagCity")));
                } while (cursor.moveToNext());
            }
//            Log.e("pooii", modulname.get(0) + " , " + modulname.size() + " , " + pagename);
//            imglist.get(0)

            CustomGrid adapter = new CustomGrid(getContext(), modulname, imglist, flag);

            grid.setAdapter(adapter);
            grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {

                    //                    ((TextView) gridView.getItemAtPosition(position)).setBackgroundColor(Color.RED)
//                    Toast.makeText(getContext(), "You Clicked at " +pagename.get(position), Toast.LENGTH_SHORT).show();


                    switch (pagename.get(position)) {
                        case "AttendanceActivity":
                            view.setBackgroundColor(Color.LTGRAY);
                            Intent intent = new Intent(getContext(), AttendanceActivity.class);
                            startActivity(intent);
                            break;
                        case "NoticeActivity":
                            view.setBackgroundColor(Color.LTGRAY);
                            startActivity(new Intent(getContext(), NoticeActivity.class));
                            break;
                        case "GiveHomeworkActivity":
                            view.setBackgroundColor(Color.LTGRAY);
                            startActivity(new Intent(getContext(), GiveHomeworkActivity.class));
                            break;
                        case "SeeTimeTableActivity":
                            view.setBackgroundColor(Color.LTGRAY);
                            startActivity(new Intent(getContext(), SeeTimeTableActivity.class));
                            break;
                        case "OtherTeacher":
                            view.setBackgroundColor(Color.LTGRAY);
//                            startActivity(new Intent(getContext(), video_classwise.class));
                            startActivity(new Intent(getContext(), Chat_Topic_Activity.class));
                            break;
                        case "ClassAttendance":
                            view.setBackgroundColor(Color.LTGRAY);
//                            startActivity(new Intent(getContext(), video_classwise.class));
                           // startActivity(new Intent(getActivity(), ClasswiseattendanceActivity.class));
                           startActivity(new Intent(getActivity(),Classteacheratten.class));

                            break;

                        case "TechCWActivity":
                            view.setBackgroundColor(Color.LTGRAY);
                            startActivity(new Intent(getContext(),Classwork_Activity.class));
                          // Classwork_Activity Parent_module
                            break;

//                        case "CTAtendanceActivity":
//                            view.setBackgroundColor(Color.LTGRAY);
////                            startActivity(new Intent(getContext(), Classteacheratten.class));
//                                startActivity(new Intent(getContext(), ClasswiseattendanceActivity.class));
//                            break;


                        case "VideoActivity":
                            view.setBackgroundColor(Color. LTGRAY );
                            startActivity(new Intent(getContext(), Jitsivideo.class));
                            break;

                            case "STAttendance":
                            view.setBackgroundColor(Color. LTGRAY );
                                startActivity(new Intent(getContext(), ClasswiseattendanceActivity.class));
                            break;
                             case "ptmActivity":
                            view.setBackgroundColor(Color. LTGRAY );
                                startActivity(new Intent(getContext(), Parent_module.class));
                            break;


//                        case "SchoolActivity":
//                            startActivity(new Intent(getContext(), webview.class));
//
//                            break;


                        default:
                            Log.e("onclickicon", "No Match");

                    }
                }


            });


            imageProfile.setOnClickListener(new View.OnClickListener() {             //capture profile image and set here
                @Override
                public void onClick(View view) {
                    Intent camera_intent = new Intent(ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, REQUEST_CODE);
                }
            });

//        }


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE && resultCode == Activity.RESULT_OK) {


            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageProfile.setImageBitmap(bitmap);

            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] byteArray = byteArrayOutputStream.toByteArray();

            String encoded = Base64.encodeToString(byteArray, Base64.DEFAULT);
            SharedPreferences.Editor editor = sfp.edit();
            editor.putString("profileImageString", "" + encoded);
            editor.apply();

        }

    }

}
