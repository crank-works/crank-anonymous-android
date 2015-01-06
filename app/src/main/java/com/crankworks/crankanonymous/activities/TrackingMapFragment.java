package com.crankworks.crankanonymous.activities;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.util.Log;

import com.crankworks.crankanonymous.trackingservice.DummyTracker;
import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.ITracker;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by marcus on 1/4/15.
 */
public class TrackingMapFragment extends MapFragment implements ITrackObserver
{
    private static final String TAG = TrackingMapFragment.class.getSimpleName();

    private ITracker mTracker;

    private ITracker getTracker()
    {
        if (mTracker == null)
            Log.i(TAG, "getTracker: mTracker is null");

        return mTracker != null ? mTracker : DummyTracker.instance();
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
//    {
//        super.onCreateView(inflater, container, savedInstanceState);
//        Log.v(TAG, "onCreateView");
//        View view = inflater.inflate(R.layout.tracking_map_fragment, container, false);
//        return view;
//    }

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
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        LatLng latlon = new LatLng(lat, lon);
        CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(latlon, 15);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        getMap().animateCamera(cu);

        CircleOptions co = new CircleOptions();
        co.center(latlon);
        co.radius(5);
        co.fillColor(Color.BLUE);
        getMap().addCircle(co);
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
