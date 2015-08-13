package activity3.scino.com.scinosqlite.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Created by Name on 10/08/15.
 */
public class BookTable {

    private static final String TAG = BookTable.class.toString();

    public static final String TABLE_BOOK = "book";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";

    private static final String DATABASE_CREATE = "create table "
            + TABLE_BOOK
            + "("
            + COLUMN_ID + " integer primary key autoincrement,"
            + COLUMN_NAME + " text not null"
            + ");";

    public static void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE);
    }

    public static void onUpgrate(SQLiteDatabase database, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade database to newVersion =" + newVersion + " from oldVersion =" + oldVersion);

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_BOOK);
        onCreate(database);
    }
}
