package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import de.bandb.costinator.customadapter.CostelementListViewItem;
import de.bandb.costinator.customadapter.CostgroupListViewItem;
import de.bandb.costinator.database.entities.TCostelement;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCostelement extends Activity  {
	public static final String COSTELEMENTTAG 			= "costelement";
	private static final String	LOGTAG 					= "Costgroup_NewCostelement";
	
	private Button 		save;
	private EditText 	name;
	private EditText	desc;
	private EditText 	value;
	private Spinner 	periode;
	private Spinner		tolerance;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_costelement);

		save 		= (Button) findViewById(R.id.buttonSaveCostelement);
		name 		= (EditText) findViewById(R.id.editTextCostelementName);
		desc 		= (EditText) findViewById(R.id.editTextCostelementDesc);
		value 		= (EditText) findViewById(R.id.editTextCostelementValue);
		periode 	= (Spinner) findViewById(R.id.spinnerCostelementPeriodical);
		tolerance 	= (Spinner) findViewById(R.id.spinnerCostelementTolerance);
		save.setOnClickListener(saveListener);
		
		
		
		Intent intent 		= getIntent();
	   	Bundle b = intent.getExtras();
	
	   	if (b != null) {
		CostelementListViewItem element = (CostelementListViewItem) b.get(Costgroup.COSTELEMENT);
		name.setText(element.getName());
		desc.setText(element.getDesc());
		value.setText(element.getValue());
		
		String tag 		= getString(R.string.dayly);
		String woche	= getString(R.string.weekly);
		String monat	= getString(R.string.monthly);
		String quartal	= getString(R.string.quart);
		String jahr		= getString(R.string.yearly);
		
		if		(tag.equals(element.getPeriode())) {
			periode.setSelection(1);
			
	} 	else if (woche.equals(element.getPeriode())) {
			periode.setSelection(2);
			
	}	else if (monat.equals(element.getPeriode())) {
			periode.setSelection(3);
		
	}	else if (quartal.equals(element.getPeriode())) {
			periode.setSelection(4);
		
	}	else if (jahr.equals(element.getPeriode())) {
			periode.setSelection(5);
	}	
		
		
		String selectedTolerance = element.getTolerance();
		String [] tolerances = getResources().getStringArray(R.array.tolerances);
		for(int i =0; i < tolerances.length; i++){
		
			if(tolerances[i].equals(selectedTolerance)) {
				tolerance.setSelection(i);
				break;
				}
				
			}
		}
		
}	
			

		


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_costelement, menu);
		return true;
	}

	OnClickListener saveListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!(name.getText().toString().equals("") || name.getText().toString().trim().equals(" ") ||
				value.getText().toString().equals("") || Double.valueOf(value.getText().toString()) <= 0.0
			   || periode.getSelectedItemId() == 0)) {
			
				CostelementListViewItem element = new CostelementListViewItem(name.getText().toString(), desc.getText().toString(),
													value.getText().toString(), getResources().getString(R.string.currency), periode.getSelectedItem().toString(),
													tolerance.getSelectedItem().toString());
				Intent returnIntent 	= new Intent();
				returnIntent.putExtra(COSTELEMENTTAG, element);
				
				setResult(RESULT_OK, returnIntent);     
				finish();
			}
			else
				Toast.makeText(NewCostelement.this, getResources().getString(R.string.err_missing_costelement_data), Toast.LENGTH_LONG).show();
		}
	};
	
	
}
