package com.crankworks.trackingservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class TrackingService extends Service
{
    private static final String TAG = TrackingService.class.getSimpleName();
    private final TrackingServiceBinder mBinder = new TrackingServiceBinder();
    private final TrackingRecorder mTrackingRecorder = new TrackingRecorder(this, mBinder);

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
