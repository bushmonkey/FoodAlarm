package tmedia_ltd.foodalarm;

/**
 * Created by will on 9/4/2015.
 */
import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class CustomListActivity extends Activity {
    private foodDetailsDataSource datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);
        populateUsersList();

        datasource = new foodDetailsDataSource(this);
        datasource.open();
    }

    private void populateUsersList() {

        // Construct the data source
        ArrayList<FoodTable_SQLlite> arrayOfUsers = new ArrayList<FoodTable_SQLlite>();
        //arrayOfUsers=ItemArray.AddItem(ItemName);
        arrayOfUsers=datasource.getAllComments();
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

}