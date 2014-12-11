package com.crankworks.trackingservice;

import android.content.Context;
import android.location.LocationManager;
import android.os.Binder;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements IRecorder
{
    private static final String TAG = TrackingServiceBinder.class.getSimpleName();

    private TrackingService mTrackingService;
    private IRecorderStateListener mListener = new DummyRecorderStateListener();

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

    public void setListener(IRecorderStateListener listener)
    {
        if (listener != null)
            mListener = listener;
        else
            mListener = new DummyRecorderStateListener();

        notifyState();
    }

    public IRecorderStateListener getListener()
    {
        return mListener;
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
        mState.notifyState(getListener());
    }
}
