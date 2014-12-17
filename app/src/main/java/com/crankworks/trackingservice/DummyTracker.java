package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/17/14.
 */
public class DummyTracker implements ITracker
{
    private static DummyTracker mInstance;

    public static ITracker instance()
    {
        if (mInstance == null)
            mInstance = new DummyTracker();

        return mInstance;
    }

    private DummyTracker()
    {}

    public void attachObserver(ITrackObserver observer)
    {}

    public void detachObserver(ITrackObserver observer)
    {}

    public void startRecording()
    {}

    public void pauseRecording()
    {}

    public void finishRecording()
    {}

    public void cancelRecording()
    {}

    public void notifyState()
    {}
}
