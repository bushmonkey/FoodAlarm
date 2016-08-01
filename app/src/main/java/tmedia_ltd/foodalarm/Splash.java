package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends Activity {
    private Context context;
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_splash);
        SharedPreferences settings = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        settings.edit().clear().commit();

/*        SharedPreferences preferences = getSharedPreferences("session", getApplicationContext().MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
            editor.putString("productName", "Name");
            editor.putString("expiryDate", " / / ");
            editor.putString("quantity", "Quantity");
            editor.putString("price", "Price");
            editor.commit();*/


        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(Splash.this,activity_card_main.class);
                Splash.this.startActivity(mainIntent);
                Splash.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);

    }
}
