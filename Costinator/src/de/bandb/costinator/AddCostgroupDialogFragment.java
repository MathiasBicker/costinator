package de.bandb.costinator;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import android.view.View;
import android.view.View.OnClickListener;

import android.view.Window;
import android.view.WindowManager;

import android.widget.Button;
import android.widget.EditText;







public class AddCostgroupDialogFragment extends DialogFragment  {
	
	private EditText costgroupName;
	private Button saveButton;
	
	
	public AddCostgroupDialogFragment() {}
	
	
	interface onSubmitListener {  
		  void setOnSubmitListener(String arg);  
		 }  
	
	public onSubmitListener mListener;
	
	@Override  
	 public Dialog onCreateDialog(Bundle savedInstanceState) {  
	  final Dialog dialog = new Dialog(getActivity());  
	  dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
	  dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);  
	  dialog.setContentView(R.layout.fragment_add_costgroup);
	
	  dialog.show();
		
	  saveButton = (Button) dialog.findViewById(R.id.buttonSave);  
	  costgroupName = (EditText) dialog.findViewById(R.id.fragment_dialog_add_costgroup_name);  
	  
	  saveButton.setOnClickListener(new OnClickListener() {  
	  
	   @Override  
	   public void onClick(View v) {  
	    mListener.setOnSubmitListener(costgroupName.getText().toString());  
	    dismiss();  
	   }  
	  });  
	  return dialog; 
	  
	  
	}


	
	
}
  


	

