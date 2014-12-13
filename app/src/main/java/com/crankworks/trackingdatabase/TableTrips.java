package com.crankworks.trackingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

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

    static class Row
    {
        public long      _id;
        public long      start_time;
        public long      end_time;
        public String    objective;
        public double    latitude_high;
        public double    latitude_low;
        public double    longitude_high;
        public double    longitude_low;
        public double    distance;
    }

    static void createTable(SQLiteDatabase db)
    {
        final String sqlString = "create table " + TABLE_NAME + " ("
                + COLUMN_ID             + " integer primary key autoincrement, "
                + COLUMN_START_TIME     + " integer, "
                + COLUMN_END_TIME       + " integer, "
                + COLUMN_OBJECTIVE      + " text, "
                + COLUMN_LATITUDE_HIGH  + " double, "
                + COLUMN_LATITUDE_LOW   + " double, "
                + COLUMN_LONGITUDE_HIGH + " double, "
                + COLUMN_LONGITUDE_LOW  + " double, "
                + COLUMN_DISTANCE       + " double)";

        db.execSQL(sqlString);
    }

    static boolean addRow(SQLiteDatabase mDb, Row row)
    {
        boolean success = true;

        // Add the latest point
        ContentValues rowValues = new ContentValues();
        rowValues.put(COLUMN_START_TIME, row.start_time);
        rowValues.put(COLUMN_END_TIME, row.end_time);
        rowValues.put(COLUMN_OBJECTIVE, row.objective);
        rowValues.put(COLUMN_LATITUDE_HIGH, row.latitude_high);
        rowValues.put(COLUMN_LATITUDE_LOW, row.latitude_low);
        rowValues.put(COLUMN_LONGITUDE_HIGH, row.longitude_high);
        rowValues.put(COLUMN_LONGITUDE_LOW, row.longitude_low);
        rowValues.put(COLUMN_DISTANCE, row.distance);

        success = success && (mDb.insert(TABLE_NAME, null, rowValues) > 0);

        return success;
    }
}
