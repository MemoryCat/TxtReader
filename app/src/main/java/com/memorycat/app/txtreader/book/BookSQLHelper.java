package com.memorycat.app.txtreader.book;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by xie on 2017/1/25.
 */

public class BookSQLHelper extends SQLiteOpenHelper implements Serializable {
    public static final String DATABASE_NAME = "book.db";
    public static final String TABLE_NAME = "book";
    public static final String[] COLUMNS_ALL = new String[]{
            "_id", "bookContent", "bookName", "author", "addDate", "positionPointer", "filePath", "lastReadDate"
    };
    private static final String TAG = "BookSQLHelper";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CREATE =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    "_id  INTEGER PRIMARY KEY  AUTOINCREMENT, " +
                    "bookContent text," +
                    "bookName text," +
                    "author text," +
                    "addDate datetime," +
                    "positionPointer bigint," +
                    "filePath TEXT, " +
                    "lastReadDate datetime)";
    private final static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Context context;

    public BookSQLHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public static Book getBookFromCursor(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(0));
        book.setBookContent(cursor.getString(1));
        book.setBookName(cursor.getString(2));
        book.setAuthor(cursor.getString(3));
        book.setPositionPointer(cursor.getLong(5));
        book.setFilePath(cursor.getString(6));
        try {
            book.setAddDate(BookSQLHelper.simpleDateFormat.parse(cursor.getString(4)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            book.setLastReadDate(BookSQLHelper.simpleDateFormat.parse(cursor.getString(7)));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return book;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLE_CREATE);
    }

    public void add(Book book) {
        SQLiteDatabase writableDatabase = super.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("bookContent", book.getBookContent());
        contentValues.put("bookName", book.getBookName());
        contentValues.put("author", book.getAuthor());
        contentValues.put("addDate", simpleDateFormat.format(book.getAddDate()));
        contentValues.put("positionPointer", book.getPositionPointer());
        contentValues.put("filePath", book.getFilePath());
        contentValues.put("lastReadDate", simpleDateFormat.format(book.getLastReadDate()));

        long id = writableDatabase.insert(TABLE_NAME, "bookName", contentValues);
        Log.d(TAG, "add: " + id);

        writableDatabase.close();
    }

    public List<Book> getAllBooks() {
        List<Book> ret = new LinkedList<Book>();
        SQLiteDatabase readableDatabase = super.getReadableDatabase();
        Cursor cursor = readableDatabase.rawQuery("select * from " + TABLE_NAME, null);
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                ret.add(BookSQLHelper.getBookFromCursor(cursor));
            }
        }
        return ret;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade() called with: sqLiteDatabase = [" + sqLiteDatabase + "], oldVersion = [" + oldVersion + "], newVersion = [" + newVersion + "]");
    }
}
