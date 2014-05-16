package com.maialen.settings;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.ToggleButton;
import android.os.Build;

public class SettingsActivity extends Activity {
	
	private static final String MY_PREFS="Preferencias";
	private static final String AUTOREFRESH="autorefresh";
	private static final String INTERVALO="intervalo";
	
	SharedPreferences mySharedPreferences;

	private ToggleButton tggAuto;
	private Spinner spinnerInterval;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		
		//obtener las preferencias de MY_PREFS
		mySharedPreferences = getSharedPreferences(MY_PREFS, Activity.MODE_PRIVATE);
		
		//leer las preferencias
		//mySharedPreferences.getBoolean("clave", valor_por_defecto)
		boolean auto = mySharedPreferences.getBoolean(AUTOREFRESH, false);
		
		int position = mySharedPreferences.getInt(INTERVALO, 0);
		
		//poner los valores en la pantalla

		tggAuto= (ToggleButton) findViewById(R.id.tggAutorefresh);
		spinnerInterval= (Spinner) findViewById(R.id.spinner1);
		
		tggAuto.setChecked(auto);
		spinnerInterval.setSelection(position);
		
		
	}
	
	@Override
	protected void onPause(){
	     // Suspend UI updates, threads, or CPU intensive processes
	     // that don’t need to be updated when the Activity isn’t
	     // the active foreground Activity.
	     super.onPause();
	     
	     //salvar el estado de las preferencias
	     SharedPreferences.Editor editor = mySharedPreferences.edit();
	     
	     editor.putBoolean(AUTOREFRESH, tggAuto.isChecked());
	     
	     editor.putInt(INTERVALO, spinnerInterval.getSelectedItemPosition());
	     
	     //obligatorio para que se escriban los datos
	     editor.apply();
	     
	     
	}




}
