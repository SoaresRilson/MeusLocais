package br.edu.uea.mobilidadeUrbana;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class OpenHelper extends SQLiteOpenHelper{
	private static final String DATABASE_NAME = "MeusLocais.db";
	private static final int DATABASE_VERSION = 1;
	public static final String TABLE_NAME = "Locais";
	public static final String	COLUNA_DESCRIÇAO = "Descricao";
	public static final String COLUNA_LONGITUDE = "Longitude";
	public static final String	COLUNA_LATITUDE = "Latitude";
	

	public OpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("CREATE TABLE " + TABLE_NAME + 
        		" (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
        		COLUNA_DESCRIÇAO + " TEXT, "+  
        		COLUNA_LATITUDE + " DOUBLE, " + 
        		COLUNA_LONGITUDE + " DOUBLE)");
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		Log.i("Script", "onUpgrade");
		
	}

}
