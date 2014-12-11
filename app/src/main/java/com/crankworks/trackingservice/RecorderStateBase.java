package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/9/14.
 */
public class RecorderStateBase implements IRecorderState
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

    public IRecorderState startRecording()
    {
        return null;
    }

    public IRecorderState pauseRecording()
    {
        return null;
    }

    public IRecorderState finishRecording()
    {
        return null;
    }

    public IRecorderState cancelRecording()
    {
        return null;
    }

    public void notifyState(IRecorderStateListener listener)
    {
    }
}
