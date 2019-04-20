package com.habib.movie.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class Preference {
    private static String PREFERENCE = "preference";
    private static String DAILY = "daily";
    private static String RELEASE = "release";
    private static String MESSAGE_DAILY = "messageDaily";
    private static String MESSAGE_RELEASE = "messageRelease";
    public SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    @SuppressLint("CommitPrefEdits")
    public Preference(Context context){
        sharedPreferences = context.getSharedPreferences(PREFERENCE,Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }
    public void setDailyTime(String time){
        editor.putString(DAILY,time);
        editor.commit();
    }
    public void setDailyMessage(String message){
        editor.putString(MESSAGE_DAILY,message);
    }
    public void setReleaseTime(String time){
        editor.putString(DAILY,time);
        editor.commit();
    }
    public void setReleaseMessage(String message){
        editor.putString(MESSAGE_RELEASE,message);
    }
}
