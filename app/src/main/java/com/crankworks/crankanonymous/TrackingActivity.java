package com.crankworks.crankanonymous;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Configuration;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.lang.Math;

public class TrackingActivity extends Activity implements IRecorderStateListener
{
    private static final String TAG = TrackingActivity.class.getSimpleName();

    private TextView fieldLatitude;
    private TextView fieldLongitude;
    private TextView fieldSpeed;
    private TextView fieldAccuracy;
    private TextView fieldBearing;

    private Button mButtonRecord;
    private Button mButtonPause;
    private Button mButtonStop;
    private Button mButtonCancel;

    private IRecorder mRecorder = new DummyRecorder();

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            mRecorder = (IRecorder) service;
            if (mRecorder == null)
            {
                Log.d(TAG, "mRecorder is null, creating dummy recorder");
                mRecorder = new DummyRecorder();
            }
            else
                mRecorder.setListener(TrackingActivity.this);
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.v(TAG, "onServiceDisconnected");
            mRecorder = new DummyRecorder();
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
        fieldLatitude = (TextView) findViewById(R.id.tracking_latitude);
        fieldLongitude = (TextView) findViewById(R.id.tracking_longitude);
        fieldSpeed = (TextView) findViewById(R.id.tracking_speed);
        fieldAccuracy = (TextView) findViewById(R.id.tracking_accuracy);
        fieldBearing = (TextView) findViewById(R.id.tracking_bearing);

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
        mRecorder.startRecording();
    }

    public void onPauseClicked(View view)
    {
        Log.v(TAG, "onPauseClicked");
        mRecorder.pauseRecording();
    }

    public void onStopClicked(View view)
    {
        Log.v(TAG, "onStopClicked");
        mRecorder.finishRecording();
    }

    public void onCancelClicked(View view)
    {
        Log.v(TAG, "onCancelClicked");
        mRecorder.cancelRecording();
    }

    private void showUserGpsDisabled()
    {
        Toast.makeText(this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecorder.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mRecorder.setListener(null);
    }

    public void recorderLocation(Location location)
    {
        double lat = location.getLatitude();
        double lon = location.getLongitude();
        float speed = location.getSpeed();
        float bearing = location.getBearing();
        float accuracy = location.getAccuracy();

        if (Log.isLoggable(TAG, Log.VERBOSE))
        {
            Log.v(TAG, "onLocationChanged: " + lat + ", " + lon);
            Log.v(TAG, "            speed: " + toMph(speed));
            Log.v(TAG, "          bearing: " + bearing);
            Log.v(TAG, "         accuracy: " + accuracy);
        }

            Log.v(TAG, "onLocationChanged: " + lat + ", " + lon +
                       ", speed: " + toMph(speed) +
                       ", bearing: " + bearing +
                       ", accuracy: " + accuracy);

        fieldLatitude.setText(toDms(lat));
        fieldLongitude.setText(toDms(lon));
        fieldSpeed.setText(String.valueOf(toMph(speed)));
        fieldBearing.setText(String.valueOf(bearing));
        fieldAccuracy.setText(String.valueOf(accuracy));
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

    public void recorderIdle()
    {
        Log.v(TAG, "recorderIdle");
        mButtonRecord.setEnabled(true);
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(false);
        mButtonCancel.setEnabled(true);
    }

    public void recorderRecording()
    {
        Log.v(TAG, "recorderRecording");
        mButtonRecord.setEnabled(false);
        mButtonPause.setEnabled(true);
        mButtonStop.setEnabled(true);
        mButtonCancel.setEnabled(true);
    }

    public void recorderPaused()
    {
        Log.v(TAG, "recorderPaused");
        mButtonRecord.setEnabled(true);
        mButtonPause.setEnabled(false);
        mButtonStop.setEnabled(true);
        mButtonCancel.setEnabled(true);
    }
}
