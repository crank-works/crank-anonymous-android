package com.crankworks.trackingservice;

import java.util.ArrayList;

/**
 * Created by marcus on 12/9/14.
 */
class RecorderStateBase implements IRecorderState
{
    private static final String TAG = RecorderStateBase.class.getSimpleName();
    private TrackingServiceBinder mStateContext;

    public RecorderStateBase(TrackingServiceBinder stateContext)
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

    /* IRecorderState interface */

    public IRecorderState startRecording()
    {
        return this;
    }

    public IRecorderState pauseRecording()
    {
        return this;
    }

    public IRecorderState finishRecording()
    {
        return this;
    }

    public IRecorderState cancelRecording()
    {
        return this;
    }

    public void notifyState(ArrayList<ITrackObserver> observers)
    {
    }
}
