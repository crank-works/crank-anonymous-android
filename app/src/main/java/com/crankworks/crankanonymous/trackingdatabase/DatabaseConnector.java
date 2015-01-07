package com.crankworks.crankanonymous.trackingdatabase;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.ITracker;

import java.util.ArrayList;

/**
 * Created by marcus on 12/13/14.
 */
public class DatabaseConnector implements ITrackObserver
{
    private static final String TAG = DatabaseConnector.class.getSimpleName();

    private static DatabaseConnector mInstance;

    private ITracker mTracker;
    private Context mContext;
    private Database mDatabase;

    public static DatabaseConnector connectorInstance()
    {
        if (mInstance == null)
            throw new RuntimeException("DatabaseConnector.connectorInstance: DatabaseConnector not instantiated.");

        return mInstance;
    }

    public static DatabaseConnector connectorInstance(ITracker tracker)
    {
        if (mInstance == null)
            mInstance = new DatabaseConnector(tracker);

        return mInstance;
    }

    private DatabaseConnector(ITracker tracker)
    {
        mTracker = tracker;
        mTracker.attachObserver(this);
    }

    public boolean onFinished()
    {
        mTracker.detachObserver(this);
        mInstance = null;
        return mDatabase != null;
    }

    public void onCanceled()
    {
        mTracker.detachObserver(this);
        if (mDatabase != null)
            mDatabase.deleteCurrentTrip();
        mInstance = null;
    }

    /* ITrackObserver interface */

    public void trackerAttach(Context context)
    {
        mContext = context;
    }

    public void trackerDetach()
    {
    }

    public void trackerLocation(Location location, ArrayList<Location> locationList)
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
    }

    public void trackerRecording()
    {}

    public void trackerPaused()
    {}
}
