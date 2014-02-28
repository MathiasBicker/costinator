package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import de.bandb.costinator.CostgroupBusinessAssesmentDialogFragment.onSubmitListenerCostgroupBusinessAssesment;
import de.bandb.costinator.customadapter.CostelementListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewCostgroup;
import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Costgroup extends FragmentActivity implements onSubmitListenerCostgroupBusinessAssesment {
	
	private static final int	NEW_COSTELEMENT_REQUEST = 10;
	private static final String	LOGTAG 					= "Costgroup";
	
	private ListView 							costelementList;
	private ArrayList<CostelementListViewItem> 	items;
	private TextView 							costgroupTitle;
	private TextView							costgroupDesc;
	private TextView							costgroupTotalCost;
	private ImageButton 						addCostelement;
	private TCostgroup							group;
	private OnClickListener addCostelementListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(Costgroup.this, NewCostelement.class);
			startActivityForResult(intent, NEW_COSTELEMENT_REQUEST);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup);
		
		costgroupTitle 		= (TextView) findViewById(R.id.textViewTitleCostgroup);
		addCostelement 		= (ImageButton) findViewById(R.id.imageButtonAddNewCostelement);
		costelementList 	= (ListView) findViewById(R.id.listViewCostgroup);
		costgroupDesc		= (TextView) findViewById(R.id.business_assesment_textViewTitleCostgroupDesc);
		costgroupTotalCost  = (TextView) findViewById(R.id.textViewTitleCostgroupCost);
		group 				= new TCostgroup();
		items 				= new ArrayList<CostelementListViewItem>();
		String[] periods 	= getResources().getStringArray(R.array.periods);
		String currency 	= getResources().getString(R.string.currency);
		String tolerancetxt = getResources().getString(R.string.tolerancetxt);
		
		//Dummy Data
		/*
		double value1 = 200.98;
		double value2 = 5057.56;
		CostelementListViewItem costelement1 = new CostelementListViewItem("Hausmeister", "Herr Klaus", value1, getResources().getString(R.string.currency), periods[3], getResources().getString(R.string.tolerancetxt) + "15" + getResources().getString(R.string.percent));
		CostelementListViewItem costelement2 = new CostelementListViewItem("Heizung", "Gas", value2, getResources().getString(R.string.currency) , periods[4]);
		items.add(costelement1);
		items.add(costelement2);
		*/
		//----
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
		//showing name,desc, totalCost of costgroup as title
		Intent intent 		= getIntent();
		String title		= intent.getStringExtra(Main.COSTGROUP_TITLE);
		String desc			= intent.getStringExtra(Main.COSTGROUP_DESC);
		String totalCost	= intent.getStringExtra(Main.COSTGROUP_TOTAL_COAST);
		group.setName(title);
		group.setDescription(desc);
		costgroupTitle.setText(costgroupTitle.getText().toString() +" "+ title);
		costgroupDesc.setText(desc);
		//costgroupTotalCost.setText(totalCost);
		
		
		addCostelement.setOnClickListener(addCostelementListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.costgroup, menu);
		return true;
	}
	
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
	        	
	        	CostgroupBusinessAssesmentDialogFragment fragment = new CostgroupBusinessAssesmentDialogFragment();
				fragment.mListener = Costgroup.this;
				fragment.show(getSupportFragmentManager(), "");
	        	return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
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
	            
	            //Wenn User --opitonal-- bei Spinner tolerance ausw�hlt, dann darf dies nicht im Layout angezeigt werden
	            String[] periodes = getResources().getStringArray(R.array.tolerances);
	            //--opitonal-- wert
	            String optionalString= periodes[0];
	            	
	            if(tolerance.equals(optionalString)) 
	            tolerance = "";
	            
	            //CostelementListViewItem newCostelement = new CostelementListViewItem(name, desc, value, getResources().getString(R.string.currency), periode, tolerance); 
	            //addCostelement(newCostelement);   	
        	} 
	    } 	  
	}
	
	public void addCostelement (CostelementListViewItem costelement) {
		items.add(costelement);
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
	}

	@Override
	public void setOnSubmitListenerCostgroupBusinessAssesment(int periode,
			int amountPeriods) {
		Intent intent = new Intent(Costgroup.this, CostgroupBusinessAssesment.class);
		intent.putExtra(CostgroupBusinessAssesment.COSTGROUPTAG, group);
		int days = -1;
		switch(periode) {
		case TCostelement.DAYLY:
			days = amountPeriods;
			break;
		case TCostelement.WEEKLY:
			days = amountPeriods * 7;
			break;
		case TCostelement.MONTHLY:
			days = amountPeriods * 30;
			break;
		case TCostelement.QUART:
			days = amountPeriods * 90;
			break;
		case TCostelement.YEARLY:
			days = amountPeriods * 360;
			break;
		default:
			Log.e(LOGTAG, CostgroupBusinessAssesment.WRONGPERIOD);
			throw new RuntimeException(CostgroupBusinessAssesment.WRONGPERIOD);
		}
		intent.putExtra(CostgroupBusinessAssesment.DAYSTAG, days);
		startActivity(intent);
	}
}
	

