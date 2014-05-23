package com.maialen.datos;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.net.Uri;

public class TerremotosContentProvider extends ContentProvider{

	public static final Uri CONTENT_URI = Uri.parse("content://com.maialen.preferencias.PreferenciasActivity/elements");
	TerremotosDBOpenHelper terremotosHelper;
	//variables para distinguir los distintos tipos de consultas
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	
	private static final UriMatcher uriMatcher;
	
	static {
		   uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		   uriMatcher.addURI("com.maialen.preferencias.PreferenciasActivity", "elements", ALLROWS);
		   uriMatcher.addURI("com.maialen.preferencias.PreferenciasActivity", "elements/#", SINGLE_ROW);
				   
		}
	
	
	
	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		terremotosHelper = new TerremotosDBOpenHelper(getContext(), TerremotosDBOpenHelper.DATABASE_NAME, null, TerremotosDBOpenHelper.DATABASE_VERSION);
		
		
		return true;
	}

	@Override
	public Cursor query(Uri arg0, String[] arg1, String arg2, String[] arg3,
			String arg4) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	//ell helper de la BD
	
	private class TerremotosDBOpenHelper extends SQLiteOpenHelper{

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
	
}
