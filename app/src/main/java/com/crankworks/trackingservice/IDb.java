package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/12/14.
 */
public interface IDb
{
    public DataTrip firstTrip();
    public DataTrip nextTrip();
    public boolean writeTrip(DataTrip data);

    public DataCoordinate firstCoordinate(long trip_id);
    public DataCoordinate nextCoordinate();
    public boolean writeCoordinate(DataCoordinate data);
}
