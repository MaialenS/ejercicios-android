package com.maialen.settingsframework;

import java.util.List;

import android.preference.PreferenceActivity;

public class PreferenciasActivity extends  PreferenceActivity{

	public static final String KEY_PREF_ACTUALIZAR = "AUTOREFRESH";
	public static final String KEY_PREF_INTERVALOS = "INTERVALOS";
	public static final String KEY_PREF_MAGNITUD = "MAGNITUD";
	
	public void onBuildHeaders(List<Header> target){
		//cargar las cabeceras de las preferencias
		loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	}
	
	
}
