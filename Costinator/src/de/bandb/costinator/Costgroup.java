package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;


import de.bandb.costinator.customadapter.CostelementListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewCostgroup;
import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.app.Activity;


import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Costgroup extends Activity {
	
	
	static final int NEW_COSTELEMENT_REQUEST = 10;
	
	static final String CURRENCY_EURO = "€";
	static final String CURRENCY_DOLLAR_US = "$";
	
	private ListView costelementList;
	private ArrayList<CostelementListViewItem> items;
	private ArrayList<TCostelement> elementList;
	
	private TextView costgroupTitle;
	private ImageButton addCostelement;
	
	
	
	  
	
	 

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup);
		
		costgroupTitle = (TextView) findViewById(R.id.textViewTitleCostgroup);
		addCostelement = (ImageButton) findViewById(R.id.imageButtonAddNewCostelement);
		costelementList = (ListView) findViewById(R.id.listViewCostgroup);
		
		
		
		
		items = new ArrayList<CostelementListViewItem>();
		
		
		/**
		 * Dummy Data
		 */
		double value1 = 200.98;
		double value2 = 5057.56;
		CostelementListViewItem costelement1 = new CostelementListViewItem("Hausmeister", "Herr Klaus", value1,"€", "wöchentlich", "Toleranz: 15%" );
		CostelementListViewItem costelement2 = new CostelementListViewItem("Heizung", "Gas", value2,"€" , "jährlich");
		items.add(costelement1);
		items.add(costelement2);

		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
		// Der Title der in Main ausgewaehlten Kostengruppe wird angezeigt.
		Intent intent = getIntent();
		String title = intent.getStringExtra(Main.COSTGROUP_TITLE);
		costgroupTitle.setText(costgroupTitle.getText().toString() +" "+ title);
		
		
		
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
	        	
	        	Intent intentNew = new Intent(Costgroup.this, NewCostelement.class);
				startActivityForResult(intentNew, NEW_COSTELEMENT_REQUEST);
	        	
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	            
	        case R.id.action_assessCostgroup:
	        	Intent intent = new Intent(Costgroup.this, BusinessAssesment.class);
	        	
	        	TCostgroup costgroup = new TCostgroup();
	        	costgroup.setDescription("Stephanstr. 27A");
	        	costgroup.setId(1);
	        	costgroup.setName("Haus");
	        	
	        	TCostelement costelement1 = new TCostelement();
	        	costelement1.setCostgroup(costgroup);
	        	costelement1.setDescription("Frau Schmitt");
	        	costelement1.setName("Putzfrau");
	        	costelement1.setPeriod(2);
	        	costelement1.setValue(100);
	        	costelement1.setId(1);
	        	
	        	TCostelement costelement2 = new TCostelement();
	        	costelement2.setCostgroup(costgroup);
	        	costelement2.setDescription("Schäferhund");
	        	costelement2.setName("Wachhund");
	        	costelement2.setPeriod(3);
	        	costelement2.setValue(1000);
	        	costelement2.setId(2);
	        	
	        	elementList = new ArrayList<TCostelement>();
	        	
	        	elementList.add(costelement1);
	        	elementList.add(costelement2);
	        	
	        	int days = 180;
	        	
	        	intent.putExtra(BusinessAssesment.DAYSTAG, days);
	        	
	        	
	        	startActivity(intent);
	        	
	        	
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
			Intent intent = new Intent(Costgroup.this, NewCostelement.class);
			startActivityForResult(intent, NEW_COSTELEMENT_REQUEST);
			
			

			}
	};
	
	/**
	 * Wird automatisch aufgerufen wenn gestartete NewCostelementOntime oder NewCostelementPeriodical beendet wird.
	 */
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if (requestCode == NEW_COSTELEMENT_REQUEST) {
	        // Make sure the request was successful
	        if (resultCode == RESULT_OK) {
	            
	        		String name =		data.getStringExtra(NewCostelement.PERIODICAL_COSTELEMENT_NAME);
		            String desc =		data.getStringExtra(NewCostelement.PERIODICAL_COSTELEMENT_DESC);
		            double value = 		data.getDoubleExtra(NewCostelement.PERIODICAL_COSTELEMENT_VALUE, 0);
		            String periode = 	data.getStringExtra(NewCostelement.PERIODICAL_COSTELEMENT_PERIODE);
		            String tolerance = 	data.getStringExtra(NewCostelement.PERIODICAL_COSTELEMENT_TOLERANCE);
		            
		            //Wenn User --opitonal-- bei Spinner tolerance auswählt, dann darf dies nicht im Layout angezeigt werden
		            String[] periodes = getResources().getStringArray(R.array.tolerances);
		            //--opitonal-- wert
		            String optionalString= periodes[0];
		            	
		            if(tolerance.equals(optionalString)) 
		            tolerance = "";
		            
		            CostelementListViewItem newCostelement = new CostelementListViewItem(name, desc, value,CURRENCY_EURO, periode, tolerance); 
		            addCostelement(newCostelement);
	        	
	        	
	        	} else {
	        	//TODO Exception
	        	
	        	} 
	        
	    } 
	    		  
	}
	
	public void addCostelement (CostelementListViewItem costelement) {
		
		items.add(costelement);
		
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
	}
	
	
}
	

