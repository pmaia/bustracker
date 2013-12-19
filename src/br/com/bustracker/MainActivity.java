package br.com.bustracker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initializeButton();
		initializeSpinner();

	}

	private void initializeButton() {
		final Button button = (Button) findViewById(R.id.btnTracker);
		button.setOnClickListener(new View.OnClickListener() {

			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), TrackingActivity.class);
				intent.putExtra("routeName", getRouteName());
				startActivity(intent);
			}

		});
	}

	private void initializeSpinner() {		
		Spinner spinner = (Spinner) findViewById(R.id.spnBusLines);
		// Create an ArrayAdapter using the string array and a default spinner layout
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
				R.array.bus_lines, android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		spinner.setAdapter(adapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	private String getRouteName() {
		Spinner spinner = (Spinner) findViewById(R.id.spnBusLines);
		return spinner.getSelectedItem().toString();
	}

}
