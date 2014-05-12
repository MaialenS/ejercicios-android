package com.maialen.listafragmentos;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.View.OnKeyListener;
import android.widget.Button;
import android.widget.EditText;

public class FragmentFrom extends Fragment {
	
	public interface IAddItem {
		public void addItem(String txt);
	}

	private IAddItem activity;
	private EditText txtNuevaTarea;
	private Button boton;
	
	//para obligar al activity que te llama a implementar un metodo concreto se define una interfaz
	//que lo contenga y se comprueba que es implementa la interfaz
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			this.activity = (IAddItem)activity;
		} catch(ClassCastException e) {
			throw new ClassCastException(activity.toString() + " no implementa la interfaz IAddItem");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment

		View v = inflater.inflate(R.layout.fragment_form, container, false);

		this.boton= (Button) v.findViewById(R.id.btn_tarea);
		this.txtNuevaTarea = (EditText) v.findViewById(R.id.txt_tarea);

		this.boton.setOnClickListener(new OnClickListener(){
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String men = String.valueOf(txtNuevaTarea.getText());

				if (men.length() > 0) {
					// pasar los datos al otro fragmento
					activity.addItem(txtNuevaTarea.getText().toString());
					txtNuevaTarea.setText("");
				}

				Log.d("Mio", "se crea nueva tarea");

				// pasar los datos al otro fragmento
			}
			
		});
		
		
		this.txtNuevaTarea.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == KeyEvent.ACTION_DOWN) {
					if (event.getAction() == KeyEvent.KEYCODE_DPAD_CENTER
							|| event.getAction() == KeyEvent.KEYCODE_ENTER) {

						String men = String.valueOf(txtNuevaTarea.getText());

						if (men.length() > 0) {
							// pasar los datos al otro fragmento
							activity.addItem(txtNuevaTarea.getText().toString());
							txtNuevaTarea.setText("");
						}

						Log.d("Mio", "se crea nueva tarea");

						// pasar los datos al otro fragmento

					}
					return false;
				}
				return false;
			}
		});

		return v;
	}

}