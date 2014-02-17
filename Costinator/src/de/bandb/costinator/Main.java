package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ListView;




public class Main extends Activity {

	
	private ListView costgroupList;
	private ArrayList<CostgroupListViewItem> items;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		costgroupList = (ListView) findViewById(R.id.listViewMain);
		
		items = new ArrayList<CostgroupListViewItem>();
		
		CostgroupListViewItem costgroup1 = new CostgroupListViewItem("Haus", "-6634,61/month Û");
		CostgroupListViewItem costgroup2 = new CostgroupListViewItem("Auto", "-345,15/month Û");
		items.add(costgroup1);
		items.add(costgroup2);
		
		costgroupList.setAdapter(new CustomAdapterListViewMain(items , this));
		
	}

	
	
	
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
