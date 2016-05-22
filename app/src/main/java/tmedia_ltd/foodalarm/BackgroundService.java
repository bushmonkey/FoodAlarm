package tmedia_ltd.foodalarm;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by will on 5/12/2016.
 */
public class BackgroundService extends Service {

    private boolean isRunning;
    private Context context;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
