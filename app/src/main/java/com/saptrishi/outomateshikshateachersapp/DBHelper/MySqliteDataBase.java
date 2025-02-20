package com.saptrishi.outomateshikshateachersapp.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class MySqliteDataBase {
    Context context;
    private MySqliteDBHelper dbHelper;

    public MySqliteDataBase(Context context) {
        this.context = context;
        dbHelper = new MySqliteDBHelper(context);
    }

    public long insertchildNotice(String content, String msgDateTime, int smsId, String childid) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.SmsContent, content);

        cv.put(dbHelper.msgdatetime, msgDateTime);

        cv.put("smsid", smsId);
        cv.put("childrenid", childid);

        return db.insertWithOnConflict(dbHelper.noticeTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }
//____________________________________________________________________________________________________________________________________________________________________________________________________________

    public long insertinstitutioninfo(String institutionname, byte[] institutionlogo) throws SQLiteException {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(dbHelper.institution_name, institutionname);

        cv.put(dbHelper.institution_logo, institutionlogo);

        return db.insertWithOnConflict(dbHelper.basic_school_registration_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long insertchildinfo(String chId, String chname) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.childid, chId);
        cv.put(dbHelper.childname, chname);

        return db.insertWithOnConflict(dbHelper.childInfo_tbl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long insertchildAttendance(String AttDate, String AttTime, String StuAtt_id, String in_outStatus, String studentidattendance, String att_status, String to_date) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.PunchDate, AttDate);
        cv.put(dbHelper.PunchTime, AttTime);
        cv.put(dbHelper.UserRefId, StuAtt_id);
        cv.put(dbHelper.In_OutStatus, in_outStatus);
        cv.put(dbHelper.studentidattendance, studentidattendance);
        cv.put(dbHelper.AttStatus, att_status);
        cv.put(dbHelper.Todate, to_date);
        Log.e("Error", "SKS" + att_status);
        return db.insertWithOnConflict(dbHelper.childAttendanceTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);

    }

    /***************************************************************************/

    public long permissioninsertion(String AppModuleID, String AppsublnksID, String ModuleImg, String ModuleName, String WhichApp, String errormessage, String subPgname,String flagCity) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.AppModuleID, AppModuleID);
        cv.put(dbHelper.AppsublnksID, AppsublnksID);
        cv.put(dbHelper.ModuleImg, ModuleImg);
        cv.put(dbHelper.ModuleName, ModuleName);
        cv.put(dbHelper.WhichApp, WhichApp);
        cv.put(dbHelper.errormessage, errormessage);
        cv.put(dbHelper.subPgname, subPgname);
        cv.put(dbHelper.flagCity, flagCity);


        return db.insertWithOnConflict(dbHelper.permissiontable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public long attendenceinsertion(String ClsStruId, String StuId, String SubjectId, String Attan_Status, String EmpId, String Attan_date, String Attan_time, String Attan_brid, String Attan_Sessionid, String Att_Description) {
        Log.e("ERROR_EMPID",EmpId);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.ClsStruId, ClsStruId);
        cv.put(dbHelper.StuId, StuId);
        cv.put(dbHelper.SubjectId, SubjectId);
        cv.put(dbHelper.Attan_Status, Attan_Status);
        cv.put(dbHelper.EmpId, EmpId);
        cv.put(dbHelper.Attan_date, Attan_date);
        cv.put(dbHelper.Attan_time, Attan_time);
        cv.put(dbHelper.Attan_brid, Attan_brid);
        cv.put(dbHelper.Attan_Sessionid, Attan_Sessionid);
        cv.put(dbHelper.Att_Description, Att_Description);

        return db.insertWithOnConflict(dbHelper.attendancetTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

//    public boolean attendanceupdation(String ClsStruId,String StuId,String SubjectId,String Attan_Status,String EmpId,String Attan_date,String Attan_time,String Attan_brid,String Attan_Sessionid)
//    {
//        SQLiteDatabase db=dbHelper.getReadableDatabase();
//          db.execSQL("Update"+ MySqliteDBHelper.attendancetTable+"SET ClsStruId  = "+dbHelper.ClsStruId+ " ,StuId = "+dbHelper.StuId+" ,SubjectId = "+dbHelper.SubjectId+" ,Attan_Status ="+dbHelper.Attan_Status+" ,EmpId ="+dbHelper.EmpId+", Attan_date ="+dbHelper.Attan_date+" ,Attan_time= "+dbHelper.Attan_time+" ,Attan_brid = "+dbHelper.Attan_brid+" ,Attan_Sessionid ="+dbHelper.Attan_Sessionid+"where StuId= " +dbHelper.StuId);
//          return true;
//    }  updation

    public Cursor featchattendance() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from 'attendancetTable'", null);
        return cursor;
    }

    public void deleteattendancetable() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from 'attendancetTable'");
    }


    public void deletepermissiontable() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM 'permissiontable'");
    }

    public Cursor featchpermision() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        String query = "select ModuleImg, ModuleName, subPgname, flagCity from 'permissiontable'";
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    /***************************************************************************/

    public void deletechildAttendance() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + dbHelper.childAttendanceTable);
    }

    public void deleteMasterTable() {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + dbHelper.masterTable);
    }


    //    public void deleteNotice()
//    {
//
//        SQLiteDatabase db= dbHelper.getWritableDatabase();
//        db.execSQL("delete from "+ dbHelper.noticeTable);
//    }
//
//
////_____________________________________________________________________________________________________________________________________________________________________________________________________________
//
//        public Cursor fetchNotice(){
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql = "select * from " + dbHelper.noticeTable+" ORDER BY " +"smsid" + " DESC";
//        Cursor cursor = db.rawQuery(sql , null);
//        return cursor;
//    }
    public Cursor fetchNotice(String childid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.noticeTable + " where childrenid = " + childid + " ORDER BY " + "smsid" + " DESC";
        Log.e("fetchAttendance", sql);
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor fetchMaxNoticeID(String childid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
//        String sql="select max(smsid) from "+dbHelper.noticeTable+" where childrenid = "+childid;
        Cursor cursor1 = db.rawQuery("select " + "max(smsid)" + " from " + dbHelper.noticeTable + " where " + dbHelper.childrenid + " = ?", new String[]{childid});
//        Cursor cursor=db.rawQuery(sql,null);
        return cursor1;

    }

    public Cursor fetchchildinfo() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.childInfo_tbl;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }
    //old
    /*public Cursor fetchAttendance(String childid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select PunchDate from " + dbHelper.childAttendanceTable + " where studentidattendance=" + childid;
        Log.e("fetchAttendance", sql);
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }*/
    //modify
    public Cursor fetchAttendance(String childid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select PunchDate , AttStatus , Todate from " + dbHelper.childAttendanceTable + " where studentidattendance=" + childid;
        Log.e("fetchAttendance", sql);
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor fetchINTime(String childid, String selectedDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.childAttendanceTable + " where " + dbHelper.studentidattendance + " = ? and " + dbHelper.PunchDate + "=? and " + dbHelper.In_OutStatus + "=? and " + dbHelper.AttStatus + "= ?", new String[]{childid, selectedDate, "1", ""});
        return cursor1;
    }

    public Cursor fetchOutTime(String childid, String selectedDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.childAttendanceTable + " where " + dbHelper.studentidattendance + " = ? and " + dbHelper.PunchDate + "=? and " + dbHelper.In_OutStatus + "=? and " + dbHelper.AttStatus + "=?", new String[]{childid, selectedDate, "2",""});
        return cursor1;
    }

    //for AttStatus
    public Cursor fetchAttStatus(String childid, String selectedDate) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.childAttendanceTable + " where " + dbHelper.studentidattendance + " = ? and " + dbHelper.PunchDate + "= ? ", new String[]{childid, selectedDate});
        return cursor1;
    }


    public long insertDatainMasterTable(String BranchId, String Logo, String SchoolName, String SchoolNameShort, String Staff_ClientId, String Staff_Id_Fk, String Staff_Name, String Teacherdob, String doj, String emp_id, String empname, String mobno, String fatername, String Qualification, String pre_add, String parm_add, String profile_img, String bloodgrp,String FlagCT) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.BranchId, BranchId);
        cv.put(dbHelper.Logo, Logo);
        cv.put(dbHelper.SchoolName, SchoolName);
        cv.put(dbHelper.SchoolNameShort, SchoolNameShort);
        cv.put(dbHelper.Staff_ClientId, Staff_ClientId);
        cv.put(dbHelper.Staff_Id_Fk, Staff_Id_Fk);
        cv.put(dbHelper.Staff_Name, Staff_Name);
        cv.put(dbHelper.Teacherdob, Teacherdob);
        cv.put(dbHelper.doj, doj);
        cv.put(dbHelper.emp_id, emp_id);
        cv.put(dbHelper.empname, empname);
        cv.put(dbHelper.mobno, mobno);
        cv.put(dbHelper.fatername, fatername);
        cv.put(dbHelper.Qualification, Qualification);
        cv.put(dbHelper.pre_add, pre_add);
        cv.put(dbHelper.parm_add, parm_add);
        cv.put(dbHelper.profile_img, profile_img);
        cv.put(dbHelper.bloodgrp, bloodgrp);

        return db.insertWithOnConflict(dbHelper.masterTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }


    public long inserttimetable(String classstruid, String classname, String subjectid, String TeacherID) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.clsstruci, classstruid);
        cv.put(dbHelper.classname, classname);
        cv.put(dbHelper.SubjectID, subjectid);
        cv.put(dbHelper.TeacherID, TeacherID);
        return db.insertWithOnConflict(dbHelper.timetable_tabl, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor featchtimetable() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select distinct clsstruci,classname,TeacherID from " + dbHelper.timetable_tabl, null);
        return cursor;
    }

    public void detelettimetable() {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        sqLiteDatabase.execSQL("delete from 'timetable_tabl'");
    }

    public Cursor fetchMasterTable() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = "select * from " + dbHelper.masterTable;
        Cursor cursor = db.rawQuery(sql, null);
        return cursor;
    }

    public Cursor fetchMasterTable(String staffclientid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.masterTable + " where " + dbHelper.Staff_ClientId + " = ?", new String[]{staffclientid});
        return cursor1;
    }


//    public Cursor fetchParentIdforstudentid(String childid)
//    {
//        SQLiteDatabase db = dbHelper.getReadableDatabase();
//
//        Cursor cursor1 = db.rawQuery("select "+"ParentId"+" from "+dbHelper.masterTable+" where "+dbHelper.studentid+" = ?", new String []{childid});
//        return cursor1;
//    }

    public Cursor fetchstudentExistence(String staffclientid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.masterTable + " where " + dbHelper.Staff_ClientId + " = ?", new String[]{staffclientid});
        return cursor1;
    }

    public long insertGiveHomeworkDetails(String Branchid, String ClassNm, String CreateDate, String DayID_FK, String FinalDayTimeTable_Id, String Fk_gpclsid, String Period_Name, String ScheduleDate, String TeacherID_FK, String SessionID, String SubjectID_FK, String SubjectMasterID, String SubjectName, String TimeTableMID_FK, String clsstrucid, String givehomeworkempname, String id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.Branchid, Branchid);
        cv.put(dbHelper.ClassNm, ClassNm);
        cv.put(dbHelper.CreateDate, CreateDate);
        cv.put(dbHelper.DayID_FK, DayID_FK);
        cv.put(dbHelper.FinalDayTimeTable_Id, FinalDayTimeTable_Id);
        cv.put(dbHelper.Fk_gpclsid, Fk_gpclsid);
        cv.put(dbHelper.Period_Name, Period_Name);
        cv.put(dbHelper.ScheduleDate, ScheduleDate);
        cv.put(dbHelper.SessionID, SessionID);
        cv.put(dbHelper.SubjectID_FK, SubjectID_FK);
        cv.put(dbHelper.SubjectMasterID, SubjectMasterID);
        cv.put(dbHelper.SubjectName, SubjectName);
        cv.put(dbHelper.TeacherID_FK, TeacherID_FK);
        cv.put(dbHelper.TimeTableMID_FK, TimeTableMID_FK);
        cv.put(dbHelper.clsstrucid, clsstrucid);
        cv.put(dbHelper.givehomeworkempname, givehomeworkempname);
        cv.put(dbHelper.id, id);
        Log.e("12insert", "12insert");
        return db.insertWithOnConflict(dbHelper.givehomeTable, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
    }

    //
    public Cursor fetchGiveHomework(String DateofHomeworkgiven) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.givehomeTable + " where " + dbHelper.ScheduleDate + " = ?", new String[]{DateofHomeworkgiven});
        return cursor1;

    }

    public void deletegivehomework(String Date) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.execSQL("delete from " + dbHelper.givehomeTable + " where " + dbHelper.ScheduleDate + " = ?", new String[]{Date});
    }

    public Cursor fetchparticularHomework(String getempid, String subjectid, String classid, String finaldaytimetableid) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor1 = db.rawQuery("select " + "*" + " from " + dbHelper.givehomeTable + " where " + dbHelper.TeacherID_FK + " = ? and " + dbHelper.SubjectMasterID + "=? and " + dbHelper.clsstrucid + "=? and " + dbHelper.FinalDayTimeTable_Id + "=?", new String[]{getempid, subjectid, classid, finaldaytimetableid});
        return cursor1;
    }


    public long insertchat(String branchid, String ChatQry, String ChatTopicId_Fk, String ChatTopicName, String Chat_ResQryText, String ClassNm, String Createdate, String Flg, String FwdEmpId, String LoopValue, String OtherTopicName, String QryCreatedbyId, String QryNo, String QryRespBy, String QryStatus, String RespDate, String RespFlg, String RespQryno, String TopicName, String Topicqryid, String empname, String errormessage, String studentname) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.branchid, branchid);
        cv.put(dbHelper.ChatQry, ChatQry);
        cv.put(dbHelper.ChatTopicId_Fk, ChatTopicId_Fk);
        cv.put(dbHelper.ChatTopicName, ChatTopicName);
        cv.put(dbHelper.Chat_ResQryText, Chat_ResQryText);
        cv.put(dbHelper.classnm, ClassNm);
        cv.put(dbHelper.Createdate, Createdate);
        cv.put(dbHelper.Flg, Flg);
        cv.put(dbHelper.FwdEmpId, FwdEmpId);
        cv.put(dbHelper.LoopValue, LoopValue);
        cv.put(dbHelper.OtherTopicName, OtherTopicName);
        cv.put(dbHelper.QryCreatedbyId, QryCreatedbyId);
        cv.put(dbHelper.QryNo, QryNo);
        cv.put(dbHelper.QryRespBy, QryRespBy);
        cv.put(dbHelper.QryStatus, QryStatus);
        cv.put(dbHelper.RespDate, RespDate);
        cv.put(dbHelper.RespFlg, RespFlg);
        cv.put(dbHelper.RespQryno, RespQryno);
        cv.put(dbHelper.TopicName, TopicName);
        cv.put(dbHelper.Topicqryid, Topicqryid);
        cv.put(dbHelper.empnam, empname);
        cv.put(dbHelper.errormessag, errormessage);
        cv.put(dbHelper.studentname, studentname);

        return sqLiteDatabase.insertWithOnConflict(dbHelper.chattable, null, cv, sqLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor featchqueryno() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct QryNo from 'chattable'", null);
        return cursor;
    }

    public Cursor featchquerynodetails(String queryno) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct ChatTopicName,QryCreatedbyId from 'chattable' where QryNo='" + queryno + "'", null);
        return cursor;
    }

    public Cursor featchquerynoallChatQry(String queryno) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct ChatQry from 'chattable' where QryNo='" + queryno + "'", null);
        return cursor;
    }

    public Cursor featchquerynoallChat_ResQryText(String queryno) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct Chat_ResQryText from 'chattable' where QryNo='" + queryno + "'", null);
        return cursor;
    }

    public Cursor featchquerynoallChat_ResQryTIme(String queryno) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct Chat_ResQryText from 'chattable' where QryNo='" + queryno + "'", null);
        return cursor;
    }

    public Cursor featchquerynoallChatQryTextTime(String queryno) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct Chat_ResQryText from 'chattable' where QryNo='" + queryno + "'", null);
        return cursor;
    }

    //*****************************************************************************************************8******
    public long insertprofileData(String imageString) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(dbHelper.imageString, imageString);
        return sqLiteDatabase.insertWithOnConflict(dbHelper.userprofileTable, null, cv, sqLiteDatabase.CONFLICT_REPLACE);
    }

    public Cursor fetchprofileData() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select distinct QryNo from 'chattable'", null);
        return cursor;
    }
}

//***************************************************************************************************************************************************************************************************************************************
class MySqliteDBHelper extends SQLiteOpenHelper {
    private Context context;

    static final int db_version = 27; // strictly recommended to must not be changed18

    static final String db_name = "shikshateach.com";// strictly recommended to must not be changed


    static final String basic_school_registration_tbl = "basic_school_registration_tbl";
    static final String basic_school_registration_tbl_id_auto = "basic_school_registration_tbl_id_auto";
    static final String institution_name = "institution_name";
    static final String institution_logo = "institution_name";

    static final String create_table_basic_school_info = "CREATE TABLE IF NOT EXISTS " + basic_school_registration_tbl + "(" + basic_school_registration_tbl_id_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + institution_logo + " BLOB NOT NULL, " + institution_name + " VARCHAR);";
    static final String drop_table_basicinstitutioninfo = "DROP TABLE IF EXISTS " + basic_school_registration_tbl;

//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    static final String childInfo_tbl = "childInfo_tbl";
    static final String childInfoTbl_id_auto = "childInfoTbl_id_auto";
    static final String childid = "childid";
    static final String childname = "childname";

    static final String create_table_child_info = "CREATE TABLE IF NOT EXISTS " + childInfo_tbl + "(" + childInfoTbl_id_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + childid + " VARCHAR, " + childname + " VARCHAR);";
    static final String drop_table_childinfoTable = "DROP TABLE IF EXISTS " + childInfo_tbl;


//-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    static final String childAttendanceTable = "childAttendanceTable";
    static final String childattendanceTbl_id_auto = "childcontentTbl_id_auto";
    static final String PunchDate = "PunchDate";
    static final String PunchTime = "PunchTime";
    static final String UserRefId = "UserRefId";
    static final String In_OutStatus = "In_OutStatus";
    static final String studentidattendance = "studentidattendance";
    static final String AttStatus = "AttStatus";
    static final String Todate = "Todate";
    static final String create_table_child_attendance = "CREATE TABLE IF NOT EXISTS " + childAttendanceTable + "(" + childattendanceTbl_id_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + PunchDate + " VARCHAR, " + PunchTime + " VARCHAR, " + UserRefId + " VARCHAR, " + In_OutStatus + " VARCHAR, " + studentidattendance + " VARCHAR, " + AttStatus + " VARCHAR, " + Todate + " VARCHAR );";
    static final String drop_table_child_attendance = "DROP TABLE IF EXISTS " + childAttendanceTable;
//_____________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________________

    static final String masterTable = "masterTable";
    static final String id_master_Table_auto = "id_master_Table_auto";

    static final String BranchId = "BranchId";
    static final String Logo = "Logo";
    static final String SchoolName = "SchoolName";
    static final String SchoolNameShort = "SchoolNameShort";
    static final String Staff_ClientId = "Staff_ClientId";
    static final String Staff_Id_Fk = "Staff_Id_Fk";
    static final String Staff_Name = "Staff_Name";
    static final String Teacherdob = "Teacherdob";
    static final String doj = "doj";
    static final String emp_id = "emp_id";
    static final String empname = "empname";
    static final String mobno = "mobno";
    static final String fatername = "fatername";
    static final String Qualification = "Qualification";
    static final String pre_add = "pre_add";
    static final String parm_add = "parm_add";
    static final String bloodgrp = "bloodgrp";
    static final String FlagCT = "FlagCT";
    static final String profile_img = "profile_img";


    static final String create_table_master_Table = "CREATE TABLE IF NOT EXISTS " + masterTable + "(" + id_master_Table_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + BranchId + " VARCHAR, " + Logo + " VARCHAR, " + SchoolName + " VARCHAR, " + SchoolNameShort + " VARCHAR, " + Staff_ClientId + " VARCHAR, "
            + Staff_Id_Fk + " VARCHAR, " + Staff_Name + " VARCHAR, " + Teacherdob + " VARCHAR, " + doj + " VARCHAR, " + emp_id + " VARCHAR, " + empname + " VARCHAR, " + mobno + " VARCHAR, " + fatername + " VARCHAR, " + Qualification + " VARCHAR, " + pre_add + " VARCHAR, " + parm_add + " VARCHAR, " + bloodgrp + " VARCHAR, " + profile_img + " VARCHAR, " + FlagCT + " VARCHAR);";
    static final String drop_table_master_Table = "DROP TABLE IF EXISTS " + masterTable;


    // --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    static final String givehomeTable = "givehomeTable";
    static final String id_givehomework_auto = "id_givehomework_auto";
    static final String Branchid = "Branchid";
    static final String ClassNm = "ClassNm";
    static final String CreateDate = "CreateDate";
    static final String DayID_FK = "DayID_FK";
    static final String FinalDayTimeTable_Id = "FinalDayTimeTable_Id";
    static final String Fk_gpclsid = "Fk_gpclsid";
    static final String Period_Name = "Period_Name";
    static final String ScheduleDate = "ScheduleDate";
    static final String SessionID = "SessionID";
    static final String SubjectID_FK = "SubjectID_FK";
    static final String SubjectMasterID = "SubjectMasterID";
    static final String SubjectName = "SubjectName";
    static final String TeacherID_FK = "TeacherID_FK";
    static final String TimeTableMID_FK = "TimeTableMID_FK";
    static final String clsstrucid = "clsstrucid";
    static final String givehomeworkempname = "givehomeworkempname";
    static final String id = "id";

    static final String create_table_Givehomework_Table = "CREATE TABLE IF NOT EXISTS " + givehomeTable + "(" + id_givehomework_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + Branchid + " VARCHAR, " + ClassNm + " VARCHAR, " + CreateDate + " VARCHAR, "
            + DayID_FK + " VARCHAR, " + FinalDayTimeTable_Id + " VARCHAR, "
            + Fk_gpclsid + " VARCHAR, " + Period_Name + " VARCHAR, " + ScheduleDate + " VARCHAR, " + SessionID + " VARCHAR, " + SubjectID_FK + " VARCHAR, " + SubjectMasterID + " VARCHAR, " + SubjectName + " VARCHAR, " + TeacherID_FK +
            " VARCHAR, " + TimeTableMID_FK + " VARCHAR, " + clsstrucid + " VARCHAR, " + givehomeworkempname + " VARCHAR, " + id + " VARCHAR);";

    static final String Delete_table_Givehomework_Table = "DROP TABLE IF EXISTS " + masterTable;


//_____________________________________________________________________________________________________________________________________________________________________________________________________________________________

    static final String noticeTable = "noticeTable";
    static final String id_notice_Table_auto = "id_notice_Table_auto";
    static final String SmsContent = "SmsContent";
    static final String msgdatetime = "msgdatetime";
    static final String childrenid = "childrenid";


    //    static final String create_table_child_notice = "CREATE TABLE IF NOT EXISTS "+noticeTable+"("+id_notice_Table_auto+" INTEGER PRIMARY KEY AUTOINCREMENT, "+SmsContent+" VARCHAR, "+msgdatetime+" VARCHAR, "+"smsid"+" INTEGER);";
    static final String create_table_child_notice = "CREATE TABLE IF NOT EXISTS " + noticeTable + "(" + id_notice_Table_auto + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SmsContent + " VARCHAR, " + msgdatetime + " VARCHAR, " + "smsid" + " INTEGER, " + childrenid + " VARCHAR);";

    static final String drop_table_child_notice = "DROP TABLE IF EXISTS " + noticeTable;
    /**************************************************************/

    static final String permissiontable = "permissiontable";
    static final String AppModuleID = "AppModuleID";
    static final String AppsublnksID = "AppsublnksID";
    static final String ModuleImg = "ModuleImg";
    static final String ModuleName = "ModuleName";
    static final String WhichApp = "WhichApp";
    static final String errormessage = "errormessage";
    static final String subPgname = "subPgname";
    static final String flagCity = "flagCity";
    static final String create_table_parmission = "CREATE TABLE'" + permissiontable + "'('" + AppModuleID + "' VARCHAR,'" + AppsublnksID + "' VARCHAR,'" + ModuleImg + "' VARCHAR,'" + ModuleName + "' VARCHAR,'" + WhichApp + "' VARCHAR,'" + errormessage + "' VARCHAR,'" + subPgname + "' VARCHAR,'" + flagCity + "' VARCHAR)";

    /**************************************************************/
    /****************** Chat Table ****************************/

    static final String chattable = "chattable";
    static final String branchid = "branchid";
    static final String ChatQry = "ChatQry";
    static final String ChatTopicId_Fk = "ChatTopicId_Fk";
    static final String ChatTopicName = "ChatTopicName";
    static final String Chat_ResQryText = "Chat_ResQryText";
    static final String classnm = "classnm";
    static final String Createdate = "Createdate";
    static final String Flg = "Flg";
    static final String FwdEmpId = "FwdEmpId";
    static final String LoopValue = "LoopValue";
    static final String OtherTopicName = "OtherTopicName";
    static final String QryCreatedbyId = "QryCreatedbyId";
    static final String QryNo = "QryNo";
    static final String QryRespBy = "QryRespBy";
    static final String QryStatus = "QryStatus";
    static final String RespDate = "RespDate";
    static final String RespFlg = "RespFlg";
    static final String RespQryno = "RespQryno";
    static final String TopicName = "TopicName";
    static final String Topicqryid = "Topicqryid";
    static final String empnam = "empnam";
    static final String errormessag = "errormessag";
    static final String studentname = "studentname";

    static final String create_table_chat = "CREATE TABLE '" + chattable + "'('" + branchid + "' VARCHAR,'" + ChatQry + "' VARCHAR,'" + ChatTopicId_Fk + "' VARCHAR,'" + ChatTopicName + "' VARCHAR,'" + Chat_ResQryText + "' VARCHAR,'" + classnm + "' VARCHAR,'" + Createdate + "' VARCHAR," +
            "'" + Flg + "' VARCHAR," +
            "'" + FwdEmpId + "' VARCHAR," +
            "'" + LoopValue + "' VARCHAR," +
            "'" + OtherTopicName + "' VARCHAR," +
            "'" + QryCreatedbyId + "' VARCHAR," +
            "'" + QryNo + "' VARCHAR," +
            "'" + QryRespBy + "' VARCHAR," +
            "'" + QryStatus + "' VARCHAR," +
            "'" + RespDate + "' VARCHAR," +
            "'" + RespFlg + "' VARCHAR," +
            "'" + RespQryno + "' VARCHAR," +
            "'" + TopicName + "' VARCHAR," +
            "'" + Topicqryid + "' VARCHAR," +
            "'" + empnam + "' VARCHAR," +
            "'" + errormessag + "' VARCHAR," +
            "'" + studentname + "' VARCHAR)";

    /**********************************************/
    static final String timetable_tabl = "timetable_tabl";
    static final String classname = "classname";
    static final String clsstruci = "clsstruci";
    static final String SubjectID = "SubjectID";
    static final String TeacherID = "TeacherID";

    static final String create_timetable_tab = "CREATE TABLE IF NOT EXISTS '" + timetable_tabl + "'('" + classname + "' VARCHAR,'" + clsstruci + "' VARCHAR,'" + SubjectID + "' VARCHAR,'" + TeacherID + "' VARCHAR)";


    /**********************************************************/

    static final String attendancetTable = "attendancetTable";
    static final String ClsStruId = "ClsStruId";
    static final String StuId = "StuId";
    static final String SubjectId = "SubjectId";
    static final String Attan_Status = "Attan_Status";
    static final String EmpId = "EmpId";
    static final String Attan_date = "Attan_date";
    static final String Attan_time = "Attan_time";
    static final String Attan_brid = "Attan_brid";
    static final String Attan_Sessionid = "Attan_Sessionid";
    static final String Att_Description = "Att_Description";

    static final String create_table_attendence = "CREATE TABLE '" + attendancetTable + "' ('" + ClsStruId + "' VARCHAR,'" + StuId + "' VARCHAR, '" + SubjectId + "' VARCHAR,'" + Attan_Status + "' VARCHAR, '" + EmpId + "' VARCHAR,'" + Attan_date + "' VARCHAR,'" + Attan_time + "' VARCHAR, '" + Attan_brid + "' VARCHAR,'" + Attan_Sessionid + "' VARCHAR,'" + Att_Description + "' VARCHAR)";

    /**********************************************************/


    static final String userprofileTable = "userprofileTable";
    static final String imageString = "imageString";

    static final String create_table_profile = "CREATE TABLE IF NOT EXISTS'" + userprofileTable + "'('" + imageString + "'VARCHAR)";
    static final String drop_table_user_profile = "DROP TABLE IF EXISTS " + userprofileTable;

    /*********************************************************************/
    public MySqliteDBHelper(Context context) {
        super(context, db_name, null, db_version);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
//            db.execSQL(create_table_basic_school_info);
//            db.execSQL(create_table_child_info);
//            db.execSQL(create_table_child_content);
            Log.e("tableCreation", create_table_master_Table);
            db.execSQL(create_table_master_Table);
            db.execSQL(create_table_child_attendance);
            db.execSQL(create_table_Givehomework_Table);
            db.execSQL(create_table_child_notice);
            db.execSQL(create_table_parmission);
            db.execSQL(create_table_attendence);
            db.execSQL(create_timetable_tab);
            db.execSQL(create_table_chat);
            db.execSQL(create_table_profile);
            Log.e("giveHomeworkTable", create_table_Givehomework_Table);
//            db.execSQL(create_table_child_notice);
//            db.execSQL(create_table_child_homeWork);
//            Log.e("tableCreation1",create_table_child_homeWork);

//            Toast.makeText(context, "tables created successfully", Toast.LENGTH_LONG).show();
        } catch (SQLException e) {
            e.printStackTrace();
//            Toast.makeText(context,"un-successful:" + e.getMessage(), Toast.LENGTH_LONG).show();
            Log.e("un-successful:", e.getMessage());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
//            db.execSQL(drop_table_basicinstitutioninfo);
//            db.execSQL(drop_table_childinfoTable);
//            db.execSQL(drop_table_childcontentTable);
            db.execSQL(drop_table_master_Table);
            db.execSQL(drop_table_child_attendance);
            db.execSQL(Delete_table_Givehomework_Table);
            db.execSQL(drop_table_child_notice);
            db.execSQL(drop_table_user_profile);
//            db.execSQL(drop_table_child_notice);
//            db.execSQL(drop_table_child_homeWork);
            onCreate(db);
//            Toast.makeText(context,"onUpgrade is called..", Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.getMessage();
        }
    }
}