package tmedia_ltd.foodalarm;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by will on 9/4/2015.
 */

//public class CustomItemAdapter extends ArrayAdapter<ItemArray> {
public class CustomItemAdapter extends ArrayAdapter<ItemArray> {

    public CustomItemAdapter(final Context context, final ArrayList<ItemArray> users) {
        super(context, 0, users);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ItemArray user = getItem(position);
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


        // Lookup view for data population
        TextView tvName = (TextView) convertView.findViewById(R.id.lblListItem);
        // Populate the data into the template view using the data object
        tvName.setText(user.name);
        // Return the completed view to render on screen
        return convertView;
    }
}
