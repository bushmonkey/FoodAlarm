package tmedia_ltd.foodalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by will on 9/20/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "FoodItems.db";
    public static final String CONTACTS_TABLE_NAME = "ItemDetails";
    public static final String CONTACTS_COLUMN_ID = "_id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_EXPIRY = "expiry";
    public static final String CONTACTS_COLUMN_QUANTITY = "quantity";
    public static final String CONTACTS_COLUMN_PRICE = "price";
    public static final String CONTACTS_COLUMN_USED = "used";
    public static final String CONTACTS_COLUMN_USEDDATE = "usedDate";
    public static final String CONTACTS_COLUMN_USETYPE = "useType";
    public static final String CONTACTS_COLUMN_BARCODE= "barcode";
    private HashMap hp;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 6);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table ItemDetails " +
                        "(_id integer primary key autoincrement, name text,expiry int,quantity text, price int, used int, usedDate int, useType text, barcode text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ItemDetails");
        onCreate(db);
    }

    public boolean insertContact(String name, long expiry, String quantity, long price, String scanContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("expiry", expiry);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        contentValues.put("used", 0);
        contentValues.put("barcode", scanContent);
        db.insert("ItemDetails", null, contentValues);
        Log.d("values inserted:", contentValues.toString());
        return true;
    }

    public FoodItem getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where _id=" + id + "", null);
        if (res != null)
            res.moveToFirst();
        FoodItem foodItem = new FoodItem();
        foodItem.setId(Integer.parseInt(res.getString(0)));
        foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
        foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
        foodItem.setPrice(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
        foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));
        foodItem.setBarcode(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BARCODE)));
        return foodItem;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public long numberOfRowsForUseType(String useType) {
        long numRows = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        //Cursor res = db.rawQuery("select COUNT(_id) from ItemDetails where useType=" + useType + "", null);
        DatabaseUtils.queryNumEntries(db, "ItemDetails", "useType=?", new String[]{useType});

        numRows=DatabaseUtils.queryNumEntries(db, "ItemDetails", "useType=?", new String[]{useType});

        return numRows;
    }

    public List<FoodItem> getExpiredByDate(String useType)
    {
        List<FoodItem> foodItems = new LinkedList<FoodItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where used=1 and useType=?", new String[] { useType });
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem = new FoodItem();
                foodItem.setId(Integer.parseInt(res.getString(0)));
                foodItem.setUsageType(res.getString(res.getColumnIndex(CONTACTS_COLUMN_USETYPE)));
                foodItem.setUseDate(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_USEDDATE)));
                foodItem.setUseDate_Date(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_USEDDATE)));
                foodItem.setBarcode(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BARCODE)));
                foodItems.add(foodItem);
                Log.d("getExpiredByDate()", foodItem.toExpiredString());
            } while (res.moveToNext());
        }
        return foodItems;
    }

    public List<FoodItem> getByBarcode(String barcode)
    {
        Log.d("Getting barcode", barcode);
        List<FoodItem> foodItems = new LinkedList<FoodItem>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where barcode=? LIMIT 1", new String[] { barcode });
        //Cursor res = db.rawQuery("select * from ItemDetails", new String[] { barcode });
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem = new FoodItem();
                foodItem.setId(Integer.parseInt(res.getString(0)));
                foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
                foodItem.setPrice(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
                foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));
                foodItem.setBarcode(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BARCODE)));
                foodItems.add(foodItem);
                Log.d("getByBarcode()", foodItem.getName().toString());
            } while (res.moveToNext());
        }
        return foodItems;
    }

    public boolean updateContact(Integer id, String name, Long expiry, String quantity, Long price, String barcode) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("expiry", expiry);
        contentValues.put("quantity", quantity);
        contentValues.put("price", price);
        contentValues.put("barcode", barcode);
        db.update("ItemDetails", contentValues, "_id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public boolean ExpireItem(Integer id, String useType, Long usedDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("used", 1);
        contentValues.put("useType", useType);
        contentValues.put("usedDate", usedDate);
        db.update("ItemDetails", contentValues, "_id =" + id, null);


        Log.d("id to expire:", Integer.toString(id));

        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ItemDetails",
                "_id = ? ",
                new String[]{Integer.toString(id)});
    }

    public List<FoodItem> getAllContacts() {
        List<FoodItem> foodItems = new LinkedList<FoodItem>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where used<>1 order by expiry ASC", null);
        res.moveToFirst();

        // 3. go over each row, build book and add it to list
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem=new FoodItem();
                foodItem.setId(Integer.parseInt(res.getString(0)));
                foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
                foodItem.setPrice(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
                foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));
                foodItem.setBarcode(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BARCODE)));
                // Add book to books
                foodItems.add(foodItem);
            } while (res.moveToNext());
        }

        Log.d("getAllBooks()", foodItems.toString());

        return foodItems;
    }

    public List<FoodItem> getExpiringSoon(int days) {
        List<FoodItem> foodItems = new LinkedList<FoodItem>();
        Calendar NextWeek = Calendar.getInstance();
        NextWeek.add(Calendar.DAY_OF_MONTH, 5);
        Date oldMillis = NextWeek.getTime();
        Long SoonLong = oldMillis.getTime();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where expiry < "+ SoonLong + " and used<>1 order by expiry ASC", null);
        res.moveToFirst();

        // 3. go over each row, build book and add it to list
        FoodItem foodItem = null;
        if (res.moveToFirst()) {
            do {
                foodItem=new FoodItem();
                foodItem.setId(res.getInt(res.getColumnIndex(CONTACTS_COLUMN_ID)));
                foodItem.setName(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
                foodItem.setExpiry(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_EXPIRY)));
                foodItem.setPrice(res.getLong(res.getColumnIndex(CONTACTS_COLUMN_PRICE)));
                foodItem.setQuantity(res.getString(res.getColumnIndex(CONTACTS_COLUMN_QUANTITY)));
                foodItem.setBarcode(res.getString(res.getColumnIndex(CONTACTS_COLUMN_BARCODE)));
                // Add book to books
                foodItems.add(foodItem);
            } while (res.moveToNext());
        }
        Log.d("Expiry Long:", SoonLong.toString());
        Log.d("getExpiringSoon()", foodItems.toString());

        return foodItems;

    }
}
