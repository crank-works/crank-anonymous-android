package com.crankworks.crankanonymous.activities;

import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.crankworks.crankanonymous.utilities.DisplayUnits;
import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingdatabase.DatabaseConnector;
import com.crankworks.crankanonymous.trackingservice.DummyTracker;
import com.crankworks.crankanonymous.trackingservice.ITracker;
import com.crankworks.crankanonymous.trackingservice.ITrackObserver;
import com.crankworks.crankanonymous.trackingservice.TrackingService;

import java.lang.Math;

public class OriginalTrackingActivity extends Activity implements ITrackObserver
{
    private static final String TAG = OriginalTrackingActivity.class.getSimpleName();

    private TextView mFieldTimestamp;
    private TextView mFieldLatitude;
    private TextView mFieldLongitude;
    private TextView mFieldAltitude;
    private TextView mFieldSpeed;
    private TextView mFieldAccuracy;
    private TextView mFieldBearing;

    private Button mButtonRecord;
    private Button mButtonPause;
    private Button mButtonStop;
    private Button mButtonCancel;

    private ITracker mRecorder;

    private ITracker getRecorder()
    {
        if (mRecorder == null)
            Log.i(TAG, "getRecorder: mRecorder is null");

        return mRecorder != null ? mRecorder : DummyTracker.instance();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            mRecorder = (ITracker) service;
            getRecorder().attachObserver(OriginalTrackingActivity.this);
            DatabaseConnector.connectorInstance(getRecorder());
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.v(TAG, "onServiceDisconnected");
            getRecorder().detachObserver(OriginalTrackingActivity.this);
            mRecorder = null;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentViewFromOrientation();
        findChildViews();
        bindTrackingService();
    }

    @Override
    protected void onResume()
    {
        Log.v(TAG, "onResume");
        super.onResume();
        getRecorder().attachObserver(this);
    }

    @Override
    protected void onPause()
    {
        Log.v(TAG, "onPause");
        super.onPause();
        getRecorder().detachObserver(this);
    }

    @Override
    protected void onDestroy()
    {
        Log.v(TAG, "onDestroy");
        super.onDestroy();
        if (isFinishing())
            mRecorder.detachObserver(null);
    }

    private void setContentViewFromOrientation()
    {
        switch (getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.original_tracking_landscape);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.original_tracking_portrait);
                break;

            default:
                setContentView(R.layout.original_tracking_portrait);
                break;
        }
    }

    private void findChildViews()
    {
        mFieldTimestamp = (TextView) findViewById(R.id.tracking_timestamp);
        mFieldLatitude = (TextView) findViewById(R.id.tracking_latitude);
        mFieldLongitude = (TextView) findViewById(R.id.tracking_longitude);
        mFieldAltitude = (TextView) findViewById(R.id.tracking_altitude);
        mFieldSpeed = (TextView) findViewById(R.id.tracking_speed);
        mFieldAccuracy = (TextView) findViewById(R.id.tracking_accuracy);
        mFieldBearing = (TextView) findViewById(R.id.tracking_bearing);

        mButtonRecord = (Button) findViewById(R.id.button_record);
        mButtonPause = (Button) findViewById(R.id.button_pause);
        mButtonStop = (Button) findViewById(R.id.button_stop);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);
    }

    private void bindTrackingService()
    {
        Intent i= new Intent(getApplicationContext(), TrackingService.class);
        getApplicationContext().bindService(i, mConnection, 0);
        getApplicationContext().startService(i);
    }

    public void onRecordClicked(View view)
    {
        Log.v(TAG, "onRecordClicked");
        getRecorder().startRecording();
    }

    public void onPauseClicked(View view)
    {
        Log.v(TAG, "onPauseClicked");
        getRecorder().pauseRecording();
    }

    public void onStopClicked(View view)
    {
        Log.v(TAG, "onStopClicked");
        getRecorder().finishRecording();
        boolean isTrip = DatabaseConnector.connectorInstance().onFinished();
        if (isTrip)
            openFinisher();
        finish();
    }

    public void onCancelClicked(View view)
    {
        Log.v(TAG, "onCancelClicked");
        getRecorder().cancelRecording();
        DatabaseConnector.connectorInstance().onCanceled();
        finish();
    }

    private void showUserGpsDisabled()
    {
        Toast.makeText(this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
    }

    public void resetFields()
    {
        mFieldTimestamp.setText("");
        mFieldLatitude.setText("");
        mFieldLongitude.setText("");
        mFieldSpeed.setText("");
        mFieldBearing.setText("");
        mFieldAccuracy.setText("");
    }

    public void trackerAttach(Context context)
    {
    }

    public void trackerDetach()
    {
    }

    public void trackerLocation(Location location)
    {
        DisplayUnits displayUnits = DisplayUnits.instance(this);

        String timestamp = DateUtils.formatDateTime(this, location.getTime(), DateUtils.FORMAT_SHOW_DATE |
                                                                              DateUtils.FORMAT_SHOW_TIME);

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

    private String toMph(float mps)
    {
        return String.valueOf(mps * 3600.0 / 1609.334);
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
        mButtonRecord.setEnabled(true);
        mButtonRecord.setText(R.string.button_record);
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(false);
        mButtonCancel.setEnabled(false);
        resetFields();
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

    private void openFinisher()
    {
        Intent intent = new Intent(this, FinishingActivity.class);
        startActivity(intent);
    }
}
