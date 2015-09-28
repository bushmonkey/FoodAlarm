package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartView extends Activity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

        mydb = new DBHelper(this);
        mydb.getWritableDatabase();

        ArrayList<BarEntry> entries = new ArrayList<>();
        int NumRows = mydb.numberOfRowsForUseType("Used");
        Log.d("number of rows:", Integer.toString(NumRows));
        entries.add(new BarEntry(mydb.numberOfRowsForUseType("Used"), 0));
        entries.add(new BarEntry(mydb.numberOfRowsForUseType("Wasted"), 1));

        //entries.add(new BarEntry(4, 0));
       // entries.add(new BarEntry(10, 1));

        BarDataSet dataset = new BarDataSet(entries, "# of items");
        ArrayList<String> labels = new ArrayList<String>();
        labels.add("Used");
        labels.add("Wasted");

        BarChart chart = new BarChart(this);
        setContentView(chart);
        BarData data = new BarData(labels, dataset);
        chart.setData(data);

        chart.setDescription(" ");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        chart.animateY(5000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_chart_view, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
