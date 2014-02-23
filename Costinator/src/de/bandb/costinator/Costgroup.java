package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class Costgroup extends Activity {
	
	private TextView costgroupTitle;
	private ImageButton addCostelement;
	
	private AlertDialog costClassDialog;
	private String ontime;
	private String periodical; 
	private String[] costClasses;  
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup);
		
		costgroupTitle = (TextView) findViewById(R.id.textViewTitleCostgroup);
		addCostelement = (ImageButton) findViewById(R.id.imageButtonAddNewCostelement);
		
		ontime = getString(R.string.name_RadioButton_OnetimeFixed);
		periodical = getString(R.string.name_RadioButton_PeriodicalFixed);
		costClasses = new String[] {ontime, periodical};
		
		// Der Title der in Main ausgewaehlten Kostengruppe wird angezeigt.
		Intent intent = getIntent();
		String title = intent.getStringExtra(Main.COSTGROUP_TITLE);
		costgroupTitle.setText(costgroupTitle.getText().toString() +" "+ title);
		
		//Kostenkategorie Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(Costgroup.this);
		builder.setTitle(R.string.title_activity_select_category);
		builder.setIcon(R.drawable.ic_action_new);
		builder.setItems(costClasses, new DialogInterface.OnClickListener() {

		@Override
		public void onClick(DialogInterface dialog, int which) {
			
		if (which == 0) {
			Intent intent = new Intent(Costgroup.this, NewCostelementOntime.class);
			startActivity(intent);
			
		} else {
			Intent intent = new Intent(Costgroup.this, NewCostelementPeriodical.class);
			startActivity(intent);
			
		}
	
		}
		});

		builder.setCancelable(true);
		costClassDialog = builder.create();
		
		addCostelement.setOnClickListener(addCostelementListener);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.costgroup, menu);
		return true;
	}
	
	/**
	 * Clicks auf ActionbarItems
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_addCostelement:
	        	costClassDialog.show();
	        	
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}

	
	
	
	/**
	 * Start NewCostelement Activity durch Click auf AddButton
	 */
	OnClickListener addCostelementListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			costClassDialog.show();
			
			

			}
	};
}
