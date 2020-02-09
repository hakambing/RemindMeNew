package com.example.remindme.reminder;

import android.graphics.Bitmap;

public class Reminder {
    private String Title;
    private String Date;
    private String Time;
    private String Repeat;
    private String Repeat_No;
    private String Repeat_Type;
    private String Sound;


    public Reminder(String title, String date, String time, String repeat, String repeat_no, String repeat_type, String sound){
        Title =title;
        Date = date;
        Time = time;

        Repeat = repeat;
        Repeat_No = repeat_no;
        Repeat_Type = repeat_type;
        Sound = sound;

    }

    public String getTitle() {
        return Title;
    }

    public String getDate() {
        return Date;
    }

    public String getTime() {
        return Time;
    }

    public String getRepeat() {
        return Repeat;
    }

    public String getRepeat_No() {
        return Repeat_No;
    }

    public String getRepeat_Type() {
        return Repeat_Type;
    }

    public String getSound() {
        return Sound;
    }


}
