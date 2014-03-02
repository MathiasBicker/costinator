package de.bandb.costinator;

/**
 * author: Mathias Bicker, Marc Brissier
 * version: 1.0
 * dialog to get Information from the user about the business assesment
 * he/she wants to issue
 */

import de.bandb.costinator.database.entities.TCostelement;
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
	private static final String LOGTAG = "CostgroupBusinessAssesmentDialogFragment";
	
	private RadioGroup	 periods;
	private RadioButton  period;
	private	SeekBar		 amountPeriods;
	private Button  	 save;
	private TextView	 progressInfo;
	
	public CostgroupBusinessAssesmentDialogFragment() {
	}
	
	interface onSubmitListenerCostgroupBusinessAssesment {  
		  void setOnSubmitListenerCostgroupBusinessAssesment(int periode, int value);  
		 }  
	
	protected onSubmitListenerCostgroupBusinessAssesment mListener;
	
	@Override  
	public Dialog onCreateDialog(Bundle savedInstanceState) {  
		final Dialog dialog = new Dialog(getActivity());  
		dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);  
		dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		dialog.setContentView(R.layout.fragment_costgroup_business_assesment);
		dialog.show();
			
		save 			= (Button) dialog.findViewById(R.id.button_costgroup_business_assesment_evaluate);
		progressInfo	= (TextView) dialog.findViewById(R.id.title_fragment_dialog_costgroup_business_assesment_seekbar_info_progress);
		periods			= (RadioGroup) dialog.findViewById(R.id.radioGroupPeriods);
		amountPeriods	= (SeekBar) dialog.findViewById(R.id.seekBarAmountPeriods);
		progressInfo.setText("0" + " " + getString(R.string.days));

		amountPeriods.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
				int selectedId= periods.getCheckedRadioButtonId();
				period = (RadioButton) dialog.findViewById(selectedId);
				
				String daily = getString(R.string.dailyAssesmentSpinner);
				String weekly = getString(R.string.weeklyAssesmentSpinner);
				String monthly = getString(R.string.monthlyAssesmentSpinner);
				String quartal = getString(R.string.quartalAssesmentSpinner);
				String yearly = getString(R.string.yearlyAssesmentSpinner);
				if(period.getText().toString().equals(daily)) {
					String days = getString(R.string.days);
					String day	= getString(R.string.day);
					if(arg1 == 1)
						progressInfo.setText(arg1+" "+ day);
					progressInfo.setText(arg1+" "+ days);
				}else if(period.getText().toString().equals(weekly)) {
					String weeks = getString(R.string.weeks);
					String week  = getString(R.string.week);
					if(arg1 == 1)
						progressInfo.setText(arg1+" "+ week);
					progressInfo.setText(arg1+" "+ weeks);
				}else if(period.getText().toString().equals(monthly)) {
					String months = getString(R.string.months);
					String month  = getString(R.string.month);
					if(arg1 == 1)
						progressInfo.setText(arg1+" "+ month);
					progressInfo.setText(arg1+" "+ months);
				}else if(period.getText().toString().equals(quartal)) {
					String quartals = getString(R.string.quartals);
					String quartalSingle = getString(R.string.quartal);
					if(arg1 == 1)
						progressInfo.setText(arg1+" "+ quartalSingle);
					progressInfo.setText(arg1+" "+ quartals);
				} else if (period.getText().toString().equals(yearly)) {
					String years = getString(R.string.years);
					String year  = getString(R.string.year);
					if(arg1 == 1)
						progressInfo.setText(arg1+" "+ year);
					progressInfo.setText(arg1+" "+ years);
				}	
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
		amountPeriods.setProgress(1);
	
	 	save.setOnClickListener(new OnClickListener() {    
			@Override  
			public void onClick(View v) {  
				int selectedId 	= periods.getCheckedRadioButtonId();
				int value	 	= amountPeriods.getProgress();
				period = (RadioButton) dialog.findViewById(selectedId);
				int periodValue = -1;
				if(period.getText().toString().equals(getString(R.string.dailyAssesmentSpinner)))
					periodValue = TCostelement.DAYLY;
				if(period.getText().toString().equals(getString(R.string.weeklyAssesmentSpinner)))
					periodValue = TCostelement.WEEKLY;
				if(period.getText().toString().equals(getString(R.string.monthlyAssesmentSpinner)))
					periodValue = TCostelement.MONTHLY;
				if(period.getText().toString().equals(getString(R.string.quartalAssesmentSpinner)))
					periodValue = TCostelement.QUART;
				if(period.getText().toString().equals(getString(R.string.yearlyAssesmentSpinner)))
					periodValue = TCostelement.YEARLY;
				if(periodValue < 1) {
					Log.e(LOGTAG, CostgroupBusinessAssesment.WRONGPERIOD);
					throw new RuntimeException(CostgroupBusinessAssesment.WRONGPERIOD);
				}
			    mListener.setOnSubmitListenerCostgroupBusinessAssesment(periodValue, value); 
			    dismiss();  
			}  
		});  
	return dialog;    
	}
}