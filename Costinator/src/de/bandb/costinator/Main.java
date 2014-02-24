package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;





import 	android.util.Log;
import de.bandb.costinator.AddCostgroupDialogFragment.onSubmitListener;
import de.bandb.costinator.CustomAdapter.CostgroupListViewItem;
import de.bandb.costinator.CustomAdapter.CustomAdapterListViewMain;
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

	
	private static final String TAG = "Main";
	
	public final static String COSTGROUP_TITLE = "de.bandb.costinator.COSTGROUP_TITLE";
	
	
	private ListView costgroupList;
	private ArrayList<CostgroupListViewItem> items;
	private ImageButton addCostgroup;
	//private ImageButton delCostgroup;
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addCostgroup = (ImageButton) findViewById(R.id.imageButtonAddNewCostgroup);
		//Muellheimer: delCostgroup = (ImageButton) findViewById(R.id.imageButtonCostgroupDelete);
		costgroupList = (ListView) findViewById(R.id.listViewMain);

		items = new ArrayList<CostgroupListViewItem>();

		/**
		 * Dummy Data
		 */
		CostgroupListViewItem costgroup1 = new CostgroupListViewItem("Haus",
				"-6634,61/month Û");
		CostgroupListViewItem costgroup2 = new CostgroupListViewItem("Auto",
				"-345,15/month Û");
		items.add(costgroup1);
		items.add(costgroup2);

		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));

		costgroupList.setOnItemClickListener(costgroupListListener);
		costgroupList.setOnItemLongClickListener(costgroupListLongListener);
		addCostgroup.setOnClickListener(addCostgroupListener);

		// delCostgroup.setOnClickListener(delCostgroupListener);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		 // Inflate the menu items for use in the action bar
	  
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/**
	 * Clicks auf ActionbarItems
	 */
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


	/**
	 * Start des DialogFragments, wenn auf ImageButton addCostgroup geklickt
	 * wird
	 */
	OnClickListener addCostgroupListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			AddCostgroupDialogFragment fragment = new AddCostgroupDialogFragment();
			fragment.mListener = Main.this;
			fragment.show(getSupportFragmentManager(), "");
		}
	};

	OnClickListener delCostgroupListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

		}

	};

	/**
	 * String arg enthaelt die Texteingabe von AddCostgroupDialogFragment, wird automatisch
	 * aufgerufen, sobald FragmentDialog beendet
	 */
	@Override
	public void setOnSubmitListener(String arg) {

		addCostgroup(arg);

	}

	/**
	 * Neues CostgroupListViewItem wird der ArrayList hinzugefuegt und die
	 * ListView wird erneut mit der aktualisierten ArrayList erstellt
	 * 
	 */
	public void addCostgroup(String name) {

		CostgroupListViewItem newCostgroup = new CostgroupListViewItem(name, "");
		items.add(newCostgroup);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));

	}

	
	
	public void deleteCostgroup(int position) {

		items.remove(position);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
	}

	
	/**
	 * Loescht Costgroup wenn lange gedrueckt wird
	 */
	OnItemLongClickListener costgroupListLongListener = new OnItemLongClickListener() {

		@Override
		public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {

			int position = arg2;
			deleteCostgroup(position);

			return false;
		}

	};

	OnItemClickListener costgroupListListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {

			//Start von Costgroup, der Title wird fuer den Kostengruppe title mitgegeben
			Intent intent = new Intent(Main.this, Costgroup.class);
			CostgroupListViewItem aktuelleCostgroup = (CostgroupListViewItem) items.get(arg2);
			String costgroupTitle = aktuelleCostgroup.getCostgroupTitle();
			Log.v(TAG, costgroupTitle);
			intent.putExtra(COSTGROUP_TITLE, costgroupTitle);
			startActivity(intent);

		}
	};

}
