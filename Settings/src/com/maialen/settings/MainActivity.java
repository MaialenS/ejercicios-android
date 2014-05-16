package com.maialen.settings;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
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

public class MainActivity extends Activity {

	SharedPreferences mySharedPreferences;
	private TextView textAct, textTiempo;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		textAct= (TextView) findViewById(R.id.textActulizar);
		textTiempo= (TextView) findViewById(R.id.textTiempo);
		
		//mirar las preferencias
		
		//obtener las preferencias de MY_PREFS
		mySharedPreferences = getSharedPreferences(SettingsActivity.MY_PREFS, Activity.MODE_PRIVATE);
		//leer las preferencias
		//mySharedPreferences.getBoolean("clave", valor_por_defecto)
		boolean auto = mySharedPreferences.getBoolean(SettingsActivity.AUTOREFRESH, false);
				
		int position = mySharedPreferences.getInt(SettingsActivity.INTERVALO, 0);
		
		if(auto){
			String[] mTestArray = getResources().getStringArray(R.array.array_internal); 

			textAct.setText("Se actualiza automaticamente");
			
			textTiempo.setText("Tiempo de refresco "+mTestArray[position]);
		}
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		
		
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			
			Intent i = new Intent(this, SettingsActivity.class);
		    startActivity(i);
			
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	
}
