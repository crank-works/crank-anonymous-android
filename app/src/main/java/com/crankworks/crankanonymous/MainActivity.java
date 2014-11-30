package com.crankworks.crankanonymous;

import android.app.Activity;
import android.os.Bundle;

// Launch Separate Activity
import android.view.View;
import android.content.Intent;

public class MainActivity extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    }

    public void onTrackClicked(View view) {
        Intent intent = new Intent(this, TrackingActivity.class);
        startActivity(intent);
    }
}
