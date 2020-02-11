package com.example.remindme;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.remindme.reminder.Alarm_Items;
import com.example.remindme.reminder.DatabaseHelper;
import com.example.remindme.reminder.EditReminder;
import com.example.remindme.reminder.NewReminder;
import com.example.remindme.reminder.Reminder;
import com.example.remindme.todolist.ToDoList;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    String msg = "Android : ";

    DatabaseHelper myDb;

    ArrayList<Reminder> reminderList;
    ListView reminderListView;
    Reminder reminder;
    ProgressDialog prgDialog;

    TextView count;

    Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);


        reminderList = new ArrayList<>();
        final Cursor data = myDb.getAllData();
        int numRows = data.getCount();
        if(numRows == 0){
            Toast.makeText(MainActivity.this,"No Reminders",Toast.LENGTH_LONG).show();
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


       //click ListView
        reminderListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String title = reminderList.get(i).getTitle();
                String date = reminderList.get(i).getDate();
                String time = reminderList.get(i).getTime();
                String repeat = reminderList.get(i).getRepeat();
                String repeat_no = reminderList.get(i).getRepeat_No();
                String repeat_type = reminderList.get(i).getRepeat_Type();
                String sound = reminderList.get(i).getSound();
                Log.d(msg, "onItemClick: You Clicked on "+ title);



                Cursor data = myDb.getItemID(title,date,time,repeat,repeat_no,repeat_type,sound);
                int itemID = -1;
                while(data.moveToNext()){
                    itemID = data.getInt(0);
                }
                if(itemID > -1){
                    Intent editScreenIntent = new Intent(MainActivity.this, EditReminder.class);
                    editScreenIntent.putExtra("id",itemID);
                    editScreenIntent.putExtra("title",title);
                    editScreenIntent.putExtra("date",date);
                    editScreenIntent.putExtra("time",time);
                    editScreenIntent.putExtra("repeat",repeat);
                    editScreenIntent.putExtra("repeat_no",repeat_no);
                    editScreenIntent.putExtra("repeat_type",repeat_type);
                    editScreenIntent.putExtra("sound",sound);
                    startActivity(editScreenIntent);


                }
                else{
                    Toast.makeText(MainActivity.this,time,Toast.LENGTH_LONG).show();

                }

            }
        });

        count = (TextView) findViewById(R.id.textViewRemind);
        int rows = myDb.getCount();
        count.setText(rows + " reminders");










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


        //toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.about:
                // Do Something
                Toast.makeText(getApplicationContext(), "hello ameen",
                        Toast.LENGTH_SHORT).show();
                // Add the following code
                android.app.AlertDialog alertDialog = new android.app.AlertDialog.Builder(MainActivity.this).create();
                alertDialog.setTitle("About");
                alertDialog.setMessage("RemindMe is a reminder application that also has a to-do list. \n\n\n\nVersion 1.0 \n\nDeveloped by: Ryan Ong, Manfred Koh, and\nAbdul Hakam");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                // End of adding code

                return true;
        }
        return false;
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


    /** Called when the activity is about to become visible. */
    @Override
    protected void onStart() {
        super.onStart();
        Log.d(msg, "The onStart() event");
    }

    /** Called when the activity has become visible. */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(msg, "The onResume() event");
    }

    /** Called when another activity is taking focus. */
    @Override
    protected void onPause() {
        super.onPause();
        Log.d(msg, "The onPause() event");
    }

    /** Called when the activity is no longer visible. */
    @Override
    protected void onStop() {
        super.onStop();
        Log.d(msg, "The onStop() event");
    }

    /** Called just before the activity is destroyed. */
    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(msg, "The onDestroy() event");
    }

}

