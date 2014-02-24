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
	private double				sum = 0.0;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_assesment);
		
		TextView 	elements 		= (TextView) findViewById(R.id.costgroup_business_assesment_elements);
		TextView 	costgroupView 	= (TextView) findViewById(R.id.costgroup_business_assesment_costgroup);
		TextView 	sumView 		= (TextView) findViewById(R.id.costgroup_business_assesment_sum);
		Intent 		intent 			= getIntent();
		Bundle 		bundle 			= intent.getExtras();
		if(bundle != null) {
			elementList = (List<TCostelement>) bundle.get(ELEMENTLISTTAG);
			costgroup 	= (TCostgroup) bundle.get(COSTGROUPTAG);
			days 		= bundle.getInt(DAYSTAG);
			costgroupView.append(costgroup.getName());
		}
		if(elementList.isEmpty());
		for(TCostelement e : elementList) {
			sum += computeValue(e.getValue(), e.getPeriod());
			double endValue = Math.round(100.0 * computeValue(e.getValue(), e.getPeriod())) / 100.0;	//rounding values
			elements.append(e.getName() + ": " + endValue + " (" + e.getValue() + findPeriod(e.getPeriod()) + ")\n");
		}
		
		double perDay = Math.round(100.0 * sum/days) / 100.0;			//rounding values
		double perWeek = Math.round(100.0 * sum/days*7) / 100.0;
		double perMonth = Math.round(100.0 * sum/days*30) / 100.0;
		double perQuart = Math.round(100.0 * sum/days*90) / 100.0;
		double perYear = Math.round(100.0 * sum/days*360) / 100.0;
		sumView.append(sum + " (" + perDay + getResources().getString(R.string.dayly) + "; " + perWeek + getResources().getString(R.string.weekly)
					   + "; " + perMonth + getResources().getString(R.string.monthly) + "; " + perQuart + getResources().getString(R.string.quart)
					   + "; " + perYear + getResources().getString(R.string.yearly) + ")");
	}
	
	private String findPeriod(int period) {
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
			throw new RuntimeException(WRONGPERIOD);
		}
		return type;
	}
	
	private double computeValue(double value, int period) {
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
