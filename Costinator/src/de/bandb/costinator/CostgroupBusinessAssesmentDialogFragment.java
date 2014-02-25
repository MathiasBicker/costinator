package de.bandb.costinator;


import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

import android.widget.TextView;

public class CostgroupBusinessAssesmentDialogFragment extends DialogFragment {
	
	private RadioGroup	 periods;
	private RadioButton  period;
	private	SeekBar		 amountPeriods;
	private Button  	 save;
	private TextView	 progressInfo;
	
	
	public CostgroupBusinessAssesmentDialogFragment() {}
	
	interface onSubmitListenerCostgroupBusinessAssesment {  
		  void setOnSubmitListenerCostgroupBusinessAssesment(String periode, int value);  
		 }  
	
	public onSubmitListenerCostgroupBusinessAssesment mListener;
	
	@Override  
	public Dialog onCreateDialog(Bundle savedInstanceState) {  
	final Dialog dialog = new Dialog(getActivity());  
	dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
	dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	dialog.setContentView(R.layout.fragment_costgroup_business_assesment);
	dialog.show();
		
	save = 				(Button)     dialog.findViewById(R.id.button_costgroup_business_assesment_evaluate);
	progressInfo=		(TextView)	 dialog.findViewById(R.id.title_fragment_dialog_costgroup_business_assesment_seekbar_info_progress);
	periods=			(RadioGroup) dialog.findViewById(R.id.radioGroupPeriods);
	amountPeriods=		(SeekBar) 	 dialog.findViewById(R.id.seekBarAmountPeriods);
	
	amountPeriods.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

		@Override
		public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
			
			int selectedId= periods.getCheckedRadioButtonId();
			period = (RadioButton) dialog.findViewById(selectedId);
			
			progressInfo.setText(period.getText().toString()+""+arg1);
		}

		@Override
		public void onStartTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onStopTrackingTouch(SeekBar arg0) {
			// TODO Auto-generated method stub
			
		}
		
		
	});
	
	
 	save.setOnClickListener(new OnClickListener() {    
		@Override  
		public void onClick(View v) {  
			
			int selectedId= periods.getCheckedRadioButtonId();
			
			period = (RadioButton) dialog.findViewById(selectedId);
			
			int value = amountPeriods.getProgress();
			Log.v("amountPeriods", ""+value);
			
		    mListener.setOnSubmitListenerCostgroupBusinessAssesment(period.getText().toString(), value); 
		   
		    
		    dismiss();  
		}  
	});  
	return dialog;    
	}
}