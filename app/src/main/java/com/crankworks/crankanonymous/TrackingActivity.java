package com.crankworks.crankanonymous;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.content.DialogInterface;
import android.widget.Toast;


public class TrackingActivity extends Activity implements LocationListener
{
    private static final String TAG = TrackingActivity.class.getSimpleName();
    private LocationManager mLocationManager;
    private String mProvider;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking);
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        boolean enabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        if (enabled)
        {
            Log.i(TAG, "GPS is enabled");
            Criteria criteria = createCriteria();
            mProvider = mLocationManager.getBestProvider(criteria, false);
            Log.v(TAG, "Provider: " + mProvider);
            Location location = mLocationManager.getLastKnownLocation(mProvider);
            if (location != null)
                onLocationChanged(location);
        }
    }

    private Criteria createCriteria()
    {
        Log.v(TAG, "createCriteria");
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setSpeedRequired(false);
        return criteria;
    }

    private void showUserGpsDisabled()
    {
        Toast.makeText(this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocationManager.requestLocationUpdates(mProvider, 400, 1, this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
    }

    public void onCancelClicked(View view)
    {
        finish();
    }

    @Override
    public void onLocationChanged(Location location)
    {
        int lat = (int) location.getLatitude();
        int lon = (int) location.getLongitude();
        Log.v(TAG, "onLocationChanged: " + lat + ", " + lon);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {
        Log.v(TAG, "onStatusChanged: " + provider + ", status: " + status);
    }

    @Override
    public void onProviderEnabled(String provider)
    {
        Log.v(TAG, "onProviderEnabled: " + provider);
    }

    @Override
    public void onProviderDisabled(String provider)
    {
        Log.v(TAG, "onProviderDisabled: " + provider);
    }
}
