package com.habib.movie.notification;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.habib.movie.Main2Activity;
import com.habib.movie.R;

import java.util.Calendar;
import java.util.Objects;

public class DailyReminder extends BroadcastReceiver {
    public static int NOTIFICATION_ID = 1;
    @Override
    public void onReceive(Context context, Intent intent) {
        showDaily(
                context,
                context.getResources().getString(R.string.app_name),
                context.getResources().getString(R.string.message_daily),
                intent.getStringExtra("message"),
                NOTIFICATION_ID
        );
    }

    private void showDaily(Context context,String judul,String pesan, String message, int notificationId) {
        String CHANNEL_ID = "channel_01";
        String CHANNEL_NAME = "Daily Reminder";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, Main2Activity.class);
        PendingIntent pendingIntent = TaskStackBuilder.create(context)
                .addNextIntent(intent)
                .getPendingIntent(NOTIFICATION_ID,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,message)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_notifications_black_24dp))
                .setContentTitle(judul)
                .setContentText(pesan)
                .setSubText(message)
                .setContentIntent(pendingIntent)
                .setVibrate(new long[]{1000,1000,1000,1000,1000})
                .setSound(uri)
                .setAutoCancel(true);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{1000,1000,1000,1000,1000});
            builder.setChannelId(CHANNEL_ID);
            if(manager != null){
                manager.createNotificationChannel(channel);
            }
        }
        if(manager!=null){
            manager.notify(notificationId,builder.build());
        }
    }
    public void CancelDaily(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
    public void setDailyAlarm(Context context,String type,String time,String message){
        CancelDaily(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,DailyReminder.class);
        intent.putExtra("message",message);
        intent.putExtra("type",type);
        String times[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIFICATION_ID,intent,0);
        Objects.requireNonNull(alarmManager)
                .setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}
