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
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.habib.movie.BuildConfig;
import com.habib.movie.Main2Activity;
import com.habib.movie.R;
import com.habib.movie.model.Movie;
import com.habib.movie.movie.MovieAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class ReleaseReminder extends BroadcastReceiver {
    public static int NOTIF_ID = 4;

    @Override
    public void onReceive(Context context, Intent intent) {
        getMovieRelease(context);
    }

    private void getMovieRelease(final Context context) {
        final ArrayList<Movie> movies = new ArrayList<>();
        AndroidNetworking.get(BuildConfig.MOVIE_URL+BuildConfig.API_KEY+BuildConfig.LANG_URL)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.optJSONArray("results");
                            for(int i = 0 ;i<array.length();i++){
                                JSONObject object = array.optJSONObject(i);
//                                movies.add(new Movie(
//                                        object.getString("id"),
//                                        object.getString("poster_path"),
//                                        object.getString("title"),
//                                        object.getString("vote_average"),
//                                        object.getString("release_date"),
//                                        object.getString("overview")
//                                ));
                                String id = object.getString("id");
                                String title = object.getString("title");
                                String over = object.getString("overview");
                                String release = object.getString("release_date");
                                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                                Date date = new Date();
                                String now = dateFormat.format(date);
                                if(release.equals(now)){
                                    showRelease(context,title,over,id);
                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        //Toast.makeText(requireContext(),R.string.con,Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void showRelease(Context context, String title, String over, String id) {
        String CHANNEL_ID = "channel_02";
        String CHANNEL_NAME = "Release Reminder";

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Intent intent = new Intent(context, Main2Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, Integer.parseInt(id),intent,PendingIntent.FLAG_UPDATE_CURRENT);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_notifications_black_24dp)
                .setLargeIcon(BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.ic_notifications_black_24dp))
                .setContentTitle(title)
                .setContentText(over)
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
            manager.notify(Integer.parseInt(id),builder.build());
        }
    }
    public void CancelRelease(Context context){
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReleaseReminder.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIF_ID,intent,0);
        Objects.requireNonNull(alarmManager).cancel(pendingIntent);
    }
    public void setReleaseAlarm(Context context,String type,String time,String message){
        CancelRelease(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context,ReleaseReminder.class);
        intent.putExtra("message",message);
        intent.putExtra("type",type);
        String times[] = time.split(":");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,Integer.parseInt(times[0]));
        calendar.set(Calendar.MINUTE,Integer.parseInt(times[1]));
        calendar.set(Calendar.SECOND,0);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context,NOTIF_ID,intent,0);
        Objects.requireNonNull(alarmManager)
                .setInexactRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent);
    }
}
