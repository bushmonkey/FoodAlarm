package tmedia_ltd.foodalarm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

/**
 * Created by will on 1/24/2016.
 */
public class AlarmService extends Service
{
    private PowerManager.WakeLock mWakeLock;
    private NotificationManager mManager;

    Alarm alarm = new Alarm();

    public void onCreate()
    {
        super.onCreate();
    }

    private void handleIntent(Intent intent) {
        // obtain the wake lock
        PowerManager pm = (PowerManager) getSystemService(POWER_SERVICE);
        mWakeLock = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK,"Test" );
        mWakeLock.acquire();

        // check the global background data setting
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (!info.isConnected()) {
            stopSelf();
            return;
        }

        // do the actual work, in a separate thread
        new PollTask().execute();
    }

    private class PollTask extends AsyncTask<Void, Void, Void> {
        /**
         * This is where YOU do YOUR work. There's nothing for me to write here
         * you have to fill this in. Make your HTTP request(s) or whatever it is
         * you have to do to get your updates in here, because this is run in a
         * separate thread
         */
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }



        @Override
        protected void onPostExecute(Void result) {
            SetNotification(AlarmService.this);
            // handle your data
            stopSelf();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //super.onStartCommand(intent, flags, startId);

        handleIntent(intent);

        //SetNotification(AlarmService.this);

        //return super.onStartCommand(intent, flags, startId);

        return START_NOT_STICKY;
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

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        super.onDestroy();
        mWakeLock.release();
    }

}
