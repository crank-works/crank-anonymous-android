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


public class FinishingActivity extends Activity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = FinishingActivity.class.getSimpleName();

    private Spinner fieldObjective;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "onCreate");
        setContentView(R.layout.activity_finishing);
        findChildViews();
        populateObjective();
    }

    private void findChildViews()
    {
        fieldObjective = (Spinner) findViewById(R.id.finishing_objective);
        fieldObjective.setOnItemSelectedListener(this);
    }

    private void populateObjective()
    {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.objectives_array,
                android.R.layout.simple_spinner_dropdown_item);

        fieldObjective.setAdapter(adapter);
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
