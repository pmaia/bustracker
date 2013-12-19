package br.com.bustracker.model;

import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Context;
import android.location.Location;

public class Route {
	private final String ROUTES_FILE = "routes.txt";
	private final List<RouteLocation> locations;
	private final String name;
	
	private class RouteLocation {
		private double latitude;
		private double longitude;
		
		public RouteLocation(Location location) {
			setLatitude(location.getLatitude());
			setLongitude(location.getLongitude());			
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
		
		public JSONArray toJSON() {
			JSONArray json = new JSONArray();
			json.put(Double.toString(getLatitude()));
			json.put(Double.toString(getLongitude()));
			return json;
		}
	}
	public Route(String name) {
		this.locations = new ArrayList<RouteLocation>();
		this.name = name;
	}
	
	public void appendLocation(Location newLocation) {
		locations.add(new RouteLocation(newLocation));
	}
	
	public void save(Context context) {
		FileOutputStream fos;
		try {
			fos = context.openFileOutput(ROUTES_FILE, Context.MODE_PRIVATE);
			JSONObject json = new JSONObject();		
			json.put(name, serializeLocations());
			
		    fos.write(json.toString().getBytes());
			fos.close();
		} catch (Exception e) {			
			throw new RuntimeException(e);
		}				
	}

	private JSONArray serializeLocations() {
		JSONArray locationsJSON = new JSONArray();
		for (RouteLocation location : locations) {
			JSONArray locationJSON = location.toJSON();
			locationsJSON.put(locationJSON);
		}
		return locationsJSON;
	}
}
