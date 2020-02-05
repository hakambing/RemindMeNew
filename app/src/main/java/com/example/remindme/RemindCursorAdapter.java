package com.example.remindme;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.remindme.data.DatabaseHelper;

public class RemindCursorAdapter extends android.widget.CursorAdapter {
    private TextView mTitleText, mDateAndTimeText, mRepeatInfoText;
    private ImageView mSoundImage;

    public RemindCursorAdapter(Context context, Cursor c) { super(context, c, 0 /* flags */); }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }}
    /*@Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.alarm_items, parent, false);

    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        mTitleText = (TextView) view.findViewById(R.id.recycle_title);
        mDateAndTimeText = (TextView) view.findViewById(R.id.recycle_date_time);
        mRepeatInfoText = (TextView) view.findViewById(R.id.recycle_repeat_info);
        mSoundImage = (ImageView) view.findViewById(R.id.active_image);

        int titleColumnIndex = cursor.getColumnIndex("TITLE");
        int dateColumnIndex = cursor.getColumnIndex("DATE");
        int timeColumnIndex = cursor.getColumnIndex("TIME");
        int repeatColumnIndex = cursor.getColumnIndex("REPEAT");
        int repeatNoColumnIndex = cursor.getColumnIndex("REPEAT_NO");
        int repeatTypeColumnIndex = cursor.getColumnIndex("REPEAT_TYPE");
        int activeColumnIndex = cursor.getColumnIndex("SOUND");

        String title = cursor.getString(titleColumnIndex);
        String date = cursor.getString(dateColumnIndex);
        String time = cursor.getString(timeColumnIndex);
        String repeat = cursor.getString(repeatColumnIndex);
        String repeatNo = cursor.getString(repeatNoColumnIndex);
        String repeatType = cursor.getString(repeatTypeColumnIndex);
        String sound = cursor.getString(activeColumnIndex);

        String dateTime = date + " " + time;

        setReminderTitle(title);
        setReminderDateTime(dateTime);
        setReminderRepeatInfo(repeat, repeatNo, repeatType);
        setSoundImage(sound);
    }

    // Set reminder title view
    public void setReminderTitle(String title) {
        mTitleText.setText(title);
        String letter = "A";

        if(title != null && !title.isEmpty()) {
            letter = title.substring(0, 1);
        }

    }

    // Set date and time views
    public void setReminderDateTime(String datetime) {
        mDateAndTimeText.setText(datetime);
    }

    // Set repeat views
    public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
        if(repeat.equals("true")){
            mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
        }else if (repeat.equals("false")) {
            mRepeatInfoText.setText("Repeat Off");
        }
    }

    // Set active image as on or off
    public void setSoundImage(String sound){
        if(sound.equals("true")){
            mSoundImage.setImageResource(R.drawable.ic_notifications_active_black_24dp);
        }else if (sound.equals("false")) {
            mSoundImage.setImageResource(R.drawable.ic_notifications_off_black_24dp);
        }
    }
}
*/