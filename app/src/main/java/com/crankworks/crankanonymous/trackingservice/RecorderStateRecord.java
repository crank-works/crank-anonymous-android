package com.crankworks.crankanonymous.trackingservice;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.Manifest;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by marcus on 12/8/14.
 */
class RecorderStateRecord extends RecorderState implements LocationListener
{
    private static final String TAG = RecorderStateRecord.class.getSimpleName();

    private LocationManager mLocationManager;
    private String mProvider;
    private Location mLastLocation;
    private ArrayList<Location> mLocationList = new ArrayList<>();

    public RecorderStateRecord(TrackingServiceBinder stateContext,
                               LocationManager locationManager)
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
        criteria.setAltitudeRequired(true);
        criteria.setBearingRequired(true);
        criteria.setSpeedRequired(true);
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        return criteria;
    }

    public void reset()
    {
        mLocationList = new ArrayList<>();
    }

    public Location getLastLocation()
    {
        return mLastLocation;
    }

    public ArrayList<Location> getLocationList()
    {
        return mLocationList;
    }

    /* LocationListener interface */

    @Override
    public void onLocationChanged(Location location)
    {
        Log.v(TAG, "onLocationChanged");

        mLocationList.add(location);

        for (ITrackObserver observer : getObservers())
            observer.trackerLocation(location, mLocationList);

        mLastLocation = location;
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

    private boolean isLocationUpdatesPermitted()
    {
        Context context = getStateContext().getContext();
        return context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestLocationUpdates()
    {
        if (isLocationUpdatesPermitted())
            mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
    }

    void stateBeginRecording()
    {
        Log.v(TAG, "stateBeginRecording");
        requestLocationUpdates();
    }

    void stateResumeRecording()
    {
        Log.v(TAG, "stateResumeRecording");
        requestLocationUpdates();
    }

    void stateFinishRecording()
    {
        Log.v(TAG, "stateFinishRecording");
        if (isLocationUpdatesPermitted())
            mLocationManager.removeUpdates(this);
    }

    void stateCancelRecording()
    {
        Log.v(TAG, "stateCancelRecording");
        if (isLocationUpdatesPermitted())
            mLocationManager.removeUpdates(this);
    }

    /* RecorderState interface */

    @Override
    public RecorderState pauseRecording()
    {
        Log.v(TAG, "pauseRecording");
        if (isLocationUpdatesPermitted())
            mLocationManager.removeUpdates(this);
        return getStatePause();
    }

    @Override
    public RecorderState finishRecording()
    {
        Log.v(TAG, "finishRecording");
        stateFinishRecording();
        return getStateIdle();
    }

    @Override
    public RecorderState cancelRecording()
    {
        Log.v(TAG, "cancelRecording");
        stateCancelRecording();
        return getStateIdle();
    }

    @Override
    public void notifyState(ArrayList<ITrackObserver> observers)
    {
        Log.v(TAG, "notifyState");

        for (ITrackObserver observer : observers)
            observer.trackerRecording();
    }
}
