package com.crankworks.trackingdatabase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;

import org.json.JSONException;
import org.json.JSONObject;

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

    public static class Row
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

        Row(Cursor data)
        {
            _id             = data.getLong(     data.getColumnIndex(COLUMN_ID)              );
            start_time      = data.getLong(     data.getColumnIndex(COLUMN_START_TIME)      );
            end_time        = data.getLong(     data.getColumnIndex(COLUMN_END_TIME)        );
            objective       = data.getString(   data.getColumnIndex(COLUMN_OBJECTIVE)       );
            latitude_high   = data.getDouble(   data.getColumnIndex(COLUMN_LATITUDE_HIGH)   );
            latitude_low    = data.getDouble(   data.getColumnIndex(COLUMN_LATITUDE_LOW)    );
            longitude_high  = data.getDouble(   data.getColumnIndex(COLUMN_LONGITUDE_HIGH)  );
            longitude_low   = data.getDouble(   data.getColumnIndex(COLUMN_LONGITUDE_LOW)   );
            distance        = data.getDouble(   data.getColumnIndex(COLUMN_DISTANCE)        );
        }

        Row(Location location)
        {
            start_time = location.getTime();
            end_time = location.getTime();
            latitude_high = location.getLatitude();
            latitude_low = location.getLatitude();
            longitude_high = location.getLongitude();
            longitude_low = location.getLongitude();
        }

        void update(Location location, Location fromLocation)
        {
            double t;

            end_time = location.getTime();

            t = location.getLatitude();
            if (t < latitude_low)
                latitude_low = t;
            else if (t > latitude_high)
                latitude_high = t;

            t = location.getLongitude();
            if (t < longitude_low)
                longitude_low = t;
            else if (t > longitude_high)
                longitude_high = t;

            if (fromLocation != null)
                distance += location.distanceTo(fromLocation);
        }

        public ContentValues toContentValues()
        {
            ContentValues content = new ContentValues();
            content.put(COLUMN_START_TIME,      start_time);
            content.put(COLUMN_END_TIME,        end_time);
            content.put(COLUMN_OBJECTIVE,       objective);
            content.put(COLUMN_LATITUDE_HIGH,   latitude_high);
            content.put(COLUMN_LATITUDE_LOW,    latitude_low);
            content.put(COLUMN_LONGITUDE_HIGH,  longitude_high);
            content.put(COLUMN_LONGITUDE_LOW,   longitude_low);
            content.put(COLUMN_DISTANCE,        distance);
            return content;
        }

        public JSONObject toJson() throws JSONException
        {
            JSONObject json = new JSONObject();
            json.put(COLUMN_START_TIME,     start_time);
            json.put(COLUMN_END_TIME,       end_time);
            json.put(COLUMN_OBJECTIVE,      objective);
            json.put(COLUMN_LATITUDE_HIGH,  latitude_high);
            json.put(COLUMN_LATITUDE_LOW,   latitude_low);
            json.put(COLUMN_LONGITUDE_HIGH, longitude_high);
            json.put(COLUMN_LONGITUDE_LOW,  longitude_low);
            json.put(COLUMN_DISTANCE,       distance);
            return json;
        }
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

    static long addRow(SQLiteDatabase db, Row row)
    {
        return db.insert(TABLE_NAME, null, row.toContentValues());
    }

    static long updateRow(SQLiteDatabase db, Row row)
    {
        return db.update(TABLE_NAME, row.toContentValues(), COLUMN_ID + "=" + row._id, null);
    }

    static Cursor getCursor(SQLiteDatabase db)
    {
        Cursor cursor = db.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
}
