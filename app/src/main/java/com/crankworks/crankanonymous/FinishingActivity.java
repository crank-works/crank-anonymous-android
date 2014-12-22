package com.crankworks.crankanonymous;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crankworks.trackingdatabase.Database;
import com.crankworks.trackingdatabase.TableTrips;


public class FinishingActivity extends Activity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = FinishingActivity.class.getSimpleName();

    private Spinner fieldObjective;
    private TextView fieldDistance;
    private TextView fieldTopSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_finishing);
        findChildViews();
        populateObjective();
        populateTripFields();
    }

    private void findChildViews()
    {
        fieldObjective = (Spinner) findViewById(R.id.finishing_objective);
        fieldObjective.setOnItemSelectedListener(this);

        fieldDistance = (TextView) findViewById(R.id.finishing_distance);
        fieldTopSpeed = (TextView) findViewById(R.id.finishing_top_speed);
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
        Database db = new Database(this);
        TableTrips.Row trip = db.getLastTrip();
        fieldDistance.setText(String.valueOf(trip.distance));
        fieldTopSpeed.setText(String.valueOf(trip.top_speed));
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
