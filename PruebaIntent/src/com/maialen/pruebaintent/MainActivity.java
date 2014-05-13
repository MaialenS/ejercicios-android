package com.maialen.pruebaintent;




import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
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
	
	public static final int SHOW_FORM= 1;
	public static final String SHOW_FORM_TEXT="texto";
	
	public static final int SHOW_CAMARA= 2;
	private Uri fileUri;
	
	public static final int SHOW_CONTACT= 3;
	private Button btnForm, btnCamara, btnContact;
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
			        
				  //fetchContacts();
			    	
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

	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == SHOW_FORM) {
	        // Make sure the request was successful
	    	 Log.d("ACT", "ha vuelto dato de form");
	        if (resultCode == RESULT_OK) {
	        	Log.d("ACT", "RESULT_OK");
	        	//data.getData()
	        	String datoDevuelto=data.getDataString();
	    		
	    	    if (datoDevuelto != null) {
	    	        this.textSalida.setText(datoDevuelto);
	    	    }
	            
	        }else{
	        	Log.d("ACT", "No OK");
	        	
	        }
	    }else if (requestCode == SHOW_CAMARA) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	
	        	Bundle extras = data.getExtras();
	            Bitmap imageBitmap = (Bitmap) extras.get("data");
	            this.imagen.setImageBitmap(imageBitmap);
	        }
	    }else if (requestCode == SHOW_CONTACT) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	        	
	        	Uri contactData = data.getData();
	            Cursor c =  getContentResolver().query(contactData, null, null, null, null);
	            
	            if(c.moveToFirst()){
	            	
	            	String name=c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
	            	
	            	
	            	this.contacto.setText(name);

	            	
	            	
	            	
	            }
	            
	            
	            
	            
	            
	            /*
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
	    }
	    
	}
	
	

}
