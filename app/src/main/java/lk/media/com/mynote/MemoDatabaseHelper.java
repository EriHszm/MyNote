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

    /*** @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + TABLE_MEMOS + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_TITLE + " TEXT, " +
                COLUMN_CONTENT + " TEXT)";
/*
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEMO + " TEXT)";

        db.execSQL(createTable);
    }
    ***/

    @Override
    /*追加コード　ここから*/
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 +" TEXT, " + COL3 +" TEXT)";
        db.execSQL(createTable);
    }
    /*追加コード　ここまで*/

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }
    /*追加コード　ここから*/
    public boolean addData(String title, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, title);
        contentValues.put(COL3, content);

        long result = db.insert(TABLE_NAME, null, contentValues);

        return result != -1;
    }
    /*追加コード　ここまで*/

    public ArrayList<String> getAllMemos() {
        ArrayList<String> memoList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                memoList.add(cursor.getString(cursor.getColumnIndex(COL3)));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return memoList;
    }

    public void addMemo(String memo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL3, memo);
        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public Cursor getData() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

}
