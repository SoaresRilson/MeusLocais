package br.edu.uea.mobilidadeUrbana;



import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.util.Log;

public class GerenciarBanco {
	
	private OpenHelper openHelper;
	private SQLiteDatabase db;
	private SQLiteStatement insertStmt;
	private static final String INSERT = "insert into "+ 
						OpenHelper.TABLE_NAME+ " ("
						+OpenHelper.COLUNA_DESCRIÇAO+ ", "
						+OpenHelper.COLUNA_LATITUDE+ ", "
						+OpenHelper.COLUNA_LONGITUDE+") values (?,?,?)";
	
	public GerenciarBanco(Context context) {
		openHelper = new OpenHelper(context);
	    this.db = openHelper.getWritableDatabase();
	    this.insertStmt = this.db.compileStatement(INSERT);	     
	}
	
	public long inserirLocal(MeuLocal local) {
		this.insertStmt.bindString(1, local.getDescricao());
		this.insertStmt.bindDouble(2, local.getLatitude());
		this.insertStmt.bindDouble(3, local.getLongitude());
		Log.i("Script", "inserirLocal()");
		return this.insertStmt.executeInsert();
		
	}
	
	public List<MeuLocal> recuperarLocais (){
		List<MeuLocal> locais = new ArrayList<MeuLocal>();
		Cursor cursor = this.db.query(OpenHelper.TABLE_NAME, new String[]{"id",OpenHelper.COLUNA_DESCRIÇAO, OpenHelper.COLUNA_LATITUDE, OpenHelper.COLUNA_LONGITUDE }, null, null, null, null, null);
		
		if(cursor.getCount() > 0){
	    	cursor.moveToFirst();
 		  do{
	   
	         long id = cursor.getInt(0);
             String descricao = cursor.getString(1);
             double latitude = cursor.getDouble(2);
             double longitude = cursor.getDouble(3);
             MeuLocal local = new MeuLocal(id, descricao, latitude, longitude);
             locais.add(local);
             
	       }while(cursor.moveToNext()); 
	    }
		return locais;		
	}
	
	public void limparTabelaObjeto(String descricao) {
        int delete = db.delete(OpenHelper.TABLE_NAME, OpenHelper.COLUNA_DESCRIÇAO + " = '"+descricao+"'", new String[] {});
        	
	}

}
