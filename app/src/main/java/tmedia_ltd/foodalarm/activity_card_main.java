package tmedia_ltd.foodalarm;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import tmedia_ltd.foodalarm.Fragment.nativeview.NativeCardWithListFragment;

public class activity_card_main extends AppCompatActivity {

    DBHelper mydb;
    private String scanContent;
    private Toast mToast;
    List<FoodItem> arrayOfUsers;
    public String currentBarcode;
    public String currentProduct;
    public static String SharedBarcode;



    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    public String getCurrentBarcode(){
        //Log.d("Getting barcode", currentBarcode);
        return currentBarcode;
    }

    public String getProduct(){
       return currentProduct;
    }

    public void resetProduct(){
        currentProduct="";
    }

    public void resetBarcode(){
        currentBarcode="";
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentBarcode="";
        //currentProduct="";
        List<FoodItem> arrayOfUsers = new LinkedList<FoodItem>();

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

        SharedPreferences settings = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);

    }

    public void onResume() {
        super.onResume();
        //SetAlarm(activity_card_main.this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {


        //retrieve scan result
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (requestCode != 20 && requestCode != 0 )
        {
            scanContent = scanningResult.getContents().toString();
            showToast("item scanned: " + scanContent);
            Log.d("Item scanned?", scanContent);
            Log.d("requestCode", Integer.toString(requestCode));
            RetrieveBarcodeDetails(scanContent);
            SaveBarcodePreferences(scanContent);
            String RetrievedProductName = getProductPreferences();
            //Activity activity = (Activity) this;
            Intent myIntent = new Intent(this, activity_card_main.class);
            this.startActivityForResult(myIntent, 0);
        }

    }

    private String getProductPreferences()
    {
        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        String productName = preferences.getString("productName", null);
        return productName;
    }

    public void SaveBarcodePreferences(String barcode)
    {
        SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("sessionId", barcode);
        editor.commit();
    }

    public void SaveProductName(String name)
    {
        SharedPreferences preferences = getSharedPreferences("session",getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("productName", name);
        editor.commit();
    }

    public void RetrieveBarcodeDetails(String barcode)
    {
        mydb = new DBHelper(this);
        mydb.getWritableDatabase();
        Log.d("checking barcode:", barcode);
        arrayOfUsers = mydb.getByBarcode(barcode);
        //Log.d("BarcodeInserted: ", arrayOfUsers.get(0).getName().toString());
        //currentBarcode=arrayOfUsers.get(0).getName().toString();
        //final EditText ProductEntered = (EditText) dialog.getCustomView().findViewById(R.id.ProductEntry);
        //ProductEntered.setText(arrayOfUsers.get(0).getName());
        for (FoodItem temp : arrayOfUsers) {
            currentProduct = temp.getName();
        }
        if (currentProduct != null && !currentProduct.isEmpty()) {
            SaveProductName(currentProduct);
        }
        currentBarcode=barcode;
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
