package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * activity to issue a business assesment for a given costgroup
 * and displaying it via text and bar-/pie-charts
 */


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
import android.widget.Toast;

public class CostgroupBusinessAssesment extends OrmLiteBaseActivity<DatabaseHelper> {
	public static final String 	LOGTAG 			= "BusinessAssesment";
	//tags for inter-activity-communication
	public static final String 	COSTGROUPTAG 	= "costgroup";
	public static final String 	DAYSTAG 		= "days";
	public static final int		CASEAMOUNT		= 3;
	public static final int 	BEST			= 100;
	public static final int 	AVG				= 101;
	public static final int 	WORST			= 102;
	
	//error messages
	public static final String WRONGPERIOD 		= "wrong period";
	public static final String EMPTYLIST 		= "elementlist is empty";
	
	private List<TCostelement> 			elementList;
	private List<TCostelement> 			toleranceList = new ArrayList<TCostelement>();
	private TCostgroup 					costgroup;
	private String						phase;
	private int							days;
	private double						sum			= 0.0;
	private double						bestSum 	= 0.0;
	private double						worstSum 	= 0.0;
	private double						max;
	private String						currency;
	private OnClickListener chartButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(toleranceList.isEmpty())
				Toast.makeText(CostgroupBusinessAssesment.this, getResources().getString(R.string.err_no_tolerance_items), Toast.LENGTH_LONG).show();
			else
				openBarChart();
		}
	};
	private OnClickListener chartBestButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openChart(BEST);
		}
	};
	private OnClickListener chartAvgButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openChart(AVG);
		}
	};
	private OnClickListener chartWorstButtonListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			openChart(WORST);
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
		Button		bestButton		= (Button) findViewById(R.id.btn_chart_best);
		Button		avgButton		= (Button) findViewById(R.id.btn_chart_avg);
		Button		worstButton		= (Button) findViewById(R.id.btn_chart_worst);
		bestButton.setOnClickListener(chartBestButtonListener);
		avgButton.setOnClickListener(chartAvgButtonListener);
		worstButton.setOnClickListener(chartWorstButtonListener);
		chartButton.setOnClickListener(chartButtonListener);
		//getting information from intent
		Intent 		intent 			= getIntent();
		Bundle 		bundle 			= intent.getExtras();
		currency = getResources().getString(R.string.currency);
		if(bundle != null) {
			costgroup 	= (TCostgroup) bundle.get(COSTGROUPTAG);
			elementList = getHelper().queryAllCostelements(costgroup);
			days 		= bundle.getInt(DAYSTAG);
			//displaying name of costgroup
			costgroupView.append(" " + costgroup.getName());
		}
		
		//displaying period of the business assesment
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
		
		if(elementList.isEmpty()) {
			Log.e(LOGTAG, EMPTYLIST);
			throw new RuntimeException(EMPTYLIST);
		}
		
		//displaying values for each element in list and computing best-/worst-case values
		for(TCostelement e : elementList) {
			checkCurrency(e); //check if value needs to be computed depending on currency
			e.setEndvalue(Math.round(100.0 * computeValue(e.getValue(), e.getPeriod())) / 100.0);	//rounding values
			if(e.getTolerance() == 0) {
				e.setBestValue(e.getEndvalue());
				e.setWorstValue(e.getEndvalue());
			}else {
				e.setBestValue(Math.round(100.0 * (e.getEndvalue() - e.getEndvalue() * e.getTolerance() / 100.0)) / 100.0);
				e.setWorstValue(Math.round(100.0 * (e.getEndvalue() + e.getEndvalue() * e.getTolerance() / 100.0)) / 100.0);
				toleranceList.add(e);
			}
			elements.append(e.getName() + ": \n"+ " (" + e.getValue() + currency + findPeriod(e.getPeriod()) + ")\n\n\n");
			values.append(e.getBestValue() + currency + "\n" + e.getEndvalue() + currency + "\n" + e.getWorstValue() + currency + "\n\n");
		}
		
		if(!(toleranceList.isEmpty()))
				max = toleranceList.get(0).getWorstValue(); //computing max value for bar-chart maximum value of y-axis
		//computing sums and displaying them
		for(TCostelement c : toleranceList) {
			if(c.getWorstValue() > max)
				max = c.getWorstValue();
			sum 		+= c.getEndvalue();
			bestSum 	+= c.getBestValue();
			worstSum 	+= c.getWorstValue();
		}
		
		double perDay 	= Math.round(100.0 * sum/days) / 100.0;			//rounding values
		double perWeek 	= Math.round(100.0 * sum/days*7) / 100.0;
		double perMonth = Math.round(100.0 * sum/days*30) / 100.0;
		//persisting monthly cost for costgroup
		costgroup.setMonthlyCost(perMonth);
		getHelper().update(checkCurrency(costgroup));
		double perQuart = Math.round(100.0 * sum/days*90) / 100.0;
		double perYear 	= Math.round(100.0 * sum/days*360) / 100.0;
		sum				= Math.round(100.0 * sum) / 100.0;	
		bestSum = Math.round(100 * bestSum) / 100;
		worstSum = Math.round(100 * worstSum) / 100;
		sumView.append("\n" + perDay + currency + getResources().getString(R.string.dayly) + "\n" + perWeek + currency + getResources().getString(R.string.weekly)
					   + "\n" + perMonth + currency + getResources().getString(R.string.monthly) + "\n" + perQuart + currency + getResources().getString(R.string.quart)
					   + "\n" + perYear + currency + getResources().getString(R.string.yearly) + "");
		sumValues.append(bestSum + currency + "\n" + sum + currency + "\n" + worstSum + currency);
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
	
	/**
	 * presenting bar chart copmaring all costelements with a tolerance
	 * in a bar chart which is displayed in a new graphical activity
	 */
	private void openBarChart() {
		XYMultipleSeriesRenderer renderer = getTruitonBarRenderer();
        myChartSettings(renderer);
        Intent intent = ChartFactory.getBarChartIntent(this, getTruitonBarDataset(), renderer, Type.DEFAULT, getResources().getString(R.string.barchart_title));
        startActivity(intent);
	}

	/**
	 * showing the spreading of the costelements via a pie chart which is 
	 * displayed in a new graphical activity
	 * @param caseType best-, average-, worst- case
	 */
	private void openChart(int caseType) {
        // Pie Chart Section Names
        String[] code = new String[elementList.size()];
        for(int i = 0; i < code.length; i++)
        	code[i] = elementList.get(i).getName();
 
        // Pie Chart Section Value
        double[] distribution = new double[elementList.size()];
        for(int i = 0; i < distribution.length; i++)
        	switch(caseType) {
        	case BEST:
        		distribution[i] = elementList.get(i).getBestValue();
        		break;
        	case AVG:
        		distribution[i] = elementList.get(i).getEndvalue();
        		break;
        	case WORST:
        		distribution[i] = elementList.get(i).getWorstValue();
        		break;
        	}
        
        // generating colors
        int[] colors = new int[elementList.size()];
        for(int i = 0; i < colors.length; i++)
        	colors[i] = Color.rgb((int)(Math.random()*1000)%255, (int)(Math.random()*1000)%255, (int)(Math.random()*1000)%255);
 
        // Instantiating CategorySeries to plot Pie Chart
        CategorySeries distributionSeries = new CategorySeries(getResources().getString(R.string.categories) + costgroup.getName());
        for(int i=0 ; i < distribution.length; i++) {
            // Adding a slice with its values and name to the Pie Chart
            distributionSeries.add(code[i], distribution[i]);
        }
 
        // Instantiating a renderer for the Pie Chart
        DefaultRenderer defaultRenderer  = new DefaultRenderer();
        for(int i = 0 ; i < distribution.length; i++) {
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(colors[i]);
            seriesRenderer.setDisplayChartValues(true);
            // Adding a renderer for a slice
            defaultRenderer.addSeriesRenderer(seriesRenderer);
        }
 
        //settings
        defaultRenderer.setChartTitle(costgroup.getName());
        defaultRenderer.setChartTitleTextSize(55);
        defaultRenderer.setLabelsColor(Color.BLACK);
        defaultRenderer.setLegendTextSize(50);
        defaultRenderer.setFitLegend(true);
        defaultRenderer.setZoomButtonsVisible(false);
 
        Intent intent = ChartFactory.getPieChartIntent(getBaseContext(), distributionSeries , defaultRenderer, getResources().getString(R.string.cost_allocation) + " " +  costgroup.getName());
        startActivity(intent);
    }
	
	/**
	 * creating datasets for the bar chart
	 */
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
	            for(TCostelement e : toleranceList)
	            	worstSeries.add(e.getWorstValue());
	            dataset.addSeries(worstSeries.toXYSeries());
	            break;
        	case 1:
        		CategorySeries avgSeries = new CategorySeries(legendTitles.get(i));
	            for(TCostelement e : toleranceList)
	            	avgSeries.add(e.getEndvalue());
	            dataset.addSeries(avgSeries.toXYSeries());
	            break;
        	case 2:
        		CategorySeries bestSeries = new CategorySeries(legendTitles.get(i));
	            for(TCostelement e : toleranceList)
	            	bestSeries.add(e.getBestValue());
	            dataset.addSeries(bestSeries.toXYSeries());
	            break;
        	}
        }
        return dataset;
    }
 
	/**
	 * creating a renderer containg a renderer for each case for the bar chart
	 */
    public XYMultipleSeriesRenderer getTruitonBarRenderer() {
        XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
        renderer.setAxisTitleTextSize(30);
        renderer.setChartTitleTextSize(50);
        renderer.setLabelsTextSize(30);
        renderer.setLegendTextSize(30);
        renderer.setMargins(new int[] { 10, 40, 80, 0 });
        SimpleSeriesRenderer r = new SimpleSeriesRenderer();
        r.setColor(Color.RED);
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(30);
        r.setChartValuesTextAlign(Align.CENTER);
        r.setChartValuesFormat(NumberFormat.getCurrencyInstance());
        r.setChartValuesSpacing(75);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.BLUE);
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(30);
        r.setChartValuesTextAlign(Align.CENTER);
        r.setChartValuesFormat(NumberFormat.getCurrencyInstance());
        r.setChartValuesSpacing(40);
        renderer.addSeriesRenderer(r);
        r = new SimpleSeriesRenderer();
        r.setColor(Color.GREEN);
        r.setDisplayChartValues(true);
        r.setChartValuesTextSize(30);
        r.setChartValuesTextAlign(Align.CENTER);
        r.setChartValuesFormat(NumberFormat.getCurrencyInstance());
        r.setChartValuesSpacing(5);
        renderer.addSeriesRenderer(r);
        return renderer;
    }
 
    /**
     * settings for the optics of the bar chart
     */
    private void myChartSettings(XYMultipleSeriesRenderer renderer) {
        renderer.setXAxisMin(0.5);
        renderer.setXAxisMax(toleranceList.size() + 0.5);
        renderer.setYAxisMin(0);
        renderer.setYAxisMax(max + max*0.1);
        for(int i = 0; i < toleranceList.size(); i++)
        	renderer.addXTextLabel(i+1, toleranceList.get(i).getName());
        renderer.setYLabelsAlign(Align.RIGHT);
        renderer.setBarSpacing(0.5);
        renderer.setBarWidth(40);
        renderer.setDisplayValues(true);
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
	
	/**
	 * check if currency needs to be calculated, and doing so if nessesary
	 */
	private TCostelement checkCurrency(TCostelement c) {
		if(getResources().getConfiguration().locale.equals(Locale.US)) {
			double value = c.getValue();
			double exchangeRate = Double.valueOf(getResources().getString(R.string.exchange_rate));
			c.setValue(Math.round(100.0 * value * exchangeRate) / 100.0);
		}
		return c;
	}
	
	/**
	 * check if currency needs to be calculated, and doing so if nessesary
	 */
	private TCostgroup checkCurrency(TCostgroup c) {
		if(getResources().getConfiguration().locale.equals(Locale.US)) {
			double value = c.getMonthlyCost();
			double exchangeRate = Double.valueOf(getResources().getString(R.string.exchange_rate));
			c.setMonthlyCost((Math.round(100.0 * value / exchangeRate) / 100.0));
		}
		return c;
	}
}
