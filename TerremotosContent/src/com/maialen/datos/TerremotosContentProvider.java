package com.maialen.datos;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

public class TerremotosContentProvider extends ContentProvider {
	private static final String TAG = "Terremotos";
	// CUIDADO QUE TIENE QUE ESTAR BIEN PUESTO
	public static final Uri CONTENT_URI = Uri
			.parse("content://com.maialen.provider.terremotoscontentprovider/elements");
	TerremotosDBOpenHelper terremotosHelper;
	// variables para distinguir los distintos tipos de consultas
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;

	private static final UriMatcher uriMatcher;

	static {
		uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		uriMatcher.addURI("com.maialen.preferencias.PreferenciasActivity",
				"elements", ALLROWS);
		uriMatcher.addURI("com.maialen.preferencias.PreferenciasActivity",
				"elements/#", SINGLE_ROW);
	}

	// columnas de la BD
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
	
	public static final String[] ALL_COLUMS = { 
		TerremotosContentProvider.ID_COLUMN,
		TerremotosContentProvider.ID_TERREMOTO,
		TerremotosContentProvider.PLACE_COLUMN,
		TerremotosContentProvider.TIME_COLUMN,
		TerremotosContentProvider.DETAIL_COLUMN,
		TerremotosContentProvider.MAGNITUDE_COLUMN,
		TerremotosContentProvider.LAT_COLUMN,
		TerremotosContentProvider.LON_COLUMN,
		TerremotosContentProvider.URL_COLUMN
	};
	

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = terremotosHelper.getWritableDatabase();
		// If this is a row URI, limit the deletion to the specified row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = ID_COLUMN
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
		default:
			break;
		}
		// To delete all rows and return a value pass in "1".
		if (selection == null)
			selection = "1";
		// Perform the deletion.
		int deleteCount = db.delete(terremotosHelper.DATABASE_TABLE, selection,
				selectionArgs);
		// Notify any observers of the change in the data set.
		getContext().getContentResolver().notifyChange(uri, null);
		// Return the number of deleted items.
		return deleteCount;
	}

	@Override
	public String getType(Uri uri) {
		// PARA SABER QUE TIPO DE COSAS DEVUELVE
		// ES OBLIGATORIO
		switch (uriMatcher.match(uri)) {
		case ALLROWS:
			return "vnd.android.cursor.dir/vnd.maialen.provider.terremotoscontentprovider";
		case SINGLE_ROW:
			return "vnd.android.cursor.item/vnd.maialen.provider.terremotoscontentprovider";
		default:
			throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
	}

	@Override
	public Uri insert(Uri arg0, ContentValues values) {
		
		
		// abrir la BD en modo escritura
		SQLiteDatabase db = terremotosHelper.getWritableDatabase();
		// To add empty rows to your database by passing in an empty
		// Content Values object you must use the null column hack
		// parameter to specify the name of the column that can be
		// set to null.
		String nullColumnHack = null;
		// insertar los valores a en la BD
		long id = db.insert(terremotosHelper.DATABASE_TABLE, nullColumnHack,
				values);
		// se revuelve la uri para buscar el nuevo elemento que se ha insertado
		if (id > -1) {
			
			Uri insertedId = ContentUris.withAppendedId(CONTENT_URI, id);
			
			//MUY IMPORTANTE
			// notificar a quien escuche que hay cambios en la BD
			getContext().getContentResolver().notifyChange(insertedId, null);
//			Log.d(TAG, "Content Provider notifyChange()");
			
			return insertedId;
		} else
			return null;
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		//Log.d(TAG, "Content provider onCreate");
		terremotosHelper = new TerremotosDBOpenHelper(getContext(),
				TerremotosDBOpenHelper.DATABASE_NAME, null,
				TerremotosDBOpenHelper.DATABASE_VERSION);
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] result_columns, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		Cursor c = null;
		Log.d(TAG, "haciendo query");
		// abir la BD con el helper
		SQLiteDatabase db;
		try {
			db = terremotosHelper.getWritableDatabase();
		} catch (SQLiteException ex) {
			Log.d(TAG,
					"fallo al abir la bd en escritura, se intenta en lectura");
			db = terremotosHelper.getReadableDatabase();

		}

		// preparar el constructor de la pregunta
		SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();

		// comprobar que tipo de consulta se quiere hacer y devolver lo que se
		// quiere

		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			// obtener lo que pone tras la uri
			Log.d(TAG, "SINGLE_ROW");
			String rowID = uri.getPathSegments().get(1);
			queryBuilder.appendWhere(ID_COLUMN + "=" + rowID);
			break;
		case ALLROWS:
			Log.d(TAG, "ALLROWS");
			break;
		default:
			break;
		}

		queryBuilder.setTables(TerremotosDBOpenHelper.DATABASE_TABLE);

		String groupBy = null;
		String having = null;
		// ejecutar el query
		Cursor cursor = queryBuilder.query(db, result_columns, selection,
				selectionArgs, groupBy, having, sortOrder);
		
		//avisar a los que quieren esto de que puede cambiar
		cursor.setNotificationUri(getContext().getContentResolver(), uri);

		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		// Open a read / write database to support the transaction.
		SQLiteDatabase db = terremotosHelper.getWritableDatabase();
		// If this is a row URI, limit the deletion to the specified row.
		switch (uriMatcher.match(uri)) {
		case SINGLE_ROW:
			String rowID = uri.getPathSegments().get(1);
			selection = ID_COLUMN
					+ "="
					+ rowID
					+ (!TextUtils.isEmpty(selection) ? " AND (" + selection
							+ ')' : "");
		default:
			break;
		}
		// Perform the update.
		int updateCount = db.update(terremotosHelper.DATABASE_TABLE, values,
				selection, selectionArgs);
		// Notify any observers of the change in the data set.
		
		getContext().getContentResolver().notifyChange(uri, null);

		return updateCount;
	}

	// el helper de la BD

	private class TerremotosDBOpenHelper extends SQLiteOpenHelper {

		private static final String DATABASE_NAME = "terremotosBD.db";
		protected static final String DATABASE_TABLE = "Terremotos";
		private static final int DATABASE_VERSION = 1;

		private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS "
				+ DATABASE_TABLE
				+ " ( "
				+ ID_COLUMN
				+ " INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ ID_TERREMOTO
				+ " TEXT UNIQUE, "
				+ PLACE_COLUMN
				+ " TEXT, "
				+ TIME_COLUMN
				+ " DATETIME, "
				+ DETAIL_COLUMN
				+ " TEXT, "
				+ MAGNITUDE_COLUMN
				+ " REAL, "
				+ LAT_COLUMN
				+ " REAL, "
				+ LON_COLUMN
				+ " REAL, "
				+ URL_COLUMN
				+ " TEXT, "
				+ " DATETIME, "
				+ UPDATED_AT_COLUMN
				+ " DATETIME);";

		public TerremotosDBOpenHelper(Context context, String name,
				CursorFactory factory, int version) {
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
