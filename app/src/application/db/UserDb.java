package com.example.matt.myapplication.db;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matt.myapplication.model.User;

public class UserDb {
    //Reference From Class Notes

    private SQLiteDatabase database;

    private SQLiteOpenHelper openHelper;

    //database version number
    private static final int DATABASE_VERSION = 1;

    //database name recognized as UserInfo
    private static final String DATABASE_NAME = "UserInfo.db";

    //database table name
    private static final String TABLE_USER = "user";

    //database column names
    private static final String COLUMN_USER_USERNAME = "user_username";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_ID = "user_id";

    //creating the database
    private static final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + " (" +
            COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_USER_USERNAME + " TEXT, " +
            COLUMN_USER_EMAIL + " TEXT, " + COLUMN_USER_PASSWORD + " TEXT)";

    public UserDb(Context context) {
        openHelper = new DBHelper(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_USER_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
            onCreate(db);
        }

    }

    //called when need to add user to the database
    public void addUser(User user) {
        database = openHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_USERNAME, user.getUsername());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        values.put(COLUMN_USER_EMAIL, user.getEmail());

        //insert the new rows
        database.insert(TABLE_USER, null, values);
        database.close();
    }

    //user sign in checker
    public void userSignIn(String username, String password) {
        String[] cols = {COLUMN_USER_USERNAME, COLUMN_USER_PASSWORD};
        database = openHelper.getWritableDatabase();
        String sel = "user_username = ? AND user_password = ?";
        String[] selArgs = {username, password};
        Cursor cr = database.query(TABLE_USER, cols, sel, selArgs, null, null, null);
        cr.close();
        database.close();
    }
}
