package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;


import 	android.util.Log;
import de.bandb.costinator.AddCostgroupDialogFragment.onSubmitListener;
import de.bandb.costinator.customadapter.CostgroupListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewMain;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;


public class Main extends FragmentActivity implements onSubmitListener {
	
	private static final String LOGTAG = "Main";
	
	public static final String COSTGROUP_TITLE	 		 = "de.bandb.costinator.COSTGROUP_TITLE";
	public static final String COSTGROUP_DESC	 		 = "de.bandb.costinator.COSTGROUP_DESC";
	public static final String COSTGROUP_TOTAL_COAST	 = "de.bandb.costinator.COSTGROUP_TOTALCOAST";
	
	private ListView 							costgroupList;
	private ArrayList<CostgroupListViewItem> 	items;
	private ImageButton 						addCostgroup;
	private OnClickListener 					addCostgroupListener = new OnClickListener() {
		@Override
		public void onClick(View v) {

			AddCostgroupDialogFragment fragment = new AddCostgroupDialogFragment();
			fragment.mListener = Main.this;
			fragment.show(getSupportFragmentManager(), "");
		}
	};
	private OnItemLongClickListener 			costgroupListLongListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {

			int position = arg2;
			deleteCostgroup(position);

			return false;
		}

	};
	private OnItemClickListener 				costgroupListListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			//telling new activity the name of the costgroup
			Intent intent = new Intent(Main.this, Costgroup.class);
			CostgroupListViewItem aktuelleCostgroup = (CostgroupListViewItem) items.get(arg2);
			String costgroupTitle 		= aktuelleCostgroup.getCostgroupTitle();
			String costgroupDesc		= aktuelleCostgroup.getCostgroupDesc();
			String costgroupTotalValue  = aktuelleCostgroup.getTotalCost();
			Log.v(LOGTAG, costgroupTitle);
			intent.putExtra(COSTGROUP_TITLE, costgroupTitle);
			intent.putExtra(COSTGROUP_DESC, costgroupDesc);
			intent.putExtra(COSTGROUP_TOTAL_COAST, costgroupTotalValue);
			startActivity(intent);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addCostgroup 	= (ImageButton) findViewById(R.id.imageButtonAddNewCostgroup);
		costgroupList 	= (ListView) findViewById(R.id.listViewMain);
		items 			= new ArrayList<CostgroupListViewItem>();
		String currency = getResources().getString(R.string.currency);
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
	public void setOnSubmitListener(String arg, String arg1) {
		addCostgroup(arg, arg1);
	}

	/**
	 * adding new CostgroupListViewItem to ArrayList and
	 * creating new ListView with refreshed ArrayList
	 */
	public void addCostgroup(String name, String desc) {
		CostgroupListViewItem newCostgroup = new CostgroupListViewItem(name, desc, "");
		items.add(newCostgroup);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}

	public void deleteCostgroup(int position) {
		items.remove(position);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}
}
