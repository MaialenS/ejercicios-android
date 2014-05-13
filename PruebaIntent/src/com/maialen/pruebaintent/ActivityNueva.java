package com.maialen.pruebaintent;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
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
import android.widget.TextView;
import android.os.Build;

public class ActivityNueva extends Activity {
	
	private Button btnOK, btnBack;
	private EditText textEntrada;
	private TextView textSalida;

	//cuando se haga click en boton de OK
		View.OnClickListener clickOK = new View.OnClickListener() {
			public void onClick(View v) {
				
				Uri selectedHorse = Uri.parse(textEntrada.getText().toString());
		        Intent result = new Intent(Intent.ACTION_PICK, selectedHorse);
			    	
		        setResult(RESULT_OK, result);
		        finish();
			    	
		    }
		};
			
		//cuando se haga click en boton de Back
		View.OnClickListener clickBack = new View.OnClickListener() {
			  public void onClick(View v) {
				  //si se pulsca back no se devuelven datos    
				  setResult(RESULT_CANCELED);
				  finish();
				    	
			 }
		};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nueva);
		
		this.btnOK= (Button) findViewById(R.id.btnOK);
		this.btnBack= (Button) findViewById(R.id.btnBack);
		//anadir los listeners a los botones
		
		this.btnOK.setOnClickListener(clickOK);
		this.btnBack.setOnClickListener(clickBack);
		
		this.textEntrada = (EditText) findViewById(R.id.textEntrada2);
		this.textSalida = (TextView) findViewById(R.id.textSalida2);
		
		
		//obtener los datos pasados por quien lo ha llamado
		// Get intent, action and MIME type
	    Intent intent = getIntent();
	    String action = intent.getAction();
	    String type = intent.getType();
	    
	    String sharedText=intent.getStringExtra(MainActivity.SHOW_FORM_TEXT);
		
	    if (sharedText != null) {
	        Log.d("ACT", sharedText);
	        this.textSalida.setText(sharedText);
	    }
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_nueva, menu);
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

}
