package com.crankworks.trackingdatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcus on 12/12/14.
 */
public class TableTrips
{
    private static final String TAG = TableTrips.class.getSimpleName();

    static final String TABLE_NAME = "trips";

    public static final String COLUMN_ID             = "_id";
    public static final String COLUMN_START_TIME     = "start_time";
    public static final String COLUMN_END_TIME       = "end_time";
    public static final String COLUMN_OBJECTIVE      = "objective";
    public static final String COLUMN_LATITUDE_HIGH  = "latitude_high";
    public static final String COLUMN_LATITUDE_LOW   = "latitude_low";
    public static final String COLUMN_LONGITUDE_HIGH = "longitude_high";
    public static final String COLUMN_LONGITUDE_LOW  = "longitude_low";
    public static final String COLUMN_DISTANCE       = "distance";

    private Database mDb;

    static void createTable(SQLiteDatabase db)
    {}

    TableTrips(Database database)
    {
        mDb = database;
    }
}
