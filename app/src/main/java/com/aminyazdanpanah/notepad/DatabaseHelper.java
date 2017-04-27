package com.aminyazdanpanah.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Amin Yazdanpanah. -> LinkedIn.com/aminyazdanpanah
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "notepad.db";
    private static final String TABLE_NAME = "notepad";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FILENAME = "filename";
    private static final String COLUMN_PATH = "path";
    SQLiteDatabase db;
    private static final String TABLE_CREATE = "create table notepad (id integer primary key not null , " +
            "filename text not null , path text not null );";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
        this.db = db;
    }

    public void insertFile(FileName c) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String query = "select * from notepad";
        Cursor cursor = db.rawQuery(query , null);
        int count = cursor.getCount();
        values.put(COLUMN_ID , count);
        values.put(COLUMN_FILENAME, c.getFilename());
        values.put(COLUMN_PATH, c.getPath());
        db.insert(TABLE_NAME, null, values);
        db.close();
    }


    public Cursor getListContents(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor data = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        return data;
    }

    public boolean deleteText(int id)
    {
        db = this.getWritableDatabase();
        return db.delete(TABLE_NAME, COLUMN_ID + "=" + id, null) > 0;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String query = "DROP TABLE IF EXISTS "+TABLE_NAME;
        db.execSQL(query);
        this.onCreate(db);
    }

}
