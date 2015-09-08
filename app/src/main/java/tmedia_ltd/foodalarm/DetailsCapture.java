package tmedia_ltd.foodalarm;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class DetailsCapture extends AppCompatActivity implements View.OnClickListener {
    TextView mainTextView;
    Button saveButton;
    Button newItemButton;
    EditText productNameTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_capture);
        // mainTextView = (TextView) findViewById(R.id.InfoText);
        saveButton = (Button) findViewById(R.id.UpdateDetailsBtn);
        saveButton.setOnClickListener(this);
        newItemButton = (Button) findViewById(R.id.NewBtn);
        newItemButton.setOnClickListener(this);
        productNameTxt = (EditText) findViewById(R.id.ProductNameText);
    }


    @Override
    public void onClick(View v) {
        String ProductText = productNameTxt.toString();
        ArrayList<ItemArray> ItemList = new ArrayList<ItemArray>();
        ItemList.add(new ItemArray(ProductText, "San Diego"));
        ItemArray.AddItem(ItemList);
        mainTextView.setText("Item saved");
    }
}
