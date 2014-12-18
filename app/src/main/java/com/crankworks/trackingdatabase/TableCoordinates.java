package com.crankworks.trackingdatabase;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by marcus on 12/12/14.
 */
public class TableCoordinates
{
    private static final String TAG = TableCoordinates.class.getSimpleName();

    static final String TABLE_NAME = "coordinates";

    public static final String COLUMN_ID             = "_id";
    public static final String COLUMN_TRIP_ID        = "trip_id";
    public static final String COLUMN_SEQUENCE       = "sequence";
    public static final String COLUMN_ACCURACY       = "accuracy";
    public static final String COLUMN_ALTITUDE       = "altitude";
    public static final String COLUMN_BEARING        = "bearing";
    public static final String COLUMN_LATITUDE       = "latitude";
    public static final String COLUMN_LONGITUDE      = "longitude";
    public static final String COLUMN_PROVIDER       = "provider";
    public static final String COLUMN_SPEED          = "speed";
    public static final String COLUMN_TIME           = "time";

    public static class Row
    {
        public long      _id;
        public long      trip_id;
        public long      sequence;
        public double    accuracy;
        public double    altitude;
        public double    bearing;
        public double    latitude;
        public double    longitude;
        public String    provider;
        public double    speed;
        public long      time;

        private long mSequence = 0;

        public Row(Cursor cursor)
        {
            _id         = cursor.getLong(   cursor.getColumnIndex(COLUMN_ID)        );
            trip_id     = cursor.getLong(   cursor.getColumnIndex(COLUMN_TRIP_ID)   );
            sequence    = cursor.getLong(   cursor.getColumnIndex(COLUMN_SEQUENCE)  );
            accuracy    = cursor.getDouble( cursor.getColumnIndex(COLUMN_ACCURACY)  );
            altitude    = cursor.getDouble( cursor.getColumnIndex(COLUMN_ALTITUDE)  );
            bearing     = cursor.getDouble( cursor.getColumnIndex(COLUMN_BEARING)   );
            latitude    = cursor.getDouble( cursor.getColumnIndex(COLUMN_LATITUDE)  );
            longitude   = cursor.getDouble( cursor.getColumnIndex(COLUMN_LONGITUDE) );
            provider    = cursor.getString( cursor.getColumnIndex(COLUMN_PROVIDER)  );
            speed       = cursor.getDouble( cursor.getColumnIndex(COLUMN_SPEED)     );
            time        = cursor.getLong(   cursor.getColumnIndex(COLUMN_TIME)      );
        }

        public Row(TableTrips.Row trip, Location location)
        {
            trip_id     = trip._id;
            sequence    = ++mSequence;
            accuracy    = location.getAccuracy();
            altitude    = location.getAltitude();
            bearing     = location.getBearing();
            latitude    = location.getLatitude();
            longitude   = location.getLongitude();
            provider    = location.getProvider();
            speed       = location.getSpeed();
            time        = location.getTime();
        }
        
        public ContentValues toContentValues()
        {
            ContentValues content = new ContentValues();
            content.put(COLUMN_TRIP_ID,     trip_id);
            content.put(COLUMN_ACCURACY,    accuracy);
            content.put(COLUMN_ALTITUDE,    altitude);
            content.put(COLUMN_BEARING,     bearing);
            content.put(COLUMN_LATITUDE,    latitude);
            content.put(COLUMN_LONGITUDE,   longitude);
            content.put(COLUMN_PROVIDER,    provider);
            content.put(COLUMN_SPEED,       speed);
            content.put(COLUMN_TIME,        time);
            return content;
        }

        public JSONObject toJson() throws JSONException
        {
            JSONObject json = new JSONObject();
            json.put(COLUMN_TRIP_ID,    trip_id);
            json.put(COLUMN_SEQUENCE,   sequence);
            json.put(COLUMN_ACCURACY,   accuracy);
            json.put(COLUMN_ALTITUDE,   altitude);
            json.put(COLUMN_BEARING,    bearing);
            json.put(COLUMN_LATITUDE,   latitude);
            json.put(COLUMN_LONGITUDE,  longitude);
            json.put(COLUMN_PROVIDER,   provider);
            json.put(COLUMN_SPEED,      speed);
            json.put(COLUMN_TIME,       time);
            return json;
        }
    }

    static void createTable(SQLiteDatabase db)
    {
        final String sqlString = "create table " + TABLE_NAME + " ("
                + COLUMN_ID         + " integer primary key autoincrement, "
                + COLUMN_TRIP_ID    + " integer, "
                + COLUMN_SEQUENCE   + " integer, "
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
        return mDb.insert(TABLE_NAME, null, row.toContentValues());
    }

    static Cursor getCursor(SQLiteDatabase mDb, TableTrips.Row trip)
    {
        Cursor cursor = mDb.query(TABLE_NAME, null, null, null, null, null, null);
        return cursor;
    }
}
