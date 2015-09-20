package tmedia_ltd.foodalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by will on 9/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodItems.db";
    public static final String CONTACTS_TABLE_NAME = "ItemDetails";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EXPIRY = "expiry";
    public static final String CONTACTS_COLUMN_QUANTITY = "quantity";
    public static final String CONTACTS_COLUMN_PRICE = "price";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table ItemDetails " +
                        "(id integer primary key, name text,expiry int,quantity text, price text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ItemDetails");
        onCreate(db);
    }

    public boolean insertContact(String name, long expiry, String quantity, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("expiry", expiry);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        db.insert("ItemDetails", null, contentValues);
        Log.d("values inserted:", contentValues.toString());
        return true;
    }

    public FoodItem getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where id=" + id + "", null);
        if (res != null)
            res.moveToFirst();
        FoodItem foodItem = new FoodItem();
        foodItem.setId(Integer.parseInt(res.getString(0)));
        foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
        foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
        foodItem.setPrice(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
        foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));

        return foodItem;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, Long expiry, String quantity, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("expiry", expiry);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        db.update("ItemDetails", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ItemDetails",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public List<FoodItem> getAllContacts() {
        List<FoodItem> foodItems = new LinkedList<FoodItem>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails", null);
        res.moveToFirst();

        // 3. go over each row, build book and add it to list
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem=new FoodItem();
                foodItem.setId(Integer.parseInt(res.getString(0)));
                foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
                foodItem.setPrice(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
                foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));

                // Add book to books
                foodItems.add(foodItem);
            } while (res.moveToNext());
        }

        Log.d("getAllBooks()", foodItems.toString());

        return foodItems;
    }

    public List<FoodItem> getExpiringSoon() {
        List<FoodItem> foodItems = new LinkedList<FoodItem>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails", null);
        res.moveToFirst();

        // 3. go over each row, build book and add it to list
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem=new FoodItem();
                foodItem.setId(Integer.parseInt(res.getString(0)));
                foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
                foodItem.setPrice(res.getString(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
                foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));

                // Add book to books
                foodItems.add(foodItem);
            } while (res.moveToNext());
        }

        Log.d("getAllBooks()", foodItems.toString());

        return foodItems;
    }
}
