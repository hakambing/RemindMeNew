package com.example.remindme;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class Alarm_Items extends ArrayAdapter<Reminder> {

    private LayoutInflater mInflater;
    private ArrayList<Reminder> reminders;
    private int mViewResourceId;

    public Alarm_Items(Context context,int textViewResourceId,ArrayList<Reminder> reminders){
        super(context, textViewResourceId,reminders);
        this.reminders = reminders;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;

    }

    public View getView(int position, View convertView, ViewGroup parents){
        convertView = mInflater.inflate(mViewResourceId,null);

        Reminder reminder = reminders.get(position);

        if(reminder != null){
            TextView title = (TextView) convertView.findViewById(R.id.recycle_title);
            TextView datetime = (TextView) convertView.findViewById(R.id.recycle_date_time);
            TextView repeat = (TextView) convertView.findViewById(R.id.recycle_repeat_info);
            ImageView active = (ImageView) convertView.findViewById(R.id.active_image);



            if (title != null){
                title.setText((reminder.getTitle()));
            }
            if (datetime != null){
                datetime.setText((reminder.getDate() +" "+ reminder.getTime()));
            }if (repeat != null){
                if (reminder.getRepeat().equals("true")){
                    repeat.setText(("Every "+reminder.getRepeat_No()+ " "+ reminder.getRepeat_Type()));
                }
                else {
                    repeat.setText("Repeat Off");
                }
            }
            if(reminder.getSound().equals("true")){
                active.setImageResource(R.drawable.ic_notifications_active_black_24dp);
            }else if (reminder.getSound().equals("false")) {
                active.setImageResource(R.drawable.ic_notifications_off_black_24dp);
            }




        }
        return convertView;
    }
}
