package com.maialen.terremotos;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListFragment;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.maialen.datos.ObtenerTerremotosAsync;
import com.maialen.datos.TerremotosContentProvider;
import com.maialen.preferencias.PreferenciasActivity;


public class FragmentListaTerremotos extends ListFragment implements LoaderCallbacks<Cursor>{

	private static final String TAG = "Terremotos";
	private static final int LOADER_TERREMOTOS = 1;
	static final String TERREMOTO_SELECCIONADO="id_terremoto";
	private static final int SHOW_DETAIL=1;
	
	
	private ObtenerTerremotosAsync obtenerTerremotosInternet;
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

		// Now create an array adapter and set it to display using our row
		simpleAdapter = new SimpleCursorAdapter(getActivity(),
				R.layout.tupla_terremoto, null, from, to, 0);

		simpleAdapter.setViewBinder(fechaViewBlinder);
		// si no se hace aqui aparece el loading
		setListAdapter(this.simpleAdapter);
		
		return super.onCreateView(inflater, container, savedInstanceState);
	};
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		//para poner el loader de los datos
		getLoaderManager().initLoader(LOADER_TERREMOTOS, null, this);

	}
	
	@Override
	public void onResume() {
		super.onResume();

		obtenerTerremotosInternet = new ObtenerTerremotosAsync(getActivity());

		//buscarTerremotosCP();

//		setListAdapter(this.simpleAdapter);
		
		getLoaderManager().restartLoader(LOADER_TERREMOTOS, null, this);
		descargarNuevosTerremotos();

	}

	// Called at the start of the visible lifetime. @Override
	public void onStart() {
		super.onStart();
		// Apply any required UI change now that the Fragment is visible.

	}

	private void buscarTerremotosCP() {
		// pruebas del content provider
		ContentResolver cr = getActivity().getContentResolver();
		
		String[] result_columns = TerremotosContentProvider.ALL_COLUMS;

		// Append a row ID to the URI to address a specific row.
		// Uri rowAddress
		// =ContentUris.withAppendedId(TerremotosContentProvider.CONTENT_URI,
		// rowId);
		Uri rowAddress = TerremotosContentProvider.CONTENT_URI;

		// Replace these with valid SQL statements as necessary.
		String where = TerremotosContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = { String.valueOf(obtenerMagnitud()) };
		String order = TerremotosContentProvider.TIME_COLUMN + " DESC";
		// Return the specified rows.
		Cursor resultCursor = cr.query(rowAddress, result_columns, where,
				whereArgs, order);

		// Log.d(TAG,"tama–o del cursor "+resultCursor.getCount());
		if (resultCursor == null) {
			Log.d(TAG, "el cursor es null ");
		} else {
			Log.d(TAG, "el cursor NO es null " + resultCursor.getCount());

		}
		

	}

	public void descargarNuevosTerremotos() {

		String path = getString(R.string.url2);
		try {
			URL url = new URL(path);
			obtenerTerremotosInternet.execute(url);

		} catch (MalformedURLException e) {

			e.printStackTrace();
		}

	}

	private float obtenerMagnitud() {

		// mirar las preferencias
		Context context = getActivity();
		SharedPreferences prefs = PreferenceManager
				.getDefaultSharedPreferences(context);

		Log.d(TAG, "buscando la magnitud");
		float mag = (float) Double.parseDouble(prefs.getString(
				PreferenciasActivity.KEY_PREF_MAGNITUD, "0"));
		Log.d(TAG, "magnitud de los settings -->" + mag);
		return mag;

	}
	
	public void onListItemClick (ListView l, View v, int position, long id){
		Log.d(TAG, "CLICK -->"+position+"   "+id);
		
		
		//para poder acceder al this de una clase en una instancia interna  NombreClase.this
    	Intent intent= new Intent(getActivity(), DetalleTerremoto.class);	    	
    	//pasar los datos al intent
    	intent.putExtra(TERREMOTO_SELECCIONADO, id);
    	
		startActivityForResult(intent, SHOW_DETAIL);
		
	}


//////////////////////METODOS PARA EL LOADER MANAGER////////////////////


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		
		
		Log.d(TAG, "Loader onCreateLoader");
		
		String[] result_columns = TerremotosContentProvider.ALL_COLUMS;
		String where = TerremotosContentProvider.MAGNITUDE_COLUMN + " >= ?";
		String whereArgs[] = { String.valueOf(obtenerMagnitud()) };
		String order = TerremotosContentProvider.TIME_COLUMN + " DESC";
		
		
		CursorLoader loader = new CursorLoader(getActivity(),
				TerremotosContentProvider.CONTENT_URI, result_columns, where, whereArgs, order);
		return loader;
	}



	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {

		Log.d(TAG, "Loader onLoadFinished");
		
		//simpleAdapter.swapCursor(cursor);
		this.simpleAdapter.changeCursor(cursor);
		
	}





	@Override
	public void onLoaderReset(Loader<Cursor> arg0) {
		Log.d(TAG, "Loader onLoaderReset");
		
		this.simpleAdapter.changeCursor(null);
		
		//simpleAdapter.swapCursor(null);
		
	}

}
