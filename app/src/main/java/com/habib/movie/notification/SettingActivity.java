package com.habib.movie.notification;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.habib.movie.R;

public class SettingActivity extends AppCompatActivity {
    String DAILY_TYPE = "reminderDaily";
    String DAILY_REMINDER = "dailyReminder";
    String KEY_DAILY_REMINDER = "daily";
    String RELEASE_TYPE = "reminderRelease";
    String RELEASE_REMINDER = "releaseReminder";
    String KEY_RELEASE_REMINDER = "release";
    DailyReminder dailyReminder;
    ReleaseReminder releaseReminder;
    Preference preference;
    SharedPreferences daily,release;
    SharedPreferences.Editor dailyedt,releaseedt;
    Switch dailysw,releasesw;
    String timeDaily = "07:00";
    String timeRelease = "08:00";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getResources().getString(R.string.settings));
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        dailysw = findViewById(R.id.daily);
        releasesw = findViewById(R.id.release);
        dailysw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setDaily(b);
            }
        });
        releasesw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                setRelease(b);
            }
        });
        dailyReminder = new DailyReminder();
        releaseReminder = new ReleaseReminder();
        preference = new Preference(this);
        setPreference();
    }

    private void setRelease(boolean b) {
        releaseedt = release.edit();
        if(b){
            releaseedt.putBoolean(KEY_RELEASE_REMINDER,true);
            releaseedt.commit();
            releaseOn();
        }else {
            releaseedt.putBoolean(KEY_RELEASE_REMINDER,false);
            releaseedt.commit();
            releaseOff();
        }
    }

    private void releaseOff() {
        releaseReminder.CancelRelease(SettingActivity.this);
    }

    private void releaseOn() {
        String mess = getResources().getString(R.string.release_reminder);
        preference.setReleaseTime(timeRelease);
        preference.setReleaseMessage(mess);
        releaseReminder.setReleaseAlarm(SettingActivity.this,RELEASE_TYPE,timeRelease,mess);
    }

    public void setDaily(boolean ya){
        dailyedt = daily.edit();
        if(ya){
            dailyedt.putBoolean(KEY_DAILY_REMINDER,true);
            dailyedt.commit();
            dailyOn();
        }else{
            dailyedt.putBoolean(KEY_DAILY_REMINDER,false);
            dailyedt.commit();
            dailyOff();
        }
    }

    private void dailyOff() {
        dailyReminder.CancelDaily(SettingActivity.this);
    }

    private void dailyOn() {
        String mess = getResources().getString(R.string.daily_reminder);
        preference.setDailyTime(timeDaily);
        preference.setDailyMessage(mess);
        dailyReminder.setDailyAlarm(SettingActivity.this,DAILY_TYPE,timeDaily,mess);
    }
    private void setPreference(){
        daily = getSharedPreferences(DAILY_REMINDER,MODE_PRIVATE);
        boolean checkDaily = daily.getBoolean(KEY_DAILY_REMINDER,false);
        dailysw.setChecked(checkDaily);
        release = getSharedPreferences(RELEASE_REMINDER,MODE_PRIVATE);
        boolean checkRelease = release.getBoolean(KEY_RELEASE_REMINDER,false);
        releasesw.setChecked(checkRelease);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
