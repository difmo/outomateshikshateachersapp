package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

//import com.saptrishi.outomateshikshateachersapp.BuildConfig;
import com.ms.square.android.expandabletextview.BuildConfig;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;
import com.saptrishi.outomateshikshateachersapp.adapter.CustomListAdapter;
import com.saptrishi.outomateshikshateachersapp.utils.MsgList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import android.content.SharedPreferences;

public class NoticeActivity extends AppCompatActivity {
    TextView tv_versionnames;
    String[] from = {"date", "content"};
    int[] to = {R.id.noticeDate, R.id.noticeContent};
      MySqliteDataBase dbHelper;


    SharedPreferences sharedPreferences;

    @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        tv_versionnames=findViewById(R.id.tv_versionname);
        tv_versionnames.setText(String.valueOf( BuildConfig.VERSION_NAME ));


        dbHelper=new MySqliteDataBase(this);
        LinearLayout backbutton=findViewById(R.id.backpressed);
//        backbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();

         sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
        TextView tv=findViewById(R.id.showchildname);
//        tv.setText(sharedPreferences.getString("empname",""));

        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("chk", "0");
        ed.apply();
            doOnWithoutInterNet();
//        final ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();
//
//
//        ListView lv = findViewById(R.id.noticelistView);
//        int i = 0;
//        do {
//            HashMap<String, String> hm1 = new HashMap<String, String>();
//            hm1.put("date", "11/3/2019");
//            hm1.put("content", "This is subject of Notice");
//            al.add(hm1);
//            i++;
//        } while (i < 22);
//
//
//        final SimpleAdapter sa = new SimpleAdapter(NoticeActivity.this, al, R.layout.noticedisplaypattern, from, to);
//        lv.setAdapter(sa);
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                TextView tv = view.findViewById(R.id.noticeContent);
//                tv.setText("Content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice content of the Notice ");
//            }
//        });
    }

    private void doOnWithoutInterNet() {

                final ArrayList<HashMap<String, String>> al = new ArrayList<HashMap<String, String>>();


        ListView lv = findViewById(R.id.noticelistView);
        String Stuid=sharedPreferences.getString("empid","");
        Cursor c=dbHelper.fetchNotice(Stuid);
        List<Map<String, String>> itemObj= new ArrayList<Map<String, String>>();
         List<MsgList> list = new ArrayList<MsgList>();
        Log.e("count",c.getCount()+"");
        if (c.moveToFirst()) {

            do {

                Log.e("count",c.getCount()+"");
                MsgList msgList=new MsgList();
//                Map<String, String> map = new HashMap<String, String>();
                 HashMap<String, String> map = new HashMap<String, String>();
                String date = c.getString(2);
                String msg = c.getString(1);
                Log.e("msgnotice",msg);
                msgList.setmsgDate(date);
                msgList.setmsgTxt(msg);
                map.put("date", date);
                map.put("content", msg);
                //Log.e("msgRR",""+msg);
                itemObj.add(map);
                list.add(msgList);



            } while (c.moveToNext());
            c.close();

            CustomListAdapter adapter=new CustomListAdapter(this, itemObj, R.id.noticelistView,from,to, list);
//            {
//                @Override
//                public View getView(int position, View convertView, ViewGroup parent){
//                // Get the current item from ListView
//                View view = super.getView(position,convertView,parent);
//                if(position %2 == 1)
//                {
//                    // Set a background color for ListView regular row/item
//                    view.setBackgroundColor(Color.parseColor("#FCE4EC"));
//                }
//                else
//                {
//                    // Set the background color for alternate row/item
//                    view.setBackgroundColor(Color.parseColor("#FEEBEE"));
//                }
//                return view;
//            }
//            };

        lv.setAdapter(adapter);
        }
        else
        {
            Toast.makeText(this, "No Notice...", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(NoticeActivity.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright,R.anim.right_toleft);
        finish();
    }

}
