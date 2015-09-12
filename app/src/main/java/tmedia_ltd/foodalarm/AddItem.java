package tmedia_ltd.foodalarm;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;

import java.util.Date;

public class AddItem extends Activity implements OnClickListener {
    TextView mainTextView;
    Button saveButton;
    Button newItemButton;
    Button scanBtn;
    String productNameTxt = new String();
    Date ExpiryDateValue = new Date();
    EditText ProductNameEt;
    EditText mDateEntryField;
    EditText mPriceEntryField;
    IntentIntegrator scanIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        scanIntegrator = new IntentIntegrator(this);

        // mainTextView = (TextView) findViewById(R.id.InfoText)
        newItemButton = (Button) findViewById(R.id.NewBtn);
        //newItemButton.setOnClickListener(this);
        ProductNameEt = (EditText) findViewById(R.id.ProductNameText);
        mDateEntryField = (EditText) findViewById(R.id.ExpiryDatePicker);
        mDateEntryField.addTextChangedListener(mDateEntryWatcher);

        mPriceEntryField = (EditText) findViewById(R.id.ProductPriceText);
        mPriceEntryField.addTextChangedListener(mPriceEntryWatcher);

        saveButton = (Button) findViewById(R.id.UpdateDetailsBtn);
        saveButton.setOnClickListener(this);

        scanBtn = (Button)findViewById(R.id.button);
        scanBtn.setOnClickListener(this);

        ImageView ivPencil= (ImageView) findViewById(R.id.PrdImg);
        ivPencil.setImageResource(R.drawable.pencil);

        ImageView ivQuantity= (ImageView) findViewById(R.id.QuantImg);
        ivQuantity.setImageResource(R.drawable.qut);

        ImageView ivCal= (ImageView) findViewById(R.id.CalendarImg);
        ivCal.setImageResource(R.drawable.calendar);

        ImageView ivPrice= (ImageView) findViewById(R.id.PriceImg);
        ivPrice.setImageResource(R.drawable.price);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_item, menu);
        return true;
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void BackToSummary (View v) {
        Intent myIntent=new Intent(v.getContext(),FoodSummary.class );
        startActivityForResult(myIntent,0);
    }

    public void NewItem (View v) {
        Intent myIntent = new Intent(v.getContext(), AddItem.class);
        startActivityForResult(myIntent, 0);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.UpdateDetailsBtn){
            //Date ExpiryDatePickerText = ExpiryDatePickerEt.();
            String ProductText = ProductNameEt.getText().toString();
            ItemArray.AddItem(ProductText);
            Toast.makeText(
                    getApplicationContext(),
                    "Item saved", Toast.LENGTH_SHORT)
                    .show();

        }

        if(v.getId()==R.id.button){
        //scan
        scanIntegrator.initiateScan();

        }
    }
}
