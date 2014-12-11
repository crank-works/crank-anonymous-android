package com.crankworks.trackingservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class TrackingService extends Service
{
    private static final String TAG = TrackingService.class.getSimpleName();
    private TrackingServiceBinder mBinder;

    @Override
    public void onCreate()
    {
        Log.v(TAG, "onCreate");
        mBinder = new TrackingServiceBinder(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        Log.v(TAG, "onStartCommand");
        //TODO do something useful
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        Log.v(TAG, "onBind");
        return mBinder;
    }
}
