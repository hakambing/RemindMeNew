package com.example.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ToDoList extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View view) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);


        FloatingActionButton fab1 = findViewById(R.id.addListFab);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToDoList.this, MainActivity.class);
                startActivity(i);

            }
        });

        FloatingActionButton fab2 = findViewById(R.id.reminderFab);

        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent j = new Intent(ToDoList.this, MainActivity.class);
                startActivity(j);

            }
        });
    }
}
