package com.crankworks.crankanonymous;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStatePause extends RecorderStateBase implements IRecorder
{
    public RecorderStatePause(TrackingRecorder stateContext)
    {
        super(stateContext);
    }

    public void startRecording()
    {
        getListener().recorderRecording();
    }

    public void finishRecording()
    {
        getListener().recorderIdle();
    }

    public void cancelRecording()
    {
        getListener().recorderIdle();
    }
}
