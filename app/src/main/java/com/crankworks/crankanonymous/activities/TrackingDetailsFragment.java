package com.crankworks.crankanonymous.activities;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingservice.DummyTracker;
import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.ITracker;
import com.crankworks.crankanonymous.trackingservice.TrackingService;
import com.crankworks.crankanonymous.utilities.DisplayUnits;

import java.util.ArrayList;

/**
 * Created by marcus on 1/3/15.
 */
public class TrackingDetailsFragment extends Fragment implements ITrackObserver
{
    private static final String TAG = TrackingDetailsFragment.class.getSimpleName();

    private TextView mFieldTimestamp;
    private TextView mFieldLatitude;
    private TextView mFieldLongitude;
    private TextView mFieldAltitude;
    private TextView mFieldSpeed;
    private TextView mFieldAccuracy;
    private TextView mFieldBearing;

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
        View view = inflater.inflate(R.layout.tracking_details_fragment, container, false);
        findChildViews(view);
        return view;
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

    private void findChildViews(View view)
    {
        mFieldTimestamp = (TextView) view.findViewById(R.id.tracking_timestamp);
        mFieldLatitude  = (TextView) view.findViewById(R.id.tracking_latitude);
        mFieldLongitude = (TextView) view.findViewById(R.id.tracking_longitude);
        mFieldAltitude  = (TextView) view.findViewById(R.id.tracking_altitude);
        mFieldSpeed     = (TextView) view.findViewById(R.id.tracking_speed);
        mFieldAccuracy  = (TextView) view.findViewById(R.id.tracking_accuracy);
        mFieldBearing   = (TextView) view.findViewById(R.id.tracking_bearing);
    }

    private void setCurrentDetails(Location location)
    {
        DisplayUnits displayUnits = DisplayUnits.instance(getActivity());

        String timestamp = DateUtils.formatDateTime(getActivity(),
                location.getTime(),
                DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_SHOW_TIME);

        String lat = toDms(location.getLatitude());
        String lon = toDms(location.getLongitude());
        String alt = displayUnits.formatAltitude(location.getAltitude());
        String speed = displayUnits.formatSpeed(location.getSpeed());
        String accuracy = displayUnits.formatAccuracy(location.getAccuracy());
        String bearing = displayUnits.formatBearing(location.getBearing());

        //if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "timestamp: " + timestamp);
            Log.v(TAG, " position: " + lat + ", " + lon);
            Log.v(TAG, " altitude: " + alt);
            Log.v(TAG, "    speed: " + speed);
            Log.v(TAG, "  bearing: " + bearing);
            Log.v(TAG, " accuracy: " + accuracy);
        }

        mFieldTimestamp.setText(timestamp);
        mFieldLatitude.setText(lat);
        mFieldLongitude.setText(lon);
        mFieldAltitude.setText(alt);
        mFieldSpeed.setText(speed);
        mFieldBearing.setText(String.valueOf(bearing));
        mFieldAccuracy.setText(accuracy);
    }

    /* ITrackObserver interface */
    public void trackerAttach(Context context, Location currentLocation, ArrayList<Location> locationList)
    {
        Log.v(TAG, "trackerAttach");
        if (currentLocation != null)
            setCurrentDetails(currentLocation);
    }

    public void trackerDetach()
    {
        Log.v(TAG, "trackerDetach");
    }

    public void trackerLocation(Location location, ArrayList<Location> locationList)
    {
        Log.v(TAG, "trackerLocation");
        setCurrentDetails(location);
    }

    private String toDms(double value)
    {
        double degFrac = value % 1;
        int degWhole = (int) (value - degFrac);

        double minutes = 60 * degFrac;
        double minFrac = minutes % 1;
        int minWhole = (int) Math.abs(minutes - minFrac);

        double seconds = 60 * minFrac;
        double secFrac = seconds % 1;
        int secWhole = (int) Math.abs(seconds - secFrac);

        return String.format("%02d:%02d:%02d", degWhole, minWhole, secWhole);
    }

    public void trackerIdle()
    {
        Log.v(TAG, "trackerIdle");
    }

    public void trackerRecording()
    {
        Log.v(TAG, "trackerRecording");
    }

    public void trackerPaused()
    {
        Log.v(TAG, "trackerPaused");
    }
}
