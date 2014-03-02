package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * dialog for adding a new costgroup to the database
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AddCostgroupDialogFragment extends DialogFragment  {
	private EditText 	costgroupName;
	private EditText	costgroupDescription;
	private Button 		saveButton;
	
	public static String costgroupNameforDialog;
	public static String costgroupDescforDialog;
	
	public AddCostgroupDialogFragment() {
	}
	
	interface onSubmitListener {  
		void setOnSubmitListener(String arg, String arg1);  
	}  
	
	protected onSubmitListener mListener;
	
	@Override  
	public Dialog onCreateDialog(Bundle savedInstanceState) {  
		final Dialog dialog = new Dialog(getActivity());  
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.fragment_add_costgroup);
		dialog.show();
			
		saveButton 				= (Button) dialog.findViewById(R.id.buttonSaveCostgroup);
		costgroupName 			= (EditText) dialog.findViewById(R.id.fragment_dialog_add_costgroup_name); 
		costgroupDescription	= (EditText) dialog.findViewById(R.id.fragment_dialog_add_costgroup_description);
		costgroupName.setText(costgroupNameforDialog);
		costgroupDescription.setText(costgroupDescforDialog);
		costgroupNameforDialog 	= "";
		costgroupDescforDialog 	= "";
		
	 	saveButton.setOnClickListener(new OnClickListener() {    
			@Override  
			public void onClick(View v) { 
				if(costgroupName.getText().toString().equals("") || costgroupName.getText().toString().trim().equals(" "))
					Toast.makeText(getActivity(), getResources().getString(R.string.err_no_costgroup_name), Toast.LENGTH_LONG).show();
				else {
				    mListener.setOnSubmitListener(costgroupName.getText().toString(), costgroupDescription.getText().toString()); 
				    dismiss(); 
				}
			}  
		});  
		return dialog;    
	}
}
  


	

