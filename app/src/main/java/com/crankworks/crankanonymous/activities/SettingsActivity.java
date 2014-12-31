package com.crankworks.crankanonymous.activities;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.util.Log;

import com.crankworks.crankanonymous.R;

/**
 * Created by marcus on 12/28/14.
 */
public class SettingsActivity extends Activity
{
    private static final String TAG = SettingsActivity.class.getSimpleName();

    public static final String KEY_PREF_DISPLAY_UNITS = "pref_display_units";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener
    {
        @Override
        public void onCreate(Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);

            // Load the preferences from an XML resource
            addPreferencesFromResource(R.xml.settings);

            setSummaryToCurrentValue(KEY_PREF_DISPLAY_UNITS, R.string.pref_display_units_default);
        }

        private void setSummaryToCurrentValue(String key, int defaultValue)
        {
            Preference connectionPref = findPreference(key);
            SharedPreferences sharedPreferences = getPreferenceManager().getSharedPreferences();
            String prefDisplayUnitDefault = getString(defaultValue);
            String prefDisplayUnit = sharedPreferences.getString(key, prefDisplayUnitDefault);
            connectionPref.setSummary(prefDisplayUnit);
        }

        @Override
        public void onResume()
        {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause()
        {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key)
        {
            Log.v(TAG, "SettingsFragment.onSharedPreferenceChanged: " + key);
            if (key.equals(KEY_PREF_DISPLAY_UNITS))
            {
                Preference connectionPref = findPreference(key);
                // Set summary to be the user-description for the selected value
                connectionPref.setSummary(sharedPreferences.getString(key, ""));
            }
        }
    }
}
