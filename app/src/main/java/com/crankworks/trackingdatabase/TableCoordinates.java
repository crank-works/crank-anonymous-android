package com.crankworks.trackingdatabase;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

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
    public static final String COLUMN_LATITUDE       = "latitude";
    public static final String COLUMN_LONGITUDE      = "longitude";
    public static final String COLUMN_PROVIDER       = "provider";
    public static final String COLUMN_SPEED          = "speed";
    public static final String COLUMN_TIME           = "time";

    static class Row
    {
        public long      _id;
        public long      trip_id;
        public double    accuracy;
        public double    altitude;
        public double    bearing;
        public double    latitude;
        public double    longitude;
        public String    provider;
        public double    speed;
        public long      time;

        public Row(TableTrips.Row trip, Location location)
        {
            trip_id     = trip._id;
            accuracy    = location.getAccuracy();
            altitude    = location.getAltitude();
            bearing     = location.getBearing();
            latitude    = location.getLatitude();
            longitude   = location.getLongitude();
            provider    = location.getProvider();
            speed       = location.getSpeed();
            time        = location.getTime();
        }
    }

    static void createTable(SQLiteDatabase db)
    {
        final String sqlString = "create table " + TABLE_NAME + " ("
                + COLUMN_ID         + " integer primary key autoincrement, "
                + COLUMN_TRIP_ID    + " integer, "
                + COLUMN_ACCURACY   + " double, "
                + COLUMN_ALTITUDE   + " double, "
                + COLUMN_BEARING    + " double, "
                + COLUMN_LATITUDE   + " double, "
                + COLUMN_LONGITUDE  + " double, "
                + COLUMN_PROVIDER   + " text, "
                + COLUMN_SPEED      + " double, "
                + COLUMN_TIME       + " integer)";

        db.execSQL(sqlString);
    }

    static long addPosition(SQLiteDatabase mDb, Row row)
    {
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_TRIP_ID, row.trip_id);
        rowValues.put(COLUMN_ACCURACY, row.accuracy);
        rowValues.put(COLUMN_ALTITUDE, row.altitude);
        rowValues.put(COLUMN_BEARING, row.bearing);
        rowValues.put(COLUMN_LATITUDE, row.latitude);
        rowValues.put(COLUMN_LONGITUDE, row.longitude);
        rowValues.put(COLUMN_PROVIDER, row.provider);
        rowValues.put(COLUMN_SPEED, row.speed);
        rowValues.put(COLUMN_TIME, row.time);

        return mDb.insert(TABLE_NAME, null, rowValues);
    }
}
