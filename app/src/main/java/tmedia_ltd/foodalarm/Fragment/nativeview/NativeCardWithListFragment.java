package tmedia_ltd.foodalarm.Fragment.nativeview;

/*
 * ******************************************************************************
 *   Copyright (c) 2013-2014 Gabriele Mariotti.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *  *****************************************************************************
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import it.gmariotti.cardslib.library.view.CardViewNative;
import tmedia_ltd.foodalarm.R;
import tmedia_ltd.foodalarm.cards.NativeFoodCard;

//import it.gmariotti.cardslib.demo.fragment.BaseMaterialFragment;


public abstract class NativeCardWithListFragment extends Fragment {

    NativeFoodCard card;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initCard();
        //setTitle();
    }

    protected void setTitle(){
        int titleResId = getTitleResourceId();
            getActivity().setTitle(getTitleResourceId());
    }

   // public abstract int getTitleResourceId();


    protected int getSubTitleHeaderResourceId() {
        return R.string.header_title_subtitle_cardwithlist;
    }


    protected int getTitleHeaderResourceId() {
        return R.string.header_title_group5;
    }


   // protected String getDocUrl() {
     //   return "https://github.com/gabrielemariotti/cardslib/blob/master/doc/CARDWITHLIST.md";
   // }


    //protected String getSourceUrl() {
    //    return "https://github.com/gabrielemariotti/cardslib/blob/master/demo/stock/src/main/java/it/gmariotti/cardslib/demo/fragment/nativeview/CardWithListFragment.java";
   // }

    public int getTitleResourceId() {
        return R.string.carddemo_title_carwithlist_card;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.demo_fragment_native_cardwithlist_card, container, false);
        return inflater.inflate(R.layout.activity_card_view, container, false);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    /**
     * This method builds a simple card
     */
    private void initCard() {

        //Create a Card
        card= new NativeFoodCard(getActivity());
        card.init();

        //Set card in the cardView
        CardViewNative cardView = (CardViewNative) getActivity().findViewById(R.id.carddemo_weathercard);
        cardView.setCard(card);
    }

    @Override
    public void onPause() {
        super.onPause();
        //if (card != null)
//            card.unregisterDataSetObserver();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // This ID represents the Home or Up button. In the case of this
                // activity, the Up button is shown. Use NavUtils to allow users
                // to navigate up one level in the application structure. For
                // more details, see the Navigation pattern on Android Design:
                //
                // http://developer.android.com/design/patterns/navigation.html#up-vs-back
                //
                NavUtils.navigateUpFromSameTask(getActivity());
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}