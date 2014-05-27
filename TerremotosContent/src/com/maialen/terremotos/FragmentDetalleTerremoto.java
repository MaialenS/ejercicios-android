package com.maialen.terremotos;



import java.text.SimpleDateFormat;
import java.util.Date;

import com.maialen.datos.TerremotosContentProvider;

import android.app.Fragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;




public class FragmentDetalleTerremoto extends Fragment implements LoaderCallbacks<Cursor>{

	private static final String TAG = "Terremotos";
	private static final int LOADER_DETALLE = 1;
	
	private SimpleCursorAdapter simpleAdapter;
	
	private SimpleCursorAdapter.ViewBinder fechaViewBlinder= new SimpleCursorAdapter.ViewBinder(){

		@Override
		public boolean setViewValue(View view, Cursor cursor,
				int columnIndex) {
			// TODO Auto-generated method stub
			int esFecha = cursor
					.getColumnIndexOrThrow(TerremotosContentProvider.TIME_COLUMN);
			if (columnIndex == esFecha) {
				// formatear la fecha
				TextView textFecha = (TextView) view;
				long fechaLong = cursor.getLong(cursor
						.getColumnIndex(TerremotosContentProvider.TIME_COLUMN));
				String fechaFormateada = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss").format(new Date(fechaLong));
				textFecha.setText(fechaFormateada);
				return true;

			}

			return false;
		}
		
	};

	
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
		
	    
	    
	    simpleAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.tupla_terremoto, null, from, to, 0);

		simpleAdapter.setViewBinder(fechaViewBlinder);
		// si no se hace aqui aparece el loading
		
		
		
		
		
		return super.onCreateView(inflater, container, savedInstanceState);
	};
	

	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//para poner el loader de los datos
		//getLoaderManager().initLoader(LOADER_TERREMOTOS, null, this);

		getLoaderManager().initLoader(LOADER_DETALLE, null, this);
		
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

//////////////METODOS PARA EL LOADER//////////////////

	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public void onLoadFinished(Loader<Cursor> arg0, Cursor arg1) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		// TODO Auto-generated method stub
		
	}

	

}
