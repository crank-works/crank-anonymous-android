package com.crankworks.trackingservice;

import android.content.Context;
import android.location.LocationManager;
import android.os.Binder;

import java.util.ArrayList;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements ITracker
{
    private static final String TAG = TrackingServiceBinder.class.getSimpleName();

    private TrackingService mTrackingService;
    private ArrayList<ITrackObserver> mObservers = new ArrayList<ITrackObserver>();

    RecorderStateIdle stateIdle;
    RecorderStateRecord stateRecord;
    RecorderStatePause statePause;

    private IRecorderState mState;

    public TrackingServiceBinder(TrackingService trackingService)
    {
        mTrackingService = trackingService;

        LocationManager locationManager = (LocationManager) mTrackingService.getSystemService(Context.LOCATION_SERVICE);

        stateIdle = new RecorderStateIdle(this);
        stateRecord = new RecorderStateRecord(this, locationManager);
        statePause = new RecorderStatePause(this);

        setState(stateIdle);
    }

    ArrayList<ITrackObserver> getObservers()
    {
        return mObservers;
    }

    public void attachObserver(ITrackObserver observer)
    {
        mObservers.add(observer);
        notifyState();
    }

    public void detachObserver(ITrackObserver observer)
    {
        mObservers.remove(observer);
    }

    public void setState(IRecorderState recorder)
    {
        mState = recorder;
        notifyState();
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
    }

    public void cancelRecording()
    {
        mState = mState.cancelRecording();
        notifyState();
    }

    public void notifyState()
    {
        mState.notifyState(mObservers);
    }
}
