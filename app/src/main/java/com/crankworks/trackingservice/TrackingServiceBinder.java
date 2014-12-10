package com.crankworks.trackingservice;

import android.os.Binder;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements IRecorder
{
    private IRecorder mRecorder;

    public TrackingServiceBinder()
    {
        setRecorder(null);
    }

    public void setRecorder(IRecorder recorder)
    {
        if (recorder != null)
            mRecorder = recorder;
        else
            mRecorder = new DummyRecorder();
    }

    public void setListener(IRecorderStateListener listener)
    {
        mRecorder.setListener(listener);
    }

    public void startRecording()
    {
        mRecorder.startRecording();
    }

    public void pauseRecording()
    {
        mRecorder.pauseRecording();
    }

    public void finishRecording()
    {
        mRecorder.finishRecording();
    }

    public void cancelRecording()
    {
        mRecorder.cancelRecording();
    }

    public void notifyState()
    {
        mRecorder.notifyState();
    }
}
