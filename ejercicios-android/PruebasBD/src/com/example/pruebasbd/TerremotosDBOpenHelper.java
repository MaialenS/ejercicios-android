package com.example.pruebasbd;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TerremotosDBOpenHelper extends SQLiteOpenHelper {

	private static final String DATABASE_NAME = "terremotosBD.db";
	protected static final String DATABASE_TABLE = "Terremotos";
	private static final int DATABASE_VERSION = 1;
	

	protected static final String ID_COLUMN = "_id";
	protected static final String PLACE_COLUMN = "place";
	protected static final String TIME_COLUMN = "time";
	protected static final String DETAIL_COLUMN = "detail";
	protected static final String MAGNITUDE_COLUMN = "magnitude";
	protected static final String LAT_COLUMN = "lat";
	protected static final String LON_COLUMN = "long";
	protected static final String URL_COLUMN = "url";
	protected static final String UPDATED_AT_COLUMN = "updated_at";
	
	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + 
									" (" + ID_COLUMN + " TEXT PRIMARY KEY, " + PLACE_COLUMN + " TEXT, " 
										+ TIME_COLUMN + " DATETIME, " + DETAIL_COLUMN + " TEXT, " + 
										MAGNITUDE_COLUMN + " REAL, " + LAT_COLUMN + " REAL, "+ LON_COLUMN + 
										" REAL, " + URL_COLUMN + " TEXT, "+ " DATETIME, " +
										UPDATED_AT_COLUMN + " DATETIME);";
		
	public TerremotosDBOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public TerremotosDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(DATABASE_CREATE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
		onCreate(db);
	}

	/////OPERACIONES DE LECTURA DE DATOS
	/*
	public Terremoto obtenerTerremotoId(int id){
		//columnas que se buscan 
		String[] result_columns = new String[] { ID_COLUMN, PLACE_COLUMN, TIME_COLUMN, DETAIL_COLUMN, 
												MAGNITUDE_COLUMN, LAT_COLUMN, LON_COLUMN, URL_COLUMN, 
												CREATED_AT_COLUMN, UPDATED_AT_COLUMN };
		//restriccion de la busqueda
		String where = ID_COLUMN + "= ?";
		
		//resto de clausulas

		String whereArgs[] = null;
		String groupBy = null;
		String having = null;
		String order = null;
		
		SQLiteDatabase db = hoardDBOpenHelper.getWritableDatabase();
		Cursor cursor = db.query(DATABASE_TABLE,
								result_columns, 
								where, 
								whereArgs, 
								groupBy, 
								having, 
								order);
		
		return null;	
	}
*/
	
	
}
