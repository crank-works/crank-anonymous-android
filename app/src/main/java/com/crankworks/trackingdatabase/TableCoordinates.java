package com.crankworks.trackingdatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcus on 12/12/14.
 */
public class TableCoordinates
{
    private static final String TAG = TableCoordinates.class.getSimpleName();

    static final String TABLE_NAME = "coordinates";

    public static final String COLUMN_ID             = "_id";
    public static final String COLUMN_TRIP_ID        = "trip_id";
    public static final String COLUMN_ACCURACY       = "accuracy";
    public static final String COLUMN_ALTITUDE       = "altitude";
    public static final String COLUMN_BEARING        = "bearing";
    public static final String COLUMN_ELAPSED_TIME   = "elapsed_time";
    public static final String COLUMN_LATITUDE       = "latitude";
    public static final String COLUMN_LONGITUDE      = "longitude";
    public static final String COLUMN_PROVIDER       = "provider";
    public static final String COLUMN_SPEED          = "speed";
    public static final String COLUMN_TIME           = "time";

    private Database mDb;

    static void createTable(SQLiteDatabase db)
    {}

    TableCoordinates(Database database)
    {
        mDb = database;
    }
}
