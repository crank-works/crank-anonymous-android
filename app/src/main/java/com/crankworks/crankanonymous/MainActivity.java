package com.crankworks.crankanonymous;

import android.app.ActionBar;
import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;

// For action bar
import android.view.Menu;
import android.view.MenuInflater;

// Launch Separate Activity
import android.view.MenuItem;
import android.content.Intent;
import android.widget.TextView;

import com.crankworks.trackingactivity.TrackingActivity;
import com.crankworks.trackingdatabase.Database;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        setDatabaseTrips();
        setActionBar();
    }

    private void setDatabaseTrips()
    {
        TextView viewTrips = (TextView) findViewById(R.id.database_trips);
        Database db = null;

        try
        {
            db = new Database(this).open();
            Cursor cursor = db.getTrips();
            viewTrips.setText("Number of trips: " + cursor.getCount());
        }

        finally
        {
            if (db != null)
                db.close();
        }
    }

    private void setActionBar()
    {
        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_track:
                openTracker();
                return true;
            case R.id.action_settings:
                //openSettings();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openTracker() {
        Intent intent = new Intent(this, TrackingActivity.class);
        startActivity(intent);
    }
}
