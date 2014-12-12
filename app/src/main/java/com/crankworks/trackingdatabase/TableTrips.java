package com.crankworks.trackingdatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcus on 12/12/14.
 */
public class TableTrips
{
    private static final String TAG = TableTrips.class.getSimpleName();

    static final String TABLE_NAME = "trips";

    private Database mDb;

    static void createTable(SQLiteDatabase db)
    {}

    TableTrips(Database database)
    {
        mDb = database;
    }
}
