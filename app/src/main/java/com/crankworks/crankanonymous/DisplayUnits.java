package com.crankworks.crankanonymous;

import android.content.SharedPreferences;

/**
 * Created by marcus on 12/29/14.
 */
public class DisplayUnits
{
    private static final String KEY_PREF_DISPLAY_UNITS = "pref_display_units";

    enum DISPLAY_UNITS
    {
        UNIT_ENGLISH,
        UNIT_METRIC
    }

    private static SharedPreferences sharedPreferences;

    public static void initialize(SharedPreferences sp)
    {
        sharedPreferences = sp;
    }

    private static DISPLAY_UNITS getCurrentUnit()
    {
        DISPLAY_UNITS rv = DISPLAY_UNITS.UNIT_ENGLISH;

        String value = sharedPreferences.getString(KEY_PREF_DISPLAY_UNITS, "");

        if (value.equals("English"))
            rv = DISPLAY_UNITS.UNIT_ENGLISH;
        else if (value.equals("Metric"))
            rv = DISPLAY_UNITS.UNIT_METRIC;

        return rv;
    }

    public static String displaySpeed(double value)
    {
        String rv = "";

        switch (getCurrentUnit())
        {
            case UNIT_ENGLISH:
                rv = String.format("%.1f MPH", toMPH(value));
                break;

            case UNIT_METRIC:
                rv = String.format("%.0f KM/H", toKMH(value));
                break;
        }

        return rv;
    }

    public static double toMPH(double mps)
    {
        return mps * 3600.0 / 1609.334;
    }

    public static double toKMH(double mps)
    {
        return mps * 3600.0 / 1000.0;
    }
}
