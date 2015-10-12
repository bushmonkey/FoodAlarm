package tmedia_ltd.foodalarm;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tmedia_ltd.foodalarm.Fragment.nativeview.NativeCardWithListFragment;

public class activity_card_main extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_card_main);

        if (savedInstanceState == null) {
            getFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragmentParentViewGroup, new NativeCardWithListFragment() {
                    })
                    .commit();
        }

    }

    public void trackerclick (View v) {
        Intent myIntent=new Intent(v.getContext(),AddItem.class );
        startActivityForResult(myIntent, 0);
    }
}
