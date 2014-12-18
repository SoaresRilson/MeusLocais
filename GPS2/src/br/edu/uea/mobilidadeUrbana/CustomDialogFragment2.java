package br.edu.uea.mobilidadeUrbana;

import android.app.Activity;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CustomDialogFragment2 extends DialogFragment {
	
	private String descricao;
	public CustomDialogFragment2(String descricao){
		this.descricao = descricao;
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
		
		final View view = inflater.inflate(R.layout.dialog_fragment_style_2, container);
		TextView tvDescricao = (TextView) view.findViewById(R.id.textView2);
		tvDescricao.setText(descricao);
		Button btExcluir = (Button) view.findViewById(R.id.button1);
		btExcluir.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
							
				MainActivity.banco.limparTabelaObjeto(descricao);
				MainActivity.map.clear();
				dismiss();
				
			}
		});
		
		Button btCancelar = (Button) view.findViewById(R.id.button2);
		btCancelar.setOnClickListener(new Button.OnClickListener(){
			@Override
			public void onClick(View v) {
				MainActivity.isDialogFragmentOpen = false;
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
