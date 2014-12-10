package com.crankworks.crankanonymous;

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
    }

    public void finishRecording()
    {
    }

    public void cancelRecording()
    {
    }
}
