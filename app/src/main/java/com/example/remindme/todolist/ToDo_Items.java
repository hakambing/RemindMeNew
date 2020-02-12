package com.example.remindme.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.remindme.R;

import java.util.ArrayList;

public class ToDo_Items extends ArrayAdapter<ToDo> {

    private LayoutInflater mInflater;
    private ArrayList<ToDo> toDos;
    private int mViewResourceId;

    public ToDo_Items(Context context, int textViewResourceId, ArrayList<ToDo> toDos){
        super(context, textViewResourceId, toDos);
        this.toDos = toDos;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }


    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourceId,null);

        ToDo toDo = toDos.get(position);

        if(toDo != null){
            TextView title = (TextView) convertView.findViewById(R.id.todoTitle);


            if (title != null){
                title.setText((toDo.getTitle()));
            }


        }
        return convertView;
    }
}

