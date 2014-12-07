package com.crankworks.crankanonymous;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

    private Button mButtonRecord;
    private Button mButtonPause;
    private Button mButtonStop;
    private Button mButtonCancel;

    private IRecorder mRecorder;

    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.v(TAG, "onServiceConnected");
            mRecorder = (IRecorder) service;
            if (mRecorder == null)
                Log.d(TAG, "mRecorder is null");
            else
                mRecorder.setListener(TrackingActivity.this);
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.v(TAG, "onServiceDisconnected");
            mRecorder = null;
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tracking);

        fieldLatitude = (TextView) findViewById(R.id.tracking_latitude);
        fieldLongitude = (TextView) findViewById(R.id.tracking_longitude);

        mButtonRecord = (Button) findViewById(R.id.button_record);
        mButtonPause = (Button) findViewById(R.id.button_pause);
        mButtonStop = (Button) findViewById(R.id.button_stop);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);

        Intent i= new Intent(getApplicationContext(), TrackingService.class);
        getApplicationContext().bindService(i, mConnection, 0);
        getApplicationContext().startService(i);
    }

    public void onRecordClicked(View view)
    {
        Log.v(TAG, "onRecordClicked");
        if (mRecorder != null)
        {
            Log.v(TAG, "Start recording");
            mRecorder.startRecording();
        }
        else
            Log.v(TAG, "mRecorder is null");
    }

    public void onPauseClicked(View view)
    {
        Log.v(TAG, "onPauseClicked");
        if (mRecorder != null)
        {
            Log.v(TAG, "Pause recording");
            mRecorder.pauseRecording();
        }
        else
            Log.v(TAG, "mRecorder is null");
    }

    public void onStopClicked(View view)
    {
        Log.v(TAG, "onStopClicked");
        if (mRecorder != null)
            mRecorder.finishRecording();
    }

    public void onCancelClicked(View view)
    {
        Log.v(TAG, "onCancelClicked");
        if (mRecorder != null)
            mRecorder.cancelRecording();
    }

    private void showUserGpsDisabled()
    {
        Toast.makeText(this, "GPS is Disabled", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mRecorder != null)
            mRecorder.setListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mRecorder != null)
            mRecorder.setListener(null);
    }

    public void recorderLocation(Location location)
    {
        double lat = location.getLatitude();
        double lon = location.getLongitude();

        Log.v(TAG, "onLocationChanged: " + lat + ", " + lon);
        fieldLatitude.setText(toDms(lat));
        fieldLongitude.setText(toDms(lon));
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
        mButtonStop.setEnabled(true);
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
