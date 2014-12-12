package com.crankworks.trackingdatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcus on 12/12/14.
 */
public class TableCoordinates
{
    private static final String TAG = TableCoordinates.class.getSimpleName();

    static final String TABLE_NAME = "coordinates";

    private Database mDb;

    static void createTable(SQLiteDatabase db)
    {}

    TableCoordinates(Database database)
    {
        mDb = database;
    }
}
