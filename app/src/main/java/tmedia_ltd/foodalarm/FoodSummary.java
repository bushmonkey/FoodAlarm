package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class FoodSummary extends Activity  {
    ExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_summary);

        TextView submit=(TextView)findViewById(R.id.textView);
        submit.setOnClickListener(onSubmit);

        populateItemList();

        ArrayList<ItemArray> arrayOfUsers = ItemArray.getUsers();
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
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
/*
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);

        // preparing list data
        prepareListData();

        listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);

        // setting list adapter
        expListView.setAdapter(listAdapter);

        // Listview Group click listener
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                // Toast.makeText(getApplicationContext(),
                // "Group Clicked " + listDataHeader.get(groupPosition),
                // Toast.LENGTH_SHORT).show();
                return false;
            }
        });

        // Listview Group expanded listener
        expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

            //@Override
            public void onGroupExpand(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Expanded",
                        Toast.LENGTH_SHORT).show();
            }
        });

        // Listview Group collasped listener
        expListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {

            @Override
            public void onGroupCollapse(int groupPosition) {
                Toast.makeText(getApplicationContext(),
                        listDataHeader.get(groupPosition) + " Collapsed",
                        Toast.LENGTH_SHORT).show();

            }
        });

        // Listview on child click listener
        expListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,int groupPosition, int childPosition, long id)
            {
                Toast.makeText(
                        getApplicationContext(),
                        "Testing", Toast.LENGTH_SHORT)
                        .show();
                View toolbar = v.findViewById(R.id.toolbar);

                // Creating the expand animation for the item
                ExpandableAnimation expandAni = new ExpandableAnimation(toolbar, 500);

                // Start the animation on the toolbar
                toolbar.startAnimation(expandAni);
                return true;
            }


        });
        */
    }

    /*
     * Preparing the list data
     */

    private void populateItemList() {
        // Construct the data source
        ArrayList<ItemArray> arrayOfUsers = new ArrayList<ItemArray>();
        arrayOfUsers=ItemArray.AddItem(arrayOfUsers);
        // Create the adapter to convert the array to views
        CustomItemAdapter adapter = new CustomItemAdapter(this, arrayOfUsers);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.lvUsers);
        listView.setAdapter(adapter);
    }

    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("Expiring soon");
        listDataHeader.add("Now Showing");
        listDataHeader.add("Coming Soon..");

        // Adding child data
        List<String> top250 = new ArrayList<String>();
        top250.add("The Shawshank Redemption");
        top250.add("The Godfather");
        top250.add("The Godfather: Part II");
        top250.add("Pulp Fiction");
        top250.add("The Good, the Bad and the Ugly");
        top250.add("The Dark Knight");
        top250.add("12 Angry Men");

        List<String> nowShowing = new ArrayList<String>();
        nowShowing.add("The Conjuring");
        nowShowing.add("Despicable Me 2");
        nowShowing.add("Turbo");
        nowShowing.add("Grown Ups 2");
        nowShowing.add("Red 2");
        nowShowing.add("The Wolverine");

        List<String> comingSoon = new ArrayList<String>();
        comingSoon.add("2 Guns");
        comingSoon.add("The Smurfs 2");
        comingSoon.add("The Spectacular Now");
        comingSoon.add("The Canyons");
        comingSoon.add("Europa Report");

        listDataChild.put(listDataHeader.get(0), top250); // Header, Child data
        listDataChild.put(listDataHeader.get(1), nowShowing);
        listDataChild.put(listDataHeader.get(2), comingSoon);
    }

    private View.OnClickListener onSubmit=new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            Intent myIntent=new Intent(view.getContext(),DetailsCapture.class );
            startActivityForResult(myIntent,0);

        }
    };
}
