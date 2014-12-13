package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/12/14.
 */
public interface ITrackWriter
{
    public boolean writeTrip(DataTrip data);
    public boolean writeCoordinate(DataCoordinate data);
}
