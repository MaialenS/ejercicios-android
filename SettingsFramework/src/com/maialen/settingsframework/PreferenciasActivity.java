package com.maialen.settingsframework;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.util.Log;
/*
//con las cabeceras
public class PreferenciasActivity extends  PreferenceActivity {

	public static final String KEY_PREF_ACTUALIZAR = "AUTOREFRESH";
	public static final String KEY_PREF_INTERVALOS = "INTERVALOS";
	public static final String KEY_PREF_MAGNITUD = "MAGNITUD";
	
	public void onBuildHeaders(List<Header> target){
		//cargar las cabeceras de las preferencia
		loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	}
	
}
*/
import android.widget.TextView;

//par mostrar las preferencias sin pantalla intermedia

public class PreferenciasActivity extends  Activity implements OnSharedPreferenceChangeListener{

	public static final String KEY_PREF_ACTUALIZAR = "AUTOREFRESH";
	public static final String KEY_PREF_INTERVALOS = "INTERVALOS";
	public static final String KEY_PREF_MAGNITUD = "MAGNITUD";
	
	private SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//poner un fragmento como vista main
		getFragmentManager().beginTransaction()
							.replace(android.R.id.content, new PreferenciasFragment())
							.commit();
		
		
		//mirar las preferencias
	    Context context = getApplicationContext();
	    prefs = PreferenceManager.getDefaultSharedPreferences(context);	
		
		
		prefs.registerOnSharedPreferenceChangeListener(this);
	}  
	
	@Override
	public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
			String key) {
		// TODO Auto-generated method stub

		
		
		 if(key.equals(KEY_PREF_ACTUALIZAR)){

			 boolean auto = prefs.getBoolean(PreferenciasActivity.KEY_PREF_ACTUALIZAR, false);
			 Log.d("ACT", "Se ha cambiado el autorefresh-->"+String.valueOf(auto));
			 
		 }else if(key.equals(KEY_PREF_INTERVALOS)){

			 String intervalo = prefs.getString(PreferenciasActivity.KEY_PREF_INTERVALOS, "default");
			 
			 Log.d("ACT", "Se ha cambiado el intervalo-->"+intervalo);
			 
		 }else if(key.equals(KEY_PREF_MAGNITUD)){

			 String magnitud = prefs.getString(PreferenciasActivity.KEY_PREF_MAGNITUD, "default");
			 
			 Log.d("ACT", "Se ha cambiado la magnitud-->"+magnitud);
			 
		 }else{
			 Log.d("ACT", "Algo se ha cambiado");
		 }
		
	}
	
	
	
}
