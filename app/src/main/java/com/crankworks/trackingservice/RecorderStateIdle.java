package com.crankworks.trackingservice;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marcus on 12/8/14.
 */
class RecorderStateIdle extends RecorderState
{
    private static final String TAG = RecorderStateIdle.class.getSimpleName();

    /* IRecorderState interface */

    public RecorderStateIdle(TrackingServiceBinder stateContext)
    {
        super(stateContext);
    }

    public RecorderState startRecording()
    {
        Log.v(TAG, "startRecording");
        getStateRecorder().stateBeginRecording();
        return getStateRecorder();
    }

    @Override
    public void notifyState(ArrayList<ITrackObserver> observers)
    {
        Log.v(TAG, "notifyState");

        for (ITrackObserver observer : observers)
            observer.trackerIdle();
    }
}
