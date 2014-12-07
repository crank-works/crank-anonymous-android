package com.crankworks.crankanonymous;

import android.location.Location;

/**
 * Created by marcus on 12/7/14.
 */
public interface IRecorderStateListener
{
    public void recorderLocation(Location location);
    public void recorderIdle();
    public void recorderRecording();
    public void recorderPaused();
}
