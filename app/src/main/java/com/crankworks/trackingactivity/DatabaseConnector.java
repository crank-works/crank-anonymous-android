package com.crankworks.trackingactivity;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.crankworks.trackingdatabase.Database;
import com.crankworks.trackingservice.ITrackObserver;

/**
 * Created by marcus on 12/13/14.
 */
public class DatabaseConnector implements ITrackObserver
{
    private static final String TAG = DatabaseConnector.class.getSimpleName();

    private static DatabaseConnector mInstance;

    private Context mContext;
    private Database mDatabase;

    public static DatabaseConnector connectorInstance()
    {
        if (mInstance == null)
            mInstance = new DatabaseConnector();

        return mInstance;
    }

    private DatabaseConnector()
    {
    }

    /* ITrackObserver interface */

    public void trackerAttach(Context context)
    {
        mContext = context;
    }

    public void trackerDetach()
    {
        mDatabase = null;
    }

    public void trackerLocation(Location location)
    {
        Log.v(TAG, "trackerLocation");

        if (mDatabase == null)
        {
            mDatabase = new Database(mContext);
            mDatabase.newTrip(location);
        }
        else
        {
            mDatabase.newPosition(location);
        }
    }

    public void trackerIdle()
    {
        mDatabase = null;
    }

    public void trackerRecording()
    {}

    public void trackerPaused()
    {}
}
