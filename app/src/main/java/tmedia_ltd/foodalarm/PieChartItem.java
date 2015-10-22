package tmedia_ltd.foodalarm;

/**
 * Created by will on 10/15/2015.
 */
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.LayoutInflater;
import android.view.View;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

public class PieChartItem extends ListViewChartItem {

    //private Typeface mTf;
    private String DescriptionText;
    private SpannableString mCenterText;

    public PieChartItem(ChartData<?> cd, Context c, String Description) {
        super(cd);
        DescriptionText = Description;
        //mTf = Typeface.createFromAsset(c.getAssets(), "OpenSans-Regular.ttf");
        //mCenterText = generateCenterText();
    }

    @Override
    public int getItemType() {
        return TYPE_PIECHART;
    }

    @Override
    public View getView(int position, View convertView, Context c) {

        ViewHolder holder = null;

        if (convertView == null) {

            holder = new ViewHolder();

            convertView = LayoutInflater.from(c).inflate(
                    R.layout.list_item_piechart, null);
            holder.chart = (PieChart) convertView.findViewById(R.id.chart);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // apply styling
        holder.chart.setDescription(DescriptionText);
        holder.chart.setHoleRadius(52f);
        holder.chart.setTransparentCircleRadius(57f);
        //holder.chart.setCenterText(mCenterText);
       // holder.chart.setCenterTextTypeface(mTf);
        //holder.chart.setCenterTextSize(9f);
        holder.chart.setUsePercentValues(true);
        //holder.chart.setExtraOffsets(5, 10, 50, 10);

        mChartData.setValueFormatter(new PercentFormatter());
        //mChartData.setValueTypeface(mTf);
        mChartData.setValueTextSize(11f);
        mChartData.setValueTextColor(Color.WHITE);
        // set data
        holder.chart.setData((PieData) mChartData);
        holder.chart.setNoDataTextDescription("No data available");
        Legend l = holder.chart.getLegend();
        l.setEnabled(false);
        // do not forget to refresh the chart
        // holder.chart.invalidate();
        holder.chart.animateXY(900, 900);

        return convertView;
    }

    private SpannableString generateCenterText() {
        SpannableString s = new SpannableString("Food Waste\\ndistribution");
        s.setSpan(new RelativeSizeSpan(1.6f), 0, 14, 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.VORDIPLOM_COLORS[0]), 0, 14, 0);
        s.setSpan(new RelativeSizeSpan(1.4f), 25, s.length(), 0);
        s.setSpan(new ForegroundColorSpan(ColorTemplate.getHoloBlue()), 25, s.length(), 0);
        return s;
    }

    private static class ViewHolder {
        PieChart chart;
    }
}