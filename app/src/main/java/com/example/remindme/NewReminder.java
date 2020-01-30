package com.example.remindme;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import java.util.Calendar;

public class NewReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {
    private static final int EXISTING_VEHICLE_LOADER = 0;

    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private Calendar mCalendar;
    RelativeLayout date;
    RelativeLayout dateShow;

    RelativeLayout time;
    RelativeLayout timeShow;

    Switch switchRepeat;
    RelativeLayout repeatNum;
    RelativeLayout repeatType;
    RelativeLayout repeatNumShow;
    RelativeLayout repeatTypeShow;

    Button cancelBtn;

    ImageView mImageView;
    RelativeLayout mChooseBtn;
    ImageView deleteImage;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

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
        repeatNum = findViewById(R.id.RepeatNo);
        repeatType = findViewById(R.id.RepeatType);
        repeatNumShow = findViewById(R.id.repeatNoShow);
        repeatTypeShow = findViewById(R.id.repeatTypeShow);
        switchRepeat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (switchRepeat.isChecked()){
                    repeatNum.setVisibility(View.VISIBLE);
                    repeatType.setVisibility(View.VISIBLE);
                }else{
                    repeatNum.setVisibility(View.GONE);
                    repeatType.setVisibility(View.GONE);
                }
            }
        });
        repeatNum.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View view) {
                visible = !visible;
                repeatNumShow.setVisibility(visible ? View.VISIBLE: View.GONE);
            }
        });
        repeatType.setOnClickListener(new View.OnClickListener() {
            boolean visible;
            @Override
            public void onClick(View view) {
                visible = !visible;
                repeatTypeShow.setVisibility(visible ? View.VISIBLE: View.GONE);
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

        //image selector
        mImageView = findViewById(R.id.image_view);
        mChooseBtn = findViewById(R.id.image);
        deleteImage = findViewById(R.id.deleteImage);


        mChooseBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                        //permission not granted, request it
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        //show popup for runtime permission
                        requestPermissions(permissions, PERMISSION_CODE);
                    }
                    else {
                        //permission already granted
                        pickImageFromGallery();


                    }
                }
                else{
                    //system os is less than marshmallow
                    pickImageFromGallery();

                }
            }

        });

        deleteImage.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                mImageView.setImageResource(0);
                mImageView.setVisibility(View.GONE);
                deleteImage.setVisibility(View.GONE);
            }
        });

    }

    private void pickImageFromGallery(){
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case PERMISSION_CODE:{
                if(grantResults.length >0 && grantResults[0]==
                PackageManager.PERMISSION_GRANTED){
                    pickImageFromGallery();
                }
                else {
                    Toast.makeText(this,"Permission denied!",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    boolean visible;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            mImageView.setImageURI(data.getData());
            visible = !visible;
            mImageView.setVisibility(View.VISIBLE);
            deleteImage.setVisibility(View.VISIBLE);
        }
    }



    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }

    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

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
