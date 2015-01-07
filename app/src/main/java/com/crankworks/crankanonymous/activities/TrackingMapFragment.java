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
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

/**
 * Created by marcus on 1/4/15.
 */
public class TrackingMapFragment extends MapFragment implements ITrackObserver, LocationSource
{
    private static final String TAG = TrackingMapFragment.class.getSimpleName();

    private ITracker mTracker;
    LocationSource.OnLocationChangedListener mLocationListener;

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
        getMap().setLocationSource(this);
        getMap().setMyLocationEnabled(true);
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

    private void setCameraPosition(Location location)
    {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        LatLng latlon = new LatLng(lat, lon);
        CameraPosition cameraPosition = CameraPosition.fromLatLngZoom(latlon, 15);
        CameraUpdate cu = CameraUpdateFactory.newCameraPosition(cameraPosition);
        getMap().animateCamera(cu);
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

        if (mLocationListener != null)
        {
            mLocationListener.onLocationChanged(location);
            setCameraPosition(location);
        }
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

    /* LocationSource interface */

    public void activate(LocationSource.OnLocationChangedListener locationListener)
    {
        if (locationListener == null)
            throw new IllegalArgumentException(TAG + ": activate: locationListener is null");

        if (mLocationListener != null)
            throw new IllegalStateException(TAG + ": activate: already activated");

        mLocationListener = locationListener;
    }

    public void deactivate()
    {
        mLocationListener = null;
    }
}
