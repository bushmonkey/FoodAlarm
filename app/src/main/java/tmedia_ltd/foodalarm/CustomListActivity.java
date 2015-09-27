package tmedia_ltd.foodalarm;

/**
 * Created by will on 9/4/2015.
 */

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CustomListActivity extends Activity {
    private DBHelper datasource;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);
        populateUsersList();

    }

    private void populateUsersList() {

        // Construct the data source
        List<FoodItem> arrayOfUsers = new ArrayList<FoodItem>();
        //arrayOfUsers=ItemArray.AddItem(ItemName);
        arrayOfUsers=datasource.getExpiringSoon(2);
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

}