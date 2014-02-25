package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class BusinessAssesment extends Activity {

	public static final String LOGTAG 			= "BusinessAssesment";
	public static final String ELEMENTLISTTAG 	= "elementlist";
	public static final String COSTGROUPTAG 	= "costgroup";
	public static final String DAYSTAG 			= "days";
	//error messages
	public static final String WRONGPERIOD 		= "wrong period";
	public static final String EMPTYLIST 		= "elementlist is empty";
	
	private List<TCostelement> 	elementList;
	private TCostgroup 			costgroup;
	private int					days;
	private double				sum = 0.0;
	private OnClickListener 	chartButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openChart();
		}
	};
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_business_assesment);
		
		//initializing widgets
		TextView 	elements 		= (TextView) findViewById(R.id.costgroup_business_assesment_elements);
		TextView 	costgroupView 	= (TextView) findViewById(R.id.costgroup_business_assesment_costgroup);
		TextView 	sumView 		= (TextView) findViewById(R.id.costgroup_business_assesment_sum);
		Button 		chartButton		= (Button) findViewById(R.id.costgroup_business_assesment_btn);
		chartButton.setOnClickListener(chartButtonListener);
		//getting information from intent
		Intent 		intent 			= getIntent();
		Bundle 		bundle 			= intent.getExtras();
		if(bundle != null) {
			elementList = (List<TCostelement>) bundle.get(ELEMENTLISTTAG);
			costgroup 	= (TCostgroup) bundle.get(COSTGROUPTAG);
			days 		= bundle.getInt(DAYSTAG);
			//displaying name of costgroup
			costgroupView.append(costgroup.getName());
		}
		
		//checking if elementlist is empty
		if(elementList.isEmpty()) {
			Log.e(LOGTAG, EMPTYLIST);
			throw new RuntimeException(EMPTYLIST);
		}
		
		//displaying values for each element in list
		for(TCostelement e : elementList) {
			sum += computeValue(e.getValue(), e.getPeriod());
			double endValue = Math.round(100.0 * computeValue(e.getValue(), e.getPeriod())) / 100.0;	//rounding values
			elements.append(e.getName() + ": " + endValue + " (" + e.getValue() + findPeriod(e.getPeriod()) + ")\n");
		}
		
		//computing sums and displaying them
		double perDay 	= Math.round(100.0 * sum/days) / 100.0;			//rounding values
		double perWeek 	= Math.round(100.0 * sum/days*7) / 100.0;
		double perMonth = Math.round(100.0 * sum/days*30) / 100.0;
		double perQuart = Math.round(100.0 * sum/days*90) / 100.0;
		double perYear 	= Math.round(100.0 * sum/days*360) / 100.0;
		sumView.append(sum + " (" + perDay + getResources().getString(R.string.dayly) + "; " + perWeek + getResources().getString(R.string.weekly)
					   + "; " + perMonth + getResources().getString(R.string.monthly) + "; " + perQuart + getResources().getString(R.string.quart)
					   + "; " + perYear + getResources().getString(R.string.yearly) + ")");
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
			Log.e(LOGTAG, WRONGPERIOD);
			throw new RuntimeException(WRONGPERIOD);
		}
		return type;
	}
	
	/**
	 * computing value for given phase
	 * @param value value per period
	 * @param period period in which value needs to be payed
	 * @return computed value 
	 */
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
			Log.e(LOGTAG, WRONGPERIOD);
			throw new RuntimeException(WRONGPERIOD);
		}
		return computedValue;
	}

	private void openChart(){
		 
        // Pie Chart Section Names
        String[] code = new String[elementList.size()];
        for(int i = 0; i < code.length; i++)
        	code[i] = elementList.get(i).getName();
 
        // Pie Chart Section Value
        double[] distribution = new double[elementList.size()];
        for(int i = 0; i < distribution.length; i++)
        	distribution[i] = elementList.get(i).getValue();
        
        // generating colors
        int[] colors = new int[elementList.size()];
        for(int i = 0; i < colors.length; i++)
        	colors[i] = Color.rgb((int)(Math.random()*1000)%255, (int)(Math.random()*1000)%255, (int)(Math.random()*1000)%255);
 
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(getResources().getString(R.string.categories) + costgroup.getName());
        for(int i=0 ;i < distribution.length;i++){
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
 
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ;i<distribution.length;i++){
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
 
        defaultRenderer.setChartTitle(getResources().getString(R.string.categories) + costgroup.getName());
        defaultRenderer.setChartTitleTextSize(20);
        defaultRenderer.setZoomButtonsVisible(true);
 
        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, "AChartEnginePieChartDemo");
 
        // Start Activity
        startActivity(intent);
 
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business_assesment, menu);
		return true;
	}

}
