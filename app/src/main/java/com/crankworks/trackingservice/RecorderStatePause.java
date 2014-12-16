package com.crankworks.trackingservice;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marcus on 12/8/14.
 */
class RecorderStatePause extends RecorderState
{
    private static final String TAG = RecorderStatePause.class.getSimpleName();

    public RecorderStatePause(TrackingServiceBinder stateContext)
    {
        super(stateContext);
    }

    /* RecorderState interface */

    @Override
    public RecorderState startRecording()
    {
        Log.v(TAG, "startRecording");
        getStateRecorder().stateResumeRecording();
        return getStateRecorder();
    }

    @Override
    public RecorderState finishRecording()
    {
        Log.v(TAG, "finishRecording");
        getStateRecorder().stateFinishRecording();
        return getStateIdle();
    }

    @Override
    public RecorderState cancelRecording()
    {
        Log.v(TAG, "cancelRecording");
        getStateRecorder().stateCancelRecording();
        return getStateIdle();
    }

    @Override
    public void notifyState(ArrayList<ITrackObserver> observers)
    {
        Log.v(TAG, "notifyState");

        for (ITrackObserver observer : observers)
            observer.trackerPaused();
    }
}
