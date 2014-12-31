package com.crankworks.crankanonymous;

import junit.framework.TestCase;

/**
 * Created by marcus on 12/30/14.
 */
public class DisplayUnitsTest extends TestCase
{
    private DisplayUnits displayUnits;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        displayUnits = new DisplayUnits()
        {
            @Override
            public String formatSpeed(double mps)
            {
                return null;
            }

            @Override
            public String formatAltitude(double meters)
            {
                return null;
            }

            @Override
            public String formatDistance(double meters)
            {
                return null;
            }

            @Override
            public String formatAccuracy(double meters)
            {
                return null;
            }
        };
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        displayUnits = null;
    }

    private void checkBearing(double b, String expected)
    {
        assertEquals(expected, displayUnits.formatBearing(b - 22.5));
        assertEquals(expected, displayUnits.formatBearing(b));
        assertEquals(expected, displayUnits.formatBearing(b + 22.4));
    }

    public void testFormatBearing() throws Exception
    {
        checkBearing(  0.0, "north");
        checkBearing( 45.0, "northeast");
        checkBearing( 90.0, "east");
        checkBearing(135.0, "southeast");
        checkBearing(180.0, "south");
        checkBearing(225.0, "southwest");
        checkBearing(270.0, "west");
        checkBearing(315.0, "northwest");
        checkBearing(360.0, "north");
    }

    public void testFormatElapsedTime() throws Exception
    {
        assertEquals("00:00:00", displayUnits.formatElapsedTime(0));
        assertEquals("00:00:01", displayUnits.formatElapsedTime(1000));
        assertEquals("00:01:00", displayUnits.formatElapsedTime(60000));
        assertEquals("01:00:00", displayUnits.formatElapsedTime(3600000));
        assertEquals("99:59:59", displayUnits.formatElapsedTime(359999000));
    }
}
