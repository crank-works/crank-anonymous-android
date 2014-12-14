package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/7/14.
 */
public interface ITracker
{
    public void attachObserver(ITrackObserver observer);
    public void detachObserver(ITrackObserver observer);
    public void startRecording();
    public void pauseRecording();
    public void finishRecording();
    public void cancelRecording();
    public void notifyState();
}
