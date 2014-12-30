package com.crankworks.crankanonymous;

//import android.test.InstrumentationTestCase;

import junit.framework.TestCase;

/**
 * Created by marcus on 12/29/14.
 */
public class DisplayUnitsEnglishTest extends TestCase
{
    private DisplayUnits displayUnits;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();
        displayUnits = new DisplayUnitsEnglish();
    }

    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        displayUnits = null;
    }

    public void testFormatSpeed() throws Exception
    {
        assertEquals("0.0 MPH", displayUnits.formatSpeed(0.0));
        assertEquals("10.0 MPH", displayUnits.formatSpeed(4.4704));
        assertEquals("22.4 MPH", displayUnits.formatSpeed(10.0));
    }

    public void testFormatAltitude() throws Exception
    {
        assertEquals("0.0 feet", displayUnits.formatAltitude(0.0));
        assertEquals("1000.0 feet", displayUnits.formatAltitude(304.8));
        assertEquals("3280.8 feet", displayUnits.formatAltitude(1000.0));
    }

    public void testFormatDistance() throws Exception
    {
        assertEquals("0.0 miles", displayUnits.formatDistance(0.0));
        assertEquals("1.0 miles", displayUnits.formatDistance(1609.3));
        assertEquals("0.6 miles", displayUnits.formatDistance(1000.0));
    }

    public void testFormatAccuracy() throws Exception
    {
        assertEquals("0.0 feet", displayUnits.formatAccuracy(0.0));
        assertEquals("1000.0 feet", displayUnits.formatAccuracy(304.8));
        assertEquals("3280.8 feet", displayUnits.formatAccuracy(1000.0));
    }
}
