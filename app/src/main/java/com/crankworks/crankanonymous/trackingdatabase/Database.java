package com.crankworks.crankanonymous.trackingdatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.location.Location;
import android.util.Log;

/**
 * Created by marcus on 12/12/14.
 */
public class Database
{
    private static final String TAG = Database.class.getSimpleName();

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tracking_service.db";

    Context mContext;
    DatabaseHelper mDbHelper;
    SQLiteDatabase mDb;

//    private static final String TABLE_CREATE_TRIPS = "create table " + DATABASE_TABLE_TRIPS
//            + ""

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db)
        {
            Log.v(TAG, "Creating database");

            TableTrips.createTable(db);
            TableCoordinates.createTable(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.i(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
            db.execSQL("DROP TABLE IF EXISTS " + TableTrips.TABLE_NAME);
            db.execSQL("DROP TABLE IF EXISTS " + TableCoordinates.TABLE_NAME);
            onCreate(db);
        }
    }

    TableTrips.Row currentTrip;
    Location lastLocation;

    public static Database createInstance(Context context)
    {
        return new Database(context);
    }

    public Database(Context context)
    {
        mContext = context;
    }

    public Database open() throws SQLException
    {
        mDbHelper = new DatabaseHelper(mContext);
        mDb = mDbHelper.getWritableDatabase();
        return this;
    }

    public void close()
    {
        if (mDb != null)
            mDb.close();

        if (mDbHelper != null)
            mDbHelper.close();
    }

    public void newTrip(Location location)
    {
        Log.v(TAG, "newTrip");

        try
        {
            open();

            currentTrip = new TableTrips.Row(location);
            currentTrip._id = TableTrips.addRow(mDb, currentTrip);
            lastLocation = location;

            TableCoordinates.Row currentPosition = new TableCoordinates.Row(currentTrip, location);
            TableCoordinates.addPosition(mDb, currentPosition);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "newTrip", e);
        }

        finally
        {
            close();
        }
    }

    public void newPosition(Location location)
    {
        Log.v(TAG, "newPosition");

        if (currentTrip == null)
            return;

        try
        {
            open();

            currentTrip.update(location, lastLocation);
            TableTrips.updateRow(mDb, currentTrip);
            lastLocation = location;

            TableCoordinates.Row currentPosition = new TableCoordinates.Row(currentTrip, location);
            TableCoordinates.addPosition(mDb, currentPosition);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "newPosition", e);
        }

        finally
        {
            close();
        }
    }

    public void deleteCurrentTrip()
    {
        Log.v(TAG, "deleteCurrentTrip");

        if (currentTrip == null)
            return;

        try
        {
            open();

            TableCoordinates.deleteTrip(mDb, currentTrip._id);
            TableTrips.deleteRow(mDb, currentTrip._id);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "deleteCurrentTrip", e);
        }

        finally
        {
            close();
        }
    }

    public void setUploaded(int row_id, boolean flag)
    {
        Log.v(TAG, "setUploaded");

        try
        {
            open();
            TableTrips.setUploaded(mDb, row_id, flag);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "setUploaded", e);
        }

        finally
        {
            close();
        }
    }

    public Cursor getTrips()
    {
        return TableTrips.getCursor(mDb);
    }

    public TableTrips.Row getLastTrip()
    {
        Log.v(TAG, "getLastTrip");
        TableTrips.Row trip = null;

        try
        {
            open();
            Cursor cursor = getTrips();
            cursor.moveToLast();
            trip = new TableTrips.Row(cursor);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "getLastTrip", e);
        }

        finally
        {
            close();
        }

        return trip;
    }

    public int tripCount()
    {
        Log.v(TAG, "tripCount");
        int count = 0;

        try
        {
            open();
            count = getTrips().getCount();
        }

        catch (SQLException e)
        {
            Log.e(TAG, "tripCount", e);
        }

        finally
        {
            close();
        }

        return count;
    }

    public int totalDistance()
    {
        Log.v(TAG, "totalDistance");
        int distance = 0;

        try
        {
            open();
            distance = TableTrips.totalDistance(mDb);
        }

        catch (SQLException e)
        {
            Log.e(TAG, "totalDistance", e);
        }

        finally
        {
            close();
        }

        return distance;
    }
}
