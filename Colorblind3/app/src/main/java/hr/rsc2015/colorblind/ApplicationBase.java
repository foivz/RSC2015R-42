package hr.rsc2015.colorblind;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import static android.location.LocationManager.GPS_PROVIDER;

/**
 * Created by nicba on 22/11/2015.
 */
public class ApplicationBase extends Application {
	ArrayList<LocationInterface> locationInterfaces = new ArrayList<>();
	protected LocationInterface locationInterface = null;
	SharedPreferences sharedPreferences;

	@Override
	public void onCreate() {
		super.onCreate();
		LocationManager locationManager = (LocationManager)
				getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new LocationUpdateListener();
		//noinspection ResourceType
		locationManager.requestLocationUpdates(
				GPS_PROVIDER, 1000, 1, locationListener);
		sharedPreferences = getSharedPreferences("hr.rsc2015.colorblind", Context.MODE_PRIVATE);
	}

	private class LocationUpdateListener implements android.location.LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			Toast.makeText(
					getBaseContext(),
					"Location changed: Lat: " + loc.getLatitude() + " Lng: "
							+ loc.getLongitude(), Toast.LENGTH_SHORT).show();
			if (sharedPreferences.getInt("id", -1) != -1)
				new LocationUpdateTask(new LatLng(loc.getLatitude(), loc.getLongitude())).execute();
			for (LocationInterface locationInterface : locationInterfaces) {
				if (locationInterface != null)
					if (sharedPreferences.getInt("id", -1) != -1) {
						locationInterface.locationUpdate(new LatLng(loc.getLatitude(), loc.getLongitude()));
					}
			}
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}

	public void addLocationInterface(LocationInterface locationInterface) {
		locationInterfaces.add(locationInterface);
	}

	public interface LocationInterface {
		void locationUpdate(LatLng latLng);
	}

	public class LocationUpdateTask extends AsyncTask<String, Void, String> {
		private final LatLng latLng;
		private int id = -1;

		public LocationUpdateTask(LatLng latLng) {
			this.latLng = latLng;
		}

		@Override
		protected void onPreExecute() {
			Log.d("TAG", "onPreExecute: ");
		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
				URL url = new URL("http://192.168.10.10/API/location.php");
				Map<String, Object> params = new LinkedHashMap<>();
				params.put("id", sharedPreferences.getInt("id", -1));
				params.put("data", latLng.latitude + ":" + latLng.longitude);
				StringBuilder postData = new StringBuilder();
				for (Map.Entry<String, Object> param : params.entrySet()) {
					if (postData.length() != 0) postData.append('&');
					postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
					postData.append('=');
					postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
				}
				byte[] postDataBytes = postData.toString().getBytes("UTF-8");
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("POST");
				conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
				conn.setDoOutput(true);
				conn.getOutputStream().write(postDataBytes);
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
				return in.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "false";
		}

		@Override
		protected void onPostExecute(String headerCode) {
			Log.d("LocUpd", "onPostExecute: " + headerCode);
		}
	}
}
