package com.example.pruebasbd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class TerremotosBD {
	TerremotosDBOpenHelper mDbHelper;
	private SQLiteDatabase db;
	
	public TerremotosBD(Context context){
		super();
		
		mDbHelper = new TerremotosDBOpenHelper(context);
	}
	
	public void open() throws SQLException {
	    db = mDbHelper.getWritableDatabase();
	  }
	
	public void close() {
	    db.close();
	  }
	
	///OPERACIONES DE CREAR ELEMENTO
	
	 public void crearTerremoto(Terremoto terremoto) {
		 ContentValues values = new ContentValues();
		 if(terremoto!=null){
			 //asignar valores a la fila
			 values.put(mDbHelper.ID_COLUMN, terremoto.getId());
			 values.put(mDbHelper.PLACE_COLUMN, terremoto.getPlace());
			 values.put(mDbHelper.TIME_COLUMN, terremoto.getTime());
			 values.put(mDbHelper.DETAIL_COLUMN, terremoto.getDetail());
			 values.put(mDbHelper.MAGNITUDE_COLUMN, terremoto.getMagnitude());
			 values.put(mDbHelper.LAT_COLUMN, terremoto.getLatitude());
			 values.put(mDbHelper.LON_COLUMN, terremoto.getLongitude());
			 values.put(mDbHelper.URL_COLUMN, terremoto.getUrl());
			 values.put(mDbHelper.UPDATED_AT_COLUMN, terremoto.getUpdated());
			 

				long ok=db.insert(TerremotosDBOpenHelper.DATABASE_TABLE, null, values);
				Log.d("ACT","insertando, estado--> " + String.valueOf(ok));
			 
		 }else {
			Log.d("ACT", "el terremoto no tiene valores"); 
		 } 
	}
	
	//operaciones para obtener terremotos
	 
	 //obtener todos
	 
	 public Cursor getAllTerremotos() {
		// ArrayList<Terremoto> terremotos = new ArrayList<Terremoto>();
		    
		  //columnas que se buscan 
			String[] result_columns = new String[] { mDbHelper.ID_COLUMN, mDbHelper.PLACE_COLUMN, mDbHelper.TIME_COLUMN, mDbHelper.DETAIL_COLUMN, 
					mDbHelper.MAGNITUDE_COLUMN, mDbHelper.LAT_COLUMN, mDbHelper.LON_COLUMN, mDbHelper.URL_COLUMN, 
					mDbHelper.UPDATED_AT_COLUMN };

			//resto de clausulas
			String where=null;
			String whereArgs[] = null;
			String groupBy = null;
			String having = null;
			String order = null;
			
			SQLiteDatabase db = mDbHelper.getWritableDatabase();
			Cursor cursor = db.query(TerremotosDBOpenHelper.DATABASE_TABLE,
									result_columns, 
									where, 
									whereArgs, 
									groupBy, 
									having, 
									order);
		    
			return cursor;
	 }

	 
		public void deleteTerremotoId(int id) {
			// Specify a where clause that determines which row(s) to delete.
			// Specify where arguments as necessary.
			// Delete the rows that match the where clause
			String where = mDbHelper.ID_COLUMN + "= ?";
			String whereArgs[] = null;
			whereArgs[0]=String.valueOf(id);
			
			db.delete(TerremotosDBOpenHelper.DATABASE_TABLE, where,
			whereArgs);
			Log.d("ACT", "DELETE en la BD de la tupla : "+where+" ="+whereArgs);
		}
	 
	 
	 
	 
	 
}
