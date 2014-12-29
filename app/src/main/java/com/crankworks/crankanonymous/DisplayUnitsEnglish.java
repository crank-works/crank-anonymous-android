package com.crankworks.crankanonymous;

/**
 * Created by marcus on 12/29/14.
 */
public class DisplayUnitsEnglish extends DisplayUnits
{
    public DisplayUnitsEnglish()
    {}

    public String formatSpeed(double mps)
    {
        return String.format("%.1f MPH", toMPH(mps));
    }

    public String formatAltitude(double meters)
    {
        return String.format("%.1f feet", toFeet(meters));
    }

    public String formatDistance(double meters)
    {
        return String.format("%.1f miles", toMiles(meters));
    }

    public String formatAccuracy(double meters)
    {
        return String.format("%.1f feet", toFeet(meters));
    }

    private double toMPH(double mps)
    {
        return mps * 3600.0 / 1609.334;
    }

    private double toFeet(double meters)
    {
        return 3.28084 * meters;
    }

    public double toMiles(double meters)
    {
        return 0.000621371 * meters;
    }
}
