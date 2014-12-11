package com.crankworks.trackingservice;

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

    public void startRecording()
    {
        getListener().recorderRecording();
        RecorderStateRecord recorder = getStateContext().stateRecord;
        recorder.stateBeginRecording();
        getStateContext().setState(recorder);
    }

    @Override
    public void notifyState()
    {
        getListener().recorderIdle();
    }
}
