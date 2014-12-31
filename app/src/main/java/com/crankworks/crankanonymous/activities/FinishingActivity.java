package com.crankworks.crankanonymous.activities;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crankworks.crankanonymous.utilities.DisplayUnits;
import com.crankworks.crankanonymous.R;
import com.crankworks.trackingdatabase.Database;
import com.crankworks.trackingdatabase.TableTrips;


public class FinishingActivity extends Activity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = FinishingActivity.class.getSimpleName();

    private Spinner fieldObjective;
    private TextView fieldElapsedTime;
    private TextView fieldDistance;
    private TextView fieldAverageSpeed;
    private TextView fieldTopSpeed;
    private TextView fieldTotalClimb;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentViewFromOrientation();
        findChildViews();
        populateObjective();
        populateTripFields();
    }

    private void setContentViewFromOrientation()
    {
        switch (getResources().getConfiguration().orientation)
        {
            case Configuration.ORIENTATION_LANDSCAPE:
                setContentView(R.layout.finishing_landscape);
                break;

            case Configuration.ORIENTATION_PORTRAIT:
                setContentView(R.layout.finishing_portrait);
                break;

            default:
                setContentView(R.layout.finishing_portrait);
                break;
        }
    }

    private void findChildViews()
    {
        fieldObjective = (Spinner) findViewById(R.id.finishing_objective);
        fieldObjective.setOnItemSelectedListener(this);

        fieldElapsedTime = (TextView) findViewById(R.id.finishing_elapsed_time);
        fieldDistance = (TextView) findViewById(R.id.finishing_distance);
        fieldAverageSpeed = (TextView) findViewById(R.id.finishing_average_speed);
        fieldTopSpeed = (TextView) findViewById(R.id.finishing_top_speed);
        fieldTotalClimb = (TextView) findViewById(R.id.finishing_total_climb);
    }

    private void populateObjective()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.objectives_array,
                android.R.layout.simple_spinner_dropdown_item);

        fieldObjective.setAdapter(adapter);
    }

    private void populateTripFields()
    {
        DisplayUnits displayUnits = DisplayUnits.instance(this);

        Database db = new Database(this);
        TableTrips.Row trip = db.getLastTrip();

        if (trip != null)
        {
            fieldElapsedTime.setText(displayUnits.formatElapsedTime(trip.end_time - trip.start_time));
            fieldDistance.setText(displayUnits.formatDistance(trip.distance));
            fieldAverageSpeed.setText(displayUnits.formatSpeed(getAverageSpeedInMps(trip)));
            fieldTopSpeed.setText(displayUnits.formatSpeed(trip.top_speed));
            fieldTotalClimb.setText(displayUnits.formatAltitude(trip.total_climb));
        }
    }

    private double getAverageSpeedInMps(TableTrips.Row trip)
    {
        double elapsedTime = (double) (trip.end_time - trip.start_time);
        return 1000.0 * trip.distance / elapsedTime;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        Log.v(TAG, "onCreateOptionsMenu");
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_finishing, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        Log.v(TAG, "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        String selection = (String) parent.getItemAtPosition(pos);
        Log.v(TAG, "onItemSelected: " + selection);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {}

}
