package com.crankworks.crankanonymous;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.concurrent.TimeUnit;

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

    public String formatBearing(double bearing)
    {
        String ordinals[] =
        {
            "north", "northeast", "east", "southeast", "south", "southwest", "west", "northwest"
        };

        int index = (((int) (bearing + 22.5)) % 360) / 45;
        return ordinals[index];
    }

    public String formatElapsedTime(long milliseconds)
    {
        final long hr = TimeUnit.MILLISECONDS.toHours(milliseconds);
        final long min = TimeUnit.MILLISECONDS.toMinutes(milliseconds - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(milliseconds - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        return String.format("%02d:%02d:%02d", hr, min, sec);
    }
}
