package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;
import java.util.Date;

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
import android.widget.ListView;
import android.widget.TextView;

public class Costgroup extends Activity {
	
	
	static final int ONTIME_COSTELEMENT_REQUEST = 0;
	static final int PERIODICAL_COSTELEMENT_REQUEST = 1;
	
	private ListView costelementList;
	private ArrayList<CostelementListViewItem> items;
	
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
		costelementList = (ListView) findViewById(R.id.listViewCostgroup);
		
		ontime = getString(R.string.name_RadioButton_OnetimeFixed);
		periodical = getString(R.string.name_RadioButton_PeriodicalFixed);
		costClasses = new String[] {ontime, periodical};
		
		items = new ArrayList<CostelementListViewItem>();
		
		
		/**
		 * Dummy Data
		 */
		Date date = new Date();
		double value = 200.98;
		CostelementListViewItem costelement1 = new CostelementListViewItem("Werkstatt", value, date);
		CostelementListViewItem costelement2 = new CostelementListViewItem("Werkstatt", value +1, date, date, "monatlich");
		items.add(costelement1);
		items.add(costelement2);

		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
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
			startActivityForResult(intent, ONTIME_COSTELEMENT_REQUEST);
			
		} else {
			Intent intent = new Intent(Costgroup.this, NewCostelementPeriodical.class);
			startActivityForResult(intent, PERIODICAL_COSTELEMENT_REQUEST);
			
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
	
	/**
	 * Wird automatisch aufgerufen wenn gestartete NewCostelementOntime oder NewCostelementPeriodical beendet wird.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == ONTIME_COSTELEMENT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            
	        	//TODO Handle Receive Data put to ListView
	        	
	        	} else {
	        	//TODO Exception
	        	
	        	} 
	        
	    } else if (requestCode == PERIODICAL_COSTELEMENT_REQUEST) {
	    	
	    	if (resultCode == RESULT_OK) {
	            
	    		//TODO Handle Receive Data put to ListView
	    		
	    		} else {
	        	//TODO Exception
	        	
	    		} 
	    }	
	        	
	        
	    }
	
	
	
}
	

