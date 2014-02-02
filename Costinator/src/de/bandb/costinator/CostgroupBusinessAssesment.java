package de.bandb.costinator;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class CostgroupBusinessAssesment extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup_business_assesment);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.costgroup_business_assesment, menu);
		return true;
	}

}
