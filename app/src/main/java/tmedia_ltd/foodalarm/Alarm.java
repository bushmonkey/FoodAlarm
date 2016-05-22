package tmedia_ltd.foodalarm;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

/**
 * Created by will on 1/24/2016.
 */
public class Alarm extends BroadcastReceiver
{
    DBHelper mydb;
    List<FoodItem> arrayOfUsers;

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


        //context.startService(i);
       // SetNotification(context);
       // SetAlarm(context);
        Log.i("test","action received");
        //Intent background = new Intent(context, BackgroundService.class);
        //context.startService(background);

        mydb = new DBHelper(context);
        mydb.getWritableDatabase();
        arrayOfUsers = mydb.getExpiringSoon(2);
        int itemCount = arrayOfUsers.size();

        Log.d("number of items: ", Integer.toString(itemCount));

        if (itemCount>0) {
            showNotification(context);
        }
       // wl.release();
    }

    public void showNotification(Context context) {
        Intent intent = new Intent(context, activity_card_main.class);
        PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.ic_stat_notification)
                .setContentTitle("Food Alarm")
                .setContentText("Items are waiting in your cupboard!");
        mBuilder.setContentIntent(pi);
        mBuilder.setDefaults(Notification.DEFAULT_SOUND);
        mBuilder.setAutoCancel(true);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(0, mBuilder.build());
    }

    public void SetAlarm(Context context)
    {
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 00);
        calendar.set(Calendar.MINUTE,00);

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        //Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(context, AlarmService.class);

        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        am.cancel(pi);

         //   AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
       // Intent i = new Intent(context, Alarm.class);
        //PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0 );
       // PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT );
        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);
        am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pi);
    }

    public void SetNotification(Context context) {

        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.setClass(context, activity_card_main.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);

        PendingIntent sender = PendingIntent.getActivity(context, 192839, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);
        //AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
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
       // alarmManager.cancel(sender);

    }

}
