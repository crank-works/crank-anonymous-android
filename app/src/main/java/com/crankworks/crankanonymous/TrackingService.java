package com.crankworks.crankanonymous;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

public class TrackingService extends Service implements LocationListener, IRecorder
{
    private static final String TAG = TrackingService.class.getSimpleName();
    private LocationManager mLocationManager;
    private String mProvider;
    private final TrackingServiceBinder mBinder = new TrackingServiceBinder(this);
    private IRecorderStateListener mListener;

    private enum State
    {
        STATE_IDLE,
        STATE_RECORDING,
        STATE_PAUSED
    }

    private State mState;

    @Override
    public void onCreate()
    {
        mState = State.STATE_IDLE;

        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (enabled)
        {
            Log.i(TAG, "GPS is enabled");

            mProvider = mLocationManager.getBestProvider(createCriteria(), false);
            Log.v(TAG, "Provider: " + mProvider);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //TODO do something useful
        return Service.START_NOT_STICKY;
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

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }

    @Override
    public void onLocationChanged(Location location)
    {
        if (mListener != null)
            mListener.recorderLocation(location);
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

    public void setListener(IRecorderStateListener listener)
    {
        mListener = listener;
        notifyState();
    }

    public void startRecording()
    {
        mLocationManager.requestLocationUpdates(mProvider, 5000, 10, this);
        setState(State.STATE_RECORDING);
    }

    public void pauseRecording()
    {
        mLocationManager.removeUpdates(this);
        setState(State.STATE_PAUSED);
    }

    public void finishRecording()
    {
        mLocationManager.removeUpdates(this);
        setState(State.STATE_IDLE);
    }

    public void cancelRecording()
    {
        mLocationManager.removeUpdates(this);
        setState(State.STATE_IDLE);
    }

    public void notifyState()
    {
        if (mListener == null)
            return;

        switch (mState)
        {
            case STATE_IDLE:
                mListener.recorderIdle();
                break;

            case STATE_PAUSED:
                mListener.recorderPaused();
                break;

            case STATE_RECORDING:
                mListener.recorderRecording();
                break;
        }
    }

    private void setState(State state)
    {
        mState = state;
        notifyState();
    }
}
