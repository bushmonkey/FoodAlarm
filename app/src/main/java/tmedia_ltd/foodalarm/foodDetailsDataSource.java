package tmedia_ltd.foodalarm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by will on 9/16/2015.
 */
public class foodDetailsDataSource {

    //database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
        "FoodDetail"};

    public foodDetailsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public FoodTable_SQLlite createFoodTable(String comment) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_FOODDETAILS, comment);
        long insertId = database.insert(MySQLiteHelper.TABLE_FOODDETAILS, null,
                values);
        Cursor cursor = database.query(MySQLiteHelper.TABLE_FOODDETAILS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FoodTable_SQLlite newComment = cursorToFoodTable(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteFoodDetail(FoodTable_SQLlite comment) {
        long id = comment.getId();
        System.out.println("Food item deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_FOODDETAILS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public ArrayList<FoodTable_SQLlite> getAllComments() {
        ArrayList<FoodTable_SQLlite> comments = new ArrayList<FoodTable_SQLlite>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_FOODDETAILS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FoodTable_SQLlite comment = cursorToFoodTable(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private FoodTable_SQLlite cursorToFoodTable(Cursor cursor) {
        FoodTable_SQLlite comment = new FoodTable_SQLlite();
        comment.setId(cursor.getLong(0));
        comment.setFoodDetail(cursor.getString(1));
        return comment;
    }
}


