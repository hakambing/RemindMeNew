package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.remindme.data.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    DatabaseHelper myDb;
    ArrayList<Reminder> reminderList;
    ListView reminderListView;
    Reminder reminder;
    ProgressDialog prgDialog;
    RemindCursorAdapter mCursorAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        reminderList = new ArrayList<>();
        Cursor data = myDb.getAllData();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(MainActivity.this,"There is nothing",Toast.LENGTH_LONG).show();
        }
        else{
            while(data.moveToNext()){
                reminder = new Reminder(data.getString(1),data.getString(2),data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getString(7));
                reminderList.add(reminder);

            }
            Alarm_Items adapter = new Alarm_Items(this,R.layout.alarm_items,reminderList);
            reminderListView = (ListView) findViewById(R.id.reminderList);
            reminderListView.setAdapter(adapter);
        }











        //Floating action button
        FloatingActionButton fab1 = findViewById(R.id.addReminderFab);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, NewReminder.class);
                startActivity(i);

            }
        });

        FloatingActionButton fab2 = findViewById(R.id.todoListFab);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(MainActivity.this, ToDoList.class);
                startActivity(j);

            }
        });



    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }
}

