package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.widget.TextView;

public class BusinessAssesment extends Activity {

	public static final String ELEMENTLISTTAG 	= "elementlist";
	public static final String COSTGROUPTAG 	= "costgroup";
	public static final String DAYSTAG 			= "days";
	public static final String WRONGPERIOD 		= "wrong period";
	
	private List<TCostelement> 	elementList;
	private TCostgroup 			costgroup;
	private int					days;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_assesment);
		
		TextView 	elements = (TextView) findViewById(R.id.costgroup_business_assesment_elements);
		TextView 	costgroupView = (TextView) findViewById(R.id.costgroup_business_assesment_costgroup);
		Intent 		intent = getIntent();
		Bundle 		bundle = intent.getExtras();
		if(bundle != null) {
			elementList = (List<TCostelement>) bundle.get(ELEMENTLISTTAG);
			costgroup 	= (TCostgroup) bundle.get(COSTGROUPTAG);
			days 		= bundle.getInt(DAYSTAG);
			costgroupView.append(costgroup.getName());
		}
		if(elementList.isEmpty());
		for(TCostelement e : elementList)
			elements.append(e.getName() + ": " + e.getValue());
	}
	
	private double computeValue(double value, int period, Date from, Date until) {
		double computedValue = -1.0;
		switch(period) {
		case TCostelement.DAYLY:
			computedValue = value * days;
			break;
		case TCostelement.WEEKLY:
			computedValue = value * (days/7);
			break;
		case TCostelement.MONTHLY:
			computedValue = value * (days/30);
			break;
		case TCostelement.QUART:
			computedValue = value * (days/90);
			break;
		case TCostelement.YEARLY:
			computedValue = value * (days/360);
			break;
		default:
			throw new RuntimeException(WRONGPERIOD);
		}
		return computedValue;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business_assesment, menu);
		return true;
	}

}
