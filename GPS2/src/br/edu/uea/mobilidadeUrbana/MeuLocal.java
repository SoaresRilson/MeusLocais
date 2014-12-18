package br.edu.uea.mobilidadeUrbana;

public class MeuLocal {
	
	private long id;
	private double latitude;
	private double longitude;
	private String descricao;
	
	public MeuLocal( long id, String descricao, double latitude, double longitude ){
			
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.descricao = descricao;		
		
	}
	
	public MeuLocal(  String descricao, double latitude, double longitude){
		
		 this.latitude = latitude;
		 this.longitude = longitude;
		 this.descricao = descricao;		
		
	}
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public double getLatitude() {
		return latitude;
	}
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	public double getLongitude() {
		return longitude;
	}
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	

}
