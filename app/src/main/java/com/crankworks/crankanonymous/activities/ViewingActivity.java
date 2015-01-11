package com.crankworks.crankanonymous.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingdatabase.Database;
import com.crankworks.crankanonymous.trackingdatabase.TableTrips;

/**
 * Created by marcus on 1/10/15.
 */
public class ViewingActivity extends Activity
{
    private static final String TAG = TrackingActivity.class.getSimpleName();

    private ListView mTripList;
    private Database mDb;
    private SimpleCursorAdapter dataAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");

        setContentView(R.layout.viewing_trip_list);
        mTripList = (ListView) findViewById(R.id.viewing_trip_list);
        populateList();
    }

    private void populateList()
    {
        String[] columns = new String[]
        {
            TableTrips.COLUMN_START_TIME,
            TableTrips.COLUMN_DISTANCE
        };

        // the XML defined views which the data will be bound to
        int[] to = new int[]
        {
            R.id.view_time_value,
            R.id.view_distance_value
        };

        Database mDb = new Database(this);
        mDb.open();
        Cursor cursor = mDb.getTrips();

        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.viewing_trip_info,
                cursor,
                columns,
                to,
                0);

        mTripList.setAdapter(dataAdapter);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.v(TAG, "onStart");
    }

    @Override
    protected void onStop()
    {
        Log.v(TAG, "onStop");
        super.onStop();
    }
}
