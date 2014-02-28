package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 */

import de.bandb.costinator.customadapter.CostelementListViewItem;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class NewCostelement extends Activity  {
	public static final String COSTELEMENTTAG = "costelement";
	
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
			   Double.valueOf(value.getText().toString()) <= 0.0 || periode.getSelectedItemId() == 0)) {
			
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
