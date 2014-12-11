package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/11/14.
 */
public interface IRecorderState
{
    public void startRecording();
    public void pauseRecording();
    public void finishRecording();
    public void cancelRecording();
    public void notifyState();
}
