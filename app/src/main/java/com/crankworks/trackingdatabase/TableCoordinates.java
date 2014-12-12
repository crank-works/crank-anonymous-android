package com.crankworks.trackingdatabase;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by marcus on 12/12/14.
 */
public class TableCoordinates
{
    private static final String TAG = TableCoordinates.class.getSimpleName();

    static final String TABLE_NAME = "coordinates";

    public static final String FIELD_ID             = "_id";
    public static final String FIELD_TRIP_ID        = "trip_id";
    public static final String FIELD_ACCURACY       = "accuracy";
    public static final String FIELD_ALTITUDE       = "altitude";
    public static final String FIELD_BEARING        = "bearing";
    public static final String FIELD_ELAPSED_TIME   = "elapsed_time";
    public static final String FIELD_LATITUDE       = "latitude";
    public static final String FIELD_LONGITUDE      = "longitude";
    public static final String FIELD_PROVIDER       = "provider";
    public static final String FIELD_SPEED          = "speed";
    public static final String FIELD_TIME           = "time";

    private Database mDb;

    static void createTable(SQLiteDatabase db)
    {}

    TableCoordinates(Database database)
    {
        mDb = database;
    }
}
