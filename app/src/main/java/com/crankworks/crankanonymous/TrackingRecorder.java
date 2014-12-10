package com.crankworks.crankanonymous;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by marcus on 12/9/14.
 */
public class TrackingRecorder
{
    private static final String TAG = TrackingRecorder.class.getSimpleName();

    private Context mContext;
    private IRecorderStateListener mListener;
    private LocationManager mLocationManager;

    public TrackingRecorder(Context context)
    {
        mContext = context;
        mLocationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (enabled)
        {
            Log.i(TAG, "GPS is enabled");

            String provider = mLocationManager.getBestProvider(createCriteria(), false);
            Log.v(TAG, "Provider: " + provider);
        }
    }


    private Criteria createCriteria()
    {
        Log.v(TAG, "createCriteria");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(true);
        return criteria;
    }

    public void setListener(IRecorderStateListener listener)
    {
        mListener = listener;
    }

    public IRecorderStateListener getListener()
    {
        return mListener;
    }
}
