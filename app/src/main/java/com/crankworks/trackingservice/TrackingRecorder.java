package com.crankworks.trackingservice;

import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.util.Log;

/**
 * Created by marcus on 12/9/14.
 */
public class TrackingRecorder
{
    private static final String TAG = TrackingRecorder.class.getSimpleName();

    private TrackingService mTrackingService;
    private IRecorderStateListener mListener;
    private TrackingServiceBinder mBinder;

    private RecorderStateIdle stateIdle;
    private RecorderStateRecord stateRecord;
    private RecorderStatePause statePause;

    public TrackingRecorder(TrackingService trackingService, TrackingServiceBinder binder)
    {
        mTrackingService = trackingService;
        mBinder = binder;

        LocationManager locationManager = (LocationManager) mTrackingService.getSystemService(Context.LOCATION_SERVICE);

        stateIdle = new RecorderStateIdle(this);
        stateRecord = new RecorderStateRecord(this, locationManager);
        statePause = new RecorderStatePause(this);

        setState(stateIdle);
    }


    private Criteria createCriteria()
    {
        Log.v(TAG, "createCriteria");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(true);
        return criteria;
    }

    public void setListener(IRecorderStateListener listener)
    {
        mListener = listener;
    }

    public IRecorderStateListener getListener()
    {
        return mListener;
    }

    public void setState(IRecorder state)
    {
        mBinder.setRecorder(state);
    }
}
