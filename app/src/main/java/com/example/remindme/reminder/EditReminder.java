package com.example.remindme.reminder;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

import com.example.remindme.MainActivity;
import com.example.remindme.R;

import java.util.Calendar;

public class EditReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {
    private EditText mTitleText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;

    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mSound;
    private int selectedID;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    RelativeLayout date;
    RelativeLayout dateShow;
    DatePicker simpleDatePicker;
    Button dateSubmit;

    RelativeLayout time;
    RelativeLayout timeShow;
    TimePicker simpleTimePicker;
    Button timeSubmit;

    Switch switchRepeat;
    RelativeLayout repeatNum;
    RelativeLayout repeatType;
    RelativeLayout repeatNumShow;
    RelativeLayout repeatTypeShow;
    EditText repeatNoEditText;
    Button repeatNoSubmit;

    TextView rptMinute;
    TextView rptHour;
    TextView rptDay;
    TextView rptWeek;
    TextView rptMonth;

    Button cancelBtn;
    Button deleteBtn;
    Button saveBtn;


    ImageView mImageView;
    RelativeLayout mChooseBtn;
    Button deleteImage;

    Switch switchSound;

    DatabaseHelper myDb;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_reminder);
        saveBtn = (Button) findViewById(R.id.setBtn);
        deleteBtn = (Button) findViewById(R.id.deleteBtn);

        //initialise values
        mTitleText = (EditText) findViewById(R.id.editText);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mRepeatText = (TextView) findViewById(R.id.set_repeat);
        mRepeatNoText = (TextView) findViewById(R.id.set_repeat_no);
        mRepeatTypeText = (TextView) findViewById(R.id.set_repeat_type);
        switchRepeat = (Switch) findViewById(R.id.repeat_switch);
        switchSound = (Switch) findViewById(R.id.sound_switch);

        myDb = new DatabaseHelper(this);

        //get the intent extra from MainActivity
        Intent receivedIntent = getIntent();

        //now get the itemID we passed as an extra
        selectedID = receivedIntent.getIntExtra("id",-1);

        //values
        mTitle = receivedIntent.getStringExtra("title");
        mDate = receivedIntent.getStringExtra("date");
        mTime = receivedIntent.getStringExtra("time");
        mRepeat = receivedIntent.getStringExtra("repeat");
        mRepeatNo = receivedIntent.getStringExtra("repeat_no");
        mRepeatType = receivedIntent.getStringExtra("repeat_type");
        mSound = receivedIntent.getStringExtra("sound");

        //set all the textviews
        mTitleText.setText(mTitle);
        mDateText.setText(mDate);
        mTimeText.setText(mTime);
        mRepeatNoText.setText(mRepeatNo);
        mRepeatTypeText.setText(mRepeatType);
        mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");

        //Title
        mTitleText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTitle = s.toString().trim();
                mTitleText.setError(null);
                saveBtn.setEnabled(!mTitle.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });



        //Date
        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

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

        simpleDatePicker = (DatePicker) findViewById(R.id.DatePicker);
        dateSubmit = (Button) findViewById(R.id.submitDate);
        dateSubmit.setOnClickListener(new View.OnClickListener(){
            int monthOfYear;
            int mDay;
            int mMonth;
            int mYear;
            @Override
            public void onClick(View v){
                monthOfYear ++;
                mDay = simpleDatePicker.getDayOfMonth();
                mMonth = simpleDatePicker.getMonth() + 1;
                mYear = simpleDatePicker.getYear();
                mDate = mDay + "/" + mMonth + "/" + mYear;
                mDateText.setText(mDate);
                dateShow.setVisibility(View.GONE);
            }
        });

        //time
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

        simpleTimePicker = (TimePicker) findViewById(R.id.timePicker1);
        timeSubmit = (Button) findViewById(R.id.submitTime);
        timeSubmit.setOnClickListener(new View.OnClickListener(){
            int monthOfYear;
            @Override
            public void onClick(View v){
                mHour = simpleTimePicker.getHour();
                mMinute = simpleTimePicker.getMinute();
                if (mMinute < 10) {
                    mTime = mHour + ":" + "0" + mMinute;
                } else {
                    mTime = mHour + ":" + mMinute;
                }
                mTimeText.setText(mTime);
                timeShow.setVisibility(View.GONE);

            }
        });

        //repeat
        switchRepeat = findViewById(R.id.repeat_switch);
        repeatNum = findViewById(R.id.RepeatNo);
        repeatType = findViewById(R.id.RepeatType);
        repeatNumShow = findViewById(R.id.repeatNoShow);
        repeatTypeShow = findViewById(R.id.repeatTypeShow);

        if (mRepeat.equals("false")){
            switchRepeat.setChecked(false);
        }else{
            switchRepeat.setChecked(true);
        }

        switchRepeat.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (switchRepeat.isChecked()){
                    repeatNum.setVisibility(View.VISIBLE);
                    repeatType.setVisibility(View.VISIBLE);

                    mRepeat = "true";
                    mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                }else{
                    repeatNum.setVisibility(View.GONE);
                    repeatType.setVisibility(View.GONE);
                    repeatNumShow.setVisibility(View.GONE);
                    repeatTypeShow.setVisibility(View.GONE);

                    mRepeat = "false";
                    mRepeatText.setText("Off");
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
        //confirm repeatNum
        repeatNoEditText = findViewById(R.id.repeatNoEditText);
        repeatNoSubmit = findViewById(R.id.repeatNoSubmit);
        repeatNoSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String repeatEditTextStr = repeatNoEditText.getText().toString();
                mRepeatNo = repeatEditTextStr;
                mRepeatNoText.setText(repeatEditTextStr);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatNumShow.setVisibility(View.GONE);


            }
        });

        //confirm Repeat Type Start
        rptMinute = findViewById(R.id.minute);
        rptHour = findViewById(R.id.hour);
        rptDay = findViewById(R.id.day);
        rptWeek = findViewById(R.id.week);
        rptMonth = findViewById(R.id.month);
        rptMinute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepeatType = "Minute";
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatTypeShow.setVisibility(View.GONE);

            }
        });
        rptHour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepeatType = "Hour";
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatTypeShow.setVisibility(View.GONE);

            }
        });
        rptDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepeatType = "Day";
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatTypeShow.setVisibility(View.GONE);

            }
        });
        rptWeek.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepeatType = "Week";
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatTypeShow.setVisibility(View.GONE);

            }
        });
        rptMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mRepeatType = "Month";
                mRepeatTypeText.setText(mRepeatType);
                mRepeatText.setText("Every " + mRepeatNo + " " + mRepeatType + "(s)");
                repeatTypeShow.setVisibility(View.GONE);

            }
        });

        //sound
        switchSound.findViewById(R.id.sound_switch);

        if (mSound.equals("false")){
            switchSound.setChecked(false);
        }else{
            switchSound.setChecked(true);
        }

        switchSound.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if (switchSound.isChecked()){
                    mSound = "true";

                }else{
                    mSound = "false";
                }
            }
        });

        //cancel button
        cancelBtn = findViewById(R.id.cancelBtn);
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent i = new Intent(EditReminder.this, MainActivity.class);
                startActivity(i);
            }
        });

        //save button
        saveBtn = findViewById(R.id.setBtn);

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

        UpdateData();
        DeleteData();



    }
    public void UpdateData(){
        saveBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        myDb.updateData(mTitle,mDate,mTime,mRepeat,mRepeatNo,mRepeatType,mSound,selectedID);
                        Toast.makeText(EditReminder.this,"Reminder Updated!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditReminder.this, MainActivity.class);
                        startActivity(i);

                    }
                }
        );

    }
    public void DeleteData(){
        deleteBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        myDb.deleteData(selectedID);
                        Toast.makeText(EditReminder.this,"Reminder Deleted!", Toast.LENGTH_LONG).show();
                        Intent i = new Intent(EditReminder.this, MainActivity.class);
                        startActivity(i);

                    }
                }
        );

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

    //codes for "Add Image" function
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
}
