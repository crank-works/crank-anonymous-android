package com.crankworks.crankanonymous.trackingservice;

import android.content.Context;
import android.location.Location;

import java.util.ArrayList;

/**
 * Created by marcus on 12/7/14.
 */
public interface ITrackObserver
{
    public void trackerAttach(Context context);
    public void trackerDetach();
    public void trackerLocation(Location location, ArrayList<Location> locationList);
    public void trackerIdle();
    public void trackerRecording();
    public void trackerPaused();
}
