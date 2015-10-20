package tmedia_ltd.foodalarm;

import android.content.Context;
import android.view.View;

import com.github.mikephil.charting.data.ChartData;

/**
 * Created by will on 10/15/2015.
 */


/**
 * baseclass of the chart-listview items
 * @author philipp
 *
 */
public abstract class ListViewChartItem {

    protected static final int TYPE_BARCHART = 0;
    protected static final int TYPE_LINECHART = 1;
    protected static final int TYPE_PIECHART = 2;

    protected ChartData<?> mChartData;

    public ListViewChartItem(ChartData<?> cd) {
        this.mChartData = cd;
    }

    public abstract int getItemType();

    public abstract View getView(int position, View convertView, Context c);
}


