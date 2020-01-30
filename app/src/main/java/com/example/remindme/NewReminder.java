package com.example.remindme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class NewReminder extends AppCompatActivity implements View.OnClickListener {
    RelativeLayout date;
    RelativeLayout dateShow;

    RelativeLayout time;
    RelativeLayout timeShow;

    Switch switchRepeat;
    RelativeLayout repeatShow;

    Button cancelBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);
        date = findViewById(R.id.date);
        dateShow = findViewById(R.id.dateShow);
        date.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @Override
            public void onClick(View v){
                visible = !visible;
                dateShow.setVisibility(visible ? View.VISIBLE: View.GONE);
            }

        });

        time = findViewById(R.id.time);
        timeShow = findViewById(R.id.timeShow);
        time.setOnClickListener(new View.OnClickListener(){
            boolean visible;
            @Override
            public void onClick(View v){
                visible = !visible;
                timeShow.setVisibility(visible ? View.VISIBLE: View.GONE);
            }

        });

        switchRepeat = findViewById(R.id.repeat_switch);
        repeatShow = findViewById(R.id.repeatShow);
        switchRepeat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (switchRepeat.isChecked()){
                    repeatShow.setVisibility(View.VISIBLE);
                }else{
                    repeatShow.setVisibility(View.GONE);
                }
            }
        });

        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(NewReminder.this, MainActivity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public void onClick(View v) {

    }
}
