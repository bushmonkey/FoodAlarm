package tmedia_ltd.foodalarm.cards;

/**
 * Created by will on 9/30/2015.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import it.gmariotti.cardslib.library.internal.Card;
import it.gmariotti.cardslib.library.internal.CardHeader;
import it.gmariotti.cardslib.library.internal.base.BaseCard;
import it.gmariotti.cardslib.library.prototypes.CardWithList;
import it.gmariotti.cardslib.library.prototypes.LinearListView;
import tmedia_ltd.foodalarm.ChartView;
import tmedia_ltd.foodalarm.DBHelper;
import tmedia_ltd.foodalarm.FoodItem;
import tmedia_ltd.foodalarm.R;
import tmedia_ltd.foodalarm.activity_card_main;


public class NativeFoodCard extends CardWithList {
    //CustomItemAdapter adapter;
    DBHelper mydb;
    List<FoodItem> arrayOfUsers;
    String lastFilter;
    private Context context;
    private Toast mToast;
    private Thread mThread;
    private EditText passwordInput;

    private String scanContent;

    private View positiveAction;
    //private final static int STORAGE_PERMISSION_RC = 69;
    //private Handler mHandler;
    EditText mDateEntryField;
    Boolean ValidExpiryDate;
    EditText mPriceEntryField;
    EditText mBarcodeEntryField;
    EditText ProductNameEt;
    Context cardContext;

    private void showToast(String message) {
        if (mToast != null) {
            mToast.cancel();
            mToast = null;
        }
        mToast = Toast.makeText(getContext(), message, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private void startThread(Runnable run) {
        if (mThread != null)
            mThread.interrupt();
        mThread = new Thread(run);
        mThread.start();
    }

    public NativeFoodCard(Context context) {
        super(context);
        this.context=context;
        this.cardContext=context;
    }

    @Override
    protected CardHeader initCardHeader() {

        //Add Header
        CardHeader header = new CardHeader(getContext(),R.layout.card_innerheader);

        //Add a popup menu. This method set OverFlow button to visible
        header.setPopupMenu(R.menu.popup_item, new CardHeader.OnClickCardHeaderPopupMenuListener() {
            @Override
            public void onMenuItemClick(BaseCard card, MenuItem item) {
                switch (item.getItemId()){
                    case R.id.action_add:
                        showCustomView();
                        break;
                    case R.id.action_popup:
                        Activity activity = (Activity) context;
                        Intent myIntent = new Intent(context, ChartView.class);
                        activity.startActivityForResult(myIntent, 0);
                        break;
                }

            }
        });

        header.setTitle("Items in your cupboard"); //should use R.string.
        return header;
    }

    @Override
    protected void initCard() {

        //Set the whole card as swipeable
        setSwipeable(false);
        //setOnSwipeListener(new OnSwipeListener() {
         //   @Override
         //   public void onSwipe(Card card) {
                //SelectSwipeReason(card);
         //   }


        //});

    }


    @Override
    protected List<ListObject> initChildren() {

        lastFilter="all";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 18);
        calendar.set(Calendar.MINUTE, 30);
        calendar.set(Calendar.SECOND, 0);

        mydb = new DBHelper(getContext());
        mydb.getWritableDatabase();



        if (lastFilter.equals("soon"))
            arrayOfUsers = mydb.getExpiringSoon(2);
        else if (lastFilter.equals("week"))
            arrayOfUsers = mydb.getExpiringSoon(7);
        else if (lastFilter.equals("month"))
            arrayOfUsers = mydb.getExpiringSoon(30);
        else
            arrayOfUsers = mydb.getAllContacts();

        // use the SimpleCursorAdapter to show the
        // elements in a ListView
        //adapter = new CustomItemAdapter(getContext(), arrayOfUsers);
        //populateItemList();


        //Init the list
        List<ListObject> mObjects = new ArrayList<ListObject>();

        for (FoodItem temp : arrayOfUsers) {
            WeatherObject w1= new WeatherObject(this);
            w1.city = temp.getName();
            w1.temperature = temp.getExpiry();
            w1.expiryLong = temp.getExpiryLong();
            w1.setObjectId(temp.getId());
            mObjects.add(w1);
        }

        //Example onSwipe
        /*w2.setOnItemSwipeListener(new OnItemSwipeListener() {
            @Override
            public void onItemSwipe(ListObject object,boolean dismissRight) {
                Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
            }
        });*/


        return mObjects;
    }

    @Override
    public View setupChildView(int childPosition, ListObject object, View convertView, ViewGroup parent) {

        //Setup the ui elements inside the item
        TextView city = (TextView) convertView.findViewById(R.id.carddemo_weather_city);
        //ImageView icon = (ImageView) convertView.findViewById(R.id.carddemo_weather_icon);
        TextView temperature = (TextView) convertView.findViewById(R.id.carddemo_weather_temperature);
        TextView dateView = (TextView) convertView.findViewById(R.id.carddemo_weather_date);
        //Retrieve the values from the object
        WeatherObject weatherObject= (WeatherObject)object;
        //icon.setImageResource(weatherObject.weatherIcon);
        city.setText(weatherObject.city);
        temperature.setText(weatherObject.temperature);
        dateView.setText(weatherObject.temperature);
        setExpiryText(dateView,weatherObject.temperature,weatherObject.expiryLong);
        return  convertView;
    }

    @Override
    public int getChildLayoutId() {
        return R.layout.card_innercard;
    }



    // -------------------------------------------------------------
    // Weather Object
    // -------------------------------------------------------------

    private void populateItemList() {
        // Construct the data source
        String ItemName = new String();
        //arrayOfUsers=ItemArray.AddItem(ItemName);
        if (lastFilter.equals("soon"))
            arrayOfUsers = mydb.getExpiringSoon(2);
        else if (lastFilter.equals("week"))
            arrayOfUsers = mydb.getExpiringSoon(7);
        else if (lastFilter.equals("month"))
            arrayOfUsers = mydb.getExpiringSoon(30);
        else
            arrayOfUsers = mydb.getAllContacts();
        // Create the adapter to convert the array to views
        //CustomItemAdapter adapter = new CustomItemAdapter(getContext(), arrayOfUsers);
        // Attach the adapter to a ListView
        //ListView listView = (ListView) findViewById(R.id.lvUsers);
        //listView.setAdapter(adapter);
    }

    public class WeatherObject extends DefaultListObject{

        public String city;
        //public int weatherIcon;
        public String temperature;
        public String temperatureUnit="°C";
        public Long expiryLong;

        public WeatherObject(Card parentCard){
            super(parentCard);
            init();
        }

        private void init(){
            //OnClick Listener
            setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(LinearListView parent, View view, int position, ListObject object) {
                    Toast.makeText(getContext(), "Click on " + getObjectId(), Toast.LENGTH_SHORT).show();
                }
            });

            //OnItemSwipeListener
            setOnItemSwipeListener(new OnItemSwipeListener() {
                @Override
                public void onItemSwipe(ListObject object, boolean dismissRight) {
                    //Toast.makeText(getContext(), "Swipe on " + object.getObjectId(), Toast.LENGTH_SHORT).show();
                    SelectSwipeReason(object.getObjectId());

                }
            });
        }

    }

    private void SelectSwipeReason(final String ObjectId)

    {
        new MaterialDialog.Builder(getContext())
                .title(R.string.SelectionHeader)
                .items(R.array.SelectionArray)
                .itemsCallbackSingleChoice(2, new MaterialDialog.ListCallbackSingleChoice() {
                    @Override

                    public boolean onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        showToast("Item " + text);
                        mydb.ExpireItem(Integer.parseInt(ObjectId), text.toString(), System.currentTimeMillis());

                        return true; // allow selection
                    }
                })
                .positiveText(R.string.md_choose_label)
                .show();
        //Toast.makeText(getContext(), "Swipe on " + card.getCardHeader().getTitle(), Toast.LENGTH_SHORT).show();
    }

    private void setExpiryText(TextView DatetxtView, String currentItem, Long expiryLong) {
        Calendar currentDate = Calendar.getInstance(); //Get the current date
        Calendar NextWeek = Calendar.getInstance();
        NextWeek.add(Calendar.DAY_OF_MONTH, 10);

// reset hour, minutes, seconds and millis
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.MILLISECOND, 0);

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy"); //format it as per your requirement
        String dateNow = formatter.format(currentDate.getTime());
        String ExpiredDateString = currentItem;
        Date ExpiredDate = new Date(expiryLong);
        Date NextWeekDate =  NextWeek.getTime();
        Date TodayDate = currentDate.getTime();


        Log.d("expired date:", ExpiredDate.toString());
        Log.d("todays date:",TodayDate.toString() );
        Log.d("next week date:",NextWeekDate.toString() );
        if (ExpiredDate.compareTo(TodayDate)<0) {
            DatetxtView.setTextColor(Color.parseColor("#7E1518"));
            DatetxtView.setText("Expired");
        }
        else if (ExpiredDate.compareTo(TodayDate)==0) {
            DatetxtView.setTextColor(Color.parseColor("#A8383B"));
            DatetxtView.setText("Today");
        } else if (ExpiredDate.compareTo(NextWeekDate)<=0) {
            DatetxtView.setTextColor(Color.parseColor("#D3696C"));
            DatetxtView.setText("Soon");
        } else {
            DatetxtView.setTextColor(Color.parseColor("#7ed267"));
            DatetxtView.setText(ExpiredDateString);
            DatetxtView.setText("In date");
        }
    }

    public void showCustomView() {
        MaterialDialog dialog = new MaterialDialog.Builder(getContext())
                .title("Enter product details")
                .autoDismiss(false)
                .customView(R.layout.dialog_customview, true)
                .positiveText("Save")
                .neutralText("Barcode")
                .negativeText(android.R.string.cancel)
                .onPositive(new MaterialDialog.SingleButtonCallback() {

                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showToast("Item saved");
                        Long ExpiryDateinMillis = ConvertExpiryDate(passwordInput.getText().toString());
                        String ProductText = ProductNameEt.getText().toString();



                        Double PriceText = Double.valueOf((mPriceEntryField.getText().toString()));

                        //removes decimal place if present
                        PriceText = PriceText*100;
                        Float PriceStreamed = PriceText.floatValue();
                        //ItemArray.AddItem(ProductText);
                        //boolean itemInserted = mydb.insertContact(ProductText,System.currentTimeMillis(),"quantity","price");
                        boolean itemInserted = mydb.insertContact(ProductText, ExpiryDateinMillis, "quantity", PriceStreamed.longValue(), scanContent);
                        Log.d("Item inserted?", Boolean.toString(itemInserted));
                        Activity activity = (Activity) context;
                        Intent myIntent = new Intent(context, activity_card_main.class);
                        activity.startActivityForResult(myIntent, 0);
                    }
                })
                .onNeutral(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        Activity activity = (Activity) context;
                        IntentIntegrator scanIntegrator = new IntentIntegrator(activity);
                        scanIntegrator.initiateScan();

                    }

                    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
                    //retrieve scan result
                    IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
                        if (scanningResult != null) {
                            showToast("Item scanned");
                            //we have a result
                            scanContent = scanningResult.getContents();
                            String scanFormat = scanningResult.getFormatName();
                            Log.d("Item scanned?",scanningResult.getContents().toString());
                        }
                    }
                })
                .onNegative(new MaterialDialog.SingleButtonCallback(){
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which)
                    {Activity activity = (Activity) context;
                        Intent myIntent = new Intent(context, activity_card_main.class);
                        activity.startActivityForResult(myIntent, 0);}
                })
                .build();
        ProductNameEt = (AutoCompleteTextView) dialog.getCustomView().findViewById(R.id.ProductEntry);

                ArrayAdapter adapter = new ArrayAdapter(context,
                android.R.layout.simple_dropdown_item_1line, COUNTRIES);
        final AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView)
                ProductNameEt;
        autoCompleteTextView.setAdapter(adapter);

        ValidExpiryDate = false;
        mPriceEntryField = (EditText) dialog.getCustomView().findViewById(R.id.priceEntry);
        mPriceEntryField.addTextChangedListener(mPriceEntryWatcher);

        positiveAction = dialog.getActionButton(DialogAction.POSITIVE);
        //noinspection ConstantConditions
       passwordInput = (EditText) dialog.getCustomView().findViewById(R.id.DateEntry);
        passwordInput.addTextChangedListener(new TextWatcher() {


            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String working = s.toString();
                boolean isValid = true;
                if (working.length()==2 && before ==0) {
                    if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>31) {
                        isValid = false;
                    } else {
                        working+="/";
                        passwordInput.setText(working);
                        passwordInput.setSelection(working.length());
                    }
                }
                else if (working.length()==5 && before ==0) {
                    String enteredMonth = working.substring(3);
                    if (Integer.parseInt(enteredMonth) < 1 || Integer.parseInt(enteredMonth)>12) {
                        isValid = false;
                    } else {
                        working+="/20";
                        passwordInput.setText(working);
                        passwordInput.setSelection(working.length());
                    }
                }
                else if (working.length()==10 && before ==0) {
                    String enteredYear = working.substring(6);
                    //int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    //if (Integer.parseInt(enteredYear) < currentYear) {
                    //     isValid = false;
                    //}
                    // else {
                    isValid = true;
                    ValidExpiryDate = true;
                    positiveAction.setEnabled(s.toString().trim().length() > 0);
                    //working+="/";
                    passwordInput.setText(working);
                    passwordInput.setSelection(working.length());
                    //}
                } else if (working.length()!=10) {
                    positiveAction.setEnabled(false);
                    isValid = false;
                }

                if (!isValid) {
                    passwordInput.setError("Enter a valid date: dd/MM/YYYY");
                } else {
                    passwordInput.setError(null);
                }

            }

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

        });


        dialog.show();
        positiveAction.setEnabled(false); // disabled by default
    }

    private TextWatcher mDateEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==2 && before ==0) {
                if (Integer.parseInt(working) < 1 || Integer.parseInt(working)>31) {
                    isValid = false;
                } else {
                    working+="/";
                    mDateEntryField.setText(working);
                    mDateEntryField.setSelection(working.length());
                }
            }
            else if (working.length()==5 && before ==0) {
                String enteredMonth = working.substring(3);
                if (Integer.parseInt(enteredMonth) < 1 || Integer.parseInt(enteredMonth)>12) {
                    isValid = false;
                } else {
                    working+="/20";
                    mDateEntryField.setText(working);
                    mDateEntryField.setSelection(working.length());
                }
            }
            else if (working.length()==10 && before ==0) {
                String enteredYear = working.substring(6);
                //int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                //if (Integer.parseInt(enteredYear) < currentYear) {
                //     isValid = false;
                //}
                // else {
                isValid = true;
                ValidExpiryDate = true;
                //working+="/";
                mDateEntryField.setText(working);
                mDateEntryField.setSelection(working.length());
                //}
            } else if (working.length()!=10) {
                isValid = false;
            }

            if (!isValid) {
                mDateEntryField.setError("Enter a valid date: dd/MM/YYYY");
            } else {
                mDateEntryField.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

    private TextWatcher mPriceEntryWatcher = new TextWatcher() {

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String working = s.toString();
            boolean isValid = true;
            if (working.length()==0 && before ==0) {
                working+="£";
                mPriceEntryField.setText(working);
                mPriceEntryField.setSelection(working.length());


            } else if (working.length()!=0) {
                isValid = true;
                positiveAction.setEnabled(s.toString().trim().length() > 0);
            }

            if (!isValid) {
                mPriceEntryField.setError("Enter a valid price");
            } else {
                mPriceEntryField.setError(null);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {}

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    };

    private Long ConvertExpiryDate(String ExpiryDate)
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        formatter.setLenient(false);

        ExpiryDate = ExpiryDate + " 00:00";
        Date oldDate = null;
        try {
            oldDate = formatter.parse(ExpiryDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long oldMillis = oldDate.getTime();

        return oldMillis;
    }

    static final String[] COUNTRIES = new String[] {
            "Apple","Applesauce","Asparagus","BBQ sauce","Baby food","Baby formula","Bacon","Bagels","Baked beans","Baking chocolate",
            "Baking soda","Bananas","Basil","Bay leaves","Beer","Biscuits","Black pepper","Bread - french","Bread - wheat",
            "Bread - white", "Broccoli","Butter","Blue cheese","Chicken breast","Green beans","Hamburger buns","Hash browns","Hotdog buns","Kidney beans","peanut butter",
            "bell peppers","pinto beans","Refried beans","Roast beef","Cabbage","Cake mix","Candy","Sweets","Cantaloupe","Carrots","Cat food","Cauliflower","Cayenne pepper",
            "Celery","Cereal","Chocolate","Cheddar cheese","Mozzarella cheese","Ricotta cheese","Parmesan cheese","Camembert","Brie","Cherries","Gruyere cheese","Cheese spread",
            "Chicken strips","Whole chicken","Chili powder","Oven chips","Microwave chips","potatoes","Crisps","Chives","Cinnamon","Cocoa","Coconut","Coffee","Corn","Crackers","Cucumber",
            "Ice cream","Pork chops","Eggs","Dog food","Fish","Flour","Garlic","onion","Grapefruit","Ham","Hamburger","Honey","Jalapenos","Jam","Jello","Ketchup","Lasagna","Pasta","Spaghetti",
            "Lemon","Lime","Lemon juice","Lettuce","Tomato","Mayonnaise","Milk","Nectarines","Oranges","Pears","Noodles","Oil","Orange juice","Paprika","Parsley","Peaches","Peanuts","Peas",
            "Pineapple","Pizza","Popcorn","Pretzels","Raisins","Ravioli","Rice","Sugar","Mustard","Salt","Sausage","Shrimps","Crab","Soup","Steak","Thyme","Tomato paste","Waffles","Yogurt","Zucchini"
    };
}
