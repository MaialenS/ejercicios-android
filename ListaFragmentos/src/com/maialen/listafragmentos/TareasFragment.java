package com.maialen.listafragmentos;

import java.util.ArrayList;

import com.maialen.interfaces.TareasFragmentInterfaz;

import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class TareasFragment extends ListFragment implements TareasFragmentInterfaz{
	static final String STATE_LISTADO = "estadoListado";
	ArrayList<String> listado;
	private ArrayAdapter<String> adapter;

	 @Override
	 public void onActivityCreated(Bundle savedInstanceState) {
	        super.onActivityCreated(savedInstanceState);
	        this.listado = new ArrayList<String>();
	        this.adapter= new ArrayAdapter<String>(getActivity(),
	                android.R.layout.simple_list_item_activated_1, this.listado);
	        setListAdapter(this.adapter);
	        
	        if(savedInstanceState!=null){
	        	this.listado.addAll(savedInstanceState.getStringArrayList(STATE_LISTADO));
	        }
	 }

	 ////metodos de la interfaz
	 
		@Override
		public void addTarea(String tarea) {
			
			this.listado.add(0, tarea);
			this.adapter.notifyDataSetChanged();

		}
	

		// Called to save UI state changes at the
		// end of the active lifecycle.
		@Override
		public void onSaveInstanceState(Bundle savedInstanceState) {
		  // Save UI state changes to the savedInstanceState.
		  // This bundle will be passed to onCreate, onCreateView, and
		  // onCreateView if the parent Activity is killed and restarted.
			
			savedInstanceState.putStringArrayList(STATE_LISTADO, this.listado);
			
			super.onSaveInstanceState(savedInstanceState);
		}
		
		
		// Called at the start of the visible lifetime. @Override
		public void onStart(){
		  super.onStart();
		  // Apply any required UI change now that the Fragment is visible.
		  
		  
		  
		}
		
		
}
