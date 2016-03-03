package tmedia_ltd.foodalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import java.util.Calendar;

/**
 * Created by will on 1/24/2016.
 */
public class Alarm extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        //PowerManager pm= (PowerManager) context.getSystemService(context.POWER_SERVICE);
       // PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"");
        //wl.acquire();

/**
        android.support.v4.app.NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("My notification")
                        .setContentText("Hello World!");

        String ns = Context.NOTIFICATION_SERVICE;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        notificationManager.notify(1, mBuilder.build());
**/



        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, Alarm.class);
        context.startService(i);
        SetNotification(context);
        SetAlarm(context);

       // wl.release();
    }

    public void SetAlarm(Context context)
    {
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE,30);

            AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        //PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0 );
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public void SetNotification(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(context, activity_card_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent sender = PendingIntent.getActivity(context, 192839, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setSmallIcon(R.drawable.ic_stat_notification);
        builder.setContentTitle("Food Alarm");
        builder.setContentText("Items are waiting in your cupboard!");
        builder.setOngoing(true);
        builder.setContentIntent(sender);

        builder.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(
                Context.NOTIFICATION_SERVICE);
        notificationManager.notify(192888, builder.build());

    }

}
