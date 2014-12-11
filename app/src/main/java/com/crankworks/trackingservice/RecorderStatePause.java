package com.crankworks.trackingservice;

import android.util.Log;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStatePause extends RecorderStateBase
{
    private static final String TAG = RecorderStatePause.class.getSimpleName();

    public RecorderStatePause(TrackingServiceBinder stateContext)
    {
        super(stateContext);
    }

    public IRecorderState startRecording()
    {
        Log.v(TAG, "startRecording");
        getStateRecorder().stateResumeRecording();
        return getStateRecorder();
    }

    public IRecorderState finishRecording()
    {
        Log.v(TAG, "finishRecording");
        getStateRecorder().stateFinishRecording();
        return getStateIdle();
    }

    public IRecorderState cancelRecording()
    {
        Log.v(TAG, "cancelRecording");
        getStateRecorder().stateCancelRecording();
        return getStateIdle();
    }

    @Override
    public void notifyState(IRecorderStateListener listener)
    {
        Log.v(TAG, "notifyState");
        listener.recorderPaused();
    }
}
