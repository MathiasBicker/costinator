package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.ArrayList;

import de.bandb.costinator.CostgroupBusinessAssesmentDialogFragment.onSubmitListenerCostgroupBusinessAssesment;
import de.bandb.costinator.customadapter.CostelementListViewItem;
import de.bandb.costinator.customadapter.CustomAdapterListViewCostgroup;
import android.os.Bundle;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

public class Costgroup extends FragmentActivity implements onSubmitListenerCostgroupBusinessAssesment {
	
	static final int NEW_COSTELEMENT_REQUEST = 10;
	
	private ListView 							costelementList;
	private ArrayList<CostelementListViewItem> 	items;
	private TextView 							costgroupTitle;
	private ImageButton 						addCostelement;
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
		
		costgroupTitle 	= (TextView) findViewById(R.id.textViewTitleCostgroup);
		addCostelement 	= (ImageButton) findViewById(R.id.imageButtonAddNewCostelement);
		costelementList = (ListView) findViewById(R.id.listViewCostgroup);

		items = new ArrayList<CostelementListViewItem>();
		String[] periods 	= getResources().getStringArray(R.array.periods);
		String currency 	= getResources().getString(R.string.currency);
		String tolerancetxt = getResources().getString(R.string.tolerancetxt);
		String percent 		= getResources().getString(R.string.percent);
		
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
		
		//dummy car
		CostelementListViewItem steuer 				= new CostelementListViewItem("Steuer", "KFZ-Steuer", 128.4, currency, periods[5]);
		CostelementListViewItem kraftstoff 			= new CostelementListViewItem("Kraftstoff", "Diesel", 150.0, currency, periods[3], tolerancetxt + "20" + percent);
		CostelementListViewItem versicherung 		= new CostelementListViewItem("Versicherung", "Barmenia Versicherungsnr: 3584399", 567.32, currency, periods[5]);
		CostelementListViewItem wartung 			= new CostelementListViewItem("Wartung", "Reperaturen, Reifen, etc.", 1500.0, currency, periods[5], tolerancetxt + "100" + percent);
		CostelementListViewItem finanzierungsrate 	= new CostelementListViewItem("Finanzierungsrate", "Darlehen Sparkasse 6% Zinsen pa.", 628.3, currency, periods[3]);
		items.add(steuer);
		items.add(kraftstoff);
		items.add(versicherung);
		items.add(wartung);
		items.add(finanzierungsrate);
		//-------
		//dummy car english
		/*
		CostelementListViewItem steuer 				= new CostelementListViewItem("Steuer", "KFZ-Steuer", 176.54, currency, periods[5]);
		CostelementListViewItem kraftstoff 			= new CostelementListViewItem("Kraftstoff", "Diesel", 206.24, currency, periods[3], tolerancetxt + "20" + percent);
		CostelementListViewItem versicherung 		= new CostelementListViewItem("Versicherung", "Barmenia Versicherungsnr: 3584399", 780.01, currency, periods[5]);
		CostelementListViewItem wartung 			= new CostelementListViewItem("Wartung", "Reperaturen, Reifen, etc.", 2062.35, currency, periods[5], tolerancetxt + "100" + percent);
		CostelementListViewItem finanzierungsrate 	= new CostelementListViewItem("Finanzierungsrate", "Darlehen Sparkasse 6% Zinsen pa.", 863.85, currency, periods[3]);
		items.add(steuer);
		items.add(kraftstoff);
		items.add(versicherung);
		items.add(wartung);
		items.add(finanzierungsrate);
		*/
		//-------
		//dummy flat
		/*
		CostelementListViewItem miete 		= new CostelementListViewItem("Miete", "Kaltmiete", 450.0, currency, periods[3]);
		CostelementListViewItem wasser 		= new CostelementListViewItem("Wasser", "Heizung, Wasser", 50.0, currency, periods[3], tolerancetxt + "10" + percent);
		CostelementListViewItem stellplatz 	= new CostelementListViewItem("Stellplatz", "Miete Stellplatz Auto", 25.0, currency, periods[3]);
		CostelementListViewItem internet 	= new CostelementListViewItem("Internet", "1&1 Internet, Telefon", 34.99, currency, periods[3]);
		CostelementListViewItem strom		= new CostelementListViewItem("Strom", "Strom Hitenergie", 47.86, currency, periods[3], tolerancetxt + "5" + percent);
		items.add(miete);
		items.add(wasser);
		items.add(stellplatz);
		items.add(internet);
		items.add(strom);
		*/
		//--
		//dummy flat english
		/*
		CostelementListViewItem miete 		= new CostelementListViewItem("Miete", "Kaltmiete", 618.71, currency, periods[3]);
		CostelementListViewItem wasser 		= new CostelementListViewItem("Wasser", "Heizung, Wasser", 68.75, currency, periods[3], tolerancetxt + "10" + percent);
		CostelementListViewItem stellplatz 	= new CostelementListViewItem("Stellplatz", "Miete Stellplatz Auto", 34.38, currency, periods[3]);
		CostelementListViewItem internet 	= new CostelementListViewItem("Internet", "1&1 Internet, Telefon", 48.11, currency, periods[3]);
		CostelementListViewItem strom		= new CostelementListViewItem("Strom", "Strom Hitenergie", 65.80, currency, periods[3], tolerancetxt + "5" + percent);
		items.add(miete);
		items.add(wasser);
		items.add(stellplatz);
		items.add(internet);
		items.add(strom);
		*/
		//--
		//dummy master
		/*
		CostelementListViewItem miete 			= new CostelementListViewItem("Miete", "WG-Zimmer", 300.0, currency, periods[3], tolerancetxt + "5" + percent);
		CostelementListViewItem versicherung	= new CostelementListViewItem("Versicherung", "Krankenversicherung Debeka", 120.0, currency, periods[3]);
		CostelementListViewItem handy 			= new CostelementListViewItem("Handyvertrag", "Vodafone Vertragsnr: 239203", 40.0, currency, periods[3]);
		CostelementListViewItem lebensmittel	= new CostelementListViewItem("Lebensmittel", "", 180.0, currency, periods[3], tolerancetxt + "20" + percent);
		CostelementListViewItem freizeit 		= new CostelementListViewItem("Freizeit", "Feiern, Kino etc.", 50.0, currency, periods[2], tolerancetxt + "25" + percent);
		CostelementListViewItem kleidung		= new CostelementListViewItem("Kleidung", "", 100.0, currency, periods[3], tolerancetxt + "30" + percent);
		items.add(miete);
		items.add(versicherung);
		items.add(handy);
		items.add(lebensmittel);
		items.add(freizeit);
		items.add(kleidung);
		*/
		//--
		//dummy master english
		/*
		CostelementListViewItem miete 			= new CostelementListViewItem("Miete", "WG-Zimmer", 412.47, currency, periods[3], tolerancetxt + "5" + percent);
		CostelementListViewItem versicherung	= new CostelementListViewItem("Versicherung", "Krankenversicherung Debeka", 164.99, currency, periods[3]);
		CostelementListViewItem handy 			= new CostelementListViewItem("Handyvertrag", "Vodafone Vertragsnr: 239203", 55.0, currency, periods[3]);
		CostelementListViewItem lebensmittel	= new CostelementListViewItem("Lebensmittel", "", 247.48, currency, periods[3], tolerancetxt + "20" + percent);
		CostelementListViewItem freizeit 		= new CostelementListViewItem("Freizeit", "Feiern, Kino etc.", 68.75, currency, periods[2], tolerancetxt + "25" + percent);
		CostelementListViewItem kleidung		= new CostelementListViewItem("Kleidung", "", 137.49, currency, periods[3], tolerancetxt + "30" + percent);
		items.add(miete);
		items.add(versicherung);
		items.add(handy);
		items.add(lebensmittel);
		items.add(freizeit);
		items.add(kleidung);
		*/
		//--
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
		
		//showing name of costgroup as title
		Intent intent 		= getIntent();
		String title		= intent.getStringExtra(Main.COSTGROUP_TITLE);
		String desc			= intent.getStringExtra(Main.COSTGROUP_DESC);
		String totalCost	= intent.getStringExtra(Main.COSTGROUP_TOTAL_COAST);
		costgroupTitle.setText(costgroupTitle.getText().toString() +" "+ title);
		
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
				
//	        	Intent intent = new Intent(Costgroup.this, BusinessAssesment.class);
//	        	
//	        	TCostgroup costgroup = new TCostgroup();
//	        	costgroup.setDescription("Stephanstr. 27A");
//	        	costgroup.setId(1);
//	        	costgroup.setName("Haus");
//	        	
//	        	TCostelement costelement1 = new TCostelement();
//	        	costelement1.setCostgroup(costgroup);
//	        	costelement1.setDescription("Frau Schmitt");
//	        	costelement1.setName("Putzfrau");
//	        	costelement1.setPeriod(2);
//	        	costelement1.setValue(100);
//	        	costelement1.setId(1);
//	        	
//	        	TCostelement costelement2 = new TCostelement();
//	        	costelement2.setCostgroup(costgroup);
//	        	costelement2.setDescription("Sch�ferhund");
//	        	costelement2.setName("Wachhund");
//	        	costelement2.setPeriod(3);
//	        	costelement2.setValue(1000);
//	        	costelement2.setId(2);
//	        	
//	        	elementList = new ArrayList<TCostelement>();
//	        	elementList.add(costelement1);
//	        	elementList.add(costelement2);
//	        	
//	        	int days = 180;
//	        	intent.putExtra(BusinessAssesment.DAYSTAG, days);
//	        	startActivity(intent);
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
	            
	            CostelementListViewItem newCostelement = new CostelementListViewItem(name, desc, value, getResources().getString(R.string.currency), periode, tolerance); 
	            addCostelement(newCostelement);   	
        	} 
	    } 	  
	}
	
	public void addCostelement (CostelementListViewItem costelement) {
		items.add(costelement);
		costelementList.setAdapter(new CustomAdapterListViewCostgroup(items, this));
	}

	@Override
	public void setOnSubmitListenerCostgroupBusinessAssesment(String periode,
			int amountPeriods) {
	}
}
	

