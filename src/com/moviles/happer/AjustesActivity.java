package com.moviles.happer;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class AjustesActivity extends ActionBarActivity
{
	//private HApper instancia;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
