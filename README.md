# FoodAlarm
Android Waste app

public class Login extends Activity {

@Override
public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login_screen);

    Button submit=(Button)findViewById(R.id.submit);
    submit.setOnClickListener(onSubmit);
}

private View.OnClickListener onSubmit=new View.OnClickListener() {

    @Override
    public void onClick(View view) {
        Intent myIntent=new Intent(view.getContext(),ListDeals.class );
        startActivityForResult(myIntent,0);

    }
};

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.login_screen, menu);
    return true;
}

@Override
public void onActivityResult(int requestCode,int resultCode, Intent data) {
    if(resultCode==2) {
        finish();
    } 
}

}
