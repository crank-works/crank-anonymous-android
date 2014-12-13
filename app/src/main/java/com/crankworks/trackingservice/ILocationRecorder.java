package com.crankworks.trackingservice;

import android.location.Location;

/**
 * Created by marcus on 12/12/14.
 */
public interface ILocationRecorder
{
    public boolean newTrip();
    public boolean recordPosition(Location location);
}
