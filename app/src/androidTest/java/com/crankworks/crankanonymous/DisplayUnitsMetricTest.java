package com.crankworks.crankanonymous;

import junit.framework.TestCase;

/**
 * Created by marcus on 12/30/14.
 */
public class DisplayUnitsMetricTest extends TestCase
{
    private DisplayUnits displayUnits;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        displayUnits = new DisplayUnitsMetric();
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        displayUnits = null;
    }

    public void testFormatSpeed() throws Exception
    {
        assertEquals("0.0 KM/H", displayUnits.formatSpeed(0.0));
        assertEquals("10.0 KM/H", displayUnits.formatSpeed(2.77778));
        assertEquals("36.0 KM/H", displayUnits.formatSpeed(10.0));
    }

    public void testFormatAltitude() throws Exception
    {
        assertEquals("0.0 meters", displayUnits.formatAltitude(0.0));
        assertEquals("1000.0 meters", displayUnits.formatAltitude(1000.0));
        assertEquals("3280.8 meters", displayUnits.formatAltitude(3280.8));
    }

    public void testFormatDistance() throws Exception
    {
        assertEquals("0.0 KM", displayUnits.formatDistance(0.0));
        assertEquals("1.0 KM", displayUnits.formatDistance(1000.0));
        assertEquals("0.6 KM", displayUnits.formatDistance(600));
    }

    public void testFormatAccuracy() throws Exception
    {
        assertEquals("0.0 meters", displayUnits.formatAccuracy(0.0));
        assertEquals("1000.0 meters", displayUnits.formatAccuracy(1000.0));
        assertEquals("3280.8 meters", displayUnits.formatAccuracy(3280.8));
    }
}
