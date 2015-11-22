package hr.rsc2015.colorblind;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

	private static final String TAG = "APP";
	Button loginButton;
	EditText usernameEditText, passwordEditText;
	LoginActivity instance;
	SharedPreferences sharedPreferences;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signin);
		instance = this;
		sharedPreferences = getSharedPreferences("hr.rsc2015.colorblind", Context.MODE_PRIVATE);
		Intent i = new Intent(this, GameActivity.class);
		Log.d(TAG, String.valueOf(sharedPreferences.getInt("id", -1)));
		if (sharedPreferences.getInt("id", -1) != -1)
			startActivity(i);
		loginButton = (Button) findViewById(R.id.buttonLogin);
		usernameEditText = (EditText) findViewById(R.id.editTextUsername);
		passwordEditText = (EditText) findViewById(R.id.editTextPassword);
		loginButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new LoginTask(instance).execute(usernameEditText.getText().toString(), passwordEditText.getText().toString());
			}
		});
	}

	public class LoginTask extends AsyncTask<String, Void, String> {
		private LoginActivity activity;
		private int id = -1;

		public LoginTask(LoginActivity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
				Log.d(TAG, "onClick: 2");
				URL url = new URL("http://192.168.10.10/API/login.php");
				Map<String, Object> params = new LinkedHashMap<>();
				params.put("username", arg0[0]);
				Log.d(TAG, arg0[0]);
				Log.d(TAG, arg0[1]);
				params.put("password", arg0[1]);
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
			Log.d("APP", "onPostExecute: " + headerCode);
			if (headerCode.equalsIgnoreCase("false")) {
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
			} else {
				Log.d(TAG, "onPostExecute: " + headerCode);
				try {
					JSONObject jsonObject = new JSONObject(headerCode);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					Log.d(TAG, "onPostExecute: " + Integer.parseInt(jsonObject.getString("ID")));
					editor.putInt("id", Integer.parseInt(jsonObject.getString("ID")));
					editor.apply();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
