package com.crankworks.trackingservice;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStateRecord extends RecorderStateBase implements LocationListener, IRecorder
{
    private static final String TAG = RecorderStateRecord.class.getSimpleName();

    private LocationManager mLocationManager;
    private String mProvider;

    public RecorderStateRecord(TrackingRecorder stateContext, LocationManager locationManager)
    {
        super(stateContext);
        mLocationManager = locationManager;
    }

    @Override
    public void onLocationChanged(Location location)
    {
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
        mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
    }

    void stateResumeRecording()
    {
        mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
    }

    void stateFinishRecording()
    {
        mLocationManager.removeUpdates(this);
    }

    void stateCancelRecording()
    {
        mLocationManager.removeUpdates(this);
    }

    /* IRecorder interface */

    @Override
    public void pauseRecording()
    {
        mLocationManager.removeUpdates(this);
        getListener().recorderPaused();
        getStateContext().setState(getStateContext().statePause);
    }

    @Override
    public void finishRecording()
    {
        stateFinishRecording();
        getListener().recorderIdle();
        getStateContext().setState(getStateContext().stateIdle);
    }

    @Override
    public void cancelRecording()
    {
        stateCancelRecording();
        getListener().recorderIdle();
        getStateContext().setState(getStateContext().stateIdle);
    }
}
