package com.crankworks.crankanonymous.activities;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.text.format.DateUtils;
import android.util.Log;

import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingdatabase.DatabaseConnector;
import com.crankworks.crankanonymous.trackingservice.DummyTracker;
import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.ITracker;
import com.crankworks.crankanonymous.trackingservice.TrackingService;
import com.crankworks.crankanonymous.utilities.DisplayUnits;
import com.google.android.gms.maps.MapFragment;

/**
 * Created by marcus on 1/3/15.
 */
public class TrackingActivity extends Activity
{
    private static final String TAG = TrackingActivity.class.getSimpleName();

    private ITracker mRecorder;

    private TrackingButtonsFragment mButtonsFragment;
    private TrackingDetailsFragment mDetailsFragment;
    private TrackingMapFragment mMapFragment;

    private ITracker getRecorder()
    {
        if (mRecorder == null)
            Log.i(TAG, "getRecorder: mRecorder is null");

        return mRecorder != null ? mRecorder : DummyTracker.instance();
    }

    private ServiceConnection mConnection = new ServiceConnection()
    {
        public void onServiceConnected(ComponentName className, IBinder service)
        {
            Log.v(TAG, "onServiceConnected");
            mRecorder = (ITracker) service;
            DatabaseConnector.connectorInstance(getRecorder());

            if (mButtonsFragment != null)
                mButtonsFragment.setTracker(getRecorder());

            if (mDetailsFragment != null)
                mDetailsFragment.setTracker(getRecorder());

            if (mMapFragment != null)
                mMapFragment.setTracker(getRecorder());
        }

        public void onServiceDisconnected(ComponentName className)
        {
            Log.v(TAG, "onServiceDisconnected");
            mRecorder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        setContentViewFromOrientation();
        findFragments();
        bindTrackingService();
    }

    private void setContentViewFromOrientation()
    {
        switch (getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.tracking_landscape);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
            default:
                setContentView(R.layout.tracking_portrait);
                break;
        }
    }

    private void findFragments()
    {
        mButtonsFragment    = (TrackingButtonsFragment) getFragmentManager().findFragmentById(R.id.tracking_buttons_fragment);
        mDetailsFragment    = (TrackingDetailsFragment) getFragmentManager().findFragmentById(R.id.tracking_details_fragment);
        mMapFragment        = (TrackingMapFragment) getFragmentManager().findFragmentById(R.id.tracking_map_fragment);
//        mMapFragment        = ((MapFragment) getFragmentManager().findFragmentById(R.id.tracking_map_fragment));
//        mMapFragment.getMap().;
    }

    private void bindTrackingService()
    {
        Intent i= new Intent(getApplicationContext(), TrackingService.class);
        getApplicationContext().bindService(i, mConnection, 0);
        getApplicationContext().startService(i);
    }

    private void openFinisher()
    {
        Intent intent = new Intent(this, FinishingActivity.class);
        startActivity(intent);
    }
}
