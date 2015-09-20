package tmedia_ltd.foodalarm;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by will on 9/16/2015.
 */

public class MySQLiteHelper extends SQLiteOpenHelper {
    public static final String COLUMN_ID = "_id";
    public static final String DATABASE_NAME = "FoodItems.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_FOODDETAILS = "FoodDetails";
    public static final String COLUMN_FOODDETAILS = "FoodDetail";

    //Database creation SQL statement
    private static final String DATABASE_CREATE = "create table FoodDetails ("
            + COLUMN_ID
            + " integer primary key autoincrement, FoodDetail text not null);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version "+ oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS FoodDetails");
        onCreate(db);
    }
}
