package com.example.matt.myapplication.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.matt.myapplication.model.Transaction;

import java.util.ArrayList;

//Transaction Database for added transactions on the app
public class TransactionDb {

    private SQLiteDatabase database;

    private SQLiteOpenHelper openHelper;

    public static final String DB_NAME = "transactions.db";
    //database version
    public static final int DB_VERSION = 1;

    //database table name
    public static final String TRANSACTIONS_TABLE = "Transactions";

    //database columns specification

    public static final String ITEM = "item";
    public static final int ITEM_COLUMN = 0;

    public static final String COST = "cost";
    public static final int COST_COLUMN = 1;

    public static final String DATE = "date";
    public static final int DATE_COLUMN = 2;

    public static final String SELLER = "seller";
    public static final int SELLER_COLUMN = 3;

    public static final String PAYMENT = "payment";
    public static final int PAYMENT_COLUMN = 4;


    //database format to avoid mistakes in database calling
    private static final String FORMAT = "CREATE TABLE %s (%s TEXT, %s TEXT, %s TEXT, %s TEXT, %s TEXT)";

    public static final String CREATE_TRANSACTIONS_TABLE =
            String.format(FORMAT, TRANSACTIONS_TABLE, ITEM, COST, DATE, SELLER, PAYMENT);

    public TransactionDb(Context context) {

        openHelper = new DBHelper(context, DB_NAME, null, DB_VERSION);
    }

    private static class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name,
                        SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(CREATE_TRANSACTIONS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS " + TRANSACTIONS_TABLE);
            onCreate(db);

        }

    }

    //saves a transaction to the database
    public void saveTransaction(Transaction transaction) {

        database = openHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(ITEM, transaction.getItem());
        cv.put(COST, transaction.getCost());
        cv.put(DATE, transaction.getDate());
        cv.put(SELLER, transaction.getSeller());
        cv.put(PAYMENT, transaction.getPayment());

        database.insert(TRANSACTIONS_TABLE, null, cv);

        database.close();

    }

    //gets all transactions and returns in an array list of transactions
    public ArrayList<Transaction> getAllTransactions() {

        database = openHelper.getReadableDatabase();

        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor cursor = database.query(
                TRANSACTIONS_TABLE,
                null, null, null,
                null, null, DATE);

        while (cursor.moveToNext()) {
            String item = cursor.getString(cursor.getColumnIndex(ITEM));
            String cost = cursor.getString(cursor.getColumnIndex(COST));
            String date = cursor.getString(cursor.getColumnIndex(DATE));
            String seller = cursor.getString(cursor.getColumnIndex(SELLER));
            String payment = cursor.getString(cursor.getColumnIndex(PAYMENT));

            transactions.add(new Transaction(item, cost, date, seller, payment));
        }

        //clean up
        cursor.close();
        database.close();
        return transactions;

    }


    //clears the database
    public int deleteAllTransactions() {
        database = openHelper.getWritableDatabase();

        int number = database.delete(TRANSACTIONS_TABLE, null, null);

        database.close();
        return number;
    }

    //returns a total of all spending done
    public float getAllSpending() {
        database = openHelper.getWritableDatabase();
        float total = 0;

        Cursor cursor = database.query(TRANSACTIONS_TABLE, null, null, null,
                null, null, COST);

        while (cursor.moveToNext()) {
            total += Float.parseFloat(cursor.getString(cursor.getColumnIndex(COST)));
        }

        cursor.close();
        database.close();
        return total;
    }

    //returns a total of all spending done with a particular payment method
    public float getSpendingByPayment(String payment) {
        database = openHelper.getWritableDatabase();
        float total = 0;
        String selection = "payment = ?";
        String[] selectionArgs = new String[]{payment};


        Cursor cursor = database.query(TRANSACTIONS_TABLE, null, selection, selectionArgs,
                null, null, COST);

        while (cursor.moveToNext()) {
            total += Float.parseFloat(cursor.getString(cursor.getColumnIndex(COST)));
        }

        cursor.close();
        database.close();
        return total;
    }

}
