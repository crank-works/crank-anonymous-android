package com.crankworks.crankanonymous.activities;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.SimpleCursorAdapter;

import com.crankworks.crankanonymous.R;
import com.crankworks.crankanonymous.trackingdatabase.Database;
import com.crankworks.crankanonymous.trackingdatabase.TableTrips;
import com.crankworks.crankanonymous.utilities.DisplayUnits;

import java.text.SimpleDateFormat;

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
        String[] listTableColumns = new String[]
        {
            TableTrips.COLUMN_START_TIME,
            TableTrips.COLUMN_DISTANCE
        };

        int[] listViewIds = new int[]
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
                listTableColumns,
                listViewIds,
                0);

        dataAdapter.setViewBinder( new SimpleCursorAdapter.ViewBinder()
        {
            SimpleDateFormat formatDate = new SimpleDateFormat("yyyy/MM/dd");
            DisplayUnits displayUnits = DisplayUnits.instance(ViewingActivity.this);

            @Override
            public boolean setViewValue(View view, Cursor cursor, int column)
            {
                if (column == cursor.getColumnIndex(TableTrips.COLUMN_START_TIME))
                {
                    TextView tv = (TextView) view;
                    long date = cursor.getLong(column);
                    String dateStr = formatDate.format(date);
                    tv.setText(dateStr);
                    return true;
                }
                else if (column == cursor.getColumnIndex(TableTrips.COLUMN_DISTANCE))
                {
                    TextView tv = (TextView) view;
                    double distance = cursor.getDouble(column);
                    String distanceStr = displayUnits.formatDistance(distance);
                    tv.setText(distanceStr);
                    return true;
                }

                return false;
            }
        });

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
