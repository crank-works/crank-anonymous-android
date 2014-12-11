package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/11/14.
 */
public interface IRecorderState
{
    public IRecorderState startRecording();
    public IRecorderState pauseRecording();
    public IRecorderState finishRecording();
    public IRecorderState cancelRecording();
    public void notifyState(IRecorderStateListener listener);
}
