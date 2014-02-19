package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import de.bandb.costinator.AddCostgroupDialogFragment.onSubmitListener;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

public class Main extends FragmentActivity implements onSubmitListener {

	private ListView costgroupList;
	private ArrayList<CostgroupListViewItem> items;
	private ImageButton addCostgroup;
	private ImageButton delCostgroup;

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
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
	 * String arg enthaelt die Texteingabe von AddCostgroupDialogFragment
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

			Toast.makeText(getApplicationContext(), "" + arg2,
					Toast.LENGTH_SHORT).show();

		}
	};

}
