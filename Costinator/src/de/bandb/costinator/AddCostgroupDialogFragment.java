package de.bandb.costinator;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.view.WindowManager.LayoutParams;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;

import android.widget.TextView.OnEditorActionListener;






public class AddCostgroupDialogFragment extends DialogFragment implements IAddCostgroupDialogFragment  {
	
	private EditText costgroupName;
	
	public AddCostgroupDialogFragment() {}
	
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_costgroup, container);
        costgroupName = (EditText) view.findViewById(R.id.fragment_dialog_add_costgroup_name);
        getDialog().setTitle(R.string.title_dialog_add_costgroup);
        
        costgroupName.requestFocus();
        //setOnEditorActionListener(costgroupName);
        getDialog().getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        
        
        


        return view;
    }
	
	
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
        	onFinishEditDialog(costgroupName.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }


	@Override
	public void onFinishEditDialog(String string) {
		
		IAddCostgroupDialogFragment activity = (IAddCostgroupDialogFragment) getActivity();
        activity.onFinishEditDialog(string);
		
	}


	
	

  }


	

