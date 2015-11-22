package hr.rsc2015.colorblind;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

import static android.location.LocationManager.GPS_PROVIDER;

public class MainActivity extends AppCompatActivity {
	public static ArrayList<String> buffer = new ArrayList<>();
	String TAG = "ALL";
	Button openMap;
	public static Update update;
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	private GoogleApiClient client;
	Button joinRedTeam, joinBlueTeam;
	CheckBox readyBox;
	MainActivity INSTANCE;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		INSTANCE = this;
		LocationManager locationManager = (LocationManager)
				getSystemService(Context.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener();
		openMap = (Button) findViewById(R.id.openMap);
		openMap.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(INSTANCE, MapsActivity.class);
				startActivity(intent);
			}
		});
		//noinspection ResourceType
		locationManager.requestLocationUpdates(
				GPS_PROVIDER, 1000, 1, locationListener);
		Thread gpsThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Looper.prepare();
					BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
					Log.d(TAG, "run: ");
					final Socket clientSocket = new Socket("192.168.10.2", 31337);
					Log.d(TAG, "run: 1");
					DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
					final BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
					Thread inThread = new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								Looper.prepare();
								while (true) {
									String in = inFromServer.readLine();
									Log.d(TAG, "run: 1" + in);
									switch (in.charAt(0)) {
										case 'a': {
											if (MainActivity.update != null)
												MainActivity.update.process(in.substring(1, in.length()));
										}
									}
									Thread.sleep(1000);
								}
							} catch (IOException e) {
								e.printStackTrace();
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					});
					inThread.start();
					while (clientSocket.isConnected()) {
						if (MainActivity.buffer.size() > 0) {
							Log.d(TAG, "run: while");
							outToServer.writeBytes(MainActivity.buffer.get(0) + "\n");
							MainActivity.buffer.remove(0);
						}
					}
					clientSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		//gpsThread.start();
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
		joinRedTeam = (Button) findViewById(R.id.RedTeam);
		joinBlueTeam = (Button) findViewById(R.id.BlueTeam);
		joinRedTeam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				buffer.add("1:ID:1");
				joinRedTeam.setBackgroundColor(Color.RED);
				joinRedTeam.setEnabled(false);
				joinBlueTeam.setEnabled(false);
			}
		});
		joinBlueTeam.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				buffer.add("1:ID:2");
				joinBlueTeam.setBackgroundColor(Color.BLUE);
				joinRedTeam.setEnabled(false);
				joinBlueTeam.setEnabled(false);
			}
		});
		readyBox = (CheckBox) findViewById(R.id.ready);
		readyBox.setOnCheckedChangeListener(
				new CompoundButton.OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
						buffer.add("2:" + (isChecked ? 1 : 0));
						Log.d(TAG, "onCheckedChanged: ");
					}
				}
		);
	}

	@Override
	public void onStart() {
		super.onStart();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		client.connect();
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://hr.rsc2015.colorblind/http/host/path")
		);
		AppIndex.AppIndexApi.start(client, viewAction);
	}

	@Override
	public void onStop() {
		super.onStop();

		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		Action viewAction = Action.newAction(
				Action.TYPE_VIEW, // TODO: choose an action type.
				"Main Page", // TODO: Define a title for the content shown.
				// TODO: If you have web page content that matches this app activity's content,
				// make sure this auto-generated web page URL is correct.
				// Otherwise, set the URL to null.
				Uri.parse("http://host/path"),
				// TODO: Make sure this auto-generated app deep link URI is correct.
				Uri.parse("android-app://hr.rsc2015.colorblind/http/host/path")
		);
		AppIndex.AppIndexApi.end(client, viewAction);
		client.disconnect();
	}

	private class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location loc) {
			Toast.makeText(
					getBaseContext(),
					"Location changed: Lat: " + loc.getLatitude() + " Lng: "
							+ loc.getLongitude(), Toast.LENGTH_SHORT).show();
			buffer.add("3:" + loc.getLongitude() + ";" + loc.getLatitude());
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

	public interface Update {
		public void process(String data);
	}
}
