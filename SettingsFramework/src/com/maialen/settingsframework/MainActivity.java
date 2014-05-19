package com.maialen.settingsframework;



import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.os.Build;
import android.preference.PreferenceManager;

public class MainActivity extends Activity {

	private static final int SHOW_PREFERENCES = 1;
	
	private TextView txtAutorefresh, txtIntervalo, txtMagnitud;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		txtAutorefresh = (TextView) findViewById(R.id.textAutorefresh);
		txtIntervalo = (TextView) findViewById(R.id.textIntervalo);
		txtMagnitud = (TextView) findViewById(R.id.textMagnitud);
		
	}  

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			//llamar a quien carga las cabeceras de las preferencias
			Intent i = new Intent(this, PreferenciasActivity.class);
			startActivityForResult(i, SHOW_PREFERENCES);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


	
	@Override
	public void onResume() {
	    super.onResume();  // Always call the superclass method first

	  //mirar las preferencias
	    Context context = getApplicationContext();
	    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);	
	  	//String syncConnPref = sharedPref.getString(SettingsActivity.KEY_PREF_SYNC_CONN, "");	
	    
	    boolean auto = prefs.getBoolean(PreferenciasActivity.KEY_PREF_ACTUALIZAR, false);
	    
	    String intervalo = prefs.getString(PreferenciasActivity.KEY_PREF_INTERVALOS, "default");
	    
	    String magnitud = prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "default");
	    
	    txtAutorefresh.setText(Boolean.toString(auto));
	    txtIntervalo.setText(intervalo);
	    txtMagnitud.setText(magnitud);
	    
	    
	}

}
