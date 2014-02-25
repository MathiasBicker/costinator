package de.bandb.costinator;



import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class NewCostelement extends Activity  {

	
	public static final String PERIODICAL_COSTELEMENT_NAME = "CostelementName";
	public static final String PERIODICAL_COSTELEMENT_VALUE = "CostelementValue";
	public static final String PERIODICAL_COSTELEMENT_DESC = "CostelementDesc";
	public static final String PERIODICAL_COSTELEMENT_TOLERANCE = "CostelementTolerance";
	public static final String PERIODICAL_COSTELEMENT_PERIODE = "CostelementPeriode";
	
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

		save = 		(Button) findViewById(R.id.buttonSaveCostelement);
		name =	 	(EditText) findViewById(R.id.editTextCostelementName);
		desc = 		(EditText) findViewById(R.id.editTextCostelementDesc);
		value = 	(EditText) findViewById(R.id.editTextCostelementValue);
		periode = 	(Spinner) findViewById(R.id.spinnerCostelementPeriodical);
		tolerance = (Spinner) findViewById(R.id.spinnerCostelementTolerance);
		
		
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

			String nameString = 		name.getText().toString();
			String descString =			desc.getText().toString();
			double valueDouble = 		Double.parseDouble(value.getText().toString());
			String periodeString = 		periode.getSelectedItem().toString();
			String toleranceString =	tolerance.getSelectedItem().toString();

			Intent returnIntent = new Intent();
			
			returnIntent.putExtra(PERIODICAL_COSTELEMENT_NAME, nameString);
			returnIntent.putExtra(PERIODICAL_COSTELEMENT_DESC, descString);
			returnIntent.putExtra(PERIODICAL_COSTELEMENT_VALUE, valueDouble);
			returnIntent.putExtra(PERIODICAL_COSTELEMENT_PERIODE, periodeString);
			returnIntent.putExtra(PERIODICAL_COSTELEMENT_TOLERANCE, toleranceString);
			
			
			setResult(RESULT_OK,returnIntent);     
			finish();
			
		}
		

	};

	

}
