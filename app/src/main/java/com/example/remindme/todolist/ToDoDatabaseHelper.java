package com.example.remindme.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import androidx.annotation.Nullable;

import com.example.remindme.Reminder;

import java.sql.Blob;

public class ToDoDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "todolistdatabase.db";
    public static final String TABLE_NAME = "todolist";
    public static final String COL_1 = "_ID";
    public static final String COL_2 = "TITLE";

    public ToDoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("create table " + TABLE_NAME +" (_ID INTEGER PRIMARY KEY AUTOINCREMENT,TITLE TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public boolean insertData(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,title);

        long result = db.insert(TABLE_NAME,null, contentValues);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }

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
