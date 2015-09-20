package tmedia_ltd.foodalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;

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
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table ItemDetails " +
                        "(id integer primary key, name text,expiry text,quantity text, price text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS ItemDetails");
        onCreate(db);
    }

    public boolean insertContact(String name, String expiry, String quantity, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", expiry);
        contentValues.put("email", quantity);
        contentValues.put("street", price);
        db.insert("ItemDetails", null, contentValues);
        return true;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String expiry, String quantity, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", expiry);
        contentValues.put("email", quantity);
        contentValues.put("street", price);
        db.update("ItemDetails", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("ItemDetails",
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<ItemArray> getAllContacts() {
        ArrayList<ItemArray> array_list = new ArrayList<ItemArray>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from ItemDetails", new String[] { CONTACTS_COLUMN_NAME });
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            ItemArray.AddItem(res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)));
            res.moveToNext();
        }
        return array_list;
    }
}
