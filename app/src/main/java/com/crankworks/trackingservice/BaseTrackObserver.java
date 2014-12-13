package com.crankworks.trackingservice;

import android.location.Location;

/**
 * Created by marcus on 12/10/14.
 */
public class BaseTrackObserver implements ITrackObserver
{
    public void trackerLocation(Location location)
    {}

    public void trackerIdle()
    {}

    public void trackerRecording()
    {}

    public void trackerPaused()
    {}
}
