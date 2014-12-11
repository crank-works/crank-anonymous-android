package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStateIdle extends RecorderStateBase implements IRecorder
{
    public RecorderStateIdle(TrackingRecorder stateContext)
    {
        super(stateContext);
    }

    public void startRecording()
    {
        getListener().recorderRecording();
        RecorderStateRecord recorder = getStateContext().stateRecord;
        getStateContext().setState(recorder);
    }
}