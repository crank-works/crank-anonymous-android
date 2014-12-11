package com.crankworks.trackingservice;

import android.content.Context;
import android.location.LocationManager;
import android.os.Binder;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements IRecorder
{

    private TrackingService mTrackingService;
    private IRecorderStateListener mListener;
    private TrackingServiceBinder mBinder;

    RecorderStateIdle stateIdle;
    RecorderStateRecord stateRecord;
    RecorderStatePause statePause;

    private IRecorder mState;

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
        mListener = listener;
    }

    public IRecorderStateListener getListener()
    {
        return mListener;
    }

    public void setState(IRecorder recorder)
    {
        if (recorder != null)
            mState = recorder;
        else
            mState = new DummyRecorder();
    }

    public void startRecording()
    {
        mState.startRecording();
    }

    public void pauseRecording()
    {
        mState.pauseRecording();
    }

    public void finishRecording()
    {
        mState.finishRecording();
    }

    public void cancelRecording()
    {
        mState.cancelRecording();
    }

    public void notifyState()
    {
        mState.notifyState();
    }
}
