package com.example.remindme.todolist;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.remindme.MainActivity;
import com.example.remindme.R;
import com.example.remindme.reminder.Alarm_Items;
import com.example.remindme.reminder.EditReminder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ToDoList extends AppCompatActivity implements View.OnClickListener {

    ToDoDatabaseHelper myDb;
    ArrayList<ToDo> todoList;
    ListView toDoListView;
    ToDo toDo;

    @Override
    public void onClick(View view) {


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.todo_list);

        myDb = new ToDoDatabaseHelper(this);

        todoList = new ArrayList<>();
        Cursor data = myDb.getAllData();
        int numRows = data.getCount();
        if(numRows==0){
            Toast.makeText(ToDoList.this,"There is nothing",Toast.LENGTH_LONG).show();
        }else{
            while(data.moveToNext()){
                toDo = new ToDo(data.getString(1));
                todoList.add(toDo);
            }
            ToDo_Items adapter = new ToDo_Items(this,R.layout.todo_items,todoList);
            toDoListView = (ListView) findViewById(R.id.todoList);
            toDoListView.setAdapter(adapter);

        }

try {
    toDoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String title = todoList.get(i).getTitle();

            Cursor data = myDb.getItemID(title);
            int itemID = -1;
            while (data.moveToNext()) {
                itemID = data.getInt(0);
            }
            if (itemID > -1) {
                Intent editScreenIntent = new Intent(ToDoList.this, EditToDo.class);
                editScreenIntent.putExtra("id", itemID);
                editScreenIntent.putExtra("title", title);
                startActivity(editScreenIntent);


            } else {
                Toast.makeText(ToDoList.this, "Hi", Toast.LENGTH_LONG).show();

            }
        }
    });
}catch (NullPointerException ignored){
    Toast.makeText(ToDoList.this, "No To Do list", Toast.LENGTH_LONG).show();

}

        FloatingActionButton fab1 = findViewById(R.id.addListFab);

        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ToDoList.this, NewToDo.class);
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
