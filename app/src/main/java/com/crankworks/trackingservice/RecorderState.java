package com.crankworks.trackingservice;

import java.util.ArrayList;

/**
 * Created by marcus on 12/9/14.
 */
class RecorderState
{
    private static final String TAG = RecorderState.class.getSimpleName();
    private TrackingServiceBinder mStateContext;

    public RecorderState(TrackingServiceBinder stateContext)
    {
        mStateContext = stateContext;
    }

    protected TrackingServiceBinder getStateContext()
    {
        return mStateContext;
    }

    protected RecorderStateIdle getStateIdle()
    {
        return getStateContext().stateIdle;
    }

    protected RecorderStateRecord getStateRecorder()
    {
        return getStateContext().stateRecord;
    }

    protected RecorderStatePause getStatePause()
    {
        return getStateContext().statePause;
    }

    protected ArrayList<ITrackObserver> getObservers()
    {
        return mStateContext.getObservers();
    }

    /* RecorderState interface */

    public RecorderState startRecording()
    {
        return this;
    }

    public RecorderState pauseRecording()
    {
        return this;
    }

    public RecorderState finishRecording()
    {
        return this;
    }

    public RecorderState cancelRecording()
    {
        return this;
    }

    public void notifyState(ArrayList<ITrackObserver> observers)
    {
    }
}
