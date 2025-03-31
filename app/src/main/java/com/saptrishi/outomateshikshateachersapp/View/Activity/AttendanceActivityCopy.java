package com.saptrishi.outomateshikshateachersapp.View.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.snackbar.Snackbar;
import com.saptrishi.outomateshikshateachersapp.Connectivity;
import com.saptrishi.outomateshikshateachersapp.DBHelper.MySqliteDataBase;
import com.saptrishi.outomateshikshateachersapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class AttendanceActivityCopy extends AppCompatActivity {
    TextView setDate, childName, childClass;
    String childid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Window window = this.getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.statusbarcolor));
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.hide();


        if (!Connectivity.isConnected(getApplicationContext())) {
            Snackbar.make(findViewById(android.R.id.content), "Your Attendance is not Sync...", Snackbar.LENGTH_SHORT).show();
        }


        TextView tv = findViewById(R.id.showchildname);
        SharedPreferences sftname = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        tv.setText(sftname.getString("empname",""));


//        ActionBar actionBar = getSupportActionBar();
//        actionBar.hide();
        ImageView backbutton = findViewById(R.id.backpressed);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setDate = findViewById(R.id.tvDate);
        childClass = findViewById(R.id.showChildClass);


//        final CaldroidFragment caldroidFragment = new CaldroidFragment();
        Bundle args = new Bundle();
        final Calendar cal = Calendar.getInstance();
        // Tuesday

//
//        args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
//        args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));


//        caldroidFragment.setArguments(args);

        final MySqliteDataBase mySqliteDataBase = new MySqliteDataBase(this);
        FragmentTransaction t = getSupportFragmentManager().beginTransaction();

//        t.replace(R.id.calc, caldroidFragment);
        t.commit();

//        SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//        final String childid = sharedPreferences.getString("childrenid", "");
//        childName.setText(sharedPreferences.getString("childName", ""));
//        childClass.setText(sharedPreferences.getString("childClass", ""));
//        SharedPreferences.Editor ed = sharedPreferences.edit();
//        ed.putString("chk", "0");
//        ed.apply();
//
//        if (TextUtils.isEmpty(childid)) {
//            Toast.makeText(this, "You did't selected any child", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(AttendanceActivity.this, MainMenuActivity.class));
//            finish();
//        } else {
//            Cursor cursor = mySqliteDataBase.fetchAttendance(childid);
//
//            if (cursor.moveToFirst()) {
//                Log.e("value in table0", "value in table0............................................................");
//                    do {
//                    Log.e("table0showattendance+", "val=" + cursor.getString(0));
//
//                    String sDate1 = cursor.getString(0);
//                    Date date1 = null;
//                    try {
//                        date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//                    assert date1 != null;
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.presentbackground), date1);
//                    }
//
//                } while (cursor.moveToNext());
//                Date date1 = null;
//                try {
//                    date1 = new SimpleDateFormat("dd-MM-yyyy").parse("21-07-2019");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.presentbackground), date1);
//
//            }
//        }

//        FragmentTransaction t = getSupportFragmentManager().beginTransaction();
//
//        t.replace(R.id.calc, caldroidFragment);
//        t.commit();


//        final CaldroidListener listener = new CaldroidListener() {
//
//            @Override
//            public void onSelectDate(Date selectedDate, View view) {
//
////                Toast.makeText(getApplicationContext(), selectedDate+"", Toast.LENGTH_SHORT).show();
//
////                Calendar c = Calendar.getInstance();
////                System.out.println("Current time => " + c.getTime());
//
//                Calendar calendar = Calendar.getInstance();
//                SimpleDateFormat currdateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                String currentDate = currdateFormat.format(calendar.getTime());
//                Date today = null, conditionDate = null;
//
//
//                Log.e("currentDate", currdateFormat + "");
//                Log.e("currentDate", currentDate + "");
//
//                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
//                String Fpdatetime = df.format(selectedDate.getTime());
////                // formattedDate have current date/time
////                Toast.makeText(getApplicationContext(), "before="+selectedDate+" After="+Fpdatetime, Toast.LENGTH_LONG).show();
////
//                Log.e("childID", childid + "=" + Fpdatetime);
//                try {
//                    today = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
//                    conditionDate = new SimpleDateFormat("dd-MM-yyyy").parse(Fpdatetime);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                setDate.setText(Fpdatetime);
//                TextView inTime = findViewById(R.id.tv_inTime);
//                TextView outTime = findViewById(R.id.tv_outTime);
//                TextView status = findViewById(R.id.tv_status);
//                inTime.setText("--:--:--");
//                outTime.setText("--:--:--");
//                assert today != null;
//                assert conditionDate != null;
//                if (conditionDate.before(today) || today.equals(conditionDate)) {
//
//                    LinearLayout inoutContainer = findViewById(R.id.llinoutContainer);
//                    inoutContainer.setVisibility(View.VISIBLE);
//
//
//                    Cursor inTimeCursor = mySqliteDataBase.fetchINTime(childid, Fpdatetime);
//                    Cursor outTimeCursor = mySqliteDataBase.fetchOutTime(childid, Fpdatetime);
//                    Cursor fetchAttStatus = mySqliteDataBase.fetchAttStatus(childid, Fpdatetime);//nEW
//                    //SKS_listener
//
//                    if (inTimeCursor.moveToFirst()) {
//                        Log.e("datecomesin", "=" + inTimeCursor.getString(2));
//                        String in = inTimeCursor.getString(2).substring(0, 8);
//                        inTime.setText(in);
//                        try {
//                            String _24HourTime =in;//
//                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");   /// time format 12 hrs
//                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm: a");
//                            Date _24HourDt = _24HourSDF.parse(_24HourTime);
//                            Log.d("12HoursFormat:",_12HourSDF.format(_24HourDt)+"");
//                            inTime.setText (_12HourSDF.format(_24HourDt)+"");
//                        }
//                        catch (Exception e)
//                        {
//                            e.printStackTrace();
//                        }
//                        Log.e( "onSelectDate: ",""+in );
//                        status.setText("Present");
//
//                    }
//                    if (outTimeCursor.moveToFirst()) {
//                        Log.e("datecomesin", "=" + outTimeCursor.getString(2).substring(0, 8));
////                        outTime.setText(outTimeCursor.getString(2).substring(0, 8));
//                       String out = outTimeCursor.getString(2).substring(0, 8);
//                          outTime.setText(out);
//
//
//                        try {
//                            String _24HourTime = out;//
//                            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm:ss");   /// time format 12 hrs
//                            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm:aa");
//                            Date _24HourDt = _24HourSDF.parse(_24HourTime);
//                            Log.d("12HoursFormat:",_12HourSDF.format(_24HourDt)+"");
//                            outTime.setText (_12HourSDF.format(_24HourDt)+"");
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//
//
//
//
//                        Log.e( "onSelectDate: ",""+out );
//                        status.setText("Present");
//                    }
//                    if (fetchAttStatus.moveToFirst()) {
//                        String status_ = fetchAttStatus.getString(6).toString();
//                        if (status_ != null && status_.equalsIgnoreCase("P")) {
//                            status.setText("Present");
////                            Toast.makeText(AttendanceActivity.this, "Present!", Toast.LENGTH_SHORT).show();
//                        } else if (status_ != null && status_.equalsIgnoreCase("WFH")) {
//                            status.setText("Work From Home");
////                            Toast.makeText(AttendanceActivity.this, "Work From Home!", Toast.LENGTH_SHORT).show();
//                        } else if (status_ != null && status_.equalsIgnoreCase("HD")) {
//                            status.setText("Half day");
////                            Toast.makeText(AttendanceActivity.this, "Half Day!", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    if (fetchAttStatus.moveToFirst() || outTimeCursor.moveToFirst() || inTimeCursor.moveToFirst()) {
//
//                    } else {
//                        status.setText("Absent");
////                        Toast toast =   Toast.makeText(AttendanceActivity.this, "Absent!", Toast.LENGTH_SHORT);
////                        toast.setGravity(Gravity.CENTER, 0, 0);
////                        toast.show();
//                    }
//
//
//                }
//            }
//
//
//            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//            @Override
//            public void onCaldroidViewCreated() {
//
//                SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
//                String stringendDate = "30-12-2021";
//                String startDate = "01-04-2019";
//                Date datestartDate = null;
//                Date dateendDate = null;
//                try {
//                    datestartDate = format.parse(startDate);
//                    dateendDate = format.parse(stringendDate);
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                caldroidFragment.setMaxDate(dateendDate);
//                caldroidFragment.setMinDate(datestartDate);
////                caldroidFragment.setMaxDateFromString("31-03-2020","dd-MM-yyyy");
////                caldroidFragment.setMinDateFromString("01-04-2019","dd-MM-yyyy");
//
//                Date date = null;
//                for (int j = -1; j <= 1; j++) {
//                    for (int i = 1; i <= 52; i++) {
//
//                        try {
//                            cal.add(Calendar.WEEK_OF_YEAR, j);
//                            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//                            String format1 = format.format(cal.getTime());
//
//                            date = format.parse(format1);
//                            if (date.before(datestartDate))
//                                break;
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.holiday), date);
//                        }
//
//                    }
//                }
//
//                for (int i = 1; i <= 52; i++) {
//
//                    try {
//                        cal.add(Calendar.WEEK_OF_YEAR, 1);
//                        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
//                        String format1 = format.format(cal.getTime());
//
//                        date = format.parse(format1);
//                        if (date.after(dateendDate))
//                            break;
//
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.holiday), date);
//                    }
//
//                }
//
//
//                SimpleDateFormat currdateFormat = new SimpleDateFormat("dd-MM-yyyy");
//                Calendar calendar = Calendar.getInstance();
//                String currentDate = currdateFormat.format(calendar.getTime());
//                Date today = null;
//                try {
//                    today = new SimpleDateFormat("dd-MM-yyyy").parse(currentDate);
//                    Log.e("todayMonday", today + "");
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//                for (int k = 2; k <= 7; k++) {
//                    for (int j = -1; j <= 1; j++) {
//                        for (int i = 1; i <= 52; i++) {
//                            try {
//                                cal.add(Calendar.WEEK_OF_YEAR, j);
//                                cal.set(Calendar.DAY_OF_WEEK, k);
//                                String format1 = format.format(cal.getTime());
//                                date = format.parse(format1);
//
//                                Log.e("todayMonday", today + " j=" + j + "");
//                                Log.e("todayMondaystart", datestartDate + " j=" + j + "");
//                                Log.e("todayMondaydate", date + " j=" + j + "");
//
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                            assert today != null;
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && date.before(today)) {
//                                caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.absent), date);
//                            }
//
//                        }
//                    }
//
//                }
//
//
//                SharedPreferences sharedPreferences = getSharedPreferences("ShikshaContainer1", Context.MODE_PRIVATE);
//                childid = sharedPreferences.getString("empid", "");
//                childName = findViewById(R.id.showchildname);
////                childName.setText(sharedPreferences.getString("empname", ""));
////                childClass.setText(sharedPreferences.getString("childClass", ""));
//                SharedPreferences.Editor ed = sharedPreferences.edit();
//                ed.putString("chk", "0");
//                ed.apply();
//
//
//                Cursor cursor = mySqliteDataBase.fetchAttendance(childid);//SKS<----------------fetch
//
//                if (cursor.moveToFirst()) {
//                    Log.e("value in table0", "value in table0............");
//                    do {
//                        Log.e("table0showattendance+", "val=" + cursor.getString(0));
//                        String sDate1 = cursor.getString(0);// PUCHNDATE SKS<----------------
//                        String AttStatus = cursor.getString(1);//SKS<----------------
//                        String Todate = cursor.getString(2);//SKS<----------------
//                        Log.e("DATE_SKS", sDate1 + "--");
//                        Log.e("DATE_SKS", AttStatus + "--");
//                        Log.e("DATE_SKS", Todate + "--");
//
//                        Date date1 = null;
//                        Date punchDate = null;
//                        Date toDate = null;
//                        try {
//                            date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
//                        } catch (ParseException e) {
//                            e.printStackTrace();
//                        }
//                        assert date1 != null;
//
//                        if (AttStatus != null && AttStatus.equalsIgnoreCase("P")) {//new conditions
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.presentbackgound), date1);
//                            }
//                        } else if (AttStatus != null && AttStatus.equalsIgnoreCase("HD")) {//new conditions
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.halfday), date1);
//                            }
//                        } else if (AttStatus != null && AttStatus.equalsIgnoreCase("")) {//new conditions
//                            if (date1 != null) {
//                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                    caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.presentbackgound), date1);
//                                }
//                            }
//
//                        }
//                        if (Todate != null && sDate1 != null) {
//                            try {
//                                punchDate = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
//                                toDate = new SimpleDateFormat("dd-MM-yyyy").parse(Todate);
//                                Log.e("DATE_SKS1", "TESTING" + "--");
//                                List<Date> listOfDates3 = getDaysBetweenDates(punchDate, toDate);
//                                System.out.println("DATE_SKS" + listOfDates3);
//                                for (int i = 0; i < listOfDates3.size(); i++) {
//                                    Log.e("DATE_SKS1", listOfDates3.get(i) + "--");
//                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                        caldroidFragment.setBackgroundDrawableForDate(getDrawable(R.drawable.presentbackgound), listOfDates3.get(i));
//                                    }
//                                }
//
//                                date1 = new SimpleDateFormat("dd-MM-yyyy").parse(sDate1);
//                            } catch (ParseException e) {
//                                e.printStackTrace();
//                            }
//                            assert date1 != null;
//                        }
//
//                    } while (cursor.moveToNext());
//                }
//
//            }
//        };
//
//        caldroidFragment.setCaldroidListener(listener);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        startActivity(new Intent(AttendanceActivityCopy.this, MainMenuActivity.class));
        overridePendingTransition(R.anim.left_toright, R.anim.right_toleft);
        //finish();
    }

    public static List<Date> getDaysBetweenDates(Date startdate, Date enddate) {
        List<Date> dates = new ArrayList<Date>();

        List<Date> lDate = new ArrayList<Date>();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(startdate);

        while (calendar.getTime().before(enddate)) {
            Date result = calendar.getTime();
            dates.add(result);
            calendar.add(Calendar.DATE, 1);
            if (enddate.after(calendar.getTime())) {
                lDate.add(calendar.getTime());
            } else {
                break;
            }
        }
        lDate.add (enddate); // Add the end time to the collection
        return lDate;
    }



//    @Override
//    public void onCaldroidViewCreated() {
//        // Supply your own adapter to weekdayGridView (SUN, MON, etc)
//        caldroidFragment.getWeekdayGridView().setAdapter(YOUR_ADAPTER);
//
//        Button leftButton = caldroidFragment.getLeftArrowButton();
//        Button rightButton = caldroidFragment.getRightArrowButton();
//        TextView textView = caldroidFragment.getMonthTitleTextView();
//
//        // Do customization here
//    }

}
