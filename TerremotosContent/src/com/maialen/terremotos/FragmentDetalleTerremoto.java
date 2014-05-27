package com.maialen.terremotos;



import com.maialen.datos.TerremotosContentProvider;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;




public class FragmentDetalleTerremoto extends Fragment {

	private static final String TAG = "Terremotos";

	


	
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		String[] from = new String[] {
				TerremotosContentProvider.MAGNITUDE_COLUMN,
				TerremotosContentProvider.PLACE_COLUMN,
				TerremotosContentProvider.TIME_COLUMN,
				TerremotosContentProvider.ID_COLUMN

		};
		int[] to = new int[] { R.id.textTupaMagnitud, R.id.textTuplaLugar,
				R.id.textTupaFecha };

		
		getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		
		//obtener el identificador el terremoto
		Intent intent = getActivity().getIntent();
	    //comprobar si esta por si se ha girado o fallado algo o por si viene de otro lugar
	    if (intent!=null){
		    String action = intent.getAction();
		    String type = intent.getType();
		    Long id_terremoto=intent.getLongExtra(FragmentListaTerremotos.TERREMOTO_SELECCIONADO, 0);
			
		    Log.d(TAG, "id terremoto-> "+id_terremoto);
	    }
		
		
		
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	};
	

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//para poner el loader de los datos
		//getLoaderManager().initLoader(LOADER_TERREMOTOS, null, this);

	}
	
	@Override
	public void onResume() {
		super.onResume();



	}

	// Called at the start of the visible lifetime. @Override
	public void onStart() {
		super.onStart();
		// Apply any required UI change now that the Fragment is visible.

	}

	

}
