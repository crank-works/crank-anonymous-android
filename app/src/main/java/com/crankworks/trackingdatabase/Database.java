package com.crankworks.trackingdatabase;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
        mDb.close();
        mDbHelper.close();
    }
}
