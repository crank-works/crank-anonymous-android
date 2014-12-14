package com.crankworks.trackingservice;

import android.location.Location;

/**
 * Created by marcus on 12/7/14.
 */
public interface ITrackObserver
{
    public void trackerLocation(Location location);
    public void trackerIdle();
    public void trackerRecording();
    public void trackerPaused();
}