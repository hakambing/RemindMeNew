package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.remindme.data.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {


    DatabaseHelper myDb;
    ListView reminderListView;
    ProgressDialog prgDialog;

    private static final int VEHICLE_LOADER = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ListView
        ListView listView = (ListView) findViewById(R.id.reminderList);
        myDb = new DatabaseHelper(this);

        ArrayList<String> reminderList = new ArrayList<>();
        Cursor data = myDb.getAllData();

        if(data.getCount() == 0){
            Toast.makeText(MainActivity.this,"No Reminders",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                reminderList.add(data.getString(1));
                reminderList.add(data.getString(2));
                reminderList.add(data.getString(3));
                reminderList.add(data.getString(5));
                reminderList.add(data.getString(6));
                reminderList.add(data.getString(7));
                /*ListAdapter listAdapter = new ArrayAdapter<>(this.an)*/

            }
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

        myDb = new DatabaseHelper(this);


    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
    }
}

