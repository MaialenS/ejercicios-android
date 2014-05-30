package com.maialen.terremotos;


import com.maialen.datos.Terremoto;
import com.maialen.datos.TerremotosContentProvider;
import com.maialen.preferencias.PreferenciasActivity;
import com.maialen.servicios.ServicioBusquedaTerremotos;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends Activity {
	
	private static final String TAG="Terremotos";
	private static final String MIS_PREFERENCIAS = "preferencias_terremotos";
	private static final String PRIMERA_EJECUCION = "primera_ejecucion";
	
	private ActionBar actionBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//NAVEGACION POR PESTA„AS
		
		actionBar= getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		Tab tabOne = actionBar.newTab();
		Tab tabTwo = actionBar.newTab();
		
		//tabOne.setText("primero");
		tabOne.setIcon(R.drawable.ic_action_view_as_list);
		tabOne.setTabListener( new TabListener (this, R.id.container, FragmentListaTerremotos.class));
		
		tabTwo.setIcon(R.drawable.ic_action_map);
		tabTwo.setTabListener( new TabListener (this, R.id.container, MapaFragmento.class));
		
		actionBar.addTab(tabOne);
		actionBar.addTab(tabTwo);

		
		//comprobar si es la primera vez que se ejecuta la aplicacion
		
		SharedPreferences mySharedPreferences = getSharedPreferences(MIS_PREFERENCIAS,
                Activity.MODE_PRIVATE);
		boolean primeraVez = mySharedPreferences.getBoolean(PRIMERA_EJECUCION, true);
		if(primeraVez){//primera vez que se ejecuta la aplicacion
			Log.d(TAG, "primera ejecucion del app");
			//apuntar que ya se ha ejecutado por primera vez
			SharedPreferences.Editor editor = mySharedPreferences.edit();
			editor.putBoolean(PRIMERA_EJECUCION, false);
			//guardar los cambios
			editor.apply();
			
			//pedir terremotos
			Intent intent = new Intent(this, ServicioBusquedaTerremotos.class);
			startService(intent);
			
		}else{
			Log.d(TAG, "no es primera ejecucion del app");
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
			Intent i = new Intent(this, PreferenciasActivity.class);
			startActivity(i);
			
			return true;
		} else if(id == R.id.action_refresh){
			//refrescar los terremotos
			//((FragmentListaTerremotos)(getFragmentManager().findFragmentById(R.id.container))).descargarNuevosTerremotos();
			// Explicitly start My Service
			Intent intent = new Intent(this, ServicioBusquedaTerremotos.class);
			startService(intent);
			
		}
		return super.onOptionsItemSelected(item);
	}

	
	public static class TabListener<T extends Fragment> implements ActionBar.TabListener {

		private Fragment fragment;
	    private Activity activity;
	    private Class<T> fragmentClass;
	    private int fragmentContainer;
		
	    public TabListener(Activity activity, int fragmentContainer, Class<T> fragmentClass) {
			this.activity = activity;
			this.fragmentContainer = fragmentContainer;
			this.fragmentClass = fragmentClass;
		}
	    
		@Override
		public void onTabReselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			 if (fragment != null){
				    ft.attach(fragment);
			 }
		}

		@Override
		public void onTabSelected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if (fragment == null) {
		        String fragmentName = fragmentClass.getName();
		        fragment = Fragment.instantiate(activity, fragmentName);
		        ft.add(fragmentContainer, fragment, fragmentName);
		      } else{
		        ft.attach(fragment);
		      }
		}

		@Override
		public void onTabUnselected(Tab tab, FragmentTransaction ft) {
			// TODO Auto-generated method stub
			if (fragment != null){
			    ft.detach(fragment);
			}
		}
	  
	}

}
