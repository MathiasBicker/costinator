package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * activity that displays all costelements of a given costgroup in a list
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.bandb.costinator.AddCostgroupDialogFragment.onSubmitListener;
import de.bandb.costinator.CostgroupBusinessAssesmentDialogFragment.onSubmitListenerCostgroupBusinessAssesment;
import de.bandb.costinator.customadapter.CostelementListViewItem;
import de.bandb.costinator.customadapter.CostgroupListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewCostgroup;
import de.bandb.costinator.database.OrmLiteFragmentActivity;
import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Toast;

public class Costgroup extends OrmLiteFragmentActivity implements onSubmitListenerCostgroupBusinessAssesment, onSubmitListener {
	//constants for inter-activity-communication
	public static final String	COSTELEMENT				= "Costelement";
	
	private static final String	LOGTAG 					= "Costgroup";
	private static final int	NEW_COSTELEMENT_REQUEST 	= 10;
	private static final int	EDIT_COSTELEMENT_REQUEST 	= 20;
	
	private ListView 							costelementList;
	private ArrayList<CostelementListViewItem> 	items;
	private TextView 							costgroupTitle;
	private TextView							costgroupDesc;
	private TextView							costgroupTotalCost;
	private ImageButton 						addCostelement;
	private TCostgroup							group;
	private int									position;
	private AlertDialog							dialog;
	private OnClickListener addCostelementListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//adding a new costelement via NewCostelement activity
			Intent intent = new Intent(Costgroup.this, NewCostelement.class);
			startActivityForResult(intent, NEW_COSTELEMENT_REQUEST);
		}
	};
	private OnItemLongClickListener longListener = new OnItemLongClickListener() {
		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			//deleting costelemnt via dialog
			position = arg2;
			dialog.show();
			dialog.getWindow().setLayout(600, 300);
			return false;
		}
	};
	private OnItemClickListener costelementListListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//Start NewCostelement for altering costelement
			position = arg2;
			Intent 					intent 	= new Intent(Costgroup.this, NewCostelement.class);
			CostelementListViewItem element = (CostelementListViewItem) items.get(position);
			intent.putExtra(COSTELEMENT, element);
			startActivityForResult(intent, EDIT_COSTELEMENT_REQUEST);
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
		items 				= new ArrayList<CostelementListViewItem>();
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
		//showing name, description and computed total cost of costgroup as title
		Intent 					intent 		= getIntent();
		Bundle 					b 			= intent.getExtras();
		CostgroupListViewItem 	costgroup 	= (CostgroupListViewItem) b.get(Main.COSTGROUPTAG);
		costgroupTitle.append(" " + costgroup.getCostgroupTitle());
		costgroupDesc.setText(costgroup.getCostgroupDesc());
		costgroupTotalCost.setText(costgroup.getTotalCost());
		//querying for all costelements from the database and displaying them in listview
		group = getHelper().queryCostgroup(costgroup.getId());
		List<TCostelement> list = getHelper().queryAllCostelements(group);
		if(list != null && !(list.isEmpty()))
			for(TCostelement e : list)
				items.add(checkCurrency(new CostelementListViewItem(e, findPeriod(e.getPeriod()), getResources().getString(R.string.currency))));
		addCostelement.setOnClickListener(addCostelementListener);
		costelementList.setOnItemLongClickListener(longListener);
		costelementList.setOnItemClickListener(costelementListListener);
		
		//Delete Dialog
		AlertDialog.Builder builder = new AlertDialog.Builder(Costgroup.this);
		String titlePart = getString(R.string.title_costelement_delete_dialog);
		builder.setTitle(titlePart);
		// Add the buttons
		builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
        	   delete(position);
           }
        });
		builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
           public void onClick(DialogInterface dialog, int id) {
               dialog.cancel();
           }
        });
		dialog = builder.create();
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
	        	//issuing business assessment via CostgroupBusinessAssesment activity and CostgroupBusinessAssesmentDialogFragment dialog
	        	if(items.isEmpty())
	        		Toast.makeText(this, getResources().getString(R.string.err_no_items), Toast.LENGTH_LONG).show();
	        	else {
		        	CostgroupBusinessAssesmentDialogFragment fragment = new CostgroupBusinessAssesmentDialogFragment();
					fragment.mListener = Costgroup.this;
					fragment.show(getSupportFragmentManager(), "");
	        	}
	        	return true;
	        case R.id.action_editCostgroup:
	        	//altering costgroup information
	        	AddCostgroupDialogFragment fragment = new AddCostgroupDialogFragment();
				fragment.mListener = Costgroup.this;
				Intent 					intent 		= getIntent();
				Bundle 					b 			= intent.getExtras();
				CostgroupListViewItem 	costgroup 	= (CostgroupListViewItem) b.get(Main.COSTGROUPTAG);
				AddCostgroupDialogFragment.costgroupNameforDialog = costgroup.getCostgroupTitle();
				AddCostgroupDialogFragment.costgroupDescforDialog = costgroup.getCostgroupDesc();
				fragment.show(getSupportFragmentManager(), "");
				return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	    // Check which request we're responding to
	    if(requestCode == NEW_COSTELEMENT_REQUEST)
	        // Make sure the request was successful
	        if(resultCode == RESULT_OK){
	        	//adding new costelement to database
	            Bundle 					b 		= data.getExtras();
	            CostelementListViewItem element = (CostelementListViewItem) b.get(NewCostelement.COSTELEMENTTAG);
	            addCostelement(checkCurrency(element));   	
        	} 
	    else if(requestCode == EDIT_COSTELEMENT_REQUEST)
	    	 if(resultCode == RESULT_OK) {
	    		 //updating altered costelement in database
	    		 Bundle 					b 		= data.getExtras();
		         CostelementListViewItem 	element = (CostelementListViewItem) b.get(NewCostelement.COSTELEMENTTAG);
		         updateCostelement(checkCurrency(element));
	    	 }
	}
	
	/**
	 * parsing CostelementListViewItem to TCostelement and adding it to database
	 */
	private void addCostelement (CostelementListViewItem costelement) {
		TCostelement element;
		if((element = getHelper().queryCostelement(costelement.getId())) == null)
			element = new TCostelement(costelement, findPeriodId(costelement.getPeriode()));
		element.setCostgroup(group);
		getHelper().create(element);
		items.add(new CostelementListViewItem(element, findPeriod(element.getPeriod()), getResources().getString(R.string.currency)));
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
	}
	
	/**
	 * parsing CostelementListViewItem to TCostelement and updating it in the database
	 */
	private void updateCostelement (CostelementListViewItem costelement) {
		TCostelement element = new TCostelement(costelement, findPeriodId(costelement.getPeriode()));
		element.setCostgroup(group);
		element.setId(costelement.getId());
		getHelper().update(element);
		items.set(position, costelement);
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
	}

	@Override
	public void setOnSubmitListenerCostgroupBusinessAssesment(int periode,
			int amountPeriods) {
		//checking return form CostgroupBusinessAssesmentDialogFragment dialog and starting activity CostgroupBusinessAssesment
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
	
	/**
	 * @param period period constant form TCostelement
	 * @return string containing e.g. '/dayly'
	 */
	public String findPeriod(int period) {
		String type = null;
		switch(period) {
		case TCostelement.DAYLY:
			type = getResources().getString(R.string.dayly);
			break;
		case TCostelement.WEEKLY:
			type = getResources().getString(R.string.weekly);
			break;
		case TCostelement.MONTHLY:
			type = getResources().getString(R.string.monthly);
			break;
		case TCostelement.QUART:
			type = getResources().getString(R.string.quart);
			break;
		case TCostelement.YEARLY:
			type = getResources().getString(R.string.yearly);
			break;
		default:
			Log.e(LOGTAG, CostgroupBusinessAssesment.WRONGPERIOD);
			throw new RuntimeException(CostgroupBusinessAssesment.WRONGPERIOD);
		}
		return type;
	}
	
	/**
	 * @param period period string containing e.g. '/dayly'
	 * @return string period constant form TCostelement
	 */
	public int findPeriodId(String period) {
		String[] array = getResources().getStringArray(R.array.periods);
		if(period.equals(array[0]))
			return TCostelement.DAYLY;
		else if(period.equals(array[1]))
			return TCostelement.WEEKLY;
		else if(period.equals(array[2]))
			return TCostelement.MONTHLY;
		else if(period.equals(array[3]))
			return TCostelement.QUART;
		else if(period.equals(array[4]))
			return TCostelement.YEARLY;
		else {
			Log.e(LOGTAG, CostgroupBusinessAssesment.WRONGPERIOD + period + getResources().getString(R.string.monthly));
			throw new RuntimeException(CostgroupBusinessAssesment.WRONGPERIOD);
		}
	}
	
	/**
	 * delete costelement on given position in the list and in the database
	 */
	public void delete(int position) {
		CostelementListViewItem item = items.get(position);
		TCostelement element = getHelper().queryCostelement(item.getId());
		getHelper().delete(element);
		items.remove(position);
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
	}
	
	/**
	 * check if currency needs to be calculated, and doing so if nessesary
	 */
	private CostelementListViewItem checkCurrency(CostelementListViewItem c) {
		if(getResources().getConfiguration().locale.equals(Locale.US)) {
			double value = Double.valueOf(c.getValue().substring(0, c.getValue().length() - 2));
			double exchangeRate = Double.valueOf(getResources().getString(R.string.exchange_rate));
			c.setValue(String.valueOf(Math.round(100.0 * value * exchangeRate) / 100.0));
		}
		return c;
	}

	@Override
	public void setOnSubmitListener(String title, String desc) {
		//updating costgroup in the database and as shown in title
		String 					titleappend = getString(R.string.title_activity_costgroup);
		costgroupTitle.setText(titleappend + " " + title);
		costgroupDesc.setText(desc);
		Intent 					intent 		= getIntent();
		Bundle 					b 			= intent.getExtras();
		CostgroupListViewItem 	costgroup 	= (CostgroupListViewItem) b.get(Main.COSTGROUPTAG);
		TCostgroup 				element 	= getHelper().queryCostgroup(costgroup.getId());
		element.setName(title);
		element.setDescription(desc);
		getHelper().update(element);
	}
}
	

