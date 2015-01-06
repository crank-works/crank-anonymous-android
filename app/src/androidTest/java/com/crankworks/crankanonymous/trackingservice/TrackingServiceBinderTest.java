package com.crankworks.crankanonymous.trackingservice;

import android.content.Context;
import android.content.ContextWrapper;
import android.location.Location;
import android.test.AndroidTestCase;

import junit.framework.TestCase;

import java.util.ArrayList;

/**
 * Created by marcus on 1/1/15.
 */
public class TrackingServiceBinderTest extends AndroidTestCase
{
    private TrackingServiceBinder binder;
    DummyTrackObserver observer;

    @Override
    public void setUp() throws Exception
    {
        super.setUp();

        binder = new TrackingServiceBinder(getContext());
        observer = new DummyTrackObserver();

        binder.attachObserver(observer);
        assertTrue(observer.isAttached());
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));
    }

    @Override
    public void tearDown() throws Exception
    {
        binder.detachObserver(observer);
        assertFalse(observer.isAttached());

        observer = null;
        binder = null;
        super.tearDown();
    }

    public void testCancel()
    {
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.pauseRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_PAUSED));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.cancelRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));
    }

    public void testFinish()
    {
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.pauseRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_PAUSED));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.finishRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));
    }

    public void testRestart()
    {
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.finishRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));

        binder.startRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_RECORDING));

        binder.finishRecording();
        assertTrue(observer.isState(DummyTrackObserver.STATE.STATE_IDLE));
    }

    static class DummyTrackObserver implements ITrackObserver
    {
        enum STATE
        {
            STATE_IDLE,
            STATE_RECORDING,
            STATE_PAUSED
        }

        private STATE m_state = STATE.STATE_IDLE;
        private boolean m_bAttached = false;

        public boolean isState(STATE s)
        {
            return s == m_state;
        }
        
        public boolean isAttached()
        {
            return m_bAttached;
        }

        public void trackerAttach(Context context)
        {
            m_bAttached = true;
        }

        public void trackerDetach()
        {
            m_bAttached = false;
        }

        public void trackerLocation(Location location, ArrayList<Location> locationList) {}

        public void trackerIdle()
        {
            m_state = STATE.STATE_IDLE;
        }

        public void trackerRecording()
        {
            m_state = STATE.STATE_RECORDING;
        }

        public void trackerPaused()
        {
            m_state = STATE.STATE_PAUSED;
        }
    }
}
