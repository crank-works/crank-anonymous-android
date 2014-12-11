package com.crankworks.trackingservice;

import android.location.Location;

/**
 * Created by marcus on 12/10/14.
 */
public class DummyRecorderStateListener implements IRecorderStateListener
{
    public void recorderLocation(Location location)
    {}

    public void recorderIdle()
    {}

    public void recorderRecording()
    {}

    public void recorderPaused()
    {}
}
