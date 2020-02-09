package com.example.remindme.reminder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "remindmesalakau.db";
    public static final String TABLE_NAME = "salakau";
    public static final String COL_1 = "_ID";
    public static final String COL_2 = "TITLE";
    public static final String COL_3 = "DATE";
    public static final String COL_4 = "TIME";
    public static final String COL_5  = "IMAGE";
    public static final String COL_6 = "REPEAT";
    public static final String COL_7 = "REPEAT_NO";
    public static final String COL_8 = "REPEAT_TYPE";
    public static final String COL_9 = "SOUND";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

      }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (_ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT, DATE TEXT, TIME TEXT,REPEAT TEXT, REPEAT_NO TEXT, REPEAT_TYPE TEXT, SOUND TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

   public boolean insertData(String title, String date, String time,String repeat, String repeat_no, String repeat_type, String sound){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,title);
        contentValues.put(COL_3,date);
        contentValues.put(COL_4,time);
        contentValues.put(COL_6,repeat);
        contentValues.put(COL_7,repeat_no);
        contentValues.put(COL_8,repeat_type);
        contentValues.put(COL_9,sound);
        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }

    }

    public Cursor getItemID(String title, String date, String time,String repeat, String repeat_no, String repeat_type, String sound){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT _ID FROM " + TABLE_NAME + " WHERE TITLE = '" + title + "' " +
                "AND DATE = '" + date + "' " +
                "AND TIME = '" + time + "' " +
                "AND REPEAT = '" + repeat + "' " +
                "AND REPEAT_NO = '" + repeat_no + "' " +
                "AND REPEAT_TYPE = '" + repeat_type + "' " +
                "AND SOUND = '" + sound + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Update
    public void updateData(String titleNew, String dateNew, String timeNew,String repeatNew, String repeat_noNew, String repeat_typeNew, String soundNew, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE salakau SET" +
                " TITLE = '" + titleNew + "'," +
                " DATE = '" + dateNew + "'," +
                " TIME = '" + timeNew + "'," +
                " REPEAT = '" + repeatNew + "'," +
                " REPEAT_NO = '" + repeat_noNew + "'," +
                " REPEAT_TYPE = '" + repeat_typeNew + "'," +
                " SOUND = '" + soundNew + "'" +
                " WHERE _ID = '" + id + "'";
        db.execSQL(query);
    }

    //Delete
    public void deleteData(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM salakau WHERE _ID = '" + id + "'";
        db.execSQL(query);
    }



    public Cursor getAllData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+TABLE_NAME,null);
        return res;
    }




    /*@Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Create a String that contains the SQL statement to create the reminder table
        String SQL_CREATE_ALARM_TABLE = "CREATE TABLE " + AlarmReminderContract.AlarmReminderEntry.TABLE_NAME + " ("
                + AlarmReminderContract.AlarmReminderEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_TITLE + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_DATE + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_TIME + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE + " TEXT NOT NULL, "
                + AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE + " TEXT NOT NULL " + " );";

        // Execute the SQL statement
        sqLiteDatabase.execSQL(SQL_CREATE_ALARM_TABLE);
    }*/


}
