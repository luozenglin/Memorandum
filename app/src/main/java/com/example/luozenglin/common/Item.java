package com.example.luozenglin.common;

import android.text.format.Time;

public class Item {
    private String datetime;
    private String content;

    public String getDatetime() {
        return datetime;
    }
    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    public static String getDate(String millis){
        Time time = new Time();
        time.set(Long.parseLong(millis));
        return ""+time.year+"/"+(time.month+1)+"/"+time.monthDay;
    }
    public static String getTime(String millis){
        Time time = new Time();
        time.set(Long.parseLong(millis));
        return ""+time.hour+":"+time.second;
    }
}
