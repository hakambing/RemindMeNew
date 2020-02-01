package com.example.remindme;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
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

import com.example.remindme.data.AlarmReminderContract;
import com.example.remindme.data.DatabaseHelper;
import com.example.remindme.reminder.AlarmScheduler;

import java.util.Calendar;

public class NewReminder extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener, LoaderManager.LoaderCallbacks<Cursor> {

    DatabaseHelper myDb;
    private static final int EXISTING_VEHICLE_LOADER = 0;

    private EditText mTitleText;
    private TextView mDateText, mTimeText, mRepeatText, mRepeatNoText, mRepeatTypeText;
    private Calendar mCalendar;
    private int mYear, mMonth, mHour, mMinute, mDay;
    private long mRepeatTime;

    private Uri mCurrentReminderUri;
    private boolean mVehicleHasChanged = false;
    private String mTitle;
    private String mTime;
    private String mDate;
    private String mRepeat;
    private String mRepeatNo;
    private String mRepeatType;
    private String mSound;

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
    Button viewAllBtn;

    ImageView mImageView;
    RelativeLayout mChooseBtn;
    ImageView deleteImage;

    Switch switchSound;

    // Values for orientation change
    private static final String KEY_TITLE = "title_key";
    private static final String KEY_TIME = "time_key";
    private static final String KEY_DATE = "date_key";
    private static final String KEY_REPEAT = "repeat_key";
    private static final String KEY_REPEAT_NO = "repeat_no_key";
    private static final String KEY_REPEAT_TYPE = "repeat_type_key";
    private static final String KEY_ACTIVE = "active_key";

    // Constant values in milliseconds
    private static final long milMinute = 60000L;
    private static final long milHour = 3600000L;
    private static final long milDay = 86400000L;
    private static final long milWeek = 604800000L;
    private static final long milMonth = 2592000000L;

    private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mVehicleHasChanged = true;
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_reminder);
        myDb = new DatabaseHelper(this);


        //initialise values
        mTitleText = (EditText) findViewById(R.id.editText);
        mDateText = (TextView) findViewById(R.id.set_date);
        mTimeText = (TextView) findViewById(R.id.set_time);
        mRepeatText = (TextView) findViewById(R.id.set_repeat);
        mRepeatNoText = (TextView) findViewById(R.id.set_repeat_no);
        mRepeatTypeText = (TextView) findViewById(R.id.set_repeat_type);
        switchRepeat = (Switch) findViewById(R.id.repeat_switch);
        switchSound = (Switch) findViewById(R.id.sound_switch);

        // Initialize default values

        mRepeat = "true";
        mRepeatNo = Integer.toString(1);
        mRepeatType = "Hour";
        mSound = "true";

        mCalendar = Calendar.getInstance();
        mHour = mCalendar.get(Calendar.HOUR_OF_DAY);
        mMinute = mCalendar.get(Calendar.MINUTE);
        mYear = mCalendar.get(Calendar.YEAR);
        mMonth = mCalendar.get(Calendar.MONTH) + 1;
        mDay = mCalendar.get(Calendar.DATE);

        mDate = mDay + "/" + mMonth + "/" + mYear;
        mTime = mHour + ":" + mMinute;

        // Setup TextViews using reminder values
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
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        //Date
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

        //Sound switch
        switchSound.findViewById(R.id.sound_switch);
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
                Intent i = new Intent(NewReminder.this, MainActivity.class);
                startActivity(i);
            }
        });

        //delete button
        deleteBtn = findViewById(R.id.deleteBtn);
        if (mCurrentReminderUri == null){
            deleteBtn.setVisibility(View.GONE);
        }

        //save button
        saveBtn = findViewById(R.id.setBtn);
        viewAllBtn = findViewById(R.id.viewReminderBtn);

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

        AddData();
        viewAll();
    }

    //delete reminder
    private void deleteReminder() {
        // Only perform the delete if this is an existing reminder.
        if (mCurrentReminderUri != null) {
            // Call the ContentResolver to delete the reminder at the given content URI.
            // Pass in null for the selection and selection args because the mCurrentreminderUri
            // content URI already identifies the reminder that we want.
            int rowsDeleted = getContentResolver().delete(mCurrentReminderUri, null, null);

            // Show a toast message depending on whether or not the delete was successful.
            if (rowsDeleted == 0) {
                // If no rows were deleted, then there was an error with the delete.
                Toast.makeText(this, getString(R.string.editor_delete_reminder_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                // Otherwise, the delete was successful and we can display a toast.
                Toast.makeText(this, getString(R.string.editor_delete_reminder_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        // Close the activity
        finish();
    }

   public void AddData(){
        saveBtn.setOnClickListener(
                new View.OnClickListener(){
                    @Override
                    public void onClick(View v){
                        boolean isInserted = myDb.insertData(mTitle,mDate,mTime,mRepeat,mRepeatNo,mRepeatType,mSound);
                        if (isInserted==true){
                            Toast.makeText(NewReminder.this,"Reminder Saved!", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(NewReminder.this, MainActivity.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(NewReminder.this, "Error saving reminder.", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

    }
   /* @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

        String[] projection = {
                AlarmReminderContract.AlarmReminderEntry._ID,
                AlarmReminderContract.AlarmReminderEntry.KEY_TITLE,
                AlarmReminderContract.AlarmReminderEntry.KEY_DATE,
                AlarmReminderContract.AlarmReminderEntry.KEY_TIME,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO,
                AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE,
                AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE,
        };

        // This loader will execute the ContentProvider's query method on a background thread
        return new CursorLoader(this,   // Parent activity context
                mCurrentReminderUri,         // Query the content URI for the current reminder
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);                  // Default sort order
    }*/

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






    @Override
    public void onTimeSet(TimePicker timePicker, int i, int i1) {

    }


    public void viewAll(){
        viewAllBtn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Cursor data = myDb.getAllData();
                        if(data.getCount()==0){
                            showMessage("no data sial","dog");
                            return;
                        }
                        StringBuffer buffer = new StringBuffer();
                        while (data.moveToNext()){
                            buffer.append("Id :"+data.getString(0)+"\n");
                            buffer.append("Title :"+data.getString(1)+"\n");
                            buffer.append("Date :"+data.getString(2)+"\n");
                            buffer.append("Time :"+data.getString(3)+"\n");
                            buffer.append("Repeat :"+data.getString(5)+"\n");
                            buffer.append("RepeatNo :"+data.getString(6)+"\n");
                            buffer.append("RepeatType :"+data.getString(7)+"\n");
                            buffer.append("Sound :"+data.getString(8)+"\n\n");


                        }
                        //show all data
                        showMessage("Data",buffer.toString());

                    }
                }
        );
    }

    public void showMessage(String title,String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        // Proceed with moving to the first row of the cursor and reading data from it
        // (This should be the only row in the cursor)
        if (cursor.moveToFirst()) {
            int titleColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TITLE);
            int dateColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_DATE);
            int timeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_TIME);
            int repeatColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT);
            int repeatNoColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_NO);
            int repeatTypeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_REPEAT_TYPE);
            int activeColumnIndex = cursor.getColumnIndex(AlarmReminderContract.AlarmReminderEntry.KEY_ACTIVE);

            // Extract out the value from the Cursor for the given column index
            String title = cursor.getString(titleColumnIndex);
            String date = cursor.getString(dateColumnIndex);
            String time = cursor.getString(timeColumnIndex);
            String repeat = cursor.getString(repeatColumnIndex);
            String repeatNo = cursor.getString(repeatNoColumnIndex);
            String repeatType = cursor.getString(repeatTypeColumnIndex);
            String active = cursor.getString(activeColumnIndex);



            // Update the views on the screen with the values from the database
            mTitleText.setText(title);
            mDateText.setText(date);
            mTimeText.setText(time);
            mRepeatNoText.setText(repeatNo);
            mRepeatTypeText.setText(repeatType);
            mRepeatText.setText("Every " + repeatNo + " " + repeatType + "(s)");
            // Setup up active buttons
            // Setup repeat switch
            if (repeat.equals("false")) {
                switchRepeat.setChecked(false);
                mRepeatText.setText(R.string.repeat_off);

            } else if (repeat.equals("true")) {
                switchRepeat.setChecked(true);
            }

        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    @Override
    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

    }
}
