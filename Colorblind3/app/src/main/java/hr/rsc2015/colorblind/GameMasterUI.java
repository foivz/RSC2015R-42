package hr.rsc2015.colorblind;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class GameMasterUI extends AppCompatActivity {

	SharedPreferences sharedPreferences;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_master_ui);
		sharedPreferences = getSharedPreferences("hr.rsc2015.colorblind", Context.MODE_PRIVATE);
	}
	public class CreateGameTask extends AsyncTask<String, Void, String> {

		public CreateGameTask() {
		}

		@Override
		protected void onPreExecute() {
		}

		@Override
		protected String doInBackground(String... arg0) {
			try {
				URL url = new URL("http://192.168.10.10/API/generate_game.php");
				Map<String, Object> params = new LinkedHashMap<>();
				params.put("la", "la");
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
			if (headerCode.equalsIgnoreCase("false")) {
				Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
			} else {
				try {
					JSONObject jsonObject = new JSONObject(headerCode);
					SharedPreferences.Editor editor = sharedPreferences.edit();
					editor.putInt("id", Integer.parseInt(jsonObject.getString("ID")));
					editor.commit();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
