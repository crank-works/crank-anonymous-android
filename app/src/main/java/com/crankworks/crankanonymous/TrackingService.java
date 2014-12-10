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

public class TrackingService extends Service
{
    private static final String TAG = TrackingService.class.getSimpleName();
    private final TrackingServiceBinder mBinder = new TrackingServiceBinder();
    private IRecorderStateListener mListener;

    @Override
    public void onCreate()
    {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return mBinder;
    }
}
