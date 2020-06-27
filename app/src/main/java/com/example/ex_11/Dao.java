package com.example.ex_11;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Dao {

    private final DBHelper helper;
    private static final String TAG = "Dao";

    public Dao(Context context) {
        //创建数据库
        helper = new DBHelper(context);
        helper.getWritableDatabase();
    }
    public void insert(String tabName, ContentValues values){
        SQLiteDatabase db=helper.getWritableDatabase();
        db.insert(tabName,null,values);
        db.close();
    }
    public void delete(String tabName,int id){
        //String sql1="select * from "+tabName+" where title ="+title;
        String sql="delete from "+tabName + " where _id = "+id;
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
    public void update(String tabName ){
        String sql="update "+ tabName + " set author = 'jack' where title ='java'";
        SQLiteDatabase db=helper.getWritableDatabase();
        db.execSQL(sql);
        db.close();
    }
    public Cursor query(String tabName){
        SQLiteDatabase db=helper.getWritableDatabase();
        Cursor cursor = db.query(tabName,null, null, null,null,null,null);
        return cursor;
    }
}

