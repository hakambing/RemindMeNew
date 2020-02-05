package com.example.remindme;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
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
            TextView date = (TextView) convertView.findViewById(R.id.recycle_date);
            TextView time = (TextView) convertView.findViewById(R.id.recycle_time);
            TextView repeat = (TextView) convertView.findViewById(R.id.recycle_repeat);
            TextView repeat_no = (TextView) convertView.findViewById(R.id.recycle_repeat_no);
            TextView repeat_type = (TextView) convertView.findViewById(R.id.recycle_repeat_type);
            TextView sound = (TextView) convertView.findViewById(R.id.active_image);

            if (title != null){
                title.setText((reminder.getTitle()));
            }
            if (date != null){
                date.setText((reminder.getDate()));
            }if (time != null){
                time.setText((reminder.getTime()));
            }if (repeat != null){
                repeat.setText((reminder.getRepeat()));
            }if (repeat_no != null){
                repeat_no.setText((reminder.getRepeat_No()));
            }if (repeat_type != null){
                repeat_type.setText((reminder.getRepeat_Type()));
            }if (sound != null){
                sound.setText((reminder.getSound()));
            }

        }
        return convertView;
    }
}
