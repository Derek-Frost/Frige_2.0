package com.example.frige_20.DB;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.io.IOException;

public class Database {

    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;

    public Database(Context context) {
        mDBHelper = new DatabaseHelper(context);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }

    public void insertDB(String table, String columns, ContentValues values){
        mDb.insert(table, columns, values);
    }

    public void deleteDB(String table, String whereClause, String[] whereArgs){
        mDb.delete(table, whereClause, whereArgs);
    }

    @SuppressLint("Recycle")
    public void rawQueryDB(String sql, String[] selectionArgs, Cursor cursor){
        cursor = mDb.rawQuery(sql, selectionArgs);
    }

    public void updateDB(String table, ContentValues values, String whereClause, String[] whereArgs){
        mDb.update(table, values, whereClause, whereArgs);
    }

}
