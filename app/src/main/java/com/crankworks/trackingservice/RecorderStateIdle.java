package com.crankworks.trackingservice;

import android.util.Log;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStateIdle extends RecorderStateBase
{
    private static final String TAG = RecorderStateIdle.class.getSimpleName();

    public RecorderStateIdle(TrackingServiceBinder stateContext)
    {
        super(stateContext);
    }

    public IRecorderState startRecording()
    {
        Log.v(TAG, "startRecording");
        getStateRecorder().stateBeginRecording();
        return getStateRecorder();
    }

    @Override
    public void notifyState(IRecorderStateListener listener)
    {
        Log.v(TAG, "notifyState");
        listener.recorderIdle();
    }
}
