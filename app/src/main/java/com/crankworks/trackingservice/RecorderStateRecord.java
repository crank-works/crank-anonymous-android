package com.crankworks.trackingservice;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStateRecord extends RecorderStateBase implements LocationListener
{
    private static final String TAG = RecorderStateRecord.class.getSimpleName();

    private LocationManager mLocationManager;
    private String mProvider;

    private ArrayList<Location> listLocation;

    public RecorderStateRecord(TrackingServiceBinder stateContext, LocationManager locationManager)
    {
        super(stateContext);
        Log.v(TAG, "RecorderStateRecord");
        mLocationManager = locationManager;
        mProvider = mLocationManager.getBestProvider(createCriteria(), true);
        Log.v(TAG, "mProvider: " + mProvider);
    }

    private Criteria createCriteria()
    {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(true);
        return criteria;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        Log.v(TAG, "onLocationChanged");
        listLocation.add(location);
        getListener().recorderLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.v(TAG, "onStatusChanged: " + provider + ", status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Log.v(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Log.v(TAG, "onProviderDisabled: " + provider);
    }

    /* interstate interface */

    void stateBeginRecording()
    {
        Log.v(TAG, "stateBeginRecording");
        mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
    }

    void stateResumeRecording()
    {
        Log.v(TAG, "stateResumeRecording");
        mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
    }

    void stateFinishRecording()
    {
        Log.v(TAG, "stateFinishRecording");
        mLocationManager.removeUpdates(this);
    }

    void stateCancelRecording()
    {
        Log.v(TAG, "stateCancelRecording");
        mLocationManager.removeUpdates(this);
        listLocation.clear();
    }

    /* IRecorder interface */

    @Override
    public IRecorderState pauseRecording()
    {
        Log.v(TAG, "pauseRecording");
        mLocationManager.removeUpdates(this);
        return getStatePause();
    }

    @Override
    public IRecorderState finishRecording()
    {
        Log.v(TAG, "finishRecording");
        stateFinishRecording();
        return getStateIdle();
    }

    @Override
    public IRecorderState cancelRecording()
    {
        Log.v(TAG, "cancelRecording");
        stateCancelRecording();
        return getStateIdle();
    }

    @Override
    public void notifyState(IRecorderStateListener listener)
    {
        Log.v(TAG, "notifyState");
        listener.recorderRecording();
    }
}
