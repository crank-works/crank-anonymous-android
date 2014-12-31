package com.crankworks.crankanonymous.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.crankworks.crankanonymous.utilities.DisplayUnits;
import com.crankworks.crankanonymous.R;
import com.crankworks.trackingactivity.TrackingActivity;
import com.crankworks.trackingdatabase.Database;

public class MainActivity extends Activity
{
    private static final String TAG = MainActivity.class.getSimpleName();

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        Log.v(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        setContentView(R.layout.main);
        setActionBar();
    }

    public void onResume()
    {
        Log.v(TAG, "onResume");
        super.onResume();
        setDatabaseTrips();
        setDistance();
    }

    private void setDatabaseTrips()
    {
        TextView viewTrips = (TextView) findViewById(R.id.main_number_trips);
        int count = new Database(this).tripCount();
        viewTrips.setText(String.valueOf(count));
    }

    private void setDistance()
    {
        DisplayUnits displayUnits = DisplayUnits.instance(this);
        TextView viewDistance= (TextView) findViewById(R.id.main_total_distance);
        int distance = new Database(this).totalDistance();
        viewDistance.setText(displayUnits.formatDistance(distance));
    }

    private void setActionBar()
    {
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        boolean rv = true;

        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_track:
                openTracker();
                break;

            case R.id.action_settings:
                openSettings();
                break;

            default:
                rv = super.onOptionsItemSelected(item);
        }

        return rv;
    }

    private void openTracker()
    {
        Intent intent = new Intent(this, TrackingActivity.class);
        startActivity(intent);
    }

    private void openSettings()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }
}
