package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodSummary extends Activity {
//public class FoodSummary extends Activity {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    CustomItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);

        TextView submit=(TextView)findViewById(R.id.textView);
        submit.setOnClickListener(onSubmit);


        ListView listView = (ListView) findViewById(R.id.lvUsers);
         populateItemList();

        ArrayList<ItemArray> arrayOfUsers = ItemArray.getUsers();
        // Create the adapter to convert the array to views

         adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView

        listView.setAdapter(adapter);

        // Creating an item click listener, to open/close our toolbar for each item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, final View view, int position, long id) {

                View toolbar = view.findViewById(R.id.toolbar);

                // Creating the expand animation for the item
                ExpandableAnimation expandAni = new ExpandableAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
            }
        });

    }

    /*
     * Preparing the list data
     */

    private void populateItemList() {
        // Construct the data source
        String ItemName = new String();
        ArrayList<ItemArray> arrayOfUsers = new ArrayList<ItemArray>();
        //arrayOfUsers=ItemArray.AddItem(ItemName);
        arrayOfUsers=ItemArray.getUsers();
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

    public void usedClick (View v) {
            ListView lv = (ListView) findViewById(R.id.lvUsers);
            int position = lv.getPositionForView(v);
            ItemArray.RemoveItem(position);
        Toast.makeText(
                getApplicationContext(),
                position+" Item used", Toast.LENGTH_SHORT)
                .show();
            adapter.notifyDataSetChanged();
    }

    public void wastedClick (View v) {
        ListView lv = (ListView) findViewById(R.id.lvUsers);
        int position = lv.getPositionForView(v);
        ItemArray.RemoveItem(position);
        Toast.makeText(
                getApplicationContext(),
                "Item wasted", Toast.LENGTH_SHORT)
                .show();
        adapter.notifyDataSetChanged();
    }

    private void prepareListData() {

    }

    private View.OnClickListener onSubmit=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(view.getContext(),AddItem.class );
            startActivityForResult(myIntent,0);

        }
    };
}
