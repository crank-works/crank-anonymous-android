package com.crankworks.crankanonymous;

import android.app.Activity;
import android.content.ComponentName;
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

import com.crankworks.trackingservice.BaseTracker;
import com.crankworks.trackingservice.ITracker;
import com.crankworks.trackingservice.ITrackObserver;
import com.crankworks.trackingservice.TrackingService;

import java.lang.Math;

public class TrackingActivity extends Activity implements ITrackObserver
{
    private static final String TAG = TrackingActivity.class.getSimpleName();

    private TextView mFieldTimestamp;
    private TextView mFieldLatitude;
    private TextView mFieldLongitude;
    private TextView mFieldSpeed;
    private TextView mFieldAccuracy;
    private TextView mFieldBearing;

    private Button mButtonRecord;
    private Button mButtonPause;
    private Button mButtonStop;
    private Button mButtonCancel;

    private final static ITracker mDummyRecorder = new BaseTracker();
    private ITracker mRecorder;

    private ITracker getRecorder()
    {
        return mRecorder != null ? mRecorder : mDummyRecorder;
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            mRecorder = (ITracker) service;
            getRecorder().attachObserver(TrackingActivity.this);
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.v(TAG, "onServiceDisconnected");
            getRecorder().detachObserver(TrackingActivity.this);
            mRecorder = null;
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentViewFromOrientation();
        findChildViews();
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
                setContentView(R.layout.tracking_portrait);
                break;

            default:
                setContentView(R.layout.tracking_portrait);
                break;
        }
    }

    private void findChildViews()
    {
        mFieldTimestamp = (TextView) findViewById(R.id.tracking_timestamp);
        mFieldLatitude = (TextView) findViewById(R.id.tracking_latitude);
        mFieldLongitude = (TextView) findViewById(R.id.tracking_longitude);
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
    }

    public void onCancelClicked(View view)
    {
        Log.v(TAG, "onCancelClicked");
        getRecorder().cancelRecording();
        finish();
    }

    private void showUserGpsDisabled()
    {
        Toast.makeText(this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
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

    public void resetFields()
    {
        mFieldTimestamp.setText("");
        mFieldLatitude.setText("");
        mFieldLongitude.setText("");
        mFieldSpeed.setText("");
        mFieldBearing.setText("");
        mFieldAccuracy.setText("");
    }

    public void trackerLocation(Location location)
    {
        String timestamp = DateUtils.formatDateTime(this, location.getTime(), DateUtils.FORMAT_SHOW_DATE |
                                                                              DateUtils.FORMAT_SHOW_TIME);

        String lat = toDms(location.getLatitude());
        String lon = toDms(location.getLongitude());
        String speed = toMph(location.getSpeed());
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy();

        //if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "timestamp: " + timestamp);
            Log.v(TAG, " position: " + lat + ", " + lon);
            Log.v(TAG, "    speed: " + speed);
            Log.v(TAG, "  bearing: " + bearing);
            Log.v(TAG, " accuracy: " + accuracy);
        }

        mFieldTimestamp.setText(timestamp);
        mFieldLatitude.setText(lat);
        mFieldLongitude.setText(lon);
        mFieldSpeed.setText(speed);
        mFieldBearing.setText(String.valueOf(bearing));
        mFieldAccuracy.setText(String.valueOf(accuracy));
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
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(true);
        mButtonCancel.setEnabled(true);
    }
}
