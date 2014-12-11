package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/9/14.
 */
public class RecorderStateBase implements IRecorder
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

    protected IRecorderStateListener getListener()
    {
        return mStateContext.getListener();
    }

    public void setListener(IRecorderStateListener listener)
    {
    }

    public void startRecording()
    {
    }

    public void pauseRecording()
    {
    }

    public void finishRecording()
    {
    }

    public void cancelRecording()
    {
    }

    public void notifyState()
    {
    }
}
