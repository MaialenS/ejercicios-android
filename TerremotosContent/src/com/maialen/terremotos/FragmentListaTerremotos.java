package com.maialen.terremotos;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.ListFragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;


import com.maialen.datos.ObtenerTerremotosAsync;
import com.maialen.datos.TerremotosContentProvider;
import com.maialen.preferencias.PreferenciasActivity;

public class FragmentListaTerremotos extends ListFragment{

	private static final String TAG = "Terremotos";
	
	
	private ObtenerTerremotosAsync obtenerTerremotosInternet;

	static final String STATE_LISTADO = "estadoListado";
	private SimpleCursorAdapter simpleAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

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
				R.layout.tupla_terremoto, null, from, to);

		simpleAdapter.setViewBinder(new SimpleCursorAdapter.ViewBinder() {
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

		});

		// this.adapter= new ListaTerremotosAdapter(getActivity(), null);

		setListAdapter(this.simpleAdapter);

		
		
	}

	@Override
	public void onResume() {
		super.onResume();

		obtenerTerremotosInternet = new ObtenerTerremotosAsync(getActivity());

		buscarTerremotosCP();

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

		String[] result_columns = { TerremotosContentProvider.ID_COLUMN,
				TerremotosContentProvider.ID_TERREMOTO,
				TerremotosContentProvider.PLACE_COLUMN,
				TerremotosContentProvider.TIME_COLUMN,
				TerremotosContentProvider.DETAIL_COLUMN,
				TerremotosContentProvider.MAGNITUDE_COLUMN,
				TerremotosContentProvider.LAT_COLUMN,
				TerremotosContentProvider.LON_COLUMN,
				TerremotosContentProvider.URL_COLUMN

		};

		// Append a row ID to the URI to address a specific row.
		// Uri rowAddress
		// =ContentUris.withAppendedId(TerremotosContentProvider.CONTENT_URI,
		// rowId);
		Uri rowAddress = TerremotosContentProvider.CONTENT_URI;

		// Replace these with valid SQL statements as necessary.
		String where = TerremotosContentProvider.MAGNITUDE_COLUMN+" >= ?";
		String whereArgs[] = {String.valueOf(obtenerMagnitud())};
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

		this.simpleAdapter.changeCursor(resultCursor);
		this.simpleAdapter.notifyDataSetChanged();

	}

	public void descargarNuevosTerremotos() {

		String path = getString(R.string.url_terremotos);
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

}
