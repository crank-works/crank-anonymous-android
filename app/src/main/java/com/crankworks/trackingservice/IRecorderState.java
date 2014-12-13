package com.crankworks.trackingservice;

import java.util.ArrayList;

/**
 * Created by marcus on 12/11/14.
 */
interface IRecorderState
{
    public IRecorderState startRecording();
    public IRecorderState pauseRecording();
    public IRecorderState finishRecording();
    public IRecorderState cancelRecording();
    public void notifyState(ArrayList<ITrackObserver> observers);
}
