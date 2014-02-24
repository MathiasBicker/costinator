package de.bandb.costinator;

import java.util.Date;

import de.bandb.costinator.customadapter.CostelementListViewItem;
import android.os.Bundle;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewCostelementPeriodical extends Activity  {

	
	public static final String PERIODICAL_COSTELEMENT_NAME = "periodicalName";
	public static final String PERIODICAL_COSTELEMENT_VALUE = "periodicalValue";
	public static final String PERIODICAL_COSTELEMENT_STARTDATE = "periodicalStartDate";
	public static final String PERIODICAL_COSTELEMENT_ENDDATE = "periodicalEndDate";
	public static final String PERIODICAL_COSTELEMENT_PERIODE = "periodicalPeriode";
	
	private Button save;
	private EditText name;
	private EditText value;
	private DatePicker startDate;
	private DatePicker endDate;
	private Spinner periode;


	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_costelement_periodical);

		save = (Button) findViewById(R.id.buttonSave);
		name = (EditText) findViewById(R.id.editTextCostelementNamePeriodical);
		value = (EditText) findViewById(R.id.editTextCostelementValuePeriodical);
		startDate = (DatePicker) findViewById(R.id.datePickerStartPeriodical);
		endDate = (DatePicker) findViewById(R.id.datePickerEndPeriodical);
		periode = (Spinner) findViewById(R.id.spinnerCostelementPeriodical);
		
		save.setOnClickListener(saveListener);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_costelement_periodical, menu);
		return true;
	}

	OnClickListener saveListener = new OnClickListener() {

		@SuppressWarnings("deprecation")
		@Override
		public void onClick(View v) {

			String nameString = name.getText().toString();
			double valueDouble = Double.parseDouble(value.getText().toString());

			Date startdate = new Date();
			startdate.setMonth(startDate.getMonth());
			startdate.setYear(startDate.getYear());
			startdate.setDate(startDate.getDayOfMonth());
			String startdateString = startdate.toString();

			Date enddate = new Date();
			enddate.setMonth(endDate.getMonth());
			enddate.setYear(endDate.getYear());
			enddate.setDate(endDate.getDayOfMonth());
			String enddateString = enddate.toString();

			String periodeString = periode.getSelectedItem().toString();


			Intent intent = new Intent();
			intent.putExtra(PERIODICAL_COSTELEMENT_NAME, nameString);
			intent.putExtra(PERIODICAL_COSTELEMENT_VALUE, valueDouble);
			intent.putExtra(PERIODICAL_COSTELEMENT_STARTDATE, startdateString);
			intent.putExtra(PERIODICAL_COSTELEMENT_ENDDATE, enddateString);
			intent.putExtra(PERIODICAL_COSTELEMENT_PERIODE, periodeString);
			finish();
		}
		

	};

	

}
