package com.crankworks.crankanonymous.trackingservice;

import android.content.Context;
import android.location.LocationManager;
import android.os.Binder;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements ITracker
{
    private static final String TAG = TrackingServiceBinder.class.getSimpleName();

    private Context mTrackingContext;
    private ArrayList<ITrackObserver> mObservers = new ArrayList<ITrackObserver>();

    RecorderStateIdle stateIdle;
    RecorderStateRecord stateRecord;
    RecorderStatePause statePause;

    private RecorderState mState;

    public TrackingServiceBinder(Context trackingContext)
    {
        mTrackingContext = trackingContext;

        LocationManager locationManager = (LocationManager) mTrackingContext.getSystemService(Context.LOCATION_SERVICE);

        stateIdle = new RecorderStateIdle(this);
        stateRecord = new RecorderStateRecord(this, locationManager);
        statePause = new RecorderStatePause(this);

        mState = stateIdle;
        notifyState();
    }

    ArrayList<ITrackObserver> getObservers()
    {
        return mObservers;
    }

    /* ITracker interface */

    public void attachObserver(ITrackObserver observer)
    {
        Log.v(TAG, "attachObserver: is null? " + (observer == null ? "true" : "false"));

        if (observer == null)
            Log.v(TAG, "attachObserver: observer is null");
        else if (mObservers.contains(observer))
            Log.v(TAG, "attachObserver: observer is already in observers list");
        else
        {
            Log.v(TAG, "attachObserver: adding observer to observers list");
            mObservers.add(observer);
            observer.trackerAttach(mTrackingContext, stateRecord.getLastLocation(), stateRecord.getLocationList());
        }

        notifyState();
    }

    public void detachObserver(ITrackObserver observer)
    {
        if (observer == null)
        {
            for (ITrackObserver o : mObservers)
                o.trackerDetach();

            mObservers.clear();
        }
        else
        {
            observer.trackerDetach();
            mObservers.remove(observer);
        }
    }

    public void startRecording()
    {
        mState = mState.startRecording();
        notifyState();
    }

    public void pauseRecording()
    {
        mState = mState.pauseRecording();
        notifyState();
    }

    public void finishRecording()
    {
        mState = mState.finishRecording();
        notifyState();
        stateRecord.reset();
    }

    public void cancelRecording()
    {
        mState = mState.cancelRecording();
        notifyState();
        stateRecord.reset();
    }

    public void notifyState()
    {
        mState.notifyState(mObservers);
    }
}
