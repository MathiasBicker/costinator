package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

import com.j256.ormlite.android.apptools.OrmLiteBaseActivity;

import de.bandb.costinator.database.DatabaseHelper;
import de.bandb.costinator.database.entities.TCostelement;
import de.bandb.costinator.database.entities.TCostgroup;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CostgroupBusinessAssesment extends OrmLiteBaseActivity<DatabaseHelper> {

	public static final String 	LOGTAG 			= "BusinessAssesment";
	public static final String 	COSTGROUPTAG 	= "costgroup";
	public static final String 	DAYSTAG 		= "days";
	public static final int		CASEAMOUNT		= 3;
	//error messages
	public static final String WRONGPERIOD 		= "wrong period";
	public static final String EMPTYLIST 		= "elementlist is empty";
	
	private List<TCostelement> 			elementList;
	private TCostgroup 					costgroup;
	private String						phase;
	private int							days;
	private double						sum			= 0.0;
	private double						bestSum 	= 0.0;
	private double						worstSum 	= 0.0;
	private double						max;
	private String						currency;
	private OnClickListener 			chartButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openBarChart();
			//openChart();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_costgroup_business_assesment);
		
		//initializing widgets
		TextView 	elements 		= (TextView) findViewById(R.id.costgroup_business_assesment_elements);
		TextView 	values	 		= (TextView) findViewById(R.id.costgroup_business_assesment_elements_values);
		TextView 	costgroupView 	= (TextView) findViewById(R.id.costgroup_business_assesment_costgroup);
		TextView 	sumView 		= (TextView) findViewById(R.id.costgroup_business_assesment_sum);
		TextView 	sumValues 		= (TextView) findViewById(R.id.costgroup_business_assesment_sum_values);
		Button 		chartButton		= (Button) findViewById(R.id.btn_chart_new);
		chartButton.setOnClickListener(chartButtonListener);
		//getting information from intent
		Intent 		intent 			= getIntent();
		Bundle 		bundle 			= intent.getExtras();
		currency	 				= getResources().getString(R.string.currency);
		if(bundle != null) {
			costgroup 	= (TCostgroup) bundle.get(COSTGROUPTAG);
			elementList = getHelper().queryAllCostelements(costgroup);
			days 		= bundle.getInt(DAYSTAG);
			//displaying name of costgroup
			costgroupView.append(" " + costgroup.getName());
		}
		
		if(days % 360 == 0 && days / 360 > 0)
			phase = String.valueOf(days / 360) + " " + getResources().getString(R.string.years);
		else if(days % 90 == 0 && days / 90 > 0)
			phase = String.valueOf(days / 90) + " " + getResources().getString(R.string.quartals);
		else if(days % 30 == 0 && days / 30 > 0)
			phase = String.valueOf(days / 30) + " " + getResources().getString(R.string.months);
		else if(days % 7 == 0 && days / 7 > 0)
			phase = String.valueOf(days / 7) + " " + getResources().getString(R.string.weeks);
		else 
			phase = String.valueOf(days) + " " + getResources().getString(R.string.days);
		
		costgroupView.append(" " + getResources().getString(R.string.phase) + " " + phase + ")");
		
		//checking if elementlist is empty
		if(elementList.isEmpty()) {
			Log.e(LOGTAG, EMPTYLIST);
			throw new RuntimeException(EMPTYLIST);
		}
		
		//displaying values for each element in list
		for(TCostelement e : elementList) {
			//sum += computeValue(e.getValue(), e.getPeriod());
			e.setEndvalue(Math.round(100.0 * computeValue(e.getValue(), e.getPeriod())) / 100.0);	//rounding values
			if(e.getTolerance() == 0) {
				e.setBestValue(e.getEndvalue());
				e.setWorstValue(e.getEndvalue());
			}else {
				e.setBestValue(e.getEndvalue() - e.getEndvalue() * e.getTolerance() / 100);
				e.setWorstValue(e.getEndvalue() + e.getEndvalue() * e.getTolerance() / 100);
			}
			elements.append(e.getName() + ": \n"+ " (" + e.getValue() + currency + findPeriod(e.getPeriod()) + ")\n");
			values.append(e.getEndvalue() + currency + "\n\n");
		}
		
		max = elementList.get(0).getWorstValue();
		//computing sums and displaying them
		for(TCostelement c : elementList) {
			if(c.getWorstValue() > max)
				max = c.getWorstValue();
			sum 		+= c.getEndvalue();
			bestSum 	+= c.getBestValue();
			worstSum 	+= c.getWorstValue();
		}
		
		double perDay 	= Math.round(100.0 * sum/days) / 100.0;			//rounding values
		double perWeek 	= Math.round(100.0 * sum/days*7) / 100.0;
		double perMonth = Math.round(100.0 * sum/days*30) / 100.0;
		double perQuart = Math.round(100.0 * sum/days*90) / 100.0;
		double perYear 	= Math.round(100.0 * sum/days*360) / 100.0;
		sum				= Math.round(100.0 * sum) / 100.0;	
		sumView.append("\n" + perDay + currency + getResources().getString(R.string.dayly) + "\n" + perWeek + currency + getResources().getString(R.string.weekly)
					   + "\n" + perMonth + currency + getResources().getString(R.string.monthly) + "\n" + perQuart + currency + getResources().getString(R.string.quart)
					   + "\n" + perYear + currency + getResources().getString(R.string.yearly) + "");
		sumValues.append(sum + currency);
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
		double doubleDays = (double) days;
		switch(period) {
		case TCostelement.DAYLY:
			computedValue = value * doubleDays;
			break;
		case TCostelement.WEEKLY:
			computedValue = value * (doubleDays/7);
			break;
		case TCostelement.MONTHLY:
			computedValue = value * (doubleDays/30);
			break;
		case TCostelement.QUART:
			computedValue = value * (doubleDays/90);
			break;
		case TCostelement.YEARLY:
			computedValue = value * (doubleDays/360);
			break;
		default:
			Log.e(LOGTAG, WRONGPERIOD);
			throw new RuntimeException(WRONGPERIOD);
		}
		return computedValue;
	}
	
	private void openBarChart() {
		XYMultipleSeriesRenderer renderer = getTruitonBarRenderer();
        myChartSettings(renderer);
        Intent intent = ChartFactory.getBarChartIntent(this, getTruitonBarDataset(), renderer, Type.DEFAULT, getResources().getString(R.string.barchart_title));
        startActivity(intent);
	}

	private void openChart(){
		 
        // Pie Chart Section Names
        String[] code = new String[elementList.size()];
        for(int i = 0; i < code.length; i++)
        	code[i] = elementList.get(i).getName();
 
        // Pie Chart Section Value
        double[] distribution = new double[elementList.size()];
        for(int i = 0; i < distribution.length; i++)
        	distribution[i] = elementList.get(i).getEndvalue();
        
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
 
        defaultRenderer.setChartTitle(costgroup.getName());
        defaultRenderer.setChartTitleTextSize(55);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLegendTextSize(50);
        defaultRenderer.setFitLegend(true);
        defaultRenderer.setZoomButtonsVisible(false);
 
        // Creating an intent to plot bar chart using dataset and multipleRenderer
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, getResources().getString(R.string.cost_allocation) + " " +  costgroup.getName());
 
        // Start Activity
        startActivity(intent);
 
    }
	
	private XYMultipleSeriesDataset getTruitonBarDataset() {
        XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
        ArrayList<String> legendTitles = new ArrayList<String>();
        legendTitles.add(getResources().getString(R.string.best_case));
        legendTitles.add(getResources().getString(R.string.avg_case));
        legendTitles.add(getResources().getString(R.string.worst_case));
        for (int i = 0; i < CASEAMOUNT; i++) {
        	switch(i) {
        	case 0:
	            CategorySeries worstSeries = new CategorySeries(legendTitles.get(i));
	            for(TCostelement e : elementList)
	            	worstSeries.add(e.getWorstValue());
	            dataset.addSeries(worstSeries.toXYSeries());
	            break;
        	case 1:
        		CategorySeries avgSeries = new CategorySeries(legendTitles.get(i));
	            for(TCostelement e : elementList)
	            	avgSeries.add(e.getEndvalue());
	            dataset.addSeries(avgSeries.toXYSeries());
	            break;
        	case 2:
        		CategorySeries bestSeries = new CategorySeries(legendTitles.get(i));
	            for(TCostelement e : elementList)
	            	bestSeries.add(e.getBestValue());
	            dataset.addSeries(bestSeries.toXYSeries());
	            break;
        	}
        }
        return dataset;
    }
 
    public XYMultipleSeriesRenderer getTruitonBarRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(30);
        renderer.setChartTitleTextSize(50);
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(30);
        renderer.setMargins(new int[] { 10, 40, 80, 0 });
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.GREEN);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.RED);
        renderer.addSeriesRenderer(r);
        return renderer;
    }
 
    private void myChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(elementList.size() + 0.5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(max + max*0.1);
        for(int i = 0; i < elementList.size(); i++)
        	renderer.addXTextLabel(i+1, elementList.get(i).getName());
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setBarSpacing(0.5);
        renderer.setXTitle(getResources().getString(R.string.x_axis));
        renderer.setYTitle(getResources().getString(R.string.y_axis));
        renderer.setShowGrid(true);
        renderer.setGridColor(Color.GRAY);
        renderer.setXLabels(0); // sets the number of integer labels to appear
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.business_assesment, menu);
		return true;
	}

}
