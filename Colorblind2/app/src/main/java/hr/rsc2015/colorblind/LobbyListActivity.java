package hr.rsc2015.colorblind;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class LobbyListActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_lobby_list);
		findViewById(R.id.createNew).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
	}
}
