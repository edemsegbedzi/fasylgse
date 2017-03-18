package com.fasylgh.fasylgse.model;

import android.provider.BaseColumns;

/**
 * Created by edem on 12/27/16.
 */

public class DBEntries implements BaseColumns {

    public static final String DB_NAME = "fassyl_storks";
    public static final String TABLE_NAME = "storks";
    public static final String NAME = "name";
    public static final String CHANGE = "change";
    public static final String PRICE = "price";
    public static final String VOLUME = "volume";
    public static final String TIME_STAMP = "timeStamp";
    public static final String ID = "id";
    public static final int DATABASE_VERSION = 1;

    private DBEntries() {
    }
}
