package com.example.remindme.todolist;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remindme.MainActivity;
import com.example.remindme.R;

public class NewToDo extends AppCompatActivity {
    String msg = "Android : ";
    ToDoDatabaseHelper myDb;

    EditText mTitleText;
    private String mTitle;
    Button addButton;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_todo);

        //initailise values
        mTitleText = (EditText) findViewById(R.id.addTitle);
        addButton = (Button) findViewById(R.id.addToDo);



        //Title (ITS WORKING)
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        AddData();


    }

    public void AddData(){
        addButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isInserted = myDb.insertData(mTitle);
                        if (isInserted==true){
                            Toast.makeText(NewToDo.this, "Item saved!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(NewToDo.this, ToDoList.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(NewToDo.this,"Error saving item.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );
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


