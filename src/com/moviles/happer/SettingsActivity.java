package com.moviles.happer;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.v4.app.NavUtils;
import android.text.InputType;
import android.view.MenuItem;

public class SettingsActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener
{
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		if(Integer.valueOf(android.os.Build.VERSION.SDK) >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
			getWindow().setUiOptions(ActivityInfo.UIOPTION_SPLIT_ACTION_BAR_WHEN_NARROW);
		
		super.onCreate(savedInstanceState);
		setupActionBar();
		addPreferencesFromResource(R.xml.pref_general);
		
		SharedPreferences sp = getPreferenceScreen().getSharedPreferences();
        EditTextPreference editTextPref = (EditTextPreference) findPreference("prefSincronizacion");
        editTextPref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextPref.setSummary(sp.getString("prefSincronizacion", "15"));
        editTextPref = (EditTextPreference) findPreference("prefIdUsuario");
        editTextPref.setSummary(sp.getString("prefIdUsuario", ""));
        editTextPref = (EditTextPreference) findPreference("prefDistance");
        editTextPref.setSummary(sp.getInt("prefDistance", 40)+ "");
        editTextPref = (EditTextPreference) findPreference("prefNombreUsuario");
        editTextPref.setSummary(sp.getString("prefNombreUsuario", ""));
        editTextPref = (EditTextPreference) findPreference("prefNombresContacto");
        editTextPref.setSummary(sp.getString("prefNombresContacto", ""));
        editTextPref = (EditTextPreference) findPreference("prefCelularContacto");
        editTextPref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextPref.setSummary(sp.getString("prefCelularContacto", ""));
        editTextPref = (EditTextPreference) findPreference("prefTiempoAlarma");
        editTextPref.getEditText().setInputType(InputType.TYPE_CLASS_NUMBER);
        editTextPref.setSummary(sp.getString("prefTiempoAlarma", "10"));
        editTextPref = (EditTextPreference) findPreference("prefRingtone");
        editTextPref.setSummary(sp.getString("prefRingtone", "Morning flower alarm"));
        editTextPref = (EditTextPreference) findPreference("prefTiempoPregunta");
        editTextPref.setSummary(sp.getString("prefTiempoPregunta", "Sin Tiempo"));        
	}

	@SuppressWarnings("deprecation")
	@Override
	protected void onResume() 
	{
	    super.onResume();
	    // Set up a listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onPause() {
	    super.onPause();
	    // Unregister the listener whenever a key changes
	    getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
	}
	
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Show the Up button in the action bar.
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == android.R.id.home) {
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			// TODO: If Settings has multiple levels, Up should navigate up
			// that hierarchy.
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/** {@inheritDoc} */
	@Override
	public boolean onIsMultiPane() 
	{
		return isXLargeTablet(this);
	}

	/**
	 * Helper method to determine if the device has an extra-large screen. For
	 * example, 10" tablets are extra-large.
	 */
	@TargetApi(Build.VERSION_CODES.GINGERBREAD) 
	private static boolean isXLargeTablet(Context context) 
	{
		return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
	}

	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) 
	{
	    	@SuppressWarnings("deprecation")
			Preference connectionPref = findPreference(key);
	    	if (connectionPref instanceof EditTextPreference)
	    		connectionPref.setSummary(sharedPreferences.getString(key, ""));
	}
}
