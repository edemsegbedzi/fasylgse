package com.fasylgh.fasylgse.model;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.fasylgh.fasylgse.model.DBEntries.CHANGE;
import static com.fasylgh.fasylgse.model.DBEntries.DATABASE_VERSION;
import static com.fasylgh.fasylgse.model.DBEntries.DB_NAME;
import static com.fasylgh.fasylgse.model.DBEntries.ID;
import static com.fasylgh.fasylgse.model.DBEntries.NAME;
import static com.fasylgh.fasylgse.model.DBEntries.PRICE;
import static com.fasylgh.fasylgse.model.DBEntries.TABLE_NAME;
import static com.fasylgh.fasylgse.model.DBEntries.TIME_STAMP;
import static com.fasylgh.fasylgse.model.DBEntries.VOLUME;


/**
 * Created by edem on 12/27/16.
 */

public class SQLiteDataBase extends SQLiteOpenHelper {


    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    ID + " INTEGER PRIMARY KEY ," +
                    NAME + " TEXT," +
                    PRICE + " TEXT," +
                    CHANGE + " TEXT," +
                    VOLUME + " TEXT," +
                    TIME_STAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP" +

                    " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + TABLE_NAME;


    public SQLiteDataBase(Context context) {
        super(context, DB_NAME, null, DATABASE_VERSION);
    }

    public SQLiteDataBase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);

    }
}
