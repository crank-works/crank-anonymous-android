package com.crankworks.crankanonymous.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingservice.DummyTracker;
import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.ITracker;

import java.util.ArrayList;

/**
 * Created by marcus on 1/4/15.
 */
public class TrackingButtonsFragment extends Fragment implements ITrackObserver
{
    private static final String TAG = TrackingButtonsFragment.class.getSimpleName();

    private View mView;

    private Button mButtonRecord;
    private Button mButtonPause;
    private Button mButtonStop;
    private Button mButtonCancel;

    private ITracker mTracker;

    private ITracker getTracker()
    {
        if (mTracker == null)
            Log.i(TAG, "getTracker: mTracker is null");

        return mTracker != null ? mTracker : DummyTracker.instance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        super.onCreateView(inflater, container, savedInstanceState);
        Log.v(TAG, "onCreateView");

        mView =  getViewFromOrientation(inflater, container);
        wireButtons(mView);
        return mView;
    }

    @Override
    public void onStart()
    {
        Log.v(TAG, "onStart");
        super.onStart();
        getTracker().attachObserver(this);
    }

    @Override
    public void onStop()
    {
        Log.v(TAG, "onStop");
        getTracker().detachObserver(this);
        super.onStop();
    }

    void setTracker(ITracker tracker)
    {
        Log.v(TAG, "setTracker");
        mTracker = tracker;
        mTracker.attachObserver(this);
    }

    private View getViewFromOrientation(LayoutInflater inflater, ViewGroup container)
    {
        View view;

        switch (getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
                view = inflater.inflate(R.layout.tracking_buttons_landscape_fragment, container, false);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
            default:
                view = inflater.inflate(R.layout.tracking_buttons_portrait_fragment, container, false);
                break;
        }

        return view;
    }

    private void wireButtons(View view)
    {
        wireRecordButton(view);
        wirePauseButton(view);
        wireStopButton(view);
        wireCancelButton(view);
    }

    private void wireRecordButton(View view)
    {
        mButtonRecord = (Button) view.findViewById(R.id.button_record);
        mButtonRecord.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            { getTracker().startRecording(); }
        });
    }

    private void wirePauseButton(View view)
    {
        mButtonPause = (Button) view.findViewById(R.id.button_pause);
        mButtonPause.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            { getTracker().pauseRecording(); }
        });
    }

    private void wireStopButton(View view)
    {
        mButtonStop = (Button) view.findViewById(R.id.button_stop);
        mButtonStop.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            { getTracker().finishRecording(); }
        });
    }

    private void wireCancelButton(View view)
    {
        mButtonCancel = (Button) view.findViewById(R.id.button_cancel);
        mButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v)
            { getTracker().cancelRecording(); }
        });
    }

    /* ITrackObserver interface */

    public void trackerAttach(Context context)
    {
        Log.v(TAG, "trackerAttach");
    }

    public void trackerDetach()
    {
        Log.v(TAG, "trackerDetach");
    }

    public void trackerLocation(Location location, ArrayList<Location> locationList)
    {
        Log.v(TAG, "trackerLocation");
    }

    public void trackerIdle()
    {
        Log.v(TAG, "trackerIdle");
        mButtonRecord.setEnabled(true);
        mButtonRecord.setText(R.string.button_record);
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(false);
        mButtonCancel.setEnabled(false);
    }

    public void trackerRecording()
    {
        Log.v(TAG, "trackerRecording");
        mButtonRecord.setEnabled(false);
        mButtonPause.setEnabled(true);
        mButtonStop.setEnabled(true);
        mButtonCancel.setEnabled(true);
    }

    public void trackerPaused()
    {
        Log.v(TAG, "trackerPaused");
        mButtonRecord.setEnabled(true);
        mButtonRecord.setText(R.string.button_resume);
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(true);
        mButtonCancel.setEnabled(true);
    }
}
