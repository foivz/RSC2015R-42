package hr.rsc2015.colorblind;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

	EditText username, password;
	Button login;
	String TAG = "APP";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		username = (EditText) findViewById(R.id.username);
		password = (EditText) findViewById(R.id.password);
		login = (Button) findViewById(R.id.login);
		login.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				login();
			}
		});

	}

	public void login() {
		if (username.getText().toString().length() > 0 && password.getText().toString().length() > 0) {
			new LoginTask(this).execute(username.getText().toString(), password.getText().toString());
			Log.d(TAG, "login: ");
		}
	}

	public class LoginTask extends AsyncTask<String, Void, Integer> {
		private LoginActivity activity;
		private int id = -1;

		public LoginTask(LoginActivity activity) {
			this.activity = activity;
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected Integer doInBackground(String... arg0) {
			try {
				URL url = new URL("http://192.168.10.10/API/login.php");
				Map<String, Object> params = new LinkedHashMap<>();
				params.put("username", arg0[0]);
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
				return (in.readLine().equalsIgnoreCase("true")) ? 1 : 0;
			} catch (IOException e) {
				e.printStackTrace();
			}
			return 0;
		}

		@Override
		protected void onPostExecute(Integer headerCode) {
			if (headerCode == 1) {
				Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();
			} else {

			}
		}
	}

}
