package com.crankworks.crankanonymous;

import android.app.Activity;
import android.os.Bundle;
//import android.support.v7.app.ActionBarActivity;

// For action bar
import android.view.Menu;
import android.view.MenuInflater;

// Launch Separate Activity
import android.view.MenuItem;
import android.content.Intent;

import com.crankworks.TrackingActivity.TrackingActivity;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getActionBar().setDisplayHomeAsUpEnabled(true);
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
