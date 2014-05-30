package com.maialen.adaptadores;


import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.maialen.datos.TerremotosContentProvider;
import com.maialen.terremotos.R;

public class ListaTerremotosAdapter extends CursorAdapter {
	private LayoutInflater mInflater;
	public Cursor c;

	public ListaTerremotosAdapter(Context context, Cursor cursor) {
		super(context, cursor, 0);

		/*
		 * Gets an inflater that can instantiate the ListView layout from the
		 * file.
		 */
		mInflater = LayoutInflater.from(context);
		c = cursor;

	}

	/**
	 * Defines a class that hold resource IDs of each item layout row to prevent
	 * having to look them up each time data is bound to a row.
	 */
	private class ViewHolder {
		TextView magnitud;
		TextView lugar;
		TextView fecha;
		//TextView id_bd;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
		/*
		 * Inflates the item layout. Stores resource IDs in a in a ViewHolder
		 * class to prevent having to look them up each time bindView() is
		 * called.
		 */
		final View itemView = mInflater.inflate(R.layout.tupla_terremoto,
				viewGroup, false);
		final ViewHolder holder = new ViewHolder();
		holder.magnitud = (TextView) itemView
				.findViewById(R.id.textTupaMagnitud);
		holder.lugar = (TextView) itemView.findViewById(R.id.textTuplaLugar);
		holder.fecha = (TextView) itemView.findViewById(R.id.textTupaFecha);
		//holder.id_bd = (TextView) itemView.findViewById(R.id.textTuplaID);

		itemView.setTag(holder);
		return itemView;
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		final ViewHolder holder = (ViewHolder) view.getTag();

		// poner los valores en la tupla

		String magnitud = cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.MAGNITUDE_COLUMN));
		String lugar = cursor.getString(cursor
				.getColumnIndex(TerremotosContentProvider.PLACE_COLUMN));

		long fechaLong = cursor.getLong(cursor
				.getColumnIndex(TerremotosContentProvider.TIME_COLUMN));
		String fechaFormateada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.format(new Date(fechaLong));
		
		int id_bd = cursor.getInt(cursor.getColumnIndex(TerremotosContentProvider.ID_COLUMN));

		holder.magnitud.setText(magnitud);
		holder.lugar.setText(lugar);
		holder.fecha.setText(fechaFormateada);
		//holder.id_bd.setText(id_bd);

	}
}