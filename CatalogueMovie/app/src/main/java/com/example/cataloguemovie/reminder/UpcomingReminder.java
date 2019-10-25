package com.example.cataloguemovie.reminder;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.example.cataloguemovie.BuildConfig;
import com.example.cataloguemovie.R;
import com.example.cataloguemovie.model.PojoTvMovie;
import com.example.cataloguemovie.view.DetailMovie;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;

public class UpcomingReminder extends BroadcastReceiver {

    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private static int notifId = 1000;

    String date;
    String type, message;
    int position;

    private ArrayList<PojoTvMovie> listUpcoming = new ArrayList<>();
    public UpcomingReminder() {

    }

    @Override
    public void onReceive(final Context context, Intent intent) {

        AsyncHttpClient client = new AsyncHttpClient();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        date = dateFormat.format(new Date());
        String url = "https://api.themoviedb.org/3/discover/movie?api_key=" + BuildConfig.API_KEY + "&primary_release_date.gte=" + date + "&primary_release_date.lte=" + date;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObj = new JSONObject(result);
                    JSONArray list = responseObj.getJSONArray("results");
                    //for (int i = 0; i < list.length(); i++) {
                        JSONObject movietv = list.getJSONObject(position);
                        PojoTvMovie movie = new PojoTvMovie(movietv, "movie");
                        listUpcoming.add(movie);
                        int notif = 503;

                        String title = movie.getTitle();
                        String overview = movie.getOverview();
                        showReminderNotif(context, title, overview, notif);
                    //}
                } catch (Exception e) {

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });


    }

    private void showReminderNotif(Context context, String title, String overview, int notifId) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        final PojoTvMovie movie = listUpcoming.get(position);
        Intent intent = new Intent(context, DetailMovie.class);
        intent.putExtra(DetailMovie.EXTRA_PERSON, movie);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //int id = intent.getIntExtra("id",2);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, notifId, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Uri alarmtone = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle(title)
                .setContentText(overview)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setColor(ContextCompat.getColor(context, android.R.color.transparent))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmtone);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

            builder.setChannelId(NOTIFICATION_CHANNEL_ID);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        notificationManager.notify(notifId, builder.build());

    }

    public void setRepeatingAlarm(Context context) {

        int delay = 0;

        cancelReminder(context);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, UpcomingReminder.class);
        intent.putExtra("title", message);
        intent.putExtra("id", type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 8);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        int SDK_INT = Build.VERSION.SDK_INT;
        if (SDK_INT < Build.VERSION_CODES.KITKAT) {
            alarmManager.set(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay, pendingIntent);
        } else if (SDK_INT > Build.VERSION_CODES.KITKAT && SDK_INT < Build.VERSION_CODES.M) {
            alarmManager.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay,
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent
            );
        } else if (SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis() + delay, pendingIntent);
        }

        notifId += 1;
        delay += 3000;
        //Log.e("title", movie.getTitle().toString());

    }

    public void cancelReminder(Context context) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(getPending(context));
    }

    private static PendingIntent getPending(Context context) {
        /* get the application context */
        Intent alarmIntent = new Intent(context, UpcomingReminder.class);

        boolean isAlarmOn = (PendingIntent.getBroadcast(context, notifId, alarmIntent,
                PendingIntent.FLAG_NO_CREATE) != null);

//        Log.e("isAlarmOn : ", String.valueOf(isAlarmOn));

        Log.e("id_when_cancel", String.valueOf(notifId));

        return PendingIntent.getBroadcast(context, 101, alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
    }
}
