package com.crankworks.crankanonymous;

import android.app.Activity;
import android.content.Context;
import android.location.Criteria;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.content.DialogInterface;


public class TrackingActivity extends Activity
{
    private static final String TAG = TrackingActivity.class.getSimpleName();
    private LocationManager mLocationManager;

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
        }
        else
        {
            Log.i(TAG, "GPS is disabled");
            AlertDialog.Builder dlgAlert  = createGpsDisabledAlertDialog();
            dlgAlert.create().show();
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

    public void onCancelClicked(View view)
    {
        Log.v(TAG, "onCancelClicked");
        AlertDialog.Builder dlgAlert  = createAlertDialog();
        dlgAlert.create().show();
    }

    private AlertDialog.Builder createAlertDialog()
    {
        Log.v(TAG, "createAlertDialog");
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("Cancel");
        dlgAlert.setMessage("You clicked the cancel button");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        dlgAlert.setCancelable(true);
        return dlgAlert;
    }

    private AlertDialog.Builder createGpsDisabledAlertDialog()
    {
        Log.v(TAG, "createGpsDisabledAlertDialog");
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setTitle("GPS Disabled");
        dlgAlert.setMessage("GPS is Disabled");
        dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                finish();
            }
        });
        dlgAlert.setCancelable(false);
        return dlgAlert;
    }
}
