package com.maialen.datos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TerremotosDBOpenHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "terremotosBD.db";
	protected static final String DATABASE_TABLE = "Terremotos";
	private static final int DATABASE_VERSION = 1;


	public static final String ID_COLUMN = "_id";
	public static final String ID_TERREMOTO = "id_terremoto";
	public static final String PLACE_COLUMN = "place";
	public static final String TIME_COLUMN = "time";
	public static final String DETAIL_COLUMN = "detail";
	public static final String MAGNITUDE_COLUMN = "magnitude";
	public static final String LAT_COLUMN = "lat";
	public static final String LON_COLUMN = "long";
	public static final String URL_COLUMN = "url";
	public static final String UPDATED_AT_COLUMN = "updated_at";

	private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE + 
									" ( " + ID_COLUMN + " INTEGER PRIMARY KEY AUTOINCREMENT, "+ ID_TERREMOTO + " TEXT UNIQUE, " + PLACE_COLUMN + " TEXT, " 
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

	
	
	
}
