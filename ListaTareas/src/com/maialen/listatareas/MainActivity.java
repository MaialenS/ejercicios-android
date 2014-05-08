package com.maialen.listatareas;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.os.Build;

public class MainActivity extends Activity {

	static final String STATE_LISTADI = "estadoListado";
	
	
	private ArrayList<String> listado;	
	private ArrayAdapter<String> adapter;
	
	private EditText txtNuevaTarea;
	private ListView listaTareas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.listado = new ArrayList<String>();
		
		//contexto: entorno en el que se ejecuta
		//el tipo de layout a rellenar
		//datos para rellenar
		this.adapter= new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, this.listado);
		
		this.txtNuevaTarea = (EditText) findViewById(R.id.tareaNueva);
		this.listaTareas = (ListView) findViewById(R.id.listaTareas);
		//decirle a la lista cual es su adaptador
		this.listaTareas.setAdapter(this.adapter);

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

	public void anadir(View v){
		
		String men= String.valueOf( this.txtNuevaTarea.getText());
		
		if(men.length()>0){
			this.listado.add(men);
		}
		this.txtNuevaTarea.setText("");
		
		this.adapter.notifyDataSetChanged();
		
	}
	
	@Override
	protected void onSaveInstanceState(Bundle savedInstanceState) {
		// salvar el estado
		Log.d("cal", "salvar el estado ");

		savedInstanceState.putStringArrayList(STATE_LISTADI, this.listado);
	
		// Always call the superclass so it can save the view hierarchy state
		super.onSaveInstanceState(savedInstanceState);
	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {
		Log.d("cal", "restaurar el estado " );
		// Always call the superclass so it can restore the view hierarchy
		super.onRestoreInstanceState(savedInstanceState);

		//this.listado = savedInstanceState.getStringArrayList(STATE_LISTADI);
		//si se pone directamente no funciona
		this.listado.addAll(savedInstanceState.getStringArrayList(STATE_LISTADI));

	}

	@Override
	protected void onResume() {
		super.onResume();
		
		this.adapter.notifyDataSetChanged();
	}
	
	
	
}
