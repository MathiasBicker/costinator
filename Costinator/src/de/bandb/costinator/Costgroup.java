package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class Costgroup extends Activity {
	
	private TextView costgroupTitle;
	
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup);
		
		costgroupTitle = (TextView) findViewById(R.id.textViewTitleCostgroup);
		
		// Der Title der in Main ausgewaehlten Kostengruppe wird angezeigt.
		Intent intent = getIntent();
		String title = intent.getStringExtra(Main.COSTGROUP_TITLE);
		costgroupTitle.setText(costgroupTitle.getText().toString() +" "+ title);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.costgroup, menu);
		return true;
	}

}
