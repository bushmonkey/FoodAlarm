package tmedia_ltd.foodalarm;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ChartView extends AppCompatActivity {
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart_view);

        ListView lv = (ListView) findViewById(R.id.lvCharts);
        ArrayList<ListViewChartItem> list = new ArrayList<ListViewChartItem>();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            toolbar.setTitle(R.string.app_name);
            setSupportActionBar(toolbar);
        }

        mydb = new DBHelper(this);
        mydb.getWritableDatabase();
        Float NumRows = (float) mydb.numberOfRowsForUseType("Used");
        Float NumWasted = (float) mydb.numberOfRowsForUseType("Wasted");

        list.add(new PieChartItem(generateDataPie(NumRows,NumWasted), getApplicationContext(),"Distribution of used items"));
        list.add(new LineChartItem(generateDataLine(1), getApplicationContext(),"Number of items wasted"));
        list.add(new BarChartItem(generateDataBar(1), getApplicationContext(),"Money saved"));
        list.add(new PieChartItem(generateDataPie(NumRows,NumWasted), getApplicationContext(),"Top 4 Most wasted items"));

        ChartDataAdapter cda = new ChartDataAdapter(getApplicationContext(), list);
        lv.setAdapter(cda);
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

    private class ChartDataAdapter extends ArrayAdapter<ListViewChartItem> {

        public ChartDataAdapter(Context context, List<ListViewChartItem> objects) {
            super(context, 0, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            return getItem(position).getView(position, convertView, getContext());
        }

        @Override
        public int getItemViewType(int position) {
            // return the views type
            return getItem(position).getItemType();
        }

        @Override
        public int getViewTypeCount() {
            return 3; // we have 3 different item-types
        }
    }

    private BarData generateDataBar(int cnt) {

        ArrayList<BarEntry> entries = new ArrayList<BarEntry>();

        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry((int) (Math.random() * 70) + 30, i));
        }

        BarDataSet d = new BarDataSet(entries, "");
        d.setBarSpacePercent(20f);
        d.setColors(ColorTemplate.VORDIPLOM_COLORS);
        d.setHighLightAlpha(255);

        BarData cd = new BarData(getMonths(), d);
        return cd;
    }

    private PieData generateDataPie(Float NumRows, Float NumWasted){

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(NumRows, 0));
        entries.add(new Entry(NumWasted, 1));

        PieDataSet dataset = new PieDataSet(entries,"");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Used");
        xVals.add("Wasted");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(xVals,dataset);
        data.setValueFormatter(new MyValueFormatter());

        return data;
    }

    private PieData generateTop4Pie(Float NumRows, Float NumWasted){

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(NumRows, 0));
        entries.add(new Entry(NumWasted, 1));

        PieDataSet dataset = new PieDataSet(entries,"");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Used");
        xVals.add("Wasted");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        PieData data = new PieData(xVals,dataset);
        data.setValueFormatter(new MyValueFormatter());

        return data;
    }

    private boolean CreatePieChart () {
        PieChart chart = (PieChart) findViewById(R.id.chart);
        Float NumRows = Float.valueOf(mydb.numberOfRowsForUseType("Used"));
        Float NumWasted = Float.valueOf(mydb.numberOfRowsForUseType("Wasted"));
        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(NumRows, 0));
        entries.add(new Entry(NumWasted, 1));

        PieDataSet dataset = new PieDataSet(entries,"");
        ArrayList<String> xVals = new ArrayList<String>();
        xVals.add("Used");
        xVals.add("Wasted");
        PieData data = new PieData(xVals,dataset);
        data.setValueFormatter(new MyValueFormatter());
        chart.setUsePercentValues(true);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(true);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setHoleColorTransparent(true);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(true);
        chart.setData(data);
        chart.setNoDataTextDescription("No food used");
        chart.setDescription("Food waste distribution");
        dataset.setColors(ColorTemplate.PASTEL_COLORS);
        chart.animateY(1400, Easing.EasingOption.EaseInOutQuad);

       Legend l = chart.getLegend();
        l.setPosition(Legend.LegendPosition.BELOW_CHART_RIGHT);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(10f);
        l.setEnabled(false);
        return true;
    }

    public class MyValueFormatter implements ValueFormatter {

        private DecimalFormat mFormat;

        public MyValueFormatter() {
            mFormat = new DecimalFormat("###,###,##0"); // use one decimal
        }

        @Override
        public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
            // write your logic here
            return mFormat.format(value) + " %"; // e.g. append a dollar-sign
        }
    }

    private LineData generateDataLine(int cnt) {
        List<FoodItem> arrayOfUsers;
        arrayOfUsers=mydb.getExpiredByDate("Used");

        ArrayList<Entry> e1 = new ArrayList<Entry>();

        ArrayList<Entry> valsUsed = new ArrayList<Entry>();
        ArrayList<Entry> valsWasted = new ArrayList<Entry>();
        int Today = 0;
        int Yesterday = 0;
        int LastWeek = 0;
        int LastMonth = 0;
        int Older = 0;
        Calendar currentDate = Calendar.getInstance();

        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);
        Date TodayDate = currentDate.getTime();

        for (FoodItem temp : arrayOfUsers) {
            if (temp.getUseDate_Date().compareTo(TodayDate)==0)
            {
                Today++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)==-1)
            {
                Yesterday++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)>-7)
            {
                LastWeek++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)>-31)
            {
                LastMonth++;
            }
            else
            {
                Older++;
            }
        }

        Entry c1e1 = new Entry(Older, 0);
        valsUsed.add(c1e1);
        Entry c1e2 = new Entry(LastMonth, 1);
        valsUsed.add(c1e2);
        Entry c1e3 = new Entry(LastWeek, 2);
        valsUsed.add(c1e3);
        Entry c1e4 = new Entry(Yesterday, 3);
        valsUsed.add(c1e4);
        Entry c1e5 = new Entry(Today, 4);
        valsUsed.add(c1e5);


        LineDataSet d1 = new LineDataSet(valsUsed, "Used");
        d1.setLineWidth(2.5f);
        d1.setCircleSize(4.5f);
        d1.setHighLightColor(Color.rgb(244, 117, 117));
        d1.setDrawValues(false);
        d1.setColor(ColorTemplate.PASTEL_COLORS[0]);
        d1.setCircleColor(ColorTemplate.PASTEL_COLORS[0]);

        arrayOfUsers=mydb.getExpiredByDate("Wasted");
        Today = 0;
        Yesterday = 0;
        LastWeek = 0;
        LastMonth = 0;
        Older = 0;

        for (FoodItem temp : arrayOfUsers) {
            if (temp.getUseDate_Date().compareTo(TodayDate)==0)
            {
                Today++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)==-1)
            {
                Yesterday++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)>-7)
            {
                LastWeek++;
            }
            else if (temp.getUseDate_Date().compareTo(TodayDate)>-31)
            {
                LastMonth++;
            }
            else
            {
                Older++;
            }
            Log.d("comparison:", (Integer.toString(temp.getUseDate_Date().compareTo(TodayDate))));
        }

        Entry c2e1 = new Entry(Older, 0);
        valsWasted.add(c2e1);
        Entry c2e2 = new Entry(LastMonth, 1);
        valsWasted.add(c2e2);
        Entry c2e3 = new Entry(LastWeek, 2);
        valsWasted.add(c2e3);
        Entry c2e4 = new Entry(Yesterday, 3);
        valsWasted.add(c2e4);
        Entry c2e5 = new Entry(Today, 4);
        valsWasted.add(c2e5);

        LineDataSet d2 = new LineDataSet(valsWasted, "Wasted");
        d2.setLineWidth(2.5f);
        d2.setCircleSize(4.5f);
        d2.setHighLightColor(Color.rgb(244, 117, 117));
        d2.setColor(ColorTemplate.PASTEL_COLORS[1]);
        d2.setCircleColor(ColorTemplate.PASTEL_COLORS[1]);
        d2.setDrawValues(false);

        ArrayList<LineDataSet> sets = new ArrayList<LineDataSet>();
        sets.add(d1);
        sets.add(d2);

        LineData cd = new LineData(getMonths(), sets);
        return cd;
    }

    private ArrayList<String> getMonths() {

        ArrayList<String> m = new ArrayList<String>();
        m.add("Older");
        m.add("month");
        m.add("Week");
        m.add("Ystdy");
        m.add("Today");

        return m;
    }
}
