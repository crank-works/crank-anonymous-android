package com.crankworks.TrackingActivity;

import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.crankworks.trackingdatabase.Database;
import com.crankworks.trackingservice.BaseTrackObserver;
import com.crankworks.trackingservice.ITrackObserver;

/**
 * Created by marcus on 12/13/14.
 */
public class DatabaseConnector extends BaseTrackObserver implements ITrackObserver
{
    private static final String TAG = DatabaseConnector.class.getSimpleName();

    private static DatabaseConnector mInstance;

    private Context mContext;
    private Database mDatabase;

    public static DatabaseConnector connectorInstance(Context context)
    {
        if (mInstance == null)
            mInstance = new DatabaseConnector(context);

        return mInstance;
    }

    private DatabaseConnector(Context context)
    {
        mContext = context;
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
}
