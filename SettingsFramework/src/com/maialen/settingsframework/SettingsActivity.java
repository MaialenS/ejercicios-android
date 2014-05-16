package com.maialen.settingsframework;

import java.util.List;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingsActivity extends PreferenceActivity {
	

	 @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        // Add a button to the header list.
	        if (hasHeaders()) {
	            Button button = new Button(this);
	            button.setText("Some action");
	            setListFooter(button);
	        }
	    }

	    /**
	     * Populate the activity with the top-level headers.
	     */
	    @Override
	    public void onBuildHeaders(List<Header> target) {
	        loadHeadersFromResource(R.xml.userpreferenceheaders, target);
	    }

	    /**
	     * This fragment shows the preferences for the first header.
	     */
	    public static class Prefs1Fragment extends PreferenceFragment {
	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);

	            
	        }
	    }

	    /**
	     * This fragment contains a second-level set of preference that you
	     * can get to by tapping an item in the first preferences fragment.
	     */
	    public static class Prefs1FragmentInner extends PreferenceFragment {
	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);

	            // Can retrieve arguments from preference XML.
	            Log.d("args", "Arguments: " + getArguments());

	            // Load the preferences from an XML resource
	           // addPreferencesFromResource(R.xml.fragmented_preferences_inner);
	        }
	    }

	    /**
	     * This fragment shows the preferences for the second header.
	     */
	    public static class Prefs2Fragment extends PreferenceFragment {
	        @Override
	        public void onCreate(Bundle savedInstanceState) {
	            super.onCreate(savedInstanceState);

	            // Can retrieve arguments from headers XML.
	            Log.i("args", "Arguments: " + getArguments());

	            // Load the preferences from an XML resource
	            //addPreferencesFromResource(R.xml.preference_dependencies);
	        }
	    }


}
