package com.maialen.preferencias;

import com.maialen.terremotos.R;

import android.os.Bundle;
import android.preference.PreferenceFragment;



public class PreferenciasFragment extends PreferenceFragment {

	 @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //PreferenceManager.setDefaultValues(getActivity(), R.xml.advanced_preferences, false);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferenciasterremotos);
    }
	
	
	
}
