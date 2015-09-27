package tmedia_ltd.foodalarm;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by will on 9/4/2015.
 */


public class CustomItemAdapter extends ArrayAdapter<FoodItem> {

    public CustomItemAdapter(final Context context, final List<FoodItem> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        FoodItem user = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.single_list_group, parent, false);
        }

        CheckBox ItemCheckbox= (CheckBox)  convertView.findViewById(R.id.ItemCheckBox);
        final View toolbar = convertView.findViewById(R.id.toolbar);

        ItemCheckbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // Creating the expand animation for the item
                ExpandableAnimation expandAni = new ExpandableAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
            }


        });

        Button UsedButton= (Button)  convertView.findViewById(R.id.usedButton);
        Button IDButton= (Button) convertView.findViewById(R.id.idButton);

        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.lblListItem);
        TextView tvDate = (TextView) convertView.findViewById(R.id.lblListDate);
        // Populate the data into the template view using the data object

        tvName.setText(user.getName());
        setExpiryText(tvDate, user);
        IDButton.setText(user.getId());
        // Return the completed view to render on screen
        Log.d("ID button:", user.getId());
        return convertView;

    }

    private void setExpiryText(TextView DatetxtView, FoodItem currentItem) {
        Calendar currentDate = Calendar.getInstance(); //Get the current date
        Calendar NextWeek = Calendar.getInstance();
        NextWeek.add(Calendar.DAY_OF_MONTH, 5);

// reset hour, minutes, seconds and millis
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format it as per your requirement
        String dateNow = formatter.format(currentDate.getTime());
        String ExpiredDateString = currentItem.getExpiry();
        Date ExpiredDate = new Date(currentItem.getExpiryLong());
        Date NextWeekDate =  NextWeek.getTime();
        Date TodayDate = currentDate.getTime();


        Log.d("expired date:",ExpiredDate.toString() );
        Log.d("todays date:",TodayDate.toString() );
        Log.d("next week date:",NextWeekDate.toString() );
        if (ExpiredDate.compareTo(TodayDate)<0) {
            DatetxtView.setTextColor(Color.parseColor("#7E1518"));
            DatetxtView.setText("Expired: " + currentItem.getExpiry());
        }
        else if (ExpiredDate.compareTo(TodayDate)==0) {
            DatetxtView.setTextColor(Color.parseColor("#A8383B"));
            DatetxtView.setText("Today: " + currentItem.getExpiry());
        } else if (ExpiredDate.compareTo(NextWeekDate)<=0) {
            DatetxtView.setTextColor(Color.parseColor("#D3696C"));
            DatetxtView.setText("Soon: " + currentItem.getExpiry());
        } else {
            DatetxtView.setTextColor(Color.parseColor("#FDA8AA"));
            DatetxtView.setText(currentItem.getExpiry());
        }
    }
}
