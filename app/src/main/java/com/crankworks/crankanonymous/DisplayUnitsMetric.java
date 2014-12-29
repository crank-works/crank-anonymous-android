package com.crankworks.crankanonymous;

/**
 * Created by marcus on 12/29/14.
 */
public class DisplayUnitsMetric extends DisplayUnits
{
    public String formatSpeed(double mps)
    {
        return String.format("%.1f KM/H", toKMH(mps));
    }

    public String formatAltitude(double meters)
    {
        return String.format("%.1f meters", meters);
    }

    public String formatDistance(double meters)
    {
        return String.format("%.1f KM", toKilometers(meters));
    }

    public String formatAccuracy(double meters)
    {
        return String.format("%.1f meters", meters);
    }

    private double toKMH(double mps)
    {
        return mps * 3600.0 / 1000.0;
    }

    private double toKilometers(double meters)
    {
        return 0.001 * meters;
    }
}
