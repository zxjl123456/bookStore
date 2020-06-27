package com.example.ex_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG ="DBHelper";
    private SQLiteDatabase db;
    private static final String DB_NAME ="bookStore.db";
    public  static  final  String CREATE_TAB = "create table bookTab (" +
            "_id integer primary key autoincrement, " +
            "title text, " +
            "author text, " +
            "summary text)";
    DBHelper(@Nullable Context context) {
        super(context,DB_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db=db;
        db.execSQL(CREATE_TAB);
        Log.d(TAG,"创建book表");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
