package tmedia_ltd.foodalarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import java.util.Calendar;

import tmedia_ltd.foodalarm.Fragment.nativeview.NativeCardWithListFragment;

public class activity_card_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_card_main);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }


        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new NativeCardWithListFragment() {
                    })
                    .commit();
        }

        SetAlarm(activity_card_main.this);

    }

    public void onResume() {
        super.onResume();
        //SetAlarm(activity_card_main.this);
    }

    public void SetAlarm(Context context)
    {
        //dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        // Set the alarm to start at approximately 2:00 p.m.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 7);
        calendar.set(Calendar.MINUTE,20);
        Log.i("Set calendar", "for time: " + calendar.toString());

        //Intent i = new Intent(context, AlarmService.class);
        Intent i = new Intent(context, Alarm.class);
        boolean alarmRunning = (PendingIntent.getBroadcast(context, 0, i, PendingIntent.FLAG_NO_CREATE) !=null);
        if (!alarmRunning) {
            //PendingIntent pi = PendingIntent.getService(context, 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent pi = PendingIntent.getService(context, 0, i, 0);



            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, i, 0);
            AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
            am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
            //am.cancel(pi);
        }

        //PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0 );


        //am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY, pi);

    }



}
