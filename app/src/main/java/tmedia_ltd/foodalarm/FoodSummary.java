package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class FoodSummary extends Activity {
//public class FoodSummary extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    CustomItemAdapter adapter;
    private PendingIntent pendingIntent;
    NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);

        /* Retrieve a PendingIntent that will perform a broadcast */
        Intent alarmIntent = new Intent(FoodSummary.this, FoodAlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(FoodSummary.this, 0, alarmIntent, 0);
        //startNotificationAt10();
        scheduleNotification(getNotification("5 second delay"), 5000);

        TextView submit=(TextView)findViewById(R.id.textView);
        submit.setOnClickListener(onSubmit);

        ListView listView = (ListView) findViewById(R.id.lvUsers);
         populateItemList();

        ArrayList<ItemArray> arrayOfUsers = ItemArray.getUsers();
        // Create the adapter to convert the array to views

         adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView

        listView.setAdapter(adapter);

        // Creating an item click listener, to open/close our toolbar for each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                View toolbar = view.findViewById(R.id.toolbar);

                // Creating the expand animation for the item
                ExpandableAnimation expandAni = new ExpandableAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
            }
        });

    }

    /*
     * Preparing the list data
     */

    private void populateItemList() {
        // Construct the data source
        String ItemName = new String();
        ArrayList<ItemArray> arrayOfUsers = new ArrayList<ItemArray>();
        //arrayOfUsers=ItemArray.AddItem(ItemName);
        arrayOfUsers=ItemArray.getUsers();
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

    public void usedClick (View v) {
            ListView lv = (ListView) findViewById(R.id.lvUsers);
            int position = lv.getPositionForView(v);
            ItemArray.RemoveItem(position);
        Toast.makeText(
                getApplicationContext(),
                "Food used", Toast.LENGTH_SHORT)
                .show();
            adapter.notifyDataSetChanged();
    }

    public void wastedClick (View v) {
        ListView lv = (ListView) findViewById(R.id.lvUsers);
        int position = lv.getPositionForView(v);
        ItemArray.RemoveItem(position);
        Toast.makeText(
                getApplicationContext(),
                "Food wasted", Toast.LENGTH_SHORT)
                .show();
        adapter.notifyDataSetChanged();
    }

    private void prepareListData() {

    }

    private View.OnClickListener onSubmit=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(view.getContext(),AddItem.class );
            startActivityForResult(myIntent,0);

        }
    };

    public void startNotificationAt10() {
        AlarmManager manager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000 * 60 * 20;

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 21);
        calendar.set(Calendar.MINUTE, 20);

        /* Repeating on every 20 minutes interval */
        manager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private void Notify(String notificationTitle, String notificationMessage){
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        @SuppressWarnings("deprecation")

        Notification notification = new Notification(R.drawable.icon,"New Message", System.currentTimeMillis());
        Intent notificationIntent = new Intent(this,FoodSummary.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        notification.setLatestEventInfo(FoodSummary.this, notificationTitle, notificationMessage, pendingIntent);
        notificationManager.notify(9999, notification);
    }

    public void scheduleNotification(Notification notification, int delay) {

        Intent notificationIntent = new Intent(this, DeviceBootReceiver.class);
        notificationIntent.putExtra(DeviceBootReceiver.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(DeviceBootReceiver.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        /* Set the alarm to start at 10:30 AM */
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 22);
        calendar.set(Calendar.MINUTE, 35);

        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
       alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
        //alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }

    private Notification getNotification(String content) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setContentTitle("Food expiring today");
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.calendar);
        return builder.build();
    }
}
