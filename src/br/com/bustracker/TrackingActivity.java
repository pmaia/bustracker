package br.com.bustracker;

import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import br.com.bustracker.model.Route;

public class TrackingActivity extends Activity implements LocationListener {

	private LocationManager locationManager;
	private Route route;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tracking);
		// Get the location manager
		route = new Route(getIntent().getStringExtra("routeName"));
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		initializeStopTrackingButton();		
	}
	
	private void initializeStopTrackingButton() {
		final Button button = (Button) findViewById(R.id.btnStopTracking);
		final TrackingActivity activity = this;
		button.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				activity.stopLocationManager();
				
				route.save(getApplicationContext());
				Context context = getApplicationContext();
				Toast toast = Toast.makeText(context, "Route Saved", Toast.LENGTH_LONG);
				toast.show();
				finish();				
			}

		});		
	}
	
	protected void stopLocationManager() {
		locationManager.removeUpdates(this);
	}
	
	private void startLocationManager() {
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
	}

	private void showLocation(Location location) {
		if(location == null) {
			return;
		}
		
		route.appendLocation(location);
		showDebugLocationInfo(location);		
	}

	@Override
	protected void onResume() {
		super.onResume();
		startLocationManager();
	}

	@Override
	protected void onPause() {
		super.onPause();
		stopLocationManager();
	}

	@Override
	public void onLocationChanged(Location location) {
		showLocation(location);
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}
	
	private void showDebugLocationInfo(Location location) {
		String strLocation = String.format(Locale.getDefault(), "Lat.: %f Long.: %f\n", location.getLatitude(), location.getLongitude());
		final EditText debugText = (EditText) findViewById(R.id.txtDebugTracking);
		debugText.append(strLocation);		
	}	
}
