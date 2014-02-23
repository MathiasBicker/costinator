package de.bandb.costinator;

import java.util.Date;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

public class NewCostelementPeriodical extends Activity implements Parcelable  {

	private Button 		save;
	private EditText 	name;
	private EditText	value;
	private DatePicker  startDate;
	private DatePicker  endDate;
	private Spinner 	periode;
	
	private double 		valueDouble;
	
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

		@Override
		public void onClick(View v) {
			
			
			String nameString = name.getText().toString();
			valueDouble = Double.parseDouble(value.getText().toString());
			
			Date startdate = new Date();
			startdate.setMonth(startDate.getMonth());
			startdate.setYear(startDate.getYear());
			startdate.setDate(startDate.getDayOfMonth());
		
			Date enddate = new Date();
			enddate.setMonth(endDate.getMonth());
			enddate.setYear(endDate.getYear());
			enddate.setDate(endDate.getDayOfMonth());
			
			
			String periodeString = periode.getSelectedItem().toString();
			
			CostelementListViewItem newCostelement = new CostelementListViewItem( nameString, valueDouble, startdate, enddate, periodeString);
			
			Intent intent = new Intent();
//			intent.put
		}
		
		
		
	};



	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}}
