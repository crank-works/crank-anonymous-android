package com.crankworks.crankanonymous;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by marcus on 12/29/14.
 */
public abstract class DisplayUnits
{
    private static final String KEY_PREF_DISPLAY_UNITS = "pref_display_units";

    enum DISPLAY_UNITS
    {
        UNIT_ENGLISH,
        UNIT_METRIC
    }

    public static DisplayUnits instance(Context context)
    {
        return instance(PreferenceManager.getDefaultSharedPreferences(context));
    }

    public static DisplayUnits instance(SharedPreferences sp)
    {
        DisplayUnits rv = null;

        switch (getCurrentUnit(sp))
        {
            case UNIT_ENGLISH:
                rv = new DisplayUnitsEnglish();
                break;

            case UNIT_METRIC:
                rv = new DisplayUnitsMetric();
                break;
        }

        return rv;
    }

    private static DISPLAY_UNITS getCurrentUnit(SharedPreferences sp)
    {
        DISPLAY_UNITS rv = DISPLAY_UNITS.UNIT_ENGLISH;

        String value = sp.getString(KEY_PREF_DISPLAY_UNITS, "");

        if (value.equals("English"))
            rv = DISPLAY_UNITS.UNIT_ENGLISH;
        else if (value.equals("Metric"))
            rv = DISPLAY_UNITS.UNIT_METRIC;

        return rv;
    }

    public abstract String formatSpeed(double mps);
    public abstract String formatAltitude(double meters);
    public abstract String formatDistance(double meters);
    public abstract String formatAccuracy(double meters);
}
