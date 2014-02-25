package de.bandb.costinator;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class CostgroupBusinessAssesmentDialogFragment extends DialogFragment {
	
	private Spinner periode;
	private Spinner scenario;
	private Button  save;
	
	public CostgroupBusinessAssesmentDialogFragment() {}
	
	interface onSubmitListenerCostgroupBusinessAssesment {  
		  void setOnSubmitListenerCostgroupBusinessAssesment(String periode, String scenario);  
		 }  
	
	public onSubmitListenerCostgroupBusinessAssesment mListener;
	
	@Override  
	public Dialog onCreateDialog(Bundle savedInstanceState) {  
	final Dialog dialog = new Dialog(getActivity());  
	dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	dialog.setContentView(R.layout.fragment_costgroup_business_assesment);
	dialog.show();
		
	save 		= (Button)  dialog.findViewById(R.id.button_costgroup_business_assesment_evaluate);
	periode		= (Spinner) dialog.findViewById(R.id.spinner_costgroup_business_assesment_periode);
	scenario	= (Spinner) dialog.findViewById(R.id.spinner_costgroup_business_assesment_scenario);
	
 	save.setOnClickListener(new OnClickListener() {    
		@Override  
		public void onClick(View v) {  
		    mListener.setOnSubmitListenerCostgroupBusinessAssesment(periode.getSelectedItem().toString(), scenario.getSelectedItem().toString()); 
		   
		    
		    dismiss();  
		}  
	});  
	return dialog;    
	}
}
