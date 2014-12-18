package br.edu.uea.mobilidadeUrbana;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CustomDialogFragment extends DialogFragment {

	private double latitude;
	private double longitude;
	private MeuLocal local;
	public CustomDialogFragment(double latitude, double longitude){
		this.latitude = latitude;
		this.longitude = longitude;
	}
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.Theme_Holo_Light);
		setCancelable(false);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		super.onCreateView(inflater, container, savedInstanceState);
		Log.i("Script", "onCreateView()");
		
		final View view = inflater.inflate(R.layout.dialog_fragment_style, container);
		Button btSalvar = (Button) view.findViewById(R.id.button1);
		btSalvar.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				
				EditText descricao = (EditText) view.findViewById(R.id.editText1);
				
				if(descricao.length() <= 1){
					//Toast.makeText(base, "Todos os Campos São Obrigatorios", Toast.LENGTH_LONG).show();
				}else{
					
					local = new MeuLocal(descricao.getText().toString(), latitude, longitude); 
					MainActivity.banco.inserirLocal(local);		
					dismiss();
					
				}
				
			}
		});
		
		Button btCancelar = (Button) view.findViewById(R.id.button2);
		btCancelar.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				dismiss();
				
			}
		});
		return(view);
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		Log.i("Script", "onActivityCreated()");
		
		getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
	}
	
	
}
