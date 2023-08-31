package lk.media.com.mynote;

import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

        import java.util.ArrayList;

public class

MemoDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "memoDatabase";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "memo_table"; // テーブル名
    public static final String COL1 = "ID";               // IDカラム
    public static final String COL2 = "TITLE";            // タイトルカラム
    public static final String COL3 = "CONTENT";          // メモの内容カラム


    public MemoDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 +" TEXT)";
        db.execSQL(createTable);
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, content);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }

    public ArrayList<String> getAllMemos() {
        ArrayList<String> memoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                String title = cursor.getString(cursor.getColumnIndex(COL2));
                // String content = cursor.getString(cursor.getColumnIndex(COL3)); // If you need the content
                memoList.add(title); // You can also append content here if needed
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memoList;
    }


    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
