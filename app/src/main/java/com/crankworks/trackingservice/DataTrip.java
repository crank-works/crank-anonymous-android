package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/12/14.
 */
public class DataTrip
{
    public long      _id;
    public long      start_time;
    public long      end_time;
    public String    objective;
    public double    latitude_high;
    public double    latitude_low;
    public double    longitude_high;
    public double    longitude_low;
    public double    distance;

    public static DataTrip first(IDb db)
    {
        return db.firstTrip();
    }

    public static DataTrip next(IDb db)
    {
        return db.nextTrip();
    }

    public boolean write(IDb db)
    {
        return db.writeTrip(this);
    }
}
