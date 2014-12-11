package com.crankworks.trackingservice;

/**
 * Created by marcus on 12/8/14.
 */
public class RecorderStatePause extends RecorderStateBase implements IRecorder
{
    public RecorderStatePause(TrackingServiceBinder stateContext)
    {
        super(stateContext);
    }

    public void startRecording()
    {
        getStateRecorder().stateResumeRecording();
        getStateContext().setState(getStateRecorder());
        getListener().recorderRecording();
    }

    public void finishRecording()
    {
        getStateRecorder().stateFinishRecording();
        getStateContext().setState(getStateIdle());
        getListener().recorderIdle();
    }

    public void cancelRecording()
    {
        getStateRecorder().stateCancelRecording();
        getStateContext().setState(getStateIdle());
        getListener().recorderIdle();
    }

    @Override
    public void notifyState()
    {
        getListener().recorderPaused();
    }
}
