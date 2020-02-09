package com.example.remindme.todolist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    public Cursor getItemID(String title){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT _ID FROM " + TABLE_NAME + " WHERE TITLE = '" + title + "' ";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    //Update
    public void updateData(String titleNew, int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE salakau SET TITLE = '" + titleNew + "' WHERE _ID = '" + id + "'";
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





}
