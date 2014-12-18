package br.edu.uea.mobilidadeUrbana;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class MainActivity extends FragmentActivity  implements LocationListener{
	private SupportMapFragment mapFrag;
	public static GoogleMap map;
	private LocationManager locationManager;
	private Marker marker;
	private List<Marker> markers;
	private List<MeuLocal> locais;
	public static GerenciarBanco banco;
	private LatLng localizacaoAtual; 
	public static boolean remuve = false;
	
	
	protected void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_activity);
		
		Button btListar = (Button) findViewById(R.id.button1);
		btListar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				configMarker();			
			}
		});
		
		Button btLimpar = (Button) findViewById (R.id.button2);
		btLimpar.setOnClickListener(new  OnClickListener() {
			
			@Override
			public void onClick(View v) {
				map.clear();				
			}
		});
		
		Button btMinhaLocalizacao = (Button) findViewById(R.id.button3);
		btMinhaLocalizacao.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				map.clear();
				if(localizacaoAtual != null){
					CameraPosition cameraPosition = new CameraPosition.Builder().target(localizacaoAtual).zoom(20).bearing(0).tilt(0).build();
					CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
					map.animateCamera(update);
					customAddMarker( localizacaoAtual, "Rilson", "Minha posição atual");
				}
			}
		});
		
		MainActivity.banco = new GerenciarBanco(this);
		GoogleMapOptions options = new GoogleMapOptions();
		options.zOrderOnTop(true);
		mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment1);
		map = mapFrag.getMap();
		configMap();
	}
	
	@Override
	public void onResume(){
		super.onResume();
		
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		
		if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
			Intent it = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
			startActivity(it);
		}
		else{
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 10, this);
		}
	}
	
	@Override
	public void onPause(){
		super.onPause();
		locationManager.removeUpdates(this);
		
	}
	
	public void configMap(){
		map = mapFrag.getMap();
		map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
		
	}
	
	public void configLocation(LatLng latLng){
		CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(20).bearing(0).tilt(0).build();
		CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
		
		map.setMyLocationEnabled(true);
		map.animateCamera(update);
		MyLocation myLocation = new MyLocation();
		map.setLocationSource(myLocation);
		myLocation.setLocation(latLng);
		customAddMarker(latLng, "Rilson", "Minha posição atual");
		
		map.setInfoWindowAdapter(new InfoWindowAdapter(){

			@Override
			public View getInfoContents(Marker marker) {
				TextView tv = new TextView(MainActivity.this);
				tv.setText(Html.fromHtml("<b><font color=\"#ff0000\">"+marker.getTitle()+":</font></b> "+marker.getSnippet()));
				
				return tv;
			}

			@Override
			public View getInfoWindow(Marker marker) {
				LinearLayout ll = new LinearLayout(MainActivity.this);
				ll.setPadding(30, 30, 30, 30);
				ll.setBackgroundColor(Color.GRAY);
				
				TextView tv = new TextView(MainActivity.this);
				tv.setText(Html.fromHtml(marker.getTitle()+": "+marker.getSnippet()));
				ll.addView(tv);
				
				return ll;
			}
			
		});
		
		map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
			@Override
			public void onMapClick(LatLng latLng) {
				Log.i("Script", "setOnMapClickListener()");
				
				/*if(marker != null){
					marker.remove();
				}*/
				map.clear();
				customAddMarker(new LatLng(latLng.latitude, latLng.longitude), "Rilson", "Outro local");
				
			}
		});
		
		map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
			@Override
			public boolean onMarkerClick(Marker marker) {
				Log.i("Script", "3: Marker: "+marker.getTitle());
				return false;
			}
		});
		
		map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
			@Override
			public void onInfoWindowClick(Marker marker) {
				Log.i("Script", "4: Marker: "+marker.getTitle());
				if (marker.getTitle().toString().equals("Rilson")){
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					CustomDialogFragment cdf = new CustomDialogFragment(marker.getPosition().latitude, marker.getPosition().longitude);
					cdf.show(ft, "dialog");
				}else{
					FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
					CustomDialogFragment2 cdf = new CustomDialogFragment2(marker.getSnippet().toString());
					cdf.show(ft, "dialog");
					configMarker();
				}
				
					
			}
		});
	}
	
	public void customAddMarker(LatLng latLng, String title, String snippet){
		MarkerOptions options = new MarkerOptions();
		options.position(latLng).title(title).snippet(snippet).draggable(true);
			
		marker = map.addMarker(options);
	}
	
	
	public void configMarker(){
		map.clear();
		LatLng latlng = null;
		locais = MainActivity.banco.recuperarLocais();
		if (locais.size() < 1 ){
			Toast.makeText(getApplicationContext(), "Você ainda não possui nenhum favorito", Toast.LENGTH_LONG).show();
		}
		for (int i = 0; i < locais.size(); i++){
			latlng = new LatLng(locais.get(i).getLatitude(), locais.get(i).getLongitude()); 
			customAddMarker(latlng, "Meu Local", locais.get(i).getDescricao());
			
		}
		
		if(latlng != null){
			CameraPosition cameraPosition = new CameraPosition.Builder().target(latlng).zoom(12).bearing(0).tilt(0).build();
			CameraUpdate update = CameraUpdateFactory.newCameraPosition(cameraPosition);
			map.animateCamera(update);
		}
	}
	
	public class MyLocation implements LocationSource{
		private OnLocationChangedListener listener;
		
		@Override
		public void activate(OnLocationChangedListener listener) {
			this.listener = listener;
			
		}

		@Override
		public void deactivate() {
			Log.i("Script", "deactivate()");
		}
		
		
		public void setLocation(LatLng latLng){
			Location location = new Location(LocationManager.GPS_PROVIDER);
			location.setLatitude(latLng.latitude);
			location.setLongitude(latLng.longitude);
			
			if(listener != null){
				listener.onLocationChanged(location);
			}
		}
	}
	
	public void turnOffDialogFragment(){
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		CustomDialogFragment cdf = (CustomDialogFragment) getSupportFragmentManager().findFragmentByTag("dialog");
		if(cdf != null){
			cdf.dismiss();
			ft.remove(cdf);
		}
	}

	@Override
	public void onLocationChanged(Location location) {
		
			localizacaoAtual = new LatLng(location.getLatitude(), location.getLongitude());
			configLocation(new LatLng(location.getLatitude(), location.getLongitude()));
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

}
