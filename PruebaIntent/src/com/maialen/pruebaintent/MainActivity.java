package com.maialen.pruebaintent;




import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.os.Build;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.content.ContentResolver;


public class MainActivity extends Activity {
	
	private static final String TEXTO_MOSTRAR="texto_mostrar";
	private static final String IMAGEN_MOSTRAR="imagen_mostrar";
	private static final String CONTACTO_MOSTRAR="contacto_mostrar";
	
	public static final int SHOW_FORM= 1;
	public static final String SHOW_FORM_TEXT="texto";
	
	public static final int SHOW_CAMARA= 2;
	private Uri fileUri;
	
	public static final int SHOW_CONTACT= 3;
	private Button btnForm, btnCamara, btnContact;
	
	public static final int MEDIA_TYPE_IMAGE = 1;
	
	private EditText textEntrada;
	private TextView textSalida, contacto;
	private ImageView imagen;

	private SimpleCursorAdapter dataAdapter;
	

	//cuando se haga click en boton de form
	View.OnClickListener clickForm = new View.OnClickListener() {
	    public void onClick(View v) {
	    	//para poder acceder al this de una clase en una instancia interna  NombreClase.this
	    	Intent intent= new Intent(MainActivity.this, ActivityNueva.class);	    	
	    	//pasar los datos al intent
	    	intent.putExtra(SHOW_FORM_TEXT, textEntrada.getText().toString());
	    	startActivityForResult(intent, SHOW_FORM);
	   	}
	};
	  
	//cuando se haga click en boton de Camara
	View.OnClickListener clickCamara = new View.OnClickListener() {
		  public void onClick(View v) {  
			    // create Intent to take a picture and return control to the calling application
			  	Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			    fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
			    intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri); // set the image file name
			    // hacer el intent si alguien puede
			    if (intent.resolveActivity(getPackageManager()) != null) {
			        startActivityForResult(intent, SHOW_CAMARA);
			    }  	
	    }
	};
		
	//cuando se haga click en boton de Contacto
		View.OnClickListener clickContacto = new View.OnClickListener() {
			  public void onClick(View v) {			      
				 Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
			        startActivityForResult(intent, SHOW_CONTACT);	        
			  }
		};
		  
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.btnForm= (Button) findViewById(R.id.btnFormulario);
		this.btnCamara= (Button) findViewById(R.id.btnCamara);
		this.btnContact= (Button) findViewById(R.id.btnContacto);
		
		this.btnForm.setOnClickListener(clickForm);
		this.btnCamara.setOnClickListener(clickCamara);
		this.btnContact.setOnClickListener(clickContacto);
		
		this.textEntrada = (EditText) findViewById(R.id.textEntrada1);
		this.textSalida = (TextView) findViewById(R.id.textSalida1);
		this.contacto = (TextView) findViewById(R.id.contacto);
		
		this.imagen= (ImageView) findViewById(R.id.imagen);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // mirar a que aplicacion corresponde el "request", el codigo de que actividad
	    if (requestCode == SHOW_FORM) {
	        // Make sure the request was successful
	    	 Log.d("ACT", "ha vuelto dato de form");
	        if (resultCode == RESULT_OK) {
	        	tratarForm(data);
	        }else{
	        	Log.d("ACT", "No OK");
	        }
	    }else if (requestCode == SHOW_CAMARA) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	Log.d("ACT", "Camara OK");
	        	//Bundle extras = data.getExtras();
	            //Bitmap imageBitmap = (Bitmap) extras.get("data");
	        	//this.imagen.setImageBitmap(imageBitmap);
	        	
	        	ponerImagen();
	        }
	    }else if (requestCode == SHOW_CONTACT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	tratarContacto(data);
	        }
	    }
	}
	
	private void tratarForm(Intent data){
		Log.d("ACT", "RESULT_OK");
    	//data.getData()
    	String datoDevuelto=data.getDataString();  		
	    if (datoDevuelto != null) {
	        this.textSalida.setText(datoDevuelto);
	    }
	}
	
	private Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    
	    //para mantener las proporciones
	    float max= Math.max(scaleWidth, scaleHeight);
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(max, max);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	private void ponerImagen(){
		
		// Get the dimensions of the View
	    int targetW = imagen.getWidth();
	    int targetH = imagen.getHeight();
		
	 // Get the dimensions of the bitmap
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();
	    bmOptions.inJustDecodeBounds = true;
	    
		try {
			Bitmap imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
			this.imagen.setImageBitmap(getResizedBitmap(imageBitmap,targetH,targetW));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private void tratarContacto(Intent data){
		Uri contactData = data.getData();
        Cursor c =  getContentResolver().query(contactData, null, null, null, null);      
        if(c.moveToFirst()){       	
        	String name=c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
        	this.contacto.setText(name);
        }
    
        /*//adaptador para el cursor y un listView
     // For the cursor adapter, specify which columns go into which views
        String[] fromColumns = {ContactsContract.Data.DISPLAY_NAME};
        int[] toViews = {android.R.id.text1}; // The TextView in simple_list_item_1
        // Create an empty adapter we will use to display the loaded data.
        // We pass null for the cursor, then update it in onLoadFinished()
        SimpleCursorAdapter mAdapter = new SimpleCursorAdapter(this, 
                android.R.layout.simple_list_item_1, c,
                fromColumns, toViews, 0);

        this.lista.setAdapter(mAdapter);

        */    
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// salvar el estado  IMAGEN_MOSTRAR
		Log.d("cal", "salvar el estado " + savedInstanceState);

		savedInstanceState.putString(TEXTO_MOSTRAR,
				this.textSalida.getText().toString());
		savedInstanceState.putString(CONTACTO_MOSTRAR,
				this.contacto.getText().toString());
		savedInstanceState.putString(IMAGEN_MOSTRAR,
				this.fileUri.toString());
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("cal", "restaurar el estado " + savedInstanceState);
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

	//	this.calculos.numero1 = savedInstanceState.getString(this.calculos.STATE_NUM1);
		this.textSalida.setText(savedInstanceState.getString(TEXTO_MOSTRAR));
		this.contacto.setText(savedInstanceState.getString(CONTACTO_MOSTRAR));
		
		String uri =savedInstanceState.getString(IMAGEN_MOSTRAR);
		this.fileUri= Uri.parse(uri);
		ponerImagen();
	}
	
	/** Create a file Uri for saving an image or video */
	private static Uri getOutputMediaFileUri(int type){
	      return Uri.fromFile(getOutputMediaFile(type));
	}

	/** Create a File for saving an image or video */
	private static File getOutputMediaFile(int type){
		//codigo de android developer
		
	    // To be safe, you should check that the SDCard is mounted
	    // using Environment.getExternalStorageState() before doing this.

	    File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
	              Environment.DIRECTORY_PICTURES), "MyCameraApp");
	    // This location works best if you want the created images to be shared
	    // between applications and persist after your app has been uninstalled.

	    // Create the storage directory if it does not exist
	    if (! mediaStorageDir.exists()){
	        if (! mediaStorageDir.mkdirs()){
	            Log.d("MyCameraApp", "failed to create directory");
	            return null;
	        }
	    }

	    // Create a media file name
	    File mediaFile;
	    if (type == MEDIA_TYPE_IMAGE){
	        mediaFile = new File(mediaStorageDir.getPath() + File.separator +
	        "IMG_"+ 1 + ".jpg");
	    } else {
	        return null;
	    }
	    return mediaFile;
	}
}
