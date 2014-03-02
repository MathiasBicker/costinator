package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import de.bandb.costinator.AddCostgroupDialogFragment.onSubmitListener;
import de.bandb.costinator.customadapter.CostgroupListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewMain;
import de.bandb.costinator.database.OrmLiteFragmentActivity;
import de.bandb.costinator.database.entities.TCostgroup;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;


public class Main extends OrmLiteFragmentActivity implements onSubmitListener {
	
	public static final String COSTGROUPTAG	 		 = "costgroup";
	
	private ListView 								costgroupList;
	public  ArrayList<CostgroupListViewItem> 		items;
	private ImageButton 							addCostgroup;
	private AlertDialog								dialog;
	private int 									position;
	private OnClickListener 						addCostgroupListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			AddCostgroupDialogFragment fragment = new AddCostgroupDialogFragment();
			fragment.mListener = Main.this;
			fragment.show(getSupportFragmentManager(), "");
		}
	};
	private OnItemLongClickListener costgroupListLongListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			
			position = arg2;
			dialog.show();
			dialog.getWindow().setLayout(600, 300);

			return false;
		}

	};
	private OnItemClickListener costgroupListListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position,
				long arg3) {
			//telling new activity the name of the costgroup
			Intent intent = new Intent(Main.this, Costgroup.class);
			CostgroupListViewItem group = (CostgroupListViewItem) items.get(position);
			intent.putExtra(COSTGROUPTAG, group);
			startActivity(intent);
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addCostgroup 			= (ImageButton) findViewById(R.id.imageButtonAddNewCostgroup);
		costgroupList 			= (ListView) findViewById(R.id.listViewMain);
		items 					= new ArrayList<CostgroupListViewItem>();
		List<TCostgroup> list	= getHelper().queryAllCostgroups();
		if(list != null)
			for(TCostgroup c : list) 
				items.add(checkCurrency(new CostgroupListViewItem(c, getResources().getString(R.string.currency))));
			
		//dummy data
		/*
		CostgroupListViewItem costgroup1 = new CostgroupListViewItem("Haus","XX", "6634,61/month");
		CostgroupListViewItem costgroup2 = new CostgroupListViewItem("Auto","BMW ALPINA 3", "-45,15/month");
		items.add(costgroup1);
		items.add(costgroup2);
		*/
		//__
		
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
		costgroupList.setOnItemClickListener(costgroupListListener);
		costgroupList.setOnItemLongClickListener(costgroupListLongListener);
		addCostgroup.setOnClickListener(addCostgroupListener);
		
		//Delete Dialog
				AlertDialog.Builder builder = new AlertDialog.Builder(Main.this);
				String titlePart = getString(R.string.title_costgroup_delete_dialog);
				builder.setTitle(titlePart);
				// Add the buttons
				builder.setPositiveButton(R.string.positive, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				        	   
				        	   deleteCostgroup(position);
				           }
				       });
				builder.setNegativeButton(R.string.negative, new DialogInterface.OnClickListener() {
				           public void onClick(DialogInterface dialog, int id) {
				               dialog.cancel();
				           }
				       });
				
				// Create the AlertDialog
				dialog = builder.create();

	}
	
	@Override
	public void onStart() {
		super.onStart();
		List<TCostgroup> list	= getHelper().queryAllCostgroups();
		if(list != null)
			for(TCostgroup c : list) 
				items.add(checkCurrency(new CostgroupListViewItem(c, getResources().getString(R.string.currency))));
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 // Inflate the menu items for use in the action bar
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle presses on the action bar items
	    switch (item.getItemId()) {
	        case R.id.action_addCostgroup:
	        	AddCostgroupDialogFragment fragment = new AddCostgroupDialogFragment();
				fragment.mListener = Main.this;
				fragment.show(getSupportFragmentManager(), "");
	            return true;
	        case R.id.action_settings:
	            //openSettings();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void setOnSubmitListener(String name, String desc) {
		TCostgroup group = new TCostgroup();
		group.setName(name);
		group.setDescription(desc);
		getHelper().create(group);
		addCostgroup(group);
	}
	
	private CostgroupListViewItem checkCurrency(CostgroupListViewItem c) {
		if(getResources().getConfiguration().locale.equals(Locale.US)) {
			double value = Double.valueOf(c.getTotalCost().substring(0, c.getTotalCost().length() - 2));
			double exchangeRate = Double.valueOf(getResources().getString(R.string.exchange_rate));
			c.setTotalCost(String.valueOf(Math.round(100.0 * value * exchangeRate) / 100.0));
		}
		return c;
	}

	/**
	 * adding new CostgroupListViewItem to ArrayList and
	 * creating new ListView with refreshed ArrayList
	 */
	public void addCostgroup(TCostgroup group) {
		CostgroupListViewItem newCostgroup = new CostgroupListViewItem(group, getResources().getString(R.string.currency));
		items.add(checkCurrency(newCostgroup));
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}

	public void deleteCostgroup(int position) {
		CostgroupListViewItem item = items.get(position);
		TCostgroup group = getHelper().queryCostgroup(item.getId());
		getHelper().delete(group);
		items.remove(position);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}
	
	@Override
	public void onResume()
	    {  // After a pause OR at startup
	    super.onResume();
	    items 					= new ArrayList<CostgroupListViewItem>();
	    List<TCostgroup> list	= getHelper().queryAllCostgroups();
		if(list != null)
			for(TCostgroup c : list) 
				items.add(checkCurrency(new CostgroupListViewItem(c, getResources().getString(R.string.currency))));
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	     }
	
}
