package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

public class Main extends FragmentActivity implements IAddCostgroupDialogFragment {

	private ListView costgroupList;
	private ArrayList<CostgroupListViewItem> items;
	private ImageButton addCostgroup;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addCostgroup = (ImageButton) findViewById(R.id.imageButtonAddNewCostgroup);
		costgroupList = (ListView) findViewById(R.id.listViewMain);

		items = new ArrayList<CostgroupListViewItem>();

		/**
		 * Dummy Data
		 */
		CostgroupListViewItem costgroup1 = new CostgroupListViewItem("Haus","-6634,61/month Û");
		CostgroupListViewItem costgroup2 = new CostgroupListViewItem("Auto","-345,15/month Û");
		items.add(costgroup1);
		items.add(costgroup2);

		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));

		addCostgroup.setOnClickListener(addCostgroupListener);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	OnClickListener addCostgroupListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			FragmentManager fm = getSupportFragmentManager();
			AddCostgroupDialogFragment addCostgroupDialog = new AddCostgroupDialogFragment();
			addCostgroupDialog.show(fm, "fragment_add_costgroup");
			

		}
	};
	
	/**
	 * Input Text von addCostgroupListener DialogFragment
	 */
	@Override
    public void onFinishEditDialog(String inputText) {
        
		CostgroupListViewItem newCostgroup = new CostgroupListViewItem(inputText);
		items.add(newCostgroup);
		costgroupList.setAdapter(new CustomAdapterListViewMain(items, this));
    }

}
