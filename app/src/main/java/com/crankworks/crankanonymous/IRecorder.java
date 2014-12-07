package com.crankworks.crankanonymous;

/**
 * Created by marcus on 12/7/14.
 */
public interface IRecorder
{
    public void setListener(IRecorderStateListener listener);
    public void startRecording();
    public void pauseRecording();
    public void finishRecording();
    public void cancelRecording();
    public void notifyState();
}
