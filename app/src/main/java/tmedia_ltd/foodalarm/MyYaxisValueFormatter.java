package tmedia_ltd.foodalarm;

import com.github.mikephil.charting.components.YAxis;

/**
 * Created by will on 10/21/2015.
 */
public interface MyYaxisValueFormatter extends YaxisValueFormatter {
    String getFormattedValue(float value, YAxis yAxis);
}
