package com.crankworks.crankanonymous;

import android.os.Binder;

/**
 * Created by marcus on 12/7/14.
 */

public class TrackingServiceBinder extends Binder implements IRecorder
{
    private TrackingService mTrackingService;

    public TrackingServiceBinder(TrackingService trackingService)
    {
        mTrackingService = trackingService;
    }

    public void setListener(IRecorderStateListener listener)
    {
        mTrackingService.setListener(listener);
    }

    public void startRecording()
    {
        mTrackingService.startRecording();
    }

    public void pauseRecording()
    {
        mTrackingService.pauseRecording();
    }

    public void finishRecording()
    {
        mTrackingService.finishRecording();
    }

    public void cancelRecording()
    {
        mTrackingService.cancelRecording();
    }

    public void notifyState()
    {
        mTrackingService.notifyState();
    }
}
