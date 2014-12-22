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
    public static final String COLUMN_ALTITUDE_HIGH  = "altitude_high";
    public static final String COLUMN_ALTITUDE_LOW   = "altitude_low";
    public static final String COLUMN_TOTAL_CLIMB    = "total_climb";
    public static final String COLUMN_DISTANCE       = "distance";
    public static final String COLUMN_TOP_SPEED      = "top_speed";
    public static final String COLUMN_UPLOADED       = "uploaded";

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
        public double    altitude_high;
        public double    altitude_low;
        public double    total_climb;
        public double    distance;
        public double    top_speed;
        public boolean   uploaded;

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
            altitude_high   = data.getDouble(   data.getColumnIndex(COLUMN_ALTITUDE_HIGH)   );
            altitude_low    = data.getDouble(   data.getColumnIndex(COLUMN_ALTITUDE_LOW)    );
            total_climb     = data.getDouble(   data.getColumnIndex(COLUMN_TOTAL_CLIMB)     );
            distance        = data.getDouble(   data.getColumnIndex(COLUMN_DISTANCE)        );
            top_speed       = data.getDouble(   data.getColumnIndex(COLUMN_TOP_SPEED)       );
            uploaded        = data.getInt(      data.getColumnIndex(COLUMN_UPLOADED)        ) != 0;
        }

        Row(Location location)
        {
            start_time = location.getTime();
            end_time = location.getTime();
            latitude_high = location.getLatitude();
            latitude_low = location.getLatitude();
            longitude_high = location.getLongitude();
            longitude_low = location.getLongitude();
            altitude_high = location.getAltitude();
            altitude_low = location.getAltitude();
            total_climb = 0.0;
            distance = 0.0;
            top_speed = location.getSpeed();
            uploaded = false;
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

            t = location.getAltitude();
            if (t < altitude_low)
                altitude_low = t;
            else if (t > altitude_high)
                altitude_high = t;

            t = location.getSpeed();
            if (t > top_speed)
                top_speed = t;

            if (fromLocation != null)
            {
                distance += location.distanceTo(fromLocation);
                double alt_diff = location.getAltitude() - fromLocation.getAltitude();
                if (alt_diff > 0)
                    total_climb += alt_diff;
            }
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
            content.put(COLUMN_ALTITUDE_HIGH,   altitude_high);
            content.put(COLUMN_ALTITUDE_LOW,    altitude_low);
            content.put(COLUMN_TOTAL_CLIMB,     total_climb);
            content.put(COLUMN_DISTANCE,        distance);
            content.put(COLUMN_TOP_SPEED,       top_speed);
            content.put(COLUMN_UPLOADED,        uploaded ? 1 : 0);
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
            json.put(COLUMN_ALTITUDE_HIGH,  altitude_high);
            json.put(COLUMN_ALTITUDE_LOW,   altitude_low);
            json.put(COLUMN_TOTAL_CLIMB,    total_climb);
            json.put(COLUMN_DISTANCE,       distance);
            json.put(COLUMN_TOP_SPEED,      top_speed);
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
                + COLUMN_ALTITUDE_HIGH  + " double, "
                + COLUMN_ALTITUDE_LOW   + " double, "
                + COLUMN_TOTAL_CLIMB    + " double, "
                + COLUMN_DISTANCE       + " double, "
                + COLUMN_TOP_SPEED      + " double, "
                + COLUMN_UPLOADED       + " integer)";

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
        return db.query(TABLE_NAME, null, null, null, null, null, null);
    }

    public static int totalDistance(SQLiteDatabase db)
    {
        final Cursor cursor = db.rawQuery("SELECT SUM(" + COLUMN_DISTANCE + ") as " + COLUMN_DISTANCE + " FROM " + TABLE_NAME + ";", null);
        int sum = 0;

        if (cursor != null)
        {
            try
            {
                if (cursor.moveToFirst())
                    sum = cursor.getInt(0);
            }

            finally
            {
                cursor.close();
            }
        }

        return sum;
    }

    public static long setUploaded(SQLiteDatabase db, int row_id, boolean flag)
    {
        ContentValues content = new ContentValues();
        content.put(COLUMN_UPLOADED, flag ? 1 : 0);
        return db.update(TABLE_NAME, content, COLUMN_ID + "=" + row_id, null);
    }

}
