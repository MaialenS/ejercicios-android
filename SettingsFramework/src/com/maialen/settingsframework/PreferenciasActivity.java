package com.maialen.settingsframework;

import java.util.List;

import android.preference.PreferenceActivity;

public class PreferenciasActivity extends  PreferenceActivity{

	public void onBuildHeaders(List<Header> target){
		//cargar las cabeceras de las preferencias
		loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	}
	
	
}
